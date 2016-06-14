package com.toobe.serviceRest;

/**
 * Created by mathilde on 13/03/2016.
 */

import com.toobe.dto.Planning;
import com.toobe.dto.ShoppingCategory;
import com.toobe.dto.info.ObjString;
import com.toobe.model.ManagerBdd;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/")
public class PlanningService {

    @Path("/plannings/user/{idUser}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getPlanningsOfUser(@PathParam("idUser") int idUser){
        List<Planning> list = ManagerBdd.getInstance().getPlanningsOfUser(idUser);
        return Response.ok(list).build();
    }
    @Path("/createPlanning/user/{idUser}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response createPlanning(@PathParam("idUser") int idUser){
        Planning planning = ManagerBdd.getInstance().createPlanning(idUser);
        return Response.ok(planning).build();
    }


    @Path("postNewRecipeCaseMeal")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response postNewRecipeCaseMeal(List<Long> listId){
        System.out.println("POST new recipeCaseMeal  :::  idRecipe "+listId.get(0)+" --- idCaseMeal "+listId.get(1));
        ManagerBdd.getInstance().postNewRecipeCaseMeal(listId.get(0), listId.get(1));
        return Response.ok(new ObjString("MOUAHAHAH")).build();
    }

    @Path("deleteOldRecipeCaseMeal")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response deleteOldRecipeCaseMeal(List<Long> listId){
        System.out.println("DELETE old recipeCaseMeal  :::  idRecipe "+listId.get(0)+" --- idCaseMeal "+listId.get(1));
        ManagerBdd.getInstance().deleteOldRecipeCaseMeal(listId.get(0), listId.get(1));
        return Response.ok(new ObjString("MOUAHAHAH")).build();
    }
    @Path("postNewNamePlanning/{namePlanning}")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response postNewNamePlanning(Long idPlanning, @PathParam("namePlanning") String namePlanning){
        System.out.println("POST new NAME PLANNING  ::: idPlanning: "+idPlanning+" ----  namePlanning: "+namePlanning);
        ManagerBdd.getInstance().postNewNamePlanning(idPlanning, namePlanning);
        return Response.ok(new ObjString("MOUAHAHAH")).build();
    }

    @Path("putLastOpenPlannings") //OLD & NEW
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response putLastOpenPlannings(List<Long> listId){
        ManagerBdd.getInstance().putLastOpenPlannings(listId.get(0), listId.get(1));
        return Response.ok(new ObjString("MOUAHAHAH")).build();
    }
    @Path("putLastOpenNewPlanning") //just NEW
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response putLastOpenNewPlanning(Long idNewOpenPlanning){
        ManagerBdd.getInstance().putLastOpenNewPlanning(idNewOpenPlanning);
        return Response.ok(new ObjString("MOUAHAHAH")).build();
    }



    @Path("putShowWeekMeal/{showWeekMeal}")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response putShowWeekMeal(Long idWeekMeal,  @PathParam("showWeekMeal") boolean showWeekMeal){
        System.out.println("PUT show weekMeal  ::: idWeekMeal: "+idWeekMeal+" ----  showWeekMeal: "+showWeekMeal);
        ManagerBdd.getInstance().putShowWeekMeal(idWeekMeal, showWeekMeal);
        return Response.ok(new ObjString("MOUAHAHAH")).build();
    }
    @Path("putNbPersCaseMeal/{nbPersCaseMeal}")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response putNbPersCaseMeal(Long idCaseMeal, @PathParam("nbPersCaseMeal") int nbPersCaseMeal){
        ManagerBdd.getInstance().putNbPersCaseMeal(idCaseMeal, nbPersCaseMeal);
        return Response.ok(new ObjString("MOUAHAHAH")).build();
    }
    @Path("putNbPersGlobalPlanning/{nbPersGlobal}")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response putNbPersGlobalPlanning(Long idPlanning, @PathParam("nbPersGlobal") int nbPersGlobal){
        ManagerBdd.getInstance().putNbPersGlobalPlanning(idPlanning, nbPersGlobal);
        return Response.ok(new ObjString("MOUAHAHAH")).build();
    }
    @Path("deletePlanningById")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response deletePlanningById(Long idPlanning){
        ManagerBdd.getInstance().deletePlanningById(idPlanning);
        return Response.ok(new ObjString("MOUAHAHAH")).build();
    }

    @Path("/getNamePlanning/{idPlanning}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getNamePlanning(@PathParam("idPlanning") Long idPlanning){
        //localhost:8080/rest/recipe/2
        String namePlanning = ManagerBdd.getInstance().getNamePlanning(idPlanning);
        return Response.ok(new ObjString(namePlanning)).build();
    }


    @Path("/clonePlanning/{idPlanning}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response clonePlanning(@PathParam("idPlanning") Long idPlanning){
        //localhost:8080/rest/recipe/2
        Planning planning = ManagerBdd.getInstance().clonePlanning(idPlanning);
        return Response.ok(planning).build();
    }




    @Path("/planning/{idPlanning}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getPlanningById(@PathParam("idPlanning") Long idPlanning){
        Planning planning = ManagerBdd.getInstance().getPlanningById(idPlanning);
        return Response.ok(planning).build();
    }




    @Path("/createPlanningShopping/{idPlanning}")
    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response createPlanningShopping(@PathParam("idPlanning") Long idPlanning, List<ShoppingCategory> shoppingCategories){
        Planning planning = new ManagerBdd().createPlanningShopping(idPlanning, shoppingCategories);
        return Response.ok(planning).build();
    }


    @Path("/cutShoppingToPlanning/{idPlanning}")
    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response cutShoppingToPlanning(@PathParam("idPlanning") Long idPlanning){
        Planning planning = new ManagerBdd().cutShoppingToPlanning(idPlanning);
        return Response.ok(planning).build();
    }

    /* NO USE
    @Path("/planning/currentUser/{idUser}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getPlanningCurrentOfUser(@PathParam("idUser") int idUser){
        //localhost:8080/rest/recipe/2
        Planning planning = new ManagerBdd().getPlanningCurrentOfUser(idUser);
        return Response.ok(planning).build();
    }
    */


    //POST new rel  recipe-caseMeal
    //DELETE rel recipe-caseMeal
    //PUT change name planning
    //DELETE planning



}
