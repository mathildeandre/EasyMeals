package com.toobe.serviceRest;

/**
 * Created by mathilde on 13/03/2016.
 */

import com.toobe.dto.Food;
import com.toobe.dto.Recipe;
import com.toobe.dto.info.ObjString;
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
        return Response.ok(new ObjString("MOUAHAHAH")).build();
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
        return Response.ok(new ObjString("MOUAHAHAH")).build();
    }
    @Path("/putAdminValidateFoodWithNewName/{newNameFood}/{idFood}/{idCategory}")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    /* VALIDE FOOD with newName : on valide food en lui affectant une category et avec un nouveau nom  */
    public Response putAdminValidateFoodWithNewName(@PathParam("newNameFood") String newNameFood, @PathParam("idFood") Long idFood, @PathParam("idCategory") Long idCategory) {
        ManagerGet.getInstance().putAdminValidateFoodWithNewName(newNameFood, idFood, idCategory);
        return Response.ok(new ObjString("MOUAHAHAH")).build();
    }
    @Path("/putAdminReplaceFood/{idExistingFood}/{idUselessFood}")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    /* REMPLACE FOOD : par une deja existante qui etait la mm chose (on remplacera ds la relation ingredient et supprimera celle creee par le user */
    public Response putAdminReplaceFood(@PathParam("idExistingFood") Long idExistingFood, @PathParam("idUselessFood") Long idUselessFood) {
        ManagerGet.getInstance().putAdminReplaceFood(idExistingFood, idUselessFood);
        return Response.ok(new ObjString("MOUAHAHAH")).build();
    }
    @Path("/deleteFood/{idFood}")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    /* FOOD REFUSEE : on accepte pas la food, ca la supprimera ainsi que la relation ingredient */
    public Response deleteFood(@PathParam("idFood") Long idFood) {
        ManagerGet.getInstance().deleteFood(idFood);
        return Response.ok(new ObjString("MOUAHAHAH")).build();
    }


    /********************* CATEGORY *********************/
    @Path("/categoriesNotValidated")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getCategoriesNotValidated(){
        List<RecipeCategory> list = new ManagerGet().getCategoriesNotValidated();
        return Response.ok(list).build();
    }
    @Path("/putAdminValidateCategory/{idCategory}")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    /* VALIDE CATEGORY : On valide la new cat*/
    public Response putAdminValidateCategory(@PathParam("idCategory") Long idCategory) {
        ManagerGet.getInstance().putAdminValidateCategory(idCategory);
        return Response.ok(new ObjString("MOUAHAHAH")).build();
    }
    @Path("/putAdminValidateCategoryWithNewName/{newNameCategory}/{idCategory}")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    /* VALIDE CATEGORY with newName : on valide cat  et avec un nouveau nom  */
    public Response putAdminValidateCategoryWithNewName(@PathParam("newNameCategory") String newNameCategory, @PathParam("idCategory") Long idCategory) {
        ManagerGet.getInstance().putAdminValidateCategoryWithNewName(newNameCategory, idCategory);
        return Response.ok(new ObjString("MOUAHAHAH")).build();
    }
    @Path("/putAdminReplaceCategory/{idExistingCategory}/{idUselessCategory}")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    /* REMPLACE CATEGORY : par une deja existante qui etait la mm chose (on remplacera ds la relation avec recipe...et supprimera celle creee par le user */
    public Response putAdminReplaceCategory(@PathParam("idExistingCategory") Long idExistingCategory, @PathParam("idUselessCategory") Long idUselessCategory) {
        ManagerGet.getInstance().putAdminReplaceCategory(idExistingCategory, idUselessCategory);
        return Response.ok(new ObjString("MOUAHAHAH")).build();
    }
    @Path("/deleteCategory/{idCategory}")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    /* CATEGORY REFUSEE : on accepte pas la cat, ca la supprimera ainsi que la relation avec recipe */
    public Response deleteCategory(@PathParam("idCategory") Long idCategory) {
        ManagerGet.getInstance().deleteCategory(idCategory);
        return Response.ok(new ObjString("MOUAHAHAH")).build();
    }



    /********************* SPECIALITY *********************/
    @Path("/specialitiesNotValidated")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getSpecialitiesNotValidated(){
        List<RecipeOrigin> list = new ManagerGet().getSpecialitiesNotValidated();
        return Response.ok(list).build();
    }
    @Path("/putAdminValidateSpeciality/{idSpeciality}")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response putAdminValidateSpeciality(@PathParam("idSpeciality") Long idSpeciality) {
        ManagerGet.getInstance().putAdminValidateSpeciality(idSpeciality);
        return Response.ok(new ObjString("MOUAHAHAH")).build();
    }
    @Path("/putAdminValidateSpecialityWithNewName/{newNameSpeciality}/{idSpeciality}")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response putAdminValidateSpecialityWithNewName(@PathParam("newNameSpeciality") String newNameSpeciality, @PathParam("idSpeciality") Long idSpeciality) {
        ManagerGet.getInstance().putAdminValidateSpecialityWithNewName(newNameSpeciality, idSpeciality);
        return Response.ok(new ObjString("MOUAHAHAH")).build();
    }
    @Path("/putAdminReplaceSpeciality/{idExistingSpeciality}/{idUselessSpeciality}")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response putAdminReplaceSpeciality(@PathParam("idExistingSpeciality") Long idExistingSpeciality, @PathParam("idUselessSpeciality") Long idUselessSpeciality) {
        ManagerGet.getInstance().putAdminReplaceSpeciality(idExistingSpeciality, idUselessSpeciality);
        return Response.ok(new ObjString("MOUAHAHAH")).build();
    }





}
