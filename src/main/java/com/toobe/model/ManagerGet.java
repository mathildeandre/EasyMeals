package com.toobe.model;

import com.toobe.dao.*;
import com.toobe.dto.*;

import java.sql.Connection;
import java.util.List;

/**
 * Created by mathilde on 27/03/2016.
 */
public class ManagerGet {

    private RecipeDao recipeDao;
    private PlanningDao planningDao;
    private RecipeCategoryDao recipeCategoryDao;
    private RecipeOriginDao recipeOriginDao;
    private FoodDao foodDao;
    private Connection conn;

    public List<Recipe> getRecipes(String recipeType, int idUser){
        startConnection();
        recipeDao = new RecipeDao();
        return recipeDao.getRecipes(conn, recipeType, idUser);
    }
    public Recipe getRecipeById(int idRecipe){
        startConnection();
        recipeDao = new RecipeDao();
        return recipeDao.getRecipeById(conn, idRecipe);
    }

    public List<Planning> getPlanningsOfUser(int idUser){
        startConnection();
        planningDao = new PlanningDao();
        return planningDao.getPlanningsOfUser(conn, idUser);
    }
    public Planning getPlanningById(int idPlanning){
        startConnection();
        planningDao = new PlanningDao();
        return planningDao.getPlanningById(conn, idPlanning);
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



    public List<FoodCategory> getFoodCategories(){
        startConnection();
        foodDao = new FoodDao();
        return foodDao.getFoodCategories(conn);
    }
    public List<RecipeCategory> getRecipeCategories(){
        startConnection();
        recipeCategoryDao = new RecipeCategoryDao();
        return recipeCategoryDao.getRecipeCategories(conn);
    }
    public List<RecipeOrigin> getRecipeOrigins(){
        startConnection();
        recipeOriginDao = new RecipeOriginDao();
        return recipeOriginDao.getRecipeOrigins(conn);
    }


    public void startConnection(){
         Database db = new Database();
        conn = db.getConnection();

    }
}
