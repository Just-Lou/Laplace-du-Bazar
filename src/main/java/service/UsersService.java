package service;

import business.Users;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import mapper.UsersMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.jboss.resteasy.reactive.NoCache;
import org.eclipse.microprofile.jwt.JsonWebToken;
import java.util.Set;



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
    public Users getAllUsers(@PathParam("id") UUID id) {
        Users user = usersMapper.getUserById(id);
        return user;
    }

    @GET
    @Path("/whoami2")
    @RolesAllowed({"StandardUser", "Administrator"})
    public String getCurrentUser(@Context SecurityContext securityContext) {
        return "User: " + securityContext.getUserPrincipal().getName();
    }

    @GET
    @Path("/whoami")
    @RolesAllowed({"StandardUser", "Administrator"})
    public String whoami(@Context SecurityContext securityContext) {
        String username = securityContext.getUserPrincipal().getName();
        return username;
    }

    @Inject
    SecurityIdentity identity; //je sais pas

    @GET
    @Path("/roles")
    @PermitAll
    public Set<String> getRoles() {
        return identity.getRoles();
    }

    // Necessaire pour logger avec quarkus, puis ensuire rediriger vers la page d'origine (pour le momement on force back a /login.html)
    @Path("/login")
    @GET
    @RolesAllowed({"StandardUser", "Administrator"}) //Ne pas mettre PermitAll, a la place mettre tous les roles autorises
    public Response redirectToWebsite() {
        return Response.status(Response.Status.FOUND)
                .header("Location", "http://localhost/login.html")
                .build();
    }


    @GET
    @Path("/logout")
    @PermitAll
    public Response appLogout() {
        return Response.ok() // ca marche pas jsp pourquoi
                .header("Set-Cookie", "quarkus-credential=; Path=/; Max-Age=0; HttpOnly; SameSite=Lax")
                .build();
    }
}
