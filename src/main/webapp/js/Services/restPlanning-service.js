/**
 * Created by fabien on 18/04/2016.
 */

var myService = angular.module('services');

myService.service("restPlanningService", function ($http, $q, $log, $location, restGlobalService) {


    var plannings = [];
    var planningsShopping = [];


    function getPlannings(){
        if(plannings.length == 0){
            plannings = restGlobalService.getPlannings();
            $log.warn("[restPLanning-service] getPlannings() --> on appel restGLobalService");
        }
        return plannings;
    }
    function getPlanningsShopping(){
        if(planningsShopping.length == 0){
            planningsShopping = restGlobalService.getPlanningsShopping();
            $log.warn("[restPLanning-service] getPlanningsShopping() --> on appel restGLobalService");
        }
        return planningsShopping;
    }





    /***********************************************************************************************************************************/
    /********************************          planningsShopping         **************************************************************/
    /**********************************************************************************************************************************/
    function createPlanningShopping(planning, shoppingCategories){
        postObjToServer('POST', '/rest/createPlanningShopping/'+planning.id, shoppingCategories).then(function(data){
            /* VIEW*/
            var index = plannings.indexOf(planning);
            plannings.splice(index, 1);
            if(plannings.length == 0){//si on a delete last planning, on en cree un new
                createNewPlanning();
            }

            //AJOUT DANS VUE
            var newPlanningShopping = data;
            planningsShopping.push(newPlanningShopping);
            $location.path("/errand");
        })
    }
    function createNewPlanning(){
        return getObjFromServer('/rest/createPlanning/user/2').then(function(data){ //2 = idUser

            var newPlanning = data;
            plannings.push(newPlanning);
            var index = plannings.indexOf(newPlanning);
            return index;
        })
    }
    function deletePlanningShopping(idPlanning){
        deletePlanningById(idPlanning);
    }
    /***********************************************************************************************************************************/
    /**************************************** end planningsShopping    ****************************************************************/
    /***********************************************************************************************************************************/



    /*************************************************** CLONE PLANNING ********************************************************/
    function cloneIntoMyPlannings(planningToClone){
        clonePlanning(planningToClone.id).then(function(data){
            var newClonedPlanning = data;
            plannings.push(newClonedPlanning);
            makePlanningCurrent(newClonedPlanning.id);//lastOpen..
            //ATTENTION il faut etre sûr ici que "makePlanningCurrent" s'effectue avt $location.path("/planning") qui chargement les plannigs...
            //=> loop 1000 pour patienter? -> etre que lastOpen soit bien a jour dans plannings avt daller dans plannings! :p
            $location.path("/planning");
        })
    }
    function clonePlanning(idPlanning){
        return $http({
            method: 'GET',
            url: '/rest/clonePlanning/'+idPlanning
        })
            .then(function (response) {
                if (response.status == 200) {
                    return response.data;
                }
                return $q.reject(response); //si HTTP pas de gestion d'erreur dans la version HTTP d'angular 1.3
            })
    }
    /*********************************************** end CLONE PLANNING ********************************************************/





    function makePlanningCurrent(idPlanningNewCurrent){
        /************* VIEW ************/
        //1. SET planning new current && other lastOpen to false
        var idPlanningOldCurrent = -1;
        for(var i=0; i<plannings.length; i++){
            if(plannings[i].id == idPlanningNewCurrent){
                plannings[i].lastOpen = true;
            }else if(plannings[i].lastOpen == true){
                plannings[i].lastOpen = false;
                idPlanningOldCurrent = plannings[i].id;
            }
        }
        /********* BBD ****************/
        //2.PUT lastOpen true (& oldCurrent to false)
        if(idPlanningOldCurrent != -1){
            $log.info("[makePlanningCurrent] : putLastOpenPlannings OLD & NEW")
            putLastOpenPlannings(idPlanningOldCurrent, idPlanningNewCurrent); //OLD & NEW
        }
        else{
            $log.info("[makePlanningCurrent] : putLastOpenPlannings just NEW")
            putLastOpenNewPlanning(idPlanningNewCurrent); //just NEW
        }

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
    function putLastOpenPlannings(idOldOpenPlanning, idNewOpenPlanning){ //OLD & NEW
        postObjToServer('POST', '/rest/putLastOpenPlannings', [idOldOpenPlanning, idNewOpenPlanning])
    }
    function putLastOpenNewPlanning(idNewOpenPlanning){ //just NEW
        postObjToServer('POST', '/rest/putLastOpenNewPlanning', idNewOpenPlanning)
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
        postNewRecipeCaseMeal: postNewRecipeCaseMeal,
        deleteOldRecipeCaseMeal: deleteOldRecipeCaseMeal,
        postNewNamePlanning: postNewNamePlanning,
        putShowWeekMeal: putShowWeekMeal,
        putNbPersCaseMeal: putNbPersCaseMeal,
        deletePlanningById: deletePlanningById,
        createNewPlanning: createNewPlanning,
        putNbPersGlobalPlanning: putNbPersGlobalPlanning,
        cloneIntoMyPlannings: cloneIntoMyPlannings,
        makePlanningCurrent: makePlanningCurrent,

        getPlanningsShopping: getPlanningsShopping,
        createPlanningShopping: createPlanningShopping,
        deletePlanningShopping: deletePlanningShopping


    };
});
