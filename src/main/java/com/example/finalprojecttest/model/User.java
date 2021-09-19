package com.example.finalprojecttest.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // make sure our date loads fast enough w/o getting error
public class User implements Serializable{

	
	private static final long serialVersionUID = 1L;

	// roles that we will use for our security
	public enum Role {
		ROLE_USER, ROLE_ADMIN
	}
		
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull
	@Column(columnDefinition = "varchar(20) default 'N/A'")
	private String userName;
	
	@NotNull
	@Column(columnDefinition = "varchar(20) default 'N/A'")
	private String password;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Review> reviews;
	
	@JsonIgnore
	// User type "admin" can create and manipulate multiples restaurants pages
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Restaurant> restaurants;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;

	public User() {
		this(-1, "NA","NA", Role.ROLE_USER);
	}

	public User(Integer id,@NotNull String userName, @NotNull String password, Role role) {
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.role = role;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public List<Review> getReviews() {
		return reviews;
	}
	
	public List<Restaurant> getRestaurants() {
		return restaurants;
	}
	
}
