package com.toobe.serviceRest;

/**
 * Created by mathilde on 13/03/2016.
 */

import com.toobe.dto.Recipe;
import com.toobe.dto.RecipeCategory;
import com.toobe.dto.RecipeOrigin;
import com.toobe.model.ManagerGet;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/")
//localhost:8080/rest/
public class RecipeService {

    /*********************/
    /******** GET ********/
    /*********************/

    @Path("recipes/{recipeType}/{idUser}") //recipes/course/2
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getRecipes(@PathParam("recipeType") String recipeType, @PathParam("idUser") int idUser){

        List<Recipe> list = ManagerGet.getInstance().getRecipes(recipeType, idUser);
        return Response.ok(list).build();
    }

    @Path("/recipe/{idRecipe}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getRecipeById(@PathParam("idRecipe") int idRecipe){
        //localhost:8080/rest/recipe/2
        Recipe recipe = ManagerGet.getInstance().getRecipeById(idRecipe);
        return Response.ok(recipe).build();
    }

    @Path("/recipeCategories")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getRecipeCategories(){
        List<RecipeCategory> list = ManagerGet.getInstance().getRecipeCategories();
        return Response.ok(list).build();
    }


    @Path("/recipeOrigins")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getRecipeOrigins(){
        List<RecipeOrigin> list = ManagerGet.getInstance().getRecipeOrigins();
        return Response.ok(list).build();
    }


    @Path("/recipePublicNotValidated/{recipeType}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getRecipes(@PathParam("recipeType") String recipeType){
        List<Recipe> list = ManagerGet.getInstance().getRecipesPublicNotValidated(recipeType);
        return Response.ok(list).build();
    }


    @Path("/recipeTypes")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getRecipeTypes(){
        List<String> list = ManagerGet.getInstance().getRecipeTypes();
        return Response.ok(list).build();
    }


    @Path("recipe/create")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createRecipe(Recipe r){
        return null;
    }



}
