package service;

import business.Apartment;
import jakarta.annotation.security.PermitAll;
import mapper.ApartmentsMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;


@Path("/laplace/apartments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ApartmentsService {

    @Inject
    ApartmentsMapper apartmentsMapper;

    @GET
    @Path("getAllApartments")
    @PermitAll
    public List<Apartment> getAllApartments() {
        List<Apartment> apartments = apartmentsMapper.getAllApartments();
        return apartments;
    }

    @GET
    @Path("getApartment/{id}")
    @PermitAll
    public Apartment getApartmentById(@PathParam("id") UUID id) {
        Apartment apartment = apartmentsMapper.getApartmentById(id);
        return apartment;
    }

}
