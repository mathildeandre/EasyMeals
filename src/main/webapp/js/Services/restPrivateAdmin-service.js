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
    function putAdminValidateFood(idFood, idFoodCategory){
        postObjToServer('POST', '/rest/putAdminValidateFood/'+idFood+'/'+idFoodCategory)
    }
    /* VALIDE FOOD with newName : on valide food en lui affectant une category et avec un nouveau nom  */
    function putAdminValidateFoodWithNewName(newNameFood, idFood, idFoodCategory){
        postObjToServer('POST', '/rest/putAdminValidateFoodWithNewName/'+newNameFood+'/'+idFood+'/'+idFoodCategory)
    }
    /* REMPLACE FOOD : par une deja existante qui etait la mm chose (on remplacera ds la relation ingredient et supprimera celle creee par le user */
    function putAdminReplaceFood(idExistingFood, idUselessFood){
        postObjToServer('POST', '/rest/putAdminReplaceFood/'+idExistingFood+'/'+idUselessFood)
    }
    /* FOOD REFUSEE : on accepte pas la food, ca la supprimera ainsi que la relation ingredient */
    function deleteFood(idFood){
        postObjToServer('POST', '/rest/deleteFood/'+idFood)
    }


    /********************** CATEGORIES ************************/
    function getBDDCategoriesNotValidated(){
        return getObjFromServer('rest/categoriesNotValidated');
    }
    /* VALIDE CATEGORY : On valide la new cat*/
    function putAdminValidateCategory(idCategory){
        postObjToServer('POST', '/rest/putAdminValidateCategory/'+idCategory)
    }
    /* VALIDE CATEGORY with newName : on valide cat  et avec un nouveau nom  */
    function putAdminValidateCategoryWithNewName(newNameFood, idCategory){
        postObjToServer('POST', '/rest/putAdminValidateCategoryWithNewName/'+newNameFood+'/'+idCategory)
    }
    /* REMPLACE CATEGORY : par une deja existante qui etait la mm chose (on remplacera ds la relation avec recipe...et supprimera celle creee par le user */
    function putAdminReplaceCategory(idExistingCategory, idUselessCategory){
        postObjToServer('POST', '/rest/putAdminReplaceCategory/'+idExistingCategory+'/'+idUselessCategory)
    }
    /* CATEGORY REFUSEE : on accepte pas la cat, ca la supprimera ainsi que la relation avec recipe */
    function deleteCategory(idCategory){
        postObjToServer('POST', '/rest/deleteCategory/'+idCategory)
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
        deleteFood: deleteFood,

        getBDDCategoriesNotValidated: getBDDCategoriesNotValidated,
        putAdminValidateCategory: putAdminValidateCategory,
        putAdminValidateCategoryWithNewName: putAdminValidateCategoryWithNewName,
        putAdminReplaceCategory: putAdminReplaceCategory,
        deleteCategory: deleteCategory

    };
});
