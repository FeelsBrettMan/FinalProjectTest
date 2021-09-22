package com.example.finalprojecttest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.finalprojecttest.exception.ResourceNotFoundException;
import com.example.finalprojecttest.model.User;
import com.example.finalprojecttest.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository repo;
	
	// Get all users
	public List<User> getUsers(){
		return repo.findAll();
	}
	
	// Get users by id
	public User getUserById(int id) throws ResourceNotFoundException {
		if(repo.existsById(id)) {
			return repo.getById(id);

		}
		
		throw new ResourceNotFoundException("User with id = " + id + " is not found");
	
	}
	
	// get by username
	public User getByUsername(String userName)
	{
		return repo.findOneByUserName(userName);
	}
	
	// Delete user by id
	public User deleteById(int id) throws ResourceNotFoundException {

		if(repo.existsById(id)) {
			User deleted = repo.getById(id);
			repo.deleteById(id);
			return deleted;
		}
		throw new ResourceNotFoundException("User with id = " + id + " is not found");
		
	}
	
	// Update user info
	public User updateUser(User user) throws ResourceNotFoundException{
		
		Integer passedId = user.getId();
		
		if(repo.existsById(passedId)) {
			User updated = repo.save(user);
			return updated;	
		}
		
		throw new ResourceNotFoundException("User with id = " + passedId + " is not found");
	
		
	}
	
	// Add new user 
	public User createUser(User user) {
		user.setId(-1);
		
		// each review and restaurant (for Admin case) created for a user has a -1 id at first
		user.setNewReviews();
		user.setNewRestaurants();
		User created = repo.save(user);
		return created;
	}
	
	// Delete user
	public User deleteUserbyId(int id) {
		User removed = repo.getById(id);
		repo.deleteById(id);
		return removed;
	}
	
	
}









