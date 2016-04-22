package com.toobe.rest;

/**
 * Created by mathilde on 13/03/2016.
 */

import com.toobe.dto.Recipe;
import com.toobe.model.ManagerGet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/recipes/{recipeType}")
public class Recipes {

    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getCourses(@PathParam("recipeType") String recipeType){
        //localhost:8080/rest/recipes/course
        List<Recipe> list = new ManagerGet().getRecipes(recipeType);
        return Response.ok(list).build();
    }
}
