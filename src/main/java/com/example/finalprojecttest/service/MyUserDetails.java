package com.example.finalprojecttest.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.finalprojecttest.model.User;

//contains all details necessary to authenticate user
public class MyUserDetails implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	
	//attributes needed for authorization/authentication
	private String username;
	private String password;
	private List<GrantedAuthority> authorities;
	
	public MyUserDetails(User user) { 
		this.username = user.getUserName();
		this.password = user.getPassword();
		this.authorities = Arrays.asList(new SimpleGrantedAuthority(user.getRole().name()));
	}
	
	public MyUserDetails() {}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	//needs to be overridden, but not utilized in user model
	@Override
	public boolean isEnabled() {
		return true;
	}

}
