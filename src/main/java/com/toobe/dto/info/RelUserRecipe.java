package com.toobe.dto.info;

/**
 * Created by mathilde on 27/03/2016.
 */


public class RelUserRecipe {

    private boolean isFavorite;
    private boolean isForPlanning;
    private int ratingUser;
    private boolean isHide;

    public RelUserRecipe(){

    }
    public RelUserRecipe(boolean isFavorite, boolean isForPlanning, int ratingUser, boolean isHide) {
        this.isFavorite = isFavorite;
        this.isForPlanning = isForPlanning;
        this.ratingUser = ratingUser;
        this.isHide = isHide;
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

    public int getRatingUser() {
        return ratingUser;
    }

    public void setRatingUser(int ratingUser) {
        this.ratingUser = ratingUser;
    }

    public boolean isHide() {
        return isHide;
    }

    public void setHide(boolean hide) {
        isHide = hide;
    }
}
