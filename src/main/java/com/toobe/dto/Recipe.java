package com.toobe.dto;

/**
 * Created by mathilde on 27/03/2016.
 */

import com.toobe.dto.info.*;

import java.io.Serializable;
import java.util.List;

/**
        {"id":2,
        "name":"Burritos",
         isPublic : true;
         idUser : {id:2, pseudo: 'mathou', email:'math@gmail.fr'};
        "pixName":"burritos.jpg",
        "recipeType":"course",
        "nbPerson":9,
        "ingredients":[{"qty":200,"unit":"g","food":{"id":1,"name":"cabillaud","idCategory":5, validated:true}},{..},...],
        "descriptions":[{"name":"faire cuire steack","noDescip":1},{"name":"mettre dans crepe avec legumes","noDescip":2},{"name":"Votre burritos est pret !","noDescip":3}],
    x   "descriptionOpen":false,
        "origin":{"id":1,"name":"viande","numRank":5},
        "categories":[{"id":1,"name":"viande","numRank":1},{"id":3,"name":"legume","numRank":8}],
        "isFavorite":false,
        "isForPlanning":false,
        "rating":0,
        "nbVoter":0,
        ratingUser:3
        timeCooking:120,
        timePreparation:170,
        isHide: false;
        isValidated: true;
        }

 */

public class Recipe implements Serializable{

    private int id;
    private String name;
    private boolean isPublic;
    private User user;
    private String pixName;
    private RecipeType recipeType; //STARTER / COURSE / DESERT
    private List<Ingredient> ingredients;
    private List<RecipeDescription> descriptions;
    //private boolean descriptionOpen;
    private RecipeOrigin origin;
    private List<RecipeCategory> categories;
    private int nbPerson;
    private int rating;
    private int nbVoter;
    private int timeCooking;
    private int timePreparation;
    private boolean isValidated;

    /* ceci sera l'obj RelUserRecipe dans le constructor */
    private boolean isFavorite; //coeur
    private boolean isForPlanning;
    private int ratingUser;
    private boolean isHide;


    public Recipe(){
    }

    public Recipe(int id, String name, boolean isPublic, User user, String pixName, RecipeType recipeType,
                  List<Ingredient> ingredients, List<RecipeDescription> descriptions, RecipeOrigin origin,
                  List<RecipeCategory> categories, int nbPerson, int rating, int nbVoter, int timeCooking,
                  int timePreparation, boolean isValidated, RelUserRecipe relUserRecipe) {
        this.id = id;
        this.name = name;
        this.isPublic = isPublic;
        this.user = user;
        this.pixName = pixName;
        this.recipeType = recipeType;
        this.ingredients = ingredients;
        this.descriptions = descriptions;
        this.origin = origin;
        this.categories = categories;
        this.nbPerson = nbPerson;
        this.rating = rating;
        this.nbVoter = nbVoter;
        this.timeCooking = timeCooking;
        this.timePreparation = timePreparation;
        this.isValidated = isValidated;

        this.isFavorite = relUserRecipe.isFavorite();
        this.isForPlanning = relUserRecipe.isForPlanning();
        this.ratingUser = relUserRecipe.getRatingUser();
        this.isHide = relUserRecipe.isHide();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPixName() {
        return pixName;
    }

    public void setPixName(String pixName) {
        this.pixName = pixName;
    }

    public RecipeType getRecipeType() {
        return recipeType;
    }

    public void setRecipeType(RecipeType recipeType) {
        this.recipeType = recipeType;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<RecipeDescription> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<RecipeDescription> descriptions) {
        this.descriptions = descriptions;
    }

    public RecipeOrigin getOrigin() {
        return origin;
    }

    public void setOrigin(RecipeOrigin origin) {
        this.origin = origin;
    }

    public List<RecipeCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<RecipeCategory> categories) {
        this.categories = categories;
    }

    public int getNbPerson() {
        return nbPerson;
    }

    public void setNbPerson(int nbPerson) {
        this.nbPerson = nbPerson;
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

    public int getRatingUser() {
        return ratingUser;
    }

    public void setRatingUser(int ratingUser) {
        this.ratingUser = ratingUser;
    }

    public int getTimeCooking() {
        return timeCooking;
    }

    public void setTimeCooking(int timeCooking) {
        this.timeCooking = timeCooking;
    }

    public int getTimePreparation() {
        return timePreparation;
    }

    public void setTimePreparation(int timePreparation) {
        this.timePreparation = timePreparation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /* BOOLEANS */

    public boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public boolean getIsForPlanning() {
        return isForPlanning;
    }

    public void setIsForPlanning(boolean forPlanning) {
        isForPlanning = forPlanning;
    }

    public boolean getIsHide() {
        return isHide;
    }

    public void setIsHide(boolean hide) {
        isHide = hide;
    }

    public boolean getIsValidated() {
        return isValidated;
    }

    public void setIsValidated(boolean validated) {
        isValidated = validated;
    }
}
