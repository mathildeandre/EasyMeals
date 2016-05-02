package com.toobe.dto;

/**
 * Created by fabien on 28/04/2016.
 */


/**
 {"id":1,"name":"cabillaud","idCategory":5, validated:true}
 */

public class Food {
    private Long id;
    private String name;
    private int idCategory;
    private boolean isValidated;

    public Food(Long id, String name, int idCategory, boolean isValidated) {
        this.id = id;
        this.name = name;
        this.idCategory = idCategory;
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

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public boolean isValidated() {
        return isValidated;
    }

    public void setValidated(boolean validated) {
        isValidated = validated;
    }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", idCategory=" + idCategory +
                ", isValidated=" + isValidated +
                '}';
    }
}
