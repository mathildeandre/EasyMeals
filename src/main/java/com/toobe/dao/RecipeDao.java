package com.toobe.dao;

import com.mysql.jdbc.JDBC4ResultSet;
import com.mysql.jdbc.ResultSetImpl;
import com.toobe.dto.Food;
import com.toobe.dto.User;
import com.toobe.dto.info.*;
import com.toobe.dto.Ingredient;
import com.toobe.dto.Recipe;
import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mathilde on 27/03/2016.
 */
public class RecipeDao {

    private FoodDao foodDao;
    private RecipeCategoryDao recipeCategoryDao;
    private RecipeOriginDao recipeOriginDao;


    public RecipeDao() {
        foodDao = new FoodDao();
        recipeCategoryDao = new RecipeCategoryDao();
        recipeOriginDao = new RecipeOriginDao();
    }


    /**
     * Creation of a new recipe
     */
    private final static String CREATE_RECIPE = "INSERT INTO Recipe( name, idType, isPublic, idOwner, nbPerson, pixName, idOrigin, isValidated, " +
                                                    "timeCooking, timePreparation) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);\n";
    public Recipe createRecipe(Connection conn, Recipe recipe) {
        PreparedStatement stm;
        ResultSet resultRecipe;
        int isOk = 0;
        Long idRecipe = null; //PK long ?
        try {
            /* NOT ANYMORE speciality created instantanously during the creation
            //0. CHECK if RECIPE_ORIGIN exists already (otherwise it has just been created by the user : id==-1) so we create/insert it
            Long idOrigin = recipe.getOrigin().getId();
            if(idOrigin == -1){
                idOrigin = recipeOriginDao.createNewOrigin(conn, recipe.getOrigin().getName());
            }*/

            //1. INSERT into RECIPE
            stm = conn.prepareStatement(CREATE_RECIPE, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, recipe.getName());
            stm.setLong(2, recipe.getRecipeType().getIdType());
            stm.setBoolean(3, recipe.getIsPublic()); //recipe.getIsPublic()
            stm.setLong(4, recipe.getUser().getId());//recipe.getIdUser()
            //stm.setFloat(5, 0);//recipe.getRating() rating can be null
            //stm.setInt(6, 0);//recipe.getNbVoter() nbVoter default 0
            stm.setInt(5, recipe.getNbPerson());
            stm.setString(6, recipe.getPixName());
            stm.setLong(7, recipe.getOrigin().getId());
            stm.setBoolean(8, false);
            stm.setInt(9, recipe.getTimeCooking());
            stm.setInt(10, recipe.getTimePreparation());
            isOk = stm.executeUpdate();
            if (isOk == 0) {
                throw new SQLException("Creating Recipe failed, no rows affected, recipe=" + recipe.toString());
            }
            resultRecipe = stm.getGeneratedKeys();
            if (resultRecipe.next()) {
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
        return getRecipeById(conn, idRecipe);//isOk != 0;
    }


    public boolean sendImage() {
        return true;
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

            int idType;
            String name;
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
            "SELECT recipe.name as recipeName, recipe.isPublic, pixName, nbPerson, ro.id as idRo, ro.name as nameRo, ro.numRank, isValidatedRecipeOrigin, " +
                    "rating, nbVoter, isValidated, timeCooking, timePreparation, user.id as idUser, user.pseudo as pseudoUser, user.email as emailUser," +
                    "rt.id as idRecipeType, rt.name as nameRecipeType " +
                    "FROM RECIPE, Recipe_Origin ro, User, Recipe_Type rt " +
                    "WHERE recipe.idOrigin = ro.id  AND idOwner = user.id AND rt.id = recipe.idType AND recipe.id = ? ";


    public Recipe getRecipeById(Connection conn, Long idRecipe) {
        Recipe recipe = new Recipe();
        PreparedStatement stm;
        try {
            /* on fait la requete pr avoir les liste des plats en fonction de notre recipeType (: idType)*/
            stm = conn.prepareStatement(SELECT_RECIPE_By_ID);
            stm.setLong(1, idRecipe);
            ResultSet resRecipe = stm.executeQuery();

            if (resRecipe.next()) {
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
                List<RecipeCategory> categoryList = recipeCategoryDao.getCategoryRecipeList(conn, idRecipe); //new ArrayList<RecipeCategory>(); //


                /* recipeType en arg de la fct*/

                /* ingredientList, descriptionList & categoryList construitent au dessus */
                String name = resRecipe.getString("recipeName");
                boolean isPublic = resRecipe.getBoolean("isPublic");
                String pixName = resRecipe.getString("pixName");
                int nbPerson = resRecipe.getInt("nbPerson");
                float rating = resRecipe.getFloat("rating");
                int nbVoter = resRecipe.getInt("nbVoter");
                boolean isValidated = resRecipe.getBoolean("isValidated");
                int timeCooking = resRecipe.getInt("timeCooking");
                int timePreparation = resRecipe.getInt("timePreparation");

                User user = new User(resRecipe.getLong("idUser"), resRecipe.getString("pseudoUser"), resRecipe.getString("emailUser"));
                RecipeType recipeType = new RecipeType(idRecipeType, nameRecipeType);
                RecipeOrigin recipeOrigin = new RecipeOrigin(resRecipe.getLong("idRo"), resRecipe.getString("nameRo"), resRecipe.getInt("numRank"), resRecipe.getBoolean("isValidatedRecipeOrigin"));

                recipe = new Recipe(idRecipe, name, isPublic, user, pixName, recipeType, ingredientList, descriptionList,
                        recipeOrigin, categoryList, nbPerson, rating, nbVoter, timeCooking, timePreparation, isValidated, relUserRecipe);

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipe;
    }




    /****************************/
    /******* RECIPES *******/
    /****************************/


    public List<RecipeImage> getBddRecipesImages(Connection conn, Long idUser){

        List<RecipeImage> listRecipeImage = new ArrayList<RecipeImage>();
        RecipeImage recipeImage = new RecipeImage();
        PreparedStatement stm;
        try {
            /* on fait la requete pr avoir toutes les recettes pr un user (entree, plat desserts etc)*/
            stm = conn.prepareStatement(SELECT_All_RECIPES_FOR_USER_NoRecipeType);
            stm.setLong(1, idUser);
            ResultSet resRecipe = stm.executeQuery();

            Long idRecipe;
            String pixName;
            while (resRecipe.next()) {
                idRecipe = resRecipe.getLong("idRecipe");
                pixName = resRecipe.getString("pixName");

                //System.out.println(".................. start img");
                //recupere image
                InputStream is;

                File file = new File("C:\\Users\\mathilde\\workspace\\EasyMealsBack\\src\\main\\resources\\img\\"+pixName+".jpg");
                if(file.exists()){
                    //System.out.println("IMG found!!!  :) :)");
                }else{
                    //System.out.println("IMG user not found^^ :/   ---- SO WE TAKE DEFAULT IMG");
                    file = new File("C:\\Users\\mathilde\\workspace\\EasyMealsBack\\src\\main\\resources\\img\\3_photoNonDispo.jpg");
                }

                try {
                    FileInputStream imageInFile = new FileInputStream(file);

                    byte imageData[] = new byte[(int) file.length()];
                    imageInFile.read(imageData);
                    // Converting Image byte array into Base64 String
                    String imageDataString = new String(Base64.encodeBase64(imageData));
                    recipeImage = new RecipeImage(idRecipe, imageDataString);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                listRecipeImage.add(recipeImage);// = buildListRecipe(conn, resRecipe, idUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listRecipeImage;
    }

    private static final String SELECT_All_RECIPES_FOR_USER_NoRecipeType =
            "SELECT recipe.id as idRecipe, recipe.name as recipeName, recipe.isPublic, pixName, idType, recipe_type.name as nameRecipeType, nbPerson, ro.id as idRo, ro.name as nameRo, ro.numRank, isValidatedRecipeOrigin, rating, nbVoter, isValidated, timeCooking, timePreparation, user.id as idUser, user.pseudo as pseudoUser, user.email as emailUser " +
                    "FROM RECIPE, Recipe_Origin ro, User, Recipe_Type " +
                    "WHERE recipe.idOrigin = ro.id  AND recipe.idOwner = user.id AND isPublic = 1 AND Recipe_Type.id = recipe.idType " +
                    " UNION " +
                    "SELECT  recipe.id as idRecipe, recipe.name as recipeName, recipe.isPublic, pixName, idType, recipe_type.name as nameRecipeType, nbPerson, ro.id as idRo, ro.name as nameRo, ro.numRank, isValidatedRecipeOrigin, rating, nbVoter, isValidated, timeCooking, timePreparation, user.id as idUser, user.pseudo as pseudoUser, user.email as emailUser " +
                    "FROM RECIPE, Recipe_Origin ro, User, Recipe_Type " +
                    "WHERE recipe.idOrigin = ro.id  AND idOwner = user.id  AND idOwner = ? AND Recipe_Type.id = recipe.idType  ";




    //SELECT recipe.id as idRecipe, recipe.name as recipeName, recipe.isPublic, pixName, idType, recipe_type.name as nameRecipeType, nbPerson, ro.id as idRo, ro.name as nameRo, ro.numRank, isValidatedRecipeOrigin, rating, nbVoter, isValidated, timeCooking, timePreparation, user.id as idUser, user.pseudo as pseudoUser, user.email as emailUser FROM RECIPE, Recipe_Origin ro, User, Recipe_Type WHERE recipe.idOrigin = ro.id  AND recipe.idOwner = user.id AND isPublic = 1 AND Recipe_Type.id = recipe.idType
    public List<Recipe> getRecipesForUser(Connection conn, Long idUser) {
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


    public List<Recipe> buildListRecipe(Connection conn, ResultSet resRecipe, Long idUser) {
        List<Recipe> listRecipe = new ArrayList<Recipe>();
        Recipe recipe;
        try {

            Long idRecipe;
            int nbPerson, nbVoter, timeCooking, timePreparation, idRecipeType;
            float rating;
            String name, pixName, nameRecipeType;
            boolean isPublic, isValidated;
            User user;
            RecipeType recipeType;
            RecipeOrigin recipeOrigin;

            while (resRecipe.next()) {
                idRecipe = resRecipe.getLong("idRecipe");

                /* REL USER RECIPE */
                RelUserRecipe relUserRecipe = getRelUserRecipe(conn, idRecipe, idUser);
                /* INGREDIENTS */
                List<Ingredient> ingredientList = getIngredientList(conn, idRecipe); //new ArrayList<Ingredient>(); //
                /* DESCRIPTIONS */
                List<RecipeDescription> descriptionList = getDescriptionList(conn, idRecipe); //new ArrayList<String>();//
                /* CATEGORIES */
                List<RecipeCategory> categoryList = recipeCategoryDao.getCategoryRecipeList(conn, idRecipe); //new ArrayList<RecipeCategory>(); //


                /* recipeType en arg de la fct*/

                /* ingredientList, descriptionList & categoryList construitent au dessus */
                name = resRecipe.getString("recipeName");
                isPublic = resRecipe.getBoolean("isPublic");
                pixName = resRecipe.getString("pixName");
                nbPerson = resRecipe.getInt("nbPerson");
                rating = resRecipe.getFloat("rating");
                nbVoter = resRecipe.getInt("nbVoter");
                isValidated = resRecipe.getBoolean("isValidated");
                timeCooking = resRecipe.getInt("timeCooking");
                timePreparation = resRecipe.getInt("timePreparation");
                idRecipeType = resRecipe.getInt("idType");
                nameRecipeType = resRecipe.getString("nameRecipeType");

                user = new User(resRecipe.getLong("idUser"), resRecipe.getString("pseudoUser"), resRecipe.getString("emailUser"));
                recipeType = new RecipeType(idRecipeType, nameRecipeType);
                recipeOrigin = new RecipeOrigin(resRecipe.getLong("idRo"), resRecipe.getString("nameRo"), resRecipe.getInt("numRank"), resRecipe.getBoolean("isValidatedRecipeOrigin"));

                recipe = new Recipe(idRecipe, name, isPublic, user, pixName, recipeType, ingredientList, descriptionList,
                        recipeOrigin, categoryList, nbPerson, rating, nbVoter, timeCooking, timePreparation, isValidated, relUserRecipe);


                //**************************************************************************************
                //******************************   IMAGE ************************************************
                //****************************************************************************************
                InputStream is;

                File file = new File("C:\\Users\\mathilde\\workspace\\EasyMealsBack\\src\\main\\resources\\img\\"+pixName+".jpg");
                if(file.exists()){
                    //System.out.println("IMG found!!!  :) :)");
                }else{
                    //System.out.println("IMG user not found^^ :/   ---- SO WE TAKE DEFAULT IMG");
                    file = new File("C:\\Users\\mathilde\\workspace\\EasyMealsBack\\src\\main\\resources\\img\\3_photoNonDispo.jpg");
                }

                try {
                    FileInputStream imageInFile = new FileInputStream(file);

                    byte imageData[] = new byte[(int) file.length()];
                    imageInFile.read(imageData);
                    // Converting Image byte array into Base64 String
                    String imageDataString = new String(Base64.encodeBase64(imageData));
                    recipe.setImage(imageDataString);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //**************************************************************************************
                //****************************** end  IMAGE ************************************************
                //****************************************************************************************

                listRecipe.add(recipe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listRecipe;
    }


    private static final String SELECT_RelUserRecipe = "SELECT * FROM Rel_User_Recipe " +
            " WHERE idUser = ? AND idRecipe = ?";

    private RelUserRecipe getRelUserRecipe(Connection conn, Long idRecipe, Long idUser) {
        RelUserRecipe relUserRecipe = new RelUserRecipe();
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement(SELECT_RelUserRecipe);
            stm.setLong(1, idUser);
            stm.setLong(2, idRecipe);
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

    private List<Ingredient> getIngredientList(Connection conn, Long idRecipe) {

        List<Ingredient> ingredientList = new ArrayList<Ingredient>();
        Ingredient ingr;
        float qty;
        String unit;
        int idFood;
        String nameFood;
        int idCategoryFood;
        boolean isValidated;

        PreparedStatement stm;
        Food food;
        try {
            stm = conn.prepareStatement(SELECT_INGREDIENT);
            stm.setLong(1, idRecipe);
            ResultSet resIngredient = stm.executeQuery();
            while (resIngredient.next()) {
                qty = resIngredient.getFloat("quantity");
                unit = resIngredient.getString("unit");
                idFood = resIngredient.getInt("idFood");
                nameFood = resIngredient.getString("name");
                isValidated = resIngredient.getBoolean("isValidated");
                idCategoryFood = resIngredient.getInt("idCategory");
                ingr = new Ingredient(qty, unit, new Food(new Long(idFood), nameFood, idCategoryFood, isValidated));
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
    private List<RecipeDescription> getDescriptionList(Connection conn, Long idRecipe) {
        List<RecipeDescription> descriptionList = new ArrayList<RecipeDescription>();
        String descr;
        int numDescrip;
        RecipeDescription recipeDescrip;
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement("SELECT description, numDescription FROM Recipe_Description WHERE idRecipe =  ?  ORDER BY numDescription");
            stm.setLong(1, idRecipe);
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
            if (idFood == null) {
                throw new SQLException("PB : idFood is NULL -> either idFood is null from json format or,  No Row have been inserted in FOOD table for food = " + ingr.getFood().toString());
            }
            if (idFood > 0) {
                // insert ingredient
                stmIngr = conn.prepareStatement(INSERT_INGREDIENT);
                stmIngr.setLong(1, idRecipe);
                stmIngr.setLong(2, idFood);
                stmIngr.setFloat(3, ingr.getQty());
                stmIngr.setString(4, ingr.getUnit());
                insertIngrOk = stmIngr.executeUpdate();
            } else {
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

            /* NOT ANYMORE recipeCategory created instantanously during the creation
            //0. CHECK if RECIPE_CATEGORY exists already (otherwise it has just been created by the user : id==-1) so we create/insert it
            Long idCategory = category.getId();
            if(idCategory == -1){
                idCategory = recipeCategoryDao.createNewRecipeCategory(conn, category.getName(), category.getIdRecipeType());
            }*/

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


    /***************************************************************************************************************************************************************/
    /************************************************************** PUT - UPDATE data *****************************************************************************/
    /*************************************************************************************************************************************************************/

    private final static String CREATE_Rel_USER_RECIPE = "INSERT INTO Rel_User_Recipe(idRecipe, idUser) VALUES (?, ?);\n";

    public void create_RelUserRecipe(Connection conn, Long idRecipe, Long idUser) {
        PreparedStatement stm;
        int isOk = 0;
        try {

            stm = conn.prepareStatement(CREATE_Rel_USER_RECIPE);
            stm.setLong(1, idRecipe);
            stm.setLong(2, idUser);
            isOk = stm.executeUpdate();
            if (isOk == 0) {
                throw new SQLException("Creating Rel_User_Recipe failed, no rows affected");
            } else {
                System.out.println("Creation >> Rel_User_Recipe << (idRecipe: " + idRecipe + ", idUser: " + idUser + ") ------------  OK ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private final static String SELECT_Rel_USER_RECIPE = "SELECT * FROM Rel_User_Recipe WHERE idRecipe = ? AND idUser = ? ;\n";

    public boolean isExist_RelUserRecipe(Connection conn, Long idRecipe, Long idUser) {
        PreparedStatement stm;
        boolean exist = false;
        try {
            stm = conn.prepareStatement(SELECT_Rel_USER_RECIPE);
            stm.setLong(1, idRecipe);
            stm.setLong(2, idUser);
            ResultSet res = stm.executeQuery();
            if (res.next()) {
                exist = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exist;
    }


    private final static String UPDATE_isFavorite_REL_USER_RECIPE = "UPDATE Rel_User_Recipe SET isFavorite = ? WHERE idRecipe = ? AND idUser = ? ;\n";

    public void putIsFavorite(Connection conn, Long idRecipe, Long idUser, boolean isFavorite) {
        PreparedStatement stm;
        int isOk = 0;
        try {
            //1. verif que Rel_User_Recipe correspondante existe - sinon on la créée
            if (!isExist_RelUserRecipe(conn, idRecipe, idUser)) {
                create_RelUserRecipe(conn, idRecipe, idUser);
            }
            //2.update Rel_User_Recipe
            stm = conn.prepareStatement(UPDATE_isFavorite_REL_USER_RECIPE);
            stm.setBoolean(1, isFavorite);
            stm.setLong(2, idRecipe);
            stm.setLong(3, idUser);
            isOk = stm.executeUpdate();
            if (isOk == 0) {
                throw new SQLException("putIsFavorite failed, no rows affected");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private final static String UPDATE_isForPlanning_REL_USER_RECIPE = "UPDATE Rel_User_Recipe SET isForPlanning = ? WHERE idRecipe = ? AND idUser = ? ;\n";

    public void putIsForPlanning(Connection conn, Long idRecipe, Long idUser, boolean isForPlanning) {
        PreparedStatement stm;
        int isOk = 0;
        try {
            //1. verif que Rel_User_Recipe correspondante existe - sinon on la créée
            if (!isExist_RelUserRecipe(conn, idRecipe, idUser)) {
                create_RelUserRecipe(conn, idRecipe, idUser);
            }
            //2.update Rel_User_Recipe
            stm = conn.prepareStatement(UPDATE_isForPlanning_REL_USER_RECIPE);
            stm.setBoolean(1, isForPlanning);
            stm.setLong(2, idRecipe);
            stm.setLong(3, idUser);
            isOk = stm.executeUpdate();
            if (isOk == 0) {
                throw new SQLException("putIsForPlanning failed, no rows affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private final static String UPDATE_ratingUser_REL_USER_RECIPE = "UPDATE Rel_User_Recipe SET ratingUser = ? WHERE idRecipe = ? AND idUser = ? ;\n";
    private final static String SELECT_rating_RECIPE = "SELECT rating, nbVoter FROM recipe where id = ? ;\n";
    private final static String UPDATE_rating_RECIPE = "UPDATE Recipe SET rating = ?, nbVoter = ?  WHERE id = ? ;\n";

    public void putRatingUser(Connection conn, Long idRecipe, Long idUser, int ratingUser) {
        PreparedStatement stm;
        int isOk = 0;
        try {
            System.out.println("[[putRatingUser]] - idRecipe: "+idRecipe+" -- idUser: "+idUser+", rating user : "+ratingUser);
            //1. verif que Rel_User_Recipe correspondante existe - sinon on la créée
            if (!isExist_RelUserRecipe(conn, idRecipe, idUser)) {
                create_RelUserRecipe(conn, idRecipe, idUser);
            }
            //2. update Rel_User_Recipe
            stm = conn.prepareStatement(UPDATE_ratingUser_REL_USER_RECIPE);
            stm.setInt(1, ratingUser);
            stm.setLong(2, idRecipe);
            stm.setLong(3, idUser);
            isOk = stm.executeUpdate();
            if (isOk == 0) {
                throw new SQLException("putRatingUser failed, no rows affected");
            }

            //3. get rating info of RECIPE
            stm = conn.prepareStatement(SELECT_rating_RECIPE);
            stm.setLong(1, idRecipe);
            ResultSet res = stm.executeQuery();
            if (res.next()) {
                float rating = res.getFloat("rating");
                int nbVoter = res.getInt("nbVoter");
                float newRating = (float) (rating * nbVoter + ratingUser) / (nbVoter + 1);
                System.out.println("division result (avec cast (float) ) : " + (float) (rating * nbVoter + ratingUser) / (nbVoter + 1));
                System.out.println("division result : " + (rating * nbVoter + ratingUser) / (nbVoter + 1));

                //4. update RECIPE rating
                stm = conn.prepareStatement(UPDATE_rating_RECIPE);
                stm.setFloat(1, newRating);
                stm.setInt(2, nbVoter + 1);
                stm.setLong(3, idRecipe);
                isOk = stm.executeUpdate();
                if (isOk == 0) {
                    throw new SQLException("update rating RECIPE failed, no rows affected");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
