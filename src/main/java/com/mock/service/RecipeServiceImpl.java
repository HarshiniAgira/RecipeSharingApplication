package com.mock.service;

import com.mock.Utility.RecipeMapper;
import com.mock.dto.*;
import com.mock.entities.*;
import com.mock.exception.*;
import com.mock.repository.*;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.mock.Utility.RecipeMapper.convertToRecipe;


@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeRepo recipeRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RatingRepo ratingRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private RecipeMapper recipeMapper;


    @Override
    public RecipeResDto createRecipe(RecipeReqDto recipeReqDto) throws UserException {
        User user = userRepo.findById(recipeReqDto.getUserId())
                .orElseThrow(() -> new UserException("No User Found"));

        Recipe recipe = convertToRecipe(recipeReqDto);
        recipe.setUser(user);
        Recipe savedRecipe = recipeRepo.save(recipe);

        return RecipeMapper.convertToRecipeResDto(savedRecipe);
    }

    @Override
    public RecipeResDto getRecipe(int recipeId) throws RecipeException {
        Recipe recipe = recipeRepo.findById(recipeId)
                .orElseThrow(() -> new RecipeException("Recipe with Id '" + recipeId + "' not found"));
        return RecipeMapper.convertToRecipeResDto(recipe);
    }

    @Override
    public RecipeResDto getRecipeByTitle(String recipeTitle) throws RecipeException {
        Recipe recipe = recipeRepo.findByTitle(recipeTitle)
                .orElseThrow(() -> new RecipeException("Recipe with title '" + recipeTitle + "' not found"));
        return RecipeMapper.convertToRecipeResDto(recipe);
    }

    @Override
    public List<RecipeResDto> getAllRecipe() throws RecipeException {
        List<Recipe> recipes = recipeRepo.findAll();
        if (recipes.isEmpty()) {
            throw new RecipeException("No recipes found");
        }
        RecipeMapper recipeMapper = new RecipeMapper();
        return recipes.stream()
                .map(RecipeMapper::convertToRecipeResDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteRecipe(int recipeId) throws UserException, RecipeException {

        Optional<Recipe> recipeOptional = recipeRepo.findById(recipeId);
        if (!recipeOptional.isPresent()) {
            throw new RecipeException("Recipe not found with ID: " + recipeId);
        }

        Recipe recipe = recipeOptional.get();
        User user = recipe.getUser();
        if (user == null) {
            throw new UserException("User not found for recipe with ID: " + recipeId);
        }

        recipeRepo.delete(recipe);
    }

    @Override
    public Rating rateRecipe(int recipeId, int ratingValue) throws RecipeException {
        Recipe recipe = recipeRepo.findById(recipeId)
                .orElseThrow(() -> new RecipeException("Recipe not found with ID: " + recipeId));

        Rating rating = recipe.getRating();
        if (rating == null) {
            rating = new Rating();
            rating.setNoOfRaters(0);
            rating.setRatingValue(0);
            rating.setRecipe(recipe);
        }

        int totalRaters = rating.getNoOfRaters();
        int totalRating = rating.getRatingValue();

        totalRaters++;
        totalRating += ratingValue;
        int newRatingValue = totalRating / totalRaters;

        rating.setNoOfRaters(totalRaters);
        rating.setRatingValue(newRatingValue);

        recipe.setRating(rating);
        ratingRepo.save(rating);

        return rating;
    }

    @Override
    public void bookmarkRecipe(int recipeId, int userId) throws  RecipeException, UserException {
        Recipe recipe = recipeRepo.findById(recipeId)
                .orElseThrow(() -> new RecipeException("Recipe not found with ID: " + recipeId));

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserException("User not found for recipe with ID: " + recipeId));

        user.getBookmarkedRecipes().add(recipe);
        userRepo.save(user);
    }

    @Override
    public CommentResDto addComment(CommentDto commentDto) throws  RecipeException, UserException {
        User user = userRepo.findById(commentDto.getUserId())
                .orElseThrow(() -> new UserException("User not found "));

        Recipe recipe = recipeRepo.findById(commentDto.getRecipeId())
                .orElseThrow(() -> new RecipeException("Recipe not found "));

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setRecipe(recipe);
        comment.setContent(commentDto.getContent());
        comment.setCommentCreatedAt(new Date());

        comment = commentRepo.save(comment);

        CommentResDto commentResDto = new CommentResDto();
        commentResDto.setId(comment.getId());
        commentResDto.setUserId(comment.getUser().getUserId());
        commentResDto.setRecipeId(comment.getRecipe().getId());
        commentResDto.setContent(comment.getContent());
        commentResDto.setCommentCreatedAt(comment.getCommentCreatedAt());

        return commentResDto;
    }

    @Override
    public RecipeResDto updateRecipe(int recipeId, RecipeReqDto recipeReqDto) throws UserException, RecipeException, NotFoundException {
        Recipe existingRecipe = recipeRepo.findById(recipeId)
                .orElseThrow(() -> new RecipeException("Recipe not found with ID: " + recipeId));

        Recipe updatedRecipe = convertToRecipe(recipeReqDto);
        updatedRecipe.setId(existingRecipe.getId()); // Make sure to set the ID of the existing recipe
        Recipe savedRecipe = recipeRepo.save(updatedRecipe);

        return RecipeMapper.convertToRecipeResDto(savedRecipe);
    }
}
