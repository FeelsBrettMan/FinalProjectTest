package com.example.finalprojecttest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalprojecttest.model.AuthenticationRequest;
import com.example.finalprojecttest.model.AuthenticationResponse;
import com.example.finalprojecttest.exception.ResourceNotFoundException;
import com.example.finalprojecttest.model.User;
import com.example.finalprojecttest.repository.UserRepository;
import com.example.finalprojecttest.service.MyUserDetailsService;
import com.example.finalprojecttest.service.UserService;
import com.example.finalprojecttest.util.JwtUtil;

import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RequestMapping("/api")
@RestController
public class UserController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private MyUserDetailsService userDetailsService;
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	UserService service;
	
	
	@ApiOperation(value = "Get all users")
	// Get all users
	@CrossOrigin
	@GetMapping("/users")
	public List<User> getAllUsers() {
		return service.getUsers();
	}
	
	// Provide details on how to document this api
	@ApiOperation(value = "Find a user by their id")		
	// Get users by id
	@CrossOrigin
	@GetMapping("/users/{id}")
	public ResponseEntity<?> getUser(@PathVariable (value = "id") int id) throws ResourceNotFoundException {
			
		return ResponseEntity.ok().body(service.getUserById(id));
			
	}
	
	@ApiOperation(value = "Find user by username")
	// find by username
	@CrossOrigin
	@GetMapping("/users/userName/{userName}")
	public User getByUsername(@PathVariable (value = "userName") String userName)
	{
		return service.getByUsername(userName);
	}
	
	@ApiOperation(value = "Delete user by id")
	// Delete user by id
	@CrossOrigin
	@DeleteMapping("/users/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable (value = "id") int id) throws ResourceNotFoundException{
			
		return new ResponseEntity<>(service.deleteById(id), HttpStatus.OK);
		
	}
	
	@ApiOperation(value = "Update user info")
	// Update user info
	@CrossOrigin
	@PutMapping("/users")
	public ResponseEntity<?> updateUsernamebyId(@Valid @RequestBody User user) throws ResourceNotFoundException{
		
		return new ResponseEntity<>(service.updateUser(user), HttpStatus.OK);		
		
	}
	
	@ApiOperation(value = "Create new user")
	// create new user
	@CrossOrigin
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user){
		return ResponseEntity.status(201).body(service.createUser(user));
	}
	
	
	
	// user can provide credentials and get back a jwt
		// that can be used to perform request for all other APIs
		@PostMapping("/authenticate")
		public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest request) throws Exception{ 
			
			// will catch the exception for bad credentials and 
			try {
			// make sure we can authenticate our user based on the username and password
			authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
					);
			}catch(BadCredentialsException e) { 
				
				//.... then provide message as to why user couldn't be authenticated
				throw new Exception("Incorrect username or password");
			}
			// as long as user is found we can create the jwt
			
			
			//find the user..
			final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
			//..generate token for this user
			final String jwt = jwtUtil.generateTokens(userDetails);
			
			//return token
			return ResponseEntity.status(200).body(new AuthenticationResponse(jwt));
			
		}
		
}












































