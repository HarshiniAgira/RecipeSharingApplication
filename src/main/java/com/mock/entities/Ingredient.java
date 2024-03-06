package com.mock.entities;

import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

// Define an embeddable class representing an ingredient
@Setter
@Getter
@Embeddable
public class Ingredient {

	private String name;
	private String quantity;

	// Constructor to initialize the Incredient with name and quantity
	public Ingredient(String name, String quantity) {
		super();
		this.name = name;
		this.quantity = quantity;
	}

}
