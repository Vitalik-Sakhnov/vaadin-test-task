package com.haulmont.vaadintesttask.services;

import com.haulmont.vaadintesttask.models.Recipe;

import java.util.List;

public interface RecipeService {
    List<Recipe> findAll();

    void update(Long id, Recipe recipe);

    void save(Recipe recipe);

    void delete(Recipe recipe);
}
