package com.tiffy.controller;

import com.tiffy.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RecipeController {

    @Autowired
    RecipeService recipeService;

    @GetMapping("/recipes")
    public String searchRecipe() {
        return "recipe/recipe-search";
    }
}
