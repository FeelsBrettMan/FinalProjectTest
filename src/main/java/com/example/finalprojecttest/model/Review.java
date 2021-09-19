package com.example.finalprojecttest.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // make sure our date loads fast enough w/o getting error
public class Review implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="restaurant_id", referencedColumnName = "id")
	private Restaurant restaurant;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="user_id", referencedColumnName = "id")
	private User user;
	
	@NotNull
	@Column(columnDefinition = "varchar(300) default 'N/A'")
	private String description;
	
	@NotNull
	@Column(columnDefinition = "double(1, 2) default 0.00")
	private double rating;

	public Review() {
		this(-1, new Restaurant(), new User(), "NA", 0);
	}
	public Review(Integer id, Restaurant restaurant, User user, @NotNull String description, @NotNull double rating) {
		super();
		this.id = id;
		this.restaurant = restaurant;
		this.user = user;
		this.description = description;
		this.rating = rating;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Restaurant getRestaurantId() {
		return restaurant;
	}

	public void setRestaurantId(Restaurant restaurantId) {
		this.restaurant = restaurantId;
	}

	public User getUser() {
		return user;
	}

	public void setUserId(User user) {
		this.user = user;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
	
	// only need setter for User
	public void setUser(User user) {
		this.user = user;
	}
	
	// only need setter for Restaurant
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	
	
	
}