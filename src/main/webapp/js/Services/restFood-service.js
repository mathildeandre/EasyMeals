/**
 * Created by fabien on 18/04/2016.
 */

var myService = angular.module('services');

myService.service("restFoodService", function ($http, $q, $log, restGlobalService) {

    /*
    foods = [{"id":1,"name":"steack","idCategory":2,"isValidated":false},{..}]
     */
    var foodCategories = [];
    var foods = [];


    function getFoodCategories(){
        if(foodCategories.length == 0){
            foodCategories = restGlobalService.getFoodCategories();
            $log.warn("[restFood-service] getFoodCategories() --> on appel restGLobalService");
        }
        return foodCategories;
    }
    function getFoods(){
        if(foods.length == 0){
            foods = restGlobalService.getFoods();
            $log.warn("[restFood-service] getFoods() --> on appel restGLobalService");
        }
        return foods;
    }

    function addFood(food){
        foods.push(food);
    }


    /* useless
    function initLoadData(){
        $log.warn("[FOOD SERVICE] INIT - LOADING DATA")

        //FOOD CATEGORIES
        if(foodCategories == undefined || foodCategories.length == 0){
            getObjFromServer('/rest/foodCategories').then(function(data){ //2 = idUser
                foodCategories = data;
                $log.warn("foodCategories loaded!")

            })
        }
        // FOODS
        if(foods == undefined || foods.length == 0){
            getObjFromServer('/rest/foods').then(function(data){ //2 = idUser
                foods = data;
                $log.warn("foods loaded!")

            })
        }
    }
    */

    return {
        getFoodCategories: getFoodCategories,
        getFoods: getFoods,
        addFood: addFood
    };
});
