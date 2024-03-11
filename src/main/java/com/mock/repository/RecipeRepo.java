package com.mock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mock.entities.Recipe;
import java.util.Optional;

@Repository
public interface RecipeRepo extends JpaRepository<Recipe, Integer> {
    Optional<Recipe> findByTitle(String title);
}
