package com.cft.rest.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cft.rest.api.book.BookResponse;
import com.cft.rest.api.book.BookUpdate;
import com.cft.rest.api.book.CreateBookRequest;
import com.cft.rest.api.book.UpdateBookRequest;
import com.cft.rest.entities.Book;
import com.cft.rest.exceptions.DuplicateEntityException;
import com.cft.rest.exceptions.EntityNotFoundException;
import com.cft.rest.services.BookService;

import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

@Path("/books")
public class BookResource {

	private static final Logger LOGGER = LogManager.getLogger(BookResource.class);
	
	private final BookService bookService;

	@Inject
	public BookResource(BookService bookService) {
		this.bookService = bookService;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<BookResponse> findAllBooks() {
		return bookService
				.findAllBooks()
				.stream()
				.map(BookResponse::new)
				.collect(Collectors.toUnmodifiableList());				
	}
	
	@GET
	@Path("/{bookId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findBookById(@PathParam("bookId") long id) {
		try {
			Book book = bookService.findBookByUniqueId(id).orElseThrow();
			return Response.ok(new BookResponse(book)).build();
		} catch (Exception e) {
			return Response.status(Status.NOT_FOUND).build();
		}		
	}	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createBook(@NotNull CreateBookRequest request, @Context UriInfo uriInfo) {
		try {	
			Book book = bookService.addBook(new Book(request.getTitle(), request.getYearIssued(), 
					request.getAuthors(), request.getNumberOfCopies()));
			UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
			uriBuilder.path(String.valueOf(book.getId()));

			LOGGER.info("Successfully created a new book {}", book);
			return Response.created(uriBuilder.build()).build();
		} catch (DuplicateEntityException e) {
			return Response
				.status(Status.BAD_REQUEST)
				.entity(e.getMessage())
				.type(MediaType.APPLICATION_JSON)
				.build();			
		}
	}	

	@PATCH
	@Path("/{bookId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateBook(@PathParam("bookId") long id, @NotNull UpdateBookRequest request) {
		try {
			BookUpdate bookUpdate = new BookUpdate(id, request.getTitle(), request.getYearIssued(), 
					request.getNumberOfCopies(), request.getAuthors());
			Book updateBook = bookService.updateBook(bookUpdate);
			return Response.ok(new BookResponse(updateBook)).build();
		} catch (DuplicateEntityException e) {
			return Response
				.status(Status.BAD_REQUEST)
				.entity(e.getMessage())
				.type(MediaType.APPLICATION_JSON)
				.build();			
		} catch (EntityNotFoundException ex) {
			return Response
					.status(Status.NOT_FOUND)
					.entity(ex.getMessage())
					.type(MediaType.APPLICATION_JSON)
					.build();		
		}
 
	}

	@DELETE
    @Path("/{bookId}")
    public Response deleteBook(@PathParam("bookId") long id) {
    	try {
    		Book book = bookService.findBookByUniqueId(id).orElseThrow();
        	bookService.deleteBook(book);
        	return Response.noContent().build();
    	} catch (Exception e) {
    		return Response.status(Status.NOT_FOUND).build();
    	}
    }	
}
