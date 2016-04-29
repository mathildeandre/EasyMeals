package com.toobe.restPOST;

/**
 * Created by mathilde on 13/03/2016.
 */

import com.toobe.dto.TestObj;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/testObjJson")
public class ObjJson {

    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response insertObjTest(TestObj r){
        System.out.println(" course obj json "+r.getName());
        //Boolean rep = new ManagerPost().insertFood();
        return Response.ok("OK ca a bien marche le post ").build();
    }
}
