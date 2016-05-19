/**
 * Created by fabien on 18/04/2016.
 */

var myService = angular.module('services');

myService.service("restPlanningService", function ($http, $q, $log) {

    var plannings = [];


    function getPlannings(){
        $log.warn("ici on appel getPLannings...")
        return plannings;
    }



    function postNewRecipeCaseMeal(idRecipe, idCaseMeal){
        postObjToServer('POST', '/rest/postNewRecipeCaseMeal', [idRecipe, idCaseMeal])
    }
    function deleteOldRecipeCaseMeal(idRecipe, idCaseMeal){
        postObjToServer('POST', '/rest/deleteOldRecipeCaseMeal', [idRecipe, idCaseMeal])
    }
    function postNewNamePlanning(idPlanning, namePlanning){
        postObjToServer('POST', '/rest/postNewNamePlanning/'+namePlanning, idPlanning)
    }
    function putLastOpenPlanning(idOldOpenPlanning, idNewOpenPlanning){
        postObjToServer('POST', '/rest/putLastOpenPlanning', [idOldOpenPlanning, idNewOpenPlanning])
    }
    function putShowWeekMeal(idWeekMeal, showWeekMeal){
        postObjToServer('POST', '/rest/putShowWeekMeal/'+showWeekMeal, idWeekMeal)
    }
    function putNbPersCaseMeal(idCaseMeal, nbPersCaseMeal){
        postObjToServer('POST', '/rest/putNbPersCaseMeal/'+nbPersCaseMeal, idCaseMeal)
    }
    function putNbPersGlobalPlanning(idPlanning, nbPersGlobal){
        postObjToServer('POST', '/rest/putNbPersGlobalPlanning/'+nbPersGlobal, idPlanning)
    }
    function deletePlanningById(idPlanning){
        postObjToServer('POST', '/rest/deletePlanningById', idPlanning)
    }
    function createNewPlanning(){
        return $http({
            method: 'GET',
            url: '/rest/createPlanning/user/2'
        })
            .then(function (response) {
                if (response.status == 200) {
                    return response.data;
                }
                return $q.reject(response); //si HTTP pas de gestion d'erreur dans la version HTTP d'angular 1.3
            })
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
    function postObjToServer(method, url, data) {
        return $http({
            method: method,
            url: url,
            data: data
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
        initLoadData: initLoadData,
        postNewRecipeCaseMeal: postNewRecipeCaseMeal,
        deleteOldRecipeCaseMeal: deleteOldRecipeCaseMeal,
        postNewNamePlanning: postNewNamePlanning,
        putLastOpenPlanning: putLastOpenPlanning,
        putShowWeekMeal: putShowWeekMeal,
        putNbPersCaseMeal: putNbPersCaseMeal,
        deletePlanningById: deletePlanningById,
        createNewPlanning: createNewPlanning,
        putNbPersGlobalPlanning: putNbPersGlobalPlanning

    };
});
