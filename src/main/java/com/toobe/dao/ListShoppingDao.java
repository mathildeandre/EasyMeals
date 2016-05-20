package com.toobe.dao;

import com.toobe.dto.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mathilde on 27/03/2016.
 */
public class ListShoppingDao {


    private final static String CREATE_LISTSHOPPLANNING = "INSERT INTO ListShopPlanning_User(name, idListShop, idPlanning, idUser, lastOpen) VALUES (?, ?, ?, ?, ?);\n";
    public ListShoppingPlanning createListShoppingPlanning(Connection conn, Long idPlanning, int idUser, List<ListShoppingCategory> listShoppingCategories){
        PreparedStatement stm;
        ResultSet res;
        Long idListShopPlanning = null;
        int isOk = 0;
        PlanningDao planningDao = new PlanningDao();
        ListShoppingPlanning listShopPlanning = new ListShoppingPlanning();
        try {
            //modify field isForListShop to TRUE of this planning
            planningDao.putIsForListShop(conn, idPlanning, true);
            //get jsonObj Planning in order to return a jsonObj ListShoppingPlanning
            Planning planning = planningDao.getPlanningById(conn, idPlanning); //copyOfPlanning(conn, idPlanning, true);
            //Long idNewPlanning = planning.getId();
            String namePlanning = planning.getName();
            //get jsonObj ListShopping and then INSERT it into BDD
            ListShopping listShopping = new ListShopping("", listShoppingCategories);
            Long idNewListShopping = createBddListShopping(conn, listShopping);


            //1. INSERT LIST_SHOP_PLANNING
            stm = conn.prepareStatement(CREATE_LISTSHOPPLANNING, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, namePlanning); //name
            stm.setLong(2, idNewListShopping); //idListShop
            stm.setLong(3, idPlanning); //idPlanning
            stm.setInt(4, idUser); //idUser
            stm.setBoolean(5, true); //lastOpen
            isOk = stm.executeUpdate();
            if (isOk == 0) {
                throw new SQLException("Creating LIST_SHOP_PLANNING failed, no rows affected");
            }
            res = stm.getGeneratedKeys();
            if(res.next()){
                idListShopPlanning  = res.getLong(1);
            }

            listShopPlanning = new ListShoppingPlanning(idListShopPlanning, namePlanning, 0, listShopping, planning, true);



        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listShopPlanning;
    }







    private final static String INSERT_LIST_SHOPPING = "INSERT INTO List_Shopping(name) VALUES (?);\n";
    private final static String INSERT_LIST_SHOPPING_CATEGORY = "INSERT INTO ListShopping_Category(idListShop, idFoodCategory) VALUES (?, ?);\n";
    private final static String INSERT_INGREDIENT_LISTSHOP = "INSERT INTO Ingredient_ListShop(nameFood, idListShopCategory, quantity, unit) VALUES (?, ?, ?, ?);\n";
    public Long createBddListShopping(Connection conn, ListShopping listShopping){
        PreparedStatement stm;
        ResultSet res;
        int isOk = 0;
        Long idListShopping = null;
        Long idListShoppingCategory = null;
        try {

            //1. INSERT LIST_SHOPPING
            stm = conn.prepareStatement(INSERT_LIST_SHOPPING, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, listShopping.getName()); //name
            isOk = stm.executeUpdate();
            if (isOk == 0) {
                throw new SQLException("Creating LIST_SHOPPING failed, no rows affected");
            }
            res = stm.getGeneratedKeys();
            if(res.next()){
                idListShopping  = res.getLong(1);
            }



            for (ListShoppingCategory listShopCat : listShopping.getListShoppingCategories()) {
                if(listShopCat.getIngredients().size() > 0){//only if the category contains elements

                    //2. INSERT LIST_SHOPPING_CATEGORY
                    stm = conn.prepareStatement(INSERT_LIST_SHOPPING_CATEGORY, Statement.RETURN_GENERATED_KEYS);
                    stm.setLong(1, idListShopping); //idListShop
                    stm.setInt(2, listShopCat.getId()); //idFoodCategory
                    isOk = stm.executeUpdate();
                    if (isOk == 0) {
                        throw new SQLException("Creating LIST_SHOPPING_CATEGORY failed, no rows affected");
                    }
                    res = stm.getGeneratedKeys();
                    if(res.next()){
                        idListShoppingCategory  = res.getLong(1);
                    }

                    for(Ingredient ingr :listShopCat.getIngredients()){
                        //3. INSERT INGREDIENT_LISTSHOP
                        stm = conn.prepareStatement(INSERT_INGREDIENT_LISTSHOP);
                        stm.setString(1, ingr.getFood().getName()); //nameFood
                        stm.setLong(2, idListShoppingCategory); //idListShopCategory
                        stm.setInt(3, ingr.getQty()); //quantity
                        stm.setString(4, ingr.getUnit()); //unit
                        isOk = stm.executeUpdate();
                        if (isOk == 0) {
                            throw new SQLException("Creating INGREDIENT_LISTSHOP failed, no rows affected");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idListShopping;
    }




    public List<ListShoppingPlanning> getListsShoppingPlanning(Connection conn, int idUser){
        PreparedStatement stm;

        PlanningDao planningDao = new PlanningDao();
        List<ListShoppingPlanning> list_listShoppingPlanning = new ArrayList<ListShoppingPlanning>();
        try {

            /* on recup toutes les listShoppingPLanning for a user   */
            stm = conn.prepareStatement("SELECT * from ListShopPlanning_User WHERE idUser = "+idUser);
            ResultSet res = stm.executeQuery();

            while(res.next()){
                Long id = res.getLong("id");
                String name = res.getString("name");
                int date  = res.getInt("date");
                boolean lastOpen  = res.getBoolean("lastOpen");
                ListShopping listShopping = getListShoppingById(conn, res.getInt("idListShop"));
                Planning planning = planningDao.getPlanningById(conn, res.getLong("idPlanning"));
                ListShoppingPlanning listShoppingPlanning = new ListShoppingPlanning(id, name, date, listShopping, planning, lastOpen);
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
            stm = conn.prepareStatement(
                    "SELECT lsc.id as id, idFoodCategory, name as nameFoodCategory, numRank  " +
                    "FROM ListShopping_Category as lsc JOIN Food_Category as fc ON lsc.idFoodCategory = fc.id " +
                    "WHERE idListShop = "+idListShopping);
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
                stm = conn.prepareStatement("SELECT * FROM Ingredient_ListShop WHERE idListShopCategory = "+idListShoppingCategory);
                ResultSet resIngredient = stm.executeQuery();
                while(resIngredient.next()){
                    qty = resIngredient.getInt("quantity");
                    unit = resIngredient.getString("unit");
                    //idFood = resIngredient.getInt("idFood");
                    nameFood = resIngredient.getString("nameFood");
                    //idCategoryIngr = resIngredient.getInt("idCategory");
                    //isValidated = resIngredient.getBoolean("isValidated");
                    //ingr = new Ingredient(qty, unit, new Food(new Long(1), nameFood, 1, false));
                    ingr = new Ingredient(qty, unit, new Food(nameFood));
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
