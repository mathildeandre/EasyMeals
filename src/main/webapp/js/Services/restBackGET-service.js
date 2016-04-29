/**
 * Created by fabien on 18/04/2016.
 */

var myService = angular.module('services');

myService.factory("restGETFactory", function ($http, $q, $log) {

    return {
        getCourses: getCourses,
        getListsShoppingPlanning: getListsShoppingPlanning,
        loadAllData : loadAllData
    };


    var starter = [];
    var courses = [];
    var desserts = [];
    var breakfasts = [];

    var listFood = [];
    var listFoodCategory = [];
    var listRecipeCategory = [];
    var listRecipeOrigin = [];

    var listsShoppingPlanning = [];

    function getCourses(){
        $log.info("[getCourses] courses name : "+courses[0].name)
        return courses;
    }
    function getListsShoppingPlanning(){
        $log.info("[getListsShoppingPlanning]");
        return listsShoppingPlanning;
    }


    function loadAllData(){
        $log.warn("ON LOAD TOUTES LES DONN2ES")


        /* COURSES */
        if(courses == undefined){
            getObjFromServer('/rest/recipes/course/2').then(function(data){ //2 = idUser
                courses = data;
                $log.warn("courses loaded!")

            })
        }

        /* LIST SHOPPING PLANNING _ user */
        if(listsShoppingPlanning == undefined){
            getObjFromServer('/rest/listShoppingPlanning/2').then(function(data){ //2 = idUser
                listsShoppingPlanning = data;
                $log.warn("listShoppingPlanning loaded!")
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

    /*
    function getCoursesServer() {
        return $http({
            method: 'GET',
            url: '/rest/recipes/course/2' //2 = idUser
        })
            .then(function (response) {
                if (response.status == 200) {
                    return response.data;
                }
                return $q.reject(response); //si HTTP pas de gestion d'erreur dans la version HTTP d'angular 1.3
            })
    };
    */
});
