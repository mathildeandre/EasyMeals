package com.toobe.dto;

/**
 * Created by fabien on 10/04/2016.
 */

import java.util.ArrayList;
import java.util.List;



  /*

  NEW ....
  //Planning = {id: .., name: myVeganPlanning, lastOpen: true, nbPersGlobal:4,  weekMeals: [aWeekMealLunch, aWeekMealDinner, .., ..], isForListShop: true, shoppingCategory:[...]}
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
    private int nbPersGlobal;
    private boolean isForListShop;
    private List<WeekMeal> weekMeals;
    private List<ShoppingCategory> shoppingCategories;

    public Planning(){

    }
    public Planning(Long id, String name, boolean lastOpen, int nbPersGlobal, boolean isForListShop, List<WeekMeal> weekMeals, List<ShoppingCategory> shoppingCategories) {
        this.id = id;
        this.name = name;
        this.lastOpen = lastOpen;
        this.nbPersGlobal = nbPersGlobal;
        this.isForListShop = isForListShop;
        this.weekMeals = weekMeals;
        this.shoppingCategories = shoppingCategories;
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


    public boolean isLastOpen() { return lastOpen; }

    public void setLastOpen(boolean lastOpen) {
        this.lastOpen = lastOpen;
    }

    public int getNbPersGlobal() {
        return nbPersGlobal;
    }

    public void setNbPersGlobal(int nbPersGlobal) {
        this.nbPersGlobal = nbPersGlobal;
    }

    public boolean getIsForListShop() {
        return isForListShop;
    }

    public void setIsForListShop(boolean isForListShop) {
        this.isForListShop = isForListShop;
    }

    public List<WeekMeal> getWeekMeals() {
        return weekMeals;
    }

    public void setWeekMeals(List<WeekMeal> weekMeals) {
        this.weekMeals = weekMeals;
    }

    public List<ShoppingCategory> getShoppingCategories() { return shoppingCategories; }

    public void setShoppingCategories(List<ShoppingCategory> shoppingCategories) { this.shoppingCategories = shoppingCategories; }
}










