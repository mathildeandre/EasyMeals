package com.toobe.dao;

import com.toobe.dto.*;
import com.toobe.dto.info.RecipeCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mathilde on 27/03/2016.
 */
public class PlanningDao {

  /*NEW ....
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

    private final static String CREATE_PLANNING = "INSERT INTO Planning(name, lastOpen, idUser) VALUES (?, ?, ?);\n";
    private final static String CREATE_WEEKMEAL = "INSERT INTO Planning_WeekMeal(weekMealName, showWeekMeal, idPlanning) VALUES (?, ?, ?);\n";
    private final static String CREATE_CASEMEAL = "INSERT INTO Planning_CaseMeal(noDay, nbPers, idPlanningWeekMeal) VALUES (?, ?, ?);\n";

    public Planning createPlanning(Connection conn, int idUser) {
        PreparedStatement stm, stmWM, stmCM;
        ResultSet res;
        int isOk = 0;
        Long idPlanning = null;
        Long idWeekMeal = null;
        try {
            //1. INSERT PLANNING
            stm = conn.prepareStatement(CREATE_PLANNING, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, "nouveauPlanning");
            stm.setBoolean(2, false);
            stm.setLong(3, idUser);
            isOk = stm.executeUpdate();
            if (isOk == 0) {
                throw new SQLException("Creating Planning failed, no rows affected");
            }
            res = stm.getGeneratedKeys();
            if(res.next()){
                idPlanning  = res.getLong(1);
            }

            //2. INSERT WEEKMEAL & CASEMEAL
            List<String> list = new ArrayList<String>();
            list.add("lunch"); list.add("dinner"); list.add("breakfast"); list.add("snack");
            for(String str : list){//loop on lunch, dinner etc
                stmWM = conn.prepareStatement(CREATE_WEEKMEAL, Statement.RETURN_GENERATED_KEYS);
                stmWM.setString(1, str);
                stmWM.setBoolean(2, false);
                stmWM.setLong(3, idPlanning);
                isOk = stmWM.executeUpdate();
                if (isOk == 0) {
                    throw new SQLException("Creating WeekMeal failed, no rows affected");
                }
                res = stmWM.getGeneratedKeys();
                if(res.next()){
                    idWeekMeal  = res.getLong(1);
                    for(int i=1; i<=7; i++){
                        stmCM = conn.prepareStatement(CREATE_CASEMEAL);
                        stmCM.setInt(1, i); //noDay
                        stmCM.setInt(2, 4); //nbPers
                        stmCM.setLong(3, idWeekMeal); //idPlanningWeekMeal
                        isOk = stmCM.executeUpdate();
                        if (isOk == 0) {
                            throw new SQLException("Creating WeekMeal failed, no rows affected");
                        }
                    }
                }else{
                    throw new SQLException("getGeneratedKeys of WEEK_MEAL did not work");
                }

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getPlanningById(conn, idPlanning);
    }



    /*

     */


    /**
     * On trouve ici toutes les planning pour un idUser
     */
    public Planning getPlanningById(Connection conn, Long idPlanning){
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
            Long idPlanning;
            if(res.next()){
                idPlanning = res.getLong("id");
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
            Long idPlanning;
            while(res.next()){
                idPlanning = res.getLong("id");
                Planning planning =  getPlanningById(conn, idPlanning);
                listPlanning.add(planning);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listPlanning;
    }








}
