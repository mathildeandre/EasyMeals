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
        $scope.$broadcast('updateFilter'); //pour filterRecipePlanning-controller.js
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
     //Planning = {id:.., name: myVeganPlanning, lastOpen: true, nbPersGlobal:4, weekMeals: [aWeekMealLunch, aWeekMealDinner, .., ..]}
     //WeekMeal = {id:.., weekMealName: lunch, show:true, caseMeals:[caseMeal1, caseMeal2, ..., caseMeal7]}
     //caseMeal = {id: lunch4, nbPers:5 , numDay:4,  recipes:[recipe1, recipe2, ...]}
     //recipe =  {id:'1',name:'burger',recipeType:'course',nbPerson:4,ingredients:[{qty:400, unit:'g', food:'steak'},{qty:4, unit:'', food:'bread'}],description:'faire des burgers'}

     OLD ...
     //fourWeekMeals = [aWeekMealLunch, aWeekMealDinner, .., ..]
     //aWeekMeal = {typeMeal: lunch, show:true, weekMeals:[meal1, meal2, ..., meal7]}
     //meal = {id: lunch4, nbPers:5 , recipes:[recipe1, recipe2, ...]} //ex lunch of thursday
     //recipe =  {id:'1',name:'burger',recipeType:'course',nbPerson:4,ingredients:[{qty:400, unit:'g', food:'steak'},{qty:4, unit:'', food:'bread'}],description:'faire des burgers'}
     */


    $scope.plannings = restPlanningService.getPlannings();

    $scope.currentPlanning = $scope.plannings.filter(function(obj){
        return obj.lastOpen == true;
    })[0];

    $scope.changementCurrentPlanning = function(){
        //on vide listShop générée
        $scope.$broadcast('emptyListShop');
    }



    $scope.modifyingPlanningName = false;
    $scope.modifyPlanningName = function(){
        $scope.modifyingPlanningName = true;
    }
    $scope.modificationPlanningNameDONE = function(){
        var planning = $scope.currentPlanning;
        $scope.modifyingPlanningName = false;
        $log.debug("NEW NAME PLANNING ::::::: "+ planning.name+ "(id planning :"+planning.id+")");
        restPlanningService.postNewNamePlanning(planning.id, planning.name);
    }

    $scope.pressEnterListName = function(event){
        event.stopPropagation();
        event.preventDefault();
        if(event.keyCode == 13){
            $scope.modificationPlanningNameDONE();
        }
    }


    // called on $watch '$scope.currentPlanning'
    var updatePlanningBDD = function(newValue, oldValue, scope){
        $scope.$broadcast('calculListShopping');
        var newCaseMeal, oldCaseMeal;
        if(newValue.id != oldValue.id){
            //ATTENTION oldValue est une copy du dernier planning courant et ne reference plus notre planning....
            restPlanningService.makePlanningCurrent(newValue.id); //lastOpen...

        }else if(newValue.name != oldValue.name){
            //PUT : updateNamePlanning
            // -> on le fait dans 'modificationPlanningNameDONE()' car ici update a chaque nouvelle lettre ajoutee...
        }else{
            if(newValue.nbPersGlobal != oldValue.nbPersGlobal){
                //PUT : updateNbPersGlobalPlanning
                restPlanningService.putNbPersGlobalPlanning(newValue.id, newValue.nbPersGlobal)
            }
            for(var i=0; i<newValue.weekMeals.length; i++){
                if(newValue.weekMeals[i].show != oldValue.weekMeals[i].show){
                    //PUT : update show of weekmeal
                    restPlanningService.putShowWeekMeal(newValue.weekMeals[i].id, newValue.weekMeals[i].show);
                }else{
                    for(var j=0; j<newValue.weekMeals[i].caseMeals.length; j++){
                        newCaseMeal = newValue.weekMeals[i].caseMeals[j];
                        oldCaseMeal = oldValue.weekMeals[i].caseMeals[j];
                        if(newCaseMeal.nbPers != oldCaseMeal.nbPers){
                            //PUT nbPers
                            restPlanningService.putNbPersCaseMeal(newCaseMeal.id, newCaseMeal.nbPers)
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


    $scope.deletePlanning = function(){

        var idPlanningToDelete = $scope.currentPlanning.id;
        var index = $scope.plannings.indexOf($scope.currentPlanning);
        $scope.plannings.splice(index, 1);
        $scope.currentPlanning = $scope.plannings[0];

        restPlanningService.makePlanningCurrent($scope.currentPlanning.id); //lastOpen...
        restPlanningService.deletePlanningById(idPlanningToDelete);
    }

    $scope.createNewPlanning = function(){
        restPlanningService.createNewPlanning().then(function(data){
            var newPlanning = data;
            $scope.plannings.push(newPlanning);
            var index = $scope.plannings.indexOf(newPlanning);
            $scope.currentPlanning = $scope.plannings[index];
            $log.warn("new planning PUSHED")
            //putLastOpenPlanning se fait tout seul dans updatePlanningBDD() !
        })
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