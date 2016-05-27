
var myModule = angular.module('controllers');
myModule.controller('ModalRecipeFillPlanningCtrl', ['$scope', '$uibModal', '$log', 'restPlanningService', function ($scope, $uibModal, $log, restPlanningService) {


    $scope.openModalRecipeFillPlanning = function (recipe, event) {
        event.stopPropagation();



        var modalInstance = $uibModal.open({
            animation: true,
            templateUrl: '../../partials/recipe/modals/modalRecipeFillPlanning.html',
            controller: 'ModalInstanceRecipeFillPlanningCtrl',
            size: 'lg',
            resolve: {
                recipe: function () {
                    return recipe;
                }
            }
        });

        modalInstance.result.then(function (selectedRecipe) {
            $log.debug("selecteditem : "+selectedRecipe.name+" into caseMeal id : "+caseMeal.id);
            $scope.addRecipeIntoCaseMeal(selectedRecipe, caseMeal);
        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };

}]);

// Please note that $uibModalInstance represents a modal window (instance) dependency.
// It is not the same as the $uibModal service used above.

myModule.controller('ModalInstanceRecipeFillPlanningCtrl', function ($scope, $log, $uibModalInstance, recipe, restPlanningService, AppendixFunctionsService) {


    $scope.recipe = recipe;
    $scope.plannings = restPlanningService.getPlannings();

    $scope.currentPlanning = $scope.plannings.filter(function(obj){
        return obj.lastOpen == true;
    })[0];


    $scope.displayMealType = function(mealType){
        return AppendixFunctionsService.displayMealType(mealType);
    }




    $scope.chooseRecipe = function(recipe){
        $uibModalInstance.close(recipe);
    }
    /*
    $scope.ok = function () {
        $uibModalInstance.close($scope.selected);
    };
    */

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };


    //copy of recipe-controller.js
    $scope.displayRatingOfRecipeByTitle = function(recipe){
        $log.info("hehe displayRatingOfRecipeByTitle")
        var ratingUser = recipe.ratingUser;
        if(ratingUser == 0){ratingUser = "-"}
        return ("Note : "+recipe.rating+"\nMa note : "+ratingUser);
    }

    //copy of recipe-controller.js
    $scope.isStarFull = function(numStar, rating){
        $log.info("hehe isStarFull")
        //$log.info("---------------numstar : "+numStar+" rating : "+Math.round(rating)+"------------- RESULT ::: ");
        return numStar <= Math.round(rating);
    }

});
