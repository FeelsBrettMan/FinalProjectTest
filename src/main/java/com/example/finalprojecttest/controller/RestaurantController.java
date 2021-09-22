package com.example.finalprojecttest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import com.example.finalprojecttest.repository.RestaurantRepository;
import com.example.finalprojecttest.repository.UserRepository;
import com.example.finalprojecttest.service.RestaurantService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RequestMapping("/api")
@RestController
public class RestaurantController {

	@Autowired
	RestaurantService service;
	
	@Autowired
	UserRepository userRepo;
	
	@ApiOperation(value = "Get all Restaurants")
	// Get all Restaurants
	@CrossOrigin
	@GetMapping("/restaurants")
	public List<Restaurant> getAllRestaurants() {
		return service.getRestaurants();
	}
	
	// Provide details on how to document this api
	@ApiOperation(value = "Find a restaurant by its id")		
	// Get Restaurants by id
	@CrossOrigin
	@GetMapping("/restaurants/{id}")
	public ResponseEntity<?> getRestaurant(@PathVariable (value = "id") int id) throws ResourceNotFoundException {
		
		return ResponseEntity.ok().body(service.getRestaurantById(id));
		
	
		
	}
	
	@ApiOperation(value = "Find restaurant by name")
	// find by name
	@CrossOrigin
	@GetMapping("/restaurants/name/{name}")
	public Restaurant getByName(@PathVariable (value = "name") String name)
	{
		return service.getByName(name);
	}
	
	@ApiOperation(value = "Pull all restaurants pages for 1 admin")
	// pull all restaurants pages for 1 admin
	@GetMapping("/admin/{adminId}/restaurants")
	public List<Restaurant> getAllRestaurantsByAdminId(@PathVariable (value = "adminId") int adminId){
		return service.getAllRestaurantsByAdminId(adminId);
	}
	
	@ApiOperation(value = "Delete all restaurants for 1 admin: Ony admin can do")
	// Delete all restaurants for 1 admin: Ony admin can do
	@CrossOrigin
	@DeleteMapping("/admin/{adminId}/restaurants")
	public ResponseEntity<?> deleteAll(@PathVariable (value = "adminId") int adminId) throws ResourceNotFoundException{
		if(userRepo.existsById(adminId)) {
			return new ResponseEntity<>(service.deleteAll(adminId), HttpStatus.OK);
		}
		
		throw new ResourceNotFoundException("restaurant with id = " + adminId + " is not found");
		
	}
	
	@ApiOperation(value = "Delete restaurant by id: Ony admin can do")
	// Delete restaurant by id: Ony admin can do
	@CrossOrigin
	@DeleteMapping("/admin/restaurants/{id}")
	public ResponseEntity<?> deleteRestaurant(@PathVariable (value = "id") int id) throws ResourceNotFoundException{
			
		return new ResponseEntity<>(service.deleteOne(id), HttpStatus.OK);
		
	}
	
	@ApiOperation(value = "Update restaurant info: Ony admin can do")
	// Update restaurant info: Ony admin can do
	@CrossOrigin
	@PutMapping("/admin/restaurants")
	public ResponseEntity<?> updatebyId(@Valid @RequestBody Restaurant restaurant) throws ResourceNotFoundException{
		
		return new ResponseEntity<>(service.updateRestaurant(restaurant), HttpStatus.OK);		
	
	}
	
	@ApiOperation(value = "Create new restaurant: Ony admin can do")
	// create new restaurant: Ony admin can do
	@CrossOrigin
	@PostMapping("/admin/{adminId}/restaurants")
	public ResponseEntity<Restaurant> createRestaurant(@PathVariable(value = "adminId") int adminId, @Valid @RequestBody Restaurant restaurant){
		return ResponseEntity.status(201).body(service.createRestaurant(adminId, restaurant));
	}

}
