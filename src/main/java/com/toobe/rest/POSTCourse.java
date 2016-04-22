package com.toobe.rest;

/**
 * Created by mathilde on 13/03/2016.
 */

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.toobe.dto.Recipe;
import com.toobe.dto.TestObj;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/course")
public class POSTCourse {

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response insertFood(TestObj r){
        System.out.println(" course obj json "+r);
        //Boolean rep = new ManagerPost().insertFood();
        return Response.ok("OK ca a bien marche le post ").build();
    }
}
