package com.toobe.serviceRest;

/**
 * Created by mathilde on 13/03/2016.
 */

import com.toobe.dto.Planning;
import com.toobe.model.ManagerGet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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






    @Path("/planning/{idPlanning}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getPlanningById(@PathParam("idPlanning") Long idPlanning){
        //localhost:8080/rest/recipe/2
        Planning planning = new ManagerGet().getPlanningById(idPlanning);
        return Response.ok(planning).build();
    }

    @Path("/planning/currentUser/{idUser}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getPlanningCurrentOfUser(@PathParam("idUser") int idUser){
        //localhost:8080/rest/recipe/2
        Planning planning = new ManagerGet().getPlanningCurrentOfUser(idUser);
        return Response.ok(planning).build();
    }


    //POST new rel  recipe-caseMeal
    //DELETE rel recipe-caseMeal
    //PUT change name planning
    //DELETE planning



}
