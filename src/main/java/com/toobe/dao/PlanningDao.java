package com.toobe.dao;

import com.toobe.dto.*;
import com.toobe.dto.info.RecipeCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

/**
 * Created by mathilde on 27/03/2016.
 */
public class PlanningDao {

  /*NEW ....
  //Planning = {name: myVeganPlanning, lastOpen: true,  weekMeals: [aWeekMealLunch, aWeekMealDinner, .., ..]}
  //WeekMeal = {weekMealName: lunch, show:true, caseMeals:[caseMeal1, caseMeal2, ..., caseMeal7]}
  //caseMeal = {id: lunch4, nbPers:5 , numDay:4,  recipes:[recipe1, recipe2, ...]}
  //recipe =  {id:'1',name:'burger',recipeType:'course',nbPerson:4,ingredients:[{qty:400, unit:'g', food:'steak'},{qty:4, unit:'', food:'bread'}],description:'faire des burgers'}

    OLD ...
    //fourWeekMeals = [aWeekMealLunch, aWeekMealDinner, .., ..]
    //aWeekMeal = {typeMeal: lunch, show:true, weekMeals:[meal1, meal2, ..., meal7]}
    //meal = {id: lunch4, nbPers:5 , recipes:[recipe1, recipe2, ...]} //ex lunch of thursday
    //recipe =  {id:'1',name:'burger',recipeType:'course',nbPerson:4,ingredients:[{qty:400, unit:'g', food:'steak'},{qty:4, unit:'', food:'bread'}],description:'faire des burgers'}
   */
  private final static String CREATE_COPY_PLANNING = "INSERT INTO Planning(name, lastOpen, idUser, nbPersGlobal, isForListShop) VALUES (?, ?, ?, ?, ?);\n";
    private final static String CREATE_COPY_WEEKMEAL = "INSERT INTO Planning_WeekMeal(weekMealName, showWeekMeal, idPlanning) VALUES (?, ?, ?);\n";
    private final static String CREATE_COPY_CASEMEAL = "INSERT INTO Planning_CaseMeal(numDay, nbPers, idPlanningWeekMeal) VALUES (?, ?, ?);\n";
    private final static String CREATE_COPY_REL_RECIPE_CASEMEAL = "INSERT INTO Rel_Recipe_CaseMealPlanning(idRecipe, idPlanningCaseMeal, nbPers) VALUES (?, ?, ?);\n";

    public Planning copyOfPlanning(Connection conn, Long idPlanning) { //A FAIRE UNE FOIS LORS DE CREATION DE USER
        PreparedStatement stm, stmWM, stmCM;
        ResultSet res;
        int isOk = 0;
        Long idPlanningCopy = null;
        Long idWeekMealCopy = null;
        Long idCaseMealCopy = null;
        try {
            /*************************/
            /******* PLANNING *******/
            /***********************/
            stm = conn.prepareStatement("SELECT * FROM PLANNING WHERE id = "+idPlanning);
            res = stm.executeQuery();
            if(res.next()) {
                /**************************** CREATION NEW PLANNING COPY ******************************/
                //1. recup info
                String name = res.getString("name");
                //boolean lastOpen = res.getInt("lastOpen") == 1;
                int nbPersGlobal = res.getInt("nbPersGlobal");
                int idUser = res.getInt("idUser");
                //2. create copy
                stm = conn.prepareStatement(CREATE_COPY_PLANNING, Statement.RETURN_GENERATED_KEYS);
                stm.setString(1, name);
                stm.setBoolean(2, false); //lastOpen
                stm.setLong(3, idUser);
                stm.setInt(4, nbPersGlobal);
                stm.setBoolean(5, false); //isForListShop
                isOk = stm.executeUpdate();
                if (isOk == 0) {
                    throw new SQLException("Creating COPY Planning failed, no rows affected");
                }
                res = stm.getGeneratedKeys();
                if (res.next()) {
                    idPlanningCopy = res.getLong(1);
                }
                /************************ end CREATION NEW PLANNING COPY ******************************/

                /*************************/
                /******* WEEK MEAL ******/
                /***********************/
                stm = conn.prepareStatement("SELECT * FROM PLANNING_WEEKMEAL  WHERE idPlanning = " + idPlanning);
                ResultSet resWeekMeal = stm.executeQuery();
                while (resWeekMeal.next()) {
                    Long idWeekMeal = resWeekMeal.getLong("id");


                    /**************************** CREATION WEEKMEAL COPY ******************************/
                    //1. recup info
                    String weekMealName = resWeekMeal.getString("weekMealName");
                    boolean show = resWeekMeal.getInt("showWeekMeal") == 1;
                    //2. create copy
                    stmWM = conn.prepareStatement(CREATE_COPY_WEEKMEAL, Statement.RETURN_GENERATED_KEYS);
                    stmWM.setString(1, weekMealName);
                    stmWM.setBoolean(2, show);
                    stmWM.setLong(3, idPlanningCopy);
                    isOk = stmWM.executeUpdate();
                    if (isOk == 0) {
                        throw new SQLException("Creating COPY WeekMeal failed, no rows affected");
                    }
                    res = stmWM.getGeneratedKeys();
                    if (res.next()) {
                        idWeekMealCopy = res.getLong(1);
                    }
                    /************************ end CREATION WEEKMEAL COPY ******************************/


                    /*************************/
                    /******* CASE MEAL ******/
                    /***********************/
                    stm = conn.prepareStatement("SELECT * FROM PLANNING_CASEMEAL  WHERE idPlanningWeekMeal = " + idWeekMeal);
                    ResultSet resCaseMeal = stm.executeQuery();
                    while (resCaseMeal.next()) {
                        Long idCaseMeal = resCaseMeal.getLong("id");

                        /**************************** CREATION CASEMEAL COPY ******************************/
                        //1. recup info
                        int nbPers = resCaseMeal.getInt("nbPers");
                        int numDay = resCaseMeal.getInt("numDay");
                        //2. create copy
                        stmCM = conn.prepareStatement(CREATE_COPY_CASEMEAL,  Statement.RETURN_GENERATED_KEYS);
                        stmCM.setInt(1, numDay); //numDay
                        stmCM.setInt(2, nbPers); //nbPers
                        stmCM.setLong(3, idWeekMealCopy); //idPlanningWeekMeal
                        isOk = stmCM.executeUpdate();
                        if (isOk == 0) {
                            throw new SQLException("Creating COPY CaseMeal failed, no rows affected");
                        }
                        res = stmCM.getGeneratedKeys();
                        if (res.next()) {
                            idCaseMealCopy = res.getLong(1);
                        }
                        /************************ end CREATION CASEMEAL COPY ******************************/


                        /***************************************/
                        /******* RECIPES into CASE MEAL *******/
                        /***************************************/
                        stm = conn.prepareStatement("SELECT * FROM Rel_recipe_caseMealPlanning  WHERE idPlanningCaseMeal = " + idCaseMeal);
                        ResultSet resRelRecipeCaseMeal = stm.executeQuery();
                        while (resRelRecipeCaseMeal.next()) {
                            /**************************** CREATION REL RECIPE_CASEMEAL COPY ******************************/
                            //1. recup info
                            int idRecipe = resRelRecipeCaseMeal.getInt("idRecipe");
                            int nbPersRelRCM = resRelRecipeCaseMeal.getInt("nbPers");
                            //2. create copy
                            stm = conn.prepareStatement(CREATE_COPY_REL_RECIPE_CASEMEAL);
                            stm.setInt(1, idRecipe); //idRecipe
                            stm.setLong(2, idCaseMealCopy); //idPlanningCaseMeal
                            stm.setInt(3, nbPersRelRCM); //nbPers
                            isOk = stm.executeUpdate();
                            if (isOk == 0) {
                                throw new SQLException("Creating COPY Rel_Recipe_CaseMeal failed, no rows affected");
                            }
                            /************************ end CREATION REL RECIPE_CASEMEAL COPY ******************************/
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getPlanningById(conn, idPlanning);
    }

    private final static String CREATE_PLANNING = "INSERT INTO Planning(name, lastOpen, idUser) VALUES (?, ?, ?);\n";
    private final static String CREATE_WEEKMEAL = "INSERT INTO Planning_WeekMeal(weekMealName, showWeekMeal, idPlanning) VALUES (?, ?, ?);\n";
    private final static String CREATE_CASEMEAL = "INSERT INTO Planning_CaseMeal(numDay, nbPers, idPlanningWeekMeal) VALUES (?, ?, ?);\n";

    public Planning createPlanning(Connection conn, int idUser) { //A FAIRE UNE FOIS LORS DE CREATION DE USER
        PreparedStatement stm, stmWM, stmCM;
        ResultSet res;
        int isOk = 0;
        Long idPlanning = null;
        Long idWeekMeal = null;
        try {
            //1. INSERT PLANNING
            stm = conn.prepareStatement(CREATE_PLANNING, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, "nouveauPlanning");
            stm.setBoolean(2, true); //lastOpen
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
            list.add("breakfast");list.add("lunch"); list.add("snack");list.add("dinner");
            for(String str : list){//loop on lunch, dinner etc
                stmWM = conn.prepareStatement(CREATE_WEEKMEAL, Statement.RETURN_GENERATED_KEYS);
                stmWM.setString(1, str);
                stmWM.setBoolean(2, (str.equals("lunch")||str.equals("dinner")));
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
                        stmCM.setInt(1, i); //numDay
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
                        int numDay = resCaseMeal.getInt("numDay");
                        CaseMeal caseMeal = new CaseMeal(idCaseMeal, nbPers, numDay, listRecipe);
                        listCaseMeal.add(caseMeal);
                    }
                    String weekMealName = resWeekMeal.getString("weekMealName");
                    boolean show = resWeekMeal.getInt("showWeekMeal") == 1;
                    WeekMeal weekMeal = new WeekMeal(idWeekMeal, weekMealName, show, listCaseMeal);
                    listWeekMeal.add(weekMeal);
                }

                String name = res.getString("name");
                boolean lastOpen = res.getInt("lastOpen") == 1;
                int nbPersGlobal = res.getInt("nbPersGlobal");
                boolean isForListShop = res.getInt("isForListShop") == 1;
                planning = new Planning(idPlanning, name, lastOpen, nbPersGlobal, isForListShop, listWeekMeal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return planning;
    }






    /*
    NO USE .... -- On recup le planning d'un user avec le boolean lastOPen = true
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
     * On trouve ici toutes les planning pour un idUser QUI NE SONT PAS pour la listShoppingPlanning
     * => pas forcement optimal...
     */
    public List<Planning> getPlanningsOfUser(Connection conn, int idUser){
        List<Planning> listPlanning = new ArrayList<Planning>();
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement("SELECT * FROM PLANNING WHERE idUser = "+idUser+" AND isForListShop = 0");
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




    private final static String CREATE_REL_RECIPECASEMEAL = "INSERT INTO Rel_Recipe_CaseMealPlanning(idRecipe, idPlanningCaseMeal) VALUES (?, ?);\n";
    public void postNewRecipeCaseMeal(Connection conn, Long idRecipe, Long idCaseMeal){
        PreparedStatement stm;
        int isOk = 0;
        try {
            stm = conn.prepareStatement(CREATE_REL_RECIPECASEMEAL);
            stm.setLong(1, idRecipe);
            stm.setLong(2, idCaseMeal);
            isOk = stm.executeUpdate();
            if (isOk == 0) {
                throw new SQLException("postNewRecipeCaseMeal failed, no rows affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private final static String DELETE_REL_RECIPECASEMEAL = "DELETE FROM Rel_Recipe_CaseMealPlanning WHERE idRecipe = ? AND idPlanningCaseMeal = ?;\n";
    public void deleteOldRecipeCaseMeal(Connection conn, Long idRecipe, Long idCaseMeal){
        PreparedStatement stm;
        int isOk = 0;
        try {
            stm = conn.prepareStatement(DELETE_REL_RECIPECASEMEAL);
            stm.setLong(1, idRecipe);
            stm.setLong(2, idCaseMeal);
            isOk = stm.executeUpdate();
            if (isOk == 0) {
                throw new SQLException("deleteOldRecipeCaseMeal failed, no rows affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private final static String UPDATE_NAME_PLANNING = "UPDATE Planning SET name = ? WHERE id = ?;\n";
    public void postNewNamePlanning(Connection conn, Long idPlanning, String namePlanning){
        PreparedStatement stm;
        int isOk = 0;
        try {
            stm = conn.prepareStatement(UPDATE_NAME_PLANNING);
            stm.setString(1, namePlanning);
            stm.setLong(2, idPlanning);
            isOk = stm.executeUpdate();
            if (isOk == 0) {
                throw new SQLException("postNewNamePlanning failed, no rows affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private final static String UPDATE_LASTOPEN_PLANNING = "UPDATE Planning SET lastOpen = ? WHERE id = ?;\n";
    public void putLastOpenPlanning(Connection conn, Long idOldOpenPlanning, Long idNewOpenPlanning){
        PreparedStatement stm;
        int isOk = 0;
        try {
            stm = conn.prepareStatement(UPDATE_LASTOPEN_PLANNING);
            stm.setBoolean(1, false);
            stm.setLong(2, idOldOpenPlanning);
            isOk = stm.executeUpdate();
            if (isOk == 0) {
                throw new SQLException("putLastOpenPlanning failed, no rows affected");
            }
            stm = conn.prepareStatement(UPDATE_LASTOPEN_PLANNING);
            stm.setBoolean(1, true);
            stm.setLong(2, idNewOpenPlanning);
            isOk = stm.executeUpdate();
            if (isOk == 0) {
                throw new SQLException("putLastOpenPlanning failed, no rows affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private final static String UPDATE_SHOW_WEEKMEAL = "UPDATE Planning_WeekMeal SET showWeekMeal = ? WHERE id = ?;\n";
    public void putShowWeekMeal(Connection conn, Long idWeekMeal, Boolean showWeekMeal){
        PreparedStatement stm;
        int isOk = 0;
        try {
            stm = conn.prepareStatement(UPDATE_SHOW_WEEKMEAL);
            stm.setBoolean(1, showWeekMeal);
            stm.setLong(2, idWeekMeal);
            isOk = stm.executeUpdate();
            if (isOk == 0) {
                throw new SQLException("putShowWeekMeal failed, no rows affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private final static String UPDATE_NbPers_CASEMEAL = "UPDATE Planning_CaseMeal SET nbPers = ? WHERE id = ?;\n";
    public void putNbPersCaseMeal(Connection conn, Long idCaseMeal, int nbPersCaseMeal){
        PreparedStatement stm;
        int isOk = 0;
        try {
            stm = conn.prepareStatement(UPDATE_NbPers_CASEMEAL);
            stm.setInt(1, nbPersCaseMeal);
            stm.setLong(2, idCaseMeal);
            isOk = stm.executeUpdate();
            if (isOk == 0) {
                throw new SQLException("putNbPersCaseMeal failed, no rows affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private final static String UPDATE_NbPersGlobal = "UPDATE Planning SET nbPersGlobal = ? WHERE id = ?;\n";
    public void putNbPersGlobalPlanning(Connection conn, Long idPlanning, int nbPersGlobal){
        PreparedStatement stm;
        int isOk = 0;
        try {
            stm = conn.prepareStatement(UPDATE_NbPersGlobal);
            stm.setInt(1, nbPersGlobal);
            stm.setLong(2, idPlanning);
            isOk = stm.executeUpdate();
            if (isOk == 0) {
                throw new SQLException("putNbPersCaseMeal failed, no rows affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private final static String DELETE_Planning = "DELETE FROM Planning WHERE id = ?;\n";
    public void deletePlanningById(Connection conn, Long idPlanning){
        PreparedStatement stm;
        int isOk = 0;
        try {
            stm = conn.prepareStatement(DELETE_Planning);
            stm.setLong(1, idPlanning);
            isOk = stm.executeUpdate();
            if (isOk == 0) {
                throw new SQLException("deletePlanningById failed, no rows affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private final static String UPDATE_ISFORLISTSHOP_PLANNING = "UPDATE Planning SET isForListShop = ? WHERE id = ?;\n";
    public void putIsForListShop(Connection conn, Long idPlanning, boolean isForListShop){
        PreparedStatement stm;
        int isOk = 0;
        try {
            stm = conn.prepareStatement(UPDATE_ISFORLISTSHOP_PLANNING);
            stm.setBoolean(1, isForListShop);
            stm.setLong(2, idPlanning);
            isOk = stm.executeUpdate();
            if (isOk == 0) {
                throw new SQLException("putIsForListShop failed, no rows affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }






}
