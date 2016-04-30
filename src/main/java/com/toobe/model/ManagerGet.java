package com.toobe.model;

import com.toobe.dao.*;
import com.toobe.dto.*;

import java.sql.Connection;
import java.util.List;

/**
 * Created by mathilde on 27/03/2016.
 * CLASS Singleton : only one instance at a time
 */
public class ManagerGet {

    private static ManagerGet self;

    private RecipeDao recipeDao;
    private ListShoppingDao listShoppingDao;
    private PlanningDao planningDao;
    private RecipeCategoryDao recipeCategoryDao;
    private RecipeOriginDao recipeOriginDao;
    private FoodDao foodDao;
    private Connection conn;

    public static ManagerGet getInstance(){
        if(self != null){
            return self;
        }
        return new ManagerGet();
    }

    public ManagerGet(){
        recipeDao = new RecipeDao();
    }

    // TODO Close connection?

    // TODO add constructor to initailize all dao ?

    /*********************************************/
    /************* RECIPE ***********************/
    /*******************************************/
    public List<Recipe> getRecipes(String recipeType, int idUser){
        startConnection();
        recipeDao = new RecipeDao();
        return recipeDao.getRecipes(conn, recipeType, idUser);
    }
     public List<Recipe> getRecipesPublicNotValidated(String recipeType){
        startConnection();
        recipeDao = new RecipeDao();
        return recipeDao.getRecipesPublicNotValidated(conn, recipeType);
    }


    public Recipe getRecipeById(int idRecipe){
        startConnection();
        recipeDao = new RecipeDao();
        return recipeDao.getRecipeById(conn, idRecipe);
    }


    public List<String> getRecipeTypes(){
        startConnection();
        recipeDao = new RecipeDao();
        return recipeDao.getRecipeTypes(conn);
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


    /***********************************************/
    /************* PLANNING ***********************/
    /*********************************************/
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

    /****************************************************/
    /************* LIST SHOPPING ***********************/
    /**************************************************/
    public ListShopping getListShoppingById(int idListShopping){
        startConnection();
        listShoppingDao = new ListShoppingDao();
        return listShoppingDao.getListShoppingById(conn, idListShopping);
    }

    public List<ListShoppingPlanning> getListsShoppingPlanning(int idUser){
        startConnection();
        listShoppingDao = new ListShoppingDao();
        return listShoppingDao.getListsShoppingPlanning(conn, idUser);
    }





    /*********************************************/
    /************** FOOD ************************/
    /*******************************************/
    public List<Food> getFoods(){
        startConnection();
        foodDao = new FoodDao();
        return foodDao.getFoods(conn);
    }
    public List<Food> getFoodsNotValidated(){
        startConnection();
        foodDao = new FoodDao();
        return foodDao.getFoodsNotValidated(conn);
    }


    public List<String> getFoodsString(){
        startConnection();
        foodDao = new FoodDao();
        return foodDao.getFoodsString(conn);
    }
    public List<FoodCategory> getFoodCategories(){
        startConnection();
        foodDao = new FoodDao();
        return foodDao.getFoodCategories(conn);
    }


    private void startConnection(){
         Database db = new Database();
        conn = db.getConnection();

    }
}
