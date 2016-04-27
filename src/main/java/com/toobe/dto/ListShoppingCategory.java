package com.toobe.dto;

/**
 * Created by fabien on 10/04/2016.
 */

import java.util.List;



  /*
    listShoppingCategory = {id:0, name:'Autre', noRank:5 , ingredients:[{qty:50, unit:"g", food:"ski", rayonId:0}]}
 */
public class ListShoppingCategory {
      private int id;
      private String name;
      private int noRank;
      private List<Ingredient> ingredients;

      public ListShoppingCategory(int id, String name, int noRank, List<Ingredient> ingredients) {
          this.id = id;
          this.name = name;
          this.noRank = noRank;
          this.ingredients = ingredients;
      }

      public int getId() {
          return id;
      }

      public void setId(int id) {
          this.id = id;
      }

      public String getName() {
          return name;
      }

      public void setName(String name) {
          this.name = name;
      }

      public int getNoRank() {
          return noRank;
      }

      public void setNoRank(int noRank) {
          this.noRank = noRank;
      }

      public List<Ingredient> getIngredients() {
          return ingredients;
      }

      public void setIngredients(List<Ingredient> ingredients) {
          this.ingredients = ingredients;
      }
  }
