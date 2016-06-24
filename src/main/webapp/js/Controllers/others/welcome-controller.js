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


    if($localStorage.userConnected){
        $log.debug("[WelcomeMealCtrl] user connected !! pseudo : "+$scope.userConnected.pseudo)
        $log.debug("[WelcomeMealCtrl] user connected !! id : "+$scope.userConnected.id)
        $log.debug("[WelcomeMealCtrl] user connected !! isAdmin : "+$scope.userConnected.isAdmin)
        $log.debug("[WelcomeMealCtrl] emit to global-CTRL")
        $scope.$emit('userConnected', $scope.userConnected);
    }else{
        $log.debug("[WelcomeMealCtrl] user NOT connected !! ")
    }

    /*************end EMIT TO GLOBAL-CTRL : UNIQUEMENT POUR AFFICHER pseudo dans NAVBAR ********************************/






});
