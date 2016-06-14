package com.toobe.serviceRest;

/**
 * Created by mathilde on 13/03/2016.
 */

/*
@Path("/")
public class ListShoppingService {

    @Path("/listShopping/{idListShopping}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getListShoppingById(@PathParam("idListShopping") int idListShopping){
        //localhost:8080/rest/recipe/2
        com.toobe.dto.ListShopping listShopping = new ManagerBdd().getListShoppingById(idListShopping);
        return Response.ok(listShopping).build();
    }

    @Path("/listShoppingPlanning/{idUser}")
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getListShoppingByUser(@PathParam("idUser") int idUser){
        //localhost:8080/rest/recipe/2
        List<ListShoppingPlanning> listShopping = new ManagerBdd().getListsShoppingPlanning(idUser);
        return Response.ok(listShopping).build();
    }
    @Path("/createListShoppingPlanning/{idPlanning}/{idUser}")
    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response createListShoppingPlanning(@PathParam("idPlanning") Long idPlanning, @PathParam("idUser") int idUser, List<ListShoppingCategory> listShoppingCategories){
        ListShoppingPlanning listShoppingPlanning = new ManagerBdd().createListShoppingPlanning(idPlanning, idUser, listShoppingCategories);
        return Response.ok(listShoppingPlanning).build();
    }
    @Path("/deleteListShoppingPlanningById")
    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response deleteListShoppingPlanningById(Long idListShoppingPlanning){
        ManagerBdd.getInstance().deleteListShoppingPlanningById(idListShoppingPlanning);
        return Response.ok(new TestObj("MOUAHAHAH")).build();
    }

}
*/