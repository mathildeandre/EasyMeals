package com.toobe.dto;

/**
 * Created by fabien on 10/04/2016.
 */

import java.util.List;

/*
ListShoppingPlanning = {name:"", date:"16/07/16", listShopping:{name:.., listShoppingCategories}, planning:{name:.., lastOpen:..  ...}}

 */

public class ListShoppingPlanning {
    private int id;
    private String name;
    private int date;
    private ListShopping listShopping;
    private Planning planning;

    public ListShoppingPlanning(){

    }
    public ListShoppingPlanning(int id, String name, int date, ListShopping listShopping, Planning planning) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.listShopping = listShopping;
        this.planning = planning;
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

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public ListShopping getListShopping() {
        return listShopping;
    }

    public void setListShopping(ListShopping listShopping) {
        this.listShopping = listShopping;
    }

    public Planning getPlanning() {
        return planning;
    }

    public void setPlanning(Planning planning) {
        this.planning = planning;
    }
}
