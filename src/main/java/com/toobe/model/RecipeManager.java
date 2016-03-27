package com.toobe.model;

import com.toobe.dao.Database;
import com.toobe.dao.RecipeDao;
import com.toobe.dto.Recipe;

import java.sql.Connection;
import java.util.List;

/**
 * Created by mathilde on 27/03/2016.
 */
public class RecipeManager {

    private RecipeDao recipeDao;
    private Connection conn;


    public List<Recipe> getRecipes(){
        startConnection();
        recipeDao = new RecipeDao();
        return recipeDao.getRecipes(conn);

    }

    public void startConnection(){
         Database db = new Database();
        conn = db.getConnection();

    }
}
