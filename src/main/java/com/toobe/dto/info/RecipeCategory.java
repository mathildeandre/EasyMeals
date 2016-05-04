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

    private int id;
    private String name;
    private int numRank;

    public RecipeCategory(int id, String name, int numRank) {
        this.id = id;
        this.name = name;
        this.numRank = numRank;
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
}
