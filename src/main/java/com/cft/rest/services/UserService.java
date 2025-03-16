package com.cft.rest.services;

import java.util.List;
import java.util.Optional;

import com.cft.rest.api.user.UserUpdate;
import com.cft.rest.entities.User;
import com.cft.rest.exceptions.DuplicateEntityException;
import com.cft.rest.exceptions.EntityNotFoundException;
import com.cft.rest.repositories.UserRepository;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class UserService {

    private final UserRepository userRepository;

    @Inject
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public List<User> findAllUsers() {
        return userRepository.findAllUsers();
    }
    
    public Optional<User> findUserByUniqueId(long id) {
        return userRepository.findUserByUniqueId(id);
    }
    
    public User addUser(User user) {
		String name = user.getName();
		User existingUser = findUserByName(name).orElse(null);
		if (existingUser != null) {
			throw new DuplicateEntityException("The user with the name '" + name + "' already exists");
		}
    	
        return userRepository.addUser(user);
    }
    
    public User updateUser(UserUpdate userUpdate) {
		String newName = userUpdate.getName();
		long userId = userUpdate.getId();
		if (newName != null ) {
			User user = findUserByName(newName).orElse(null);
			if (user != null && userId != user.getId()) {
				throw new DuplicateEntityException("The user with the name '" + newName + "' already exists");
			}			
		}
    	
    	User oldUser = userRepository.findUserByUniqueId(userId).orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
    	
    	User newUser = new User();
    	newUser.setId(userId);
    	newUser.setSex(oldUser.getSex());    	
    	newUser.setName(newName == null ? oldUser.getName() : newName);
    	
    	Integer newAge = userUpdate.getAge();
    	newUser.setAge(newAge == null ? oldUser.getAge() : newAge);
    	
        return userRepository.updateUser(newUser); 
    }
    
    public void deleteUser(User user) {    	
        userRepository.deleteUser(user);
    }    
    
    public Optional<User> findUserByName(String name) {
    	return userRepository.findUserByName(name);
    }
}
