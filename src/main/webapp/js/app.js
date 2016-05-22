/**
 * Created by fabien on 02/03/2016.
 */


angular.module('myApp', ['controllers','services', 'ngRoute', 'ngFileUpload'] )
    .config(['$routeProvider',
        function($routeProvider) {
            $routeProvider
                .when('/welcomeMeal', {templateUrl: '../partials/others/welcomeMeal.html', controller: 'WelcomeMealCtrl'})
                //.when('/oldRecipe/:recipeType', {templateUrl: '../partials/recipe/oldRecipe.html', controller:'RecipeCtrl'})
                .when('/recipe/:recipeType/:selection?', {templateUrl: '../partials/recipe/recipe.html', controller:'RecipeCtrl'})
                .when('/singleRecipe/:recipeType/:id', {templateUrl: '../partials/recipe/singleRecipe.html', controller:'SingleRecipeCtrl'})
                .when('/recipeCreation/:recipeType', {templateUrl: '../partials/recipe/recipeCreation.html', controller:'RecipeCreationCtrl'})



                .when('/planning', {
                    templateUrl: '../partials/planning/planning.html',
                    controller: 'PlanningCtrl',
                   /* resolve:{ recipesResolve: function getObjFromServer($http, $route) {
                                            return $http({
                                                method: 'GET',
                                                url: 'http://localhost:8080/rest/recipes/course/2'
                                            })
                                                .then(function (response) {
                                                    if (response.status == 200) {
                                                        return response.data;
                                                    }
                                                    return $q.reject(response); //si HTTP pas de gestion d'erreur dans la version HTTP d'angular 1.3
                                                })
                                        }
                    }*/
                })
                .when('/errand', {templateUrl: '../partials/others/errand.html', controller: 'ErrandCtrl'})

                .when('/calculationExpense', {templateUrl: '../partials/others/calculationExpense.html', controller:'CalculationExpenseCtrl'})
                .when('/calculationAlcohol', {templateUrl: '../partials/others/calculationAlcohol.html', controller:'CalculationAlcoholCtrl'})
                .when('/grilleImpot', {templateUrl: '../partials/others/grilleImpot.html', controller:'GrilleImpotCtrl'})
                .when('/testDirective', {templateUrl: '../partials/others/testDirective.html', controller:'TestDirectiveCtrl'})
                .otherwise({ redirectTo: '/welcomeMeal' });
        }]);