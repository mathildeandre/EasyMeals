package com.toobe.dto;

/**
 * Created by mathilde on 27/03/2016.
 */

import java.io.Serializable;
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

public class Recipe implements Serializable{

    private int id;
    private String name;
    private String pixName;
    private String recipeType;
    private int nbPerson;
    private List<Ingredient> ingredients;
    private List<String> descriptions;
    private boolean descriptionOpen;
    private String origin;
    private List<CategoryRecipe> categories;
    private boolean isFavorite;
    private boolean isForPlanning;
    private int rating;
    private int nbVoter;

    public Recipe(int id, String name, String pixName, String recipeType, int nbPerson, List<Ingredient> ingredients, List<String> descriptions,
                  String origin, List<CategoryRecipe> categories, boolean isFavorite, boolean isForPlanning, int rating, int nbVoter) {
        this.id = id;
        this.name = name;
        this.pixName = pixName;
        this.recipeType = recipeType;
        this.nbPerson = nbPerson;
        this.ingredients = ingredients;
        this.descriptions = descriptions;
        this.descriptionOpen = false;
        this.origin = origin;
        this.categories = categories;
        this.isFavorite = isFavorite;
        this.isForPlanning = isForPlanning;
        this.rating = rating;
        this.nbVoter = nbVoter;
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

    public String getPixName() {
        return pixName;
    }

    public void setPixName(String pixName) {
        this.pixName = pixName;
    }

    public String getRecipeType() {
        return recipeType;
    }

    public void setRecipeType(String recipeType) {
        this.recipeType = recipeType;
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

    public List<String> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<String> descriptions) {
        this.descriptions = descriptions;
    }

    public boolean isDescriptionOpen() {
        return descriptionOpen;
    }

    public void setDescriptionOpen(boolean descriptionOpen) {
        this.descriptionOpen = descriptionOpen;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public List<CategoryRecipe> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryRecipe> categories) {
        this.categories = categories;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public boolean isForPlanning() {
        return isForPlanning;
    }

    public void setForPlanning(boolean forPlanning) {
        isForPlanning = forPlanning;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getNbVoter() {
        return nbVoter;
    }

    public void setNbVoter(int nbVoter) {
        this.nbVoter = nbVoter;
    }
}
