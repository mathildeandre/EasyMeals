package com.toobe.dao;

import com.mysql.jdbc.JDBC4ResultSet;
import com.mysql.jdbc.ResultSetImpl;
import com.toobe.dto.Food;
import com.toobe.dto.User;
import com.toobe.dto.info.RecipeCategory;
import com.toobe.dto.Ingredient;
import com.toobe.dto.Recipe;
import com.toobe.dto.info.RecipeDescription;
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


    public RecipeDao() {
        foodDao = new FoodDao();
    }



    /**
     * Creation of a new recipe
     *
     * @param conn
     * @param recipe
     * @return
     */
    private final static String CREATE_RECIPE = "INSERT INTO Recipe( name, idType, isPublic, " +
            "idUser, rating, nbVoter, nbPerson, pixName, idOrigin, isValidated, timeCooking, timePreparation) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);\n";

    public boolean createRecipe(Connection conn, Recipe recipe) {
        PreparedStatement stm;
        ResultSet resultRecipe;
        int isOk = 0;
        Long idRecipe = null; //PK long ?
        try {
            //1. INSERT into RECIPE
            stm = conn.prepareStatement(CREATE_RECIPE, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, recipe.getName());
            stm.setLong(2, recipe.getRecipeType().getIdType());
            stm.setBoolean(3, recipe.getIsPublic()); //recipe.getIsPublic()
            stm.setLong(4, recipe.getUser().getId());//recipe.getIdUser()
            stm.setInt(5, 0);//recipe.getRating()
            stm.setInt(6, 0);//recipe.getNbVoter()
            stm.setInt(7, recipe.getNbPerson());
            stm.setString(8, recipe.getPixName());
            stm.setInt(9, recipe.getOrigin().getId());
            stm.setBoolean(10, false);
            stm.setInt(11, recipe.getTimeCooking());
            stm.setInt(12, recipe.getTimePreparation());
            isOk = stm.executeUpdate();
            if (isOk == 0) {
                throw new SQLException("Creating Recipe failed, no rows affected, recipe="+recipe.toString());
            }
            resultRecipe = stm.getGeneratedKeys();
            if(resultRecipe.next()){
                idRecipe = resultRecipe.getLong(1);
            }

            //2. INSERT INGREDIENTS (with insert FOOD if needed)
            List<Ingredient> listIngredient = recipe.getIngredients();
            insertsIngredient(conn, listIngredient, idRecipe);

            //3. INSERT rel CATEGORIES
            List<RecipeCategory> listCategory = recipe.getCategories();
            insertsRelRecipeCategory(conn, listCategory, idRecipe);

            //4. INSERT DESCRIPTION
            List<RecipeDescription> listDescription = recipe.getDescriptions();
            insertsRecipeDescription(conn, listDescription, idRecipe);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isOk != 0;
    }








    /****************************/
    /******* RECIPE TYPE *******/
    /****************************/
    public List<RecipeType> getRecipeTypes(Connection conn) {
        List<RecipeType> list = new ArrayList<RecipeType>();
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement("SELECT * FROM recipe_type");
            ResultSet res = stm.executeQuery();

            int idType; String name;
            while (res.next()) {
                idType = res.getInt("id");
                name = res.getString("name");
                list.add(new RecipeType(idType, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }








    /****************************/
    /******* RECIPE BY ID *******/
    /****************************/
    private static final String SELECT_RECIPE_By_ID =
            "SELECT recipe.name as recipeName, recipe.isPublic, pixName, nbPerson, ro.id as idRo, ro.name as nameRo, ro.numRank, " +
                    "rating, nbVoter, isValidated, timeCooking, timePreparation, user.id as idUser, user.pseudo as pseudoUser, user.email as emailUser," +
                    "rt.id as idRecipeType, rt.name as nameRecipeType " +
            "FROM RECIPE, Recipe_Origin ro, User, Recipe_Type rt " +
            "WHERE recipe.idOrigin = ro.id  AND recipe.idUser = user.id AND rt.id = recipe.idType AND recipe.id = ? ";



    public Recipe getRecipeById(Connection conn, int idRecipe) {
        Recipe recipe = new Recipe();
        PreparedStatement stm;
        try {
            /* on fait la requete pr avoir les liste des plats en fonction de notre recipeType (: idType)*/
            stm = conn.prepareStatement(SELECT_RECIPE_By_ID);
            stm.setInt(1, idRecipe);
            ResultSet resRecipe = stm.executeQuery();

            if(resRecipe.next()){
                Long idUser = resRecipe.getLong("idUser");
                int idRecipeType = resRecipe.getInt("idRecipeType");
                String nameRecipeType = resRecipe.getString("nameRecipeType");

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
                String name = resRecipe.getString("recipeName");
                boolean isPublic = resRecipe.getBoolean("isPublic");
                String pixName = resRecipe.getString("pixName");
                int nbPerson = resRecipe.getInt("nbPerson");
                int rating = resRecipe.getInt("rating");
                int nbVoter = resRecipe.getInt("nbVoter");
                boolean isValidated = resRecipe.getBoolean("isValidated");
                int timeCooking = resRecipe.getInt("timeCooking");
                int timePreparation = resRecipe.getInt("timePreparation");

                User user = new User(resRecipe.getLong("idUser"), resRecipe.getString("pseudoUser"), resRecipe.getString("emailUser"));
                RecipeType recipeType = new RecipeType(idRecipeType, nameRecipeType);
                RecipeOrigin recipeOrigin = new RecipeOrigin(resRecipe.getInt("idRo"), resRecipe.getString("nameRo"), resRecipe.getInt("numRank"));

                recipe = new Recipe(idRecipe, name, isPublic, user, pixName, recipeType, ingredientList, descriptionList,
                        recipeOrigin, categoryList, nbPerson, rating, nbVoter, timeCooking, timePreparation, isValidated, relUserRecipe);

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipe;
    }



    /****************************/
    /******* RECIPE PUBLIC NOT VALIDATED *******/
    /****************************/
     /* On trouve ici toutes les recettes PUBLIC de type COURSE (on verra plus tard pour ajouter celles PRIVATE des users specifiés...)*/
    private static final String SELECT_RECIPES_PUBLIC_NOT_VALIDATED =
            "SELECT recipe.id as idRecipe, recipe.name as recipeName, recipe.isPublic, pixName, idType, recipe_type.name as nameRecipeType, nbPerson, ro.id as idRo, ro.name as nameRo, ro.numRank, rating, nbVoter, isValidated, timeCooking, timePreparation, user.id as idUser, user.pseudo as pseudoUser, user.email as emailUser " +
            "FROM RECIPE, Recipe_Origin ro, User, Recipe_Type " +
            "WHERE recipe.idOrigin = ro.id  AND recipe.idUser = user.id AND Recipe_Type.id = recipe.idType AND isPublic = 1 AND isValidated = 0 ";
    public List<Recipe> getRecipesPublicNotValidated(Connection conn) {
        List<Recipe> listRecipe = new ArrayList<Recipe>();
        Recipe recipe;
        PreparedStatement stm;
        try {
            /* on fait la requete pr avoir les liste des plats en fonction de notre recipeType (mnt idType)*/
            stm = conn.prepareStatement(SELECT_RECIPES_PUBLIC_NOT_VALIDATED);
            ResultSet resRecipe = stm.executeQuery();


            listRecipe = buildListRecipe(conn, resRecipe, new Long(-1));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listRecipe;
    }





    /****************************/
    /******* RECIPES *******/
    /****************************/


    private static final String SELECT_All_RECIPES_FOR_USER_NoRecipeType =
            "SELECT recipe.id as idRecipe, recipe.name as recipeName, recipe.isPublic, pixName, idType, recipe_type.name as nameRecipeType, nbPerson, ro.id as idRo, ro.name as nameRo, ro.numRank, rating, nbVoter, isValidated, timeCooking, timePreparation, user.id as idUser, user.pseudo as pseudoUser, user.email as emailUser " +
            "FROM RECIPE, Recipe_Origin ro, User, Recipe_Type " +
            "WHERE recipe.idOrigin = ro.id  AND recipe.idUser = user.id AND isPublic = 1 AND Recipe_Type.id = recipe.idType " +
            " UNION " +
            "SELECT  recipe.id as idRecipe, recipe.name as recipeName, recipe.isPublic, pixName, idType, recipe_type.name as nameRecipeType, nbPerson, ro.id as idRo, ro.name as nameRo, ro.numRank, rating, nbVoter, isValidated, timeCooking, timePreparation, user.id as idUser, user.pseudo as pseudoUser, user.email as emailUser " +
            "FROM RECIPE, Recipe_Origin ro, User, Recipe_Type " +
            "WHERE recipe.idOrigin = ro.id  AND recipe.idUser = user.id  AND idUser = ? AND Recipe_Type.id = recipe.idType  ";
    public List<Recipe> getRecipesForUser(Connection conn, Long idUser){
        List<Recipe> listRecipe = new ArrayList<Recipe>();
        Recipe recipe;
        PreparedStatement stm;
        try {
            /* on fait la requete pr avoir toutes les recettes pr un user (entree, plat desserts etc)*/
            stm = conn.prepareStatement(SELECT_All_RECIPES_FOR_USER_NoRecipeType);
            stm.setLong(1, idUser);
            ResultSet resRecipe = stm.executeQuery();

            listRecipe = buildListRecipe(conn, resRecipe, idUser);


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
    /************************************************************************************************************************************************************/
    /************************************************************************************************************************************************************/




    private List<Recipe> buildListRecipe(Connection conn, ResultSet resRecipe, Long idUser){
        List<Recipe> listRecipe = new ArrayList<Recipe>();
        Recipe recipe;
        try{

            int idRecipe, nbPerson, rating, nbVoter, timeCooking, timePreparation, idRecipeType;
            String name, pixName, nameRecipeType;
            boolean isPublic, isValidated;
            User user;
            RecipeType recipeType;
            RecipeOrigin recipeOrigin;

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
                rating = resRecipe.getInt("rating");
                nbVoter = resRecipe.getInt("nbVoter");
                isValidated = resRecipe.getBoolean("isValidated");
                timeCooking = resRecipe.getInt("timeCooking");
                timePreparation = resRecipe.getInt("timePreparation");
                idRecipeType = resRecipe.getInt("idType");
                nameRecipeType = resRecipe.getString("nameRecipeType");

                user = new User(resRecipe.getLong("idUser"), resRecipe.getString("pseudoUser"), resRecipe.getString("emailUser"));
                recipeType = new RecipeType(idRecipeType, nameRecipeType);
                recipeOrigin = new RecipeOrigin(resRecipe.getInt("idRo"), resRecipe.getString("nameRo"), resRecipe.getInt("numRank"));

                recipe = new Recipe(idRecipe, name, isPublic, user, pixName, recipeType, ingredientList, descriptionList,
                        recipeOrigin, categoryList, nbPerson, rating, nbVoter, timeCooking, timePreparation, isValidated, relUserRecipe);

                listRecipe.add(recipe);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return listRecipe;
    }



    private static final String SELECT_RelUserRecipe = "SELECT * FROM Rel_User_Recipe " +
            " WHERE idUser = ? AND idRecipe = ?";
    private RelUserRecipe getRelUserRecipe(Connection conn, int idRecipe, Long idUser){
        RelUserRecipe relUserRecipe = new RelUserRecipe();
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement(SELECT_RelUserRecipe);
            stm.setLong(1, idUser);
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


    /* INSERT INGREDIENTS (function called by CREATION RECIPE) */
    private static final String INSERT_INGREDIENT = "INSERT INTO Ingredient(idRecipe, idFood, quantity, unit) VALUES (?, ?, ?, ?)";
    private boolean insertsIngredient(Connection conn, List<Ingredient> listIngr, Long idRecipe) throws SQLException {
        PreparedStatement stmIngr;
        int insertIngrOk = 0;
        for (Ingredient ingr : listIngr) {
            // insert new food
            Long idFood = ingr.getFood().getId();
            //idFood = -1 -> string empty
            //idFood = 0 -> new food
            //idFood > 0 -> existing food
            if (idFood == 0) {
                idFood = foodDao.createFood(conn, ingr.getFood().getName());
            }
            if(idFood == null){
                throw new SQLException("PB : idFood is NULL -> either idFood is null from json format or,  No Row have been inserted in FOOD table for food = " + ingr.getFood().toString());
            }
            if(idFood > 0){
                // insert ingredient
                stmIngr = conn.prepareStatement(INSERT_INGREDIENT);
                stmIngr.setLong(1, idRecipe);
                stmIngr.setLong(2, idFood);
                stmIngr.setInt(3, ingr.getQty());
                stmIngr.setString(4, ingr.getUnit());
                insertIngrOk = stmIngr.executeUpdate();
            }else{
                //Empty foodName -> no treatment
                //throw new SQLException("No Row have been inserted in FOOD table for food = " + ingr.getFood().toString());
            }

        }
        return insertIngrOk != 0;
    }

    /* INSERT rel CATEGORIES (function called by CREATION RECIPE) */
    private static final String INSERT_REL_RECIPE_CATEGORY = "INSERT INTO Rel_Recipe_Category(idCategory, idRecipe) VALUES (?, ?)";
    private boolean insertsRelRecipeCategory(Connection conn, List<RecipeCategory> listCategory, Long idRecipe) throws SQLException {
        PreparedStatement stmIngr;
        int insertOk = 0;
        for (RecipeCategory category : listCategory) {
            stmIngr = conn.prepareStatement(INSERT_REL_RECIPE_CATEGORY);
            stmIngr.setLong(1, category.getId());
            stmIngr.setLong(2, idRecipe);
            insertOk = stmIngr.executeUpdate();
        }
        return insertOk != 0;
    }


    /* INSERT rel DESCRIPTION (function called by CREATION RECIPE) */
    private static final String INSERT_RECIPE_DESCRIPTION = "INSERT INTO Recipe_Description(idRecipe, numDescription, description) VALUES (?, ?, ?)";
    private boolean insertsRecipeDescription(Connection conn, List<RecipeDescription> listDescription, Long idRecipe) throws SQLException {
        PreparedStatement stmIngr;
        int insertOk = 0;
        for (RecipeDescription description : listDescription) {
            stmIngr = conn.prepareStatement(INSERT_RECIPE_DESCRIPTION);
            stmIngr.setLong(1, idRecipe);
            stmIngr.setInt(2, description.getNumDescrip());
            stmIngr.setString(3, description.getName());
            insertOk = stmIngr.executeUpdate();
        }
        return insertOk != 0;
    }












    /****************************************************************************************************************************************************************/
    /***************************************************************************************************************************************************************/
    /*******************************                     USELESSS below                                ************************************************************/
    /*************************************************************************************************************************************************************/
    /************************************************************************************************************************************************************/
    /************************************************************************************************************************************************************/
    /************************************************************************************************************************************************************/

    /**
     * ::::::::::::::::::::::: ATTTTTENTION :::::::::::::::::::       FCT QUI NE FONCTIONNE PAS -> buildListRecipe n'est plus adapté pour cette fct
     * On trouve ici toutes les recettes PUBLIC de type COURSE (on verra plus tard pour ajouter celles PRIVATE des users specifiés...)
     *
    private static final String SELECT_All_RECIPES_FOR_USER = "SELECT recipe.id as idRecipe, recipe.name as recipeName, recipe.isPublic, pixName, nbPerson, ro.id as idRo, ro.name as nameRo, ro.numRank, rating, nbVoter, isValidated, timeCooking, timePreparation, user.id as idUser, user.pseudo as pseudoUser, user.email as emailUser " +
            "FROM RECIPE, Recipe_Origin ro, User " +
            "WHERE recipe.idOrigin = ro.id  AND recipe.idUser = user.id AND isPublic = 1 AND idType = ? " +
            " UNION " +
            "SELECT  recipe.id as idRecipe, recipe.name as recipeName, recipe.isPublic, pixName, nbPerson, ro.id as idRo, ro.name as nameRo, ro.numRank, rating, nbVoter, isValidated, timeCooking, timePreparation, user.id as idUser, user.pseudo as pseudoUser, user.email as emailUser " +
            "FROM RECIPE, Recipe_Origin ro, User " +
            "WHERE recipe.idOrigin = ro.id  AND recipe.idUser = user.id  AND idUser = ? AND idType = ? ";
    public List<Recipe> getRecipes(Connection conn, String nameRecipeType, int idUser) {
        List<Recipe> listRecipe = new ArrayList<Recipe>();
        Recipe recipe;
        PreparedStatement stm;
        try {
            // on recup l'id de Recipe_Type correspondant a notre recipeType (ex : course)
            stm = conn.prepareStatement("SELECT * FROM RECIPE_TYPE WHERE name = '" + nameRecipeType + "'");
            ResultSet res = stm.executeQuery();
            int idRecipeType = 1;
            if (res.next()) {
                idRecipeType = res.getInt("id");
            }
            // on fait la requete pr avoir les liste des plats en fonction de notre recipeType (: idType)*
            stm = conn.prepareStatement(SELECT_All_RECIPES_FOR_USER);
            stm.setInt(1, idRecipeType);
            stm.setInt(2, idUser);
            stm.setInt(3, idRecipeType);
            ResultSet resRecipe = stm.executeQuery();

            /******************************* ATTENTION FCT CI DESSOUS NE FONCTIONNE PAS.... *
            //listRecipe = buildListRecipe(conn, resRecipe, idRecipeType, nameRecipeType, idUser);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listRecipe;
    }
     */
}
