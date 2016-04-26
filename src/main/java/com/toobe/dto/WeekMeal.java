package com.toobe.dto;

/**
 * Created by fabien on 10/04/2016.
 */

import java.util.List;

/**
 * //WeekMeal = {weekMealName: lunch, show:true, caseMeals:[caseMeal1, caseMeal2, ..., caseMeal7]}
 */
public class WeekMeal {
    private String weekMealName;
    private boolean show;
    private List<CaseMeal> caseMeals;

    public WeekMeal(String weekMealName, boolean show, List<CaseMeal> caseMeals) {
        this.weekMealName = weekMealName;
        this.show = show;
        this.caseMeals = caseMeals;
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
