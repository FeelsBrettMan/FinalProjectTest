package com.example.finalprojecttest.controller;

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
import com.example.finalprojecttest.repository.RestaurantRepository;
import com.example.finalprojecttest.repository.ReviewRepository;
import com.example.finalprojecttest.repository.UserRepository;
import com.example.finalprojecttest.service.RestaurantService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

	private final String STARTING_URI = "http://localhost:8080/api";
	@Autowired
	 private MockMvc mockMvc;
	 @MockBean
	 UserRepository userRepo;
	 @MockBean
	 RestaurantRepository repo;
	 @MockBean
	 ReviewRepository revRepo;
	 @MockBean
	 RestaurantService service;
	 
	 @InjectMocks
	 private RestaurantController rstaurantController;
	
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
	@Test
	void testGetAllRestaurants() throws Exception {
		
		
		List<Restaurant> restaurants = Arrays.asList(
			new Restaurant(1, "Macdolnad", "1st Main St", "Sell fastfood", reviews1),
			new Restaurant(2, "Wedny", "1nd Main St", "Sell fastfood 2", reviews2)
		);
		String uri = STARTING_URI + "/restaurants";
		when(service.getRestaurants()).thenReturn(restaurants);
		mockMvc.perform(get(uri))
        .andDo(print())
        .andExpect( status().isOk() )
        .andExpect( content().contentType(MediaType.APPLICATION_JSON_VALUE) )
        .andExpect( jsonPath("$[0].id").value(restaurants.get(0).getId()) ) 
		.andExpect( jsonPath("$[0].name").value(restaurants.get(0).getName() ))
		.andExpect( jsonPath("$[0].address").value(restaurants.get(0).getAddress()) )
		.andExpect( jsonPath("$[0].description").value(restaurants.get(0).getDescription()) );

			
	}
	
	@Test
	void testGetRestaurantById() throws Exception {
		
		int id = 1;
		String uri = STARTING_URI + "/restaurants/{id}";
		
		Restaurant restaurant = new Restaurant(1, "Chickfilla", "1st West St", "Sell chicken", reviews1);
		
		// when service is called, return this review
		when( service.getRestaurantById(id) ).thenReturn(restaurant);
		
		// perform the get request, and pass the id to the uri
		mockMvc.perform( get(uri, id) )
				.andDo(print())
				.andExpect( status().isOk() )
				.andExpect( content().contentType(MediaType.APPLICATION_JSON_VALUE) ) // check if data returned is json
				.andExpect( jsonPath("$.id").value(restaurant.getId() ) )
				.andExpect( jsonPath("$.name").value(restaurant.getName()) )
				.andExpect( jsonPath("$.address").value(restaurant.getAddress()) )
				.andExpect( jsonPath("$.description").value(restaurant.getDescription()) );
		
			
	}
	
	@Test
	@DisplayName("testGetRestaurantNotFound passed")
	void testGetRestaurantNotFound() throws Exception{
		int id = 1;
		String uri = STARTING_URI + "/restaurants/{id}";
		
		when( service.getRestaurantById(id) )
			.thenThrow(new com.example.finalprojecttest.exception.ResourceNotFoundException("Restaurant with id = " + id + " could not be found"));
		
		mockMvc.perform( get(uri, id) )
			
			.andExpect( status().isNotFound() ); // is the status code 404

	}
	
	@Test
	void testCreateRestaurant() throws Exception {
		
		int adminId = 1;
		String uri = STARTING_URI + "/admin/{adminId}/restaurants";
		
		Restaurant restaurant = new Restaurant(1, "Bammboo", "1st North St", "Sell asian food", reviews2);
		
		when(service.createRestaurant(Mockito.eq(adminId), Mockito.any(Restaurant.class) )).thenReturn(restaurant);
		
		mockMvc.perform( post(uri, adminId) 
					     .content(asJsonString(restaurant) ) 
					     .contentType(MediaType.APPLICATION_JSON_VALUE) )
				.andDo(print())
				.andExpect( status().isCreated() ) // is created, 201 
				.andExpect( content().contentType(MediaType.APPLICATION_JSON_VALUE ) ); // data returned in format expected
		
	
			
	}
	
	@Test
	void testUpdateRestaurant() throws Exception{
		String uri = STARTING_URI + "/admin/restaurants";
		int id = 1;
		
		Restaurant restaurant = new Restaurant(1, "Culver's", "1st South St", "Sell BBQ", reviews1);
		
		when(service.updateRestaurant( Mockito.any(Restaurant.class))).thenReturn(restaurant);
		
		mockMvc.perform( put(uri)
						 .contentType(MediaType.APPLICATION_JSON_VALUE)
						 .content( asJsonString(restaurant) ) )
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect( content().contentType(MediaType.APPLICATION_JSON_VALUE ) );
	
	}
	
	@Test 
	void testDeleteReviewById() throws Exception{
		int id = 1;
		String uri = STARTING_URI + "/admin/restaurants/{id}";
		Restaurant removed = new Restaurant(1, "KBOB", "1st East St", "Sell Korean food", reviews2);
		when(service.deleteOne(id)).thenReturn(removed);
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
	
}
