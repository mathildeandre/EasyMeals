package com.toobe.serviceRest;

/**
 * Created by mathilde on 13/03/2016.
 */

import com.toobe.dto.Food;
import com.toobe.dto.FoodCategory;
import com.toobe.model.ManagerBdd;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/")
public class FoodService {

    @Path("/foods")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getFoods(){
        List<Food> list = new ManagerBdd().getFoods();
        return Response.ok(list).build();
    }

    @Path("/foodCategories")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getFoodCategories(){
        List<FoodCategory> list = new ManagerBdd().getFoodCategories();
        return Response.ok(list).build();
    }

    @Path("/foodsString")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getFoodsString(){
        List<String> list = new ManagerBdd().getFoodsString();
        return Response.ok(list).build();
    }
}

