package service;

import business.Equipment;
import business.EquipmentViewModel;
import jakarta.annotation.security.RolesAllowed;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import jakarta.inject.Inject;
import mapper.EquipmentsMapper;
import mapper.UsersMapper;

import java.io.StringReader;
import java.util.List;
import java.util.UUID;

@Path("/laplace/equipments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EquipmentsService {

    @Inject
    EquipmentsMapper equipmentsMapper;

    @Inject
    UsersMapper usersMapper;

    @GET
    @Path("getAllEquipments")
    @RolesAllowed({"StandardUser", "Administrator"})
    public List<EquipmentViewModel> getAllEquipments(@Context SecurityContext securityContext) {
        String userEmail = securityContext.getUserPrincipal().getName();
        UUID userUUID = UUID.fromString(usersMapper.getUserIdByEmail(userEmail));
        return equipmentsMapper.getAllEquipments(userUUID);
    }

    @GET
    @Path("getEquipmentById/{id}")
    @RolesAllowed({"StandardUser", "Administrator"})
    public Equipment getEquipmentById(@PathParam("id") UUID id, @Context SecurityContext securityContext) {
        String userEmail = securityContext.getUserPrincipal().getName();
        UUID userUUID = UUID.fromString(usersMapper.getUserIdByEmail(userEmail));
        return equipmentsMapper.getEquipmentById(id, userUUID);
    }

    @GET
    @Path("deleteEquipment")
    @RolesAllowed({"StandardUser", "Administrator"})
    public void deleteEquipment(@QueryParam("id") UUID id) {
        equipmentsMapper.deleteEquipment(id);
    }

    @POST
    @Path("addToFavorites")
    public Response addToFavorites(String jsonBody, @Context SecurityContext securityContext) {
        JsonObject json = Json.createReader(new StringReader(jsonBody)).readObject();
        UUID adId = UUID.fromString(json.getString("adId"));
        UUID userUUID = UUID.fromString(usersMapper.getUserIdByEmail(securityContext.getUserPrincipal().getName()));
        equipmentsMapper.addToFavorites(userUUID, adId);
        return Response.ok("Ajouté au favoris avec succès").build();
    }

    @POST
    @Path("removeFromFavorites")
    public Response removeFromFavorites(String jsonBody, @Context SecurityContext securityContext) {
        JsonObject json = Json.createReader(new StringReader(jsonBody)).readObject();
        UUID adId = UUID.fromString(json.getString("adId"));
        UUID userUUID = UUID.fromString(usersMapper.getUserIdByEmail(securityContext.getUserPrincipal().getName()));
        equipmentsMapper.removeFromFavorites(userUUID, adId);
        return Response.ok("Retiré des favoris avec succès").build();
    }

    @GET
    @Path("getFavoriteEquipments")
    @RolesAllowed({"StandardUser", "Administrator"})
    public List<EquipmentViewModel> getFavoriteEquipments(@Context SecurityContext securityContext) {
        UUID userUUID = UUID.fromString(usersMapper.getUserIdByEmail(securityContext.getUserPrincipal().getName()));
        return equipmentsMapper.getFavoriteEquipments(userUUID);
    }

    @GET
    @Path("search")
    @RolesAllowed({"StandardUser", "Administrator"})
    public List<EquipmentViewModel> getEquipmentsByCriteria(
            @QueryParam("minPrice") Float minPrice,
            @QueryParam("maxPrice") Float maxPrice,
            @QueryParam("minScore") Float minScore,
            @QueryParam("equipmentName") String equipmentName,
            @QueryParam("sortBy") String sortBy
    ) {
        return equipmentsMapper.getEquipmentsByCriteria(minPrice, maxPrice, minScore, equipmentName, sortBy);
    }
}
