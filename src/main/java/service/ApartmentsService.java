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
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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

        UUID userUUID = UUID.fromString(jwt.getSubject());

        List<ApartmentViewModel> apartments = apartmentsMapper.getAllApartments(userUUID);
        return apartments;
    }

    @GET
    @Path("getApartmentById/{id}")
    @RolesAllowed({"StandardUser", "Administrator", "ExternalUser"})
    public ApartmentDetailsViewModel getApartmentById(@PathParam("id") UUID id, @Context SecurityContext securityContext) {
        UUID userUUID = UUID.fromString(jwt.getSubject());
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

        UUID userUUID = UUID.fromString(jwt.getSubject());

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

        UUID userUUID = UUID.fromString(jwt.getSubject());

        apartmentsMapper.removeFromFavorites(userUUID, adId);

        return Response.ok("Ajouté au favoris avec succès").build();
    }

    @GET
    @Path("getFavoriteApartments")
    @RolesAllowed({"StandardUser", "Administrator", "ExternalUser"})
    public List<ApartmentViewModel> getFavoriteApartments(@Context SecurityContext securityContext) {

        UUID userUUID = UUID.fromString(jwt.getSubject());
        List<ApartmentViewModel> apartments = apartmentsMapper.getFavoriteApartments(userUUID);
        return apartments;
    }

    @GET
    @Path("search")
    @RolesAllowed({"StandardUser", "Administrator", "ExternalUser"})
    public List<ApartmentViewModel> getApartmentsByCriteria(
            @QueryParam("userId") UUID userId,
            @QueryParam("minPrice") Float minPrice,
            @QueryParam("maxPrice") Float maxPrice,
            @QueryParam("disponibilityBefore") LocalDate disponibilityBefore,
            @QueryParam("sortBy") String sortBy,
            @QueryParam("minSellerScore") Float minSellerScore
    ) {
        return apartmentsMapper.getApartmentsByCriteria(
                userId, minPrice, maxPrice, disponibilityBefore, sortBy, minSellerScore
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

        java.nio.file.Path current = Paths.get("").toAbsolutePath();
        while (current != null && !current.getFileName().toString().equals("Laplace-du-Bazar")) {
            current = current.getParent();
        }

        java.nio.file.Path target = current.resolve("docker/webserver/adImages").resolve(adId.toString());
        String folderPath = target.toString();


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
            @RestForm("imageName") String imageName,
            @RestForm("image") File image
    ) {

        String cleanAdId = adId.replace("\"", "").trim();
        String folderPath = apartmentsMapper.getApartmentById(UUID.fromString(cleanAdId), UUID.fromString(jwt.getSubject())).getFolderPath();

        File adImageFolder = new File(folderPath);
        adImageFolder.mkdirs();

        if (!image.exists()) {
            System.out.println("Fichier image inexistant !");
        }

        try {
            File destination = new File(folderPath, imageName);
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

        UUID userUUID = UUID.fromString(jwt.getSubject());

        List<ApartmentViewModel> apartments = apartmentsMapper.getAllApartmentsFromUser(userUUID);
        return apartments;
    }

    @GET
    @Path("/getSizes")
    @RolesAllowed({"StandardUser", "Administrator", "ExternalUser"})
    public List<ApartmentSize> getSizes() {
        return apartmentsMapper.getSizes();
    }

    @GET
    @Path("/getImageListFromAdId/{adId}")
    @RolesAllowed({"StandardUser", "Administrator", "ExternalUser"})
    public List<String> getImageListFromAdId(@PathParam("adId") String adId) {
        String folderPath = apartmentsMapper.getFolderPath(UUID.fromString(adId));

        File dossier = new File(folderPath);

        if (dossier.isDirectory()) {
            List<String> images = Arrays.asList(dossier.list());
            return images;
        } else {
            System.out.println("Ce chemin n'est pas un dossier.");
            return List.of();
        }
    }

    @POST
    @Path("/archiveAd/{adId}")
    @RolesAllowed({"StandardUser", "Administrator", "ExternalUser"})
    public Response archiveAd(
            @PathParam("adId") String adId
    ) {
        String cleanAdId = adId.replace("\"", "").trim();
        UUID adUUID = UUID.fromString(cleanAdId);
        String sellerId = apartmentsMapper.getSellerId(adUUID);

        UUID test = UUID.fromString(jwt.getSubject());

        if(sellerId == null || !(Objects.equals(jwt.getSubject(), sellerId))) {
            return Response.serverError().entity("Erreur de permission").build();
        }

        apartmentsMapper.archiveAd(adUUID);

        return Response.ok().build();
    }

    @GET
    @Path("/getDefaultImage/{adId}")
    @RolesAllowed({"StandardUser", "Administrator", "ExternalUser"})
    public Response getDefaultImage(@PathParam("adId") String adId) {
        try {
            File folder = new File(apartmentsMapper.getFolderPath(UUID.fromString(adId)));
            File[] files = folder.listFiles(File::isFile);

            if (files != null && files.length > 0) {
                File firstFile = files[0];
                return Response.ok(firstFile.getName()).build();
            } else {
                return Response.ok("Aucune image trouvée").build();
            }
        } catch (Exception e){
            return  Response.ok("Aucune image").build();
        }

    }
}
