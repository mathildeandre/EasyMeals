/**
 * Created by fabien on 02/03/2016.
 */
var myModule = angular.module('controllers');

myModule.controller('RecipeCreationCtrl', function($scope, $log, $http, $location, $routeParams, RecipeService, AppendixFunctionsService, restRecipeService, restFoodService, units) {

    /*RECIPE = {
        name:"Burritos",
     xx pixName:"burritos.jpg",
        recipeType:"course",
        nbPerson:9,
        ingredients:[{qty:200,unit:"g",food:{id:1,name:"cabillaud",idCategory:5, validated:true}},{..}],
        descriptions:[{name:"faire cuire steack",numDescrip:1},{..}],
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
    }
    */

    var recipeTypeName = $routeParams.recipeType;
    $scope.recipeTypeName =  recipeTypeName;
    $scope.foods = restFoodService.getFoods();
    $scope.categories = restRecipeService.getCategories();
    $scope.origins = restRecipeService.getOrigins();
    var recipeTypes = restRecipeService.getRecipeTypes();


    var getIdType = function(){
        for(var i=0; i<recipeTypes.length; i++){
            if(recipeTypes[i].nameType == recipeTypeName){
                return recipeTypes[i].idType;
            }
        }
        return 0; //erreur..
    }


    $scope.recipe =  {
        name:'',
        recipeType:{idType:getIdType(),nameType:recipeTypeName},
        user:{id:2117, pseudo:'', email:''}, /* <---------- idUser A AFFINER ----------------------- */
        nbPerson:4,
        ingredients:[{qty:1, unit:'g', food:{"id":-1,"name":"","idCategory":1,"isValidated":false}}],
        descriptions:[{name:"",numDescrip:1}],
        origin:$scope.origins[0],
        categories:[],
        timeCooking:30,
        timePreparation:20,
        isPublic: true

    };

    $scope.displayRecipeTypeName = AppendixFunctionsService.displayRecipeType($scope.recipeTypeName);


    /*keyUpdateFilter  pr food*/
    $scope.pressEnterAreaDescrip = function(event){
        event.stopPropagation();
        event.preventDefault();
        if(event.keyCode == 13){
            $scope.addDescription();
        }
    }
    $scope.pressKey = function(event){
        alert("KEY PRESSED - no : "+event.keyCode);

        event.stopPropagation();
    }

    var updateCategories = function(){
        $scope.recipe.categories = [];
        for(var i=0; i<$scope.categories.length; i++){
            if($scope.categories[i].checked){
                /// category inserted : {"id":2,"name":"four","numRank":5,"checked":true}
                /// maybe we need to remove the field "checked" ? (=> new category = {id: ..,...} )
                $scope.recipe.categories.push($scope.categories[i]);
            }
        }
    }
    $scope.$watch('categories', updateCategories, true);


    $scope.units = units;
    $scope.unitStep = function(aUnit){
        return AppendixFunctionsService.unitStep(aUnit);
    }

    $scope.addRowIngredient = function(){
        var ingredient = {qty:1, unit:'g', food:{"id":-1,"name":"","idCategory":1,"isValidated":false}};//{qty:20, unit:units[2], food:'steack'};
        $scope.recipe.ingredients.push(ingredient);
    }
    $scope.removeRowIngredient = function(index,ingredient){
        //var index = $scope.recipe.ingredients.indexOf(ingredient); //fonctionne aussi tres bien
        $scope.recipe.ingredients.splice(index, 1); //le 1 indique combien d'element on remove a partir de index
    }


    var nextNumDescrip = 2;
    $scope.addDescription = function(){
        $scope.recipe.descriptions.push({name:"",numDescrip:nextNumDescrip++});
    }
    $scope.removeDescription = function(index,ingredient){
        $scope.recipe.descriptions.splice(index, 1);
        for(var i=index; i<$scope.recipe.descriptions.length; i++){
            $scope.recipe.descriptions[i].numDescrip--;
        }
        nextNumDescrip--;

    }

    $scope.createRecipe = function(){

        //suppression du champ .checked de category (initil pour la base et povoquerait une erreur car non existant en java)
        for(var i=0; i<$scope.recipe.categories.length; i++){
            delete $scope.recipe.categories[i].checked;
        }
        //ajout en local des nouvelles foods (s'il y en a) car on ne reload pas la bdd avec les new food
        for(var i=0; i<$scope.recipe.ingredients.length; i++){
            if($scope.recipe.ingredients[i].food.id == 0){
                restFoodService.addFood($scope.recipe.ingredients[i].food);
            }
        }

        restRecipeService.createRecipe($scope.recipe, recipeTypeName);
        /*switch(recipeTypeName){
            case 'starter' : RecipeService.addStarter(recipe); break;
            case 'course' :  RecipeService.addCourse(recipe); break;
            case 'dessert' : RecipeService.addDessert(recipe); break;
            case 'breakfast' : RecipeService.addBreakfast(recipe); break;
            case 'cocktail' : RecipeService.addCocktail(recipe); break;

        }*/
        $location.path("/recipe/"+recipeTypeName);//$location.hash(recipe.id);
    }



    /******************************************************
     * *************** FILTER WITH FOODS for ingredients ******************
     * ****************************************************/
    /*
    PETIT EXERCICE interessant sur les pointeurs : si on remplace
    $scope.currentIngr directement par $scope.currentFood (pour eviter de faire $scope.currentIngr.food a chaque fois)
    => eh bien ca ne marchera pas !
    Explication :
    - Lorsque le pointeur de $scope.currentIngr se met sur la var ingr (ds la fct $scope.onFocusInputIngredient)
    il est ensuite possible de lui modifier son champ .food car on pointe tjrs sur le mm INGREDIENT donc c bon (ds fct $scope.fillUpIngrWithFood)
    - Si on avait directement $scope.currentFood, lors de l'initialisation on pointe bien sur la bonne food,
    mais qd on passe une nouvelle food, le pointeur currentFood ira ailleur et perd l'ingredient!
     */
    $scope.filterFood = '';
    $scope.currentIngr = {};//{food:{"id":0,"name":"","idCategory":1,"isValidated":false}};
    $scope.showFoods = false;

    /* lorsque on ecrit ds input foodName */
    $scope.keyUpdateFilter = function(nameIngr){
        if(event.keyCode == 13){
            $scope.addRowIngredient();
        }else{
            $scope.filterFood = nameIngr; //update filter
            if(nameIngr == undefined || nameIngr == ""){
                //$log.warn(">>keyUpdateFilter<< is nameIngr == ''?: "+nameIngr);
                $scope.currentIngr.food.id=-1;
            }else{
                var foodFound = false;
                var id, name, idCategory;
                //on check si le mot correspond a une food a chaque nouvelle lettre entre/suppr.
                for(var i=0; i<$scope.foods.length; i++){
                    if (nameIngr == $scope.foods[i].name){
                        foodFound = true;
                        id = $scope.foods[i].id;
                        name = $scope.foods[i].name;
                        idCategory = $scope.foods[i].idCategory;
                    }
                }
                if(foodFound){
                    $scope.currentIngr.food.id = id;
                    $scope.currentIngr.food.idCategory = idCategory;
                }else{
                    $scope.currentIngr.food.id=0;
                }
            }
        }
    }
    $scope.onFocusInputIngredient = function(ingr){
        $scope.showFoods = true;
        $scope.filterFood = ingr.food.name; //on initialise le filtre
        $scope.currentIngr = ingr; //on initialise currentIngr
    }
    $scope.onBlurInputIngredient = function(){
        $scope.showFoods = false;
    }

    /* lorsque click on foodName proposition */
    $scope.fillUpIngrWithFood = function(index, evt, food){
        if(evt.which === 1) {
            var newFood = JSON.parse(JSON.stringify(food));//NEW OBJECT
            $scope.currentIngr.food = newFood;
        }
    }

    /******************************************************
     * ************* end FILTER WITH FOODS ****************
     * ****************************************************/

    /*

    [html]
    <input.. step="{{step}}"
    <select .. ng-change="changeValSelect(ingredient)"


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

    */
});



/*

 {"name":"Lasagnes",
 "recipeType":"course",
 "nbPerson":4,
 "ingredients":[{"qty":75,"unit":"g","food":{"id":20,"name":"oeuf","idCategory":6,"isValidated":false}},
 {"qty":1.3,"unit":"kg","food":{"id":26,"name":"reblochon","idCategory":8,"isValidated":false}}],
 "descriptions":[{"name":"faire cuire le reblochon","numDescrip":1},{"name":"puis le manger","numDescrip":2}],
 "origin":{"id":4,"name":"indien","numRank":4},
 "categories":[{"id":1,"name":"viande","numRank":1,"checked":true},
 {"id":2,"name":"four","numRank":5,"checked":true}],
 "timeCooking":50,
 "timePreparation":20}
 */