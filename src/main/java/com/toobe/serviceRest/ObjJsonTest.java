package com.toobe.serviceRest;

/**
 * Created by mathilde on 13/03/2016.
 */

import com.toobe.dto.Ingredient;
import com.toobe.dto.Recipe;
import com.toobe.dto.TestObj;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/recipe/createTest") //not used
public class ObjJsonTest {

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response insertObjTest(Recipe r){
        System.out.println(" recipe.name "+r.getName());
        System.out.println(" recipe.recipeType "+r.getRecipeType().getNameType());
        System.out.println(" recipe.nbPers "+r.getNbPerson());
        System.out.println(" recipe.timeCooking "+r.getTimeCooking());
        System.out.println(" recipe.timePreparation "+r.getTimePreparation());
        //Boolean rep = new ManagerPost().insertFood();
        return Response.ok(new TestObj("reponse ok")).build();
    }
}
