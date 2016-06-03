package com.toobe.serviceRest;

/**
 * Created by mathilde on 13/03/2016.
 */

import com.toobe.dto.Food;
import com.toobe.dto.Recipe;
import com.toobe.dto.TestObj;
import com.toobe.dto.info.RecipeCategory;
import com.toobe.dto.info.RecipeOrigin;
import com.toobe.dto.info.RecipeType;
import com.toobe.model.ManagerGet;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

//import org.springframework.web.multipart.MultipartFile;

@Path("/")
//localhost:8080/rest/
public class PrivateAdminService {


    /********************* RECIPE *********************/
    @Path("/recipesPublicNotValidated")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getRecipes() {
        List<Recipe> list = ManagerGet.getInstance().getRecipesPublicNotValidated();
        return Response.ok(list).build();
    }
    @Path("/putAdminValidateRecipe/{idRecipe}/{isPublic}")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response putAdminValidateRecipe(@PathParam("idRecipe") Long idRecipe, @PathParam("isPublic") boolean isPublic) {
        ManagerGet.getInstance().putAdminValidateRecipe(idRecipe, isPublic);
        return Response.ok(new TestObj("MOUAHAHAH")).build();
    }




    /********************* FOOD *********************/
    @Path("/foodsNotValidated")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getFoodsNotValidated(){
        List<Food> list = new ManagerGet().getFoodsNotValidated();
        return Response.ok(list).build();
    }
    @Path("/putAdminValidateFood/{idFood}/{idCategory}")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    /* VALIDE FOOD : On valide la new food en lui affectant une category*/
    public Response putAdminValidateFood(@PathParam("idFood") Long idFood, @PathParam("idCategory") Long idCategory) {
        ManagerGet.getInstance().putAdminValidateFood(idFood, idCategory);
        return Response.ok(new TestObj("MOUAHAHAH")).build();
    }
    @Path("/putAdminValidateFoodWithNewName/{newNameFood}/{idFood}/{idCategory}")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    /* VALIDE FOOD with newName : on valide food en lui affectant une category et avec un nouveau nom  */
    public Response putAdminValidateFoodWithNewName(@PathParam("newNameFood") String newNameFood, @PathParam("idFood") Long idFood, @PathParam("idCategory") Long idCategory) {
        ManagerGet.getInstance().putAdminValidateFoodWithNewName(newNameFood, idFood, idCategory);
        return Response.ok(new TestObj("MOUAHAHAH")).build();
    }
    @Path("/putAdminReplaceFood/{idExistingFood}/{idUselessFood}")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    /* REMPLACE FOOD : par une deja existante qui etait la mm chose (on remplacera ds la relation ingredient et supprimera celle creee par le user */
    public Response putAdminReplaceFood(@PathParam("idExistingFood") Long idExistingFood, @PathParam("idUselessFood") Long idUselessFood) {
        ManagerGet.getInstance().putAdminReplaceFood(idExistingFood, idUselessFood);
        return Response.ok(new TestObj("MOUAHAHAH")).build();
    }
    @Path("/deleteFood/{idFood}")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    /* FOOD REFUSEE : on accepte pas la food, ca la supprimera ainsi que la relation ingredient */
    public Response deleteFood(@PathParam("idFood") Long idFood) {
        ManagerGet.getInstance().deleteFood(idFood);
        return Response.ok(new TestObj("MOUAHAHAH")).build();
    }





}
