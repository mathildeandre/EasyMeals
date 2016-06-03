/**
 * Created by fabien on 19/03/2016.
 */


var myModule = angular.module('controllers');

myModule.controller('GlobalCtrl', function($scope, $log, $routeParams, $location, GlobalService, restRecipeService, restPlanningService, restFoodService) {

    /**
     * Pour avoir une variable "global" utilisable entre les differentes section/view
     * il faut passer par un service (ici global-service) autrement les variables initialisee
     * uniquement ici dans le controller vont etre remise a zero a chaque fois que l'on recherche la view...
     *
     */

    //restRecipeService.initLoadData();
    //restPlanningService.initLoadData();
    //restFoodService.initLoadData();

    $scope.classBody = "bodyEasyMeals";


    /** utilisé danns 'index.html' : <body class="{{classBody}}" ng-controller="GlobalCtrl">*/
    $scope.$on('intoIdea', function() {
        $scope.classBody = "bodyIdea";
    });
    $scope.$on('intoRecipe', function() {
        $scope.classBody = "bodyRecipe";
    });
    $scope.$on('intoPlanning', function() {
        $scope.classBody = "bodyPlanning";
    });
    $scope.$on('intoErrand', function() {
        $scope.classBody = "bodyErrand";
    });
    $scope.$on('intoExpense', function() {
        $scope.classBody = "bodyExpense";
    });
    $scope.$on('intoPrivateAdmin', function() {
        $scope.classBody = "bodyPrivateAdmin";
    });
    $scope.$on('intoAlcohol', function() {
        $scope.classBody = "bodyAlcohol";
    });

    /* ici on regarde si le path contient le mot  de la var 'viewLocationRoot'
    * FONCTIONNE AUSSI mais vu qu'il a y le mot recipe a plusieurs endroit ca peut etre en confusion..*/
    $scope.isActiveInNavBar = function (viewLocationRoot) {
        return (($location.path().indexOf(viewLocationRoot)) > -1);
    };
    /*$scope.isActiveInNavBar = function (viewLocation) {
     return '#/planning' === $location.path();
     };*/




    $scope.showDebug=GlobalService.getDebug();
    $scope.toggleDebug = function(){
        GlobalService.toggleDebug();
        $scope.showDebug=GlobalService.getDebug();
    }

    $scope.showHelp=GlobalService.getHelp();
    $scope.toggleHelp = function(){
        GlobalService.toggleHelp();
        $scope.showHelp=GlobalService.getHelp();
    }

    $scope.showImprovement=GlobalService.getImprovement();
    $scope.toggleImpr = function(){
        GlobalService.toggleImprovement();
        $scope.showImprovement=GlobalService.getImprovement();
    }
    //$scope.$watch('showImprovement', toggleImpr);
    // CE WATCH NE FONCTIONNE PAAAAS => toggleImpr est un $SCOPE et devrait etre : var toggleImpr

    $scope.myId = GlobalService.getId();
    $scope.incrementId = function(){
        GlobalService.incrementId();
        $scope.myId = GlobalService.getId();
    }




});
