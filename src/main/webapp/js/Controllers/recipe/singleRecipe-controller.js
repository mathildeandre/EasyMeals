/**
 * Created by fabien on 07/04/2016.
 */
var myModule = angular.module('controllers');


myModule.controller('SingleRecipeCtrl', function($scope, $routeParams, $location, $window,  $log, restRecipeService) {

    var recipeType = $routeParams.recipeType;
    var recipeId = $routeParams.id;
    $scope.recipeType = recipeType;
    $scope.recipeId = recipeId;


    $scope.showRecipe = false;

    restRecipeService.getBDDSingleRecipe(recipeId).then(function(data){
        $scope.recipe = data;
        //$log.debug("recipe.name : "+$scope.recipe.name);
        if($scope.recipe.name != undefined){
            $scope.showRecipe = true;
        }else{
            $log.debug("[singelRecipe] -- name UNDEFINED ")
        }
    })

});