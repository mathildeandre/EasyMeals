package com.toobe.serviceRest;

/**
 * Created by mathilde on 13/03/2016.
 */

import com.toobe.dto.Recipe;
import com.toobe.dto.info.*;
import com.toobe.model.ManagerBdd;
import com.toobe.model.ManagerUser;
//import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

@Path("/")
//localhost:8080/rest/
public class RecipeService {


    private ManagerUser managerUser = new ManagerUser();


    /*********************/
    /******** GET ********/
    /*********************/
    @Path("recipes/{idUser}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getRecipesForUser(@PathParam("idUser") Long idUser, @HeaderParam("Authorization") String authorization) {
        List<Recipe> list = null;
        String strToken = authorization.substring(7); //substring(7) will remove "Bearer "
        System.out.println("token : "+strToken);
        if(managerUser.verifyTokenOfUser(idUser, strToken)){
            System.out.println("[getRecipesForUser] - TOKEN CORRECT ! get into bdd with id "+idUser);
            list = ManagerBdd.getInstance().getRecipesForUser(idUser);
        }else{
            list = ManagerBdd.getInstance().getRecipesForUser(new Long(-1));
            System.out.println("[getRecipesForUser] - TOKEN IN-CORRECT !  get into bdd with id -1");
        }
        return Response.ok(list).build();
    }

    @Path("/recipeCategories/{idUser}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getRecipeCategories(@PathParam("idUser") Long idUser, @HeaderParam("Authorization") String authorization) {
        List<RecipeCategory> list = null;
        String strToken = authorization.substring(7); //substring(7) will remove "Bearer "
        if(managerUser.verifyTokenOfUser(idUser, strToken)){
            System.out.println("[getRecipeCategories] - TOKEN CORRECT ! get into bdd with id "+idUser);
            list = ManagerBdd.getInstance().getRecipeCategories(idUser);
        }else{
            list = ManagerBdd.getInstance().getRecipeCategories(new Long(-1));
            System.out.println("[getRecipeCategories] - TOKEN IN-CORRECT !  get into bdd with id -1");
        }
        return Response.ok(list).build();
    }
    @Path("/recipeOrigins/{idUser}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getRecipeOrigins(@PathParam("idUser") Long idUser, @HeaderParam("Authorization") String authorization) {
        List<RecipeOrigin> list = null;
        String strToken = authorization.substring(7); //substring(7) will remove "Bearer "
        if(managerUser.verifyTokenOfUser(idUser, strToken)){
            System.out.println("[getRecipeOrigins] - TOKEN CORRECT ! get into bdd with id "+idUser);
            list = ManagerBdd.getInstance().getRecipeOrigins(idUser);
        }else{
            list = ManagerBdd.getInstance().getRecipeOrigins(new Long(-1));
            System.out.println("[getRecipeOrigins] - TOKEN IN-CORRECT !  get into bdd with id -1");
        }
        return Response.ok(list).build();
    }

    @Path("/recipeTypes")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getRecipeTypes() {
        List<RecipeType> list = ManagerBdd.getInstance().getRecipeTypes();
        return Response.ok(list).build();
    }

    @Path("/recipe/{idRecipe}/{idUser}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getRecipeById(@PathParam("idRecipe") Long idRecipe, @PathParam("idUser") Long idUser, @HeaderParam("Authorization") String authorization) {
        Recipe recipe = ManagerBdd.getInstance().getRecipeById(idRecipe);

        if(recipe.getIsPublic()){
            System.out.println("[getRecipeById] - recipe public -> allowed to see");
        }else{
            //PRIVATE recipe : only his owner can see it!
            String strToken = authorization.substring(7);
            if(recipe.getUser().getId() == idUser && managerUser.verifyTokenOfUser(idUser, strToken)){ //if its my recipe and its really me!
                System.out.println("[getRecipeById] - TOKEN CORRECT ! AND its my own recipe -> allowed to see");
            }else{
                System.out.println("[getRecipeById] - not my recipe OR token incorrect  -->  recipe set to NULL");
                recipe = null;
            }
        }
        return Response.ok(recipe).build();
    }





    @Path("recipe/create")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createRecipe(Recipe recipe, @HeaderParam("Authorization") String authorization) {
        Recipe newRecipe = null;
        Long idUser = recipe.getUser().getId();
        if(!authorization.equals("No Autorization")){
            String strToken = authorization.substring(7); //substring(7) will remove "Bearer "
            if(managerUser.verifyTokenOfUser(idUser, strToken)){
                System.out.println("[createRecipe] - TOKEN CORRECT ! created into bdd");
                newRecipe = ManagerBdd.getInstance().createRecipe(recipe);
            }else{
                System.out.println("[createRecipe] - TOKEN IN-CORRECT ! - no add into bdd");
            }
        }

        /*System.out.println(" recipe.name "+recipe.getName());
        System.out.println(" recipe.pixName "+recipe.getPixName());
        System.out.println(" recipe.Userid "+recipe.getUser().getId());
        System.out.println(" recipe.recipeType "+recipe.getRecipeType().getNameType());
        System.out.println(" recipe.nbPers "+recipe.getNbPerson());
        System.out.println(" recipe.timeCooking "+recipe.getTimeCooking());
        System.out.println(" recipe.timePreparation "+recipe.getTimePreparation());
        System.out.println(" recipe.origin : (name): "+recipe.getOrigin().getName()+" --  (id):"+recipe.getOrigin().getId());
        List<Ingredient> listIngredient = recipe.getIngredients();
        for(int i=0; i<listIngredient.size(); i++){
            System.out.println(" ingredient : "+listIngredient.get(i).getQty()+listIngredient.get(i).getUnit()
                    +" de "+listIngredient.get(i).getFood().getName());
        }
        List<RecipeCategory> listCategory = recipe.getCategories();
        for(int i=0; i<listCategory.size(); i++){
            System.out.println(" category : (name):"+listCategory.get(i).getName()+" -- (id):"+listCategory.get(i).getId());
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
    public Response putIsFavorite(@PathParam("idRecipe") Long idRecipe, @PathParam("idUser") Long idUser, boolean isFavorite, @HeaderParam("Authorization") String authorization) {
        if(!authorization.equals("No Autorization")){
            String strToken = authorization.substring(7); //substring(7) will remove "Bearer "
            if(managerUser.verifyTokenOfUser(idUser, strToken)){
                System.out.println("[putIsFavorite] - TOKEN CORRECT ! bdd modified");
                ManagerBdd.getInstance().putIsFavorite(idRecipe, idUser, isFavorite);
            }else{
                System.out.println("[putIsFavorite] - TOKEN IN-CORRECT ! - no add into bdd");
            }
        }
        return Response.ok(new ObjString("MOUAHAHAH")).build();
    }

    @Path("putIsForPlanning/{idRecipe}/{idUser}")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response putIsForPlanning(@PathParam("idRecipe") Long idRecipe, @PathParam("idUser") Long idUser, boolean isForPlanning, @HeaderParam("Authorization") String authorization) {
        if(!authorization.equals("No Autorization")){
            String strToken = authorization.substring(7); //substring(7) will remove "Bearer "
            if(managerUser.verifyTokenOfUser(idUser, strToken)){
                System.out.println("[putIsForPlanning] - TOKEN CORRECT ! bdd modified");
                ManagerBdd.getInstance().putIsForPlanning(idRecipe, idUser, isForPlanning);
            }else{
                System.out.println("[putIsForPlanning] - TOKEN IN-CORRECT ! - no add into bdd");
            }
        }
        return Response.ok(new ObjString("MOUAHAHAH")).build();
    }

    @Path("putRatingUser/{idRecipe}/{idUser}")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response putRatingUser(@PathParam("idRecipe") Long idRecipe, @PathParam("idUser") Long idUser, int ratingUser, @HeaderParam("Authorization") String authorization) {
        if(!authorization.equals("No Autorization")){
            String strToken = authorization.substring(7); //substring(7) will remove "Bearer "
            if(managerUser.verifyTokenOfUser(idUser, strToken)){
                System.out.println("[putRatingUser] - TOKEN CORRECT ! bdd modified");
                ManagerBdd.getInstance().putRatingUser(idRecipe, idUser, ratingUser);
            }else{
                System.out.println("[putRatingUser] - TOKEN IN-CORRECT ! - no add into bdd");
            }
        }
        return Response.ok(new ObjString("MOUAHAHAH")).build();
    }

    @Path("putIncrNumRankCategory/{idRecipeCategory}/{idUser}")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response putIncrNumRankCategory(@PathParam("idRecipeCategory") Long idRecipeCategory, @PathParam("idUser") Long idUser, @HeaderParam("Authorization") String authorization) {
        if(!authorization.equals("No Autorization")){
            String strToken = authorization.substring(7); //substring(7) will remove "Bearer "
            if(managerUser.verifyTokenOfUser(idUser, strToken)){
                System.out.println("[putIncrNumRankCategory] - TOKEN CORRECT ! bdd modified");
                ManagerBdd.getInstance().putIncrNumRankCategory(idRecipeCategory, idUser);
            }else{
                System.out.println("[putIncrNumRankCategory] - TOKEN IN-CORRECT ! - no add into bdd");
            }
        }
        return Response.ok(new ObjString("MOUAHAHAH")).build();
    }
    @Path("putIncrNumRankOrigin/{idRecipeOrigin}/{idUser}")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response putIncrNumRankOrigin(@PathParam("idRecipeOrigin") Long idRecipeOrigin, @PathParam("idUser") Long idUser, @HeaderParam("Authorization") String authorization) {
        if(!authorization.equals("No Autorization")){
            String strToken = authorization.substring(7); //substring(7) will remove "Bearer "
            if(managerUser.verifyTokenOfUser(idUser, strToken)){
                System.out.println("[putIncrNumRankOrigin] - TOKEN CORRECT ! bdd modified");
                ManagerBdd.getInstance().putIncrNumRankOrigin(idRecipeOrigin, idUser);
            }else{
                System.out.println("[putIncrNumRankOrigin] - TOKEN IN-CORRECT ! - no add into bdd");
            }
        }
        return Response.ok(new ObjString("MOUAHAHAH")).build();
    }




    @Path("createNewSpeciality/{recipeSpecialityName}/{idUser}")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createNewSpeciality(@PathParam("recipeSpecialityName") String recipeSpecialityName, @PathParam("idUser") Long idUser, @HeaderParam("Authorization") String authorization) {
        Long idNewSpeciality = new Long(-1);
        if(!authorization.equals("No Autorization")){
            String strToken = authorization.substring(7); //substring(7) will remove "Bearer "
            if(managerUser.verifyTokenOfUser(idUser, strToken)){
                System.out.println("[putIncrNumRankOrigin] - TOKEN CORRECT ! created into bdd");
                idNewSpeciality = ManagerBdd.getInstance().createNewSpeciality(recipeSpecialityName);
            }else{
                System.out.println("[putIncrNumRankOrigin] - TOKEN IN-CORRECT ! - no add into bdd");
            }
        }
        return Response.ok(new ObjLong(idNewSpeciality)).build();
    }
    @Path("createNewCategory/{recipeCategoryName}/{idRecipeType}/{idUser}")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createNewCategory(@PathParam("recipeCategoryName") String recipeCategoryName, @PathParam("idRecipeType") Long idRecipeType, @PathParam("idUser") Long idUser, @HeaderParam("Authorization") String authorization) {
        Long idNewCategory = new Long(-1);
        if(!authorization.equals("No Autorization")){
            String strToken = authorization.substring(7); //substring(7) will remove "Bearer "
            if(managerUser.verifyTokenOfUser(idUser, strToken)){
                System.out.println("[putIncrNumRankOrigin] - TOKEN CORRECT ! created into bdd ");
                idNewCategory = ManagerBdd.getInstance().createNewRecipeCategory(recipeCategoryName, idRecipeType);
            }else{
                System.out.println("[putIncrNumRankOrigin] - TOKEN IN-CORRECT ! - no add into bdd");
            }
        }
        return Response.ok(new ObjLong(idNewCategory)).build();
    }
}
