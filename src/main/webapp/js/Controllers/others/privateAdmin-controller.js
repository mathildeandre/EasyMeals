/**
 * Created by fabien on 12/04/2016.
 */

var myModule = angular.module('controllers');

myModule.controller('PrivateAdminCtrl', function($scope, $log, $routeParams, $location, AppendixFunctionsService, restPrivateAdminService, restFoodService) {

    $scope.$emit('intoPrivateAdmin'); //will tell to parents (global-controller.js) to modify pix

    $scope.type = "recipe"; //$routeParams.ideaType;


    $scope.changeType = function(name){
        $scope.type = name;
    }

    $scope.isTypeSelected = function(name){
        return $scope.type == name;
    }


    /************** RECIPES ******************/
    /*
    ICI on autorise une recette public ou non (si non, l'utilisateur la verra toujours mais uniquement lui en privee

    TODO : ajouter petit msg que l'amdin rempli pour justifier un refus de mise en public..
           + possibilité d'ajouter des categories
     */
    $scope.recipes = []
    restPrivateAdminService.getBDDRecipesPublicNotValidated().then(function(data){
        $scope.recipes = data;
    })
    $scope.putAdminValidateRecipe = function(idRecipe, isPublic, index){
        restPrivateAdminService.putAdminValidateRecipe(idRecipe, isPublic);
        $scope.recipes.splice(index, 1);
    }

    /**** HTML ***/
    $scope.displayTime = function(timeInMinute){
        return AppendixFunctionsService.displayTime(timeInMinute);
    }
    /**** end HTML ***/





    /********************** FOOD ************************/
    /*
    ICI on supprime tout simplement un ingredient (bdd table: 'food')qui serait inapproprié (cela supprimera les éventuelle relation (table 'ingredient').
     */
    $scope.foodsNotValidated = [];
    restPrivateAdminService.getBDDFoodsNotValidated( ).then(function(data){
        $scope.foodsNotValidated = data;
        $log.warn("BOOM les foods sont laaaaaaa :p"+$scope.foods[0].name)
    })
    $scope.categories = restFoodService.getFoodCategories();

    /* VALIDE FOOD : On valide la new food en lui affectant une category*/
    $scope.putAdminValidateFood = function(idFood, idCategory, index){
        restPrivateAdminService.putAdminValidateFood(idFood, idCategory);
        $scope.foodsNotValidated.splice(index, 1);
        $log.info("putAdminValidateFood - idFood, idCategory : "+idFood+", "+idCategory)
    }
    /* VALIDE FOOD with newName : on valide food en lui affectant une category et avec un nouveau nom  */
    $scope.putAdminValidateFoodWithNewName = function(newNameFood, idFood, idCategory, index){
        restPrivateAdminService.putAdminValidateFoodWithNewName(newNameFood, idFood, idCategory);
        $scope.foodsNotValidated.splice(index, 1);
        $log.info("putAdminValidateFoodWithNewName - newName, idFood, idCategory : "+newNameFood+", "+idFood+", "+idCategory)
    }
    /* REMPLACE FOOD : par une deja existante qui etait la mm chose (on remplacera ds la relation ingredient et supprimera celle creee par le user */
    $scope.putAdminReplaceFood = function(idExistingFood, idUselessFood, index){
        restPrivateAdminService.putAdminReplaceFood(idExistingFood, idUselessFood);
        $scope.foodsNotValidated.splice(index, 1);
        $log.info("putAdminReplaceFood - idExistingFood, idUselessFood : "+idExistingFood+", "+idUselessFood+", "+idCategory)
    }

    /* FOOD REFUSEE : on accepte pas la food, ca la supprimera ainsi que la relation ingredient */
    $scope.deleteFood = function(idFood, index){
        restPrivateAdminService.deleteFood(idFood);
        $scope.foodsNotValidated.splice(index, 1);
    }


    $scope.newFoodNameById = '';
    $scope.keyUpdateIdExistingFood = function(idExistingFood){
        $scope.newFoodNameById = '';
        $scope.newFoodNameById = $scope.foods.filter(function(obj){
            return obj.id == idExistingFood;
        })[0].name;
    }



    /********************** FOOD SEARCH HTML************************/
    $scope.foods = restFoodService.getFoods();

    $scope.filterFood = '';
    $scope.foodSearch = '';
    $scope.showFoods = false;

    /* lorsque on ecrit ds input foodName */
    $scope.keyUpdateFilter = function(){
        $scope.filterFood = $scope.foodSearch;
    }
    $scope.onFocusInputIngredient = function(){
        $scope.showFoods = true;
        $scope.filterFood = $scope.foodSearch; //on initialise le filtre
    }
    $scope.onBlurInputIngredient = function(){
        $scope.showFoods = false;
    }
    /********************** end FOOD SEARCH HTML************************/



});