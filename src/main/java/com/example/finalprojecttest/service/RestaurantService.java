package com.example.finalprojecttest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.finalprojecttest.exception.ResourceNotFoundException;
import com.example.finalprojecttest.model.Restaurant;
import com.example.finalprojecttest.model.User;
import com.example.finalprojecttest.repository.RestaurantRepository;
import com.example.finalprojecttest.repository.UserRepository;

@Service
public class RestaurantService {
	
	@Autowired
	RestaurantRepository repo;

	@Autowired
	UserRepository userRepo;
	// Get all restaurants
	public List<Restaurant> getRestaurants(){
		return repo.findAll();
	}
	
	// Get restaurants by id
	public Restaurant getRestaurantById(int id) throws ResourceNotFoundException {
		if(repo.existsById(id)) {
			return repo.getById(id);
		}
		
		throw new ResourceNotFoundException("Restaurant with id = " + id + " is not found");
	}		
	
	// Find by name
	public Restaurant getByName(String name)
	{
		return repo.findByName(name);
	}
	
	// Find restaurant by admin Id
	public List<Restaurant> getAllRestaurantsByAdminId(int adminId){
		return repo.findByUser_Id(adminId);
	}
	
	// Delete all restaurants pages for 1 admin
	public List<Restaurant> deleteAll(int adminId) {

		List<Restaurant> toRemoved = repo.findByUser_Id(adminId);
		for(Restaurant restaurant : toRemoved) {
			repo.delete(restaurant);
		}
		return toRemoved;
	}
	
	// Delete 1 restaurant page for 1 admin
	public Restaurant deleteOne(int restId) throws ResourceNotFoundException{
		if(repo.existsById(restId)) {
			Restaurant toRemoved = repo.getById(restId);
			repo.deleteById(restId);
			return toRemoved;
		}
		
		throw new ResourceNotFoundException("restaurant with id = " + restId + " is not found");
		
	}
	
	// Update Restaurant info
	public Restaurant updateRestaurant(Restaurant restaurant) {
		Restaurant toUpdate = repo.getById(restaurant.getId());
		toUpdate.setName(restaurant.getName());
		toUpdate.setAddress(restaurant.getAddress());
		toUpdate.setDescription(restaurant.getDescription());
		Restaurant updated = repo.save(toUpdate);
		return updated;
	}
	
	// Create a new restaurant page for an admin
	public Restaurant createRestaurant(int adminId, Restaurant restaurant) {
		restaurant.setId(-1);
		Optional<User> user = userRepo.findById(adminId);
		restaurant.setUser(user.get());
		Restaurant created = repo.save(restaurant);
		return created;
	}
		
		

}
