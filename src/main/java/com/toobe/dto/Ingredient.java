package com.toobe.dto;

/**
 * Created by fabien on 10/04/2016.
 */

/**
 * {qty:4, unit:'', food:{"id":1,"name":"cabillaud","idCategory":5, validated:true}}
 */
public class Ingredient {
    private int qty;
    private String unit;
    private Food food;

    public Ingredient(int qty, String unit, Food food) {
        this.qty = qty;
        this.unit = unit;
        this.food = food;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }
}
