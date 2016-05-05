/**
 * Created by fabien on 13/03/2016.
 */
var myModule = angular.module('controllers');



myModule.controller('RecipeCtrl', function($scope, $routeParams, $location, $window,  $log, RecipeService, AppendixFunctionsService, restRecipeService, restFactory, $http, $q) {

    $scope.$emit('intoRecipe'); //will tell to parents (global-controller.js) to modify pix

    var recipeType = $routeParams.recipeType;
    $scope.recipeType = recipeType;
    $scope.recipes = restRecipeService.getRecipes(recipeType);

    var recipeSelection = $routeParams.selection; /*depuis navBar clique ds recette sur le coeur/pinTab...*/
    $scope.recipeSelection = recipeSelection;


    $scope.displayRecipeType = AppendixFunctionsService.displayRecipeType($scope.recipeType);
    $scope.displayButtonCreationRecipeType = AppendixFunctionsService.displayButtonCreationRecipeType($scope.recipeType);


    $scope.toggleDescOpen = function(recipe){
        recipe.descriptionOpen = !recipe.descriptionOpen;
    }

    /* important to have both fct */
    $scope.showInBlock = false;
    $scope.showInListFct = function(){
        $scope.showInBlock = false;
    }
    $scope.showInBlockFct = function(){
        $scope.showInBlock = true;
    }


    /* ICI !!!! doit communiquer avec filter controller (broadcast) */
    $scope.toggleIsFavorite = function(recipe, event){
        event.stopPropagation();
        recipe.isFavorite = !recipe.isFavorite;
        $scope.$broadcast('updateFilter');
    }
    $scope.toggleIsForPlanning = function(recipe, event){
        event.stopPropagation();
        recipe.isForPlanning = !recipe.isForPlanning;
        $scope.$broadcast('updateFilter');
    }
    $scope.openRecipeNewWindow = function(id) {
        event.stopPropagation();
        $window.open('http://localhost:8080/#/singleRecipe/'+$scope.recipeType+'/'+id);
    };
    /*$scope.reloadRoute = function() {
        alert("bjr");
        $route.reload();
        $location.search( 'viande', null );
    }*/


});