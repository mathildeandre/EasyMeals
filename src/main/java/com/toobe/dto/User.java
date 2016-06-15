package com.toobe.dto;

/**
 * Created by fabien on 28/04/2016.
 */


/**
 {"id":1,"name":"cabillaud","idCategory":5, validated:true}
 */

public class User {
    private Long id;
    private String pseudo;
    private String email;
    private String password;
    private boolean isAdmin;
    private String colorThemeRecipe;

    public User(){
        this.id = new Long(-1);
    }

    public User(Long id, String pseudo, String email) {
        this.id = id;
        this.pseudo = pseudo;
        this.email = email;
    }

    public User(Long id, String pseudo, String email, String password, boolean isAdmin, String colorThemeRecipe) {
        this.id = id;
        this.pseudo = pseudo;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
        this.colorThemeRecipe = colorThemeRecipe;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getColorThemeRecipe() {
        return colorThemeRecipe;
    }

    public void setColorThemeRecipe(String colorThemeRecipe) {
        this.colorThemeRecipe = colorThemeRecipe;
    }
}
