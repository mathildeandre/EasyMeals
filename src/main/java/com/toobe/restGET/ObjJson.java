package com.toobe.restGET;

/**
 * Created by mathilde on 13/03/2016.
 */

import com.toobe.dto.Recipe;
import com.toobe.dto.TestObj;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/recipe/create")
public class ObjJson {

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response insertObjTest(Recipe r){
        System.out.println(" course obj json "+r.getName());
        //Boolean rep = new ManagerPost().insertFood();
        return Response.ok("OK ca a bien marche le post ").build();
    }
}
