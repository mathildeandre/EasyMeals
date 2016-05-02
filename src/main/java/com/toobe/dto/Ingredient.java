package com.toobe.dto;

/**
 * Created by fabien on 10/04/2016.
 */

/**
 * {qty:4, unit:'', food:'crepe a burritos', rayonId:3}
 */
public class Ingredient {
    private int qty;
    private String unit;
    private Food food;
    private int rayonId;

    public Ingredient(int qty, String unit, Food food, int rayonId) {
        this.qty = qty;
        this.unit = unit;
        this.food = food;
        this.rayonId = rayonId;
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

    public int getRayonId() {
        return rayonId;
    }

    public void setRayonId(int rayonId) {
        this.rayonId = rayonId;
    }
}
