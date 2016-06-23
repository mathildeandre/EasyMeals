/**
 * Created by fabien on 18/04/2016.
 */

var myService = angular.module('services');

myService.service("restRecipeService", function ($http, $q, $log) {


    /*
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
            // "ratingSystem":{"isUserEditing":false,"starsEdit":[false,false,false,false,false]},
            // "timeTotal":230,"onOver":false}
        }]
        */
    /*************************** EXEMPLE recipe ABOVE **************************************/



    var startersCategories = [];
    var coursesCategories = [];
    var dessertsCategories = [];

    var origins = [];
    var recipeTypes = [];

    var allRecipes = [];
    var starters = [];
    var courses = [];
    var desserts = [];
    //var breakfasts = [];
    //var cocktails = [];


    var coursesPix = [];

    function getCoursesPix(){
        return coursesPix;
    }



    function getStartersCategories(){
        /*if(startersCategories.length == 0){
            //startersCategories = restGlobalService.getStartersCategories();
            $log.warn("[restRecipe-service] getStartersCategories() --> on appel restGLobalService");
        }*/
        return startersCategories;
    }
    function getCoursesCategories(){
        return coursesCategories;
    }
    function getDessertsCategories(){
        return dessertsCategories;
    }
    function getOrigins(){
        return origins;
    }
    function getRecipeTypes(){
        return recipeTypes;
    }
    function getStarters(){
        return starters;
    }
    function getCourses(){
        return courses;
    }
    function getDesserts(){
        return desserts;
    }
    function getAllRecipes(){
        return allRecipes;
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
    function getRecipes(recipeType) {
        switch (recipeType) {
            case 'starter' :return getStarters();
            case 'course' :return getCourses();
            case 'dessert' :return getDesserts();
            case 'breakfast' :return [];
            case 'cocktail' :return [];
        }
    }


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






    function createRecipe(recipe, fileImg){
        // Web service - BDD

        $log.info("[restRecipe] - createRecipe() - NAME RECIPE : "+recipe.name);
        recipe.pixName = recipe.user.id+"_"+recipe.name.replace(/ /g,"_");
        $log.info("[restRecipe] - createRecipe() - NAME RECIPE PIX : "+recipe.pixName);

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

            /*REST IMG */
            sendImage(fileImg, recipe.pixName);
        })

    }

    function createNewSpeciality(recipeSpecialityName, idUser){
        return postObjToServer('POST', '/rest/createNewSpeciality/'+recipeSpecialityName+'/'+idUser)
    }
    function createNewCategory(recipeCategoryName, idRecipeType, idUser){
        return postObjToServer('POST', '/rest/createNewCategory/'+recipeCategoryName+'/'+idRecipeType+'/'+idUser)
    }

    function sendImage(file, namePix){
        $log.warn("REQUETE with IMAGE ----  ENVOYE !!! "+file+ "namePix : "+namePix);
        var fd = new FormData();
        fd.append('file', file);
        return $http({
            method: 'POST',
            url: '/rest/recipe/image/'+namePix,//+'/'+namePix,
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


    function getBDDSingleRecipe(idRecipe, idUser){
        return getObjFromServer('rest/recipe/'+idRecipe+'/'+idUser).then(function (response) {

            //for(var i=0; i<1000; i++){
            //    $log.warn("FUCK IT ;) on fait le traitement là ;)")
            //}

            return response; //si HTTP pas de gestion d'erreur dans la version HTTP d'angular 1.3
        })
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






    /****************************************************************** INTIALIZATION **************************************************************************/
    /****************************************************************** INTIALIZATION **************************************************************************/
    /********** CALL FROM restGlobal-service.js  ********************** INTIALIZATION **************************************************************************/
    /****************************************************************** INTIALIZATION **************************************************************************/


    /** USELESS FOR NOW **
    function initRecipesData(idUser){
        $log.warn(":::::::::::::::::::::::::::::[restRecipeService] INIT - LOADING DATA xxxxxxxxx after connexion idUser : "+idUser);
        return getBddCategories(idUser).then(function(){
            return getBddSpecialities(idUser).then(function(){
                return getBddRecipes(idUser).then(function(){
                    $log.warn("*****[restRecipeService]  everything initialized !")
                })
            })
        })
    }

     function getBddRecipesImages(idUser) {
        return getObjFromServer('/rest/recipesImages/' + idUser);
    }
    /** end USELESS FOR NOW **/


    function getBddCategories(idUser) {
        startersCategories = [];
        coursesCategories = [];
        dessertsCategories = [];
        return getObjFromServer('/rest/recipeCategories/' + idUser).then(function (data) { //217 = idUser
            var allCategories = data;
            $log.warn("all categories loaded!71")
            for(var i=0; i<allCategories.length; i++){
                //$log.error("TRI ALL RECIPES : namerecipe : "+allRecipes[i].name +" -- recipeNameType : "+allRecipes[i].recipeType.nameType)
                switch(allCategories[i].idRecipeType){
                    case 1 : startersCategories.push(allCategories[i]); break;
                    case 2 : coursesCategories.push(allCategories[i]); break;
                    case 3 : dessertsCategories.push(allCategories[i]); break;
                    case 4 :  break;
                    case 5 : break;
                }
            }
        })
    }
    function getBddSpecialities(idUser) {
        origins = [];
        return getObjFromServer('/rest/recipeOrigins/' + idUser).then(function (data) { //217 = idUser
            origins = data;
            $log.warn("origins loaded! 71")
        })
    }

    function getBddRecipeTypes() {
        recipeTypes = [];
        return getObjFromServer('/rest/recipeTypes').then(function (data) { //217 = idUser
            recipeTypes = data;
            $log.warn("recipeTypes loaded!71")
            //return response; ??
        })
    }


    function getBddRecipes(idUser) {
        starters = [];
        courses = [];
        desserts = [];
        allRecipes = [];
        return getObjFromServer('/rest/recipes/' + idUser).then(function (data) { //217 = idUser
            allRecipes = data;
            initComplement(allRecipes);
            $log.warn("allRecipes loaded!71")
            for(var i=0; i<allRecipes.length; i++){
                //$log.error("TRI ALL RECIPES : namerecipe : "+allRecipes[i].name +" -- recipeNameType : "+allRecipes[i].recipeType.nameType)
                switch(allRecipes[i].recipeType.nameType){
                    case 'starter' : starters.push(allRecipes[i]); break;
                    case 'course' :  courses.push(allRecipes[i]); break;
                    case 'dessert' : desserts.push(allRecipes[i]); break;
                    case 'breakfast' :  break;
                    case 'cocktail' : break;
                }
            }

            //empty img..
            /*for(var j=0; j<courses.length; j++){
                //var champImg = {image: courses[j].image };
                //coursesPix.push(champImg);
                coursesPix.push(courses[j].image);
                courses[j].image = "";
            }*/


            //return response; ??
        })
    }
    function initComplement(arrayRecipe){
        for(var i=0; i<arrayRecipe.length; i++){
            arrayRecipe[i].ratingSystem = {isUserEditing: false, starsEdit: [false, false, false, false, false]};
            arrayRecipe[i].timeTotal = arrayRecipe[i].timeCooking + arrayRecipe[i].timePreparation;
        }
    }



    /****************************************************************** end INTIALIZATION **************************************************************************/
    /****************************************************************** end INTIALIZATION **************************************************************************/
    /****************************************************************** end INTIALIZATION **************************************************************************/



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
        createNewCategory: createNewCategory,

        getBddCategories: getBddCategories,
        getBddSpecialities: getBddSpecialities,
        getBddRecipeTypes: getBddRecipeTypes,
        getBddRecipes: getBddRecipes,

        getCoursesPix: getCoursesPix,
        getAllRecipes: getAllRecipes

    };
});
