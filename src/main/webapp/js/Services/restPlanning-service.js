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

    function postNewRecipeCaseMeal(idRecipe, idCaseMeal){
        return $http({
            method: 'POST',
            url: '/rest/postNewRecipeCaseMeal',
            data: [idRecipe, idCaseMeal]
        })
            .then(function (response) {
                if (response.status == 200) {
                    return response.data;
                }
                return $q.reject(response); //si HTTP pas de gestion d'erreur dans la version HTTP d'angular 1.3
            })
    }
    function deleteOldRecipeCaseMeal(idRecipe, idCaseMeal){
        return $http({
            method: 'POST',
            url: '/rest/deleteOldRecipeCaseMeal',
            data: [idRecipe, idCaseMeal]
        })
            .then(function (response) {
                if (response.status == 200) {
                    return response.data;
                }
                return $q.reject(response); //si HTTP pas de gestion d'erreur dans la version HTTP d'angular 1.3
            })
    }



    return {
        getPlannings: getPlannings,
        initLoadData: initLoadData,
        postNewRecipeCaseMeal: postNewRecipeCaseMeal,
        deleteOldRecipeCaseMeal: deleteOldRecipeCaseMeal
    };
});
