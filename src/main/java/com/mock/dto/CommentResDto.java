package com.mock.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResDto {
        private int id;
        private int userId;
        private int recipeId;
        private String content;
        private Date commentCreatedAt;

}
