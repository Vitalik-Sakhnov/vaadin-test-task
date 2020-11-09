package com.haulmont.vaadintesttask.services.impl;

import com.haulmont.vaadintesttask.models.Recipe;
import com.haulmont.vaadintesttask.repositories.RecipeRepository;
import com.haulmont.vaadintesttask.services.RecipeService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {
    private RecipeRepository recipeRepository;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Transactional
    @Override
    public List<Recipe> findAll() {
        List<Recipe> recipes = recipeRepository.findAll();
        recipes.forEach(recipe -> Hibernate.initialize(recipe.getDoctor()));
        recipes.forEach(recipe -> Hibernate.initialize(recipe.getPatient()));
        return recipes;
    }

    @Transactional
    @Override
    public void update(Long id, Recipe createdRecipe) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        recipe.setDescription(createdRecipe.getDescription());
        recipe.setDoctor(createdRecipe.getDoctor());
        recipe.setPatient(createdRecipe.getPatient());
        recipe.setPriority(createdRecipe.getPriority());
        recipe.setCreationDate(createdRecipe.getCreationDate());
        recipe.setValidity(createdRecipe.getValidity());
    }

    @Override
    public void save(Recipe recipe) {
        if (recipe != null) {
            recipeRepository.save(recipe);
        }
    }

    @Transactional
    @Override
    public void delete(Recipe recipe) {
        Hibernate.initialize(recipe.getPatient());
        Hibernate.initialize(recipe.getDoctor());
        recipeRepository.delete(recipe);
    }
}
