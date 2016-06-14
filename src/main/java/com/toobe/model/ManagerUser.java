package com.toobe.model;

import com.toobe.dao.Database;
import com.toobe.dao.UserDao;
import com.toobe.dto.info.ObjToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Date;

/**
 * Created by fabien on 14/06/2016.
 */
public class ManagerUser {

    private static final String ALGO_CHIFFREMENT = "SHA-256";
    private ManagerEncryptor managerEncryptor;
    private ManagerToken managerToken;
    private ManagerBdd managerBdd;
    private Connection conn;

    public ManagerUser(){
        managerEncryptor = new ManagerEncryptor();
        managerToken = new ManagerToken();
        managerBdd = new ManagerBdd();
    }


    public ObjToken registrationUserTreatment(String pseudo, String plainPwd){
        ObjToken objToken = new ObjToken();

        //1. CRYPT plainPwd
        String encryptedPwd = managerEncryptor.cryptePwd(plainPwd);

        //2. INSERT USER into BDD
        Long idUser = managerBdd.insertNewUser(pseudo, encryptedPwd);
        if(idUser == -1){
            objToken.setMsg("User not Created into BDD");
            return objToken;
        }

        //3. GENERATION TOKEN
        String strTokenUser = createTokenForUser(idUser);
        objToken.setIsValidToken(true);
        objToken.setToken(strTokenUser);
        objToken.setIdUser(idUser);
        objToken.setPseudo(pseudo);
        return objToken;
    }


    public ObjToken connexionUserTreatment(String pseudo, String plainPwd){
        ObjToken objToken = new ObjToken();

        //1. FIND USER INTO BDD
        Long idUser = managerBdd.getIdUserByPseudo(pseudo);
        if(idUser == -1){
            objToken.setMsg("Pseudo does not exist");
            return objToken;
        }

        //2. GET encrypted password from BDD
        String encryptedPwd = managerBdd.getEncryptedPwd(idUser);
        //3. CHECK PWD CORRECT
        boolean isPwdCorrect = managerEncryptor.checkPwd(plainPwd, encryptedPwd);


        //3. if ok : GENERATION TOKEN
        String strTokenUser = null;
        if(isPwdCorrect){
            strTokenUser = createTokenForUser(idUser);
            objToken.setIsValidToken(true);
            objToken.setToken(strTokenUser);
            objToken.setIdUser(idUser);
            objToken.setPseudo(pseudo);
        }else{
            objToken.setMsg("Password is incorrect");
        }
        return objToken;
    }



    public boolean verifyTokenOfUser(Long idUser, String strTokenUser){
        String secretKeyUser = managerBdd.getKeyAlgo(idUser);

        System.out.println("[verifyTokenOfUser] (idUser: "+idUser+") secretKey from map : " + secretKeyUser);

        System.out.println("[verifyTokenOfUser] (idUser: "+idUser+")  --before 'parseJWT'");
        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = managerToken.parseJWT(strTokenUser, secretKeyUser);
        System.out.println("[verifyTokenOfUser] 'parseJWT' didnt throw any exception -> TOKEN CORRECT");
        /*System.out.println("ID: " + claims.getId());
        System.out.println("Subject: " + claims.getSubject());
        System.out.println("Issuer: " + claims.getIssuer());
        System.out.println("Expiration: " + claims.getExpiration());*/

        //If no exception thrown we can return TRUE : the token is correct
        return true;
    }



    /****************************************    PRIVATE    ***********************************************************************/
    /****************************************    PRIVATE    ***********************************************************************/
    private String createTokenForUser(Long idUser){

        String id = "idUser"+idUser.toString();
        String issuer = "myIssue";
        String subject = "mysSubject";
        long ttlMillis = 86400000;//1 day

        String secretKeyUser = managerToken.generateSecretKey();

        System.out.println("[createTokenForUser] (idUser: "+idUser+") secretKey created : " + secretKeyUser);
        managerBdd.putKeyAlgo(secretKeyUser, idUser);

        String strTokenUser = managerToken.createJWT(id, issuer, subject, 999999, secretKeyUser);

        System.out.println("[createTokenForUser] (idUser: "+idUser+") token created : " + strTokenUser);
        return strTokenUser;
    }







}
