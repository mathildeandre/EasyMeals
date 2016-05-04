/**
 * Created by fabien on 18/04/2016.
 */

var myService = angular.module('services');

myService.service("restRecipeService", function ($http, $q, $log) {
    var isDataReady = false;
    var isCoursesReady = false;
    var categories = [];
    var origins = [];
    var starters = [];
    var courses = [];
    var desserts = [];
    //var breakfasts = [];

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



        /* STARTERS */
        if(starters == undefined || starters.length == 0){
            getObjFromServer('/rest/recipes/starter/2').then(function(data){ //2 = idUser
                starters = data;
                $log.warn("starters loaded!")

            })
        }
        /* COURSES */
        if(courses == undefined || courses.length == 0){
            getObjFromServer('/rest/recipes/course/2').then(function(data){ //2 = idUser
                courses = data;
                isCoursesReady = true;
                $log.warn("courses loaded!")
                $log.info("isCoursesReady : "+isCoursesReady);

            })
        }
        /* DESSERTS */
        if(desserts == undefined || desserts.length == 0){
            getObjFromServer('/rest/recipes/dessert/2').then(function(data){ //2 = idUser
                desserts = data;
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

    return {
        getCategories: getCategories,
        getOrigins: getOrigins,
        getStarters: getStarters,
        getCourses: getCourses,
        getDesserts: getDesserts,
        initLoadData: initLoadData,
        getIsDataReady: getIsDataReady,
        getIsCoursesReady: getIsCoursesReady
    };
});
