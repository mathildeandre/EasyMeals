package com.toobe.dao;

import com.toobe.dto.info.RecipeCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

}
