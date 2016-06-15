/**
 * Created by fabien on 19/03/2016.
 */


var myModule = angular.module('controllers');

myModule.controller('ConnexionCtrl', function($scope, $log, $routeParams, $location, restUserService) {

    $scope.$emit('intoConnexion'); //will tell to parents (global-controller.js) to modify pix

    $scope.loading = false;
    $scope.error = '';


    $scope.login = function() {
        $log.info("INTO .........................LOGIN ......................................");
        $log.info("INTO .LOGIN. : pseudo;pwd : "+$scope.pseudo+";"+$scope.password);

        $scope.loading = true;
        restUserService.connexion($scope.pseudo, $scope.password, function (result, msgError) {
            if (result === true) {
                //$location.path('/recipe/course');
                $location.path('/welcomeMeal');
            } else {
                $scope.error = msgError;//'pseudo or password is incorrect --  msgError : '+;
                $scope.loading = false;
            }
        });
    };


});
