package com.toobe.dto;

/**
 * Created by fabien on 10/04/2016.
 */

import java.util.List;


/*
shoppingCategory = {id:0, name:'Autre', numRank:5 , ingredients:[{qty:50, unit:"g", food:"ski", rayonId:0}]}
*/
public class ShoppingCategory {
    private int id;
    private String name;
    private int numRank;
    private List<Ingredient> ingredients;
    public ShoppingCategory() {
    }
    public ShoppingCategory(int id, String name, int numRank, List<Ingredient> ingredients) {
        this.id = id;
        this.name = name;
        this.numRank = numRank;
        this.ingredients = ingredients;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumRank() {
        return numRank;
    }

    public void setNumRank(int numRank) {
        this.numRank = numRank;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
