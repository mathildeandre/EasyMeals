package com.toobe.dao;

import com.toobe.dto.info.RecipeCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mathilde on 27/03/2016.
 */
public class UserDao {





    private final static String UPDATE_ColorThemeUSER = "UPDATE User SET colorThemeRecipe = ? WHERE id = ?;\n";
    public void updateBddColor(Connection conn, String colorValue, Long idUser){
        PreparedStatement stm;
        int isOk = 0;
        try {
            stm = conn.prepareStatement(UPDATE_ColorThemeUSER);
            stm.setString(1, colorValue);
            stm.setLong(2, idUser);
            isOk = stm.executeUpdate();
            if (isOk == 0) {
                throw new SQLException("updateBddColor failed, no rows affected");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    /************************************* INSERT NEW USER  ************************************************************************/
    private final static String INSERT_newUser = "INSERT INTO `User`(pseudo, pwd) VALUES (?, ?);\n";
    public Long insertNewUser(Connection conn, String pseudo, String encryptedPwd){
        PreparedStatement stm;
        ResultSet res;
        Long idNewUser = new Long(-1);
        int isOk = 0;
        try {
            stm = conn.prepareStatement(INSERT_newUser, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, pseudo);
            stm.setString(2, encryptedPwd);
            isOk = stm.executeUpdate();
            if (isOk == 0) {
                throw new SQLException("insertNewUser failed, no rows affected");
            }
            res = stm.getGeneratedKeys();
            if (res.next()) {
                idNewUser = res.getLong(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idNewUser;
    }

    /************************************* GET USER by PSEUDO  ************************************************************************/
    private final static String SELECT_userByPseudo = "SELECT id FROM User WHERE pseudo = ?;\n";
    public Long getIdUserByPseudo(Connection conn, String pseudo){
        PreparedStatement stm;
        Long idUser = new Long(-1);
        try {
            stm = conn.prepareStatement(SELECT_userByPseudo);
            stm.setString(1, pseudo);
            ResultSet res = stm.executeQuery();
            if(res.next()) {
                idUser = res.getLong("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idUser;
    }



    /********************************************************************************************************************/
    /************************************* SECRET KEY BDD **************************************************************/
    /****************** we just call secretKey : 'keyAlgo' into bdd (to be discret) **************************************/
    /********************************************************************************************************************/
    private final static String UPDATE_keyAlgo = "UPDATE User SET keyAlgo = ? WHERE id = ? ;\n";
    public void putKeyAlgo(Connection conn, String keyAlgo, Long idUser){
        PreparedStatement stm;
        int isOk = 0;
        try {
            stm = conn.prepareStatement(UPDATE_keyAlgo);
            stm.setString(1, keyAlgo);
            stm.setLong(2, idUser);
            isOk = stm.executeUpdate();
            if (isOk == 0) {
                throw new SQLException("putKeyAlgo failed, no rows affected");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private final static String SELECT_keyAlgo = "SELECT keyAlgo FROM User WHERE id = ?;\n";
    public String getKeyAlgo(Connection conn, Long idUser){
        PreparedStatement stm;
        String keyAlgo = null;
        try {
            stm = conn.prepareStatement(SELECT_keyAlgo);
            stm.setLong(1, idUser);
            ResultSet res = stm.executeQuery();
            if(res.next()) {
                keyAlgo = res.getString("keyAlgo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return keyAlgo;
    }
    /********************************************************************************************************************/
    /************************************* end SECRET KEY BDD **************************************************************/
    /********************************************************************************************************************/






    /********************************************************************************************************************/
    /************************************* ENCRYPTED PASSWORD  **********************************************************/
    /********************************************************************************************************************/
    private final static String UPDATE_encryptedPwd = "UPDATE User SET pwd = ? WHERE id = ? ;\n";
    public void putEncryptedPwd(Connection conn, String encryptedPwd, Long idUser){
        PreparedStatement stm;
        int isOk = 0;
        try {
            stm = conn.prepareStatement(UPDATE_encryptedPwd);
            stm.setString(1, encryptedPwd);
            stm.setLong(2, idUser);
            isOk = stm.executeUpdate();
            if (isOk == 0) {
                throw new SQLException("putEncryptedPwd failed, no rows affected");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private final static String SELECT_encryptedPwd = "SELECT pwd FROM User WHERE id = ?;\n";
    public String getEncryptedPwd(Connection conn, Long idUser){
        PreparedStatement stm;
        String encryptedPwd = null;
        try {
            stm = conn.prepareStatement(SELECT_encryptedPwd);
            stm.setLong(1, idUser);
            ResultSet res = stm.executeQuery();
            if(res.next()) {
                encryptedPwd = res.getString("pwd");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return encryptedPwd;
    }
    /********************************************************************************************************************/
    /************************************* end ENCRYPTED PASSWORD  *******************************************************/
    /********************************************************************************************************************/

}
