package com.cft.rest.resources;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cft.rest.api.loan.LoanResponse;
import com.cft.rest.api.user.CreateUserRequest;
import com.cft.rest.api.user.UpdateUserRequest;
import com.cft.rest.api.user.UserResponse;
import com.cft.rest.api.user.UserUpdate;
import com.cft.rest.entities.User;
import com.cft.rest.exceptions.DuplicateEntityException;
import com.cft.rest.exceptions.EntityNotFoundException;
import com.cft.rest.services.LoanService;
import com.cft.rest.services.UserService;

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
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

@Path("/users")
public class UserResource {

	private static final Logger LOGGER = LogManager.getLogger(UserResource.class);
	
	private final UserService userService;
	private final LoanService loanService;

	@Inject
	public UserResource(UserService userService, LoanService loanService) {
		this.userService = userService;
		this.loanService = loanService;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserResponse> findAllUsers() {
		return userService
				.findAllUsers()
				.stream()
				.map(UserResponse::new)
				.collect(Collectors.toUnmodifiableList());				
	}
	
	@GET
	@Path("/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findUserById(@PathParam("userId") long id) {
		try {
			User user = userService.findUserByUniqueId(id).orElseThrow();
			return Response.ok(new UserResponse(user)).build();
		} catch (Exception e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUser(@NotNull CreateUserRequest request, @Context UriInfo uriInfo) {
		try {
			User user = userService.addUser(new User(request.getName(), request.getSex(), request.getAge()));
			UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
			uriBuilder.path(String.valueOf(user.getId()));

			LOGGER.info("Successfully created a new user {}", user);

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
	@Path("/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUser(@PathParam("userId") long id, @NotNull UpdateUserRequest request) {
		try {
			UserUpdate userUpdate = new UserUpdate(id, request.getName(), request.getAge());
			User updateUser = userService.updateUser(userUpdate);
			return Response.ok(new UserResponse(updateUser)).build();
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
    @Path("/{userId}")
    public Response deleteUser(@PathParam("userId") long id) {
    	try {
    		User user = userService.findUserByUniqueId(id).orElseThrow();
        	userService.deleteUser(user);
        	return Response.noContent().build();
    	} catch (Exception e) {
    		return Response.status(Status.NOT_FOUND).build();
    	}
    }

	@GET
	@Path("/{userId}/loans")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findLoanedBooksByUser(@PathParam("userId") long id, @QueryParam("start") Long start, @QueryParam("end") Long end) {
		try {
			User user = userService.findUserByUniqueId(id).orElseThrow();
			return Response
						.ok(loanService
							.findLoansByUser(user, 						
								LocalDate.ofInstant(Instant.ofEpochSecond(start), ZoneId.systemDefault()),
								LocalDate.ofInstant(Instant.ofEpochSecond(end), ZoneId.systemDefault()))
							.stream()
							.map(LoanResponse::new)
							.collect(Collectors.toUnmodifiableList()))
						.build(); 
			
		} catch (Exception e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
}
