package com.toobe.dao;

import com.sun.org.apache.regexp.internal.RE;
import com.toobe.dto.Recipe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mathilde on 27/03/2016.
 */
public class RecipeDao {

    public List<Recipe> getRecipes(Connection conn){
        List<Recipe> listRecipe = new ArrayList<Recipe>();
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement("SELECT * FROM RECIPE");
            ResultSet res = stm.executeQuery();
            String name;
            Recipe rec;
            while(res.next()){
                name = res.getString("NAME");
                rec = new Recipe(name);
                listRecipe.add(0, rec);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listRecipe;    }
}
