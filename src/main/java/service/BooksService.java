package service;

import business.Book;
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

    @GET
    @Path("getAllBooks")
    @RolesAllowed({"StandardUser", "Administrator"})
    public List<Book> getAllBooks() {
        List<Book> books = booksMapper.getAllBooks();
        return books;
    }

    @GET
    @Path("getUser/{id}")
    @RolesAllowed({"StandardUser", "Administrator"})
    public Book getBookById(@PathParam("id") UUID id) {
        Book book = booksMapper.getBookById(id);
        return book;
    }

    @GET
    @Path("deleteBook")
    @RolesAllowed({"StandardUser", "Administrator"})
    public void deleteBook(@QueryParam("id") UUID id) {
        booksMapper.deleteBook(id);
    }

}
