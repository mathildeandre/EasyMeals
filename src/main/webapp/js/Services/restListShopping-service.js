/**
 * Created by fabien on 18/04/2016.
 */

var myService = angular.module('services');

myService.service("restListShoppingService", function ($http, $q, $log) {



    var idListShop = 14;

    var listsShoppingPlanning = [];

    function getListsShoppingPlanning(){
        $log.info("[getListsShoppingPlanning]");
        return listsShoppingPlanning;
    }
    function addListShoppingPlanning(listSP){
        listSP.id = idListShop++;
        idListShop = idListShop+1;
        listsShoppingPlanning.push(listSP);
    }

    function initLoadData(){
        $log.warn("ON LOAD TOUTES LES DONN2ES")

        /* LIST SHOPPING PLANNING _ user */
        if(listsShoppingPlanning == undefined || listsShoppingPlanning.length == 0){
            getObjFromServer('/rest/listShoppingPlanning/2').then(function(data){ //2 = idUser
                listsShoppingPlanning = data;
                $log.warn("listShoppingPlanning loaded!")
            })
        }

    }

    function getObjFromServer(url) {
        return $http({
            method: 'GET',
            url: url
        })
            .then(function (response) {
                if (response.status == 200) {
                    return response.data;
                }
                return $q.reject(response); //si HTTP pas de gestion d'erreur dans la version HTTP d'angular 1.3
            })
    };

    return {
        getListsShoppingPlanning: getListsShoppingPlanning,
        addListShoppingPlanning: addListShoppingPlanning,
        initLoadData : initLoadData
    };

});
