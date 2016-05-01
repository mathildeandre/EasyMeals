package com.toobe.model;

import com.toobe.dao.*;
import com.toobe.dto.*;
import com.toobe.dto.info.RecipeCategory;
import com.toobe.dto.info.RecipeOrigin;

import java.sql.Connection;
import java.sql.SQLException;
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
        listShoppingDao = new ListShoppingDao();
        planningDao = new PlanningDao();
        recipeCategoryDao = new RecipeCategoryDao();
        recipeOriginDao = new RecipeOriginDao();
        foodDao = new FoodDao();
    }

    // TODO Close connection?

    // TODO add constructor to initailize all dao ?

    /*********************************************/
    /************* RECIPE ***********************/
    /*******************************************/
    public List<Recipe> getRecipes(String recipeType, int idUser){
        startConnection();
        return recipeDao.getRecipes(conn, recipeType, idUser);
    }
     public List<Recipe> getRecipesPublicNotValidated(String recipeType){
        startConnection();
        return recipeDao.getRecipesPublicNotValidated(conn, recipeType);
    }


    public Recipe getRecipeById(int idRecipe){
        startConnection();
        return recipeDao.getRecipeById(conn, idRecipe);
    }


    public List<String> getRecipeTypes(){
        startConnection();
        return recipeDao.getRecipeTypes(conn);
    }
    public List<RecipeCategory> getRecipeCategories(){
        startConnection();
        return recipeCategoryDao.getRecipeCategories(conn);
    }
    public List<RecipeOrigin> getRecipeOrigins(){
        startConnection();
        return recipeOriginDao.getRecipeOrigins(conn);
    }

    public boolean createRecipe(Recipe r){
        startConnection();
        return recipeDao.createRecipe(conn,r);
    }


    /***********************************************/
    /************* PLANNING ***********************/
    /*********************************************/
    public List<Planning> getPlanningsOfUser(int idUser){
        startConnection();
        return planningDao.getPlanningsOfUser(conn, idUser);
    }
    public Planning getPlanningById(int idPlanning){
        startConnection();
        return planningDao.getPlanningById(conn, idPlanning);
    }

    /****************************************************/
    /************* LIST SHOPPING ***********************/
    /**************************************************/
    public ListShopping getListShoppingById(int idListShopping){
        startConnection();
        return listShoppingDao.getListShoppingById(conn, idListShopping);
    }

    public List<ListShoppingPlanning> getListsShoppingPlanning(int idUser){
        startConnection();
        return listShoppingDao.getListsShoppingPlanning(conn, idUser);
    }





    /*********************************************/
    /************** FOOD ************************/
    /*******************************************/
    public List<Food> getFoods(){
        startConnection();
        return foodDao.getFoods(conn);
    }
    public List<Food> getFoodsNotValidated(){
        startConnection();
        return foodDao.getFoodsNotValidated(conn);
    }


    public List<String> getFoodsString(){
        startConnection();
        return foodDao.getFoodsString(conn);
    }
    public List<FoodCategory> getFoodCategories(){
        startConnection();
        return foodDao.getFoodCategories(conn);
    }


    private void startConnection(){
         Database db = new Database();
        conn = db.getConnection();
    }

    private void closeConnection(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
