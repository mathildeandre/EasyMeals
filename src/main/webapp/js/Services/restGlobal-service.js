/**
 * Created by fabien on 18/04/2016.
 */

var myService = angular.module('services');

myService.service("restGlobalService", function ($http, $q, $log, $location) {

    var isInitGlobalDone = false;


    var plannings = [];
    var planningsShopping = [];


    var startersCategories = [];
    var coursesCategories = [];
    var dessertsCategories = [];
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

    function getStartersCategories(){
        return startersCategories;
    }
    function getCoursesCategories(){
        return coursesCategories;
    }
    function getDessertsCategories(){
        return dessertsCategories;
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
                    var allCategories = data;
                    $log.warn("all categories loaded!")
                    for(var i=0; i<allCategories.length; i++){
                        //$log.error("TRI ALL RECIPES : namerecipe : "+allRecipes[i].name +" -- recipeNameType : "+allRecipes[i].recipeType.nameType)
                        switch(allCategories[i].idRecipeType){
                            case 1 : startersCategories.push(allCategories[i]); break;
                            case 2 : coursesCategories.push(allCategories[i]); break;
                            case 3 : dessertsCategories.push(allCategories[i]); break;
                            case 4 :  break;
                            case 5 : break;
                        }
                    }

                    /* ORIGINS */
                    return getObjFromServer('/rest/recipeOrigins/2').then(function(data){//2117 = idUser
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

        getStartersCategories: getStartersCategories,
        getCoursesCategories: getCoursesCategories,
        getDessertsCategories: getDessertsCategories,
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
