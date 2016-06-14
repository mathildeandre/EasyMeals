package com.toobe.dto.info;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by fabien on 21/04/2016.
 */
@XmlRootElement
public class ObjToken {

    private String token;


    public ObjToken(){}
    public ObjToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
