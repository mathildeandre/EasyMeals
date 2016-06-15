package com.toobe.dto.info;

import com.toobe.dto.User;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by fabien on 21/04/2016.
 */
@XmlRootElement
public class ObjToken {

    private boolean isValidToken;
    private String token;
    private String msg;
    private User user;


    public ObjToken(){
        isValidToken = false;
        token = null;
        msg = null;
        user = new User();
    }

    public ObjToken(boolean isValidToken, String token, String msg, User user) {
        this.isValidToken = isValidToken;
        this.token = token;
        this.msg = msg;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
