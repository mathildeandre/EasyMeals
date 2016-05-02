package com.toobe.dto;

/**
 * Created by mathilde on 27/03/2016.
 */

import com.toobe.dto.info.RecipeCategory;
import com.toobe.dto.info.RecipeOrigin;
import com.toobe.dto.info.RecipeType;

import java.io.Serializable;
import java.util.List;

/**
        {"id":2,
        "name":"Burritos",
        "pixName":"burritos.jpg",
        "recipeType":"course",
        "nbPerson":9,
        "ingredients":[{"qty":200,"unit":"g","food":"steack hachÃ©","rayonId":2},{"qty":4,"unit":"","food":"sauce burger","rayonId":14}],
        "descriptions":[{"name":"faire cuire steack","noDescip":1},{"name":"mettre dans crepe avec legumes","noDescip":2},{"name":"Votre burritos est pret !","noDescip":3}],
        "descriptionOpen":false,
        "origin":"mexicain",
        "categories":[{"id":1,"name":"viande","noRank":1},{"id":3,"name":"legume","noRank":8}],
        "isFavorite":false,
        "isForPlanning":false,
        "rating":0,
        "nbVoter":0}

 */

public class Recipe implements Serializable{

    private int id;
    private String name;
    private boolean isPublic;
    private int idUser;
    private String pixName;
    private RecipeType recipeType; //STARTER / COURSE / DESERT
    private List<Ingredient> ingredients;
    private List<RecipeDescription> descriptions;
    //private boolean descriptionOpen;
    private RecipeOrigin origin;
    private List<RecipeCategory> categories;
    private boolean isFavorite; //coeur
    private boolean isForPlanning;
    private int nbPerson;
    private int rating;
    private int nbVoter;
    private boolean isValidated;

    public Recipe(){

    }
    public Recipe(int id, String name, boolean isPublic, int idUser, String pixName, RecipeType recipeType, int nbPerson,
                  List<Ingredient> ingredients, List<RecipeDescription> descriptions, RecipeOrigin origin,
                  List<RecipeCategory> categories, boolean isFavorite, boolean isForPlanning, int rating, int nbVoter,
                  boolean isValidated) {
        this.id = id;
        this.name = name;
        this.isPublic = isPublic;
        this.idUser = idUser;
        this.pixName = pixName;
        this.recipeType = recipeType;
        this.ingredients = ingredients;
        this.descriptions = descriptions;
        //this.descriptionOpen = false;
        this.origin = origin;
        this.categories = categories;
        this.isFavorite = isFavorite;
        this.isForPlanning = isForPlanning;
        this.nbPerson = nbPerson;
        this.rating = rating;
        this.nbVoter = nbVoter;
        this.isValidated = isValidated;
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

    public RecipeType getRecipeType() {
        return recipeType;
    }

    public void setRecipeType(RecipeType recipeType) {
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

    public List<RecipeDescription> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<RecipeDescription> descriptions) {
        this.descriptions = descriptions;
    }

    public RecipeOrigin getRecipeOrigin() {
        return origin;
    }

    public void setRecipeOrigin(RecipeOrigin origin) {
        this.origin = origin;
    }

    public List<RecipeCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<RecipeCategory> categories) {
        this.categories = categories;
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

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public boolean isValidated() {
        return isValidated;
    }

    public void setValidated(boolean validated) {
        isValidated = validated;
    }
}
