package com.toobe.rest;

/**
 * Created by mathilde on 13/03/2016.
 */

import com.toobe.model.ManagerPost;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/food")
public class POSTFood {

    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    public Response insertFood(String txt){
        System.out.println("[foodPost.java -- method:POST] - RECIVING DATA FROM POST : "+txt);
        //Boolean rep = new ManagerPost().insertFood();
        return Response.ok("OK ca a bien marche le post ''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''").build();
    }
}
