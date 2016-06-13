/**
 * Created by fabien on 18/04/2016.
 */

var myService = angular.module('services');

myService.service("restRecipeService", function ($http, $q, $log, restGlobalService) {



    var recipesTmp = [
        {
            "id": 3,
            "name": "Cabillaud au Four",
            "isPublic": true,
            "user": {"id": 2117, "pseudo": "mathou", "email": null},
            "pixName": 'cabillaudFour.jpg',
            "recipeType": {"idType": 2, "nameType": "course"},
            "ingredients": [{
                "qty": 200,
                "unit": "g",
                "food": {"id": 7, "name": "cabillaud", "idCategory": 3, "isValidated": false}
            }],
            "descriptions": [{"name": "", "numDescrip": 1}],
            "origin": {"id": 1, "name": "français", "numRank": 1},
            "categories": [{"id": 2, "name": "four", "numRank": 5}, {"id": 3, "name": "legume", "numRank": 3}, {
                "id": 4,
                "name": "poisson",
                "numRank": 2
            }],
            "nbPerson": 2, "rating": 0, "nbVoter": 0,
            "timeCooking": 220,
            "timePreparation": 10,
            "isValidated": false,
            "isFavorite": false,
            "isForPlanning": false,
            "ratingUser": 0,
            "isHide": false
            /* "ratingSystem":{"isUserEditing":false,"starsEdit":[false,false,false,false,false]},
             "timeTotal":230,"onOver":false} */
        }]

    /*
    function initTmpRecipes(){
        for(var i=0; i<recipesTmp.length; i++){
            createRecipe(recipesTmp[i]);
        }
    }
    */
    /*************************** USELESS ABOVE **************************************/
    /*************************** USELESS ABOVE **************************************/
    /*************************** USELESS ABOVE **************************************/
    /*************************** USELESS ABOVE **************************************/



    var startersCategories = [];
    var coursesCategories = [];
    var dessertsCategories = [];

    var origins = [];
    var recipeTypes = [];

    var starters = [];
    var courses = [];
    var desserts = [];
    //var breakfasts = [];
    //var cocktails = [];


    function getIdRecipeType(nameRecipeType){
        getRecipeTypes();

        //$log.debug("inot getIdRecipeType")
        for(var i=0; i<recipeTypes.length; i++){
            //$log.debug("boucle"+i+" ; recipeTypes name"+recipeTypes[i].nameType+" : id : "+recipeTypes[i].idType)
            if(recipeTypes[i].nameType == nameRecipeType){
                return recipeTypes[i].idType;
            }
        }
        return 0; //erreur..
    }

    function getStartersCategories(){
        if(startersCategories.length == 0){
            startersCategories = restGlobalService.getStartersCategories();
            $log.warn("[restRecipe-service] getStartersCategories() --> on appel restGLobalService");
        }
        return startersCategories;
    }
    function getCoursesCategories(){
        if(coursesCategories.length == 0){
            coursesCategories = restGlobalService.getCoursesCategories();
            $log.warn("[restRecipe-service] getCoursesCategories() --> on appel restGLobalService");
        }
        return coursesCategories;
    }
    function getDessertsCategories(){
        if(dessertsCategories.length == 0){
            dessertsCategories = restGlobalService.getDessertsCategories();
            $log.warn("[restRecipe-service] getDessertsCategories() --> on appel restGLobalService");
        }
        return dessertsCategories;
    }

    function getCategories(recipeType) {
        switch (recipeType) {
            case 'starter' :return getStartersCategories();
            case 'course' :return getCoursesCategories();
            case 'dessert' :return getDessertsCategories();
            case 'breakfast' :return [];
            case 'cocktail' :return [];
        }
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

    function createRecipe(recipe){
        // Web service - BDD

        insertRecipe(recipe).then(function (data) { //postObjToServer('POST', '/rest/recipe/create', recipe).then(function (response) {
            //On add dans VIEW apres avoir fait le POST car on ajoute recipe.ratingSystem ce qui aurait fait bugger le post avec un champ de l'objet non existant cote server
            var recipeCreated = data;
            /* initComplement*/
            recipeCreated.ratingSystem = {isUserEditing: false, starsEdit: [false, false, false, false, false]}
            /*push VIEW */
            //$log.info("[DATA] categoriiiiiiiiiiiiiiiiiiii (name): "+recipeCreated.categories[0].name)
            switch(recipeCreated.recipeType.nameType){
                case 'starter' : starters.push(recipeCreated); break;
                case 'course' :  courses.push(recipeCreated); break;
                case 'dessert' : desserts.push(recipeCreated); break;
                case 'breakfast' :  break;
                case 'cocktail' : break;
            }
        })

    }

    function createNewSpeciality(recipeSpecialityName){
        return postObjToServer('POST', '/rest/createNewSpeciality/'+recipeSpecialityName)
    }
    function createNewCategory(recipeCategoryName, idRecipeType){
        return postObjToServer('POST', '/rest/createNewCategory/'+recipeCategoryName+'/'+idRecipeType)
    }

    function sendImage(file){
        $log.warn("REQUETE with IMAGE ----  ENVOYE !!! "+file);
        var fd = new FormData();
        fd.append('file', file);
        return $http({
            method: 'POST',
            url: '/rest/recipe/image',
            data: file,
            //transformRequest: angular.identity
            //,
            //headers: {'Content-Type': 'multipart/form-data'}
        })
            .then(function (response) {
                if (response.status == 200) {
                    return response.data;
                }
                return $q.reject(response); //si HTTP pas de gestion d'erreur dans la version HTTP d'angular 1.3
            })
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

    function putIncrNumRankCategory(idRecipeCategory, idUser){
        postObjToServer('POST', '/rest/putIncrNumRankCategory/'+idRecipeCategory+'/'+idUser)
    }
    function putIncrNumRankOrigin(idRecipeOrigin, idUser){
        postObjToServer('POST', '/rest/putIncrNumRankOrigin/'+idRecipeOrigin+'/'+idUser)
    }
    function updateBddColor(colorValue, idUser){
        postObjToServer('POST', '/rest/updateBddColor/'+colorValue+'/'+idUser)
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
        getIdRecipeType: getIdRecipeType,
        getRecipes:getRecipes,
        getSingleRecipe: getSingleRecipe,
        getBDDSingleRecipe: getBDDSingleRecipe,
        createRecipe: createRecipe,
        sendImage: sendImage,
        putIsFavorite: putIsFavorite,
        putIsForPlanning: putIsForPlanning,
        putRatingUser: putRatingUser,
        putIncrNumRankCategory: putIncrNumRankCategory,
        putIncrNumRankOrigin: putIncrNumRankOrigin,
        updateBddColor: updateBddColor,
        createNewSpeciality: createNewSpeciality,
        createNewCategory: createNewCategory
    };
});
