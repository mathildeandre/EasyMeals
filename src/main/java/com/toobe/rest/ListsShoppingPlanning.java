package com.toobe.rest;

/**
 * Created by mathilde on 13/03/2016.
 */

        import com.toobe.dto.ListShoppingPlanning;
        import com.toobe.model.ManagerGet;

        import javax.ws.rs.GET;
        import javax.ws.rs.Path;
        import javax.ws.rs.PathParam;
        import javax.ws.rs.Produces;
        import javax.ws.rs.core.MediaType;
        import javax.ws.rs.core.Response;
        import java.util.List;

@Path("/listShoppingPlanning/{idUser}")
public class ListsShoppingPlanning {

    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getPlanningById(@PathParam("idUser") int idUser){
        //localhost:8080/rest/recipe/2
        List<ListShoppingPlanning> listShopping = new ManagerGet().getListsShoppingPlanning(idUser);
        return Response.ok(listShopping).build();
    }


}
