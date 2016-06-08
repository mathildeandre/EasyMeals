package com.toobe.serviceRest;

/**
 * Created by mathilde on 13/03/2016.
 */

import com.toobe.dto.Recipe;
import com.toobe.dto.info.ObjString;
import com.toobe.dto.info.RecipeCategory;
import com.toobe.dto.info.RecipeOrigin;
import com.toobe.dto.info.RecipeType;
import com.toobe.model.ManagerGet;

import javax.imageio.ImageIO;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

//import org.springframework.web.multipart.MultipartFile;

@Path("/")
//localhost:8080/rest/
public class UserService {

    @Path("updateBddColor/{colorValue}/{idUser}")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateBddColor(@PathParam("colorValue") String colorValue, @PathParam("idUser") Long idUser) {
        ManagerGet.getInstance().updateBddColor(colorValue, idUser);
        return Response.ok(new ObjString("MOUAHAHAH")).build();
    }
    @Path("/recipeCategories/{idUser}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getRecipeCategories(@PathParam("idUser") Long idUser) {
        List<RecipeCategory> list = ManagerGet.getInstance().getRecipeCategories(idUser);
        return Response.ok(list).build();
    }
    @Path("/recipeOrigins/{idUser}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getRecipeOrigins(@PathParam("idUser") Long idUser) {
        List<RecipeOrigin> list = ManagerGet.getInstance().getRecipeOrigins(idUser);
        return Response.ok(list).build();
    }


}
