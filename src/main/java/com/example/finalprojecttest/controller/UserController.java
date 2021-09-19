package com.example.finalprojecttest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalprojecttest.exception.ResourceNotFoundException;
import com.example.finalprojecttest.model.User;
import com.example.finalprojecttest.repository.UserRepository;
import com.example.finalprojecttest.service.UserService;

@CrossOrigin
@RequestMapping("/api")
@RestController
public class UserController {
	
	@Autowired
	UserService service;
	
	@Autowired
	UserRepository repo;
	
	// Get all users
	@CrossOrigin
	@GetMapping("/users")
	public List<User> getAllUsers() {
		return service.getUsers();
	}
	
	// Get users by id
	@CrossOrigin
	@GetMapping("/users/{id}")
	public ResponseEntity<?> getUser(@PathVariable (value = "id") int id) throws ResourceNotFoundException {
		if(repo.existsById(id)) {
			return ResponseEntity.ok().body(service.getUserById(id));
		}
		
		throw new ResourceNotFoundException("User with id = " + id + " is not found");
		
	}
	
	// find by username
	@CrossOrigin
	@GetMapping("/users/userName/{userName}")
	public User getByUsername(@PathVariable (value = "userName") String userName)
	{
		return repo.findOneByUserName(userName);
	}
	
	// Delete user by id
	@CrossOrigin
	@DeleteMapping("/users/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable (value = "id") int id) throws ResourceNotFoundException{
		if(repo.existsById(id)) {
			return new ResponseEntity<>(service.deleteById(id), HttpStatus.OK);
		}
		
		throw new ResourceNotFoundException("User with id = " + id + " is not found");
		
	}
	
	// Update user info
	@CrossOrigin
	@PutMapping("/users")
	public ResponseEntity<?> updateUsernamebyId(@Valid @RequestBody User user) throws ResourceNotFoundException{
		Integer passedId = user.getId();
		
		if(repo.existsById(passedId)) {
			return new ResponseEntity<>(service.updateUser(user), HttpStatus.OK);		}
		
		throw new ResourceNotFoundException("User with id = " + passedId + " is not found");
	}
	
	// create new user
	@CrossOrigin
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user){
		return ResponseEntity.status(201).body(service.createUser(user));
	}
	

}












































