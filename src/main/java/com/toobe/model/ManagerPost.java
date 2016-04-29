package com.toobe.model;

import com.toobe.dao.*;

import java.sql.Connection;

/**
 * Created by mathilde on 27/03/2016.
 */
public class ManagerPost {

    private RecipeDao recipeDao;
    private RecipeCategoryDao recipeCategoryDao;
    private RecipeOriginDao recipeOriginDao;
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
