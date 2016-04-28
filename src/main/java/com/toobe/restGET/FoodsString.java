package com.toobe.restGET;

/**
 * Created by mathilde on 13/03/2016.
 */

import com.toobe.model.ManagerGet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/foodsString")
public class FoodsString {

    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getFoods(){
        List<String> list = new ManagerGet().getFoodsString();
        return Response.ok(list).build();
    }
}

