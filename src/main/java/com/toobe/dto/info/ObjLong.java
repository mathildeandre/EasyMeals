package com.toobe.dto.info;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by fabien on 21/04/2016.
 */
@XmlRootElement
public class ObjLong {

    /*
    public TestObj(){

    }*/

    private Long id;

    public ObjLong(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
