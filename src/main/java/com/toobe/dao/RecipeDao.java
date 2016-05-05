package com.toobe.dao;

import com.toobe.dto.Food;
import com.toobe.dto.info.RecipeCategory;
import com.toobe.dto.Ingredient;
import com.toobe.dto.Recipe;
import com.toobe.dto.RecipeDescription;
import com.toobe.dto.info.RecipeOrigin;
import com.toobe.dto.info.RecipeType;
import com.toobe.dto.info.RelUserRecipe;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mathilde on 27/03/2016.
 */
public class RecipeDao {

    private FoodDao foodDao;

    private final static String CREATE_RECIPE = "INSERT INTO Recipe(id, name, idType, isPublic, " +
            "idUser, rating, nbVoter, nbPerson, pixName, idOrigin, isValidated) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);\n";


    public RecipeDao() {
        foodDao = new FoodDao();
    }

    /****************************/
    /******* RECIPE TYPE *******/
    /****************************/
    public List<String> getRecipeTypes(Connection conn) {
        List<String> list = new ArrayList<String>();
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement("SELECT * FROM recipe_type");
            ResultSet res = stm.executeQuery();

            String name;
            while (res.next()) {
                name = res.getString("name");
                list.add(name);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /*
    private final static String SELECT_RECIPE_TYPE_BY_NAME = "SELECT id FROM RECIPE_TYPE WHERE NAME=?";

    public int getRecipeTypeByName(Connection conn, String name){
        PreparedStatement stm;
        ResultSet resultSet;
        Integer idRecipeType = null;
        try {
            stm = conn.prepareStatement(SELECT_RECIPE_TYPE_BY_NAME);
            stm.setString(1, name);
            resultSet = stm.executeQuery();
            while(resultSet.next()){
                idRecipeType = resultSet.getInt("ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idRecipeType;
    }
    */

    //TODO
    public Recipe getRecipeById(Connection conn, int idRecipe) {
        Recipe recipe = new Recipe();
        PreparedStatement stm;
        try {
            /* on fait la requete pr avoir les liste des plats en fonction de notre recipeType (mnt idType)*/
            stm = conn.prepareStatement("SELECT recipe.id as idRecipe, recipe.name as recipeName, pixName, nbPerson, recipe_origin.name as recipeOriginName, " +
                    "recipe_Type.name as recipeTypeName, rating, nbVoter  " +
                    "FROM RECIPE , Recipe_Origin, Recipe_Type " +
                    "WHERE recipe.idOrigin = recipe_origin.id AND recipe.idType = recipe_Type.id AND recipe.id = " + idRecipe);
            ResultSet resRecipe = stm.executeQuery();

            int nbPerson, rating, nbVoter;
            String name, recipeType, pixName, origin;
            boolean isFavorite, isForPlanning;

            if (resRecipe.next()) {
                /* INGREDIENTS */
                List<Ingredient> ingredientList = getIngredientList(conn, idRecipe); //new ArrayList<Ingredient>(); //
                /* DESCRIPTIONS */
                List<RecipeDescription> descriptionList = getDescriptionList(conn, idRecipe); //new ArrayList<String>();//
                /* CATEGORIES */
                List<RecipeCategory> categoryList = getCategoryRecipeList(conn, idRecipe); //new ArrayList<RecipeCategory>(); //

                /* recipeType en arg de la fct*/
                ;
                /* ingredientList, descriptionList & categoryList construitent au dessus */
                name = resRecipe.getString("recipeName");
                pixName = resRecipe.getString("pixName");
                nbPerson = resRecipe.getInt("nbPerson");
                origin = resRecipe.getString("recipeOriginName");
                recipeType = resRecipe.getString("recipeTypeName");
                isFavorite = false;
                isForPlanning = false;
                rating = resRecipe.getInt("rating");
                nbVoter = resRecipe.getInt("nbVoter");

                recipe = new Recipe();//(idRecipe, name, pixName, recipeType, nbPerson, ingredientList, descriptionList, origin, categoryList, isFavorite, isForPlanning, rating, nbVoter);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipe;
    }


    /**
     * On trouve ici toutes les recettes PUBLIC de type COURSE (on verra plus tard pour ajouter celles PRIVATE des users specifiés...)
     */
    public List<Recipe> getRecipesPublicNotValidated(Connection conn, String recipeType) {
        List<Recipe> listRecipe = new ArrayList<Recipe>();
        Recipe recipe;
        PreparedStatement stm;
        try {
            /* on recup l'id de Recipe_Type correspondant a notre recipeType (ex : course) */
            stm = conn.prepareStatement("SELECT * FROM RECIPE_TYPE WHERE name = '" + recipeType + "'");
            ResultSet res = stm.executeQuery();
            int idType = 1;
            if (res.next()) {
                idType = res.getInt("id");
            }
            /* on fait la requete pr avoir les liste des plats en fonction de notre recipeType (mnt idType)*/
            stm = conn.prepareStatement("SELECT recipe.id as idRecipe, recipe.name as recipeName, pixName, nbPerson, recipe_origin.name as recipeOriginName, rating, nbVoter " +
                    "FROM RECIPE JOIN Recipe_Origin ON recipe.idOrigin = recipe_origin.id " +
                    "WHERE isPublic = 1 AND isValidated = 0  AND idType = " + idType);
            ResultSet resRecipe = stm.executeQuery();

            int idRecipe, nbPerson, rating, nbVoter;
            String name, pixName, origin;
            boolean isFavorite, isForPlanning;

            while (resRecipe.next()) {
                idRecipe = resRecipe.getInt("idRecipe");
                ;
                /* INGREDIENTS */
                List<Ingredient> ingredientList = getIngredientList(conn, idRecipe); //new ArrayList<Ingredient>(); //
                /* DESCRIPTIONS */
                List<RecipeDescription> descriptionList = getDescriptionList(conn, idRecipe); //new ArrayList<String>();//
                /* CATEGORIES */
                List<RecipeCategory> categoryList = getCategoryRecipeList(conn, idRecipe); //new ArrayList<RecipeCategory>(); //

                /* recipeType en arg de la fct*/
                ;
                /* ingredientList, descriptionList & categoryList construitent au dessus */
                name = resRecipe.getString("recipeName");
                pixName = resRecipe.getString("pixName");
                nbPerson = resRecipe.getInt("nbPerson");
                origin = resRecipe.getString("recipeOriginName");
                isFavorite = false;
                isForPlanning = false;
                rating = resRecipe.getInt("rating");
                nbVoter = resRecipe.getInt("nbVoter");

                recipe = new Recipe();//(idRecipe, name, pixName, recipeType, nbPerson, ingredientList, descriptionList, origin, categoryList, isFavorite, isForPlanning, rating, nbVoter);
                listRecipe.add(recipe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listRecipe;
    }

    /**
     * SELECT RECIPE.id, RECIPE.name, isPublic, idUser, rating, nbVoter, nbPerson, pixName, idOrigin, RECIPE.isValidated, idType, RECIPE_TYPE.name,
     food.id, food.name, food.idCategory, foodCat.name, foodCat.numRank, food.isValidated, ingr.quantity, ingr.unit
     FROM RECIPE, RECIPE_TYPE, INGREDIENT ingr, FOOD food, FOOD_CATEGORY foodCat
     WHERE RECIPE.idType=RECIPE_TYPE.id
     AND ingr.idRecipe=RECIPE.id
     AND ingr.idFood=food.id
     AND food.idCategory=foodCat.id;
     */

    /**
     * On trouve ici toutes les recettes PUBLIC de type COURSE (on verra plus tard pour ajouter celles PRIVATE des users specifiés...)
     */
    public List<Recipe> getRecipes(Connection conn, String recipeType, int idUser) {
        List<Recipe> listRecipe = new ArrayList<Recipe>();
        Recipe recipe;
        PreparedStatement stm;
        try {
            /* on recup l'id de Recipe_Type correspondant a notre recipeType (ex : course) */
            stm = conn.prepareStatement("SELECT * FROM RECIPE_TYPE WHERE name = '" + recipeType + "'");
            ResultSet res = stm.executeQuery();
            int idType = 1;
            if (res.next()) {
                idType = res.getInt("id");
            }
            /* on fait la requete pr avoir les liste des plats en fonction de notre recipeType (: idType)*/
            stm = conn.prepareStatement("SELECT recipe.id as idRecipe, recipe.name as recipeName, recipe.isPublic, pixName, nbPerson, ro.id as idRo, ro.name as nameRo, ro.numRank, rating, nbVoter, isValidated, timeCooking, timePreparation " +
                    "FROM RECIPE JOIN Recipe_Origin ro ON recipe.idOrigin = ro.id " +
                    "WHERE isPublic = 1 AND idType = " + idType +
                    " UNION " +
                    "SELECT  recipe.id as idRecipe, recipe.name as recipeName, recipe.isPublic, pixName, nbPerson, ro.id as idRo, ro.name as nameRo, ro.numRank, rating, nbVoter, isValidated, timeCooking, timePreparation  " +
                    "FROM RECIPE JOIN Recipe_Origin ro ON recipe.idOrigin = ro.id " +
                    "WHERE idUser = " + idUser + " AND idType = " + idType);
            ResultSet resRecipe = stm.executeQuery();

            int idRecipe, nbPerson, rating, nbVoter, idOrigin, rankOrigin,timeCooking, timePreparation;
            String name, pixName, nameOrigin;
            boolean isPublic, isValidated;

            while (resRecipe.next()) {
                idRecipe = resRecipe.getInt("idRecipe");

                /* REL USER RECIPE */
                RelUserRecipe relUserRecipe = getRelUserRecipe(conn, idRecipe, idUser);
                /* INGREDIENTS */
                List<Ingredient> ingredientList = getIngredientList(conn, idRecipe); //new ArrayList<Ingredient>(); //
                /* DESCRIPTIONS */
                List<RecipeDescription> descriptionList = getDescriptionList(conn, idRecipe); //new ArrayList<String>();//
                /* CATEGORIES */
                List<RecipeCategory> categoryList = getCategoryRecipeList(conn, idRecipe); //new ArrayList<RecipeCategory>(); //


                /* recipeType en arg de la fct*/

                /* ingredientList, descriptionList & categoryList construitent au dessus */
                name = resRecipe.getString("recipeName");
                isPublic = resRecipe.getBoolean("isPublic");
                pixName = resRecipe.getString("pixName");
                nbPerson = resRecipe.getInt("nbPerson");
                idOrigin= resRecipe.getInt("idRo");
                nameOrigin = resRecipe.getString("nameRo");
                rankOrigin = resRecipe.getInt("numRank");
                rating = resRecipe.getInt("rating");
                nbVoter = resRecipe.getInt("nbVoter");
                isValidated = resRecipe.getBoolean("isValidated");
                timeCooking = resRecipe.getInt("timeCooking");
                timePreparation = resRecipe.getInt("timePreparation");

                //REcipe Type
                //
                recipe = new Recipe(idRecipe, name, isPublic, idUser, pixName, new RecipeType(idType, recipeType),
                        ingredientList, descriptionList, new RecipeOrigin(idOrigin, nameOrigin, rankOrigin), categoryList,
                        nbPerson, rating, nbVoter, timeCooking, timePreparation, isValidated, relUserRecipe);
                //(idRecipe, name, pixName, recipeType, nbPerson, ingredientList, descriptionList, origin, categoryList, isFavorite, isForPlanning, rating, nbVoter);
                listRecipe.add(recipe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listRecipe;
    }


    /****************************************************************************************************************************************************************/
    /***************************************************************************************************************************************************************/
    /********************************************************************* FCT ANNEXES UTILISE PR les fct principales *********************************************/
    /*************************************************************************************************************************************************************/
    /************************************************************************************************************************************************************/



    private static final String SELECT_RelUserRecipe = "SELECT * FROM Rel_User_Recipe " +
            " WHERE idUser = ? AND idRecipe = ?";
    private RelUserRecipe getRelUserRecipe(Connection conn, int idRecipe, int idUser){
        RelUserRecipe relUserRecipe = new RelUserRecipe();
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement(SELECT_RelUserRecipe);
            stm.setInt(1, idUser);
            stm.setInt(2, idRecipe);
            ResultSet res = stm.executeQuery();
            if (res.next()) {
                boolean isFavorite = res.getInt("isFavorite") == 1;
                boolean isForPlanning = res.getInt("isForPlanning") == 1;
                int ratingUser = res.getInt("ratingUser");
                boolean isHide = res.getInt("isHide") == 1;
                relUserRecipe = new RelUserRecipe(isFavorite, isForPlanning, ratingUser, isHide);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return relUserRecipe;
    }


    /**
     * Utilisee dans les fct qui construisent un recipe
     */
    private static final String SELECT_INGREDIENT = "SELECT idRecipe, quantity, unit, idFood, name, idCategory, isValidated " +
            "FROM INGREDIENT, FOOD WHERE INGREDIENT.idFood=FOOD.id AND ingredient.idRecipe = ?";
    private List<Ingredient> getIngredientList(Connection conn, int idRecipe) {

        List<Ingredient> ingredientList = new ArrayList<Ingredient>();
        Ingredient ingr;
        int qty;
        String unit;
        int idFood;
        String nameFood;
        int idCategoryFood;
        boolean isValidated;

        PreparedStatement stm;
        Food food;
        try {
            stm = conn.prepareStatement(SELECT_INGREDIENT);
            stm.setInt(1, idRecipe);
            ResultSet resIngredient = stm.executeQuery();
            while (resIngredient.next()) {
                qty = resIngredient.getInt("quantity");
                unit = resIngredient.getString("unit");
                idFood = resIngredient.getInt("idFood");
                nameFood = resIngredient.getString("name");
                isValidated = resIngredient.getBoolean("isValidated");
                idCategoryFood = resIngredient.getInt("idCategory");
                ingr = new Ingredient(qty, unit, new Food(new Long(idFood), nameFood, idCategoryFood,  isValidated));
                ingredientList.add(ingr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredientList;
    }

    /**
     * Utilisee dans les fct qui construisent un recipe
     */
    private List<RecipeDescription> getDescriptionList(Connection conn, int idRecipe) {
        List<RecipeDescription> descriptionList = new ArrayList<RecipeDescription>();
        String descr;
        int numDescrip;
        RecipeDescription recipeDescrip;
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement("SELECT description, numDescription FROM Recipe_Description WHERE idRecipe = " + idRecipe + " ORDER BY numDescription");
            ResultSet resDescription = stm.executeQuery();
            while (resDescription.next()) {
                descr = resDescription.getString("description");
                numDescrip = resDescription.getInt("numDescription");
                recipeDescrip = new RecipeDescription(descr, numDescrip);
                descriptionList.add(recipeDescrip);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return descriptionList;
    }


    /**
     * Utilisee dans les fct qui construisent un recipe
     */
    private List<RecipeCategory> getCategoryRecipeList(Connection conn, int idRecipe) {

        List<RecipeCategory> categoryList = new ArrayList<RecipeCategory>();
        int idCategory;
        String nameCategory;
        int numRankCategory;
        RecipeCategory recipeCategory;
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement("SELECT id, name, numRank FROM Rel_Recipe_Category as rel JOIN Recipe_Category as rc ON rel.idCategory = rc.id WHERE rel.idRecipe = " + idRecipe);
            ResultSet resCategory = stm.executeQuery();
            while (resCategory.next()) {
                idCategory = resCategory.getInt("id");
                nameCategory = resCategory.getString("name");
                numRankCategory = resCategory.getInt("numRank");
                recipeCategory = new RecipeCategory(idCategory, nameCategory, numRankCategory);
                categoryList.add(recipeCategory);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryList;
    }

    /**
     * Creation of a new recipe
     *
     * @param conn
     * @param recipe
     * @return
     */
    public boolean createRecipe(Connection conn, Recipe recipe) {
        PreparedStatement stm;
        ResultSet resultRecipe;
        int isOk = 0;
        Long idRecipe = null;
        try {
            //1. insert into recipe
            stm = conn.prepareStatement(CREATE_RECIPE, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, recipe.getName());
            stm.setInt(2, recipe.getRecipeType().getIdType());
            stm.setBoolean(3, recipe.getIsPublic());
            stm.setInt(4, recipe.getIdUser());
            stm.setInt(5, recipe.getRating());
            stm.setInt(6, recipe.getNbVoter());
            stm.setInt(7, recipe.getNbPerson());
            stm.setString(8, recipe.getPixName());
            //stm.setInt(9, recipe.getRecipeOrigin().getId());
            stm.setBoolean(10, false);
            isOk = stm.executeUpdate();
            if (isOk == 0) {
                throw new SQLException("Creating Recipe failed, no rows affected, recipe="+recipe.toString());
            }
            resultRecipe = stm.getGeneratedKeys();
            if(resultRecipe.next()){
                idRecipe = resultRecipe.getLong(1);
            }

            //2. INSERT INGREDIENTS
            List<Ingredient> listIngredients = recipe.getIngredients();
            insertIntoIngredients(conn, listIngredients, idRecipe);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isOk != 0;
    }

    private static final String INSERT_INGREDIENT = "INSERT INTO Ingredient(idRecipe, idFood, quantity, unit) VALUES (?, ?, ?, ?)";

    private boolean insertIntoIngredients(Connection conn, List<Ingredient> listIngr, Long idRecipe) throws SQLException {
        PreparedStatement stmIngr;
        int insertIngrOk = 0;
        for (Ingredient ingr : listIngr) {
            // insert new food
            Long idFood = ingr.getFood().getId();
            if (idFood == -1) {
                idFood = foodDao.createFood(conn, ingr.getFood().getName());
            }
            if (idFood == 0) {
                throw new SQLException("No Row have been inserted in FOOD table for food = " + ingr.getFood().toString());
            }
            // insert ingredient
            stmIngr = conn.prepareStatement(INSERT_INGREDIENT);
            stmIngr.setLong(1, idRecipe);
            stmIngr.setLong(2, idFood);
            stmIngr.setInt(3, ingr.getQty());
            stmIngr.setString(4, ingr.getUnit());
            insertIngrOk = stmIngr.executeUpdate();
        }
        return insertIngrOk != 0;
    }


}
