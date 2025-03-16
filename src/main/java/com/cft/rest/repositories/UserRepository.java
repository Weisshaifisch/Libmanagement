package com.cft.rest.repositories;

import java.util.List;
import java.util.Optional;

import com.cft.rest.entities.User;

public interface UserRepository {

	List<User> findAllUsers();
	
	Optional<User> findUserByUniqueId(long id);
	
	User addUser(User user);
	
	User updateUser(User user);
	
	void deleteUser(User user);	
	
	Optional<User> findUserByName(String name);
	
}
