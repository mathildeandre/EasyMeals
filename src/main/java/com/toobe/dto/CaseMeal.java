package com.toobe.dto;

/**
 * Created by fabien on 10/04/2016.
 */

import java.util.List;

/**
 * //caseMeal = {id: lunch4, nbPers:5 , numDay:4,  recipes:[recipe1, recipe2, ...]}
 */
public class CaseMeal {
    private int id;
    private int nbPers;
    private int numDay;
    private List<Recipe> recipes;

    public CaseMeal(int id, int nbPers, int numDay, List<Recipe> recipes) {
        this.id = id;
        this.nbPers = nbPers;
        this.numDay = numDay;
        this.recipes = recipes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbPers() {
        return nbPers;
    }

    public void setNbPers(int nbPers) {
        this.nbPers = nbPers;
    }

    public int getNumDay() {
        return numDay;
    }

    public void setNumDay(int numDay) {
        this.numDay = numDay;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
