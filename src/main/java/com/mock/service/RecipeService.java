package com.mock.service;

import java.util.List;

import com.mock.dto.CommentDto;
import com.mock.dto.CommentResDto;
import com.mock.dto.RecipeReqDto;
import com.mock.dto.RecipeResDto;
import com.mock.entities.Rating;
import com.mock.entities.Recipe;
import com.mock.exception.RecipeException;
import com.mock.exception.UserException;
import javassist.NotFoundException;

public interface RecipeService {

	// Method for creating a new recipe
	 RecipeResDto createRecipe(RecipeReqDto recipeReqDto) throws UserException;

	// Method for retrieving a specific recipe by its ID
	 RecipeResDto getRecipe(int recipeId) throws RecipeException;

	//get recipe by name
	 RecipeResDto getRecipeByTitle(String  recipeTitle) throws RecipeException;

	// Method for retrieving all recipes
	 List<RecipeResDto> getAllRecipe() throws RecipeException;

	// Method for updating an existing recipe
	 RecipeResDto updateRecipe(int recipeId, RecipeReqDto recipeReqDto) throws UserException, RecipeException, NotFoundException;

	// Method for deleting an existing recipe
	void deleteRecipe(int recipeId) throws UserException, RecipeException;

	// Method for rating a recipe
//	Recipe rateRecipe(int recipeId, int ratingValue) throws RecipeException, NotFoundException;
	Rating rateRecipe(int recipeId, int ratingValue) throws RecipeException, NotFoundException;

	// Method for bookmarking a recipe
	void bookmarkRecipe(int recipeId, int userId) throws NotFoundException, RecipeException, UserException;

	CommentResDto addComment(CommentDto commentDto) throws NotFoundException, RecipeException, UserException;


}

