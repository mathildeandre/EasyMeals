package com.toobe.model;

import com.toobe.dao.*;
import com.toobe.dto.CategoryRecipe;
import com.toobe.dto.OriginRecipe;
import com.toobe.dto.Recipe;

import java.sql.Connection;
import java.util.List;

/**
 * Created by mathilde on 27/03/2016.
 */
public class ManagerGet {

    private RecipeDao recipeDao;
    private CategoryRecipeDao categoryRecipeDao;
    private OriginRecipeDao originRecipeDao;
    private FoodDao foodDao;
    private Connection conn;

    public List<Recipe> getRecipes(String recipeType){
        startConnection();
        recipeDao = new RecipeDao();
        return recipeDao.getRecipes(conn, recipeType);
    }
    public List<String> getRecipeTypes(){
        startConnection();
        recipeDao = new RecipeDao();
        return recipeDao.getRecipeTypes(conn);
    }

    public List<String> getFoods(){
        startConnection();
        foodDao = new FoodDao();
        return foodDao.getFoods(conn);
    }

    public List<CategoryRecipe> getCategoriesRecipe(){
        startConnection();
        categoryRecipeDao = new CategoryRecipeDao();
        return categoryRecipeDao.getCategoriesRecipe(conn);
    }
    public List<OriginRecipe> getOriginsRecipe(){
        startConnection();
        originRecipeDao = new OriginRecipeDao();
        return originRecipeDao.getOriginsRecipe(conn);
    }


    public void startConnection(){
         Database db = new Database();
        conn = db.getConnection();

    }
}
