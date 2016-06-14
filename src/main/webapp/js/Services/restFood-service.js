/**
 * Created by fabien on 18/04/2016.
 */

var myService = angular.module('services');

myService.service("restFoodService", function ($http, $q, $log) {

    /*
    foods = [{"id":1,"name":"steack","idCategory":2,"isValidated":false},{..}]
     */
    var foodCategories = [];
    var foods = [];


    function getFoodCategories(){
        return foodCategories;
    }
    function getFoods(){
        return foods;
    }

    function addFood(food){
        foods.push(food);
    }


    /****************************************************************** INTIALIZATION **************************************************************************/
    /****************************************************************** INTIALIZATION **************************************************************************/
    function getBddFoods() {
        foods = [];
        return getObjFromServer('/rest/foods').then(function (data) {
            foods = data;
            $log.warn("foods loaded!71")
        })
    }
    function getBddFoodCategories() {
        foodCategories = [];
        return getObjFromServer('/rest/foodCategories').then(function (data) {
            foodCategories = data;
            $log.warn("foodCategories loaded!71")
        })
    }
    /****************************************************************** INTIALIZATION **************************************************************************/

    function getObjFromServer(url) {
        return $http({
            method: 'GET',
            url: url
        })
            .then(function (response) {
                if (response.status == 200) {
                    return response.data;
                }
                return $q.reject(response); //si HTTP pas de gestion d'erreur dans la version HTTP d'angular 1.3
            })
    };

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
        addFood: addFood,

        getBddFoods: getBddFoods,
        getBddFoodCategories: getBddFoodCategories
    };
});
