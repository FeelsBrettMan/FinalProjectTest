package com.example.finalprojecttest.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // make sure our date loads fast enough w/o getting error
public class Restaurant implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull
	@Column(columnDefinition = "varchar(20) default 'N/A'")
	private String name;
	
	@NotNull
	@Column(columnDefinition = "varchar(50) default 'N/A'")
	private String address;
	
	@NotNull
	@Column(columnDefinition = "varchar(300) default 'N/A'")
	private String description;
	
	@JsonIgnore
	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
	private List<Review> reviews;
	
	// User represents 
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="user_id", referencedColumnName = "id")
	private User user;
	
	public Restaurant() {
		this(-1, "NA","NA","NA", new ArrayList<Review>());
	}
	
	public Restaurant(Integer id, @NotNull String name,@NotNull String address,@NotNull String description, 
			List<Review> reviews) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.description = description;
		this.reviews = reviews;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<Review> getReviews() {
		return reviews;
	}
	
	// only need setter for User
	public void setUser(User user) {
		this.user = user;
	}
	
	public void setNewReviews() {
		
		for(Review a : reviews) {
			a.setId(-1);
		}
	}
}
