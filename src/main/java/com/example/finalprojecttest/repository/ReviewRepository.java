package com.example.finalprojecttest.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.finalprojecttest.model.Restaurant;
import com.example.finalprojecttest.model.Review;
import com.example.finalprojecttest.model.User;

@Repository 
public interface ReviewRepository extends JpaRepository<Review, Integer>{

	List<Review> findByUser_Id(int userId);
}
