package com.example.finalprojecttest.controller;

import static org.mockito.Mockito.when;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.finalprojecttest.model.Restaurant;
import com.example.finalprojecttest.model.Review;
import com.example.finalprojecttest.model.User;
import com.example.finalprojecttest.model.User.Role;
import com.example.finalprojecttest.repository.RestaurantRepository;
import com.example.finalprojecttest.repository.UserRepository;
import com.example.finalprojecttest.service.RestaurantService;
import com.example.finalprojecttest.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;


@ExtendWith(SpringExtension.class)
@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

	private final String STARTING_URI = "http://localhost:8080/api";
	@Autowired
	 private MockMvc mockMvc;
	 @MockBean
	 UserRepository userRepo;
	 
	 @MockBean
	 RestaurantRepository restRepo;
	 @MockBean
	 ReviewService service;
	 
	 @InjectMocks
	 private ReviewController reviewController;
	
	@Test
	void testGetAllReviews() throws Exception {
		
		
		List<Review> reviews1 = Arrays.asList(
			new Review(1, "Okay to me", 3.7),
			new Review(2, "Good", 4.3),
			new Review(3, "Fine", 3.5)
		);
		
		List<Restaurant> restaurants = Arrays.asList(
				new Restaurant(1, "Bamboo", "1st Main St", "Sell Asian food", reviews1),
				new Restaurant(2, "Culvers", "1st North St", "Sell BBQ food", reviews1)
		);
		Restaurant restaurant = new Restaurant(1, "Bamboo", "1st Main St", "Sell Asian food", reviews1);
		User user = new User(1, "Max", "pass1", User.Role.ROLE_ADMIN, reviews1, restaurants);
		reviews1.get(0).setRestaurant(restaurant);
		reviews1.get(1).setRestaurant(restaurant);
		reviews1.get(2).setRestaurant(restaurant);
		reviews1.get(0).setUser(user);
		reviews1.get(2).setUser(user);
		reviews1.get(1).setUser(user);
		String uri = STARTING_URI + "/reviews";
		//Mockito.lenient().when(repo.findAll()).thenReturn(reviews1);
		when(service.getReviews()).thenReturn(reviews1);
		mockMvc.perform(get(uri))
        .andDo(print())
        .andExpect( status().isOk() )
        .andExpect( content().contentType(MediaType.APPLICATION_JSON_VALUE) )
        .andExpect( jsonPath("$[0].id").value(reviews1.get(0).getId()) ) // check each column value for the cars in list
		.andExpect( jsonPath("$[0].description").value(reviews1.get(0).getDescription()) )
		.andExpect( jsonPath("$[0].rating").value(reviews1.get(0).getRating()) )
		.andExpect( jsonPath("$[1].id").value(reviews1.get(1).getId()) )
		.andExpect( jsonPath("$[1].description").value(reviews1.get(1).getDescription()) )
		.andExpect( jsonPath("$[1].rating").value(reviews1.get(1).getRating()) );

			
	}
	
	@Test
	void testGetReviewById() throws Exception {
		
		int id = 1;
		String uri = STARTING_URI + "/reviews/{id}";
		List<Review> reviews1 = Arrays.asList(
				new Review(1, "Okay to me", 3.7),
				new Review(2, "Good", 4.3),
				new Review(3, "Fine", 3.5)
			);
			
			List<Restaurant> restaurants = Arrays.asList(
					new Restaurant(1, "Bamboo", "1st Main St", "Sell Asian food", reviews1),
					new Restaurant(2, "Culvers", "1st North St", "Sell BBQ food", reviews1)
			);
		Restaurant restaurant = new Restaurant(1, "Bamboo", "1st Main St", "Sell Asian food", reviews1);
		User user = new User(1, "Max", "pass1", User.Role.ROLE_ADMIN, reviews1, restaurants);
		
		Review review = new Review(id, "Okay to me", 3.7);

		review.setRestaurant(restaurant);
		review.setUser(user);
		
		// car that will be returned from this request
		
		// when service is called, return this review
		when( service.getReviewById(id) ).thenReturn(review);
		
		// perform the get request, and pass the id to the uri
		mockMvc.perform( get(uri, id) )
				.andDo(print())
				.andExpect( status().isOk() )
				.andExpect( content().contentType(MediaType.APPLICATION_JSON_VALUE) ) // check if data returned is json
				.andExpect( jsonPath("$.id").value(review.getId() ) )
				.andExpect( jsonPath("$.description").value(review.getDescription()) )
				.andExpect( jsonPath("$.rating").value(review.getRating()) );
		
			
	}
	
	@Test
	@DisplayName("testGetReviewNotFound passed")
	void testGetReviewNotFound() throws Exception{
		int id = 1;
		String uri = STARTING_URI + "/reviews/{id}";
		
		when( service.getReviewById(id) )
			.thenThrow(new com.example.finalprojecttest.exception.ResourceNotFoundException("Review with id = " + id + " could not be found"));
		
		mockMvc.perform( get(uri, id) )
			
			.andExpect( status().isNotFound() ); // is the status code 404

	}
	
	@Test
	void testCreateReview() throws Exception {
		
		int userId = 1;
		int restId = 1;
		String uri = STARTING_URI + "/users/{userId}/{restId}/reviews";
		List<Review> reviews1 = Arrays.asList(
				new Review(1, "Okay to me", 3.7),
				new Review(2, "Good", 4.3),
				new Review(3, "Fine", 3.5)
			);
			
		List<Restaurant> restaurants = Arrays.asList(
				new Restaurant(1, "Bamboo", "1st Main St", "Sell Asian food", reviews1),
				new Restaurant(2, "Culvers", "1st North St", "Sell BBQ food", reviews1)
		);
			Restaurant restaurant = new Restaurant(restId, "Bamboo", "1st Main St", "Sell Asian food", reviews1);
			User user = new User(userId, "Max", "pass1", User.Role.ROLE_ADMIN, reviews1, restaurants);
			
			Review review = new Review(1, "Fantastic", 4.7);
			
			review.setUser(user);
			review.setRestaurant(restaurant);

		
		when(service.createReview(Mockito.eq(1), Mockito.eq(1), Mockito.any(Review.class) )).thenReturn(review);
//		String requestBody = new ObjectMapper().valueToTree(review).toString();
		mockMvc.perform( post(uri, userId, restId) 
					     .content("{\"id\":1, \"description\":\"Fantastic\", \"rating\":4.7}") 
					     .contentType(MediaType.APPLICATION_JSON_VALUE) )
				.andDo(print())
				.andExpect( status().isCreated() ) // is created, 201 
				.andExpect( content().contentType(MediaType.APPLICATION_JSON_VALUE ) ); // data returned in format expected
		
	
			
	}
	
	@Test
	void updateReview() throws Exception{
		String uri = STARTING_URI + "/users/reviews";
		int id = 1;
		
		List<Review> reviews1 = Arrays.asList(
				new Review(1, "Okay to me", 3.7),
				new Review(2, "Good", 4.3),
				new Review(3, "Fine", 3.5)
			);
			
		List<Restaurant> restaurants = Arrays.asList(
				new Restaurant(1, "Bamboo", "1st Main St", "Sell Asian food", reviews1),
				new Restaurant(2, "Culvers", "1st North St", "Sell BBQ food", reviews1)
		);
		Restaurant restaurant = new Restaurant(1, "Bamboo", "1st Main St", "Sell Asian food", reviews1);
		User user = new User(1, "Max", "pass1", User.Role.ROLE_ADMIN, reviews1, restaurants);
		
		Review updated = new Review(1, "Amazing", 4.9);
		
		updated.setUser(user);
		updated.setRestaurant(restaurant);

		when(service.updateReview( Mockito.any(Review.class))).thenReturn(updated);
		
		mockMvc.perform( put(uri)
						 .contentType(MediaType.APPLICATION_JSON_VALUE)
						 .content("{\"id\":1, \"description\":\"Amazing\", \"rating\":4.9}") )
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect( content().contentType(MediaType.APPLICATION_JSON_VALUE ) );
	
	}
	
	@Test 
	void deleteReviewById() throws Exception{
		int id = 1;
		String uri = STARTING_URI + "/users/reviews/{id}";
		List<Review> reviews1 = Arrays.asList(
				new Review(1, "Okay to me", 3.7),
				new Review(2, "Good", 4.3),
				new Review(3, "Fine", 3.5)
			);
			
		List<Restaurant> restaurants = Arrays.asList(
				new Restaurant(1, "Bamboo", "1st Main St", "Sell Asian food", reviews1),
				new Restaurant(2, "Culvers", "1st North St", "Sell BBQ food", reviews1)
		);
		Restaurant restaurant = new Restaurant(1, "Bamboo", "1st Main St", "Sell Asian food", reviews1);
		User user = new User(1, "Max", "pass1", User.Role.ROLE_ADMIN, reviews1, restaurants);
		
		Review removed = new Review(1, "Cool place", 4.5);
		
		removed.setUser(user);
		removed.setRestaurant(restaurant);
		
		when(service.deleteOne(id)).thenReturn(removed);
		mockMvc.perform( delete(uri, id)
				 .contentType(MediaType.APPLICATION_JSON_VALUE)
				 .content("{\"id\":1, \"description\":\"Cool place\", \"rating\":4.5}" ) )
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
