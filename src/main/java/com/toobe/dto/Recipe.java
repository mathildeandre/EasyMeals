package com.toobe.dto;

/**
 * Created by mathilde on 27/03/2016.
 */
public class Recipe {

    private int id;
    private String name;

    public Recipe(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
