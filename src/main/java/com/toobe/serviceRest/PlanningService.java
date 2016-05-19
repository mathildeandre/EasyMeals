package com.toobe.serviceRest;

/**
 * Created by mathilde on 13/03/2016.
 */

import com.toobe.dto.Planning;
import com.toobe.dto.TestObj;
import com.toobe.model.ManagerGet;

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
        //localhost:8080/rest/plannings/2
        List<Planning> list = new ManagerGet().getPlanningsOfUser(idUser);
        return Response.ok(list).build();
    }
    @Path("/createPlanning/user/{idUser}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response createPlanning(@PathParam("idUser") int idUser){
        //localhost:8080/rest/recipe/2
        Planning planning = new ManagerGet().createPlanning(idUser);
        return Response.ok(planning).build();
    }


    @Path("postNewRecipeCaseMeal")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response postNewRecipeCaseMeal(List<Long> listId){
        System.out.println("POST new recipeCaseMeal  :::  idRecipe "+listId.get(0)+" --- idCaseMeal "+listId.get(1));
        ManagerGet.getInstance().postNewRecipeCaseMeal(listId.get(0), listId.get(1));
        //Boolean rep = new ManagerPost().insertFood();
        return Response.ok(new TestObj("MOUAHAHAH")).build();
    }

    @Path("deleteOldRecipeCaseMeal")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response deleteOldRecipeCaseMeal(List<Long> listId){
        System.out.println("DELETE old recipeCaseMeal  :::  idRecipe "+listId.get(0)+" --- idCaseMeal "+listId.get(1));
        ManagerGet.getInstance().deleteOldRecipeCaseMeal(listId.get(0), listId.get(1));
        //Boolean rep = new ManagerPost().insertFood();
        return Response.ok(new TestObj("MOUAHAHAH")).build();
    }
    @Path("postNewNamePlanning/{namePlanning}")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response postNewNamePlanning(Long idPlanning, @PathParam("namePlanning") String namePlanning){
        System.out.println("POST new NAME PLANNING  ::: idPlanning: "+idPlanning+" ----  namePlanning: "+namePlanning);
        ManagerGet.getInstance().postNewNamePlanning(idPlanning, namePlanning);
        //Boolean rep = new ManagerPost().insertFood();
        return Response.ok(new TestObj("MOUAHAHAH")).build();
    }
    @Path("putLastOpenPlanning")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response putLastOpenPlanning(List<Long> listId){
        System.out.println("POST new NAME PLANNING  ::: idOldOpenPlanning: "+listId.get(0)+" ----  idNewOpenPlanning: "+listId.get(1));
        ManagerGet.getInstance().putLastOpenPlanning(listId.get(0), listId.get(1));
        //Boolean rep = new ManagerPost().insertFood();
        return Response.ok(new TestObj("MOUAHAHAH")).build();
    }
    @Path("putShowWeekMeal/{showWeekMeal}")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response putShowWeekMeal(Long idWeekMeal,  @PathParam("showWeekMeal") Boolean showWeekMeal){
        System.out.println("PUT show weekMeal  ::: idWeekMeal: "+idWeekMeal+" ----  showWeekMeal: "+showWeekMeal);
        ManagerGet.getInstance().putShowWeekMeal(idWeekMeal, showWeekMeal);
        //Boolean rep = new ManagerPost().insertFood();
        return Response.ok(new TestObj("MOUAHAHAH")).build();
    }
    @Path("putNbPersCaseMeal/{nbPersCaseMeal}")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response putNbPersCaseMeal(Long idCaseMeal, @PathParam("nbPersCaseMeal") int nbPersCaseMeal){
        ManagerGet.getInstance().putNbPersCaseMeal(idCaseMeal, nbPersCaseMeal);
        //Boolean rep = new ManagerPost().insertFood();
        return Response.ok(new TestObj("MOUAHAHAH")).build();
    }
    @Path("putNbPersGlobalPlanning/{nbPersGlobal}")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response putNbPersGlobalPlanning(Long idPlanning, @PathParam("nbPersGlobal") int nbPersGlobal){
        ManagerGet.getInstance().putNbPersGlobalPlanning(idPlanning, nbPersGlobal);
        return Response.ok(new TestObj("MOUAHAHAH")).build();
    }
    @Path("deletePlanningById")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response deletePlanningById(Long idPlanning){
        ManagerGet.getInstance().deletePlanningById(idPlanning);
        return Response.ok(new TestObj("MOUAHAHAH")).build();
    }





    @Path("/planning/{idPlanning}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getPlanningById(@PathParam("idPlanning") Long idPlanning){
        //localhost:8080/rest/recipe/2
        Planning planning = new ManagerGet().getPlanningById(idPlanning);
        return Response.ok(planning).build();
    }

    /* NO USE
    @Path("/planning/currentUser/{idUser}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getPlanningCurrentOfUser(@PathParam("idUser") int idUser){
        //localhost:8080/rest/recipe/2
        Planning planning = new ManagerGet().getPlanningCurrentOfUser(idUser);
        return Response.ok(planning).build();
    }
    */


    //POST new rel  recipe-caseMeal
    //DELETE rel recipe-caseMeal
    //PUT change name planning
    //DELETE planning



}
