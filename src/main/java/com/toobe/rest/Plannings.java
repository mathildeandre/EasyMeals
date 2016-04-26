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
import java.util.List;

@Path("/plannings/{idUser}")
public class Plannings {

    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getPlannings(@PathParam("idUser") int idUser){
        //localhost:8080/rest/plannings/2
        List<Planning> list = new ManagerGet().getPlanningsOfUser(idUser);
        return Response.ok(list).build();
    }


}
