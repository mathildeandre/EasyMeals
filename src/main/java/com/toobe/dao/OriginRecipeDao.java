package com.toobe.dao;

import com.toobe.dto.OriginRecipe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mathilde on 27/03/2016.
 */
public class OriginRecipeDao {

    /**
     * On trouve ici toutes les foods
     */
    public List<OriginRecipe> getOriginsRecipe(Connection conn){
        List<OriginRecipe> originRecipeList = new ArrayList<OriginRecipe>();
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement("SELECT * FROM recipe_origin");
            ResultSet res = stm.executeQuery();

            int id;
            String name;
            int noRank;
            OriginRecipe originRecipe;
            while(res.next()){
                id = res.getInt("id");
                name = res.getString("name");
                noRank = res.getInt("noRank");
                originRecipe = new OriginRecipe(id, name, noRank);
                originRecipeList.add(originRecipe);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return originRecipeList;
    }






}
