package service;

import business.ApartmentDetailsViewModel;
import business.ApartmentSize;
import business.ApartmentViewModel;
import io.vertx.ext.web.FileUpload;
import jakarta.annotation.security.RolesAllowed;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.core.*;
import mapper.ApartmentsMapper;
import jakarta.ws.rs.*;
import jakarta.inject.Inject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import mapper.UsersMapper;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.reactive.RestForm;


@Path("/laplace/apartments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ApartmentsService {

    @Inject
    ApartmentsMapper apartmentsMapper;

    @Inject
    UsersMapper usersMapper;

    @Inject
    JsonWebToken jwt;
    @Inject
    Application application;

    @Context
    io.vertx.ext.web.RoutingContext rc;

    @GET
    @Path("getAllApartments")
    @RolesAllowed({"StandardUser", "Administrator", "ExternalUser"})
    public List<ApartmentViewModel> getAllApartments(@Context SecurityContext securityContext) {
        String userEmail = securityContext.getUserPrincipal().getName();

        String userId = usersMapper.getUserIdByEmail(userEmail);

        UUID userUUID = UUID.fromString(userId);

        List<ApartmentViewModel> apartments = apartmentsMapper.getAllApartments(userUUID);
        return apartments;
    }

    @GET
    @Path("getApartmentById/{id}")
    @RolesAllowed({"StandardUser", "Administrator", "ExternalUser"})
    public ApartmentDetailsViewModel getApartmentById(@PathParam("id") UUID id, @Context SecurityContext securityContext) {
        String userEmail = securityContext.getUserPrincipal().getName();

        String userId = usersMapper.getUserIdByEmail(userEmail);

        UUID userUUID = UUID.fromString(userId);
        return apartmentsMapper.getApartmentById(id, userUUID);
    }

    @GET
    @Path("deleteApartment")
    @RolesAllowed({"Administrator"})
    public void deleteApartment(@QueryParam("id") UUID id) {
        apartmentsMapper.deleteApartment(id);
    }

    @POST
    @Path("addToFavorites")
    @Consumes("application/json")
    @RolesAllowed({"StandardUser", "Administrator", "ExternalUser"})
    public Response addToFavorites(String jsonBody, @Context SecurityContext securityContext) {
        JsonObject json = Json.createReader(new StringReader(jsonBody)).readObject();
        UUID adId = UUID.fromString(json.getString("adId"));

        String userEmail = securityContext.getUserPrincipal().getName();

        String userId = usersMapper.getUserIdByEmail(userEmail);

        UUID userUUID = UUID.fromString(userId);

        apartmentsMapper.addToFavorites(userUUID, adId);

        return Response.ok("Ajouté au favoris avec succès").build();
    }

    @POST
    @Path("removeFromFavorites")
    @Consumes("application/json")
    @RolesAllowed({"StandardUser", "Administrator", "ExternalUser"})
    public Response removeFromFavorites(String jsonBody, @Context SecurityContext securityContext) {
        JsonObject json = Json.createReader(new StringReader(jsonBody)).readObject();
        UUID adId = UUID.fromString(json.getString("adId"));

        String userEmail = securityContext.getUserPrincipal().getName();

        String userId = usersMapper.getUserIdByEmail(userEmail);

        UUID userUUID = UUID.fromString(userId);

        apartmentsMapper.removeFromFavorites(userUUID, adId);

        return Response.ok("Ajouté au favoris avec succès").build();
    }

    @GET
    @Path("getFavoriteApartments")
    @RolesAllowed({"StandardUser", "Administrator", "ExternalUser"})
    public List<ApartmentViewModel> getFavoriteApartments(@Context SecurityContext securityContext) {
        String userEmail = securityContext.getUserPrincipal().getName();
        String userId = usersMapper.getUserIdByEmail(userEmail);
        UUID userUUID = UUID.fromString(userId);
        List<ApartmentViewModel> apartments = apartmentsMapper.getFavoriteApartments(userUUID);
        return apartments;
    }

    @GET
    @Path("search")
    @RolesAllowed({"StandardUser", "Administrator", "ExternalUser"})
    public List<ApartmentViewModel> getApartmentsByCriteria(
            @QueryParam("minPrice") Float minPrice,
            @QueryParam("maxPrice") Float maxPrice,
            @QueryParam("minScore") Float minScore,
            @QueryParam("disponibilityBefore") String disponibilityBefore,
            @QueryParam("apartmentSize") String apartmentSize,
            @QueryParam("sortBy") String sortBy
    ) {
        return apartmentsMapper.getApartmentsByCriteria(
                minPrice, maxPrice, minScore, disponibilityBefore, apartmentSize, sortBy
        );
    }

    @POST
    @Path("/createApartment")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response createApartment(
            @RestForm("title") String title,
            @RestForm("price") String price,
            @RestForm("description") String description,
            @RestForm("disponibility") String disponibility,
            @RestForm("apartmentSizeId") String apartmentSizeId
    ) {
        UUID adId = UUID.randomUUID();

        String folderPath = "../adsImages/" + adId;

        float flPrice = Float.parseFloat(price);

        //Création de l'annonce
        apartmentsMapper.createAd(adId, title, description, folderPath, flPrice, UUID.fromString(jwt.getSubject()));

        //Création de l'appartement
        UUID apartmentId = UUID.randomUUID();
        UUID apartmentSizeUUID = UUID.fromString(apartmentSizeId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(disponibility, formatter);

        apartmentsMapper.createApartment(apartmentId, date, null, apartmentSizeUUID, adId);
        return Response.ok(adId).build();
    }

    @POST
    @Path("/uploadImagesForAd/{adid}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadImagesForAd(
            @PathParam("adid") String adId,
            @RestForm("image") File image
    ) {

        var cleanAdId = adId.replace("\"", "").trim();
        String folderPath = apartmentsMapper.getApartmentById(UUID.fromString(cleanAdId), UUID.fromString(jwt.getSubject())).getFolderPath();

        File adImageFolder = new File(folderPath);
        adImageFolder.mkdirs();

        if (!image.exists()) {
            System.out.println("Fichier image inexistant !");
        }

        try {
            String originalName = image.getName();
            File destination = new File(folderPath, originalName);
            Files.copy(image.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            return Response.serverError().entity("Erreur de lecture").build();
        }
        return Response.ok().build();
    }

    @GET
    @Path("getAllApartmentsFromUser")
    @RolesAllowed({"StandardUser", "Administrator", "ExternalUser"})
    public List<ApartmentViewModel> getAllApartmentsFromUser(@Context SecurityContext securityContext) {
        String userEmail = securityContext.getUserPrincipal().getName();
        String userId = usersMapper.getUserIdByEmail(userEmail);
        UUID userUUID = UUID.fromString(userId);

        List<ApartmentViewModel> apartments = apartmentsMapper.getAllApartmentsFromUser(userUUID);
        return apartments;
    }
	
	@GET
	@Path("/getSizes")
    @RolesAllowed({"StandardUser", "Administrator", "ExternalUser"})
    public List<ApartmentSize> getSizes() {
        return apartmentsMapper.getSizes();
    }

}
