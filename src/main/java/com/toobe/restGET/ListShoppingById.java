package com.toobe.restGET;

/**
 * Created by mathilde on 13/03/2016.
 */

import com.toobe.dto.ListShopping;
import com.toobe.model.ManagerGet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/listShopping/{idListShopping}")
public class ListShoppingById {

    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getPlanningById(@PathParam("idListShopping") int idListShopping){
        //localhost:8080/rest/recipe/2
        ListShopping listShopping = new ManagerGet().getListShoppingById(idListShopping);
        return Response.ok(listShopping).build();
    }


}
