package com.toobe.dao;

import com.toobe.dto.Ingredient;
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
public class FoodDao {

    /**
     * On trouve ici toutes les foods
     */
    public List<String> getFoods(Connection conn){
        List<String> foodList = new ArrayList<String>();
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement("SELECT name FROM FOOD");
            ResultSet res = stm.executeQuery();

            String name;
            while(res.next()){
                name = res.getString("name");
                foodList.add(name);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foodList;
    }






}
