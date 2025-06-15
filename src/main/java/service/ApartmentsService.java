package service;

import business.Apartment;
import business.ApartmentDetailsViewModel;
import business.ApartmentViewModel;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import mapper.ApartmentsMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.inject.Inject;

import java.io.StringReader;
import java.util.List;
import java.util.UUID;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import mapper.UsersMapper;


@Path("/laplace/apartments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ApartmentsService {

    @Inject
    ApartmentsMapper apartmentsMapper;

    @Inject
    UsersMapper usersMapper;

    @GET
    @Path("getAllApartments")
    @RolesAllowed({"StandardUser", "Administrator"})
    public List<ApartmentViewModel> getAllApartments(@Context SecurityContext securityContext) {
        String userEmail = securityContext.getUserPrincipal().getName();

        String userId = usersMapper.getUserIdByEmail(userEmail);

        UUID userUUID = UUID.fromString(userId);

        List<ApartmentViewModel> apartments = apartmentsMapper.getAllApartments(userUUID);
        return apartments;
    }

    @GET
    @Path("getApartmentById/{id}")
    @RolesAllowed({"StandardUser", "Administrator"})
    public ApartmentDetailsViewModel getApartmentById(@PathParam("id") UUID id, @Context SecurityContext securityContext) {
        String userEmail = securityContext.getUserPrincipal().getName();

        String userId = usersMapper.getUserIdByEmail(userEmail);

        UUID userUUID = UUID.fromString(userId);
        return apartmentsMapper.getApartmentById(id, userUUID);
    }

    @GET
    @Path("deleteApartment")
    @PermitAll
    public void deleteApartment(@QueryParam("id") UUID id) {
        apartmentsMapper.deleteApartment(id);
    }

    @POST
    @Path("addToFavorites")
    @Consumes("application/json")
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
    public Response removeFromFavorites(String jsonBody, @Context SecurityContext securityContext) {
        JsonObject json = Json.createReader(new StringReader(jsonBody)).readObject();
        UUID adId = UUID.fromString(json.getString("adId"));

        String userEmail = securityContext.getUserPrincipal().getName();

        String userId = usersMapper.getUserIdByEmail(userEmail);

        UUID userUUID = UUID.fromString(userId);

        apartmentsMapper.removeFromFavorites(userUUID, adId);

        return Response.ok("Ajouté au favoris avec succès").build();
    }
}
