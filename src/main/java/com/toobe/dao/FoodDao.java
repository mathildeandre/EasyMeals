package com.toobe.dao;

import com.toobe.dto.Food;
import com.toobe.dto.FoodCategory;
import com.toobe.dto.Ingredient;
import com.toobe.dto.Recipe;

import java.sql.*;
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
                food = new Food(new Long(id), name, idCategory, isValidated);
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
                food = new Food(new Long(id), name, idCategory, isValidated);
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

            int id, numRank;
            String name;
            while(res.next()){
                id = res.getInt("id");
                numRank = res.getInt("numRank");
                name = res.getString("name");
                foodCategory = new FoodCategory(id, name, numRank);
                foodCategoryList.add(foodCategory);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foodCategoryList;
    }

    private final static String INSERT_FOOD = "INSERT INTO Food(name, idCategory, isValidated) VALUES (?, ?, ?)";

    public Long createFood(Connection conn, String name){
        PreparedStatement stm;
        ResultSet resultSet;
        Long newId = null;
        try {
            stm = conn.prepareStatement(INSERT_FOOD, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1,name);
            stm.setNull(2, Types.INTEGER);
            stm.setBoolean(3, false);
            stm.executeUpdate();
            resultSet = stm.getGeneratedKeys();
            if(resultSet.next()){
                newId = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newId;
    }





}
