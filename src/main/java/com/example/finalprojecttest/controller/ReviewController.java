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
import com.example.finalprojecttest.model.Restaurant;
import com.example.finalprojecttest.model.Review;
import com.example.finalprojecttest.repository.ReviewRepository;
import com.example.finalprojecttest.repository.UserRepository;
import com.example.finalprojecttest.service.ReviewService;

@CrossOrigin
@RequestMapping("/api")
@RestController
public class ReviewController {

	@Autowired
	ReviewService service;
	@Autowired
	ReviewRepository repo;
	@Autowired
	UserRepository userRepo;
	
	// Get all Reviews
	@CrossOrigin
	@GetMapping("/reviews")
	public List<Review> getAllReviews() {
		return service.getReviews();
	}
	
	// Get Review by id
	@CrossOrigin
	@GetMapping("/reviews/{id}")
	public ResponseEntity<?> getReview(@PathVariable (value = "id") int id) throws ResourceNotFoundException {
		if(repo.existsById(id)) {
			return ResponseEntity.ok().body(service.getReviewById(id));
		}
		
		throw new ResourceNotFoundException("Review with id = " + id + " is not found");
		
	}
	

	// pull all Reviews for 1 user
	@GetMapping("/users/{userId}/reviews")
	public List<Review> getAllReviewsByUser(@PathVariable (value = "userId") int userId){
		return repo.findByUser_Id(userId);
	}
	
	// pull all reviews for 1 restaurant
	@GetMapping("/restaurants/{restId}/reviews")
	public List<Review> getAllReviewssByRestId(@PathVariable (value = "restId") int restId){
		return repo.findByRestaurant_Id(restId);
	}
	
	// Delete all reviews for 1 user
	@CrossOrigin
	@DeleteMapping("/users/{userId}/reviews")
	public ResponseEntity<?> deleteAll(@PathVariable (value = "userId") int userId) throws ResourceNotFoundException{
		if(userRepo.existsById(userId)) {
			return new ResponseEntity<>(service.deleteAll(userId), HttpStatus.OK);
		}
		
		throw new ResourceNotFoundException("review with id = " + userId + " is not found");
		
	}
	
	// Delete review by id
	@CrossOrigin
	@DeleteMapping("/users/reviews/{id}")
	public ResponseEntity<?> deleteReview(@PathVariable (value= "id") int id) throws ResourceNotFoundException{
		if(repo.existsById(id)) {
			return new ResponseEntity<>(service.deleteOne(id), HttpStatus.OK);
		}
		
		throw new ResourceNotFoundException("review with id = " + id + " is not found");
		
	}
	
	// Update review info
	@CrossOrigin
	@PutMapping("/users/reviews")
	public ResponseEntity<?> updatebyId(@Valid @RequestBody Review review) throws ResourceNotFoundException{
		Integer passedId = review.getId();
		
		if(repo.existsById(passedId)) {
			return new ResponseEntity<>(service.updateReview(review), HttpStatus.OK);		}
		
		throw new ResourceNotFoundException("review with id = " + passedId + " is not found");
	}
	
	// create new review
	@CrossOrigin
	@PostMapping("/users/{userId}/{restId}/reviews")
	public ResponseEntity<Review> createRestaurant(@PathVariable (value = "userId") int userId, @PathVariable (value = "restId") int restId, @Valid @RequestBody Review review){
		return ResponseEntity.status(201).body(service.createReview(userId, restId, review));
	}

	
}
