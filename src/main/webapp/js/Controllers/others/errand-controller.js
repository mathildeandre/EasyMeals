/**
 * Created by fabien on 12/04/2016.
 */

var myModule = angular.module('controllers');

myModule.controller('ErrandCtrl', function($scope, $log, $location, $localStorage, AppendixFunctionsService, restPlanningService, restUserService) {

    /*********************************** USER CONNECTED **************************************/
    $scope.isUserConnected = false;
    $scope.userConnected = {id: 0, pseudo: '', email: '', isAdmin: false, colorThemeRecipe: 'grey'};
    if($localStorage.userConnected){
        $log.debug("[[RecipeCtrl]] - USER CONNECTED !! ($localStorage known)")
        $scope.isUserConnected = true;
        $scope.userConnected = $localStorage.userConnected;
    }
    /*********************************** end USER CONNECTED **************************************/


    /*********************************** check for USER CONNECTED **************************************/
    var isTokenValidOrExpired = function(){

        if($localStorage.userConnected){
            restUserService.getIsTokenValid($scope.userConnected.id).then(function (isTokenStilValid) { //217 = idUser
                if(isTokenStilValid){
                    //user still connected
                    $log.error("[ErrandCtrl] -isTokenValidOrExpired()- USER IS STILL CONNECTED ->  do nothing")
                }else{
                    //user not connected anymore
                    $log.error("[ErrandCtrl] -isTokenValidOrExpired()- USER SHOULD NOT BE CONNECTED ANYMORE -> logout()")
                    $scope.$emit('userLogout');
                }
            })
        }
    }
    var checkIfTokenExpired = isTokenValidOrExpired();
    /*********************************** end check for USER CONNECTED **************************************/



    $scope.$emit('intoErrand'); //will tell to parents (global-controller.js) to modify pix


    $scope.isTablet = function(){
        if (navigator.userAgent.match('/Android|iPad')){
            return true
        }else{
            return false
        }
        //return navigator.userAgent.match('/Android|iPad');
    }
    $scope.nameDevice =function(){
        if (navigator.userAgent.match('/Android|iPad')){
            return 'BOOM'
        }else{
            return 'oo'
        }
    }
    $scope.planningsShopping = restPlanningService.getPlanningsShopping();

    $scope.currentPlanningShopping = $scope.planningsShopping.filter(function(obj){
        return obj.lastOpen == true;
    })[0];

    $scope.cloneIntoMyPlannings = function(){
        if(!$scope.isUserConnected ){
            $location.path('/connexion');
        }else{
            restPlanningService.cloneIntoMyPlannings($scope.currentPlanningShopping);
        }
    }

    $scope.cutShoppingToPlanning = function(){
        if(!$scope.isUserConnected ){
            $location.path('/connexion');
        }else{
            $log.info("EH VOIAL LE CUT CUT CUT")
            var idPlanningShoppingToCut = $scope.currentPlanningShopping.id;
            //delete from VIEW
            var index = $scope.planningsShopping.indexOf($scope.currentPlanningShopping);
            $scope.planningsShopping.splice(index, 1);
            if($scope.planningsShopping != undefined && $scope.planningsShopping.length > 0) { //if we didnt deleted the last planning..
                $scope.currentPlanningShopping = $scope.planningsShopping[0];
                restPlanningService.makePlanningCurrent($scope.currentPlanningShopping.id, true)//lastOpen
            }
            //cut from BDD
            restPlanningService.cutShoppingToPlanning(idPlanningShoppingToCut);
        }

    }


    $scope.deleteListShop = function(){

        if(!$scope.isUserConnected ){
            $location.path('/connexion');
        }else{
            var idPlanningShoppingToDelete = $scope.currentPlanningShopping.id;
            //delete from VIEW
            var index = $scope.planningsShopping.indexOf($scope.currentPlanningShopping);
            $scope.planningsShopping.splice(index, 1);
            if($scope.planningsShopping != undefined && $scope.planningsShopping.length > 0) { //if we didnt deleted the last planning..
                $scope.currentPlanningShopping = $scope.planningsShopping[0];
                restPlanningService.makePlanningCurrent($scope.currentPlanningShopping.id, true)//lastOpen
            }
            //delete from BDD
            restPlanningService.deletePlanningShopping(idPlanningShoppingToDelete);
        }


    }


    /* mealType = breakfast, lunch, snack, dinner*/
    $scope.displayMealType = function(mealType){
        return AppendixFunctionsService.displayMealType(mealType);
    }

    $scope.recipesToDisplay = [];
    $scope.resetRecipesToDisplay = function(){
        $scope.recipesToDisplay = [];
        if($scope.planningsShopping != undefined && $scope.planningsShopping.length > 0) {
            restPlanningService.makePlanningCurrent($scope.currentPlanningShopping.id, true)//lastOpen
        }

    }
    $scope.nbPersOfMeal = 1;
    $scope.displayMealsOfCaseMeal = function(myCaseMeal){
        $log.debug(myCaseMeal.recipes[0].name);
        $scope.recipesToDisplay = myCaseMeal.recipes;
        $scope.nbPersOfMeal = myCaseMeal.nbPers;
        createIngredientsAdapt($scope.recipesToDisplay, $scope.nbPersOfMeal);
    }

    // called from $scope.displayMealsOfCaseMeal()
    var createIngredientsAdapt = function(recipes, newNbPers){
        for(var i=0; i<recipes.length; i++){
            recipes[i].ingredientsAdapt = [];
            for(var j=0; j<recipes[i].ingredients.length; j++){
                $log.debug("newNbPers : "+newNbPers);
                var qty = recipes[i].ingredients[j].qty * newNbPers / recipes[i].nbPerson;
                var unit = recipes[i].ingredients[j].unit;
                var food = recipes[i].ingredients[j].food;

                var newIngr = {qty: qty, unit: unit, food: food};
                recipes[i].ingredientsAdapt.push(newIngr);
                //recipes[i].ingredientsAdapt.push(recipes[i].ingredients[j]);
                //recipes[i].ingredientsAdapt[j].qty = 18;// newNbPers * recipes[i].ingredients[j].qty / recipesNbPers;
            }
        }

    }



    /* copy of recipe-controller.js -> for open/close desc.*/
    $scope.toggleDescOpen = function(recipe){
        recipe.descriptionOpen = !recipe.descriptionOpen;
    }


    $scope.ingredientDone = function(category, ingr){
        var index = category.ingredients.indexOf(ingr); //fonctionne aussi tres bien
        category.ingredients[index].done = true;
    }
    $scope.ingredientCancelDone = function(category, ingr){
        var index = category.ingredients.indexOf(ingr); //fonctionne aussi tres bien
        category.ingredients[index].done = false;
    }
    $scope.reinitList = function(){
        var categories = $scope.currentPlanningShopping.shoppingCategories;
        for(var i=0; i<categories.length; i++){
            for(var j=0; j<categories[i].ingredients.length; j++){
                categories[i].ingredients[j].done = false;
            }
        }
    }

    $scope.isCategoryDone = function(category){
        var isDone = true;
        for(var i=0; i<category.ingredients.length; i++){
            if(!category.ingredients[i].done){
                isDone = false;
            }
        }
        return isDone;
    }
    $scope.isSelected = function(listId){ //NE MARCHE PAS....
        //alert("bonjou selected");
        if(listId == $scope.myViewListId){
            //alert("indeed selected");
            return "true";
        }
        else{
            //alert("not selected");
            return "false";
        }
    }


    /*********************************** MODIF NAME list ***********************/
    $scope.modifyingListName = false;
    $scope.modifyListName = function(){
        $scope.modifyingListName = true;
    }
    $scope.modificationListNameDONE = function(){
        var planningShopping = $scope.currentPlanningShopping;
        $scope.modifyingListName = false;
        $log.debug("NEW NAME LIST SHOPPING ::::::: "+ planningShopping.name+ "(id planning :"+planningShopping.id+")");
        restPlanningService.postNewNamePlanning(planningShopping.id, planningShopping.name);
    }
    $scope.pressEnterListName = function(event){
        event.stopPropagation();
        event.preventDefault();
        if(event.keyCode == 13){
            $scope.modificationListNameDONE();
        }
    }
    /******************************* end MODIF NAME list ***********************/





    $scope.isDisplayErrandList = true;
    $scope.displayErrandList = function(){
        $scope.isDisplayErrandList = true;
    }
    $scope.displayErrandPlanning = function(){
        $scope.isDisplayErrandList = false;
    }


    // SHOW WEEK MEAL BDD
    $scope.putWeekMeal = function(weekMeal){
        restPlanningService.putShowWeekMeal(weekMeal.id, weekMeal.show);
    }
});