package com.toobe.dto;

/**
 * Created by mathilde on 27/03/2016.
 */

/**
{
    {"name":"faite cuire 5min","noDescript":1}
*/

public class RecipeDescription {

    private String name;
    private int noDescip;

    public RecipeDescription(String name, int noDescip) {
        this.name = name;
        this.noDescip = noDescip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNoDescip() {
        return noDescip;
    }

    public void setNoDescip(int noDescip) {
        this.noDescip = noDescip;
    }
}
