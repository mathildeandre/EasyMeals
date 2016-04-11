package com.toobe.dao;

import com.sun.org.apache.regexp.internal.RE;
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
public class RecipeDao {

    public List<Recipe> getRecipes(Connection conn){
        List<Recipe> listRecipe = new ArrayList<Recipe>();
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement("SELECT * FROM RECIPE");
            ResultSet res = stm.executeQuery();
            String name;
            String email;
            Recipe rec;
            int i=1;
            while(res.next()){

                Ingredient ingr1 = new Ingredient(500, "g", "steak");
                Ingredient ingr2 = new Ingredient(50, "cl", "lait");
                Ingredient ingr3 = new Ingredient(4, "", "carottes");
                List<Ingredient> ingredients = new ArrayList<Ingredient>();
                ingredients.add(ingr1);
                ingredients.add(ingr2);
                ingredients.add(ingr3);

                Recipe recipe = new Recipe(i, "ma recette "+i, 18, ingredients, "voici une recette de ouuuf");

                listRecipe.add(recipe);

                i++;

                /*

                name = res.getString("NAME");
                //email =  res.getString("EMAIL");
                rec = new Recipe(name);
                rec.setBlabla(name);
                rec.setFood(name);
                //rec.setEmail(email);
                listRecipe.add(0, rec);
                */
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listRecipe;    }

}
