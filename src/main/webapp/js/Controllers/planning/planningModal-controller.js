
var myModule = angular.module('controllers');
myModule.controller('PlanningModalCtrl', ['$scope', '$uibModal', '$log', function ($scope, $uibModal, $log) {

    $scope.items = ['item1', 'item2', 'item3'];

    $scope.animationsEnabled = true;

    $scope.open = function (recipeToDisplay ) {

        var modalInstance = $uibModal.open({
            animation: $scope.animationsEnabled,
            templateUrl: '../../partials/planning/planningModal.html',
            controller: 'ModalInstanceCtrl',
            //size: size,
            resolve: {
                items: function () {
                    return recipeToDisplay;
                    //return $scope.items;
                }
            }
        });

        modalInstance.result.then(function (selectedItem) {
            $scope.selected = selectedItem;
        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };

    $scope.toggleAnimation = function () {
        $scope.animationsEnabled = !$scope.animationsEnabled;
    };

}]);

// Please note that $uibModalInstance represents a modal window (instance) dependency.
// It is not the same as the $uibModal service used above.

myModule.controller('ModalInstanceCtrl', function ($scope, $uibModalInstance, items) {


    $scope.items = items;

    $scope.selected = {
        item: $scope.items[0]
    };


    $scope.ok = function () {
        $uibModalInstance.close();
    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});
