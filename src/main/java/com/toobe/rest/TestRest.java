package com.toobe.rest;

/**
 * Created by mathilde on 13/03/2016.
 */
import com.toobe.dto.Recipe;
import com.toobe.model.RecipeManager;

import java.awt.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
@Path("/users")
public class TestRest {

    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getRecipes(){
        String recipes = null;
        List<Recipe> courseList = new ArrayList<Recipe>();
        courseList = new RecipeManager().getRecipes();
        return Response.ok(courseList).build();
    }

}