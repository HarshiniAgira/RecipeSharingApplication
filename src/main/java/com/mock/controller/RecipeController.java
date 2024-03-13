package com.mock.controller;

import java.util.List;

import com.mock.dto.CommentDto;
import com.mock.dto.CommentResDto;
import com.mock.dto.RecipeReqDto;
import com.mock.dto.RecipeResDto;
import com.mock.entities.Comment;
import com.mock.entities.Rating;
import com.mock.entities.Recipe;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import com.mock.entities.Rating;
import com.mock.exception.RecipeException;
import com.mock.exception.UserException;
import com.mock.service.RecipeService;

import javax.validation.Valid;


// Define a RESTful controller for handling Recipe-related operations
@RestController
public class RecipeController {


    @Autowired
    public RecipeService recipeService;

    // Define endpoint for adding a new recipe
    @PostMapping(value = "/recipe")
    ResponseEntity<RecipeResDto> addRecipe(@Valid @RequestBody RecipeReqDto recipeReqDto) throws RecipeException, UserException {
        // Return a response with the created recipe and status ACCEPTED
        return new ResponseEntity<>(recipeService.createRecipe(recipeReqDto), HttpStatus.ACCEPTED);
    }

    // Define endpoint for retrieving a recipe by its ID
    @GetMapping("/{id}")
    ResponseEntity<RecipeResDto> getRecipe(@PathVariable int id) throws UserException, RecipeException {
        // Return a response with the retrieved recipe and status FOUND
        return new ResponseEntity<>(recipeService.getRecipe(id), HttpStatus.FOUND);
    }

    @GetMapping("/name/{recipeName}")
    ResponseEntity<RecipeResDto> getRecipeByName(@Valid @PathVariable String recipeName) throws RecipeException {
        return new ResponseEntity<>(recipeService.getRecipeByTitle(recipeName), HttpStatus.ACCEPTED);
    }

    // Define endpoint for retrieving all recipes
    @GetMapping("/getall")
    ResponseEntity<List<RecipeResDto>> getAllRecipe() throws RecipeException {
        // Return a response with the list of all recipes and status FOUND
        return new ResponseEntity<>(recipeService.getAllRecipe(), HttpStatus.FOUND);
    }

    @PutMapping("/update/{recipeId}")
    public ResponseEntity<RecipeResDto> updateRecipe(@PathVariable int recipeId, @RequestBody RecipeReqDto recipeReqDto) throws NotFoundException, UserException, RecipeException {
        return new ResponseEntity<>(recipeService.updateRecipe(recipeId, recipeReqDto), HttpStatus.ACCEPTED);
    }

    // Define endpoint for deleting a recipe
    @DeleteMapping("/{recipeId}")
    public ResponseEntity<String> deleteRecipe(@PathVariable int recipeId) {
        try {
            recipeService.deleteRecipe(recipeId);
            return ResponseEntity.ok("Recipe deleted successfully");
        } catch (UserException | RecipeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @PostMapping("/{recipeId}/rate")
    public ResponseEntity<Rating> rateRecipe(@PathVariable int recipeId, @RequestParam int ratingValue) throws RecipeException,NotFoundException{
		Rating rating = recipeService.rateRecipe(recipeId, ratingValue);
		return new ResponseEntity<>(rating,HttpStatus.ACCEPTED);
    }

    @PostMapping("/bookmark")
    public ResponseEntity<String> bookmarkRecipe(@RequestParam int recipeId, @RequestParam int userId) throws RecipeException, UserException, NotFoundException {
        recipeService.bookmarkRecipe(recipeId, userId);
        return ResponseEntity.ok("Recipe bookmarked successfully");
    }

    @PostMapping("/comment")
    public ResponseEntity<CommentResDto> addCommentToRecipe(@RequestBody CommentDto commentDto) throws NotFoundException, RecipeException, UserException {
        CommentResDto commentResDto = recipeService.addComment(commentDto);
        return new ResponseEntity<>(commentResDto, HttpStatus.CREATED);
    }

}