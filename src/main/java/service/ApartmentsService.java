package service;

import business.Apartment;
import business.ApartmentViewModel;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;
import mapper.ApartmentsMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.inject.Inject;

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
    @Path("getApartment/{id}")
    @PermitAll
    public Apartment getApartmentById(@PathParam("id") UUID id) {
        Apartment apartment = apartmentsMapper.getApartmentById(id);
        return apartment;
    }

    @GET
    @Path("deleteApartment")
    @PermitAll
    public void deleteApartment(@QueryParam("id") UUID id) {
        apartmentsMapper.deleteApartment(id);
    }
}
