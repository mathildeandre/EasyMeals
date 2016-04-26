package com.toobe.dto;

/**
 * Created by fabien on 10/04/2016.
 */

import java.util.List;

/**
 * //caseMeal = {id: lunch4, nbPers:5 , noDay:4,  recipes:[recipe1, recipe2, ...]}
 */
public class CaseMeal {
    private int id;
    private int nbPers;
    private int noDay;
    private List<Recipe> recipes;

    public CaseMeal(int id, int nbPers, int noDay, List<Recipe> recipes) {
        this.id = id;
        this.nbPers = nbPers;
        this.noDay = noDay;
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

    public int getNoDay() {
        return noDay;
    }

    public void setNoDay(int noDay) {
        this.noDay = noDay;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
