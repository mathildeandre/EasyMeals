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


    $scope.globalNbPers = 4;
    $scope.showNbPers = false;
    $scope.spreadGlobalNbPers = function(nb){
        for(var i=0; i<$scope.currentPlanning.weekMeals.length; i++){
            for(var j=0; j<$scope.currentPlanning.weekMeals[i].caseMeals.length; j++){
                $scope.currentPlanning.weekMeals[i].caseMeals[j].nbPers = nb;
            }
        }
    }
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

    var updatePlanningBDD = function(newValue, oldValue, scope){
        var newCaseMeal, oldCaseMeal;
        if(newValue.name != oldValue.name){
            //updateNamePlanning
        }else{
            for(var i=0; i<newValue.weekMeals.length; i++){
                if(newValue.weekMeals[i].show != oldValue.weekMeals[i].show){
                    //update show of weekmeal
                }else{
                    for(var j=0; j<newValue.weekMeals[i].caseMeals.length; j++){
                        newCaseMeal = newValue.weekMeals[i].caseMeals[j];
                        oldCaseMeal = oldValue.weekMeals[i].caseMeals[j];
                        if(newCaseMeal.nbPers != oldCaseMeal.nbPers){
                            //Update nbPers
                        }else if(newCaseMeal.recipes.length > oldCaseMeal.recipes.length){
                            //POST new recipe caseMealRecipe
                            postNewRecipeCaseMeal(newCaseMeal.id, newCaseMeal.recipes, oldCaseMeal.recipes);
                        }else if(newCaseMeal.recipes.length < oldCaseMeal.recipes.length){
                            //DELETE old recipe
                            deleteOldRecipeCaseMeal(newCaseMeal.id, newCaseMeal.recipes, oldCaseMeal.recipes);
                        }
                    }
                }
            }
        }
    }
    $scope.$watch('currentPlanning', updatePlanningBDD, true);

    var postNewRecipeCaseMeal = function(idCaseMeal, newRecipes, oldRecipes){
        var isNewRecipe;
        for(var i=0; i<newRecipes.length; i++){
            isNewRecipe = true;
            for(var j=0; j<oldRecipes.length; j++){
                if(newRecipes[i].id == oldRecipes[j].id){
                    isNewRecipe = false;
                }
            }
            if(isNewRecipe){
                $log.debug("BOOM ------- new recipe (id):  "+newRecipes[i].id + "(id :"+idCaseMeal+")");
                restPlanningService.postNewRecipeCaseMeal(newRecipes[i].id, idCaseMeal);
            }
        }
    }
    var deleteOldRecipeCaseMeal = function(idCaseMeal, newRecipes, oldRecipes){
        var isDeletedRecipe;
        for(var i=0; i<oldRecipes.length; i++){
            isDeletedRecipe = true;
            for(var j=0; j<newRecipes.length; j++){
                if(oldRecipes[i].id == newRecipes[j].id){
                    isDeletedRecipe = false;
                }
            }
            if(isDeletedRecipe){
                $log.debug("BOOM ------- deleted recipe :  "+oldRecipes[i].name + "(id :"+idCaseMeal+")");
                restPlanningService.deleteOldRecipeCaseMeal(oldRecipes[i].id, idCaseMeal);
            }
        }
    }

























/* NO USEFULL ANYMORE
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

 */


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