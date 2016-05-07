/**
 * Created by fabien on 14/03/2016.
 */

myModule.controller('ListShoppingCtrl', function($scope, $location,$anchorScroll, $log, PlanningService, RecipeService, AppendixFunctionsService, ErrandService , restFoodService, restListShoppingService, fourTypeMeal, units, steps) {


    /*  $scope.categories =  [
     {id:0, name:'Autre',"numRank":1, ingredients:[]},
     {id:1, name:'Boucherie',"numRank":2, ingredients:[]},
     {id:2, name:'Poissonnerie',"numRank":4, ingredients:[]}
      ... ];*/
    $scope.categories = restFoodService.getFoodCategories();

    /* Reinitialise et initialise au tt debut les categories en ajoutant le champ ingredients  */
    var resetIngredientsOfCategories = function(){
        for(var i=0; i<$scope.categories.length; i++){
            $scope.categories[i].ingredients = [];
        }
    }
    $scope.initCategories = resetIngredientsOfCategories();



    /*************************************************************************************
     * *************************  WITH HTML   ********************************************
     * ***********************************************************************************/
    var idList = 12;
    $scope.saveListShopping = function(){
        var listSP = {id : idList++, name : 'listShop_01/05/16',
            listShopping:{name:"boom", listShoppingCategories:$scope.categories},
            planning:{name:"boom", lastOpen:true, weekMeals: $scope.fourWeekMeals}
        }
        restListShoppingService.addListShoppingPlanning(listSP);
        ErrandService.setIngrCategories($scope.categories);
        ErrandService.setPlanning($scope.fourWeekMeals);
        alert("list sauvegarder");

        $location.path("/errand");//$location.hash(recipe.id);
    }


    $scope.modifQty = false;
    $scope.toggleModifQty = function(){
        $scope.modifQty = ! $scope.modifQty;
    }


    $scope.showListShopping = function(){
        for(var i=0; i<$scope.categories.length; i++){
            if($scope.categories[i].ingredients.length > 0 ){
                return true;
            }
        }
        return false;
    }

    //$scope.newIngredient = {qty:null, unit:'', food:{}}; //pour Ajouter Ingredient mais pas vrt util


    $scope.addNewIngr = function(ingr, categoryName){
        var newIngr = JSON.parse(JSON.stringify(ingr));//NEW OBJECT
        for(var i=0; i<$scope.categories.length; i++){
            if($scope.categories[i].name == categoryName){
                $scope.categories[i].ingredients.push(newIngr);
            }
        }
    }
    $scope.trashIngredientFromCategorie = function(category, ingr){
        var index = category.ingredients.indexOf(ingr); //fonctionne aussi tres bien
        category.ingredients.splice(index, 1);
    }

    $scope.unitStep = function(aUnit){
        return AppendixFunctionsService.unitStep(aUnit);
    }
    $scope.displayIngrUnitAndFood = function(ingr){
        return AppendixFunctionsService.displayIngrUnitAndFood(ingr);
    }

    /*************************************************************************************
     * ************************* fin WITH HTML ********************************************
     * ***********************************************************************************/

    $scope.calculListShopping = function(){
        resetIngredientsOfCategories();

        var meals = [];
        var recipes = [];
        var nbPersTmp = 0;
        var recipe = {};
        var multIngr = 0;
        var ingredient = {};
        var ingrTmp = {};
        var newQtity = 0;
        var newUnit = '';
        var newIngrFood = '';

        var fourMeals = $scope.fourWeekMeals;// [$scope.breakfasts, $scope.lunchs, $scope.snacks, $scope.dinners];
        for(var a=0; a<fourMeals.length; a++){
            meals = fourMeals[a].weekMeals;
            for(var i=0; i<meals.length; i++){
                recipes = meals[i].recipes; //list recipe
                nbPersTmp = meals[i].nbPers;
                for(var j=0; j<recipes.length; j++){
                    recipe = recipes[j];
                    multIngr = nbPersTmp/recipe.nbPerson;
                    for(var k=0; k<recipe.ingredients.length; k++){
                        ingredient = recipe.ingredients[k];
                        newQtity = multIngr*ingredient.qty;
                        newUnit = ingredient.unit;
                        newIngrFood = ingredient.food;
                        ingrTmp = {qty:newQtity, unit:newUnit, food:newIngrFood};
                        for(var b=0; b<$scope.categories.length; b++){
                            if($scope.categories[b].id == ingredient.food.idCategory){
                                //on va ajouter l'ingredient ds la categorie correspondante
                                addIngrIntoCategorieAndFusionIfDuplicate(ingrTmp, $scope.categories[b].ingredients);
                            }
                        }
                    }
                }
            }
        }
        setAllIngrWithGoodUnitAndQty();
    }

    /*********************** FCT ANNEXES pr calculListShopping() ***********************************************************************/

    var addIngrIntoCategorieAndFusionIfDuplicate = function(ingrTmp, ingredients){
        var isMerged = false;
        for(var i=0; i<ingredients.length; i++){
            var ingrFromCategory = ingredients[i];
            //if ingr already into this category and units of them compatible -> merge
            if(ingrFromCategory.food.id == ingrTmp.food.id &&
                ingrFromCategory.food.name == ingrTmp.food.name &&
                ingrUnitsCompatible(ingrFromCategory, ingrTmp)){

                ingrFromCategory.qty = calculQty(ingrFromCategory, ingrTmp);
                isMerged = true;
            }
        }
        if(!isMerged){
            ingredients.push(ingrTmp);
        }
    }
    var ingrUnitsCompatible = function(ing1, ing2){
        return ((ing1.unit == ing2.unit) ||
                (ing1.unit=='g' && ing1.unit=='kg') ||
                (ing1.unit=='kg' && ing1.unit=='g') ||
                (ing1.unit=='cl' && ing1.unit=='l') ||
                (ing1.unit=='l' && ing1.unit=='cl'))
    }
    var calculQty = function(ing1, ing2){
        var unit1=ing1.unit, unit2=ing2.unit, qty1=ing1.qty, qty2=ing2.qty;

        if(unit1 == unit2){ return qty1+qty2;}
        else{ switch(unit1){
                case 'g' : if(unit2=='kg'){return qty1+qty2*1000} else{return qty1} ;
                case 'kg' :  if(unit2=='g'){return qty1+qty2/1000} else{return qty1};
                case 'cl' :  if(unit2=='l'){return qty1+qty2*100} else{return qty1};
                case 'l' :  if(unit2=='cl'){return qty1+qty2/100} else{return qty1};
                default: return qty1; /* cas ou unites differentes & unit NI g,kg,cl,l*/}
        }
    }

    var setAllIngrWithGoodUnitAndQty = function(){
        for(var i=0; i<$scope.categories.length; i++){
            for(var j=0; j<$scope.categories[i].ingredients.length; j++){
                setIngrWithGoodUnitAndQty($scope.categories[i].ingredients[j]);
            }
        }
    }
    var setIngrWithGoodUnitAndQty = function(ingr){
        var newQty = ingr.qty;
        //adjust/transform units if not well adapt (ex: 1250g will become 1,250kg & 0,8kg : 800g)
        switch(ingr.unit){
            case 'g' : if(newQty >= 1000){ingr.unit = 'kg'; ingr.qty = newQty/1000;} break;
            case 'kg' :  if(newQty <1){ingr.unit = 'g'; ingr.qty = newQty*1000;} break;
            case 'cl' :  if(newQty >= 100){ingr.unit = 'l'; ingr.qty = newQty/100;} break;
            case 'l' :  if(newQty < 1){ingr.unit = 'cl'; ingr.qty = newQty*100;} break;
        }
        /*adjust number after comma*/
        switch(ingr.unit){
            case 'g' : ingr.qty = Math.round(ingr.qty); break;
            case 'kg' : ingr.qty = Number((ingr.qty).toFixed(2)); break; //if(ingr.qty !== parseInt(ingr.qty, 10)) {ingr.qty = Number((ingr.qty).toFixed(3))};
            case 'cl' : break;
            case 'l' : break;
            default: ingr.qty = Number((ingr.qty).toFixed(1));//if (ingr.qty !== parseInt(ingr.qty, 10)){}
        }
        /* Number((ingr.qty).toFixed(3)); => permet de limiter a 3 nb apres la virgule au MAX et eventuellement moins !
         .toFixed indique le nb de chiffre apres le virgule(force mm en mettant des zero : 32 sera 32.000)
         mais toFixed transform en String donc il est important ensuite d'englober le tout par Nummber()
         Number() enleve le nb de 0 inutile dc c'est le parfait mixe (ex : 32.000 redeviendra 32! 1.300 sera 1.3) */
    }


    /**********************fin FCT ANNEXES pr calculListShopping()******************************************************************/
});
