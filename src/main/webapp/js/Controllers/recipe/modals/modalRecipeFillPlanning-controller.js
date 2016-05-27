
var myModule = angular.module('controllers');
myModule.controller('ModalRecipeFillPlanningCtrl', ['$scope', '$uibModal', '$log', function ($scope, $uibModal, $log) {


    $scope.openModal = function (recipeToDisplay, caseMeal) {

        var modalInstance = $uibModal.open({
            animation: true,
            templateUrl: '../../partials/recipe/modals/modalRecipeFillPlanning.html',
            controller: 'ModalInstanceRecipeFillPlanningCtrl',
            size: 'lg',
            resolve: {
                items: function () {
                    return recipeToDisplay;
                },
                caseMeal: function () {
                    return caseMeal;
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

myModule.controller('ModalInstanceRecipeFillPlanningCtrl', function ($scope, $log, $uibModalInstance, items) {


    $scope.items = items;


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
