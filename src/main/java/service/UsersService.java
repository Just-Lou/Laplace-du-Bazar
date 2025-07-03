package service;

import business.Users;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jdk.jfr.BooleanFlag;
import mapper.UsersMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.inject.Inject;

import java.security.Principal;
import java.util.*;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.jboss.resteasy.reactive.NoCache;
import org.eclipse.microprofile.jwt.JsonWebToken;


@Path("/laplace/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsersService {

    @Inject
    UsersMapper usersMapper;

    @Inject
    JsonWebToken jwt;

    @Inject
    SecurityIdentity securityIdentity;

    @GET
    @Path("getAllUsers")
    @RolesAllowed("Administrator")
    public List<Users> getAllUsers() {
        List<Users> users = usersMapper.getAllUsers();
        return users;
    }

    @GET
    @Path("getUser/{id}")
    @RolesAllowed("Administrator")
    public Users getUserById(@PathParam("id") UUID id) {
        Users user = usersMapper.getUserById(id);
        return user;
    }

    @GET
    @Path("createUser")
    @RolesAllowed({"Administrator", "StandardUser", "ExternalUser"})
    public Response createUser() {
        usersMapper.createUser(UUID.fromString(jwt.getSubject()), jwt.getClaim("given_name"), jwt.getClaim("family_name"),
                               jwt.getClaim("email"), securityIdentity.getRoles().toArray(new String[0]));

        return Response.ok().build();
    }

    @GET
    @Path("createUserByAdmin")
    @RolesAllowed("Administrator")
    public Response createUserByAdmin(@QueryParam("firstName") String firstName,
                                      @QueryParam("lastName") String lastName,
                                      @QueryParam("email") String email,
                                      @QueryParam("role") String[] roles) {

        usersMapper.createUserByAdmin(UUID.randomUUID(), firstName, lastName, email, roles);

        return Response.ok().build();
    }

    @GET
    @Path("updateUser/{id}")
    @RolesAllowed({"Administrator", "StandardUser", "ExternalUser"})
    public Response updateUser(@PathParam("id") UUID id,
                           @QueryParam("firstName") String firstName,
                           @QueryParam("lastName") String lastName,
                           @QueryParam("email") String email) {

        usersMapper.updateUser(id, firstName, lastName, email);

        return Response.ok().build();
    }

    @GET
    @Path("deleteUser/{id}")
    @RolesAllowed({"Administrator"})
    public Response deleteUser(@PathParam("id") UUID id) {
        if (!Objects.equals(jwt.getSubject(), id.toString()) && securityIdentity.getRoles().contains("StandardUser")) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        usersMapper.deleteUser(id);
        return Response.ok().build();
    }

    @GET
    @Path("/whoami")
    @RolesAllowed({"StandardUser", "Administrator", "ExternalUser"})
    public Map<String, Object> whoami() {
        Map<String, Object> userInfo = Map.of(
//                "jwt", jwt.getRawToken(),
                "userId", jwt.getSubject(),
                "username", jwt.getName(),
                "email", jwt.getClaim("email"),
                "firstName", jwt.getClaim("given_name"),
                "lastName", jwt.getClaim("family_name"),
                "roles", securityIdentity.getRoles().toArray(new String[0])[0]

        );
        return userInfo;
    }

    @GET
    @Path("/roles")
    @PermitAll
    public Set<String> getRoles() {
        return securityIdentity.getRoles();
    }

    // Necessaire pour logger avec quarkus, puis ensuire rediriger vers la page d'origine (pour le momement on force back a /login.html)
    @Path("/login")
    @GET
    @RolesAllowed({"StandardUser", "Administrator", "ExternalUser"}) //(Juste dans cette fonction) Ne pas mettre PermitAll, a la place mettre tous les roles autorises
    public Response redirectToWebsite() {
        if (usersMapper.getUserById(UUID.fromString(jwt.getSubject())) == null) {
            try {
                createUser();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Error creating user: " + e.getMessage())
                        .build();
            }
        }

        return Response.status(Response.Status.FOUND)
                .header("Location", "http://localhost/login.html")
                .build();
    }

    @GET
    @Path("/logout")
    @PermitAll
    public Response appLogout() {
        return Response.status(Response.Status.FOUND)
                .header("Location", "http://localhost:6969/realms/users/protocol/openid-connect/logout?redirect_uri=http://localhost/login.html")
                .header("Set-Cookie", "quarkus-credential=; Path=/; Max-Age=0; HttpOnly; SameSite=Lax")
                .header("Set-Cookie", "q_session=; Path=/; Max-Age=0; HttpOnly; SameSite=Lax")
                .build();
    }

}
