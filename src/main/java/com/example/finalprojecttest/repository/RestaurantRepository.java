package com.example.finalprojecttest.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.finalprojecttest.model.Restaurant;
import com.example.finalprojecttest.model.User;

@Repository 
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer>{

	Restaurant findByName(String name);
	List<Restaurant> findByUserId(int adminId);

}
