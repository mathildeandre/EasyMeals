package com.toobe.dto;

/**
 * Created by fabien on 10/04/2016.
 */

import java.util.List;

/**
 * //WeekMeal = {id:.., weekMealName: lunch, show:true, caseMeals:[caseMeal1, caseMeal2, ..., caseMeal7]}
 */
public class WeekMeal {
    private int id;
    private String weekMealName;
    private boolean show;
    private List<CaseMeal> caseMeals;

    public WeekMeal(int id, String weekMealName, boolean show, List<CaseMeal> caseMeals) {
        this.id = id;
        this.weekMealName = weekMealName;
        this.show = show;
        this.caseMeals = caseMeals;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWeekMealName() {
        return weekMealName;
    }

    public void setWeekMealName(String weekMealName) {
        this.weekMealName = weekMealName;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public List<CaseMeal> getCaseMeals() {
        return caseMeals;
    }

    public void setCaseMeals(List<CaseMeal> caseMeals) {
        this.caseMeals = caseMeals;
    }
}
