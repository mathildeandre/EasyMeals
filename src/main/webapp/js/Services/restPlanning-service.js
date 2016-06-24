/**
 * Created by fabien on 18/04/2016.
 */

var myService = angular.module('services');

myService.service("restPlanningService", function ($http, $q, $log, $localStorage, $location) {

    function giveIdUser(){
        if($localStorage.userConnected){
            $log.debug("[restPlanningService] giveIdUser() -existing localStorage.id  : "+$localStorage.userConnected.id);
            return $localStorage.userConnected.id
        }else{
            $log.debug("[restPlanningService] giveIdUser() -no existing localStorage.id  ; return id -1 ");
            return -1;
        }
    }
    function setIdUser(idUserConnected){
        idUser = idUserConnected;
    }
    var idUser = giveIdUser();//$localStorage.id;


    var nbPlanTmp = 1;
    var planningEmptyForNoUserConnected = {"id":-1,"name":"planningTemporaire"+nbPlanTmp,"lastOpen":true,"nbPersGlobal":4,"isForListShop":false,"weekMeals":[{"id":5,"weekMealName":"breakfast","show":false,"caseMeals":[{"id":29,"nbPers":4,"numDay":1,"recipes":[]},{"id":30,"nbPers":4,"numDay":2,"recipes":[]},{"id":31,"nbPers":4,"numDay":3,"recipes":[]},{"id":32,"nbPers":4,"numDay":4,"recipes":[]},{"id":33,"nbPers":4,"numDay":5,"recipes":[]},{"id":34,"nbPers":4,"numDay":6,"recipes":[]},{"id":35,"nbPers":4,"numDay":7,"recipes":[]}]},{"id":6,"weekMealName":"lunch","show":true,"caseMeals":[{"id":36,"nbPers":4,"numDay":1,"recipes":[]},{"id":37,"nbPers":4,"numDay":2,"recipes":[]},{"id":38,"nbPers":4,"numDay":3,"recipes":[]},{"id":39,"nbPers":4,"numDay":4,"recipes":[]},{"id":40,"nbPers":4,"numDay":5,"recipes":[]},{"id":41,"nbPers":4,"numDay":6,"recipes":[]},{"id":42,"nbPers":4,"numDay":7,"recipes":[]}]},{"id":7,"weekMealName":"snack","show":false,"caseMeals":[{"id":43,"nbPers":4,"numDay":1,"recipes":[]},{"id":44,"nbPers":4,"numDay":2,"recipes":[]},{"id":45,"nbPers":4,"numDay":3,"recipes":[]},{"id":46,"nbPers":4,"numDay":4,"recipes":[]},{"id":47,"nbPers":4,"numDay":5,"recipes":[]},{"id":48,"nbPers":4,"numDay":6,"recipes":[]},{"id":49,"nbPers":4,"numDay":7,"recipes":[]}]},{"id":8,"weekMealName":"dinner","show":true,"caseMeals":[{"id":50,"nbPers":4,"numDay":1,"recipes":[]},{"id":51,"nbPers":4,"numDay":2,"recipes":[]},{"id":52,"nbPers":4,"numDay":3,"recipes":[]},{"id":53,"nbPers":4,"numDay":4,"recipes":[]},{"id":54,"nbPers":4,"numDay":5,"recipes":[]},{"id":55,"nbPers":4,"numDay":6,"recipes":[]},{"id":56,"nbPers":4,"numDay":7,"recipes":[]}]}]};


    var plannings = [];
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

        $log.info("[restPlanning] -- -createPlanningShopping()-  idUser:"+idUser);
        if(idUser == -1){//not connected

            //AJOUT PLANNING DANS LIST SHOP -- VIEW
            var index = plannings.indexOf(planning);

            //1. creation new planning shop a partir de planning (il manque que la liste de categories)
            var newPlanningShop = plannings[index];
            //2. creation liste categories
            // "shoppingCategories":[{"id":7,"name":"Viande","numRank":2,"ingredients":[{"qty":4,"unit":"","food":{"id":null,"name":"steack haché","idCategory":0,"isValidated":false}}]},{"id":8,"name":"Fruit/Legume","numRank":4,"ingredients":[{"qty":3,"unit":"","food":{"id":null,"name":"tomate","idCategory":0,"isValidated":false}},{"qty":1,"unit":"","food":{"id":null,"name":"salade","idCategory":0,"isValidated":false}}]},{"id":9,"name":"Pain/Viennoiserie/Patisserie","numRank":5,"ingredients":[{"qty":8,"unit":"","food":{"id":null,"name":"pain à burger","idCategory":0,"isValidated":false}}]},{"id":10,"name":"Fromage/Yaourt","numRank":8,"ingredients":[{"qty":100,"unit":"g","food":{"id":null,"name":"fromage rapé","idCategory":0,"isValidated":false}}]},{"id":11,"name":"Epicerie Condiment","numRank":14,"ingredients":[{"qty":1,"unit":"","food":{"id":null,"name":"sauce burger","idCategory":0,"isValidated":false}}]}]}
            var listCategory = [];
            for(var i=0; i<shoppingCategories.length; i++) {
                if(shoppingCategories[i].ingredients.length > 0) {
                    listCategory.push(shoppingCategories[i]);
                }

            }
            newPlanningShop.shoppingCategories = listCategory;
            //3. ajout new planning shop  a planningsShopping
            planningsShopping.push(newPlanningShop);

            //4.suppression des planning
            plannings.splice(index, 1);
            //5.ajout dans planning new planning (CLONE planningEmptyForNoUserConnected de base)
            nbPlanTmp++;
            var newPlaningTmp = clone(planningEmptyForNoUserConnected)//NEW OBJECT
            newPlaningTmp.name = "planningTemporaire"+nbPlanTmp;
            plannings.push(newPlaningTmp);
            $location.path("/errand");

             }else{
            postObjToServer('POST', '/rest/createPlanningShopping/'+planning.id+'/'+idUser, shoppingCategories).then(function(data){ //idUser ADDED for CHECK token
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
    }
    function createNewPlanning(){
        return getObjFromServer('/rest/createPlanning/user/'+idUser).then(function(data){ // idUser

            var newPlanning = data;
            plannings.push(newPlanning);
            var index = plannings.indexOf(newPlanning);
            return index;
        })
    }
    function cutShoppingToPlanning(idPlanningShopping){
        postObjToServer('POST', '/rest/cutShoppingToPlanning/'+idPlanningShopping+'/'+idUser).then(function(data){ //idUser ADDED for CHECK token

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
    function clonePlanning(idPlanning){ //idUser ADDED for CHECK token
        return $http({
            method: 'GET',
            url: '/rest/clonePlanning/'+idPlanning+'/'+idUser
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
        postObjToServer('POST', '/rest/postNewRecipeCaseMeal/'+idUser, [idRecipe, idCaseMeal]) //idUser ADDED for CHECK token
    }
    function deleteOldRecipeCaseMeal(idRecipe, idCaseMeal){
        postObjToServer('POST', '/rest/deleteOldRecipeCaseMeal/'+idUser, [idRecipe, idCaseMeal])//idUser ADDED for CHECK token
    }
    function postNewNamePlanning(idPlanning, namePlanning){
        postObjToServer('POST', '/rest/postNewNamePlanning/'+namePlanning+'/'+idUser, idPlanning)//idUser ADDED for CHECK token
    }
    function putLastOpenPlannings(idOldOpenPlanning, idNewOpenPlanning){ //OLD & NEW
        postObjToServer('POST', '/rest/putLastOpenPlannings/'+idUser, [idOldOpenPlanning, idNewOpenPlanning])//idUser ADDED for CHECK token
    }
    function putLastOpenNewPlanning(idNewOpenPlanning){ //just NEW
        postObjToServer('POST', '/rest/putLastOpenNewPlanning/'+idUser, idNewOpenPlanning)//idUser ADDED for CHECK token
    }
    function putShowWeekMeal(idWeekMeal, showWeekMeal){
        postObjToServer('POST', '/rest/putShowWeekMeal/'+showWeekMeal+'/'+idUser, idWeekMeal)//idUser ADDED for CHECK token
    }
    function putNbPersCaseMeal(idCaseMeal, nbPersCaseMeal){
        postObjToServer('POST', '/rest/putNbPersCaseMeal/'+nbPersCaseMeal+'/'+idUser, idCaseMeal)//idUser ADDED for CHECK token
    }
    function putNbPersGlobalPlanning(idPlanning, nbPersGlobal){
        postObjToServer('POST', '/rest/putNbPersGlobalPlanning/'+nbPersGlobal+'/'+idUser, idPlanning)//idUser ADDED for CHECK token
    }
    function deletePlanningById(idPlanning){
        postObjToServer('POST', '/rest/deletePlanningById/'+idUser, idPlanning)//idUser ADDED for CHECK token
    }
    function getNamePlanning(idPlanning){
        return getObjFromServer('/rest/getNamePlanning/'+idPlanning+'/'+idUser)//idUser ADDED for CHECK token
    }





    /**************************** INTIALIZATION call by GLOBAL service*********************************/
    function getBddPlannings() {
        planningsShopping = [];
        plannings = [];
        return getObjFromServer('/rest/plannings/user/' + idUser).then(function (data) { //idUser
            var allPlannings = data;
            for (var i = 0; i < allPlannings.length; i++) {
                if (allPlannings[i].isForListShop) {
                    planningsShopping.push(allPlannings[i]);
                } else {
                    plannings.push(allPlannings[i]);
                }
            }
            $log.warn("plannings (& planningsShopping) loaded!")
            //return response; ??
            if(idUser == -1){
                $log.debug("[restPlanningService] -getBddPlannings()-  idUser -1 : push >>> planningEmptyForNoUserConnected <<<")
                plannings.push(clone(planningEmptyForNoUserConnected));
            }else{
                $log.debug("[restPlanningService] -getBddPlannings()- user connected!  id:"+idUser);
            }
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



    function clone(obj) {
        var copy;
        // Handle the 3 simple types, and null or undefined
        if (null == obj || "object" != typeof obj) return obj;
        // Handle Array
        if (obj instanceof Array) {
            copy = [];
            for (var i = 0, len = obj.length; i < len; i++) {
                copy[i] = clone(obj[i]);
            }
            return copy;
        }
        // Handle Object
        if (obj instanceof Object) {
            copy = {};
            for (var attr in obj) {
                if (obj.hasOwnProperty(attr)) copy[attr] = clone(obj[attr]);
            }
            return copy;
        }
        throw new Error("Unable to copy obj! Its type isn't supported.");
    }



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

        getBddPlannings: getBddPlannings,

        setIdUser: setIdUser


    };
});
