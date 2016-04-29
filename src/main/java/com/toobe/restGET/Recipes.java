package com.toobe.restGET;

/**
 * Created by mathilde on 13/03/2016.
 */

import com.toobe.dto.Recipe;
import com.toobe.model.ManagerGet;

import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/recipes")
//localhost:8080/rest/recipes/
public class Recipes {


    @Path("/{recipeType}/{idUser}") //course/2
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getRecipes(@PathParam("recipeType") String recipeType, @PathParam("idUser") int idUser){

        List<Recipe> list = ManagerGet.getInstance().getRecipes(recipeType, idUser);
        return Response.ok(list).build();
    }

    /*
    @Path("/create")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createRecipe(Recipe r){
        return null;
    }
    */


}
