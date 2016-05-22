/**
 * Created by fabien on 02/03/2016.
 */


angular.module('myApp', ['controllers','services', 'ngRoute', 'ngFileUpload'] )
    .config(['$routeProvider',
        function($routeProvider) {
            $routeProvider
                .when('/welcomeMeal', {
                    templateUrl: '../partials/others/welcomeMeal.html',
                    controller: 'WelcomeMealCtrl',
                    resolve:{ welcomeMealResolve: function ($log, restGlobalService){
                        $log.warn("::::: RESOLVE welcomeMeal route ::::: calling -restGlobalService.initGlobalLoadData()")
                        return restGlobalService.initGlobalLoadData();
                    }}
                })

                .when('/recipe/:recipeType/:selection?', {
                    templateUrl: '../partials/recipe/recipe.html',
                    controller:'RecipeCtrl',
                    resolve:{ recipesResolve: function ($log, restGlobalService){
                        $log.warn("::::: RESOLVE recipe route ::::: calling -restGlobalService.initGlobalLoadData()")
                        return restGlobalService.initGlobalLoadData();
                    }}
                })

                .when('/planning', {
                    templateUrl: '../partials/planning/planning.html',
                    controller: 'PlanningCtrl',
                    resolve:{ planningsResolve: function ($log, restGlobalService){
                        $log.warn("::::: RESOLVE planning route ::::: calling -restGlobalService.initGlobalLoadData()")
                        return restGlobalService.initGlobalLoadData();
                    }}
                })
                .when('/errand', {
                    templateUrl: '../partials/others/errand.html',
                    controller: 'ErrandCtrl',
                    resolve:{ planningsShoppingResolve: function ($log, restGlobalService){
                        $log.warn("::::: RESOLVE errand route ::::: calling -restGlobalService.initGlobalLoadData()")
                        return restGlobalService.initGlobalLoadData();
                    }}
                })
                .when('/calculationExpense', {
                    templateUrl: '../partials/others/calculationExpense.html',
                    controller:'CalculationExpenseCtrl',
                    resolve:{ expenseResolve: function ($log, restGlobalService){
                        $log.warn("::::: RESOLVE calculationExpense route ::::: calling -restGlobalService.initGlobalLoadData()")
                        return restGlobalService.initGlobalLoadData();
                    }}
                })



                .when('/singleRecipe/:recipeType/:id', {templateUrl: '../partials/recipe/singleRecipe.html', controller:'SingleRecipeCtrl'})
                .when('/recipeCreation/:recipeType', {templateUrl: '../partials/recipe/recipeCreation.html', controller:'RecipeCreationCtrl'})


                .when('/calculationAlcohol', {templateUrl: '../partials/others/calculationAlcohol.html', controller:'CalculationAlcoholCtrl'})
                .when('/grilleImpot', {templateUrl: '../partials/others/grilleImpot.html', controller:'GrilleImpotCtrl'})
                .when('/testDirective', {templateUrl: '../partials/others/testDirective.html', controller:'TestDirectiveCtrl'})
                .otherwise({ redirectTo: '/welcomeMeal' });
        }]);