package com.mock.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResDto {

    private int userId;
    private String userName;
    private String email;
    private int numberOfRecipes;
    private int numberOfBookmarkedRecipes;

}
