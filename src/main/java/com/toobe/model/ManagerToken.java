package com.toobe.model;

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

import org.jasypt.util.password.ConfigurablePasswordEncryptor;
/**
 * Created by fabien on 14/06/2016.
 */
public class ManagerToken {
    private static ManagerToken self;
    //private HashMap<Long, String> mapUserSecretKey;

    public static ManagerToken getInstance(){
        if(self != null){
            return self;
        }
        return new ManagerToken();
    }
    public ManagerToken(){
        //mapUserSecretKey = new HashMap<Long, String>();
    }











    /********************** GENERATION SECRET KEY *************************/
    public String generateSecretKey(){
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
    /********************** end GENERATION SECRET KEY *************************/




    /********************** PARSE JWT *************************/
    //Sample method to validate and read the JWT
    public Claims parseJWT(String jwt, String secretkey) {
        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretkey)).parseClaimsJws(jwt).getBody();
        return claims;
    }

    /********************** CREATE JWT *************************/
    //issuer = id ? //subject = "fab"
    public String createJWT(String id, String issuer, String subject, long ttlMillis, String secretkey) {

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
