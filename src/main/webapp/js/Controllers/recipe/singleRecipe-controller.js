/**
 * Created by fabien on 07/04/2016.
 */
var myModule = angular.module('controllers');


myModule.controller('SingleRecipeCtrl', function($scope, $routeParams, $location, $window,  $log, restRecipeService) {

    var recipeType = $routeParams.recipeType;
    var recipeId = $routeParams.id;
    $scope.recipeType = recipeType;
    $scope.recipeId = recipeId;
    $scope.recipe = restRecipeService.getSingleRecipe(recipeType, recipeId);
    $scope.showRecipe = $scope.recipe != null;

});