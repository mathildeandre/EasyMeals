package com.toobe.dto.info;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by fabien on 21/04/2016.
 */
@XmlRootElement
public class ObjToken {

    private boolean isValidToken;
    private String token;
    private String msg;
    private String pseudo;
    private Long idUser;


    public ObjToken(){
        isValidToken = false;
        token = null;
        msg = null;
    }

    public ObjToken(boolean isValidToken, String token, String msg, String pseudo, Long idUser) {
        this.isValidToken = isValidToken;
        this.token = token;
        this.msg = msg;
        this.pseudo = pseudo;
        this.idUser = idUser;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean getIsValidToken() {
        return isValidToken;
    }

    public void setIsValidToken(boolean isValidToken) {
        this.isValidToken = isValidToken;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }
}
