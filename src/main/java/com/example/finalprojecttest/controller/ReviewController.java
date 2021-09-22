package com.example.finalprojecttest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.finalprojecttest.exception.ResourceNotFoundException;
import com.example.finalprojecttest.model.Restaurant;
import com.example.finalprojecttest.model.Review;
import com.example.finalprojecttest.repository.ReviewRepository;
import com.example.finalprojecttest.repository.UserRepository;
import com.example.finalprojecttest.service.ReviewService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RequestMapping("/api")
@RestController
public class ReviewController {

	@Autowired
	ReviewService service;
	
	@Autowired
	UserRepository userRepo;
	
	@ApiOperation(value = "Find all reviews",
			notes = "Will return a 404 if id isn't found")
	// Get all Reviews
	@CrossOrigin
	@GetMapping("/reviews")
	public List<Review> getAllReviews() {
		return service.getReviews();
	}
	
	// Provide details on how to document this api
	@ApiOperation(value = "Find a review by its id")	
	// Get Review by id
	@CrossOrigin
	@GetMapping("/reviews/{id}")
	public ResponseEntity<?> getReview(@PathVariable (value = "id") int id) throws ResourceNotFoundException {
		
		return ResponseEntity.ok().body(service.getReviewById(id));
		
		
	}
	
	@ApiOperation(value = "Pull all Reviews for 1 user")
	// pull all Reviews for 1 user
	@GetMapping("/users/{userId}/reviews")
	public List<Review> getAllReviewsByUser(@PathVariable (value = "userId") int userId){
		return service.getReviewsByUser(userId);
	}
	
	@ApiOperation(value = "Pull all reviews for 1 restaurant")
	// pull all reviews for 1 restaurant
	@GetMapping("/restaurants/{restId}/reviews")
	public List<Review> getAllReviewssByRestId(@PathVariable (value = "restId") int restId){
		return service.getReviewsByRestaurant(restId);
	}
	
	@ApiOperation(value = "Delete all reviews for 1 user")
	// Delete all reviews for 1 user
	@CrossOrigin
	@DeleteMapping("/users/{userId}/reviews")
	public ResponseEntity<?> deleteAll(@PathVariable (value = "userId") int userId) throws ResourceNotFoundException{
		if(userRepo.existsById(userId)) {
			return new ResponseEntity<>(service.deleteAll(userId), HttpStatus.OK);
		}
		
		throw new ResourceNotFoundException("review with id = " + userId + " is not found");
		
	}
	
	@ApiOperation(value = "Delete review by id")
	// Delete review by id
	@CrossOrigin
	@DeleteMapping("/users/reviews/{id}")
	public ResponseEntity<?> deleteReview(@PathVariable (value= "id") int id) throws ResourceNotFoundException{
		
		return new ResponseEntity<>(service.deleteOne(id), HttpStatus.OK);
	
		
	}
	
	@ApiOperation(value = "Update review info")
	// Update review info
	@CrossOrigin
	@PutMapping("/users/reviews")
	public ResponseEntity<?> updatebyId(@Valid @RequestBody Review review) throws ResourceNotFoundException{
		
		return new ResponseEntity<>(service.updateReview(review), HttpStatus.OK);		
		
	}
	
	@ApiOperation(value = "Create new review")
	// create new review
	@CrossOrigin
	@PostMapping("/users/{userId}/{restId}/reviews")
	public ResponseEntity<Review> createReview(@PathVariable (value = "userId") int userId, @PathVariable (value = "restId") int restId, @Valid @RequestBody Review review){
		return ResponseEntity.status(201).body(service.createReview(userId, restId, review));
	}

	
}
