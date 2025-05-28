package service;

import business.Users;
import jakarta.annotation.security.PermitAll;
import mapper.UsersMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.inject.Inject;

import java.util.UUID;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsersService {

    @Inject
    UsersMapper usersMapper;

    @GET
    @Path("getUser/{id}")
    @PermitAll
    public Users getAllUsers(@PathParam("id") UUID id) {
        Users user = usersMapper.getUserById(id);
        return user;
    }
}
