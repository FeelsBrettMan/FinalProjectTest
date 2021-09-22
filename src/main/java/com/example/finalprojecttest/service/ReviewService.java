package com.example.finalprojecttest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.finalprojecttest.exception.ResourceNotFoundException;
import com.example.finalprojecttest.model.Restaurant;
import com.example.finalprojecttest.model.Review;
import com.example.finalprojecttest.model.User;
import com.example.finalprojecttest.repository.RestaurantRepository;
import com.example.finalprojecttest.repository.ReviewRepository;
import com.example.finalprojecttest.repository.UserRepository;

@Service
public class ReviewService {

	@Autowired
	ReviewRepository revRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	RestaurantRepository restRepo;
	
	// Get all reviews
	public List<Review> getReviews(){
		return revRepo.findAll();
	}
	
	// Get Review by id
	public Review getReviewById(int id) throws ResourceNotFoundException{
		
		Optional<Review> found = revRepo.findById(id);
		
		if(!found.isPresent()) {
			throw new ResourceNotFoundException("Review with id = " + id + " could not be found.");
		}
		
		return found.get();
	
	}		
	
	// Pull all reviews by user id
	public List<Review> getReviewsByUser(int userId) {
		return revRepo.findByUser_Id(userId);
	}
	
	// Pull all reviews by restaurant id
	public List<Review> getReviewsByRestaurant(int restId){
		return revRepo.findByRestaurant_Id(restId);
	}
	
	// Delete all Reviews for 1 user
	public List<Review> deleteAll(int userId) {

		List<Review> toRemoved = revRepo.findByUser_Id(userId);
		for(Review review : toRemoved) {
			revRepo.delete(review);
		}
		return toRemoved;
	}
	
	// Delete 1 Review for 1 user
	public Review deleteOne(int revId) throws ResourceNotFoundException {

		if(revRepo.existsById(revId)) {
			Review toRemoved = revRepo.getById(revId);
			revRepo.deleteById(revId);
			return toRemoved;
		}
		throw new ResourceNotFoundException("Review with id = " + revId + " could not be found."); 
	}
	
	// Update Review info
	public Review updateReview(Review review)  throws ResourceNotFoundException {
		Integer passedId = review.getId();
		
		if(revRepo.existsById(passedId)) {
			Review toUpdate = revRepo.getById(review.getId());
			System.out.println("toUpdate = " + toUpdate);
			toUpdate.setDescription(review.getDescription());
			toUpdate.setRating(review.getRating());
			Review updated = revRepo.save(toUpdate);
			return updated;
		}
		throw new ResourceNotFoundException("review with id = " + passedId + " is not found");
		
	
	}
	
	// Create a new Review for a restaurant with a specific user 
	public Review createReview(int userId, int restId, Review review) {
		review.setId(-1);
		Optional<User> user = userRepo.findById(userId);
		Optional<Restaurant> restaurant = restRepo.findById(restId);
		review.setUser(user.get());
		review.setRestaurant(restaurant.get());
		Review created = revRepo.save(review);
		return created;
	}
	
}
