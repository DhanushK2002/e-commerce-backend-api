package com.ecommerce.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Password {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long passwordId;
	
	private String password;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;

	public Password() {
		super();
	}

	public Password(Long passwordId, String password, User user) {
		super();
		this.passwordId = passwordId;
		this.password = password;
		this.user = user;
	}

	 
	public Long getPasswordId() {
		return passwordId;
	}

	public void setPasswordId(Long passwordId) {
		this.passwordId = passwordId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
