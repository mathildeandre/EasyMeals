package com.toobe.rest;

/**
 * Created by mathilde on 13/03/2016.
 */

import com.toobe.model.ManagerPost;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/txt")
public class POSTTxt {

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response insertTxt(String txt){
        System.out.println("[foodPost.java -- method:POST] - RECIVING DATA FROM POST : "+txt);
        //Boolean rep = new ManagerPost().insertFood();
        return Response.ok("POST txt OK ").build();
    }
}
