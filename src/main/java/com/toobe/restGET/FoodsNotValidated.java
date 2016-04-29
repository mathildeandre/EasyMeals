package com.toobe.restGET;

/**
 * Created by mathilde on 13/03/2016.
 */

import com.toobe.dto.Food;
import com.toobe.model.ManagerGet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/foodsNotValidated")
public class FoodsNotValidated {

    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getFoods(){
        List<Food> list = new ManagerGet().getFoodsNotValidated();
        return Response.ok(list).build();
    }
}

