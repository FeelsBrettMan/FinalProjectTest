package com.example.finalprojecttest.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.finalprojecttest.model.User;

@Repository 
public interface UserRepository extends JpaRepository<User, Integer>{

	User findOneByUserName(String username);
	
}
