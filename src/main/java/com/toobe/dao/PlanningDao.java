package com.toobe.dao;

import com.toobe.dto.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mathilde on 27/03/2016.
 */
public class PlanningDao {




  /*

  NEW ....
  //Planning = {name: myVeganPlanning, lastOpen: true,  weekMeals: [aWeekMealLunch, aWeekMealDinner, .., ..]}
  //WeekMeal = {weekMealName: lunch, show:true, caseMeals:[caseMeal1, caseMeal2, ..., caseMeal7]}
  //caseMeal = {id: lunch4, nbPers:5 , noDay:4,  recipes:[recipe1, recipe2, ...]}
  //recipe =  {id:'1',name:'burger',recipeType:'course',nbPerson:4,ingredients:[{qty:400, unit:'g', food:'steak'},{qty:4, unit:'', food:'bread'}],description:'faire des burgers'}

               OLD ...
            //fourWeekMeals = [aWeekMealLunch, aWeekMealDinner, .., ..]
            //aWeekMeal = {typeMeal: lunch, show:true, weekMeals:[meal1, meal2, ..., meal7]}
            //meal = {id: lunch4, nbPers:5 , recipes:[recipe1, recipe2, ...]} //ex lunch of thursday
            //recipe =  {id:'1',name:'burger',recipeType:'course',nbPerson:4,ingredients:[{qty:400, unit:'g', food:'steak'},{qty:4, unit:'', food:'bread'}],description:'faire des burgers'}
   */




    /**
     * On trouve ici toutes les planning pour un idUser
     */
    public Planning getPlanningById(Connection conn, int idPlanning){
        RecipeDao recipeDao = new RecipeDao();
        Recipe recipe;
        Planning planning = new Planning();
        PreparedStatement stm;
        try {
            /* on recup l'id de Recipe_Type correspondant a notre recipeType (ex : course) */
            stm = conn.prepareStatement("SELECT * FROM PLANNING WHERE id = "+idPlanning);
            ResultSet res = stm.executeQuery();

            int idWeekMeal, idCaseMeal, idRecipe;
            if(res.next()){

                //idPlanning = res.getInt("id");

                /******* WEEK MEAL ******/
                stm = conn.prepareStatement("SELECT * FROM PLANNING_WEEKMEAL  WHERE idPlanning = "+idPlanning);
                ResultSet resWeekMeal = stm.executeQuery();

                List<WeekMeal> listWeekMeal = new ArrayList<WeekMeal>();
                while(resWeekMeal.next()){
                    idWeekMeal = resWeekMeal.getInt("id");

                    /******* CASE MEAL *******/
                    stm = conn.prepareStatement("SELECT * FROM PLANNING_CASEMEAL  WHERE idPlanningWeekMeal = "+idWeekMeal);
                    ResultSet resCaseMeal = stm.executeQuery();

                    List<CaseMeal> listCaseMeal = new ArrayList<CaseMeal>();
                    while(resCaseMeal.next()){
                        idCaseMeal = resCaseMeal.getInt("id");

                        /******* RECIPES into CASE MEAL *******/
                        stm = conn.prepareStatement("SELECT * FROM Rel_recipe_caseMealPlanning  WHERE idPlanningCaseMeal = "+idCaseMeal);
                        ResultSet resRelRecipeCaseMeal = stm.executeQuery();
                        List<Recipe> listRecipe = new ArrayList<Recipe>();
                        while(resRelRecipeCaseMeal.next()){
                            idRecipe = resRelRecipeCaseMeal.getInt("idRecipe");
                            recipe = recipeDao.getRecipeById(conn, idRecipe);
                            listRecipe.add(recipe);
                        }
                        int nbPers = resCaseMeal.getInt("nbPers");
                        int noDay = resCaseMeal.getInt("noDay");
                        CaseMeal caseMeal = new CaseMeal(idCaseMeal, nbPers, noDay, listRecipe);
                        listCaseMeal.add(caseMeal);
                    }
                    String weekMealName = resWeekMeal.getString("weekMealName");
                    boolean show = resWeekMeal.getInt("showWeekMeal") == 1;
                    WeekMeal weekMeal = new WeekMeal(weekMealName, show, listCaseMeal);
                    listWeekMeal.add(weekMeal);
                }

                String name = res.getString("name");
                boolean lastOpen = res.getInt("lastOpen") == 1;
                planning = new Planning(name, lastOpen, listWeekMeal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return planning;
    }






    /*
    On recup le planning d'un user avec le boolean lastOPen = true
     */
    public Planning getPlanningCurrentOfUser(Connection conn, int idUser){
        Planning planning = new Planning();
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement("SELECT * FROM PLANNING WHERE idUser = "+idUser+" AND lastOpen = true");
            ResultSet res = stm.executeQuery();
            int idPlanning;
            if(res.next()){
                idPlanning = res.getInt("id");
                planning =  getPlanningById(conn, idPlanning);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return planning;
    }




    /**
     * On trouve ici toutes les planning pour un idUser
     * => pas forcement optimal...
     */
    public List<Planning> getPlanningsOfUser(Connection conn, int idUser){
        List<Planning> listPlanning = new ArrayList<Planning>();
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement("SELECT * FROM PLANNING WHERE idUser = "+idUser);
            ResultSet res = stm.executeQuery();
            int idPlanning;
            while(res.next()){
                idPlanning = res.getInt("id");
                Planning planning =  getPlanningById(conn, idPlanning);
                listPlanning.add(planning);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listPlanning;
    }








}
