/**
 * Created by fabien on 13/03/2016.
 */
var myModule = angular.module('controllers');



myModule.controller('RecipeCtrl', function($scope, $routeParams, $location, $window,  $log, RecipeService, restGETFactory, restFactory, $http, $q) {

/*
fonctionne !!
    restFactory.getTest().then(
        function(nameJson){
            $scope.word = nameJson;
        }
    )


    $scope.wordFct = function(){
        //$log.debug("PUTIIIIIIIIIIIIIIN dalee ");
        //var test = restFactory.getCourses();

        return "blablabla";
    }


    restFactory.getCourses().then(
        function(data){
            $scope.recipes = data;
            $scope.recipesToDisplay = data;
        }
    )

fin  fonctionne !!
*/

    /*
    $scope.buttonRestTxtPost = function(){

        restFactory.txtPost("-msg de angular- TEST post txt").then(
            function(data){
                $log.debug(data);
            }
        )
    }
    $scope.buttonRestObjTestPost = function(){

        restFactory.insertObjTest().then(
            function(data){
                $log.debug(data);
            }
        )
    }

*/


    /*
    */
/*
    var uneRecette = {"id":1,
        "name":"Burgers Maison",
        "pixName":"burgersMaison.jpeg",
        "recipeType":"course",
        "nbPerson":17,
        "ingredients":[{"qty":400,"unit":"g","food":"steack hachÃ©","rayonId":2},
            {"qty":5,"unit":"","food":"tomate","rayonId":4},
            {"qty":1,"unit":"","food":"salade","rayonId":4},
            {"qty":10,"unit":"","food":"pain Ã  burger","rayonId":5},
            {"qty":100,"unit":"g","food":"fromage rapÃ©","rayonId":8},
            {"qty":0,"unit":"","food":"crÃªpe Ã  burritos","rayonId":13}],
        "descriptions":["cuire steack Ã  la poÃªle",
            "preparer salade,couper tomates en rondelle et ouvrir les pains Ã  burger et mettre du fromage sur chaque partie",
            "quand les steack sont pret, mettre pains au four 2min",
            "tout est pret, mettre sauce au choix sur chaque partie du pain, steack, tomate salade",
            "Votre burger est pret !"],
        "descriptionOpen":false,
        "origin":"americain",
        "categories":[{"id":1,"name":"viande","numRank":1},{"id":2,"name":"four","noRank":5}],
        "rating":0,"nbVoter":0,
        "favorite":false,
        "forPlanning":false};
*/


/*
    var dataObj = {"name" : "ahhhhh enfin!!!"};
    var res = $http.post('/rest/course', dataObj);
    */

    /*
    restFactory.createCourse().then(
        function(data){
            $log.debug(data);
        }
    )
*/

    /* ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- */
    /* ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- */
    /* ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- */
    /* ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- */
    /* ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- */
    /* ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- */
    /* ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- */

    /*
     $scope.$watch(function(){
     return $window.width;
     }, function(value) {
     $log.debug("ALLLRRR --------------------- : "+value);
     });
     */
    $scope.$emit('intoRecipe'); //will tell to parents (global-controller.js) to modify pix

    var recipeType = $routeParams.recipeType;
    $scope.recipeType = recipeType;
    var recipeSelection = $routeParams.selection;
    $scope.recipeSelection = recipeSelection;

    var getRecipes = function(recipeType){
        switch(recipeType){
            case 'starter' : return RecipeService.getStarters();
            case 'course' :  return RecipeService.getCourses();//restGETFactory.getCourses();
            case 'dessert' : return RecipeService.getDesserts();
            case 'breakfast' : return RecipeService.getBreakfasts();
            case 'cocktail' : return RecipeService.getCocktails();
            default:  $scope.recipeType = 'ERROR';
        }
    }
    $scope.recipes = getRecipes(recipeType);


    $scope.displayRecipeType = function(){
        switch($scope.recipeType){
            case 'starter' : return 'Entrées';
            case 'course' :  return 'Plats';
            case 'dessert' : return 'Desserts';
            case 'breakfast' : return 'Déjeuners - Goûters';
            case 'cocktail' : return 'Cocktails';
        }
    }
    $scope.displayCreationRecipeType = function(){
        switch($scope.recipeType){
            case 'starter' : return 'Créer une nouvelle Entrée';
            case 'course' :  return 'Créer un nouveau Plat';
            case 'dessert' : return 'Créer un nouveau Dessert';
            case 'breakfast' : return 'Créer un nouveau Dej/Goûter';
            case 'cocktail' : return 'Créer un nouveau Cocktail';
        }
    }
   // $scope.courses = RecipeService.getCourses();


    /*
    $scope.getIngrUnitDisplay = function(ingredientUnit){
        if(ingredientUnit != ''){
            return (ingredientUnit + ' of');
        }
        else {

            return ' piece(s) of';
        }
    }
    */

    $scope.toggleDescOpen = function(recipe){
        recipe.descriptionOpen = !recipe.descriptionOpen;
    }

    $scope.showInBlock = true;

    /*
    $scope.showInList = false;
    $scope.showInListFct = function(){
        $scope.showInList = true;
        $scope.showInBlock = false;
    }
    $scope.showInBlockFct = function(){
        $scope.showInList = false;
        $scope.showInBlock = true;
    }
    */


    /* ICI !!!! doit communiquer avec filter controller (broadcast) */
    $scope.toggleIsFavorite = function(recipe, event){
        event.stopPropagation();
        recipe.isFavorite = !recipe.isFavorite;
        $scope.$broadcast('updateFilter');
    }
    $scope.toggleIsForPlanning = function(recipe, event){
        event.stopPropagation();
        recipe.isForPlanning = !recipe.isForPlanning;
        $scope.$broadcast('updateFilter');
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