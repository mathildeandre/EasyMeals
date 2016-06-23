package com.toobe.dto.info;

/**
 * Created by mathilde on 27/03/2016.
 */

/**
{
    {"id":1,"name":"viande","numRank":5}
*/

public class RecipeImage {

    private Long idRecipe;//idRecipe
    private String image;

    public RecipeImage(){

    }

    public RecipeImage(Long idRecipe, String image) {
        this.idRecipe = idRecipe;
        this.image = image;
    }

    public Long getIdRecipe() {
        return idRecipe;
    }

    public void setIdRecipe(Long idRecipe) {
        this.idRecipe = idRecipe;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
