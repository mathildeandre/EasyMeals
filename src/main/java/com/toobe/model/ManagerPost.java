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
public class ManagerPost {

    private RecipeDao recipeDao;
    private CategoryRecipeDao categoryRecipeDao;
    private OriginRecipeDao originRecipeDao;
    private FoodDao foodDao;
    private Connection conn;

    /*public List<String> insertFood(){
        startConnection();
        foodDao = new FoodDao();
        return foodDao.insertFood(conn);
    }*/

    public void startConnection(){
         Database db = new Database();
        conn = db.getConnection();

    }
}
