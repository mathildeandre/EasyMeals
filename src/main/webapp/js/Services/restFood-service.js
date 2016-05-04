/**
 * Created by fabien on 18/04/2016.
 */

var myService = angular.module('services');

myService.service("restFoodService", function ($http, $q, $log) {

    var foods = [];
    var foodCategories = [];


    function getFoodCategories(){
        return foodCategories;
    }
    function getFoods(){
        return foods;
    }

    function initLoadData(){
        $log.warn("[FOOD SERVICE] INIT - LOADING DATA")

        /* FOOD CATEGORIES */
        if(foodCategories == undefined || foodCategories.length == 0){
            getObjFromServer('/rest/foodCategories').then(function(data){ //2 = idUser
                foodCategories = data;
                $log.warn("foodCategories loaded!")

            })
        }
        /* FOODS */
        if(foods == undefined || foods.length == 0){
            getObjFromServer('/rest/foods').then(function(data){ //2 = idUser
                foods = data;
                $log.warn("foods loaded!")

            })
        }
    }


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

    return {
        getFoodCategories: getFoodCategories,
        getFoods: getFoods,
        initLoadData: initLoadData
    };
});
