package com.toobe.dao;

import com.toobe.dto.info.RecipeCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mathilde on 27/03/2016.
 */
public class RecipeCategoryDao {

    /**
     * On trouve ici toutes les foods
     */
    public List<RecipeCategory> getRecipeCategories(Connection conn){
        List<RecipeCategory> recipeCategoryList = new ArrayList<RecipeCategory>();
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement("SELECT * FROM RECIPE_CATEGORY");
            ResultSet res = stm.executeQuery();

            int idCategory;
            String nameCategory;
            int noRankCategory;
            RecipeCategory recipeCategory;
            while(res.next()){
                idCategory = res.getInt("id");
                nameCategory = res.getString("name");
                noRankCategory = res.getInt("noRank");
                recipeCategory = new RecipeCategory(idCategory, nameCategory, noRankCategory);
                recipeCategoryList.add(recipeCategory);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipeCategoryList;
    }






}
