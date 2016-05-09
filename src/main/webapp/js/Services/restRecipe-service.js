/**
 * Created by fabien on 18/04/2016.
 */

var myService = angular.module('services');

myService.service("restRecipeService", function ($http, $q, $log) {
    var isDataReady = false;
    var isCoursesReady = false;

    var categories = [];
    var origins = [];
    var recipeTypes = [];

    var starters = [];
    var courses = [];
    var desserts = [];
    //var breakfasts = [];
    //var cocktails = [];

    function getIsDataReady(){
        return isDataReady;
    }
    function getIsCoursesReady(){
        return isCoursesReady;
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
    function initComplement(array){
        for(var i=0; i<array.length; i++){
            array[i].ratingSystem = {isUserEditing: false, starsEdit: [false, false, false, false, false]}
        }
    }


    function getRecipes(recipeType) {
        switch (recipeType) {
            case 'starter' :return starters;
            case 'course' :return courses;
            case 'dessert' :return desserts;
            case 'breakfast' :return [];
            case 'cocktail' :return [];
        }
    }

    function createRecipe(recipe, recipeType){
        switch(recipeType){
            case 'starter' : starters.push(recipe); break;
            case 'course' :  courses.push(recipe); break;
            case 'dessert' : desserts.push(recipe); break;
            case 'breakfast' :  break;
            case 'cocktail' : break;
        }

        // Web service
        insertRecipe(recipe);
    }


    function getSingleRecipe(recipeType, recipeId){
        var arr;
        switch (recipeType) {
            case 'starter' : arr = starters; break;
            case 'course' : arr = courses; break;
            case 'dessert' : arr = desserts; break;
            case 'breakfast' : arr = []; break;
            case 'cocktail' : arr = []; break;
        }
        for(var i=0; i<arr.length; i++){
            if(arr[i].id == recipeId){
                return arr[i];
            }
        }
        return null;
    }
    /*
    function getStarters(){
        return starters;
    }
    function getCourses(){
        $log.info("[getCourses] courses name : "+courses[0].name)
        return courses;
    }
    function getDesserts(){
        return desserts;
    }
    */

    function initLoadData(){
        $log.warn("[RECIPE SERVICE] INIT - LOADING DATA")

        /* CATEGORIES */
        if(categories == undefined || categories.length == 0){
            getObjFromServer('/rest/recipeCategories').then(function(data){ //2 = idUser
                categories = data;
                $log.warn("categories loaded!")

            })
        }
        /* ORIGINS */
        if(origins == undefined || origins.length == 0){
            getObjFromServer('/rest/recipeOrigins').then(function(data){ //2 = idUser
                origins = data;
                $log.warn("origins loaded!")

            })
        }
        /* RECIPE TYPES */
        if(recipeTypes == undefined || recipeTypes.length == 0){
            getObjFromServer('/rest/recipeTypes').then(function(data){ //2 = idUser
                recipeTypes = data;
                $log.warn("recipeTypes loaded!")

            })
        }



        /* STARTERS */
        if(starters == undefined || starters.length == 0){
            getObjFromServer('/rest/recipes/starter/2').then(function(data){ //2 = idUser
                starters = data;
                initComplement(starters);
                $log.warn("starters loaded!")

            })
        }
        /* COURSES */
        if(courses == undefined || courses.length == 0){
            getObjFromServer('/rest/recipes/course/2').then(function(data){ //2 = idUser
                courses = data;
                initComplement(courses);
                isCoursesReady = true;
                $log.warn("courses loaded!")
                $log.info("isCoursesReady : "+isCoursesReady);

            })
        }
        /* DESSERTS */
        if(desserts == undefined || desserts.length == 0){
            getObjFromServer('/rest/recipes/dessert/2').then(function(data){ //2 = idUser
                desserts = data;
                initComplement(desserts);
                $log.warn("desserts loaded!")

            })
        }
        $log.info("isDataReady : "+isDataReady);
        isDataReady = true;
        $log.info("isDataReady : "+isDataReady);
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




    function insertRecipe(recipe){
        $log.warn("REQUETE with RECIPE ----  ENVOYE !!! "+recipe.name);
        /*var dataObj = {
            name : recipe.name
        };*/
        return $http({
            method: 'POST',
            url: '/rest/recipe/create',
            data: recipe /*{
                name:recipe.name,
                user:recipe.user,
                recipeType:recipe.recipeType,
                origin:recipe.origin,//recipe.origin
                categories:recipe.categories,
                timeCooking:recipe.timeCooking,
                timePreparation:recipe.timePreparation,
                nbPerson:recipe.nbPerson,
                descriptions:recipe.descriptions,
                ingredients:[{qty:1, unit:'g', food:{"id":1,"name":"dd","idCategory":0,"isValidated":false}}]
            }*/
        })
            .then(function (response) {
                if (response.status == 200) {
                    return response.data;
                }
                return $q.reject(response); //si HTTP pas de gestion d'erreur dans la version HTTP d'angular 1.3
            })
    }



    return {
        getCategories: getCategories,
        getOrigins: getOrigins,
        getRecipeTypes: getRecipeTypes,
        getRecipes:getRecipes,
        getSingleRecipe: getSingleRecipe,
        initLoadData: initLoadData,
        getIsDataReady: getIsDataReady,
        getIsCoursesReady: getIsCoursesReady,
        createRecipe: createRecipe
    };
});
