package com.toobe.model;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

/**
 * Created by fabien on 14/06/2016.
 */
public class ManagerEncryptor {
    private static final String ALGO_CHIFFREMENT = "SHA-256";

    public ManagerEncryptor(){
        //mapUserSecretKey = new HashMap<Long, String>();
    }


    /********************** ENCRYPTION PASSWORD *************************/
    public String cryptePwd(String plainPwd){
        ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
        passwordEncryptor.setAlgorithm(ALGO_CHIFFREMENT);
        passwordEncryptor.setPlainDigest(false);
        String encryptedPwd = passwordEncryptor.encryptPassword(plainPwd);
        return encryptedPwd;
    }

    public boolean checkPwd(String plainPwd, String encryptedPwd){
        ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
        passwordEncryptor.setAlgorithm( ALGO_CHIFFREMENT );
        passwordEncryptor.setPlainDigest( false );
        return passwordEncryptor.checkPassword(plainPwd, encryptedPwd);
    }
    /********************** end ENCRYPTION PASSWORD *************************/

}
