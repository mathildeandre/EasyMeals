package com.toobe.dao;

import com.toobe.dto.info.RecipeOrigin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mathilde on 27/03/2016.
 */
public class RecipeOriginDao {

    /**
     * On trouve ici toutes les foods
     */
    public List<RecipeOrigin> getRecipeOrigins(Connection conn){
        List<RecipeOrigin> recipeOriginList = new ArrayList<RecipeOrigin>();
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement("SELECT * FROM recipe_origin");
            ResultSet res = stm.executeQuery();

            int id;
            String name;
            int noRank;
            RecipeOrigin recipeOrigin;
            while(res.next()){
                id = res.getInt("id");
                name = res.getString("name");
                noRank = res.getInt("noRank");
                recipeOrigin = new RecipeOrigin(id, name, noRank);
                recipeOriginList.add(recipeOrigin);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipeOriginList;
    }

}
