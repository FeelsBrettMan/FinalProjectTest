package com.example.finalprojecttest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public User getUserById(int id) {
		
		return repo.getById(id);
	
	}
	
	// Delete user by id
	public User deleteById(int id) {

		User deleted = repo.getById(id);
		repo.deleteById(id);
		return deleted;
	}
	
	// Update user info
	public User updateUser(User user) {
		
		User updated = repo.save(user);
		return updated;
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









