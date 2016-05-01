/**
 * Created by fabien on 12/04/2016.
 */

var myModule = angular.module('controllers');

myModule.controller('ErrandCtrl', function($scope, $log, ErrandService, restGETFactory) {



    $scope.listsShoppingPlanning = restGETFactory.getListsShoppingPlanning();
    $scope.currentListShoppingPlanning = $scope.listsShoppingPlanning[1];

    $scope.listName = $scope.currentListShoppingPlanning.name;

    $scope.changeList = function(idListSelected){
        $log.debug("changeLIst : id "+idListSelected.id)
        for(var i=0; i<$scope.listsShoppingPlanning.length; i++){
            if(idListSelected.id == $scope.listsShoppingPlanning[i].id){
                $scope.currentListShoppingPlanning = $scope.listsShoppingPlanning[i];
                $scope.listName = $scope.currentListShoppingPlanning.name;
            }
        }
    }






    $scope.$emit('intoErrand'); //will tell to parents (global-controller.js) to modify pix


    $scope.myLists = ErrandService.getLists();
    $scope.categories = ErrandService.getIngrCategories();
    $scope.fourWeekMeals = ErrandService.getPlanning();



    /* copy of planning-controller.js -> for th row */
    $scope.displayMealType = function(mealType){
        switch(mealType){
            case 'breakfast' : return 'Petit déjeuner';
            case 'lunch' :  return 'Repas du midi';
            case 'snack' : return 'Goûter';
            case 'dinner' : return 'Dîner';
        }
    }

    $scope.recipesToDisplay = [];
    $scope.nbPersOfMeal = 1;
    $scope.displayMeal = function(myMeal){
        $log.debug(myMeal.recipes[0].name);
        $scope.recipesToDisplay = myMeal.recipes;
        $scope.nbPersOfMeal = myMeal.nbPers;
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