package com.mock.dto;

import com.mock.validatePassword.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserReqDto {
    @NotBlank(message = "Username is required")
    @Size(min = 6, max = 16, message = "Username must be between 6 and 16 characters")
    private String userName;

    @NotBlank(message = "Password is required")
    @ValidPassword(message = "A minimum 6 characters password contains a combination of uppercase and lowercase letter and number are required.")
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

}
