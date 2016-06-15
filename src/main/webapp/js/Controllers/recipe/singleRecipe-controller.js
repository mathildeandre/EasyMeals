/**
 * Created by fabien on 07/04/2016.
 */
var myModule = angular.module('controllers');


myModule.controller('SingleRecipeCtrl', function($scope, $routeParams, $localStorage, $location, $window,  $log, restRecipeService) {

    /*********************************** USER CONNECTED **************************************/
        //$localStorage.userConnected = { pseudo: response.pseudo, id: response.idUser, token: response.token }
    $scope.isUserConnected = false;
    $scope.userConnected = {id: 0, pseudo: '', email: '', isAdmin: false, colorThemeRecipe: 'grey'};
    if($localStorage.userConnected){
        $log.debug("[[SingleRecipeCtrl]] - USER CONNECTED !! ($localStorage known)")
        $scope.isUserConnected = true;
        $scope.userConnected = $localStorage.userConnected;
    }

    $log.debug("[[SingleRecipeCtrl]] - $localStorage know ?? : "+$scope.isUserConnected)
    /*********************************** end USER CONNECTED **************************************/

    var recipeType = $routeParams.recipeType;
    var recipeId = $routeParams.id;
    $scope.recipeType = recipeType;
    $scope.recipeId = recipeId;


    $scope.showRecipe = false;

    restRecipeService.getBDDSingleRecipe(recipeId, $scope.userConnected.id).then(function(data){

        //$log.warn("FUCK IT ;) puis on recupe ici :p")
        $scope.recipe = data;
        //$log.debug("recipe.name : "+$scope.recipe.name);
        if($scope.recipe.name != undefined){
            $scope.showRecipe = true;
        }else{
            $log.debug("[singelRecipe] -- name UNDEFINED ")
        }
    })

});