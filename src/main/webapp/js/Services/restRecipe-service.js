/**
 * Created by fabien on 18/04/2016.
 */

var myService = angular.module('services');

myService.service("restRecipeService", function ($http, $q, $log, restGlobalService) {

    var categories = [];
    var origins = [];
    var recipeTypes = [];

    var starters = [];
    var courses = [];
    var desserts = [];
    //var breakfasts = [];
    //var cocktails = [];

    function getCategories(){
        if(categories.length == 0){
            categories = restGlobalService.getCategories();
            $log.warn("[restRecipe-service] getCategories() --> on appel restGLobalService");
        }
        return categories;
    }
    function getOrigins(){
        if(origins.length == 0){
            origins = restGlobalService.getOrigins();
            $log.warn("[restRecipe-service] getOrigins() --> on appel restGLobalService");
        }
        return origins;
    }
    function getRecipeTypes(){
        if(recipeTypes.length == 0){
            recipeTypes = restGlobalService.getRecipeTypes();
            $log.warn("[restRecipe-service] getRecipeTypes() --> on appel restGLobalService");
        }
        return recipeTypes;
    }
    function getStarters(){
        if(starters.length == 0){
            starters = restGlobalService.getStarters();
            $log.warn("[restRecipe-service] getStarters() --> on appel restGLobalService");
        }
        return starters;
    }
    function getCourses(){
        if(courses.length == 0){
            courses = restGlobalService.getCourses();
            $log.warn("[restRecipe-service] getCourses() --> on appel restGLobalService");
        }
        return courses;
    }
    function getDesserts(){
        if(desserts.length == 0){
            desserts = restGlobalService.getDesserts();
            $log.warn("[restRecipe-service] getDesserts() --> on appel restGLobalService");
        }
        return desserts;
    }






    function getRecipes(recipeType) {
        switch (recipeType) {
            case 'starter' :return getStarters();
            case 'course' :return getCourses();
            case 'dessert' :return getDesserts();
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
        $log.debug("PUTIN FUCKING ASSHOLE...+ ")
        switch (recipeType) {
            case 'starter' : arr = starters; break;
            case 'course' : arr = courses; break;
            case 'dessert' : arr = desserts; break;
            case 'breakfast' : arr = []; break;
            case 'cocktail' : arr = []; break;
        }

        $log.debug("-- FUCKING ASSHOLE...+ "+arr[0].name)
        for(var i=0; i<arr.length; i++){
            if(arr[i].id == recipeId){
                return arr[i];
            }
        }
        return null;
    }


    function getBDDSingleRecipe(idRecipe){
        return getObjFromServer('rest/recipe/'+idRecipe);
    }




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


    function putIsFavorite(idRecipe, idUser, isFavorite){
        postObjToServer('POST', '/rest/putIsFavorite/'+idRecipe+'/'+idUser, isFavorite)
    }
    function putIsForPlanning(idRecipe, idUser, isForPlanning){
        postObjToServer('POST', '/rest/putIsForPlanning/'+idRecipe+'/'+idUser, isForPlanning)
    }
    function putRatingUser(idRecipe, idUser, ratingUser){
        postObjToServer('POST', '/rest/putRatingUser/'+idRecipe+'/'+idUser, ratingUser)
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
        getCategories: getCategories,
        getOrigins: getOrigins,
        getRecipeTypes: getRecipeTypes,
        getRecipes:getRecipes,
        getSingleRecipe: getSingleRecipe,
        getBDDSingleRecipe: getBDDSingleRecipe,
        createRecipe: createRecipe,
        putIsFavorite: putIsFavorite,
        putIsForPlanning: putIsForPlanning,
        putRatingUser: putRatingUser
    };
});
