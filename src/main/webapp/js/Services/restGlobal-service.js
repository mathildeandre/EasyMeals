/**
 * Created by fabien on 18/04/2016.
 */

var myService = angular.module('services');

myService.service("restGlobalService", function ($http, $q, $log, $location, $localStorage, restPlanningService, restRecipeService, restFoodService) {


    function giveIdUser(){
        if($localStorage.id){
            $log.debug("[restGlobalService] giveIdUser() -existing localStorage.id  : "+$localStorage.id);
            return $localStorage.id
        }else{
            $log.debug("[restGlobalService] giveIdUser() -no existing localStorage.id  ; return id -1 ");
            return -1;
        }
    }
    var isInitGlobalDone = false;
    var idUser = giveIdUser();//$localStorage.id;



    function initGlobalLoadData(){
        if(!isInitGlobalDone){
            $log.warn(":::::::::::::::::::::::::::::[GLOBAL SERVICE] INIT - LOADING DATA")
            return restPlanningService.getBddPlannings(idUser).then(function(){
                return restRecipeService.getBddCategories(idUser).then(function(){
                    return restRecipeService.getBddSpecialities(idUser).then(function(){
                        return restRecipeService.getBddRecipeTypes().then(function(){
                            return restRecipeService.getBddRecipes(idUser).then(function(){
                                return restFoodService.getBddFoodCategories().then(function(){
                                    return restFoodService.getBddFoods().then(function(){
                                        $log.warn("*****[GLOBAL SERVICE]  everything initialized !71")
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

    function initGlobalLoadData_afterConnexion(idUser){
        $log.warn(":::::::::::::::::::::::::::::[GLOBAL SERVICE] INIT - LOADING DATA xxxxxxxxx after connexion idUser : "+idUser);
        $log.debug("[restGlobalService] just to know id existing localStorage.id?  : "+$localStorage.id); //$localStorage.id  == undefined
        return restPlanningService.getBddPlannings(idUser).then(function(){
            return restRecipeService.getBddCategories(idUser).then(function(){
                return restRecipeService.getBddSpecialities(idUser).then(function(){
                    return restRecipeService.getBddRecipes(idUser).then(function(){
                                $log.warn("*****[GLOBAL SERVICE]  everything initialized !71")
                                isInitGlobalDone = true;
                    })
                })
            })
            /******** end recipes */
        })

    }

    return {
        initGlobalLoadData: initGlobalLoadData,
        initGlobalLoadData_afterConnexion: initGlobalLoadData_afterConnexion
};
});
