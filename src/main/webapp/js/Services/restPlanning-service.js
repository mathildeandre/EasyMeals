/**
 * Created by fabien on 18/04/2016.
 */

var myService = angular.module('services');

myService.service("restPlanningService", function ($http, $q, $log, $location) {


    var planningEmptyForNoUserConnected = {"id":-1,"name":"planningForUserNotConnected","lastOpen":true,"nbPersGlobal":4,"isForListShop":false,"weekMeals":[{"id":5,"weekMealName":"breakfast","show":false,"caseMeals":[{"id":29,"nbPers":4,"numDay":1,"recipes":[]},{"id":30,"nbPers":4,"numDay":2,"recipes":[]},{"id":31,"nbPers":4,"numDay":3,"recipes":[]},{"id":32,"nbPers":4,"numDay":4,"recipes":[]},{"id":33,"nbPers":4,"numDay":5,"recipes":[]},{"id":34,"nbPers":4,"numDay":6,"recipes":[]},{"id":35,"nbPers":4,"numDay":7,"recipes":[]}]},{"id":6,"weekMealName":"lunch","show":true,"caseMeals":[{"id":36,"nbPers":4,"numDay":1,"recipes":[]},{"id":37,"nbPers":4,"numDay":2,"recipes":[]},{"id":38,"nbPers":4,"numDay":3,"recipes":[]},{"id":39,"nbPers":4,"numDay":4,"recipes":[]},{"id":40,"nbPers":4,"numDay":5,"recipes":[]},{"id":41,"nbPers":4,"numDay":6,"recipes":[]},{"id":42,"nbPers":4,"numDay":7,"recipes":[]}]},{"id":7,"weekMealName":"snack","show":false,"caseMeals":[{"id":43,"nbPers":4,"numDay":1,"recipes":[]},{"id":44,"nbPers":4,"numDay":2,"recipes":[]},{"id":45,"nbPers":4,"numDay":3,"recipes":[]},{"id":46,"nbPers":4,"numDay":4,"recipes":[]},{"id":47,"nbPers":4,"numDay":5,"recipes":[]},{"id":48,"nbPers":4,"numDay":6,"recipes":[]},{"id":49,"nbPers":4,"numDay":7,"recipes":[]}]},{"id":8,"weekMealName":"dinner","show":true,"caseMeals":[{"id":50,"nbPers":4,"numDay":1,"recipes":[]},{"id":51,"nbPers":4,"numDay":2,"recipes":[]},{"id":52,"nbPers":4,"numDay":3,"recipes":[]},{"id":53,"nbPers":4,"numDay":4,"recipes":[]},{"id":54,"nbPers":4,"numDay":5,"recipes":[]},{"id":55,"nbPers":4,"numDay":6,"recipes":[]},{"id":56,"nbPers":4,"numDay":7,"recipes":[]}]}]};


    var plannings = [planningEmptyForNoUserConnected];
    var planningsShopping = [];


    function getPlannings(){
        if(plannings.length == 0){
            //plannings = restGlobalService.getPlannings();
            $log.warn("[restPLanning-service] getPlannings() --> on appel restGLobalService");
        }
        return plannings;
    }

    function getPlanningsShopping(){
        if(planningsShopping.length == 0){
            //planningsShopping = restGlobalService.getPlanningsShopping();
            $log.warn("[restPLanning-service] getPlanningsShopping() --> on appel restGLobalService");
        }
        return planningsShopping;
    }



    /* fct ajoute pour modals (modalRecipeFillPlanning-ctrl & modalPlanningCaseMeal-ctrl) -> push recipe ds caseMeal qui declenchera updatePlanningBDD et gerera le reste! */
    function addRecipeIntoCaseMeal(recipe, caseMeal){
        $log.warn("[restPlanning]INTO ADD RECIPE INTO CASE MEAL !!!!! ------------------------------- ")
        for(var k=0; k<plannings.length; k++){

            for(var i=0; i<plannings[k].weekMeals.length; i++){
                var weekMeal = plannings[k].weekMeals[i];
                for(var j=0; j<weekMeal.caseMeals.length; j++){
                    if(weekMeal.caseMeals[j].id == caseMeal.id){
                        $log.warn("BOOOM caseMeal found !!! ")
                        weekMeal.caseMeals[j].recipes.push(recipe);
                    }
                }
            }
        }
    }

    /***********************************************************************************************************************************/
    /********************************          planningsShopping         **************************************************************/
    /**********************************************************************************************************************************/

    function createPlanningShopping(planning, shoppingCategories){ //called when goShopping -- no creation from errand ..
        postObjToServer('POST', '/rest/createPlanningShopping/'+planning.id, shoppingCategories).then(function(data){
            /* VIEW*/
            var index = plannings.indexOf(planning);
            plannings.splice(index, 1);
            if(plannings.length == 0){//si on a delete last planning, on en cree un new
                /*createNewPlanning().then(function(index){
                    makePlanningCurrent(plannings[0].id, false);//lastOpen..
                    $location.path("/errand");
                })*/
            }else{
                makePlanningCurrent(plannings[0].id, false);//lastOpen..
            }

            //AJOUT DANS VUE
            var newPlanningShopping = data;
            planningsShopping.push(newPlanningShopping);
            makePlanningCurrent(newPlanningShopping.id, true); //lastOpen...
            $location.path("/errand");

        })
    }
    function createNewPlanning(){
        return getObjFromServer('/rest/createPlanning/user/2').then(function(data){ //2 = idUser //2117

            var newPlanning = data;
            plannings.push(newPlanning);
            var index = plannings.indexOf(newPlanning);
            return index;
        })
    }
    function cutShoppingToPlanning(idPlanningShopping){
        postObjToServer('POST', '/rest/cutShoppingToPlanning/'+idPlanningShopping).then(function(data){

            var newClonedPlanning = data;
            plannings.push(newClonedPlanning);
            makePlanningCurrent(newClonedPlanning.id, false);//lastOpen..
            //ATTENTION il faut etre sûr ici que "makePlanningCurrent" s'effectue avt $location.path("/planning") qui chargement les plannigs...
            //=> loop 1000 pour patienter? -> etre que lastOpen soit bien a jour dans plannings avt daller dans plannings! :p
            $location.path("/planning");
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
            makePlanningCurrent(newClonedPlanning.id, false);//lastOpen..
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





    function makePlanningCurrent(idPlanningNewCurrent, isForListShop){
        /************* VIEW ************/
        //1. SET planning new current && other lastOpen to false
        var idPlanningOldCurrent = -1;
        var arrayPlanning = [];
        if(isForListShop){
            arrayPlanning = planningsShopping;
        }else{
            arrayPlanning = plannings;
        }



        for(var i=0; i<arrayPlanning.length; i++){
            if(arrayPlanning[i].id == idPlanningNewCurrent){
                arrayPlanning[i].lastOpen = true;
                $log.debug("[NEW CURRENT] name : "+arrayPlanning[i].name+" .. (id:"+idPlanningNewCurrent+") - sera updater ds la base a TRUE")
            }else if(arrayPlanning[i].lastOpen == true){
                arrayPlanning[i].lastOpen = false;
                $log.debug("[OLD CURRENT] name : "+arrayPlanning[i].name+" .. (id:"+arrayPlanning[i].id+") - sera updater ds la base a FALSE")
                idPlanningOldCurrent = arrayPlanning[i].id;
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
    function getNamePlanning(idPlanning){
        return getObjFromServer('/rest/getNamePlanning/'+idPlanning)
    }





    /**************************** INTIALIZATION call by GLOBAL service*********************************/
    function getBddPlannings(idUser) {
        planningsShopping = [];
        plannings = [];
        return getObjFromServer('/rest/plannings/user/' + idUser).then(function (data) { //217 = idUser
            var allPlannings = data;
            for (var i = 0; i < allPlannings.length; i++) {
                if (allPlannings[i].isForListShop) {
                    planningsShopping.push(allPlannings[i]);
                } else {
                    plannings.push(allPlannings[i]);
                }
            }
            $log.warn("plannings (& planningsShopping) loaded!71")
            //return response; ??
            if(plannings[0] == undefined){
                $log.warn("plannings (& planningsShopping) loaded!72")
                plannings.push(planningEmptyForNoUserConnected);
            }else{
                $log.warn("plannings (& planningsShopping) loaded!73"+plannings)

            }
            $log.warn("plannings (& planningsShopping) loaded!74")
        })
    }
    /**************************** end INTIALIZATION *********************************/


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
        getNamePlanning: getNamePlanning,
        createNewPlanning: createNewPlanning,
        putNbPersGlobalPlanning: putNbPersGlobalPlanning,
        cloneIntoMyPlannings: cloneIntoMyPlannings,
        makePlanningCurrent: makePlanningCurrent,
        addRecipeIntoCaseMeal: addRecipeIntoCaseMeal,

        getPlanningsShopping: getPlanningsShopping,
        createPlanningShopping: createPlanningShopping,
        deletePlanningShopping: deletePlanningShopping,
        cutShoppingToPlanning: cutShoppingToPlanning,

        getBddPlannings: getBddPlannings


    };
});
