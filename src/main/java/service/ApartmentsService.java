package service;

import business.Apartment;
import business.ApartmentDetailsViewModel;
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
    @RolesAllowed({"StandardUser", "Administrator"})
    public void deleteApartment(@QueryParam("id") UUID id) {
        apartmentsMapper.deleteApartment(id);
    }

    @GET
    @Path("search")
    @RolesAllowed({"StandardUser", "Administrator"})
    public List<ApartmentViewModel> searchApartments(
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

}
