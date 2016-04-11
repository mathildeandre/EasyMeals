package com.toobe.dto;

/**
 * Created by mathilde on 27/03/2016.
 */

import java.util.List;

/**
{
        id:5,
        name:'Burritos',
        pixName:'burritos.jpg',
        recipeType:'course',
        nbPerson:2,
        ingredients:[{qty:4, unit:'', food:'crepe a burritos', rayonId:3},{qty:0.2, unit:'kg', food:'steack', rayonId:1}],
        description:'miam miam',
        descriptionOpen: false,
        origin:'Mexicain',
        categories:['viande', 'poêle', 'legume', 'Facile'],
        isFavorite: true,
        isForPlanning:false,
        rating:3.5
        }
*/

public class Recipe {

    private int id;
    private String name;
    private int nbPerson;
    private List<Ingredient> ingredients;
    private String description;

    public Recipe(int id, String name, int nbPerson, List<Ingredient> ingredients, String description){
        this.id = id;
        this.name = name;
        this.nbPerson = nbPerson;
        this.ingredients = ingredients;
        this.description = description;
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

    public int getNbPerson() {
        return nbPerson;
    }

    public void setNbPerson(int nbPerson) {
        this.nbPerson = nbPerson;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
