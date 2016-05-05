/**
 * Created by fabien on 02/03/2016.
 */
var myModule = angular.module('controllers');

myModule.controller('RecipeCreationCtrl', function($scope, $log, $location, $routeParams, RecipeService, AppendixFunctionsService, restFoodService, units, steps) {


    /*{
        name:"Burritos",
     xx pixName:"burritos.jpg",
        recipeType:"course",
        nbPerson:9,
        ingredients:[{qty:200,unit:"g",food:{id:1,name:"cabillaud",idCategory:5, validated:true}},{..}],
        descriptions:[{name:"faire cuire steack",noDescip:1},{..}],
        origin:{id:1,name:"viande",numRank:5},
        categories:[{id:1,name:"viande",numRank:1},{..}],
        timeCooking:120,
        timePreparation:170,

        /************ NO important *****
         id:2,
         isFavorite:false,
         isForPlanning:false,
         rating:0,
         nbVoter:0,
         ratingUser:3
         isHide: false;
         isValidated: true;
    }*/

    var recipeType = $routeParams.recipeType;
    $scope.recipeType =  recipeType;
    $scope.foods = restFoodService.getFoods();




    /******************************************************
     * *************** FILTER WITH FOODS ******************
     * ****************************************************/

    $scope.filterFood = '';
    $scope.currentIngr = {};
    $scope.showFoods = false;

    /* lorsque lon commence a ecrire ds input foodName */
    $scope.keyUpdateFilter = function(ingr){
        $scope.showFoods = true;
        $scope.filterFood = ingr.food.name;
        $scope.currentIngr = ingr;
    }
    /* lorsque l'on quitte le input foodName */
    $scope.onBlurInputIngredient = function(){
        $scope.showFoods = false;
        //check if food still valid!
        for(var i=0; i<$scope.foods.length; i++){
            if ($scope.currentIngr.food.id == $scope.foods[i].id){
                if($scope.currentIngr.food.name != $scope.foods[i].name){
                    var name = $scope.currentIngr.food.name;
                    $scope.currentIngr.food = {"id":0,"name":name,"idCategory":0,"isValidated":false};
                }
            }
        }
    }
    /* lorsque click on foodName proposition */
    $scope.fillUpIngrWithFood = function(index, evt, food){
        if(evt.which === 1) {
            $log.warn("BOOM fillUpIngrWithFood ---- "+food.id+food.name+food.idCategory);
            var newFood = JSON.parse(JSON.stringify(food));//NEW OBJECT
            $log.info("fillUpIngrWithFood ----NEW FOOOOD "+newFood.id+newFood.name+newFood.idCategory);
            $scope.currentIngr.food = newFood;
        }
    }

    /******************************************************
     * ************* end FILTER WITH FOODS ****************
     * ****************************************************/







    $scope.units = units;

    $scope.recipe =  {
        id:'MyRecipe',
        name:'',
        nbPerson:2,
        ingredients:[{qty:1, unit:'g', food:{"id":0,"name":"","idCategory":0,"isValidated":false}}],
        description:''
    };

    $scope.displayRecipeType = AppendixFunctionsService.displayRecipeType($scope.recipeType);

        $scope.addRowIngredient = function(){
            var ingredient = {qty:1, unit:'g', food:{"id":0,"name":"","idCategory":0,"isValidated":false}};//{qty:20, unit:units[2], food:'steack'};
            $scope.recipe.ingredients.push(ingredient);
        }
        $scope.removeRowIngredient = function(index,ingredient){
            //var index = $scope.recipe.ingredients.indexOf(ingredient); //fonctionne aussi tres bien
            $scope.recipe.ingredients.splice(index, 1); //le 1 indique combien d'element on remove a partir de index
        }


        $scope.addRecipe = function(recipe){
            alert("blabla");
            recipe.recipeType = recipeType;
            RecipeService.createRecipe(recipe);
            switch(recipeType){
                case 'starter' : RecipeService.addStarter(recipe); break;
                case 'course' :  RecipeService.addCourse(recipe); break;
                case 'dessert' : RecipeService.addDessert(recipe); break;
                case 'breakfast' : RecipeService.addBreakfast(recipe); break;
                case 'cocktail' : RecipeService.addCocktail(recipe); break;

            }
            $location.path("/recipe/"+recipeType);$location.hash(recipe.id);
        }

        $scope.step = 25;
        $scope.changeValSelect = function(ingredient){
            var unit = ingredient.unit;
            for(var i=0; i<units.length; i++){
                if(unit == units[i]){
                    //ingredient.qty = 0;
                    $scope.step = steps[i];
                }
            }
            return 0;
        }



    $scope.listFood = [
        'tomate',
        'salade',
        'steack',
        'salami',
        'saucisson',
        'saucisse',
        'courgette',
        'courge',
        'poireaux',
        'pomme de terre',
        'patate',
        'pate',
        'pate feuillete',
        'pate à lasagne'
    ]
});
