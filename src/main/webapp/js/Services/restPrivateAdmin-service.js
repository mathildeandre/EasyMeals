/**
 * Created by fabien on 18/04/2016.
 */

var myService = angular.module('services');

myService.service("restPrivateAdminService", function ($http, $q, $log, restGlobalService) {




    /************** RECIPES ******************/
    function getBDDRecipesPublicNotValidated(){
        return getObjFromServer('rest/recipesPublicNotValidated');
    }
    function putAdminValidateRecipe(idRecipe, isPublic){
        postObjToServer('POST', '/rest/putAdminValidateRecipe/'+idRecipe+"/"+isPublic)
    }


    /********************** FOODS ************************/
    function getBDDFoodsNotValidated(){
        return getObjFromServer('rest/foodsNotValidated');
    }
    /* VALIDE FOOD : On valide la new food en lui affectant une category*/
    function putAdminValidateFood(idFood, idCategory){
        postObjToServer('POST', '/rest/putAdminValidateFood/'+idFood+'/'+idCategory)
    }
    /* VALIDE FOOD with newName : on valide food en lui affectant une category et avec un nouveau nom  */
    function putAdminValidateFoodWithNewName(newNameFood, idFood, idCategory){
        postObjToServer('POST', '/rest/putAdminValidateFoodWithNewName/'+newNameFood+'/'+idFood+'/'+idCategory)
    }
    /* REMPLACE FOOD : par une deja existante qui etait la mm chose (on remplacera ds la relation ingredient et supprimera celle creee par le user */
    function putAdminReplaceFood(idExistingFood, idUselessFood){
        postObjToServer('POST', '/rest/putAdminReplaceFood/'+idExistingFood+'/'+idUselessFood)
    }
    /* FOOD REFUSEE : on accepte pas la food, ca la supprimera ainsi que la relation ingredient */
    function deleteFood(idFood){
        postObjToServer('POST', '/rest/deleteFood/'+idFood)
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

    function postObjToServer(method, url, data) {
        return $http({
            method: method,
            url: url,
            data: data
        })
            .then(function (response) {
                if (response.status == 200) {
                    return response.data;
                }
                return $q.reject(response); //si HTTP pas de gestion d'erreur dans la version HTTP d'angular 1.3
            })
    };
    return {
        getBDDRecipesPublicNotValidated: getBDDRecipesPublicNotValidated,
        putAdminValidateRecipe: putAdminValidateRecipe,

        getBDDFoodsNotValidated: getBDDFoodsNotValidated,
        putAdminValidateFood :putAdminValidateFood,
        putAdminValidateFoodWithNewName: putAdminValidateFoodWithNewName,
        putAdminReplaceFood: putAdminReplaceFood,
        deleteFood: deleteFood
    };
});
