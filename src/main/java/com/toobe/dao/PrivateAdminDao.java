package com.toobe.dao;

import com.toobe.dto.Food;
import com.toobe.dto.Ingredient;
import com.toobe.dto.Recipe;
import com.toobe.dto.User;
import com.toobe.dto.info.*;
import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mathilde on 27/03/2016.
 */
public class PrivateAdminDao {

    private RecipeDao recipeDao;


    public PrivateAdminDao() {
        recipeDao = new RecipeDao();
    }



    /**************************************************************************************************************************************************************************************/
    /**************************************************************************************************************************************************************************************/
    /**************************************************************************************************************************************************************************************/
    /**************************************************************************************************************************************************************************************/
    /************************************************************************************ RECIPE **************************************************************************************/
    /**************************************************************************************************************************************************************************************/
    /**************************************************************************************************************************************************************************************/
    /**************************************************************************************************************************************************************************************/
    /**************************************************************************************************************************************************************************************/
    /****************************/
    /******* RECIPE PUBLIC NOT VALIDATED *******/
    /****************************/
     /* On trouve ici toutes les recettes PUBLIC de type COURSE (on verra plus tard pour ajouter celles PRIVATE des users specifiés...)*/
    private static final String SELECT_RECIPES_PUBLIC_NOT_VALIDATED =
            "SELECT recipe.id as idRecipe, recipe.name as recipeName, recipe.isPublic, pixName, idType, recipe_type.name as nameRecipeType, nbPerson, ro.id as idRo, ro.name as nameRo, ro.numRank, rating, nbVoter, isValidated, timeCooking, timePreparation, user.id as idUser, user.pseudo as pseudoUser, user.email as emailUser " +
                    "FROM RECIPE, Recipe_Origin ro, User, Recipe_Type " +
                    "WHERE recipe.idOrigin = ro.id  AND recipe.idOwner = user.id AND Recipe_Type.id = recipe.idType AND isPublic = 1 AND isValidated = 0 ";

    public List<Recipe> getRecipesPublicNotValidated(Connection conn) {
        List<Recipe> listRecipe = new ArrayList<Recipe>();
        Recipe recipe;
        PreparedStatement stm;
        try {
            /* on fait la requete pr avoir les liste des plats en fonction de notre recipeType (mnt idType)*/
            stm = conn.prepareStatement(SELECT_RECIPES_PUBLIC_NOT_VALIDATED);
            ResultSet resRecipe = stm.executeQuery();

            listRecipe = recipeDao.buildListRecipe(conn, resRecipe, new Long(-1));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listRecipe;
    }

    private final static String UPDATE_ADMIN_ValidateRecipe = "UPDATE Recipe SET isValidated = true, isPublic = ? WHERE id = ?;\n";
    public void putAdminValidateRecipe(Connection conn, Long idRecipe, boolean isPublic){
        PreparedStatement stm;
        int isOk = 0;
        try {
            stm = conn.prepareStatement(UPDATE_ADMIN_ValidateRecipe);
            stm.setBoolean(1, isPublic);
            stm.setLong(2, idRecipe);
            isOk = stm.executeUpdate();
            if (isOk == 0) {
                throw new SQLException("putAdminValidateRecipe failed, no rows affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**************************************************************************************************************************************************************************************/
    /****************************************************************************** end RECIPE **************************************************************************************/
    /**************************************************************************************************************************************************************************************/






    /**************************************************************************************************************************************************************************************/
    /**************************************************************************************************************************************************************************************/
    /**************************************************************************************************************************************************************************************/
    /**************************************************************************************************************************************************************************************/
    /************************************************************************************ FOOD *******************************************************************************************/
    /**************************************************************************************************************************************************************************************/
    /**************************************************************************************************************************************************************************************/
    /**************************************************************************************************************************************************************************************/
    /**************************************************************************************************************************************************************************************/
    public List<Food> getFoodsNotValidated(Connection conn){
        List<Food> foodList = new ArrayList<Food>();
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement("SELECT * FROM FOOD WHERE isValidated = 0");
            ResultSet res = stm.executeQuery();

            String name;
            int id, idCategory;
            boolean isValidated;
            Food food;
            while(res.next()){
                id = res.getInt("id");
                name = res.getString("name");
                idCategory = res.getInt("idCategory");
                isValidated = res.getInt("isValidated") == 1;
                food = new Food(new Long(id), name, idCategory, isValidated);
                foodList.add(food);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foodList;
    }


    /* VALIDE FOOD : On valide la new food en lui affectant une category*/
    private final static String UPDTE_ADMIN_ValidateFood = "UPDATE Food SET isValidated = true, idCategory = ? WHERE id = ?;\n";
    public void putAdminValidateFood(Connection conn, Long idFood, Long idCategory){
        PreparedStatement stm;
        int isOk = 0;
        try {
            stm = conn.prepareStatement(UPDTE_ADMIN_ValidateFood);
            stm.setLong(1, idCategory);
            stm.setLong(2, idFood);
            isOk = stm.executeUpdate();
            if (isOk == 0) {
                throw new SQLException("putAdminValidateFood failed, no rows affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /* VALIDE FOOD with newName : on valide food en lui affectant une category et avec un nouveau nom  */
    private final static String UPDTE_ADMIN_ValidateFoodWithNewName = "UPDATE Food SET name = ?, isValidated = true, idCategory = ? WHERE id = ?;\n";
    public void putAdminValidateFoodWithNewName(Connection conn, String newNameFood, Long idFood, Long idCategory){
        PreparedStatement stm;
        int isOk = 0;
        try {
            stm = conn.prepareStatement(UPDTE_ADMIN_ValidateFoodWithNewName);
            stm.setString(1, newNameFood);
            stm.setLong(2, idCategory);
            stm.setLong(3, idFood);
            isOk = stm.executeUpdate();
            if (isOk == 0) {
                throw new SQLException("putAdminValidateFoodWithNewName failed, no rows affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /* REMPLACE FOOD : par une deja existante qui etait la mm chose (on remplacera ds la relation ingredient et supprimera celle creee par le user */
    private final static String UPDTE_ADMIN_ReplaceFoodInto_Ingredient = "UPDATE Ingredient SET idFood = ? WHERE idFood = ?;\n";
    private final static String UPDTE_ADMIN_ReplaceFoodInto_IngredientCustom = "UPDATE Ingredient_Custom SET idFood = ? WHERE idFood = ?;\n";
    public void putAdminReplaceFood(Connection conn, Long idExistingFood, Long idUselessFood){
        PreparedStatement stm;
        int isOk = 0;
        try {
            //1. replace useless by existing into INGREDIENT
            stm = conn.prepareStatement(UPDTE_ADMIN_ReplaceFoodInto_Ingredient);
            stm.setLong(1, idExistingFood);
            stm.setLong(2, idUselessFood);
            isOk = stm.executeUpdate();
            System.out.println("putAdminValidateFood -ingr- : "+isOk+" row affected...");

            //2. replace  into INGREDIENT CUSTOM
            stm = conn.prepareStatement(UPDTE_ADMIN_ReplaceFoodInto_IngredientCustom);
            stm.setLong(1, idExistingFood);
            stm.setLong(2, idUselessFood);
            isOk = stm.executeUpdate();
            System.out.println("putAdminValidateFood -ingr custom- : "+isOk+" row affected...");

            //3. delete useless from food
            deleteFood(conn, idUselessFood);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /* FOOD REFUSEE : on accepte pas la food, ca la supprimera ainsi que la relation ingredient */
    private final static String DELETE_ADMIN_Food = "DELETE FROM Food  WHERE id = ?;\n";
    public void deleteFood(Connection conn, Long idFood){
        PreparedStatement stm;
        int isOk = 0;
        try {
            stm = conn.prepareStatement(DELETE_ADMIN_Food);
            stm.setLong(1, idFood);
            isOk = stm.executeUpdate();
            if (isOk == 0) {
                throw new SQLException("deleteFood failed, no rows affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**************************************************************************************************************************************************************************************/
    /****************************************************************************** end FOOD **************************************************************************************/
    /**************************************************************************************************************************************************************************************/





    /**************************************************************************************************************************************************************************************/
    /**************************************************************************************************************************************************************************************/
    /************************************************************************************ CATEGORY **************************************************************************************/
    /**************************************************************************************************************************************************************************************/
    /**************************************************************************************************************************************************************************************/
    public List<RecipeCategory> getCategoriesNotValidated(Connection conn){
        List<RecipeCategory> categoryList = new ArrayList<RecipeCategory>();
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement("SELECT * FROM Recipe_Category WHERE isValidatedRecipeCategory = 0");
            ResultSet res = stm.executeQuery();

            Long id;
            String name;
            int numRank, idRecipeType;
            boolean isValidated;
            RecipeCategory category;
            while(res.next()){
                id = res.getLong("id");
                name = res.getString("name");
                numRank = res.getInt("numRank");
                idRecipeType = res.getInt("idRecipeType");
                category = new RecipeCategory(id, name, numRank, idRecipeType);
                categoryList.add(category);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryList;
    }

    private final static String UPDTE_ADMIN_ValidateCategory = "UPDATE Recipe_Category SET isValidatedRecipeCategory = true  WHERE id = ?;\n";
    public void putAdminValidateCategory(Connection conn, Long idCategory){
        PreparedStatement stm;
        int isOk = 0;
        try {
            stm = conn.prepareStatement(UPDTE_ADMIN_ValidateCategory);
            stm.setLong(1, idCategory);
            isOk = stm.executeUpdate();
            if (isOk == 0) {
                throw new SQLException("putAdminValidateCategory failed, no rows affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private final static String UPDTE_ADMIN_ValidateCategoryWithNewName = "UPDATE Recipe_Category SET name = ?, isValidatedRecipeCategory = true WHERE id = ?;\n";
    public void putAdminValidateCategoryWithNewName(Connection conn, String newNameCategory, Long idCategory){
        PreparedStatement stm;
        int isOk = 0;
        try {
            stm = conn.prepareStatement(UPDTE_ADMIN_ValidateCategoryWithNewName);
            stm.setString(1, newNameCategory);
            stm.setLong(2, idCategory);
            isOk = stm.executeUpdate();
            if (isOk == 0) {
                throw new SQLException("putAdminValidateCategoryWithNewName failed, no rows affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void putAdminReplaceCategory(Connection conn, Long idExistingCategory, Long idUselessCategory){
        //1. replace useless by existing into REL_RECIPE_Category
        replaceCategoryIntoRel_Recipe_Category(conn, idExistingCategory, idUselessCategory);

        //2. NO replace into REL_USER_RecipeCategory - no sense !

        //3. delete useless from category
        deleteCategory(conn, idUselessCategory);
    }

    private final static String SELECT_uselessCategoryRelRecipe = "SELECT * FROM Rel_Recipe_Category WHERE idCategory = ?;\n";
    private final static String SELECT_existingCategoryRelRecipe = "SELECT * FROM Rel_Recipe_Category WHERE idCategory = ? AND idRecipe = ?;\n";
    private final static String INSERT_newRel_Recipe_Category = "INSERT INTO Rel_Recipe_Category (idCategory, idRecipe) VALUES (?, ?);\n";
    /*Il ne s'agit pas dun replace en realite car si on remplacer un couple useless/recipe par existing/recipe, alr que le couple existing/recipe existe deja : violation constraint!
     donc uniquement si pour un couple useless/recipe, le couple existing/recipe n'existe pas encore on crée existing/useless! (le couple useless/recipe disparaitra lors du deleteCategory (useless)  */
    public void replaceCategoryIntoRel_Recipe_Category(Connection conn, Long idExistingCategory, Long idUselessCategory){
        PreparedStatement stm, stm2, stm3;
        int isOk = 0;
        try {
            //1. on select tous les couples avec idCategory = idUselessCategory
            stm = conn.prepareStatement(SELECT_uselessCategoryRelRecipe);
            stm.setLong(1, idUselessCategory);
            ResultSet res = stm.executeQuery();
            Long idRecipe;
            while (res.next()) {
                //2. pour chacun de ces couples :
                // on recup la recette couplé afin de voir si le couple entre cette recette et idExistingCategory exist
                idRecipe = res.getLong("idRecipe");
                stm2 = conn.prepareStatement(SELECT_existingCategoryRelRecipe);
                stm2.setLong(1, idExistingCategory);
                stm2.setLong(2, idRecipe);
                ResultSet res2 = stm2.executeQuery();
                if(!res2.next()){
                    //3. si le couple exist pas alors on le créé
                    stm3 = conn.prepareStatement(INSERT_newRel_Recipe_Category);
                    stm3.setLong(1, idExistingCategory);
                    stm3.setLong(2, idRecipe);
                    isOk = stm3.executeUpdate();
                    if (isOk == 0) {
                        throw new SQLException("replaceCategoryIntoRel_Recipe_Category failed, no rows affected");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private final static String DELETE_ADMIN_Category = "DELETE FROM Recipe_Category  WHERE id = ?;\n";
    public void deleteCategory(Connection conn, Long idCategory){
        PreparedStatement stm;
        int isOk = 0;
        try {
            stm = conn.prepareStatement(DELETE_ADMIN_Category);
            stm.setLong(1, idCategory);
            isOk = stm.executeUpdate();
            if (isOk == 0) {
                throw new SQLException("deleteCategory failed, no rows affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**************************************************************************************************************************************************************************************/
    /****************************************************************************** end CATEGORY **************************************************************************************/
    /**************************************************************************************************************************************************************************************/

}
