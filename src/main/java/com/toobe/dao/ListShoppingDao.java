package com.toobe.dao;

import com.toobe.dto.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mathilde on 27/03/2016.
 */
public class ListShoppingDao {

    public List<ListShoppingPlanning> getListsShoppingPlanning(Connection conn, int idUser){
        PreparedStatement stm;

        PlanningDao planningDao = new PlanningDao();
        List<ListShoppingPlanning> list_listShoppingPlanning = new ArrayList<ListShoppingPlanning>();
        try {

            /* on recup toutes les listShoppingPLanning for a user   */
            stm = conn.prepareStatement("SELECT * from ListShopPlanning_User WHERE idUser = "+idUser);
            ResultSet res = stm.executeQuery();

            while(res.next()){
                int id = res.getInt("id");
                String name = res.getString("name");
                int date  = res.getInt("date");
                ListShopping listShopping = getListShoppingById(conn, res.getInt("idListShop"));
                Planning planning = planningDao.getPlanningById(conn, res.getInt("idPlanning"));
                ListShoppingPlanning listShoppingPlanning = new ListShoppingPlanning(id, name, date, listShopping, planning);
                list_listShoppingPlanning.add(listShoppingPlanning);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list_listShoppingPlanning;
    }



  /*
    listShopping = {name:.., listShoppingCategories:[listShoppingCategory1, listShoppingCategory2, ..., listShoppingCategoryN]};
    listShoppingCategory = {id:0, name:'Autre', numRank:5 , ingredients:[{qty:50, unit:"g", food:"ski", rayonId:0}]}
 */
    /**
     * On trouve ici toutes les planning pour un idUser
     */
    public ListShopping getListShoppingById(Connection conn, int idListShopping){
        ListShopping listShopping = new ListShopping();
        PreparedStatement stm;
        try {

            /* on recup toutes les listShoppingCategory   */
            stm = conn.prepareStatement("SELECT lsc.id as id, idFoodCategory, name as nameFoodCategory, numRank  FROM ListShopping_Category as lsc JOIN Food_Category as fc ON lsc.idFoodCategory = fc.id WHERE idListShop = "+idListShopping);
            ResultSet res = stm.executeQuery();

            List<ListShoppingCategory> list_listShoppingCategory = new ArrayList<ListShoppingCategory>();
            while(res.next()){
                int idListShoppingCategory = res.getInt("id");
                String nameFoodCategory = res.getString("nameFoodCategory");
                int numRank = res.getInt("numRank");

                /* INGREDIENTS */
                List<Ingredient> ingredientList = new ArrayList<Ingredient>();;
                Ingredient ingr;
                int qty, idCategoryIngr;
                int idFood;
                String unit, nameFood;
                boolean isValidated;
                stm = conn.prepareStatement("SELECT quantity, unit, idFood,  name, idCategory, isValidated FROM Ingredient_ListShop JOIN FOOD ON Ingredient_ListShop.idFood = food.id WHERE Ingredient_ListShop.idListShopCategory = "+idListShoppingCategory);
                ResultSet resIngredient = stm.executeQuery();
                while(resIngredient.next()){
                    qty = resIngredient.getInt("quantity");
                    unit = resIngredient.getString("unit");
                    idFood = resIngredient.getInt("idFood");
                    nameFood = resIngredient.getString("name");
                    idCategoryIngr = resIngredient.getInt("idCategory");
                    isValidated = resIngredient.getBoolean("isValidated");
                    ingr = new Ingredient(qty, unit, new Food(new Long(idFood), nameFood, idCategoryIngr, isValidated));
                    ingredientList.add(ingr);
                }
                ListShoppingCategory listShoppingCategory = new ListShoppingCategory(idListShoppingCategory, nameFoodCategory, numRank, ingredientList);
                list_listShoppingCategory.add(listShoppingCategory);
            }
            listShopping = new ListShopping("nameListShopping", list_listShoppingCategory);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listShopping;
    }
}
