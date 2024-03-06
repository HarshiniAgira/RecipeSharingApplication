package com.mock.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// Define an entity class representing a rating for a Recipe
@Setter
@Getter
@Entity
public class Rating {

    // Setter method for setting the ID of the rating
    // Getter method for retrieving the ID of the rating
    // Primary key for the entity, auto-generated using identity strategy
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Setter method for setting the number of raters
    // Getter method for retrieving the number of raters
    // Number of users who have rated the recipe
    private int noOfRaters;

    // Setter method for setting the overall rating
    // Getter method for retrieving the overall rating
    // The overall rating for the recipe
    private int rating;

    // Setter method for setting the associated Recipe
    // Getter method for retrieving the associated Recipe
    // Reference to the associated Recipe, marked with @JsonIgnore to avoid infinite recursion
    @JsonIgnore
    @OneToOne
    private Recipe recipe;

    // Default constructor required for JPA
    public Rating() {
        // TODO Auto-generated constructor stub
    }

}
