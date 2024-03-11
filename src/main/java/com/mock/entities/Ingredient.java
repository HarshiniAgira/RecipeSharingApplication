package com.mock.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Table;

@Setter
@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Ingredient")
public class Ingredient {

	private String name;
	private String quantity;

}
