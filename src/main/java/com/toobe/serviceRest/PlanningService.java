package com.toobe.serviceRest;

/**
 * Created by mathilde on 13/03/2016.
 */

import com.toobe.dto.Planning;
import com.toobe.dto.ShoppingCategory;
import com.toobe.dto.info.ObjString;
import com.toobe.model.ManagerBdd;
import com.toobe.model.ManagerUser;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/")
public class PlanningService {



    private ManagerUser managerUser = new ManagerUser();


    @Path("/plannings/user/{idUser}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getPlanningsOfUser(@PathParam("idUser") int idUser, @HeaderParam("Authorization") String authorization){
        List<Planning> list = null;
        if(!authorization.equals("No Autorization")){
            String strToken = authorization.substring(7); //substring(7) will remove "Bearer "
            if(managerUser.verifyTokenOfUser(new Long(idUser), strToken)){
                System.out.println("[getPlanningsOfUser] - TOKEN CORRECT ! plannings returned");
                list = ManagerBdd.getInstance().getPlanningsOfUser(idUser);
            }else{
                System.out.println("[getPlanningsOfUser] - TOKEN IN-CORRECT ! - nothing into bdd");
            }
        }else{
            System.out.println("[getPlanningsOfUser] - No Autorization");
        }
        return Response.ok(list).build();
    }
    @Path("/createPlanning/user/{idUser}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response createPlanning(@PathParam("idUser") int idUser, @HeaderParam("Authorization") String authorization){
        Planning planning = null;
        if(!authorization.equals("No Autorization")){
            String strToken = authorization.substring(7); //substring(7) will remove "Bearer "
            if(managerUser.verifyTokenOfUser(new Long(idUser), strToken)){
                System.out.println("[createPlanning] - TOKEN CORRECT ! planning returned");
                planning = ManagerBdd.getInstance().createPlanning(idUser);
            }else{
                System.out.println("[createPlanning] - TOKEN IN-CORRECT ! - nothing into bdd");
            }
        }else{
            System.out.println("[createPlanning] - No Autorization");
        }
        return Response.ok(planning).build();
    }


    @Path("postNewRecipeCaseMeal/{idUser}")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response postNewRecipeCaseMeal(List<Long> listId, @PathParam("idUser") int idUser, @HeaderParam("Authorization") String authorization){
        if(!authorization.equals("No Autorization")){
            String strToken = authorization.substring(7); //substring(7) will remove "Bearer "
            if(managerUser.verifyTokenOfUser(new Long(idUser), strToken)){
                System.out.println("[postNewRecipeCaseMeal] - TOKEN CORRECT ! put bdd :   idRecipe "+listId.get(0)+" --- idCaseMeal "+listId.get(1));
                ManagerBdd.getInstance().postNewRecipeCaseMeal(listId.get(0), listId.get(1));
            }else{
                System.out.println("[postNewRecipeCaseMeal] - TOKEN IN-CORRECT ! - nothing into bdd");
            }
        }else{
            System.out.println("[postNewRecipeCaseMeal] - No Autorization");
        }

        return Response.ok(new ObjString("MOUAHAHAH")).build();
    }

    @Path("deleteOldRecipeCaseMeal/{idUser}")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response deleteOldRecipeCaseMeal(List<Long> listId, @PathParam("idUser") int idUser, @HeaderParam("Authorization") String authorization){
        if(!authorization.equals("No Autorization")){
            String strToken = authorization.substring(7); //substring(7) will remove "Bearer "
            if(managerUser.verifyTokenOfUser(new Long(idUser), strToken)){
                System.out.println("[deleteOldRecipeCaseMeal] - TOKEN CORRECT ! DELETE old recipeCaseMeal  :::  idRecipe "+listId.get(0)+" --- idCaseMeal "+listId.get(1));
                ManagerBdd.getInstance().deleteOldRecipeCaseMeal(listId.get(0), listId.get(1));
            }else{
                System.out.println("[deleteOldRecipeCaseMeal] - TOKEN IN-CORRECT ! - nothing into bdd");
            }
        }else{
            System.out.println("[deleteOldRecipeCaseMeal] - No Autorization");
        }
        return Response.ok(new ObjString("MOUAHAHAH")).build();
    }
    @Path("postNewNamePlanning/{namePlanning}/{idUser}")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response postNewNamePlanning(Long idPlanning, @PathParam("namePlanning") String namePlanning, @PathParam("idUser") int idUser, @HeaderParam("Authorization") String authorization){
        if(!authorization.equals("No Autorization")){
            String strToken = authorization.substring(7); //substring(7) will remove "Bearer "
            if(managerUser.verifyTokenOfUser(new Long(idUser), strToken)){
                System.out.println("[postNewNamePlanning] - TOKEN CORRECT ! POST new NAME PLANNING  ::: idPlanning: "+idPlanning+" ----  namePlanning: "+namePlanning);
                ManagerBdd.getInstance().postNewNamePlanning(idPlanning, namePlanning);
            }else{
                System.out.println("[postNewNamePlanning] - TOKEN IN-CORRECT ! - nothing into bdd");
            }
        }else{
            System.out.println("[postNewNamePlanning] - No Autorization");
        }
        return Response.ok(new ObjString("MOUAHAHAH")).build();
    }

    @Path("putLastOpenPlannings/{idUser}") //OLD & NEW
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response putLastOpenPlannings(List<Long> listId, @PathParam("idUser") int idUser, @HeaderParam("Authorization") String authorization){
        if(!authorization.equals("No Autorization")){
            String strToken = authorization.substring(7); //substring(7) will remove "Bearer "
            if(managerUser.verifyTokenOfUser(new Long(idUser), strToken)){
                System.out.println("[putLastOpenPlannings] - TOKEN CORRECT ! put into bdd");
                ManagerBdd.getInstance().putLastOpenPlannings(listId.get(0), listId.get(1));
            }else{
                System.out.println("[putLastOpenPlannings] - TOKEN IN-CORRECT ! - nothing into bdd");
            }
        }else{
            System.out.println("[putLastOpenPlannings] - No Autorization");
        }
        return Response.ok(new ObjString("MOUAHAHAH")).build();
    }
    @Path("putLastOpenNewPlanning/{idUser}") //just NEW
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response putLastOpenNewPlanning(Long idNewOpenPlanning, @PathParam("idUser") int idUser, @HeaderParam("Authorization") String authorization){
        if(!authorization.equals("No Autorization")){
            String strToken = authorization.substring(7); //substring(7) will remove "Bearer "
            if(managerUser.verifyTokenOfUser(new Long(idUser), strToken)){
                System.out.println("[putLastOpenNewPlanning] - TOKEN CORRECT ! put into bdd");
                ManagerBdd.getInstance().putLastOpenNewPlanning(idNewOpenPlanning);
            }else{
                System.out.println("[putLastOpenNewPlanning] - TOKEN IN-CORRECT ! - nothing into bdd");
            }
        }else{
            System.out.println("[putLastOpenNewPlanning] - No Autorization");
        }
        return Response.ok(new ObjString("MOUAHAHAH")).build();
    }



    @Path("putShowWeekMeal/{showWeekMeal}/{idUser}")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response putShowWeekMeal(Long idWeekMeal,  @PathParam("showWeekMeal") boolean showWeekMeal, @PathParam("idUser") int idUser, @HeaderParam("Authorization") String authorization){
        if(!authorization.equals("No Autorization")){
            String strToken = authorization.substring(7); //substring(7) will remove "Bearer "
            if(managerUser.verifyTokenOfUser(new Long(idUser), strToken)){
                System.out.println("[putShowWeekMeal] - TOKEN CORRECT ! PUT show weekMeal  ::: idWeekMeal: "+idWeekMeal+" ----  showWeekMeal: "+showWeekMeal);
                ManagerBdd.getInstance().putShowWeekMeal(idWeekMeal, showWeekMeal);
            }else{
                System.out.println("[putShowWeekMeal] - TOKEN IN-CORRECT ! - nothing into bdd");
            }
        }else{
            System.out.println("[putShowWeekMeal] - No Autorization");
        }
        return Response.ok(new ObjString("MOUAHAHAH")).build();
    }

    @Path("putNbPersCaseMeal/{nbPersCaseMeal}/{idUser}")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response putNbPersCaseMeal(Long idCaseMeal, @PathParam("nbPersCaseMeal") int nbPersCaseMeal, @PathParam("idUser") int idUser, @HeaderParam("Authorization") String authorization){
        if(!authorization.equals("No Autorization")){
            String strToken = authorization.substring(7); //substring(7) will remove "Bearer "
            if(managerUser.verifyTokenOfUser(new Long(idUser), strToken)){
                System.out.println("[putNbPersCaseMeal] - TOKEN CORRECT ! PUT into bdd");
                ManagerBdd.getInstance().putNbPersCaseMeal(idCaseMeal, nbPersCaseMeal);
            }else{
                System.out.println("[putNbPersCaseMeal] - TOKEN IN-CORRECT ! - nothing into bdd");
            }
        }else{
            System.out.println("[putNbPersCaseMeal] - No Autorization");
        }
        return Response.ok(new ObjString("MOUAHAHAH")).build();
    }
    @Path("putNbPersGlobalPlanning/{nbPersGlobal}/{idUser}")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response putNbPersGlobalPlanning(Long idPlanning, @PathParam("nbPersGlobal") int nbPersGlobal, @PathParam("idUser") int idUser, @HeaderParam("Authorization") String authorization){
        if(!authorization.equals("No Autorization")){
            String strToken = authorization.substring(7); //substring(7) will remove "Bearer "
            if(managerUser.verifyTokenOfUser(new Long(idUser), strToken)){
                System.out.println("[putNbPersGlobalPlanning] - TOKEN CORRECT ! PUT into bdd");
                ManagerBdd.getInstance().putNbPersGlobalPlanning(idPlanning, nbPersGlobal);
            }else{
                System.out.println("[putNbPersGlobalPlanning] - TOKEN IN-CORRECT ! - nothing into bdd");
            }
        }else{
            System.out.println("[putNbPersGlobalPlanning] - No Autorization");
        }
        return Response.ok(new ObjString("MOUAHAHAH")).build();
    }
    @Path("deletePlanningById/{idUser}")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response deletePlanningById(Long idPlanning, @PathParam("idUser") int idUser, @HeaderParam("Authorization") String authorization){
        if(!authorization.equals("No Autorization")){
            String strToken = authorization.substring(7); //substring(7) will remove "Bearer "
            if(managerUser.verifyTokenOfUser(new Long(idUser), strToken)){
                System.out.println("[deletePlanningById] - TOKEN CORRECT ! delete from bdd");
                ManagerBdd.getInstance().deletePlanningById(idPlanning);
            }else{
                System.out.println("[deletePlanningById] - TOKEN IN-CORRECT ! - nothing into bdd");
            }
        }else{
            System.out.println("[deletePlanningById] - No Autorization");
        }
        return Response.ok(new ObjString("MOUAHAHAH")).build();
    }

    @Path("/getNamePlanning/{idPlanning}/{idUser}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getNamePlanning(@PathParam("idPlanning") Long idPlanning, @PathParam("idUser") int idUser, @HeaderParam("Authorization") String authorization){
        String namePlanning = null;
        if(!authorization.equals("No Autorization")){
            String strToken = authorization.substring(7); //substring(7) will remove "Bearer "
            if(managerUser.verifyTokenOfUser(new Long(idUser), strToken)){
                System.out.println("[getNamePlanning] - TOKEN CORRECT ! get from bdd");
                namePlanning = ManagerBdd.getInstance().getNamePlanning(idPlanning);
            }else{
                System.out.println("[getNamePlanning] - TOKEN IN-CORRECT ! - nothing into bdd");
            }
        }else{
            System.out.println("[getNamePlanning] - No Autorization");
        }
        return Response.ok(new ObjString(namePlanning)).build();
    }


    @Path("/clonePlanning/{idPlanning}/{idUser}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response clonePlanning(@PathParam("idPlanning") Long idPlanning, @PathParam("idUser") int idUser, @HeaderParam("Authorization") String authorization){
        Planning planning = null;
        if(!authorization.equals("No Autorization")){
            String strToken = authorization.substring(7); //substring(7) will remove "Bearer "
            if(managerUser.verifyTokenOfUser(new Long(idUser), strToken)){
                System.out.println("[clonePlanning] - TOKEN CORRECT ! clone into bdd");
                planning = ManagerBdd.getInstance().clonePlanning(idPlanning);
            }else{
                System.out.println("[clonePlanning] - TOKEN IN-CORRECT ! - nothing into bdd");
            }
        }else{
            System.out.println("[clonePlanning] - No Autorization");
        }
        return Response.ok(planning).build();
    }






    @Path("/createPlanningShopping/{idPlanning}/{idUser}")
    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response createPlanningShopping(@PathParam("idPlanning") Long idPlanning, List<ShoppingCategory> shoppingCategories, @PathParam("idUser") int idUser, @HeaderParam("Authorization") String authorization){
        Planning planning = null;
        if(!authorization.equals("No Autorization")){
            String strToken = authorization.substring(7); //substring(7) will remove "Bearer "
            if(managerUser.verifyTokenOfUser(new Long(idUser), strToken)){
                System.out.println("[createPlanningShopping] - TOKEN CORRECT ! create into bdd");
                planning = ManagerBdd.getInstance().createPlanningShopping(idPlanning, shoppingCategories);
            }else{
                System.out.println("[createPlanningShopping] - TOKEN IN-CORRECT ! - nothing into bdd");
            }
        }else{
            System.out.println("[createPlanningShopping] - No Autorization");
        }
        return Response.ok(planning).build();
    }


    @Path("/cutShoppingToPlanning/{idPlanning}/{idUser}")
    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response cutShoppingToPlanning(@PathParam("idPlanning") Long idPlanning, @PathParam("idUser") int idUser, @HeaderParam("Authorization") String authorization){
        Planning planning = null;
        if(!authorization.equals("No Autorization")){
            String strToken = authorization.substring(7); //substring(7) will remove "Bearer "
            if(managerUser.verifyTokenOfUser(new Long(idUser), strToken)){
                System.out.println("[cutShoppingToPlanning] - TOKEN CORRECT ! cut from bdd");
                planning = ManagerBdd.getInstance().cutShoppingToPlanning(idPlanning);
            }else{
                System.out.println("[cutShoppingToPlanning] - TOKEN IN-CORRECT ! - nothing into bdd");
            }
        }else{
            System.out.println("[cutShoppingToPlanning] - No Autorization");
        }
        return Response.ok(planning).build();
    }

    /* NO USE



    @Path("/planning/{idPlanning}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getPlanningById(@PathParam("idPlanning") Long idPlanning){
        Planning planning = ManagerBdd.getInstance().getPlanningById(idPlanning);
        return Response.ok(planning).build();
    }




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
