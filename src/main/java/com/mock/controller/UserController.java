package com.mock.controller;

import com.mock.dto.UserProfileResDto;
import com.mock.dto.UserReqDto;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mock.dto.LogInDto;
import com.mock.entities.CurrentUserSession;
import com.mock.exception.CurrentUserSessionException;
import com.mock.exception.UserException;
import com.mock.service.UserService;

import javax.validation.Valid;

@RestController
public class UserController {

	@Autowired
	public UserService userService;

	// Define endpoint for adding a new user
	@PostMapping("/users")
	ResponseEntity<UserProfileResDto> createUser(@Valid @RequestBody UserReqDto userReqDto) throws UserException, CurrentUserSessionException {
		UserProfileResDto userProfileResDto = userService.createUser(userReqDto);
		return new ResponseEntity<>(userProfileResDto, HttpStatus.CREATED);

	}
	@GetMapping("/users/{userId}")
	public ResponseEntity<UserProfileResDto> getUserProfile(@PathVariable int userId) throws NotFoundException {
		UserProfileResDto userProfileResDto = userService.getUserProfile(userId);
		return ResponseEntity.ok(userProfileResDto);
	}

//	 Define endpoint for user login
	@GetMapping("/login")
	ResponseEntity<CurrentUserSession> logInUser(@RequestBody LogInDto ltd) throws UserException, CurrentUserSessionException {
		// Return a response with the current user session and status ACCEPTED
		return new ResponseEntity<>(userService.logIn(ltd), HttpStatus.ACCEPTED);
	}

	// Define endpoint for user logout
	@PostMapping("/logout/{uuid}")
	ResponseEntity<Boolean> logOutUser(@PathVariable String uuid) throws UserException, CurrentUserSessionException {
		// Return a response with the logout status and status ACCEPTED
		return new ResponseEntity<>(userService.logOut(uuid), HttpStatus.ACCEPTED);
	}
}