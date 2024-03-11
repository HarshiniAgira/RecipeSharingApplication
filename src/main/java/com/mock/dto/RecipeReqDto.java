package com.mock.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeReqDto {
    @NotNull(message = "Title must not be null")
    private String title;
    @NotNull(message = "Description must not be null")
    private String description;
    private Set<IngredientDto> ingredients;
    private String instruction;
    private String difficulty;
    private int userId;
    @NotNull(message = "Date must not be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date CreatedDate;
    //private List<CommentDto> commentListDto;
}
