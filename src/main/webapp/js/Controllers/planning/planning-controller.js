/**
 * Created by fabien on 15/03/2016.
 */


var myModule = angular.module('controllers');

myModule.controller('PlanningCtrl', function($scope, $log, RecipeService, AppendixFunctionsService, restRecipeService, restPlanningService, fourTypeMeal, units, steps) {

    $scope.$emit('intoPlanning'); //will tell to parents (global-controller.js) to modify pix

    /**
     * RECIPES LIST ...
     */
    $scope.recipeType = 'course';
    $scope.recipes = restRecipeService.getRecipes($scope.recipeType);

    $scope.changeRecipeType = function(recipeType){/* click on big top Buttons : starter, course, dessert...*/
        $scope.recipeType = recipeType;
        $scope.recipes = restRecipeService.getRecipes($scope.recipeType);
        $scope.$broadcast('updateFilter');
    }
    $scope.isRecipeTypeSelected = function(recipeType){
        return $scope.recipeType == recipeType;
    }

    $scope.collapseFilterPlanning = false;

    /** lors de souris hover : title = ... */
    $scope.displayIngredientsOfRecipeByTitle = function(recipe){
        var str="\n - "+recipe.nbPerson+" pers. -";
        for(var i=0; i<recipe.ingredients.length; i++){
            str = str+"\n"+recipe.ingredients[i].qty+recipe.ingredients[i].unit+' '+recipe.ingredients[i].food.name;
        }
        return str;
    }

    $scope.displayRecipeType = AppendixFunctionsService.displayRecipeType($scope.recipeType);
    /**
     * PLANNING Construction
     */

    /* mealType = breakfast, lunch, snack, dinner*/
    $scope.displayMealType = function(mealType){
        return AppendixFunctionsService.displayMealType(mealType);
    }

    $scope.planningInitialized = false;


    $scope.globalNbPers = 4;
    $scope.spreadGlobalNbPers = function(nb){
        var fourWeekMeals = $scope.fourWeekMeals;
        for(var i=0; i<fourWeekMeals.length; i++){
            var weekMeals = fourWeekMeals[i].weekMeals;
            for(var j=0; j<weekMeals.length; j++){
                weekMeals[j].nbPers = nb; //fonctionne par reference :) (pointeurs)
            }
        }
    }
    $scope.showNbPers = false;
    $scope.nbPers = 4;

    /*
     //fourWeekMeals = [aWeekMealLunch, aWeekMealDinner, .., ..]
     //aWeekMeal = {id: , typeMeal: lunch, show:true, weekMeals:[meal1, meal2, ..., meal7]}
     //meal = {id: lunch4, nbPers:5 , recipes:[recipe1, recipe2, ...]} //ex lunch of thursday
     //recipe =  {id:'1',name:'burger',recipeType:'course',nbPerson:4,ingredients:[{qty:400, unit:'g', food:'steak'},{qty:4, unit:'', food:'bread'}],description:'faire des burgers'}
     */


    /*NEW ....
     //Planning = {name: myVeganPlanning, lastOpen: true,  weekMeals: [aWeekMealLunch, aWeekMealDinner, .., ..]}
     //WeekMeal = {weekMealName: lunch, show:true, caseMeals:[caseMeal1, caseMeal2, ..., caseMeal7]}
     //caseMeal = {id: lunch4, nbPers:5 , noDay:4,  recipes:[recipe1, recipe2, ...]}
     //recipe =  {id:'1',name:'burger',recipeType:'course',nbPerson:4,ingredients:[{qty:400, unit:'g', food:'steak'},{qty:4, unit:'', food:'bread'}],description:'faire des burgers'}

     OLD ...
     //fourWeekMeals = [aWeekMealLunch, aWeekMealDinner, .., ..]
     //aWeekMeal = {typeMeal: lunch, show:true, weekMeals:[meal1, meal2, ..., meal7]}
     //meal = {id: lunch4, nbPers:5 , recipes:[recipe1, recipe2, ...]} //ex lunch of thursday
     //recipe =  {id:'1',name:'burger',recipeType:'course',nbPerson:4,ingredients:[{qty:400, unit:'g', food:'steak'},{qty:4, unit:'', food:'bread'}],description:'faire des burgers'}
     */

    $scope.plannings = restPlanningService.getPlannings();
    $scope.currentPlanning = $scope.plannings[0];


    var initFourWeekMeals = function(nbDay){
        var fourWeekMeals = [];
        for(var i=0; i<fourTypeMeal.length; i++){
            var typeMeal = fourTypeMeal[i];
            var weekMeals = [];
            for(var j=0; j<nbDay; j++){
                var meal = {id: typeMeal+j, nbPers:$scope.globalNbPers, recipes:[]};
                weekMeals.push(meal);
            }
            var varShow=false;
            if(typeMeal == 'lunch' || typeMeal == 'dinner'){
                varShow=true;
            }
            var aWeekMeal = {id:i, typeMeal:typeMeal, show:varShow, weekMeals:weekMeals};
            fourWeekMeals.push(aWeekMeal);
        }
        return fourWeekMeals;
    }

    $scope.fourWeekMeals = initFourWeekMeals(7);

    $scope.onOverTrash = function(){
        document.getElementById("trashPlanning").style.color = 'orange';
    }
    $scope.onOutTrash = function(){
        document.getElementById("trashPlanning").style.color = '#5bc0de';
    };
    /*
    $scope.onOverTab = function(vaa){
        alert(vaa);
    }*/

    $scope.dataDropTrash = true; //pas vraiment utilisé mais ne pas supprimer cette ligne sans enlever {{dataDropTrash}} du data-drop de la trash
    $scope.trash = [];




    /*****************************    MODAL ***********************/




});

myModule.filter('orderByRecipeType', function($log){
    var sortRecipeType = function(input){
        var recipesReordered = [];
        var arrRecipeType=['starter','course','dessert'];//ordre filter
        for(var i=0; i<arrRecipeType.length; i++){
            for(var j=0; j<input.length; j++){
                if(input[j].recipeType == arrRecipeType[i]){
                    recipesReordered.push(input[j]);
                }
            }
        }
        return recipesReordered;
    };
    return sortRecipeType;
});