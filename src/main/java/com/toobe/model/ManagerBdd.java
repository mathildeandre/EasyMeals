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
public class ManagerBdd {

    private static ManagerBdd self;

    private UserDao userDao;
    private RecipeDao recipeDao;
    //private ListShoppingDao listShoppingDao;
    private PlanningDao planningDao;
    private PrivateAdminDao privateAdminDao;

    private RecipeCategoryDao recipeCategoryDao;
    private RecipeOriginDao recipeOriginDao;
    private FoodDao foodDao;
    private Connection conn;

    public static ManagerBdd getInstance(){
        if(self != null){
            return self;
        }
        self= new ManagerBdd();
        return self;
    }

    public ManagerBdd(){
        userDao = new UserDao();
        recipeDao = new RecipeDao();
        //listShoppingDao = new ListShoppingDao();
        planningDao = new PlanningDao();
        privateAdminDao = new PrivateAdminDao();
        recipeCategoryDao = new RecipeCategoryDao();
        recipeOriginDao = new RecipeOriginDao();
        foodDao = new FoodDao();
    }

    // TODO Close connection?

    // TODO add constructor to initailize all dao ?

    /*************************************************************************/
    /*************************************************************************/
    /*********************** USER ********************************************/
    /*************************************************************************/
    /*************************************************************************/
    public void updateBddColor(String colorValue, Long idUser){
        startConnection();
        userDao.updateBddColor(conn, colorValue, idUser);
    }
    /******** INSERT / GET USER **********/
    public User insertNewUser(String pseudo, String encryptedPwd){
        startConnection();
        return userDao.insertNewUser(conn, pseudo, encryptedPwd);
    }
    public User getUserByPseudo(String pseudo){
        startConnection();
        return userDao.getUserByPseudo(conn, pseudo);
    }
    public Long getIdUserByPseudo(String pseudo){
        startConnection();
        return userDao.getIdUserByPseudo(conn, pseudo);
    }
    /******** end INSERT / GET USER **********/


    /***** SECRET KEY *****/
    public void putKeyAlgo(String keyAlgo, Long idUser){
        startConnection();
        userDao.putKeyAlgo(conn, keyAlgo, idUser);
    }
    public String getKeyAlgo(Long idUser){
        startConnection();
        return userDao.getKeyAlgo(conn, idUser);
    }
    /***** end SECRET KEY *****/


    /***** ENCRYPTED PASSWORD *****/
    public void putEncryptedPwd(String encryptedPwd, Long idUser){
        startConnection();
        userDao.putEncryptedPwd(conn, encryptedPwd, idUser);
    }
    public String getEncryptedPwd(Long idUser){
        startConnection();
        return userDao.getEncryptedPwd(conn, idUser);
    }
    /***** end ENCRYPTED PASSWORD *****/

    public List<RecipeCategory> getRecipeCategories(Long idUser){
        startConnection();
        return recipeCategoryDao.getRecipeCategories(conn, idUser);
    }
    public List<RecipeOrigin> getRecipeOrigins(Long idUser){
        startConnection();
        return recipeOriginDao.getRecipeOrigins(conn, idUser);
    }
    /*************************************************************************/
    /*********************** end USER ********************************************/
    /*************************************************************************/


    /*********************************************/
    /************* RECIPE ***********************/
    /*******************************************/
    /*
    public List<Recipe> getRecipes(String recipeType, int idUser){
        startConnection();
        return recipeDao.getRecipes(conn, recipeType, idUser);
    }
    */
    public List<Recipe> getRecipesForUser(Long idUser){
        startConnection();
        return recipeDao.getRecipesForUser(conn, idUser);
    }

    public Recipe getRecipeById(Long idRecipe){
        startConnection();
        return recipeDao.getRecipeById(conn, idRecipe);
    }

    public List<RecipeType> getRecipeTypes(){
        startConnection();
        return recipeDao.getRecipeTypes(conn);
    }

    public Recipe createRecipe(Recipe recipe){
        startConnection();
        return recipeDao.createRecipe(conn,recipe);
    }

    public void putIsFavorite(Long idRecipe, Long idUser, boolean isFavorite){
        startConnection();
        recipeDao.putIsFavorite(conn,idRecipe, idUser, isFavorite);
    }
    public void putIsForPlanning(Long idRecipe, Long idUser, boolean isForPlanning){
        startConnection();
        recipeDao.putIsForPlanning(conn,idRecipe, idUser, isForPlanning);
    }
    public void putRatingUser(Long idRecipe, Long idUser, int ratingUser){
        startConnection();
        recipeDao.putRatingUser(conn,idRecipe, idUser, ratingUser);
    }
    public void putIncrNumRankCategory(Long idRecipeCategory, Long idUser){
        startConnection();
        recipeCategoryDao.putIncrNumRankCategory(conn, idRecipeCategory, idUser);
    }
    public void putIncrNumRankOrigin(Long idRecipeOrigin, Long idUser){
        startConnection();
        recipeOriginDao.putIncrNumRankOrigin(conn, idRecipeOrigin, idUser);
    }
    public Long createNewSpeciality(String recipeSpecialityName){
        startConnection();
        return recipeOriginDao.createNewOrigin(conn, recipeSpecialityName);
    }
    public Long createNewRecipeCategory(String recipeCategoryName, Long idRecipeType){
        startConnection();
        return recipeCategoryDao.createNewRecipeCategory(conn, recipeCategoryName,idRecipeType);
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
    public Planning createPlanning(int idUser){
        startConnection();
        return planningDao.createPlanning(conn, idUser);
    }
    public Planning clonePlanning(Long idPlanning) {
        startConnection();
        return planningDao.clonePlanning(conn, idPlanning);
    }
    public Planning createPlanningShopping(Long idPlanning, List<ShoppingCategory> shoppingCategories) {
        startConnection();
        return planningDao.createPlanningShopping(conn, idPlanning, shoppingCategories);
    }
    public Planning cutShoppingToPlanning(Long idPlanning) {
        startConnection();
        return planningDao.cutShoppingToPlanning(conn, idPlanning);
    }



    public void postNewRecipeCaseMeal(Long idRecipe, Long idCaseMeal){
        startConnection();
        planningDao.postNewRecipeCaseMeal(conn, idRecipe, idCaseMeal);
    }
    public void deleteOldRecipeCaseMeal(Long idRecipe, Long idCaseMeal){
        startConnection();
        planningDao.deleteOldRecipeCaseMeal(conn, idRecipe, idCaseMeal);
    }
    public void putLastOpenPlannings(Long idOldOpenPlanning, Long idNewOpenPlanning){//OLD & NEW
        startConnection();
        planningDao.putLastOpenPlannings(conn, idOldOpenPlanning, idNewOpenPlanning);
    }
    public void postNewNamePlanning(Long idPlanning, String namePlanning){//just NEW
        startConnection();
        planningDao.postNewNamePlanning(conn, idPlanning, namePlanning);
    }
    public void putLastOpenNewPlanning(Long idNewOpenPlanning){
        startConnection();
        planningDao.putLastOpenNewPlanning(conn, idNewOpenPlanning);
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
    public String getNamePlanning(Long idPlanning){
        startConnection();
        return planningDao.getNamePlanning(conn, idPlanning);
    }



    /*********************************************/
    /************* PRIVATE ADMIN ****************/
    /*******************************************/
    public List<Recipe> getRecipesPublicNotValidated(){
        startConnection();
        return privateAdminDao.getRecipesPublicNotValidated(conn);
    }
    public void putAdminValidateRecipe(Long idRecipe, boolean isPublic){
        startConnection();
        privateAdminDao.putAdminValidateRecipe(conn, idRecipe, isPublic);
    }

    /** FOOD **/
    public List<Food> getFoodsNotValidated(){
        startConnection();
        return privateAdminDao.getFoodsNotValidated(conn);
    }
    public void putAdminValidateFood(Long idFood, Long idCategory){
        startConnection();
        privateAdminDao.putAdminValidateFood(conn, idFood, idCategory);
    }
    public void putAdminValidateFoodWithNewName(String newNameFood, Long idFood, Long idCategory){
        startConnection();
        privateAdminDao.putAdminValidateFoodWithNewName(conn, newNameFood, idFood, idCategory);
    }
    public void putAdminReplaceFood(Long idExistingFood, Long idUselessFood){
        startConnection();
        privateAdminDao.putAdminReplaceFood(conn, idExistingFood, idUselessFood);
    }
    public void deleteFood(Long idFood){
        startConnection();
        privateAdminDao.deleteFood(conn, idFood);
    }

    /** CATEGORY **/
    public List<RecipeCategory> getCategoriesNotValidated(){
        startConnection();
        return privateAdminDao.getCategoriesNotValidated(conn);
    }
    public void putAdminValidateCategory(Long idCategory){
        startConnection();
        privateAdminDao.putAdminValidateCategory(conn, idCategory);
    }
    public void putAdminValidateCategoryWithNewName(String newNameCategory, Long idCategory){
        startConnection();
        privateAdminDao.putAdminValidateCategoryWithNewName(conn, newNameCategory, idCategory);
    }
    public void putAdminReplaceCategory(Long idExistingCategory, Long idUselessCategory){
        startConnection();
        privateAdminDao.putAdminReplaceCategory(conn, idExistingCategory, idUselessCategory);
    }
    public void deleteCategory(Long idCategory){
        startConnection();
        privateAdminDao.deleteCategory(conn, idCategory);
    }

    /** SPECIALITY **/
    public List<RecipeOrigin> getSpecialitiesNotValidated(){
        startConnection();
        return privateAdminDao.getSpecialitiesNotValidated(conn);
    }
    public void putAdminValidateSpeciality(Long idSpeciality){
        startConnection();
        privateAdminDao.putAdminValidateSpeciality(conn, idSpeciality);
    }
    public void putAdminValidateSpecialityWithNewName(String newNameSpeciality, Long idSpeciality){
        startConnection();
        privateAdminDao.putAdminValidateSpecialityWithNewName(conn, newNameSpeciality, idSpeciality);
    }
    public void putAdminReplaceSpeciality(Long idExistingSpeciality, Long idUselessSpeciality){
        startConnection();
        privateAdminDao.putAdminReplaceSpeciality(conn, idExistingSpeciality, idUselessSpeciality);
    }


    /*********************************************/
    /************** FOOD ************************/
    /*******************************************/
    public List<Food> getFoods(){
        startConnection();
        return foodDao.getFoods(conn);
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



    /****************************************************/
    /************* LIST SHOPPING ***********************/
    /**************************************************/
    /*
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
    public void deleteListShoppingPlanningById(Long idListShoppingPlanning){
        startConnection();
        listShoppingDao.deleteListShoppingPlanningById(conn, idListShoppingPlanning);
    }
*/




}
