package com.toobe.dto.info;

/**
 * Created by mathilde on 01/05/2016.
 * Type of Recipe :  STRATER / COURSE/ DESERT
 */
public class RecipeType {

    int idType;
    String nameType;

    public RecipeType(){

    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }
}
