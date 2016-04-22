package com.toobe.dao;

import com.toobe.dto.CategoryRecipe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mathilde on 27/03/2016.
 */
public class CategoryRecipeDao {

    /**
     * On trouve ici toutes les foods
     */
    public List<CategoryRecipe> getCategoriesRecipe(Connection conn){
        List<CategoryRecipe> categoryRecipeList = new ArrayList<CategoryRecipe>();
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement("SELECT * FROM RECIPE_CATEGORY");
            ResultSet res = stm.executeQuery();

            int idCategory;
            String nameCategory;
            int noRankCategory;
            CategoryRecipe categoryRecipe;
            while(res.next()){
                idCategory = res.getInt("id");
                nameCategory = res.getString("name");
                noRankCategory = res.getInt("noRank");
                categoryRecipe = new CategoryRecipe(idCategory, nameCategory, noRankCategory);
                categoryRecipeList.add(categoryRecipe);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryRecipeList;
    }






}
