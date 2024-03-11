package com.mock.service;

import com.mock.dto.LogInDto;
import com.mock.dto.UserProfileResDto;
import com.mock.dto.UserReqDto;
import com.mock.entities.CurrentUserSession;
import com.mock.entities.User;
import com.mock.exception.CurrentUserSessionException;
import com.mock.exception.UserException;
import javassist.NotFoundException;

// Interface defining the contract for User-related operations
public interface UserService {

	// Method for adding a new user
	UserProfileResDto createUser(UserReqDto userReqDto) throws CurrentUserSessionException;

	UserProfileResDto getUserProfile(int userId) throws NotFoundException;

	// Method for logging in a user based on provided credentials
	public CurrentUserSession logIn(LogInDto lid) throws CurrentUserSessionException;

	// Method for logging out a user based on authentication ID
	public boolean logOut(String userAuthenticationId) throws CurrentUserSessionException;
}

