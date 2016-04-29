package com.toobe.restGET;

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

@Path("/recipe/{idRecipe}")
public class RecipeById {

    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getRecipeById(@PathParam("idRecipe") int idRecipe){
        //localhost:8080/rest/recipe/2
        Recipe recipe = new ManagerGet().getRecipeById(idRecipe);
        return Response.ok(recipe).build();
    }


}
