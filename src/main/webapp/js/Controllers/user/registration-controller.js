/**
 * Created by fabien on 19/03/2016.
 */


var myModule = angular.module('controllers');

myModule.controller('RegistrationCtrl', function($scope, $log, $routeParams, $location, restUserService) {

    $scope.$emit('intoConnexion'); //will tell to parents (global-controller.js) to modify pix
    $scope.loading = false;
    $scope.error = '';


    $scope.registration = function() {
        $log.info("INTO .........................registration ......................................");
        $log.info("INTO .registration. : pseudo;pwd : "+$scope.pseudo+";"+$scope.password);

        $scope.loading = true;
        restUserService.registration($scope.pseudo, $scope.password, function (result) {
            if (result === true) {
                $location.path('/recipe');
            } else {
                $scope.error = 'pseudo or password is incorrect';
                $scope.loading = false;
            }
        });
    };

});
