package com.haulmont.vaadintesttask.repositories;

import com.haulmont.vaadintesttask.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
