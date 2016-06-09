package com.toobe.serviceRest;

/**
 * Created by mathilde on 13/03/2016.
 */

import com.toobe.dto.Ingredient;
import com.toobe.dto.Recipe;
import com.toobe.dto.info.ObjString;
import com.toobe.dto.info.RecipeDescription;
import com.toobe.dto.info.RecipeCategory;
import com.toobe.dto.info.RecipeOrigin;
import com.toobe.dto.info.RecipeType;
import com.toobe.model.ManagerGet;
//import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

@Path("/")
//localhost:8080/rest/
public class RecipeService {

    /*********************/
    /******** GET ********/
    /*********************/
    /*
    @Path("recipes/{recipeType}/{idUser}") //recipes/course/2
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getRecipes(@PathParam("recipeType") String recipeType, @PathParam("idUser") int idUser){

        System.out.println("[WEB SERVICE] - fct getRecipes - @Path : 'recipes/"+recipeType+"/"+idUser+"'");
        List<Recipe> list = ManagerGet.getInstance().getRecipes(recipeType, idUser);
        return Response.ok(list).build();
    }
    */
    @Path("recipes/{idUser}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getRecipesForUser(@PathParam("idUser") Long idUser) {
        List<Recipe> list = ManagerGet.getInstance().getRecipesForUser(idUser);
        return Response.ok(list).build();
    }

    @Path("/recipe/{idRecipe}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getRecipeById(@PathParam("idRecipe") Long idRecipe) {
        //localhost:8080/rest/recipe/2
        Recipe recipe = ManagerGet.getInstance().getRecipeById(idRecipe);
        return Response.ok(recipe).build();
    }


    @Path("/recipeTypes")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getRecipeTypes() {
        List<RecipeType> list = ManagerGet.getInstance().getRecipeTypes();
        return Response.ok(list).build();
    }

    @Path("recipe/create")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createRecipe(Recipe recipe) {
        Recipe newRecipe = ManagerGet.getInstance().createRecipe(recipe);
        /*System.out.println(" recipe.name "+recipe.getName());
        System.out.println(" recipe.pixName "+recipe.getPixName());
        System.out.println(" recipe.Userid "+recipe.getUser().getId());
        System.out.println(" recipe.recipeType "+recipe.getRecipeType().getNameType());
        System.out.println(" recipe.nbPers "+recipe.getNbPerson());
        System.out.println(" recipe.timeCooking "+recipe.getTimeCooking());
        System.out.println(" recipe.timePreparation "+recipe.getTimePreparation());
        System.out.println(" recipe.origin "+recipe.getOrigin().getName());
        List<Ingredient> listIngredient = recipe.getIngredients();
        for(int i=0; i<listIngredient.size(); i++){
            System.out.println(" ingredient : "+listIngredient.get(i).getQty()+listIngredient.get(i).getUnit()
                    +" de "+listIngredient.get(i).getFood().getName());
        }
        List<RecipeCategory> listCategory = recipe.getCategories();
        for(int i=0; i<listCategory.size(); i++){
            System.out.println(" category : "+listCategory.get(i).getName());
        }
        List<RecipeDescription> listDescription = recipe.getDescriptions();
        for(int i=0; i<listDescription.size(); i++){
            System.out.println(" description no "+listDescription.get(i).getNumDescrip()+" : "+listDescription.get(i).getName());
        }
        */


        //Boolean rep = new ManagerPost().insertFood();
        return Response.ok(newRecipe).build();
    }

    @Path("recipe/image")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public Response sendImage(InputStream img) {
        //InputStream img = null;
        try {
            //img = fileB.getInputStream();

            System.out.println(img);

            File file = new File("C:\\Users\\fabien\\IdeaProjects\\EasyMeals\\newFile2.jpg");
            OutputStream os2 = null;
            os2 = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;

            while ((len = img.read(buf)) > 0) {
                os2.write(buf, 0, len);
            }




            //URL urlImage = new URL(adresse);
            BufferedImage image = ImageIO.read(file);
            System.out.println("h="+image.getHeight());
            System.out.println("w="+image.getWidth());
            image = resizeIMAGE(image,200,140);
            File file2 = new File("C:\\Users\\fabien\\IdeaProjects\\EasyMeals\\newFile7.jpg");
            ImageIO.write(image, "png", file2);





            //os2.close();
            img.close();
            //writer.dispose();





        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Boolean rep = new ManagerPost().insertFood();
        return Response.ok(new ObjString("MOUAHAHAH")).build();
    }


    public static BufferedImage resizeIMAGE(BufferedImage image, int largeur, int hauteur) {

        BufferedImage buf = new BufferedImage(largeur, hauteur,
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = buf.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(image, 0, 0, largeur, hauteur, null);
        g.dispose();
        return buf;
    }


    @Path("putIsFavorite/{idRecipe}/{idUser}")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response putIsFavorite(@PathParam("idRecipe") Long idRecipe, @PathParam("idUser") Long idUser, boolean isFavorite) {
        ManagerGet.getInstance().putIsFavorite(idRecipe, idUser, isFavorite);
        return Response.ok(new ObjString("MOUAHAHAH")).build();
    }

    @Path("putIsForPlanning/{idRecipe}/{idUser}")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response putIsForPlanning(@PathParam("idRecipe") Long idRecipe, @PathParam("idUser") Long idUser, boolean isForPlanning) {
        ManagerGet.getInstance().putIsForPlanning(idRecipe, idUser, isForPlanning);
        return Response.ok(new ObjString("MOUAHAHAH")).build();
    }

    @Path("putRatingUser/{idRecipe}/{idUser}")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response putRatingUser(@PathParam("idRecipe") Long idRecipe, @PathParam("idUser") Long idUser, int ratingUser) {
        ManagerGet.getInstance().putRatingUser(idRecipe, idUser, ratingUser);
        return Response.ok(new ObjString("MOUAHAHAH")).build();
    }

    @Path("putIncrNumRankCategory/{idRecipeCategory}/{idUser}")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response putIncrNumRankCategory(@PathParam("idRecipeCategory") Long idRecipeCategory, @PathParam("idUser") Long idUser) {
        ManagerGet.getInstance().putIncrNumRankCategory(idRecipeCategory, idUser);
        return Response.ok(new ObjString("MOUAHAHAH")).build();
    }
    @Path("putIncrNumRankOrigin/{idRecipeOrigin}/{idUser}")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response putIncrNumRankOrigin(@PathParam("idRecipeOrigin") Long idRecipeOrigin, @PathParam("idUser") Long idUser) {
        ManagerGet.getInstance().putIncrNumRankOrigin(idRecipeOrigin, idUser);
        return Response.ok(new ObjString("MOUAHAHAH")).build();
    }


}
