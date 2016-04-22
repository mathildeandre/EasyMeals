package com.toobe.dao;

import com.sun.org.apache.regexp.internal.RE;
import com.toobe.dto.CategoryRecipe;
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

    public List<String> getRecipeTypes(Connection conn){
        List<String> list = new ArrayList<String>();
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement("SELECT * FROM recipe_type");
            ResultSet res = stm.executeQuery();

            String name;
            while(res.next()){
                name = res.getString("name");
                list.add(name);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }



    /**
     * On trouve ici toutes les recettes PUBLIC de type COURSE (on verra plus tard pour ajouter celles PRIVATE des users specifiés...)
     */
    public List<Recipe> getRecipes(Connection conn, String recipeType){
        List<Recipe> listRecipe = new ArrayList<Recipe>();
        Recipe recipe;
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement("SELECT * FROM RECIPE_TYPE WHERE name = '"+recipeType+"'");
            ResultSet res = stm.executeQuery();

            int idType = 1;
            if(res.next()){
                idType = res.getInt("id");
            }

            stm = conn.prepareStatement("SELECT * FROM RECIPE JOIN Recipe_Origin ON recipe.idOrigin = recipe_origin.id WHERE idType = "+idType);
            ResultSet resRecipe = stm.executeQuery();


            int idRecipe;
            String name;
            String pixName;
            int nbPerson;
            boolean descriptionOpen;
            String origin;
            boolean isFavorite;
            boolean isForPlanning;
            int rating;
            int nbVoter;

            List<Ingredient> ingredientList;
            Ingredient ingr;
            int qty;
            String unit;
            String food;
            int idCategoryIngr;

            List<String> descriptionList;
            String descr;

            List<CategoryRecipe> categoryList;
            int idCategory;
            String nameCategory;
            int noRankCategory;
            CategoryRecipe categoryRecipe;


            while(resRecipe.next()){

                idRecipe = resRecipe.getInt("id"); ;

                /* INGREDIENTS */
                stm = conn.prepareStatement("SELECT quantity, unit, name, idCategory FROM INGREDIENT JOIN FOOD ON ingredient.idFood = food.id WHERE ingredient.idRecipe = "+idRecipe);
                ResultSet resIngredient = stm.executeQuery();
                ingredientList = new ArrayList<Ingredient>();
                while(resIngredient.next()){
                    qty = resIngredient.getInt("quantity");
                    unit = resIngredient.getString("unit");
                    food = resIngredient.getString("name");
                    idCategoryIngr = resIngredient.getInt("idCategory");
                    ingr = new Ingredient(qty, unit, food, idCategoryIngr);
                    ingredientList.add(ingr);
                }

                /* DESCRIPTIONS */
                stm = conn.prepareStatement("SELECT description FROM Recipe_Description WHERE idRecipe = "+idRecipe+" ORDER BY noDescription");
                ResultSet resDescription = stm.executeQuery();
                descriptionList = new ArrayList<String>();
                while(resDescription.next()){
                    descr = resDescription.getString("description");
                    descriptionList.add(descr);
                }
                /* CATEGORIES */
                stm = conn.prepareStatement("SELECT id, name, noRank FROM Rel_Recipe_Category as rel JOIN Recipe_Category as rc ON rel.idCategory = rc.id WHERE rel.idRecipe = "+idRecipe);
                ResultSet resCategory = stm.executeQuery();
                categoryList = new ArrayList<CategoryRecipe>();
                while(resCategory.next()){
                    idCategory = resCategory.getInt("id");
                    nameCategory = resCategory.getString("name");
                    noRankCategory = resCategory.getInt("noRank");
                    categoryRecipe = new CategoryRecipe(idCategory, nameCategory, noRankCategory);
                    categoryList.add(categoryRecipe);
                }



                /* recipeType en arg de la fct*/;
                /* ingredientList, descriptionList & categoryList construitent au dessus */
                name = resRecipe.getString("recipe.name");
                pixName = resRecipe.getString("pixName");
                nbPerson = resRecipe.getInt("nbPerson");
                origin = resRecipe.getString("recipe_origin.name");
                isFavorite = false;
                isForPlanning = false;
                rating = resRecipe.getInt("rating");
                nbVoter = resRecipe.getInt("nbVoter");


                recipe = new Recipe(idRecipe, name, pixName, recipeType, nbPerson, ingredientList, descriptionList, origin, categoryList, isFavorite, isForPlanning, rating, nbVoter);
                listRecipe.add(recipe);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listRecipe;
    }






    public List<String> getRecipesOld(Connection conn){
        List<String> listRecipe = new ArrayList<String>();
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement("SELECT * FROM RECIPE_type");
            ResultSet res = stm.executeQuery();
            String name;
            String email;
            Recipe rec;
            while(res.next()){



                name = res.getString("NAME");
                //email =  res.getString("EMAIL");
                /*rec = new Recipe(name);
                rec.setBlabla(name);
                rec.setFood(name);
                //rec.setEmail(email);
                listRecipe.add(0, rec);
                */
                listRecipe.add(0, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listRecipe;
    }


}
