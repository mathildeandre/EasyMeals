/**
 * Created by fabien on 19/03/2016.
 */

var myModule = angular.module('controllers');

myModule.controller('WelcomeMealCtrl', function($scope, $localStorage, $log) {

    $log.debug("EMIT TO GLOBAL - intoWelcome")
    $scope.$emit('intoWelcome'); //will tell to parents (global-controller.js) to modify pix


    /*************EMIT TO GLOBAL-CTRL : UNIQUEMENT POUR AFFICHER pseudo dans NAVBAR ********************************/
    /** when a user just logged in -> emit to Global to change the NAVBAR**/
    //$localStorage.userConnected = { pseudo: response.pseudo, id: response.idUser, token: response.token }




    $scope.userConnected = $localStorage.userConnected



    $log.debug("user connected ?? pseudo : "+$scope.userConnected.pseudo)
    $log.debug("user connected ?? pseudo : "+$scope.userConnected.id)
    $log.debug("user connected ?? pseudo : "+$scope.userConnected.isAdmin)
    if($localStorage.userConnected){
        $log.debug("EMIT TO GLOBAL")
        $scope.$emit('userConnected', $scope.userConnected);
    }
    /*************end EMIT TO GLOBAL-CTRL : UNIQUEMENT POUR AFFICHER pseudo dans NAVBAR ********************************/
});
