package com.cft.rest.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.cft.rest.api.user.CreateUserRequest;
import com.cft.rest.api.user.UpdateUserRequest;
import com.cft.rest.api.user.UserResponse;
import com.cft.rest.api.user.UserUpdate;
import com.cft.rest.entities.User;
import com.cft.rest.services.LoanService;
import com.cft.rest.services.UserService;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

@RunWith(MockitoJUnitRunner.class)
public class UserResourceTest {

	private static UserService userService;
	private static LoanService loanService;
	private static UserResource userResource;
	
	@BeforeClass
	public static void init() {
    	userService = mock(UserService.class);
    	loanService = mock(LoanService.class);
    	userResource = new UserResource(userService, loanService);
	}
	
    @Mock
    ContainerRequestContext containerRequestContext;
    
    @Test
    public void createUser() {
    	String name = "John Doe";
    	String sex = "m";
    	int age = 35;
    	CreateUserRequest request = new CreateUserRequest(name, sex, age);
    	
    	when(userService.addUser(any(User.class))).thenReturn(new User(name, sex, age));
    	
    	UriInfo uriInfo = mock(UriInfo.class);
    	when(uriInfo.getAbsolutePathBuilder()).thenReturn(UriBuilder.fromUri(URI.create("http://localhost:8080/api/users")));
    	
    	Response response = userResource.createUser(request, uriInfo); 
        assertEquals(201, response.getStatus());    	
    }
    
    @Test
    public void createUserEmptyRequestBody() {
    	Assert.assertThrows(NullPointerException.class, () -> userResource.createUser(null, null));
    }
    
    @Test
    public void testFindUsers() {
    	
    	User user = new User("John Doe", "m", 35);
    	User anotherUser = new User("Jane Doe", "f", 29);
    	List<User> users = new ArrayList<>();
    	users.add(user);
    	users.add(anotherUser);
    	when(userService.findAllUsers()).thenReturn(users);
    	
    	List<UserResponse> expectedUserResponses = new ArrayList<>();
    	expectedUserResponses.add(new UserResponse(user));
    	expectedUserResponses.add(new UserResponse(anotherUser));
    	
    	List<UserResponse> userResponses = userResource.findAllUsers();
    	assertTrue(Objects.equals(userResponses, userResponses));
    }
    
    @Test
    public void testUpdateUser() {
    	
    	User user = new User("John Doe", "m", 35);
    	user.setId(1);
    	UpdateUserRequest request = new UpdateUserRequest("John Dooe", 40);
    	User updatedUser = new User("John Dooe", "m", 40);
    	updatedUser.setId(1);
    	
    	when(userService.updateUser(any(UserUpdate.class))).thenReturn(updatedUser);
    	
    	Response response = userResource.updateUser(1, request);
    	assertEquals(200, response.getStatus());
    }
    
    @Test
    public void testUpdateUserEmptyRequestBody() {
    	Assert.assertThrows(NullPointerException.class, () -> userResource.updateUser(1, null));
    }
    
    @Test
    public void testFindUserById() {
    	User user = new User("John Doe", "m", 35);
    	user.setId(1);
    	
    	when(userService.findUserByUniqueId(1)).thenReturn(Optional.of(user));
    	Response response = userResource.findUserById(1);
    	assertEquals(200, response.getStatus());    	
    }
    
    @Test
    public void testDeleteUser() {
    	User user = new User("John Doe", "m", 35);
    	user.setId(1);

    	when(userService.findUserByUniqueId(1)).thenReturn(Optional.of(user));
    	Response response = userResource.deleteUser(1);
    	assertEquals(204, response.getStatus());
    }
}