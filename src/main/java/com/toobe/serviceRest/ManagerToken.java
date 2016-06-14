package com.toobe.serviceRest;

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
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by fabien on 14/06/2016.
 */
public class ManagerToken {

    private static ManagerToken self;
    private HashMap<Long, String> mapUserSecretKey;


    public static ManagerToken getInstance(){
        if(self != null){
            return self;
        }
        return new ManagerToken();
    }
    public ManagerToken(){
        mapUserSecretKey = new HashMap<Long, String>();
    }


    public String createTokenForUser(Long idUser){
        String id = "idUser"+idUser.toString();
        String issuer = "myIssue";
        String subject = "mysSubject";
        long ttlMillis = 86400000;//1 day

        String secretKeyUser = generateSecretKey();
        mapUserSecretKey.put(idUser, secretKeyUser);

        String strTokenUser = createJWT(id, issuer, subject, 999999, secretKeyUser);
        return strTokenUser;
    }

    public boolean verifyTokenOfUser(Long idUser, String strTokenUser){
        String secretKeyUser = mapUserSecretKey.get(idUser);

        System.out.println("[verifyTokenOfUser] idUser: " + idUser + "  --before 'parseJWT'");
        //This line will throw an exception if it is not a signed JWS (as expected)
        parseJWT(strTokenUser, secretKeyUser);
        System.out.println("[verifyTokenOfUser] 'parseJWT' didnt throw any exception -> TOKEN CORRECT");

        //If no exception thrown we can return TRUE : the token is correct
        return true;
    }




    //Sample method to validate and read the JWT
    private void parseJWT(String jwt, String secretkey) {
        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretkey)).parseClaimsJws(jwt).getBody();

        System.out.println("ID: " + claims.getId());
        System.out.println("Subject: " + claims.getSubject());
        System.out.println("Issuer: " + claims.getIssuer());
        System.out.println("Expiration: " + claims.getExpiration());
    }


    private String generateSecretKey(){
        SecretKey secretKey = null;
        try {
            // create new key
            secretKey = KeyGenerator.getInstance("AES").generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // get base64 encoded version of the key
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());

        return  encodedKey;
    }
    //issuer = id ? //subject = "fab"
    private String createJWT(String id, String issuer, String subject, long ttlMillis, String secretkey) {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);


        //We will sign our JWT with our ApiKey secret
        //byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(apiKey.getSecret());
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretkey);
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


}
