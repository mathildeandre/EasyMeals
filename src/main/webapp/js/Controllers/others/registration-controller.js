/**
 * Created by fabien on 19/03/2016.
 */


var myModule = angular.module('controllers');

myModule.controller('RegistrationCtrl', function($scope, $log, $routeParams, $location, GlobalService, restRecipeService, restPlanningService, restFoodService) {

    $scope.$emit('intoConnexion'); //will tell to parents (global-controller.js) to modify pix

    $scope.bjr = 'ahahahaha2';



});
