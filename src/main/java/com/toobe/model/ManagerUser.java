package com.toobe.model;

import com.toobe.dao.Database;
import com.toobe.dao.UserDao;
import com.toobe.dto.User;
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
        User user = managerBdd.insertNewUser(pseudo, encryptedPwd);
        Long idUser = user.getId();
        if(idUser == -1){
            objToken.setMsg("User not Created into BDD");
            return objToken;
        }

        //3. GENERATION TOKEN
        String strTokenUser = createTokenForUser(idUser);
        objToken.setIsValidToken(true);
        objToken.setToken(strTokenUser);
        objToken.setUser(user);
        return objToken;
    }


    public ObjToken connexionUserTreatment(String pseudo, String plainPwd){
        ObjToken objToken = new ObjToken();

        //1. FIND USER INTO BDD
        User user = managerBdd.getUserByPseudo(pseudo);
        //Long idUser = managerBdd.getIdUserByPseudo(pseudo);
        Long idUser = user.getId();
        if(idUser == -1){ //si user has been find
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
            System.out.println("before set user : idUser"+user.getId()+" .. pseudo :"+user.getPseudo());
            objToken.setUser(user);
        }else{
            objToken.setMsg("Password is incorrect");
        }
        return objToken;
    }



    public boolean verifyTokenOfUser(Long idUser, String strTokenUser){
        String secretKeyUser = managerBdd.getKeyAlgo(idUser);

        //managerToken.parseJWT give back null if it is not a well signed JWS, the good claims otherwise
        Claims claims = managerToken.parseJWT(strTokenUser, secretKeyUser);
        /*System.out.println("ID: " + claims.getId() + " Subject: " + claims.getSubject() + "Issuer: " + claims.getIssuer() + "Expiration: " + claims.getExpiration());*/

        if(claims == null){
            //System.out.println("[verifyTokenOfUser] claims NULL -> TOKEN IN-CORRECT");
            return false;
        }else{
            //System.out.println("[verifyTokenOfUser] 'parseJWT' didnt throw any exception -> TOKEN CORRECT");
            return true;
        }
    }



    /****************************************    PRIVATE    ***********************************************************************/
    /****************************************    PRIVATE    ***********************************************************************/
    private String createTokenForUser(Long idUser){

        String id = "idUser"+idUser.toString();
        String issuer = "myIssue";
        String subject = "mysSubject";
        //long ttlMillis = 86400000;//1 day
        //long ttlMillis = 60000;//1 min
        long ttlMillis = 900000;//15 min
        //long ttlMillis = 3600000;//1 heure

        String secretKeyUser = managerToken.generateSecretKey();

        System.out.println("[createTokenForUser] (idUser: "+idUser+") secretKey created : " + secretKeyUser);
        managerBdd.putKeyAlgo(secretKeyUser, idUser);

        String strTokenUser = managerToken.createJWT(id, issuer, subject, ttlMillis, secretKeyUser);

        System.out.println("[createTokenForUser] (idUser: "+idUser+") token created : " + strTokenUser);
        return strTokenUser;
    }







}
