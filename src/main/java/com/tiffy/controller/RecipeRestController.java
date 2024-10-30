package com.tiffy.controller;

import com.tiffy.dto.RecipeDto;
import com.tiffy.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class RecipeRestController {

    private final RecipeService recipeService;

    public RecipeRestController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/recipes/search")
    public Map<String, Object> getRecipesByIngredients(@RequestParam String ingredients) {
        List<RecipeDto> recipes = recipeService.getRecipes(ingredients);

        Map<String, Object> response = new HashMap<>();
        response.put("recipes", recipes);
        return response;
    }
}