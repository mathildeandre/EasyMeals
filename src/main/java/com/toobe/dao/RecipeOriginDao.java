package com.toobe.dao;

import com.toobe.dto.info.RecipeOrigin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mathilde on 27/03/2016.
 */
public class RecipeOriginDao {

    /**
     * On trouve ici toutes les foods
     */
    public List<RecipeOrigin> getRecipeOrigins(Connection conn, Long idUser){
        List<RecipeOrigin> recipeOriginList = new ArrayList<RecipeOrigin>();
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement("SELECT * FROM recipe_origin");
            ResultSet res = stm.executeQuery();

            Long id;
            String name;
            int numRank;
            RecipeOrigin recipeOrigin;
            while(res.next()){
                id = res.getLong("id");
                name = res.getString("name");
                numRank = getNumRank_RelUserRecipeOrigin(conn, id, idUser);
                if(numRank == -1){
                    numRank = res.getInt("numRank");
                }
                recipeOrigin = new RecipeOrigin(id, name, numRank);
                recipeOriginList.add(recipeOrigin);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipeOriginList;
    }










    /**************************************************** IA NUM_RANK **********************************************************/
    private final static String UPDATE_incrNumRank_REL_USER_RecipeORIGIN = "UPDATE Rel_User_RecipeOrigin SET numRankRel = ? WHERE idRecipeOrigin = ? AND idUser = ? ;\n";
    public void putIncrNumRankOrigin(Connection conn, Long idRecipeOrigin, Long idUser){
        PreparedStatement stm;
        int isOk = 0;
        try {
            //1. select numRank from Rel_User_RecipeCategory
            int numRank = getNumRank_RelUserRecipeOrigin(conn, idRecipeOrigin, idUser);
            //2.1 si numRank == -1 c'est que Rel_User_RecipeCategory existe pas : on la créée
            if (numRank == -1) {
                insertRelUserRecipeOrigin(conn, idRecipeOrigin, idUser, 10);
            }
            //2.2. otherwise we update-increment numRank of the relation
            else{
                stm = conn.prepareStatement(UPDATE_incrNumRank_REL_USER_RecipeORIGIN);
                stm.setInt(1, numRank+1);
                stm.setLong(2, idRecipeOrigin);
                stm.setLong(3, idUser);
                isOk = stm.executeUpdate();
                if (isOk == 0) {
                    throw new SQLException("putIncrNumRankOrigin failed, no rows affected");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private final static String SELECT_Rel_USER_RECIPEORIGIN = "SELECT * FROM Rel_User_RecipeOrigin WHERE idRecipeOrigin = ? AND idUser = ? ;\n";
    public int getNumRank_RelUserRecipeOrigin(Connection conn, Long idRecipeOrigin, Long idUser) {
        PreparedStatement stm;
        int numRank = -1;
        try {
            stm = conn.prepareStatement(SELECT_Rel_USER_RECIPEORIGIN);
            stm.setLong(1, idRecipeOrigin);
            stm.setLong(2, idUser);
            ResultSet res = stm.executeQuery();
            if (res.next()) {
                numRank = res.getInt("numRankRel");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numRank;
    }

    private static final String INSERT_REL_RecipeOrigin_User = "INSERT INTO REL_User_RecipeOrigin(idRecipeOrigin, idUser, numRankRel) VALUES (?, ?, ?)";
    private void insertRelUserRecipeOrigin(Connection conn, Long idRecipeOrigin, Long idUser, int numRank) {
        PreparedStatement stm;
        try {
            stm = conn.prepareStatement(INSERT_REL_RecipeOrigin_User);
            stm.setLong(1, idRecipeOrigin);
            stm.setLong(2, idUser);
            stm.setInt(3, numRank);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**************************************************** end IA NUM_RANK **********************************************************/


}
