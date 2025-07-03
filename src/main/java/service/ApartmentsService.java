package service;

import business.Apartment;
import business.ApartmentDetailsViewModel;
import business.ApartmentViewModel;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.core.*;
import mapper.ApartmentsMapper;
import jakarta.ws.rs.*;
import jakarta.inject.Inject;

import java.io.StringReader;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import mapper.UsersMapper;
import org.eclipse.microprofile.jwt.JsonWebToken;


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
    @Path("createApartment")
    @RolesAllowed({"Administrator", "StandardUser"})
    @Consumes("application/json")
    public Response createApartment(String jsonBody) {
        JsonObject json = Json.createReader(new StringReader(jsonBody)).readObject();

        UUID adId = UUID.randomUUID();
        String title = json.getString("title");
        String description = json.getString("description");
        String folderPath = json.getString("folderPath", null);
        float price = json.getJsonNumber("price").bigDecimalValue().floatValue();
        apartmentsMapper.createAd(adId, title, description, folderPath, price, UUID.fromString(jwt.getSubject()));

        UUID apartmentId = UUID.randomUUID();
        Timestamp disponibility = new Timestamp(System.currentTimeMillis());
        String address = json.getString("address");
        UUID apartmentSizeId = UUID.fromString(json.getString("apartmentSizeId"));
        apartmentsMapper.createApartment(apartmentId, disponibility, address, apartmentSizeId, adId);

        return Response.ok().build();
    }
}
