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
        $log.info("INTO .LOGIN. : username;pwd : "+$scope.username+";"+$scope.password);

        $scope.loading = true;
        restUserService.login($scope.username, $scope.password, function (result) {
            if (result === true) {
                $location.path('/recipe');
            } else {
                $scope.error = 'Username or password is incorrect';
                $scope.loading = false;
            }
        });
    };


});
