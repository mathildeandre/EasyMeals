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

    public User(){ }

    public User(Long id, String pseudo, String email, String password) {
        this.id = id;
        this.pseudo = pseudo;
        this.email = email;
        this.password = password;
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
}
