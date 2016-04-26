package com.toobe.rest;

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

@Path("/planning/{idPlanning}")
public class PlanningById {

    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getPlanningById(@PathParam("idPlanning") int idPlanning){
        //localhost:8080/rest/recipe/2
        Planning planning = new ManagerGet().getPlanningById(idPlanning);
        return Response.ok(planning).build();
    }


}
