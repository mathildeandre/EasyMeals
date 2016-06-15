package com.toobe.serviceRest;

/**
 * Created by mathilde on 13/03/2016.
 */

import com.toobe.dto.User;
import com.toobe.dto.info.*;
import com.toobe.model.ManagerBdd;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;

import com.toobe.model.ManagerToken;
import com.toobe.model.ManagerUser;
import io.jsonwebtoken.*;
import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

//import org.springframework.web.multipart.MultipartFile;

@Path("/")
//localhost:8080/rest/
public class UserService {

    private ManagerToken managerToken = new ManagerToken();
    private ManagerUser managerUser = new ManagerUser();
    private String key;
    public UserService(){
        //System.out.println("UserService CONSTRUCTOR !!");
    }

    @Path("updateBddColor/{colorValue}/{idUser}")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateBddColor(@PathParam("colorValue") String colorValue, @PathParam("idUser") Long idUser, @HeaderParam("Authorization") String authorization) {
        if(!authorization.equals("No Autorization")){
            String strToken = authorization.substring(7); //substring(7) will remove "Bearer "
            if(managerUser.verifyTokenOfUser(idUser, strToken)){
                System.out.println("[putIncrNumRankOrigin] - TOKEN CORRECT ! bdd modified");
                ManagerBdd.getInstance().updateBddColor(colorValue, idUser);
            }else{
                System.out.println("[putIncrNumRankOrigin] - TOKEN IN-CORRECT ! - no add into bdd");
            }
        }
        return Response.ok(new ObjString("MOUAHAHAH")).build();
    }





    @Path("/authenticate")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response authenticate(User user) {
        System.out.println("My user ---  pseudo : "+user.getPseudo()+ " / email : "+user.getEmail()+ " / password : "+user.getPassword());



        return Response.ok(new ObjString("bal val blaa")).build();
    }


    @Path("/registrationUser/{pseudo}/{pwd}")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response registrationUser(@PathParam("pseudo") String pseudo, @PathParam("pwd") String pwd) {
        System.out.println("[/rest/registrationUser] - pseudo : "+pseudo+"  pwd : "+pwd);
        ObjToken objToken = managerUser.registrationUserTreatment(pseudo, pwd);
        return Response.ok(objToken).build();
    }
    @Path("/connexionUser/{pseudo}/{pwd}")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response connexionUser(@PathParam("pseudo") String pseudo, @PathParam("pwd") String pwd) {
        System.out.println("[/rest/connexionUser] - pseudo : "+pseudo+"  pwd : "+pwd);
        ObjToken objToken = managerUser.connexionUserTreatment(pseudo, pwd);
        return Response.ok(objToken).build();
    }
/*

    @Path("/testAuthenticate/{idUser}")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response authenticate(@PathParam("idUser") Long idUser) {
        System.out.println("[/rest/testAuthenticate/idUser] - idUser : "+idUser);
        //String token = ManagerToken.getInstance().createTokenForUser(idUser);
        String token = managerToken.createTokenForUser(idUser);
        return Response.ok(new ObjToken(token)).build();
    }
    @Path("/testVerifyToken/{idUser}")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response authenticate(@PathParam("idUser") Long idUser, ObjToken token) {
        System.out.println("[/rest/testVerifyToken/idUser] - idUser : "+idUser);
        System.out.println("[/rest/testVerifyToken/idUser] - token : "+token.getToken());

        //boolean isTokenCorrect = ManagerToken.getInstance().verifyTokenOfUser(idUser, token.getToken());
        boolean isTokenCorrect = managerToken.verifyTokenOfUser(idUser, token.getToken());


        if(isTokenCorrect){
            System.out.println("[/rest/testVerifyToken/idUser] -- TOKEN CORRECT !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        }


        return Response.ok(new ObjString("bal val blaa")).build();
    }

*/



    /************************************************* USELESS BELOW ******************************************/

    //issuer = id ? //subject = "fab"
    private String createJWT(String id, String issuer, String subject, long ttlMillis) {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);


        /********************* CUSTOM - generation key **********************/
        // create new key
        SecretKey secretKey = null;
        try {
            secretKey = KeyGenerator.getInstance("AES").generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // get base64 encoded version of the key
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        /********************* end CUSTOM **********************/

        key = encodedKey;

        System.out.println("key : "+key);
        //We will sign our JWT with our ApiKey secret
        //byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(apiKey.getSecret());
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(key);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        //if it has been specified, let's add the expiration
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }



    //Sample method to validate and read the JWT
    private void parseJWT(String jwt) {



        System.out.println("(parse) key : "+key);
        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(key))
                .parseClaimsJws(jwt).getBody();
        System.out.println("ID: " + claims.getId());
        System.out.println("Subject: " + claims.getSubject());
        System.out.println("Issuer: " + claims.getIssuer());
        System.out.println("Expiration: " + claims.getExpiration());
    }


    /*
    public void generateToken(){
        System.out.println("EUUUH ON GENERE LE TOKEN ICI");

        // We need a signing key, so we'll create one just for this example. Usually
        // the key would be read from your application configuration instead.
        Key key = MacProvider.generateKey();

        String s = Jwts.builder().setSubject("Joe").signWith(SignatureAlgorithm.HS512, key).compact();

        //now let's verify the JWT . you should always discard JWTs that don't match an expected signature
        assert Jwts.parser().setSigningKey(key).parseClaimsJws(s).getBody().getSubject().equals("Joe");

        //But what if signature validation failed? You can catch SignatureException and react accordingly:
        try {

            //Jwts.parser().setSigningKey(key).parseClaimsJws(compactJwt);

            //OK, we can trust this JWT

        } catch (SignatureException e) {

            //don't trust the JWT!
        }

    }

*/












}
