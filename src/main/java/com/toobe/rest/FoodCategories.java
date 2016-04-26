package com.toobe.rest;

/**
 * Created by mathilde on 13/03/2016.
 */

import com.toobe.dto.FoodCategory;
import com.toobe.dto.RecipeCategory;
import com.toobe.model.ManagerGet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/foodCategories")
public class FoodCategories {

    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getFoodCategories(){
        List<FoodCategory> list = new ManagerGet().getFoodCategories();
        return Response.ok(list).build();
    }
}
