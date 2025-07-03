package service;

import business.Book;
import business.BookViewModel;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import mapper.BooksMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;


@Path("/laplace/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BooksService {

    @Inject
    BooksMapper booksMapper;

    @Context
    SecurityContext securityContext;

    @GET
    @Path("getAllBooks")
    @RolesAllowed({"StandardUser", "Administrator", "ExternalUser"})
    public List<BookViewModel> getAllBooks() {
        UUID userId = UUID.fromString(securityContext.getUserPrincipal().getName());
        return booksMapper.getAllBooks(userId);
    }

    @GET
    @Path("getUser/{id}")
    @RolesAllowed({"StandardUser", "Administrator", "ExternalUser"})
    public BookViewModel getBookById(@PathParam("id") UUID id) {
        UUID userId = UUID.fromString(securityContext.getUserPrincipal().getName());
        return booksMapper.getBookById(id, userId);
    }

    @GET
    @Path("deleteBook")
    @RolesAllowed({"Administrator"})
    public void deleteBook(@QueryParam("id") UUID id) {
        booksMapper.deleteBook(id);
    }

    @POST
    @Path("{id}/favorite")
    @RolesAllowed({"StandardUser", "Administrator", "ExternalUser"})
    public void addToFavorites(@PathParam("id") UUID adId) {
        UUID userId = UUID.fromString(securityContext.getUserPrincipal().getName());
        booksMapper.addToFavorites(userId, adId);
    }

    @DELETE
    @Path("{id}/favorite")
    @RolesAllowed({"StandardUser", "Administrator", "ExternalUser"})
    public void removeFromFavorites(@PathParam("id") UUID adId) {
        UUID userId = UUID.fromString(securityContext.getUserPrincipal().getName());
        booksMapper.removeFromFavorites(userId, adId);
    }

    @GET
    @Path("favorites")
    @RolesAllowed({"StandardUser", "Administrator", "ExternalUser"})
    public List<BookViewModel> getFavoriteBooks() {
        UUID userId = UUID.fromString(securityContext.getUserPrincipal().getName());
        return booksMapper.getFavoriteBooks(userId);
    }

    @GET
    @Path("search")
    @RolesAllowed({"StandardUser", "Administrator", "ExternalUser"})
    public List<BookViewModel> getBooksByCriteria(
            @QueryParam("minPrice") Double minPrice,
            @QueryParam("maxPrice") Double maxPrice,
            @QueryParam("minScore") Double minScore,
            @QueryParam("categoryId") UUID categoryId,
            @QueryParam("sortBy") String sortBy
    ) {
        UUID userId = UUID.fromString(securityContext.getUserPrincipal().getName());
        return booksMapper.getBooksByCriteria(minPrice, maxPrice, minScore, categoryId, sortBy, userId);
    }
}


