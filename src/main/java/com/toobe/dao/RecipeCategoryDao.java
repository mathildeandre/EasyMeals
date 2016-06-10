package com.toobe.dao;

import com.toobe.dto.info.RecipeCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mathilde on 27/03/2016.
 */
public class RecipeCategoryDao {

    private static final String INSERT_RecipeCategory = "INSERT INTO Recipe_Category(name, numRank, idRecipeType) VALUES (?, ?, ?)";
    public Long createNewRecipeCategory(Connection conn, String recipeCategoryName, Long idRecipeType) {
        PreparedStatement stm;
        ResultSet result;
        Long idRecipeCategory = null;
        try {
            stm = conn.prepareStatement(INSERT_RecipeCategory, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, recipeCategoryName);
            stm.setInt(2, 1);
            stm.setLong(3, idRecipeType);
            stm.executeUpdate();

            result = stm.getGeneratedKeys();
            if (result.next()) {
                idRecipeCategory = result.getLong(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idRecipeCategory;
    }




    /**
     * On trouve ici toutes les categories utiles pour les filtres (on veut donc les numRank en fonction de chaque utilsateur)
     */
    public List<RecipeCategory> getRecipeCategories(Connection conn, Long idUser){
        List<RecipeCategory> recipeCategoryList = new ArrayList<RecipeCategory>();
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement("SELECT * FROM RECIPE_CATEGORY");
            ResultSet res = stm.executeQuery();

            Long idCategory;
            int idRecipeType, numRankCategory;
            String nameCategory;
            RecipeCategory recipeCategory;
            while(res.next()){
                idCategory = res.getLong("id");
                nameCategory = res.getString("name");
                numRankCategory = getNumRank_RelUserRecipeCategory(conn, idCategory, idUser);
                if(numRankCategory == -1){
                    numRankCategory = res.getInt("numRank");
                }
                idRecipeType = res.getInt("idRecipeType");
                recipeCategory = new RecipeCategory(idCategory, nameCategory, numRankCategory, idRecipeType);
                recipeCategoryList.add(recipeCategory);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipeCategoryList;
    }


    /**
     * Utilisee dans les fct qui construisent un recipe
     * Permet de trouver toutes les recipeCategory pour UNE recette donnée
     */
    public List<RecipeCategory> getCategoryRecipeList(Connection conn, Long idRecipe) {

        List<RecipeCategory> categoryList = new ArrayList<RecipeCategory>();
        Long idCategory;
        String nameCategory;
        int idRecipeType, numRankCategory;
        RecipeCategory recipeCategory;
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement("SELECT * FROM Rel_Recipe_Category, Recipe_Category  WHERE idCategory = Recipe_Category.id AND idRecipe = ?");
            stm.setLong(1, idRecipe);
            ResultSet resCategory = stm.executeQuery();
            while (resCategory.next()) {
                idCategory = resCategory.getLong("id");
                nameCategory = resCategory.getString("name");
                numRankCategory = resCategory.getInt("numRank");
                idRecipeType = resCategory.getInt("idRecipeType");
                recipeCategory = new RecipeCategory(idCategory, nameCategory, numRankCategory, idRecipeType);
                categoryList.add(recipeCategory);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryList;
    }

    private final static String UPDATE_incrNumRank_REL_USER_RecipeCATEGORY = "UPDATE Rel_User_RecipeCategory SET numRankRel = ? WHERE idRecipeCategory = ? AND idUser = ? ;\n";
    public void putIncrNumRankCategory(Connection conn, Long idRecipeCategory, Long idUser){
        PreparedStatement stm;
        int isOk = 0;
        try {
            //1. select numRank from Rel_User_RecipeCategory
            int numRank = getNumRank_RelUserRecipeCategory(conn, idRecipeCategory, idUser);
            //2.1 si numRank == -1 c'est que Rel_User_RecipeCategory existe pas : on la créée
            if (numRank == -1) {
                insertRelUserRecipeCategory(conn, idRecipeCategory, idUser, 10);
            }
            //2.2. otherwise we update-increment numRank of the relation
            else{
                stm = conn.prepareStatement(UPDATE_incrNumRank_REL_USER_RecipeCATEGORY);
                stm.setInt(1, numRank+1);
                stm.setLong(2, idRecipeCategory);
                stm.setLong(3, idUser);
                isOk = stm.executeUpdate();
                if (isOk == 0) {
                    throw new SQLException("putIncrNumRankCategory failed, no rows affected");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    private final static String SELECT_Rel_USER_RECIPECATEGORY = "SELECT * FROM Rel_User_RecipeCategory WHERE idRecipeCategory = ? AND idUser = ? ;\n";
    public int getNumRank_RelUserRecipeCategory(Connection conn, Long idRecipeCategory, Long idUser) {
        PreparedStatement stm;
        int numRank = -1;
        try {
            stm = conn.prepareStatement(SELECT_Rel_USER_RECIPECATEGORY);
            stm.setLong(1, idRecipeCategory);
            stm.setLong(2, idUser);
            ResultSet res = stm.executeQuery();
            if (res.next()) {
                numRank = res.getInt("numRankRel");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numRank;
    }

    private static final String INSERT_REL_RecipeCategory_User = "INSERT INTO REL_User_RecipeCategory(idRecipeCategory, idUser, numRankRel) VALUES (?, ?, ?)";
    private void insertRelUserRecipeCategory(Connection conn, Long idRecipeCategory, Long idUser, int numRank) {
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement(INSERT_REL_RecipeCategory_User);
            stm.setLong(1, idRecipeCategory);
            stm.setLong(2, idUser);
            stm.setInt(3, numRank);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
