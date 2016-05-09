package com.toobe.serviceRest;

/**
 * Created by mathilde on 13/03/2016.
 */

import com.toobe.dto.Ingredient;
import com.toobe.dto.Recipe;
import com.toobe.dto.TestObj;
import com.toobe.dto.info.RecipeDescription;
import com.toobe.dto.info.RecipeCategory;
import com.toobe.dto.info.RecipeOrigin;
import com.toobe.dto.info.RecipeType;
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

        System.out.println("[WEB SERVICE] - fct getRecipes - @Path : 'recipes/"+recipeType+"/"+idUser+"'");
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
        List<RecipeType> list = ManagerGet.getInstance().getRecipeTypes();
        return Response.ok(list).build();
    }

    @Path("recipe/create")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response createRecipe(Recipe recipe){
        ManagerGet.getInstance().createRecipe(recipe);
        /*System.out.println(" recipe.name "+recipe.getName());
        System.out.println(" recipe.pixName "+recipe.getPixName());
        System.out.println(" recipe.Userid "+recipe.getUser().getId());
        System.out.println(" recipe.recipeType "+recipe.getRecipeType().getNameType());
        System.out.println(" recipe.nbPers "+recipe.getNbPerson());
        System.out.println(" recipe.timeCooking "+recipe.getTimeCooking());
        System.out.println(" recipe.timePreparation "+recipe.getTimePreparation());
        System.out.println(" recipe.origin "+recipe.getOrigin().getName());
        List<Ingredient> listIngredient = recipe.getIngredients();
        for(int i=0; i<listIngredient.size(); i++){
            System.out.println(" ingredient : "+listIngredient.get(i).getQty()+listIngredient.get(i).getUnit()
                    +" de "+listIngredient.get(i).getFood().getName());
        }
        List<RecipeCategory> listCategory = recipe.getCategories();
        for(int i=0; i<listCategory.size(); i++){
            System.out.println(" category : "+listCategory.get(i).getName());
        }
        List<RecipeDescription> listDescription = recipe.getDescriptions();
        for(int i=0; i<listDescription.size(); i++){
            System.out.println(" description no "+listDescription.get(i).getNumDescrip()+" : "+listDescription.get(i).getName());
        }
        */


        //Boolean rep = new ManagerPost().insertFood();
        return Response.ok(new TestObj("MOUAHAHAH")).build();
    }



}
