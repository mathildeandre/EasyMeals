package com.toobe.serviceRest;

/**
 * Created by mathilde on 13/03/2016.
 */

import com.toobe.dto.ListShopping;
import com.toobe.dto.ListShoppingCategory;
import com.toobe.dto.ListShoppingPlanning;
import com.toobe.dto.TestObj;
import com.toobe.model.ManagerGet;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/")
public class ListShoppingService {

    @Path("/listShopping/{idListShopping}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getListShoppingById(@PathParam("idListShopping") int idListShopping){
        //localhost:8080/rest/recipe/2
        com.toobe.dto.ListShopping listShopping = new ManagerGet().getListShoppingById(idListShopping);
        return Response.ok(listShopping).build();
    }

    @Path("/listShoppingPlanning/{idUser}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getListShoppingByUser(@PathParam("idUser") int idUser){
        //localhost:8080/rest/recipe/2
        List<ListShoppingPlanning> listShopping = new ManagerGet().getListsShoppingPlanning(idUser);
        return Response.ok(listShopping).build();
    }
    @Path("/createListShoppingPlanning/{idPlanning}/{idUser}")
    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response createListShoppingPlanning(@PathParam("idPlanning") Long idPlanning, @PathParam("idUser") int idUser, List<ListShoppingCategory> listShoppingCategories){
        ListShoppingPlanning listShoppingPlanning = new ManagerGet().createListShoppingPlanning(idPlanning, idUser, listShoppingCategories);
        return Response.ok(listShoppingPlanning).build();
    }
    @Path("/deleteListShoppingPlanningById")
    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response deleteListShoppingPlanningById(Long idListShoppingPlanning){
        ManagerGet.getInstance().deleteListShoppingPlanningById(idListShoppingPlanning);
        return Response.ok(new TestObj("MOUAHAHAH")).build();
    }

}
