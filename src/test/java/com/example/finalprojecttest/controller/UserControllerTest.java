package com.example.finalprojecttest.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.example.finalprojecttest.model.Restaurant;
import com.example.finalprojecttest.model.Review;
import com.example.finalprojecttest.model.User;
import com.example.finalprojecttest.model.User.Role;
import com.example.finalprojecttest.repository.RestaurantRepository;
import com.example.finalprojecttest.repository.ReviewRepository;
import com.example.finalprojecttest.repository.UserRepository;
import com.example.finalprojecttest.service.RestaurantService;
import com.example.finalprojecttest.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

	private final String STARTING_URI = "http://localhost:8080/api";
	@Autowired
	 private MockMvc mockMvc;
	 @MockBean
	 UserRepository userRepo;
	
	 @MockBean
	 RestaurantRepository restRepo;
	 
	 @MockBean
	ReviewRepository revRepo;
	
	 @MockBean
	 UserService service;
	 
	 @InjectMocks
	 private UserController userController;
	
	 
	 public enum Role {
			ROLE_USER, ROLE_ADMIN
	}
	 
	 List<Review> reviews1 = Arrays.asList(
				new Review(1, "Okay to me", 3.7),
				new Review(2, "Good", 4.3),
				new Review(3, "Fine", 3.5)
			);
			
	List<Review> reviews2 = Arrays.asList(
				new Review(1, "Great to me", 3.7),
				new Review(2, "OK", 4.3),
				new Review(3, "Amazing", 3.5)
	);
	
	List<Restaurant> restaurants = Arrays.asList(
			new Restaurant(1, "Macdolnad", "1st Main St", "Sell fastfood", reviews1),
			new Restaurant(2, "Wedny", "1nd Main St", "Sell fastfood 2", reviews2)
	);
	@Test
	void testGetAllRestaurants() throws Exception {
		
		
		List<User> users = Arrays.asList(
			new User(1, "Max", "pass1", User.Role.ROLE_ADMIN, reviews1, restaurants),
			new User(1, "Cindy", "pass2", User.Role.ROLE_ADMIN, reviews2, restaurants)
		);
		String uri = STARTING_URI + "/users";
		when(service.getUsers()).thenReturn(users);
		mockMvc.perform(get(uri))
        .andDo(print())
        .andExpect( status().isOk() )
        .andExpect( content().contentType(MediaType.APPLICATION_JSON_VALUE) )
        .andExpect( jsonPath("$[0].id").value(users.get(0).getId()) ) 
		.andExpect( jsonPath("$[0].userName").value(users.get(0).getUserName()))
		.andExpect( jsonPath("$[0].password").value(users.get(0).getPassword()) );

			
	}
	
	@Test
	void testGetRestaurantById() throws Exception {
		
		int id = 1;
		String uri = STARTING_URI + "/users/{id}";
		
		User user = new User(1, "Max", "pass1", User.Role.ROLE_ADMIN, reviews1, restaurants);
		
		// when service is called, return this review
		when( service.getUserById(id) ).thenReturn(user);
		
		// perform the get request, and pass the id to the uri
		mockMvc.perform( get(uri, id) )
				.andDo(print())
				.andExpect( status().isOk() )
				.andExpect( content().contentType(MediaType.APPLICATION_JSON_VALUE) ) // check if data returned is json
				.andExpect( jsonPath("$.id").value(user.getId() ) )
				.andExpect( jsonPath("$.userName").value(user.getUserName()) )
				.andExpect( jsonPath("$.password").value(user.getPassword()) );
		
	}
	
	@Test
	@DisplayName("testGetUserNotFound passed")
	void testGetUserNotFound() throws Exception{
		int id = 1;
		String uri = STARTING_URI + "/users/{id}";
		
		when( service.getUserById(id)) 
			.thenThrow(new com.example.finalprojecttest.exception.ResourceNotFoundException("User with id = " + id + " could not be found"));
		
		mockMvc.perform( get(uri, id) )
			
			.andExpect( status().isNotFound() ); // is the status code 404

	}
	
	@Test
	void testCreateUser() throws Exception {
		
		int adminId = 1;
		String uri = STARTING_URI + "/users";
		
		User user = new User(1, "Cindy", "pass2", User.Role.ROLE_ADMIN, reviews1, restaurants);
		
		when(service.createUser(Mockito.any(User.class) )).thenReturn(user);
		
		mockMvc.perform( post(uri) 
					     .content(asJsonString(user) ) 
					     .contentType(MediaType.APPLICATION_JSON_VALUE) )
				.andDo(print())
				.andExpect( status().isCreated() ) // is created, 201 
				.andExpect( content().contentType(MediaType.APPLICATION_JSON_VALUE ) ); // data returned in format expected
		
	
			
	}
	
	@Test
	void testUpdateUser() throws Exception{
		String uri = STARTING_URI + "/users";
		int id = 1;
		
		User user = new User(1, "Beth", "pass3", User.Role.ROLE_ADMIN, reviews1, restaurants);
		
		when(service.updateUser( Mockito.any(User.class))).thenReturn(user);
		
		mockMvc.perform( put(uri)
						 .contentType(MediaType.APPLICATION_JSON_VALUE)
						 .content( asJsonString(user) ) )
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect( content().contentType(MediaType.APPLICATION_JSON_VALUE ) );
	
	}
	
	@Test 
	void testDeleteUserById() throws Exception{
		int id = 1;
		String uri = STARTING_URI + "/users/{id}";
		User removed = new User(1, "Adele", "pass3", User.Role.ROLE_ADMIN, reviews1, restaurants);
		when(service.deleteById(id)).thenReturn(removed);
		mockMvc.perform( delete(uri, id)
				 .contentType(MediaType.APPLICATION_JSON_VALUE)
				 .content( asJsonString(removed) ) )
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect( content().contentType(MediaType.APPLICATION_JSON_VALUE ) );

		
	}
	
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
//	
}
