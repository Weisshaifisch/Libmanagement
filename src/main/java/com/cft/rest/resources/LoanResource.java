package com.cft.rest.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cft.rest.api.loan.BookManageRequest;
import com.cft.rest.api.loan.LoanResponse;
import com.cft.rest.entities.Book;
import com.cft.rest.entities.Loan;
import com.cft.rest.entities.User;
import com.cft.rest.exceptions.DuplicateEntityException;
import com.cft.rest.exceptions.NoMoreBooksAvailableException;
import com.cft.rest.services.BookService;
import com.cft.rest.services.LoanService;
import com.cft.rest.services.UserService;

import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/loans")
public class LoanResource {

	private final UserService userService;
	private final BookService bookService;
	private final LoanService loanService;
	
	private static final Logger LOGGER = LogManager.getLogger(LoanResource.class);

	@Inject
	public LoanResource(UserService userService, BookService bookService, LoanService loanService) {
		this.userService = userService;
		this.bookService = bookService;
		this.loanService = loanService;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<LoanResponse> findAllLoans() {
		return loanService
				.findAllLoans()
				.stream()
				.map(LoanResponse::new)
				.collect(Collectors.toUnmodifiableList());
	}

	@GET
	@Path("/{loanId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findLoanById(@PathParam("loanId") long id) {
		try {
			Loan loan = loanService.findLoanById(id).orElseThrow();
			return Response.ok(new LoanResponse(loan)).build();
		} catch (Exception e) {
			return Response.status(Status.NOT_FOUND).build();
		}		
	}
	
	@POST
	@Path("/loanbook")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response loanBook(@NotNull BookManageRequest request) {
		long userId = request.getUserId();
		long bookId = request.getBookId();
		User user;
		Book book;
		try {
			user = userService.findUserByUniqueId(userId).orElseThrow();
			book = bookService.findBookByUniqueId(bookId).orElseThrow();
		} catch (Exception e) {
			return Response.status(Status.NOT_FOUND).build();
		}

		Loan loan;
		try {		
			loan = loanService.loanBook(user, book);
		} catch (DuplicateEntityException | NoMoreBooksAvailableException e) {
			return Response
					.status(Status.BAD_REQUEST)
					.entity(e.getMessage())
					.type(MediaType.APPLICATION_JSON)
					.build();
		} 
		
		return Response.ok(new LoanResponse(loan)).build();
	}

	@POST
	@Path("/returnbook")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response returnBook(@NotNull BookManageRequest request) {
		long userId = request.getUserId();
		long bookId = request.getBookId();
		User user;
		Book book;
		try {
			user = userService.findUserByUniqueId(userId).orElseThrow();
			book = bookService.findBookByUniqueId(bookId).orElseThrow();
		} catch (Exception e) {
			return Response.status(Status.NOT_FOUND).build();
		}

		loanService.returnBook(user, book);
		return Response.noContent().build();
	}	
	
}
