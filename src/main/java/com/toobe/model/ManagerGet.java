package com.toobe.model;

import com.toobe.dao.*;
import com.toobe.dto.*;
import com.toobe.dto.info.RecipeCategory;
import com.toobe.dto.info.RecipeOrigin;
import com.toobe.dto.info.RecipeType;

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


    public List<RecipeType> getRecipeTypes(){
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

    public boolean createRecipe(Recipe recipe){
        startConnection();
        return recipeDao.createRecipe(conn,recipe);
    }


    /***********************************************/
    /************* PLANNING ***********************/
    /*********************************************/
    public List<Planning> getPlanningsOfUser(int idUser){
        startConnection();
        return planningDao.getPlanningsOfUser(conn, idUser);
    }
    public Planning getPlanningById(Long idPlanning){
        startConnection();
        return planningDao.getPlanningById(conn, idPlanning);
    }
    /* NO USE
    public Planning getPlanningCurrentOfUser(int idUser){
        startConnection();
        return planningDao.getPlanningCurrentOfUser(conn, idUser);
    }
     */
    public Planning createPlanning(int idUser){
        startConnection();
        return planningDao.createPlanning(conn, idUser);
    }

    public void postNewRecipeCaseMeal(Long idRecipe, Long idCaseMeal){
        startConnection();
        planningDao.postNewRecipeCaseMeal(conn, idRecipe, idCaseMeal);
    }
    public void deleteOldRecipeCaseMeal(Long idRecipe, Long idCaseMeal){
        startConnection();
        planningDao.deleteOldRecipeCaseMeal(conn, idRecipe, idCaseMeal);
    }
    public void postNewNamePlanning(Long idPlanning, String namePlanning){
        startConnection();
        planningDao.postNewNamePlanning(conn, idPlanning, namePlanning);
    }
    public void putLastOpenPlanning(Long idOldOpenPlanning, Long idNewOpenPlanning){
        startConnection();
        planningDao.putLastOpenPlanning(conn, idOldOpenPlanning, idNewOpenPlanning);
    }
    public void putShowWeekMeal(Long idWeekMeal, Boolean showWeekMeal) {
        startConnection();
        planningDao.putShowWeekMeal(conn, idWeekMeal, showWeekMeal);
    }
    public void putNbPersCaseMeal(Long idCaseMeal, int nbPersCaseMeal) {
        startConnection();
        planningDao.putNbPersCaseMeal(conn, idCaseMeal, nbPersCaseMeal);
    }
    public void putNbPersGlobalPlanning(Long idPlanning, int nbPersGlobal) {
        startConnection();
        planningDao.putNbPersGlobalPlanning(conn, idPlanning, nbPersGlobal);
    }
    public void deletePlanningById(Long idPlanning) {
        startConnection();
        planningDao.deletePlanningById(conn, idPlanning);
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

    public ListShoppingPlanning createListShoppingPlanning(Long idPlanning, int idUser, List<ListShoppingCategory> listShoppingCategories){
        startConnection();
        return listShoppingDao.createListShoppingPlanning(conn, idPlanning, idUser, listShoppingCategories);
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
