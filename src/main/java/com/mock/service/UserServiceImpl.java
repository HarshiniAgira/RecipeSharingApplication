package com.mock.service;


import com.mock.dto.LogInDto;
import com.mock.dto.UserProfileResDto;
import com.mock.dto.UserReqDto;
import com.mock.entities.CurrentUserSession;
import com.mock.entities.User;
import com.mock.exception.CurrentUserSessionException;
import com.mock.repository.CurrentSessionRepo;
import com.mock.repository.UserRepo;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    public CurrentSessionRepo currentSessionRepo;
    @Autowired
    public UserRepo userRepo;
    @Autowired
    public PasswordEncoder passwordEncoder;

    @Override
    public UserProfileResDto createUser(UserReqDto userReqDto) throws CurrentUserSessionException {
       User user=new User();
       user.setUserName(userReqDto.getUserName());
       user.setEmail(userReqDto.getEmail());
       String encodedPassword = passwordEncoder.encode(userReqDto.getPassword());
       user.setPassword(encodedPassword);
       User savedUser = userRepo.save(user);
       UserProfileResDto userProfileResDto=new UserProfileResDto();
       userProfileResDto.setUserName(savedUser.getUserName());
       userProfileResDto.setEmail(savedUser.getEmail());
       userProfileResDto.setUserId(savedUser.getUserId());
       return userProfileResDto;
    }

    @Override
    public UserProfileResDto getUserProfile(int userId) throws NotFoundException {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        UserProfileResDto userProfileResDto = new UserProfileResDto();
        userProfileResDto.setUserId(user.getUserId());
        userProfileResDto.setUserName(user.getUserName());
        userProfileResDto.setEmail(user.getEmail());
        userProfileResDto.setNumberOfRecipes(user.getRecipes().size());
        userProfileResDto.setNumberOfBookmarkedRecipes(user.getBookmarkedRecipes().size());

        return userProfileResDto;
    }

    // Method to log in a user based on provided credentials
    @Override
    public CurrentUserSession logIn(LogInDto lid) throws CurrentUserSessionException {
        // Find the user by username
        User user = userRepo.findByUserName(lid.getUsername());

        // Check if the user is found
        if (user == null) {
            throw new CurrentUserSessionException("User not found");
        }

        // Check if the user is already logged in
        CurrentUserSession ccus = currentSessionRepo.findByUserId(user.getUserId());
        if (ccus != null) {
            throw new CurrentUserSessionException("User is Already logged In");
        }

        // Check if the provided password is valid
        if (!user.getPassword().equals(lid.getPassword())) {
            throw new CurrentUserSessionException("Credentials are not valid");
        }

        // Generate a user authentication ID
        String userAuthenticationId = user.getUserName() + user.getUserId();

        // Create a new current user session
        CurrentUserSession cus = new CurrentUserSession();
        cus.setUserId(user.getUserId());
        cus.setUserAuthenticationId(userAuthenticationId);
        cus.setLocalDateTime(LocalDateTime.now());

        // Save the current user session in the repository
        return currentSessionRepo.save(cus);
    }

    // Method to log out a user based on authentication ID
    @Override
    public boolean logOut(String userAuthenticationId) throws CurrentUserSessionException {
        // Find the current user session by authentication ID
        CurrentUserSession currentUserSession = currentSessionRepo.findByUserAuthenticationId(userAuthenticationId);

        // Check if the user is currently logged in
        if (currentUserSession == null) {
            throw new CurrentUserSessionException("User is not logged in currently");
        }

        // Delete the current user session
        currentSessionRepo.delete(currentUserSession);

        // Return true to indicate successful logout
        return true;
    }
}
