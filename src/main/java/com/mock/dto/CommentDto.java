package com.mock.dto;

import com.mock.entities.Recipe;
import com.mock.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class CommentDto {
    private User user;
    private Recipe recipe;
    private String content;
}
