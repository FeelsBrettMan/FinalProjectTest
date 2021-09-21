package com.example.finalprojecttest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.finalprojecttest.model.User;
import com.example.finalprojecttest.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	UserRepository repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User userFound = repo.findOneByUserName(username);
		if(userFound == null ) { 
			throw new UsernameNotFoundException("No user with username: "+username);
		}
		System.out.println("~~User thats returned~~");
		System.out.println(userFound);
		return new MyUserDetails(userFound);
	}
	
	
}
