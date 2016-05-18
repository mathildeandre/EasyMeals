package com.toobe.dto;

/**
 * Created by fabien on 10/04/2016.
 */

import java.util.ArrayList;
import java.util.List;



  /*

  NEW ....
  //Planning = {id: .., name: myVeganPlanning, lastOpen: true,  weekMeals: [aWeekMealLunch, aWeekMealDinner, .., ..]}
  //WeekMeal = {id: .. weekMealName: lunch, show:true, caseMeals:[caseMeal1, caseMeal2, ..., caseMeal7]}
  //caseMeal = {id: lunch4, nbPers:5 , numDay:4,  recipes:[recipe1, recipe2, ...]}
  //recipe =  {id:'1',name:'burger',recipeType:'course',nbPerson:4,ingredients:[{qty:400, unit:'g', food:'steak'},{qty:4, unit:'', food:'bread'}],description:'faire des burgers'}

               OLD ...
            //fourWeekMeals = [aWeekMealLunch, aWeekMealDinner, .., ..]
            //aWeekMeal = {typeMeal: lunch, show:true, weekMeals:[meal1, meal2, ..., meal7]}
            //meal = {id: lunch4, nbPers:5 , recipes:[recipe1, recipe2, ...]} //ex lunch of thursday
            //recipe =  {id:'1',name:'burger',recipeType:'course',nbPerson:4,ingredients:[{qty:400, unit:'g', food:'steak'},{qty:4, unit:'', food:'bread'}],description:'faire des burgers'}
   */



/**
 * //Planning = {name:myVeganPlanning, lastOpen: true,  weekMeals = [aWeekMealLunch, aWeekMealDinner, .., ..]}
 */

/*
 -------- >>>>>>>>>>>    ATTENTION le planning est rempli uniquement avec ce qu'il faut,: la base ne stock pas les case vide etc,
     ::::::::::::::::::::::   il faudra CONSTRUIRE les cases vide dans ANGULAR...
 */
public class Planning {
    private Long id;
    private String name;
    private boolean lastOpen;
    private List<WeekMeal> weekMeals;

    public Planning(){

    }
    public Planning(Long id, String name, boolean lastOpen, List<WeekMeal> weekMeals) {
        this.id = id;
        this.name = name;
        this.lastOpen = lastOpen;
        this.weekMeals = weekMeals;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WeekMeal> getWeekMeals() {
        return weekMeals;
    }

    public void setWeekMeals(List<WeekMeal> weekMeals) {
        this.weekMeals = weekMeals;
    }

    public boolean isLastOpen() {
        return lastOpen;
    }

    public void setLastOpen(boolean lastOpen) {
        this.lastOpen = lastOpen;
    }

}










