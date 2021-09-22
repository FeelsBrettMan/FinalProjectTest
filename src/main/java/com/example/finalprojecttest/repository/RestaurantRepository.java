package com.example.finalprojecttest.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.finalprojecttest.model.Restaurant;

@Repository 
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer>{

	Restaurant findByName(String name);
	List<Restaurant> findByUser_Id(int adminId);

}
