package com.toobe.dao;

import com.toobe.dto.RecipeCategory;
import com.toobe.dto.Ingredient;
import com.toobe.dto.Recipe;
import com.toobe.dto.RecipeDescription;

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


    public Recipe getRecipeById(Connection conn, int idRecipe){
        Recipe recipe = new Recipe();
        PreparedStatement stm;
        try {
            /* on fait la requete pr avoir les liste des plats en fonction de notre recipeType (mnt idType)*/
            stm = conn.prepareStatement("SELECT recipe.id as idRecipe, recipe.name as recipeName, pixName, nbPerson, recipe_origin.name as recipeOriginName, " +
                                                "recipe_Type.name as recipeTypeName, rating, nbVoter  " +
                                        "FROM RECIPE , Recipe_Origin, Recipe_Type " +
                                        "WHERE recipe.idOrigin = recipe_origin.id AND recipe.idType = recipe_Type.id AND recipe.id = "+idRecipe);
            ResultSet resRecipe = stm.executeQuery();

            int nbPerson, rating, nbVoter;
            String name, recipeType, pixName, origin;
            boolean isFavorite, isForPlanning;

            if(resRecipe.next()){
                /* INGREDIENTS */
                List<Ingredient> ingredientList = getIngredientList(conn, idRecipe); //new ArrayList<Ingredient>(); //
                /* DESCRIPTIONS */
                List<RecipeDescription> descriptionList = getDescriptionList(conn, idRecipe); //new ArrayList<String>();//
                /* CATEGORIES */
                List<RecipeCategory> categoryList = getCategoryRecipeList(conn, idRecipe); //new ArrayList<RecipeCategory>(); //

                /* recipeType en arg de la fct*/;
                /* ingredientList, descriptionList & categoryList construitent au dessus */
                name = resRecipe.getString("recipeName");
                pixName = resRecipe.getString("pixName");
                nbPerson = resRecipe.getInt("nbPerson");
                origin = resRecipe.getString("recipeOriginName");
                recipeType = resRecipe.getString("recipeTypeName");
                isFavorite = false;
                isForPlanning = false;
                rating = resRecipe.getInt("rating");
                nbVoter = resRecipe.getInt("nbVoter");

                recipe = new Recipe(idRecipe, name, pixName, recipeType, nbPerson, ingredientList, descriptionList, origin, categoryList, isFavorite, isForPlanning, rating, nbVoter);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipe;
    }










    /**
     * On trouve ici toutes les recettes PUBLIC de type COURSE (on verra plus tard pour ajouter celles PRIVATE des users specifiés...)
     */
    public List<Recipe> getRecipesPublicNotValidated(Connection conn, String recipeType){
        List<Recipe> listRecipe = new ArrayList<Recipe>();
        Recipe recipe;
        PreparedStatement stm;
        try {
            /* on recup l'id de Recipe_Type correspondant a notre recipeType (ex : course) */
            stm = conn.prepareStatement("SELECT * FROM RECIPE_TYPE WHERE name = '"+recipeType+"'");
            ResultSet res = stm.executeQuery();
            int idType = 1;
            if(res.next()){
                idType = res.getInt("id");
            }
            /* on fait la requete pr avoir les liste des plats en fonction de notre recipeType (mnt idType)*/
            stm = conn.prepareStatement("SELECT recipe.id as idRecipe, recipe.name as recipeName, pixName, nbPerson, recipe_origin.name as recipeOriginName, rating, nbVoter " +
                                        "FROM RECIPE JOIN Recipe_Origin ON recipe.idOrigin = recipe_origin.id " +
                                        "WHERE isPublic = 1 AND isValidated = 0  AND idType = "+idType );
            ResultSet resRecipe = stm.executeQuery();

            int idRecipe, nbPerson, rating, nbVoter;
            String name, pixName, origin;
            boolean isFavorite, isForPlanning;

            while(resRecipe.next()){
                idRecipe = resRecipe.getInt("idRecipe"); ;
                /* INGREDIENTS */
                List<Ingredient> ingredientList = getIngredientList(conn, idRecipe); //new ArrayList<Ingredient>(); //
                /* DESCRIPTIONS */
                List<RecipeDescription> descriptionList = getDescriptionList(conn, idRecipe); //new ArrayList<String>();//
                /* CATEGORIES */
                List<RecipeCategory> categoryList = getCategoryRecipeList(conn, idRecipe); //new ArrayList<RecipeCategory>(); //

                /* recipeType en arg de la fct*/;
                /* ingredientList, descriptionList & categoryList construitent au dessus */
                name = resRecipe.getString("recipeName");
                pixName = resRecipe.getString("pixName");
                nbPerson = resRecipe.getInt("nbPerson");
                origin = resRecipe.getString("recipeOriginName");
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







    /**
     * On trouve ici toutes les recettes PUBLIC de type COURSE (on verra plus tard pour ajouter celles PRIVATE des users specifiés...)
     */
    public List<Recipe> getRecipes(Connection conn, String recipeType, int idUser){
        List<Recipe> listRecipe = new ArrayList<Recipe>();
        Recipe recipe;
        PreparedStatement stm;
        try {
            /* on recup l'id de Recipe_Type correspondant a notre recipeType (ex : course) */
            stm = conn.prepareStatement("SELECT * FROM RECIPE_TYPE WHERE name = '"+recipeType+"'");
            ResultSet res = stm.executeQuery();
            int idType = 1;
            if(res.next()){
                idType = res.getInt("id");
            }
            /* on fait la requete pr avoir les liste des plats en fonction de notre recipeType (mnt idType)*/
            stm = conn.prepareStatement("SELECT recipe.id as idRecipe, recipe.name as recipeName, pixName, nbPerson, recipe_origin.name as recipeOriginName, rating, nbVoter  " +
                                        "FROM RECIPE JOIN Recipe_Origin ON recipe.idOrigin = recipe_origin.id " +
                                        "WHERE isPublic = 1 AND idType = "+idType +
                                        " UNION " +
                                        "SELECT  recipe.id as idRecipe, recipe.name as recipeName, pixName, nbPerson, recipe_origin.name as recipeOriginName, rating, nbVoter " +
                                        "FROM RECIPE JOIN Recipe_Origin ON recipe.idOrigin = recipe_origin.id " +
                                        "WHERE idUser = "+idUser+" AND idType = "+idType);
            ResultSet resRecipe = stm.executeQuery();

            int idRecipe, nbPerson, rating, nbVoter;
            String name, pixName, origin;
            boolean isFavorite, isForPlanning;

            while(resRecipe.next()){
                idRecipe = resRecipe.getInt("idRecipe"); ;
                /* INGREDIENTS */
               List<Ingredient> ingredientList = getIngredientList(conn, idRecipe); //new ArrayList<Ingredient>(); //
                /* DESCRIPTIONS */
                List<RecipeDescription> descriptionList = getDescriptionList(conn, idRecipe); //new ArrayList<String>();//
                /* CATEGORIES */
                List<RecipeCategory> categoryList = getCategoryRecipeList(conn, idRecipe); //new ArrayList<RecipeCategory>(); //

                /* recipeType en arg de la fct*/;
                /* ingredientList, descriptionList & categoryList construitent au dessus */
                name = resRecipe.getString("recipeName");
                pixName = resRecipe.getString("pixName");
                nbPerson = resRecipe.getInt("nbPerson");
                origin = resRecipe.getString("recipeOriginName");
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



    /****************************************************************************************************************************************************************/
    /***************************************************************************************************************************************************************/
    /********************************************************************* FCT ANNEXES UTILISE PR les fct principales *********************************************/
    /*************************************************************************************************************************************************************/
    /************************************************************************************************************************************************************/


    /**
     * Utilisee dans les fct qui construisent un recipe
     */
    public List<Ingredient> getIngredientList(Connection conn, int idRecipe){

        List<Ingredient> ingredientList = new ArrayList<Ingredient>();;
        Ingredient ingr;
        int qty;
        String unit;
        String food;
        int idCategoryIngr;

        PreparedStatement stm;
        try {
            stm = conn.prepareStatement("SELECT quantity, unit, name, idCategory FROM INGREDIENT JOIN FOOD ON ingredient.idFood = food.id WHERE ingredient.idRecipe = "+idRecipe);
            ResultSet resIngredient = stm.executeQuery();
            while(resIngredient.next()){
                qty = resIngredient.getInt("quantity");
                unit = resIngredient.getString("unit");
                food = resIngredient.getString("name");
                idCategoryIngr = resIngredient.getInt("idCategory");
                ingr = new Ingredient(qty, unit, food, idCategoryIngr);
                ingredientList.add(ingr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredientList;
    }

    /**
     * Utilisee dans les fct qui construisent un recipe
     */
    public List<RecipeDescription> getDescriptionList(Connection conn, int idRecipe){
        List<RecipeDescription> descriptionList = new ArrayList<RecipeDescription>();
        String descr;
        int noDescrip;
        RecipeDescription recipeDescrip;
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement("SELECT description, noDescription FROM Recipe_Description WHERE idRecipe = "+idRecipe+" ORDER BY noDescription");
            ResultSet resDescription = stm.executeQuery();
            while(resDescription.next()){
                descr = resDescription.getString("description");
                noDescrip = resDescription.getInt("noDescription");
                recipeDescrip = new RecipeDescription(descr, noDescrip);
                descriptionList.add(recipeDescrip);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return descriptionList;
    }


    /**
     * Utilisee dans les fct qui construisent un recipe
     */
    public List<RecipeCategory> getCategoryRecipeList(Connection conn, int idRecipe){

        List<RecipeCategory> categoryList = new ArrayList<RecipeCategory>();
        int idCategory;
        String nameCategory;
        int noRankCategory;
        RecipeCategory recipeCategory;
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement("SELECT id, name, noRank FROM Rel_Recipe_Category as rel JOIN Recipe_Category as rc ON rel.idCategory = rc.id WHERE rel.idRecipe = "+idRecipe);
            ResultSet resCategory = stm.executeQuery();
            while(resCategory.next()){
                idCategory = resCategory.getInt("id");
                nameCategory = resCategory.getString("name");
                noRankCategory = resCategory.getInt("noRank");
                recipeCategory = new RecipeCategory(idCategory, nameCategory, noRankCategory);
                categoryList.add(recipeCategory);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryList;
    }




















    /* USELESS .... */
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
