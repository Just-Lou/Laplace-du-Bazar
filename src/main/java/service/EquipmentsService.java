package service;

import business.Equipment;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import mapper.BooksMapper;
import mapper.EquipmentsMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;


@Path("/laplace/equipments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EquipmentsService {

    @Inject
    EquipmentsMapper equipmentsMapper;

    @GET
    @Path("getAllEquipments")
    @PermitAll
    public List<Equipment> getAllEquipments() {
        List<Equipment> equipments = equipmentsMapper.getAllEquipments();
        return equipments;
    }

    @GET
    @Path("getEquipment/{id}")
    @PermitAll
    public Equipment getAllEquipments(@PathParam("id") UUID id) {
        Equipment equipment = equipmentsMapper.getEquipmentById(id);
        return equipment;
    }

    @GET
    @Path("deleteEquipment")
    @PermitAll
    public void deleteEquipment(@QueryParam("id") UUID id) {
        equipmentsMapper.deleteEquipment(id);
    }
}
