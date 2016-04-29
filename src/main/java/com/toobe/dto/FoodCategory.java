package com.toobe.dto;

/**
 * Created by mathilde on 27/03/2016.
 */

/**
{
    {"id":1,"name":"viande","noRank":5}
*/

public class FoodCategory {

    private int id;
    private String name;
    private int noRank;

    public FoodCategory(int id, String name, int noRank) {
        this.id = id;
        this.name = name;
        this.noRank = noRank;
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

    public int getNoRank() {
        return noRank;
    }

    public void setNoRank(int noRank) {
        this.noRank = noRank;
    }
}
