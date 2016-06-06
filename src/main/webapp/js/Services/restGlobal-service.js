/**
 * Created by fabien on 18/04/2016.
 */

var myService = angular.module('services');

myService.service("restGlobalService", function ($http, $q, $log, $location) {

    var isInitGlobalDone = false;


    var plannings = [];
    var planningsShopping = [];

    var categories = [];
    var origins = [];
    var recipeTypes = [];

    var starters = [];
    var courses = [];
    var desserts = [];
    //var breakfasts = [];
    //var cocktails = [];

    var foods = [];
    var foodCategories = [];



    function getPlannings(){
        return plannings;
    }
    function getPlanningsShopping(){
        return planningsShopping;
    }

    function getCategories(){
        return categories;
    }
    function getOrigins(){
        return origins;
    }
    function getRecipeTypes(){
        return recipeTypes;
    }

    function getStarters(){
        return starters;
    }
    function getCourses(){
        return courses;
    }
    function getDesserts(){
        return desserts;
    }

    function getFoods(){
        return foods;
    }
    function getFoodCategories(){
        return foodCategories;
    }

    function initComplement(arrayRecipe){
        for(var i=0; i<arrayRecipe.length; i++){
            arrayRecipe[i].ratingSystem = {isUserEditing: false, starsEdit: [false, false, false, false, false]};
            arrayRecipe[i].timeTotal = arrayRecipe[i].timeCooking + arrayRecipe[i].timePreparation;
        }
    }



    function initGlobalLoadData(){
        if(!isInitGlobalDone){
            $log.warn(":::::::::::::::::::::::::::::[GLOBAL SERVICE] INIT - LOADING DATA")
            return getObjFromServer('/rest/plannings/user/2').then(function(data){ //2 = idUser
                var allPlannings = data;
                for(var i=0; i<allPlannings.length; i++){
                    if(allPlannings[i].isForListShop){
                        planningsShopping.push(allPlannings[i]);
                    }else{
                        plannings.push(allPlannings[i]);
                    }
                }
                $log.warn("plannings loaded!")



                /************ recipe */
                $log.warn("LOADING RECIPES DATA")
                /* CATEGORIES */
                return getObjFromServer('/rest/recipeCategories/2').then(function(data){ //2117 = idUser
                    categories = data;
                    $log.warn("categories loaded!")

                    /* ORIGINS */
                    return getObjFromServer('/rest/recipeOrigins').then(function(data){
                        origins = data;
                        $log.warn("origins loaded!")

                        /* RECIPE TYPES */
                        return getObjFromServer('/rest/recipeTypes').then(function(data){
                            recipeTypes = data;
                            $log.warn("recipeTypes loaded!")

                            /* RECIPES */
                            return getObjFromServer('/rest/recipes/2').then(function(data){ //2117 = idUser
                                var allRecipes = data;
                                initComplement(allRecipes);
                                $log.warn("allRecipes loaded!")
                                for(var i=0; i<allRecipes.length; i++){
                                    //$log.error("TRI ALL RECIPES : namerecipe : "+allRecipes[i].name +" -- recipeNameType : "+allRecipes[i].recipeType.nameType)
                                    switch(allRecipes[i].recipeType.nameType){
                                        case 'starter' : starters.push(allRecipes[i]); break;
                                        case 'course' :  courses.push(allRecipes[i]); break;
                                        case 'dessert' : desserts.push(allRecipes[i]); break;
                                        case 'breakfast' :  break;
                                        case 'cocktail' : break;
                                    }
                                }

                                /* FOOD CATEGORIES */
                                return getObjFromServer('/rest/foodCategories').then(function(data){
                                    foodCategories = data;
                                    $log.warn("foodCategories loaded!")

                                    /* FOODS */
                                    return getObjFromServer('/rest/foods').then(function(data){ //2 = idUser
                                        foods = data;
                                        $log.warn("foods loaded!")



                                        isInitGlobalDone = true;

                                    })
                                })
                            })
                        })
                    })
                })
                /******** end recipes */
            })
        }else{
            $log.warn(":::::::::::::::::::::::::::::[GLOBAL SERVICE]  data already initialized...")
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
        getPlannings: getPlannings,
        getPlanningsShopping: getPlanningsShopping,

        getCategories: getCategories,
        getOrigins: getOrigins,
        getRecipeTypes: getRecipeTypes,

        getStarters: getStarters,
        getCourses: getCourses,
        getDesserts: getDesserts,

        getFoods: getFoods,
        getFoodCategories: getFoodCategories,

        initGlobalLoadData: initGlobalLoadData

};
});
