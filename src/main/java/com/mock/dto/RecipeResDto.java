package com.mock.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.Set;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeResDto {

    private String title;
    private String description;
    private Set<IngredientDto> ingredients;
    private String instruction;
    private String difficulty;
    private Date CreatedDate;

}
