package com.mock.entities;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CurrentUserSession")
public class CurrentUserSession {

	@Id
	@Column(unique = true)
	private Integer userId;

	// Setter method for setting the user authentication ID (UUID)
	// Getter method for retrieving the user authentication ID (UUID)
	// Unique identifier for user authentication
	private String userAuthenticationId;

	// Setter method for setting the timestamp of the current session
	// Getter method for retrieving the timestamp of the current session
	// Timestamp indicating the time of the current session
	private LocalDateTime localDateTime;

	// Constructor to initialize the CurrentUserSession with user ID and authentication ID
	public CurrentUserSession(Integer userId, String userAuthenticationId) {
		super();
		this.userId = userId;
		this.userAuthenticationId = userAuthenticationId;

		// Set the timestamp to the current date and time
		this.localDateTime = LocalDateTime.now();
	}

}
