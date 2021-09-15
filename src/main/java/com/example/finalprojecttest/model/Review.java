package com.example.finalprojecttest.model;

import javax.persistence.*;
import java.io.Serializable;



@Entity
public class Review implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name = "idd", nullable = false)
	private Long idd;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="restaurant_id", referencedColumnName = "id")
	private Restaurant restaurantId;
	
	@ManyToOne
	@JoinColumn(name="user_id", referencedColumnName = "id")
	private User userId;
	
	private String description;
	
	private int rating;

	public Long getIdd() {
		return idd;
	}

	public void setIdd(Long idd) {
		this.idd = idd;
	}

	public Review() {
		this(-1, new Restaurant(), new User(), "NA", 0);
	}
	public Review(Integer id, Restaurant restaurantId, User userId, String description, int rating) {
		super();
		this.id = id;
		this.restaurantId = restaurantId;
		this.userId = userId;
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
		return restaurantId;
	}

	public void setRestaurantId(Restaurant restaurantId) {
		this.restaurantId = restaurantId;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
	
	
	
}