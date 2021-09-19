package com.example.finalprojecttest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.finalprojecttest.model.Review;
import com.example.finalprojecttest.model.User;
import com.example.finalprojecttest.repository.ReviewRepository;
import com.example.finalprojecttest.repository.UserRepository;

@Service
public class ReviewService {

	@Autowired
	ReviewRepository repo;
	
	@Autowired
	UserRepository userRepo;
	// Get all reviews
	public List<Review> getReviews(){
		return repo.findAll();
	}
	
	// Get Review by id
	public Review getReviewById(int id) {
		
		return repo.getById(id);
	
	}		
	
	
	// Delete all Reviews for 1 user
	public List<Review> deleteAll(int userId) {

		List<Review> toRemoved = repo.findByUser_Id(userId);
		for(Review review : toRemoved) {
			repo.delete(review);
		}
		return toRemoved;
	}
	
	// Delete 1 Review for 1 user
	public Review deleteOne(int revId) {

		Review toRemoved = repo.getById(revId);
		repo.deleteById(revId);
		return toRemoved;
	}
	
	// Update Review info
	public Review updateReview(Review review) {
		
		Review updated = repo.save(review);
		return updated;
	}
	
	// Create a new Review for a user
	public Review createReview(int userId, Review review) {
		review.setId(-1);
		Optional<User> user = userRepo.findById(userId);
		review.setUser(user.get());
		Review created = repo.save(review);
		return created;
	}
	
}
