package com.mock.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

// Define an entity class representing a Recipe
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank
    @NotNull
    private String title;
    @NotBlank
    private String description;
    @ElementCollection
    @Embedded
    private Set<Ingredient> ingredients;
    private String instruction;
    @ElementCollection
    @Embedded
    private List<Comment> commentList;
    private Date createdDate;
    private String difficulty;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "bookmarkedRecipes")
    private Set<User> bookmarkedUsers;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Rating rating;

    // Override hashCode and equals methods based on the recipe's ID for proper comparison
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Recipe other = (Recipe) obj;
        return id == other.id;
    }

}
