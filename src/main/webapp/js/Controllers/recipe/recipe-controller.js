/**
 * Created by fabien on 13/03/2016.
 */
var myModule = angular.module('controllers');



myModule.controller('RecipeCtrl', function($scope, $routeParams, $location, $window,  $log,  $http, $q, AppendixFunctionsService, restRecipeService) {

    $scope.$emit('intoRecipe'); //will tell to parents (global-controller.js) to modify pix

    $scope.recipeType = $routeParams.recipeType;
    $scope.recipes = restRecipeService.getRecipes( $scope.recipeType);

    var recipeSelection = $routeParams.selection; /*depuis navBar clique ds recette sur le coeur/pinTab...*/
    $scope.recipeSelection = recipeSelection;

    $scope.changeRecipeType = function(recipeType){/* click on big top Buttons : starter, course, dessert...*/
        $scope.recipeType = recipeType;
        $scope.recipes = restRecipeService.getRecipes($scope.recipeType);
        $scope.$broadcast('updateFilter');
    }
    $scope.isRecipeTypeSelected = function(recipeType){
        return $scope.recipeType == recipeType;
    }

    $scope.displayRecipeType = function(){
        return AppendixFunctionsService.displayRecipeType($scope.recipeType);
    }
    $scope.displayButtonCreationRecipeType = function(){
        return AppendixFunctionsService.displayButtonCreationRecipeType($scope.recipeType);
    }

    $scope.displayTime = function(timeInMinute){
        return AppendixFunctionsService.displayTime(timeInMinute);
    }


    /**********************************************************************************************************/
    /************************************** RATING MODE ******************************************************/
    /********************************************************************************************************/

    $scope.isStarFull = function(numStar, rating){
        //$log.info("---------------numstar : "+numStar+" rating : "+Math.round(rating)+"------------- RESULT ::: ");
        return numStar <= Math.round(rating);
    }
    $scope.editRatingUser = function($index, starsEdit){
        for(var i=0; i<starsEdit.length; i++){
            starsEdit[i] = (i<=$index);
        }
    }

    $scope.validateRatingUser = function(index, recipe, eventisStarFull){
        event.stopPropagation();
        recipe.ratingUser = index+1;
        var result = (recipe.rating * recipe.nbVoter + recipe.ratingUser)/(recipe.nbVoter+1);
        recipe.rating =  Number(result.toFixed(1));
        recipe.nbVoter = recipe.nbVoter+1;
        recipe.ratingSystem.isUserEditing = false;
        restRecipeService.putRatingUser(recipe.id, 2, recipe.ratingUser);//2117
    }
    $scope.displayRatingOfRecipeByTitle = function(recipe){
        var ratingUser = recipe.ratingUser;
        if(ratingUser == 0){ratingUser = "-"}
        return ("Note : "+recipe.rating+"\nMa note : "+ratingUser);
    }

    /* no use anymore...(was into recipeItemDisplayOpen.html)*/
    $scope.toggleEditingRate = function(recipe, event){
        event.stopPropagation();
        recipe.ratingSystem.isUserEditing = !recipe.ratingSystem.isUserEditing ;
    }
    $scope.stopPropag = function(event){
        event.stopPropagation();
        $log.info("STOP PROPAG !!")
    }



    /*
    var initStars = function(){
        for(var i=0; i<$scope.recipes.length; i++){
            $scope.recipes[i].starsIsFull = [false, false, false, false, false];
        }
    }
    initStars();
    */

    $scope.isUserEditRating = true;
    $scope.ratingUserEdit = 0;
    $scope.starsEdit = [true, false, false, false, false];

    $scope.changeRatingUser = function(numStar){
        $log.debug("ratingUserEdit : "+$scope.ratingUserEdit+" numstar:"+numStar);
        $scope.ratingUserEdit = numStar;
        $log.debug("ratingUserEdit : "+$scope.ratingUserEdit+" numstar:"+numStar);
        //$scope.apply();
    }
    //$scope.overRating = false;


    $scope.hoverStar = function(numStar){
        alert(numStar);
    }

    $scope.getNumberStarsFull = function(num){
        var newNum = Math.round(num);
        return new Array(newNum);
    }
    $scope.getNumberStarsEmpty = function(num){
        var newNum = Math.round(5 - num);
        return new Array(newNum);
    }




    /**********************************************************************************************************/
    /************************************** end RATING MODE **************************************************/
    /********************************************************************************************************/



    $scope.toggleDescOpen = function(recipe){
        recipe.descriptionOpen = !recipe.descriptionOpen;
    }

    /* important to have both fct */
    $scope.showInBlock = false;
    $scope.showInListFct = function(){
        $scope.showInBlock = false;
    }
    $scope.showInBlockFct = function(){
        $scope.showInBlock = true;
    }


    /* ICI !!!! doit communiquer avec filter controller (broadcast) */
    $scope.toggleIsFavorite = function(recipe, event){
        event.stopPropagation();
        recipe.isFavorite = !recipe.isFavorite;
        $scope.$broadcast('updateFilter');
        restRecipeService.putIsFavorite(recipe.id, 2, recipe.isFavorite);//2117
    }
    $scope.toggleIsForPlanning = function(recipe, event){
        event.stopPropagation();
        recipe.isForPlanning = !recipe.isForPlanning;
        $scope.$broadcast('updateFilter');
        restRecipeService.putIsForPlanning(recipe.id, 2, recipe.isForPlanning);//2117
    }

    $scope.openRecipeNewWindow = function(id) {
        event.stopPropagation();
        $window.open('http://localhost:8080/#/singleRecipe/'+$scope.recipeType+'/'+id);
    };

    /*$scope.reloadRoute = function() {
        alert("bjr");
        $route.reload();
        $location.search( 'viande', null );
    }*/


});