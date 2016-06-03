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
                    "WHERE recipe.idOrigin = ro.id  AND recipe.idUser = user.id AND Recipe_Type.id = recipe.idType AND isPublic = 1 AND isValidated = 0 ";

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
    /************************************************************************************ FOOD **************************************************************************************/
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


}
