/**
 * Created by fabien on 18/04/2016.
 */

var myService = angular.module('services');

myService.service("restPlanningService", function ($http, $q, $log) {

    var plannings = [];


    function getPlannings(){
        return plannings;
    }


    function initLoadData(){
        $log.warn("[PLANNING SERVICE] INIT - LOADING DATA")

        /* PLANNINGS */
        if(plannings == undefined || plannings.length == 0){
            getObjFromServer('/rest/plannings/user/2').then(function(data){ //2 = idUser
                plannings = data;
                $log.warn("plannings loaded!")
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
    function insertRecipe(recipe){
        $log.warn("REQUETE with RECIPE ----  ENVOYE !!! "+recipe.name);
        /*var dataObj = {
            name : recipe.name
        };*
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
            }*
        })
            .then(function (response) {
                if (response.status == 200) {
                    return response.data;
                }
                return $q.reject(response); //si HTTP pas de gestion d'erreur dans la version HTTP d'angular 1.3
            })
    }
*/


    return {
        getPlannings: getPlannings,
        initLoadData: initLoadData
    };
});
