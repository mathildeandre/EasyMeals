package com.toobe.dto.info;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * Created by fabien on 21/04/2016.
 */
@XmlRootElement
public class ObjString {

    /*
    public TestObj(){

    }*/

    private String name;

    public ObjString(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
