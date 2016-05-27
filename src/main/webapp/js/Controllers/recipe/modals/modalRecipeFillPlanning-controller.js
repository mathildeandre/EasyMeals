
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

        modalInstance.result.then(function (caseMeal) {
            $log.debug("selecteditem : "+recipe.name+" into caseMeal id : "+caseMeal.id);
            restPlanningService.addRecipeIntoCaseMeal(recipe, caseMeal);
            /* on insert directement en base egalement puisque le $watch on current planning de "planning-controller.js" (fct updatePlanningBDD) ne fonctionne pas depuis recipe*/
            restPlanningService.postNewRecipeCaseMeal(recipe.id, caseMeal.id);
        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };

}]);

// Please note that $uibModalInstance represents a modal window (instance) dependency.
// It is not the same as the $uibModal service used above.

myModule.controller('ModalInstanceRecipeFillPlanningCtrl', function ($scope, $log, $uibModalInstance, recipe, restPlanningService, AppendixFunctionsService) {


    $scope.recipeModal = recipe;
    $scope.plannings = restPlanningService.getPlannings();

    $scope.currentPlanning = $scope.plannings.filter(function(obj){
        return obj.lastOpen == true;
    })[0];


    $scope.displayMealType = function(mealType){
        return AppendixFunctionsService.displayMealType(mealType);
    }

    $scope.chooseCaseMeal = function(caseMeal){
        $uibModalInstance.close(caseMeal);
    }

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };

});
