/**
 * Created by fabien on 12/04/2016.
 */

var myModule = angular.module('controllers');

myModule.controller('ErrandCtrl', function($scope, $log, ErrandService, AppendixFunctionsService, restListShoppingService) {



    $scope.listsShoppingPlanning = restListShoppingService.getListsShoppingPlanning();

    $scope.currentListShoppingPlanning = $scope.listsShoppingPlanning[$scope.listsShoppingPlanning.length-1];

    //$scope.listName = $scope.currentListShoppingPlanning.name;

    $scope.changeList = function(listSelected){
        $log.debug("changeLIst : id "+listSelected.id)
        for(var i=0; i<$scope.listsShoppingPlanning.length; i++){
            if(listSelected.id == $scope.listsShoppingPlanning[i].id){
                $scope.currentListShoppingPlanning = $scope.listsShoppingPlanning[i];
                //$scope.listName = $scope.currentListShoppingPlanning.name;
            }
        }
    }






    $scope.$emit('intoErrand'); //will tell to parents (global-controller.js) to modify pix


    //$scope.myLists = ErrandService.getLists();
    //$scope.categories = ErrandService.getIngrCategories();
    //$scope.fourWeekMeals = ErrandService.getPlanning();



    /* copy of planning-controller.js -> for th row */


    /* mealType = breakfast, lunch, snack, dinner*/
    $scope.displayMealType = function(mealType){
        return AppendixFunctionsService.displayMealType(mealType);
    }

    $scope.recipesToDisplay = [];
    $scope.nbPersOfMeal = 1;
    $scope.displayMealsOfCaseMeal = function(myCaseMeal){
        $log.debug(myCaseMeal.recipes[0].name);
        $scope.recipesToDisplay = myCaseMeal.recipes;
        $scope.nbPersOfMeal = myCaseMeal.nbPers;
        createIngredientsAdapt($scope.recipesToDisplay, $scope.nbPersOfMeal);
    }

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
        category.ingredients[index].done = true;;

    }
    $scope.reinitList = function(){
        var categories = $scope.currentListShoppingPlanning.listShopping.listShoppingCategories;
        for(var i=0; i<categories.length; i++){
            for(var j=0; j<categories[i].ingredients.length; j++){
                categories[i].ingredients[j].done = false;
            }

        }
    }
    $scope.reinitListOLD = function(){
        for(var i=0; i<$scope.categories.length; i++){
            for(var j=0; j<$scope.categories[i].ingredients.length; j++){
                $scope.categories[i].ingredients[j].done = false;
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
    $scope.myViewListId = 0;
    $scope.changeViewList = function(listId){
        $scope.categories = ErrandService.changeViewList(listId);
        $scope.myViewListId = listId;
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
    $scope.modifyingListName = false;
    $scope.toggleModifyListName = function(listName){
        $scope.modifyingListName = !$scope.modifyingListName;

    }

    $scope.isDisplayErrandList = true;
    $scope.displayErrandList = function(){
        $scope.isDisplayErrandList = true;
    }
    $scope.displayErrandPlanning = function(){
        $scope.isDisplayErrandList = false;
    }
});