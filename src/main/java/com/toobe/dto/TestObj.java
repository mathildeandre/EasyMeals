package com.toobe.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * Created by fabien on 21/04/2016.
 */
@XmlRootElement
public class TestObj {

    /*
    public TestObj(){

    }*/

    private String name;

    public TestObj(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
