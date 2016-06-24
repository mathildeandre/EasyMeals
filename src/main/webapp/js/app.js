/**
 * Created by fabien on 02/03/2016.
 */


angular.module('myApp', ['ngAnimate', 'ui.bootstrap','controllers','services', 'ngStorage', 'ngRoute', 'ngFileUpload'] )
    .config(['$routeProvider',
        function($routeProvider) {
            $routeProvider
                .when('/welcomeMeal', {
                    templateUrl: '../partials/others/welcome.html',
                    controller: 'WelcomeMealCtrl',
                    resolve:{ welcomeMealResolve: function ($log, restGlobalService){
                        $log.warn("::::: RESOLVE welcomeMeal route ::::: calling -restGlobalService.initGlobalLoadData()")
                        return restGlobalService.initGlobalLoadData();
                    }}
                })

                .when('/idea', {
                    templateUrl: '../partials/idea/idea.html',
                    controller:'IdeaCtrl',
                    resolve:{ ideaResolve: function ($log, restGlobalService){
                        $log.warn("::::: RESOLVE idea route ::::: calling -restGlobalService.initGlobalLoadData()")
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
                .when('/privateAdmin', {
                    templateUrl: '../partials/others/privateAdmin.html',
                    controller:'PrivateAdminCtrl',
                    resolve:{ privateAdminResolve: function ($log, restGlobalService){
                        $log.warn("::::: RESOLVE privateAdmin route ::::: calling -restGlobalService.initGlobalLoadData()")
                        return restGlobalService.initGlobalLoadData();
                    }}
                })


                .when('/connexion', {
                    templateUrl: '../partials/user/connexion.html',
                    controller: 'ConnexionCtrl'
                })
                .when('/registration', {
                    templateUrl: '../partials/user/registration.html',
                    controller: 'RegistrationCtrl'
                })


                .when('/singleRecipe/:recipeType/:id', {templateUrl: '../partials/recipe/singleRecipe.html', controller:'SingleRecipeCtrl'})
                /*old -- .when('/recipeCreation/:recipeType', {templateUrl: '../partials/recipe/modals/old_recipeCreation.html', controller:'RecipeCreationCtrl'})*/


                .when('/calculationAlcohol', {templateUrl: '../partials/others/old/calculationAlcohol.html', controller:'CalculationAlcoholCtrl'})
                .when('/grilleImpot', {templateUrl: '../partials/others/old/grilleImpot.html', controller:'GrilleImpotCtrl'})
                .when('/testDirective', {templateUrl: '../partials/others/old/testDirective.html', controller:'TestDirectiveCtrl'})
                .otherwise({ redirectTo: '/welcomeMeal' });
        }])
    .run(['$rootScope', '$http', '$location', '$localStorage', '$log', 'restUserService', function($rootScope, $http, $location, $localStorage, $log, restUserService){



        // add jwt token to auth header for all requests made by the $http service --init to 'noAutorization'
        $http.defaults.headers.common.Authorization = 'No Autorization';


        // keep user logged in after page refresh
        //$log.warn("[[APP RUN]] -- we will test $localStorage ...)
        //alert("bjr1")
        if ($localStorage.userConnected) {
            //$log.warn("[[APP RUN]] -- $localStorage know !!!!!!!!!!!! pseudo : "+$localStorage.pseudo)
            $log.info("bjr3 pseudo :"+$localStorage.userConnected.pseudo)
            $log.info("bjr3 tokeb :"+$localStorage.token)
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.token;
        }

        /* header :

            Accept:* / *
            Accept-Encoding:gzip, deflate, sdch
            Accept-Language:es-ES,es;q=0.8,ca;q=0.6,en;q=0.4,gl;q=0.2
            Access-Control-Request-Headers:accept, authorization
            Access-Control-Request-Method:GET
            Connection:keep-alive
            Host:localhost:3000
            Origin:http://localhost:8000
            Referer:http://localhost:8000/
            User-Agent:Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_4)         AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36
            Authorization : 'no Autorization' (ou 'Bearer tokenValue')
         */

    }])
    ;