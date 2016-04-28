package com.toobe.dao;

import com.toobe.dto.Food;
import com.toobe.dto.FoodCategory;
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


    public List<Food> getFoods(Connection conn){
        List<Food> foodList = new ArrayList<Food>();
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement("SELECT * FROM FOOD");
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
                food = new Food(id, name, idCategory, isValidated);
                foodList.add(food);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foodList;
    }
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
                food = new Food(id, name, idCategory, isValidated);
                foodList.add(food);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foodList;
    }

    public List<String> getFoodsString(Connection conn){
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




    /**
     * On trouve ici toutes les foodCategories
     */
    public List<FoodCategory> getFoodCategories(Connection conn){
        List<FoodCategory> foodCategoryList = new ArrayList<FoodCategory>();
        PreparedStatement stm;
        FoodCategory foodCategory;
        try {
            stm = conn.prepareStatement("SELECT * FROM FOOD_CATEGORY");
            ResultSet res = stm.executeQuery();

            int id, noRank;
            String name;
            while(res.next()){
                id = res.getInt("id");
                noRank = res.getInt("noRank");
                name = res.getString("name");
                foodCategory = new FoodCategory(id, name, noRank);
                foodCategoryList.add(foodCategory);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foodCategoryList;
    }





}
