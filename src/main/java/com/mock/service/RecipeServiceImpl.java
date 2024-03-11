package com.mock.service;

import com.mock.dto.IngredientDto;
import com.mock.dto.RecipeReqDto;
import com.mock.dto.RecipeResDto;
import com.mock.entities.*;
import com.mock.exception.RecipeException;
import com.mock.exception.UserException;
import com.mock.repository.CurrentSessionRepo;
import com.mock.repository.RatingRepo;
import com.mock.repository.RecipeRepo;
import com.mock.repository.UserRepo;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class RecipeServiceImpl implements RecipeService {
    Date date = null;
    @Autowired
    public RecipeRepo recipeRepo;
    @Autowired
    public UserRepo userRepo;
    @Autowired
    public CurrentSessionRepo currentSessionRepo;
    @Autowired
    public RatingRepo ratingRepo;

    // Implementation for creating a new recipe
    @Override
    public RecipeResDto createRecipe(RecipeReqDto recipeReqDto) throws UserException {
        Optional<User> optionalUser = userRepo.findById(recipeReqDto.getUserId());
        if (!optionalUser.isPresent()) {
            throw new UserException("No User Found");
        }
        User user = optionalUser.get();
        Recipe recipe = new Recipe();
        recipe.setUser(user);
        recipe.setTitle(recipeReqDto.getTitle());
        recipe.setInstruction(recipeReqDto.getInstruction());
        recipe.setDescription(recipeReqDto.getDescription());
        recipe.setDifficulty(recipeReqDto.getDifficulty());
        recipe.setCreatedDate(recipeReqDto.getCreatedDate());
        Set<IngredientDto> ingredients = recipeReqDto.getIngredients();
        Set<Ingredient> setOfIngredients = ingredients.stream().map(ingredientDto1 -> {
            Ingredient ingredient = new Ingredient();
            ingredient.setName(ingredientDto1.getName());
            ingredient.setQuantity(ingredientDto1.getQuantity());
            return ingredient;
        }).collect(Collectors.toSet());
        recipe.setIngredients(setOfIngredients);
        Recipe save = recipeRepo.save(recipe);

        Rating rate = new Rating();
        rate.setRecipe(save);
        rate.setNoOfRaters(0);
        rate.setRatingValue(0);
        recipe.setRating(rate);

        ratingRepo.save(rate);
        Date createdDate = save.getCreatedDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(createdDate);

        RecipeResDto recipeResDto = new RecipeResDto();
        recipeResDto.setTitle(save.getTitle());
        Set<IngredientDto> ingredientDtoSet = save.getIngredients().stream().map(ingredient -> {
            IngredientDto ingredientDto = new IngredientDto();
            ingredientDto.setName(ingredient.getName());
            ingredientDto.setQuantity(ingredient.getQuantity());
            return ingredientDto;
        }).collect(Collectors.toSet());
        recipeResDto.setIngredients(ingredientDtoSet);
        recipeResDto.setDescription(save.getDescription());
        recipeResDto.setInstruction(save.getInstruction());
        recipeResDto.setDifficulty(save.getDifficulty());
        recipeResDto.setCreatedDate(save.getCreatedDate());
        recipeResDto.setCreatedDate(save.getCreatedDate());

        return recipeResDto;
    }

    @Override
    public RecipeResDto getRecipe(int recipeId) throws RecipeException {
        Optional<Recipe> recipeById = recipeRepo.findById(recipeId);
        if (!recipeById.isPresent()) {
            throw new RecipeException("Recipe with Id '" + recipeById + "' not found");
        }


        return convertToDto(recipeById.get());

    }

    private RecipeResDto convertDto(Recipe recipe) {
        RecipeResDto dto1 = new RecipeResDto();
        dto1.setTitle(recipe.getTitle());
        dto1.setDescription(recipe.getDescription());
        dto1.setInstruction(recipe.getInstruction());
        dto1.setDifficulty(recipe.getDifficulty());
        dto1.setCreatedDate(recipe.getCreatedDate());
        Set<IngredientDto> ingredientDtos = recipe.getIngredients().stream()
                .map(this::convertIngredientToDtos1)
                .collect(Collectors.toSet());
        dto1.setIngredients(ingredientDtos);

        return dto1;
    }

    private IngredientDto convertIngredientToDtos1(Ingredient ingredient) {
        IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setName(ingredient.getName());
        ingredientDto.setQuantity(ingredient.getQuantity());
        return ingredientDto;
    }


    @Override
    public RecipeResDto getRecipeByTitle(String recipeTitle) throws RecipeException {
        Optional<Recipe> recipeByName = recipeRepo.findByTitle(recipeTitle);
        if (!recipeByName.isPresent()) {
            throw new RecipeException("Recipe with title '" + recipeTitle + "' not found");
        }
        return convertToDto(recipeByName.get());
    }

    private RecipeResDto convertToDto(Recipe recipe) {
        RecipeResDto dto = new RecipeResDto();
        dto.setTitle(recipe.getTitle());
        dto.setDescription(recipe.getDescription());
        dto.setInstruction(recipe.getInstruction());
        dto.setDifficulty(recipe.getDifficulty());
        dto.setCreatedDate(recipe.getCreatedDate());
        Set<IngredientDto> ingredientDtos = recipe.getIngredients().stream()
                .map(this::convertIngredientToDtos)
                .collect(Collectors.toSet());
        dto.setIngredients(ingredientDtos);

        return dto;
    }

    private IngredientDto convertIngredientToDtos(Ingredient ingredient) {
        IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setName(ingredient.getName());
        ingredientDto.setQuantity(ingredient.getQuantity());
        return ingredientDto;
    }


    @Override
    public List<RecipeResDto> getAllRecipe() throws RecipeException {
        List<Recipe> recipes = recipeRepo.findAll();
        if (recipes.isEmpty()) {
            throw new RecipeException("No recipes found");
        }
        return recipes.stream()
                .map(this::convertToDtos)
                .collect(Collectors.toList());
    }

    private RecipeResDto convertToDtos(Recipe recipe) {
        RecipeResDto dto = new RecipeResDto();
        dto.setTitle(recipe.getTitle());
        dto.setDescription(recipe.getDescription());
        dto.setInstruction(recipe.getInstruction());
        dto.setDifficulty(recipe.getDifficulty());
        dto.setCreatedDate(recipe.getCreatedDate());
        Set<IngredientDto> ingredientDtos = recipe.getIngredients().stream()
                .map(this::convertIngredientToDto)
                .collect(Collectors.toSet());
        dto.setIngredients(ingredientDtos);
        return dto;
    }

    private IngredientDto convertIngredientToDto(Ingredient ingredient) {
        IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setName(ingredient.getName());
        ingredientDto.setQuantity(ingredient.getQuantity());
        return ingredientDto;
    }

    @Override
    public void deleteRecipe(int recipeId) throws UserException, RecipeException {
        Optional<Recipe> recipeOptional = recipeRepo.findById(recipeId);

        if (recipeOptional.isPresent()) {
            recipeRepo.deleteById(recipeId);
        } else {
            throw new RecipeException("Recipe with ID '" + recipeId + "' not found");
        }
    }

    @Override
    public Rating rateRecipe(int recipeId, int ratingValue) throws RecipeException, NotFoundException {

        Recipe recipe = recipeRepo.findById(recipeId).orElseThrow(() -> new NotFoundException("Recipe not found"));
        Rating rating = recipe.getRating();
        if (rating == null) {
            rating = new Rating();
            rating.setNoOfRaters(0);
            rating.setRatingValue(0);
        }
        int totalRaters = rating.getNoOfRaters();
        int totalRating = rating.getRatingValue();

        totalRaters++;
        totalRating += ratingValue;
        int newRatingValue = totalRating / totalRaters;

        rating.setNoOfRaters(totalRaters);
        rating.setRatingValue(newRatingValue);

        recipe.setRating(rating);

        return rating;

    }

    @Override
    public void bookmarkRecipe(int recipeId, int userId) throws NotFoundException {
        Recipe recipe = recipeRepo.findById(recipeId)
                .orElseThrow(() -> new NotFoundException("Recipe not found"));

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        user.getBookmarkedRecipes().add(recipe);
        userRepo.save(user);
    }


    @Override
    public RecipeResDto updateRecipe(int recipeId, RecipeReqDto recipeReqDto) throws UserException, RecipeException, NotFoundException {
        Recipe existingRecipe = recipeRepo.findById(recipeId).orElse(null);
        if (existingRecipe == null) {
            throw new NotFoundException("Recipe not found with id: " + recipeId);
        }
        existingRecipe.setTitle(recipeReqDto.getTitle());
        existingRecipe.setDescription(recipeReqDto.getDescription());
        Set<IngredientDto> ingredients = recipeReqDto.getIngredients();
        Set<Ingredient> setOfIngredients = ingredients.stream().map(ingredientDto1 -> {
            Ingredient ingredient = new Ingredient();
            ingredient.setName(ingredientDto1.getName());
            ingredient.setQuantity(ingredientDto1.getQuantity());
            return ingredient;
        }).collect(Collectors.toSet());
        existingRecipe.setIngredients(setOfIngredients);
        existingRecipe.setInstruction(recipeReqDto.getInstruction());
        existingRecipe.setDifficulty(recipeReqDto.getDifficulty());
        existingRecipe.setCreatedDate(recipeReqDto.getCreatedDate());
        Recipe savedRecipe = recipeRepo.save(existingRecipe);

        return convertToRecipeResDto(savedRecipe);
    }

    private RecipeResDto convertToRecipeResDto(Recipe recipe) {
        RecipeResDto recipeResDto = new RecipeResDto();
        recipeResDto.setTitle(recipe.getTitle());
        recipeResDto.setDescription(recipe.getDescription());
        Set<IngredientDto> ingredientDtoSet = recipe.getIngredients().stream().map(ingredient -> {
            IngredientDto ingredientDto = new IngredientDto();
            ingredientDto.setName(ingredient.getName());
            ingredientDto.setQuantity(ingredient.getQuantity());
            return ingredientDto;
        }).collect(Collectors.toSet());
        recipeResDto.setIngredients(ingredientDtoSet);
        recipeResDto.setInstruction(recipe.getInstruction());
        recipeResDto.setDifficulty(recipe.getDifficulty());
        recipeResDto.setCreatedDate(recipe.getCreatedDate());
        return recipeResDto;
    }


}


//
//
//
//
//    // Implementation for rating a recipe
//    @Override
//    public Rating rateRecipe(int recipeid, int rate) throws RecipeException {
//        // Check if the rating is greater than 5
//        if (rate > 5) {
//            throw new RecipeException("Rating should not be greater than 5");
//        }
//
//        // Find the existing recipe based on the provided ID
//        Optional<Recipe> exRecipe = recipeRepo.findById(recipeid);
//
//        // Check if the existing recipe is present
//        if (!exRecipe.isPresent()) {
//            throw new RecipeException("Recipe not found by this id");
//        }
//
//        // Retrieve the recipe and its associated rating
//        Recipe recipe = exRecipe.get();
//        Rating rating = recipe.getRating();
//
//        // Calculate the new rating based on the provided rate
//        rating.setRating(((rating.getNoOfRaters() * rating.getRating()) + rate) / (rating.getNoOfRaters() + 1));
//        rating.setNoOfRaters(rating.getNoOfRaters() + 1);
//
//        // Save the updated rating to the repository
//        ratingRepo.save(rating);
//
//        // Return the updated rating
//        return rating;
//    }
//
//
//    // Implementation for bookmarking a recipe
//    @Override
//    public Recipe bookMarkRecipe(String userAuthenticationId, int recipeId) throws UserException, RecipeException {
//        // Retrieve the current user session based on authentication ID
//        CurrentUserSession cus = currentSessionRepo.findByUserAuthenticationId(userAuthenticationId);
//
//        // Find the existing recipe based on the provided ID
//        Optional<Recipe> exRecipe = recipeRepo.findById(recipeId);
//
//        // Check if the existing recipe is present
//        if (!exRecipe.isPresent()) {
//        lculate new ratin    throw new RecipeException("Recipe not found by this id");
//        }
//
//        // Retrieve the recipe
//        Recipe recipe = exRecipe.get();
//
//        // Check if the user is logged in
//        if (cus == null) {
//            throw new UserException("You must Login to bookmark this Recipe");
//        }
//
//        // Find the user associated with the current session
//        User user = userRepo.findByUserId(cus.getUserId());
//
//        // Check if a valid user is found
//        if (user == null) {
//            throw new UserException("No User Found");
//        }
//
//        // Add the recipe to the user's bookmarked recipes
//        Set<Recipe> set = user.getBookmarkedRecipes();
//        set.add(recipe);
//        user.setBookmarkedRecipes(set);
//
//        // Save the updated user information
//        userRepo.save(user);
//
//        // Add the user to the recipe's list of bookmarked users
//        Set<User> users = recipe.getBookmarkedUsers();
//        users.add(user);
//        recipe.setBookmarkedUsers(users);
//
//        // Save the updated recipe
//        return recipeRepo.save(recipe);
//    }

