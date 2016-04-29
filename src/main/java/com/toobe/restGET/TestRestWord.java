package com.toobe.restGET;

/**
 * Created by fabien on 18/04/2016.
 */

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/word")
public class TestRestWord {

    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getRecipes(){
        return Response.ok("{\"b\":\"aurevoir789\"}").build();
    }
}
