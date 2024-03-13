package com.mock.Utility;

import com.mock.dto.*;
import com.mock.entities.*;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;
@Component
public class RecipeMapper {

    public static Recipe convertToRecipe(RecipeReqDto recipeReqDto) {
        Recipe recipe = new Recipe();
        recipe.setTitle(recipeReqDto.getTitle());
        recipe.setInstruction(recipeReqDto.getInstruction());
        recipe.setDescription(recipeReqDto.getDescription());
        recipe.setDifficulty(recipeReqDto.getDifficulty());
        recipe.setCreatedDate(recipeReqDto.getCreatedDate());

        Set<Ingredient> setOfIngredients = recipeReqDto.getIngredients().stream().map(ingredientDto -> {
            Ingredient ingredient = new Ingredient();
            ingredient.setName(ingredientDto.getName());
            ingredient.setQuantity(ingredientDto.getQuantity());
            return ingredient;
        }).collect(Collectors.toSet());

        recipe.setIngredients(setOfIngredients);

        return recipe;
    }

    public static RecipeResDto convertToRecipeResDto(Recipe recipe) {
        RecipeResDto recipeResDto = new RecipeResDto();
        recipeResDto.setTitle(recipe.getTitle());
        recipeResDto.setInstruction(recipe.getInstruction());
        recipeResDto.setDescription(recipe.getDescription());
        recipeResDto.setDifficulty(recipe.getDifficulty());
        recipeResDto.setCreatedDate(recipe.getCreatedDate());

        Set<IngredientDto> ingredientDtoSet = recipe.getIngredients().stream().map(ingredient -> {
            IngredientDto ingredientDto = new IngredientDto();
            ingredientDto.setName(ingredient.getName());
            ingredientDto.setQuantity(ingredient.getQuantity());
            return ingredientDto;
        }).collect(Collectors.toSet());

        recipeResDto.setIngredients(ingredientDtoSet);

        return recipeResDto;
    }
}
