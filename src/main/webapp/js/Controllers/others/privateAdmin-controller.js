/**
 * Created by fabien on 12/04/2016.
 */

var myModule = angular.module('controllers');

myModule.controller('PrivateAdminCtrl', function($scope, $log, $routeParams, $location, AppendixFunctionsService, restPrivateAdminService, restFoodService, restRecipeService) {

    $scope.$emit('intoPrivateAdmin'); //will tell to parents (global-controller.js) to modify pix

    $scope.type = "recipe"; //$routeParams.ideaType;


    $scope.changeType = function(name){
        $scope.type = name;
    }

    $scope.isTypeSelected = function(name){
        return $scope.type == name;
    }


    /*************************************************************************************************************************************/
    /*******************************************************  RECIPE  ****************************************************************/
    /*************************************************************************************************************************************/
    /*
    ICI on autorise une recette public ou non (si non, l'utilisateur la verra toujours mais uniquement lui en privee

    TODO : ajouter petit msg que l'amdin rempli pour justifier un refus de mise en public..
           + possibilité d'ajouter des categories
     */
    $scope.recipes = []
    restPrivateAdminService.getBDDRecipesPublicNotValidated().then(function(data){
        $scope.recipes = data;
    })
    $scope.putAdminValidateRecipe = function(idRecipe, isPublic, index){ //isPublic is TRUE or FALSE (pr accepter ou refuser)
        restPrivateAdminService.putAdminValidateRecipe(idRecipe, isPublic);
        $scope.recipes.splice(index, 1);
    }

    /**** HTML ***/
    $scope.displayTime = function(timeInMinute){
        return AppendixFunctionsService.displayTime(timeInMinute);
    }
    /**** end HTML ***/





    /*************************************************************************************************************************************/
    /*******************************************************  FOOD  ****************************************************************/
    /*************************************************************************************************************************************/
    /*
    ICI on supprime tout simplement un ingredient (bdd table: 'food')qui serait inapproprié (cela supprimera les éventuelle relation (table 'ingredient').
     */
    $scope.foodsNotValidated = [];
    restPrivateAdminService.getBDDFoodsNotValidated( ).then(function(data){
        $scope.foodsNotValidated = data;
        $log.warn("BOOM les foods sont laaaaaaa :p"+$scope.foods[0].name)
    })
    $scope.foodCategories = restFoodService.getFoodCategories();

    /* VALIDE FOOD : On valide la new food en lui affectant une category*/
    $scope.putAdminValidateFood = function(idFood, idFoodCategory, index){
        restPrivateAdminService.putAdminValidateFood(idFood, idFoodCategory);
        $scope.foodsNotValidated.splice(index, 1);
        $log.info("putAdminValidateFood - idFood, idFoodCategory : "+idFood+", "+idFoodCategory)
    }
    /* VALIDE FOOD with newName : on valide food en lui affectant une category et avec un nouveau nom  */
    $scope.putAdminValidateFoodWithNewName = function(newNameFood, idFood, idFoodCategory, index){
        restPrivateAdminService.putAdminValidateFoodWithNewName(newNameFood, idFood, idFoodCategory);
        $scope.foodsNotValidated.splice(index, 1);
        $log.info("putAdminValidateFoodWithNewName - newName, idFood, idFoodCategory : "+newNameFood+", "+idFood+", "+idFoodCategory)
    }
    /* REMPLACE FOOD : par une deja existante qui etait la mm chose (on remplacera ds la relation ingredient et supprimera celle creee par le user */
    $scope.putAdminReplaceFood = function(idExistingFood, idUselessFood, index){
        restPrivateAdminService.putAdminReplaceFood(idExistingFood, idUselessFood);
        $scope.foodsNotValidated.splice(index, 1);
        $log.info("putAdminReplaceFood - idExistingFood, idUselessFood : "+idExistingFood+", "+idUselessFood+", "+idFoodCategory)
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


    /*************************************************************************************************************************************/
    /*******************************************************  end FOOD  ****************************************************************/
    /*************************************************************************************************************************************/





    /*************************************************************************************************************************************/
    /*******************************************************  CATEGORY  ****************************************************************/
    /*************************************************************************************************************************************/
    /* AVEC CURRENT category en fct de starter course & dessert...
     $scope.categoriesStarter = restRecipeService.getCategories("starter");
     var categoriesCourse = restRecipeService.getCategories("course");
     $scope.categoriesDessert = restRecipeService.getCategories("dessert");

     $scope.currentTypeCategories = categoriesCourse;

     var categoriesNotValidated = [];
     $scope.currentTypeCategoriesNotValidated = []
     restPrivateAdminService.getBDDCategoriesNotValidated( ).then(function(data){
     categoriesNotValidated = data;
     $scope.currentTypeCategoriesNotValidated = categoriesNotValidated.filter(function(obj){
     return obj.idRecipeType == 2;
     });
     $log.warn("BOOM les categories sont laaaaaaa :  "+$scope.currentTypeCategoriesNotValidated[0].name)
     })
     */
    $scope.categoriesStarter = restRecipeService.getCategories("starter");
    $scope.categoriesCourse = restRecipeService.getCategories("course");
    $scope.categoriesDessert = restRecipeService.getCategories("dessert");


    $scope.categoriesNotValidated = [];
    restPrivateAdminService.getBDDCategoriesNotValidated( ).then(function(data){
        $scope.categoriesNotValidated = data;
        $log.warn("BOOM les categories sont laaaaaaa :p"+$scope.categoriesNotValidated[0].name)
    })

    /*
    - vallide category
    - vallide category with new name
    - remplace category par existante
    - delete category

     */


    /* VALIDE CATEGORY : On valide la new cat*/
    $scope.putAdminValidateCategory = function(idCategory, index){
        restPrivateAdminService.putAdminValidateCategory(idCategory);
        $scope.categoriesNotValidated.splice(index, 1);
    }
    /* VALIDE CATEGORY with newName : on valide cat  et avec un nouveau nom  */
    $scope.putAdminValidateCategoryWithNewName = function(newNameCategory, idCategory, index){
        restPrivateAdminService.putAdminValidateCategoryWithNewName(newNameCategory, idCategory);
        $scope.categoriesNotValidated.splice(index, 1);
    }
    /* REMPLACE CATEGORY : par une deja existante qui etait la mm chose (on remplacera ds la relation avec recipe...et supprimera celle creee par le user */
    $scope.putAdminReplaceCategory = function(idExistingCategory, idUselessCategory, index){
        restPrivateAdminService.putAdminReplaceCategory(idExistingCategory, idUselessCategory);
        $scope.categoriesNotValidated.splice(index, 1);
    }

    /* CATEGORY REFUSEE : on accepte pas la cat, ca la supprimera ainsi que la relation avec recipe */
    $scope.deleteCategory = function(idCategory, index){
        restPrivateAdminService.deleteCategory(idCategory);
        $scope.categoriesNotValidated.splice(index, 1);
    }


    $scope.newCategoryNameById = '';
    $scope.keyUpdateIdExistingCategory = function(idExistingCategory, idRecipeType){
        $scope.newCategoryNameById = '';
        var array = [];
        switch (idRecipeType){
            case 1 : array =  $scope.categoriesStarter; break;
            case 2 : array =  $scope.categoriesCourse; break;
            case 3 : array =  $scope.categoriesDessert; break;
        }
        $scope.newCategoryNameById = array.filter(function(obj){
            return obj.id == idExistingCategory;
        })[0].name;
    }
    $scope.displayType = function(idRecipeType){
        switch (idRecipeType){
            case 1 : return 'starter';
            case 2 : return 'course';
            case 3 : return 'dessert';
        }
    }



    /*************************************************************************************************************************************/
    /******************************************************* end CATEGORY  ****************************************************************/
    /*************************************************************************************************************************************/







    /*************************************************************************************************************************************/
    /******************************************************* SPECIALITY  ****************************************************************/
    /*************************************************************************************************************************************/



    $scope.origins = restRecipeService.getOrigins();
    /*
     - valide origin
     - valide origin with new name
     - remplace origin par existante and delete

     */
    /*************************************************************************************************************************************/
    /******************************************************* end SPECIALITY  ****************************************************************/
    /*************************************************************************************************************************************/


});