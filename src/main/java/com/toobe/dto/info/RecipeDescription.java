package com.toobe.dto.info;

/**
 * Created by mathilde on 27/03/2016.
 */

/**
{
    {"name":"faite cuire 5min","numDescrip":1}
*/

public class RecipeDescription {

    private String name;
    private int numDescrip;

    public RecipeDescription(){

    }
    public RecipeDescription(String name, int numDescrip) {
        this.name = name;
        this.numDescrip = numDescrip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getNumDescrip() {
        return numDescrip;
    }

    public void setNumDescrip(int numDescrip) {
        this.numDescrip = numDescrip;
    }
}
