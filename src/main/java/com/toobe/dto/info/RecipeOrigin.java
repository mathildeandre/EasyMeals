package com.toobe.dto.info;

/**
 * Created by mathilde on 27/03/2016.
 */

/**
{
    {"id":1,"name":"viande","numRank":5}
*/

public class RecipeOrigin {

    private Long id;
    private String name;
    private int numRank;
    private boolean isValidated;

    public RecipeOrigin(){

    }

    public RecipeOrigin(Long id, String name, int numRank, boolean isValidated) {
        this.id = id;
        this.name = name;
        this.numRank = numRank;
        this.isValidated = isValidated;
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

    public boolean getIsValidated() {
        return isValidated;
    }

    public void setIsValidated(boolean isValidated) {
        this.isValidated = isValidated;
    }
}
