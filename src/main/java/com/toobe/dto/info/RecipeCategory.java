package com.toobe.dto.info;

/**
 * Created by mathilde on 27/03/2016.
 */

import java.util.List;

/**
{
    {"id":1,"name":"viande","numRank":5}
*/

public class RecipeCategory {

    private Long id;
    private String name;
    private int numRank;
    private int idRecipeType;

    public RecipeCategory(){

    }
    public RecipeCategory(Long id, String name, int numRank, int idRecipeType) {
        this.id = id;
        this.name = name;
        this.numRank = numRank;
        this.idRecipeType = idRecipeType;
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

    public int getNumRank() {
        return numRank;
    }

    public void setNumRank(int numRank) {
        this.numRank = numRank;
    }

    public int getIdRecipeType() {
        return idRecipeType;
    }

    public void setIdRecipeType(int idRecipeType) {
        this.idRecipeType = idRecipeType;
    }
}
