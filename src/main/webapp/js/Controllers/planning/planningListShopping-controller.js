/**
 * Created by fabien on 14/03/2016.
 */

myModule.controller('ListShoppingCtrl', function($scope, $location,$anchorScroll, $log, PlanningService, RecipeService, fourTypeMeal, units, steps) {

    /**
     * LIST SHOPPING
     */
    /*$scope.listShop = [
        {qty:0.6,unit:"kg",food:"aubergine", rayon:'Fruit & Legumes'},
        {"qty":2.4,"unit":"g","food":"cod"},
        {"qty":200,"unit":"g","food":"flour"},
        {"qty":40,"unit":"cl","food":"milk"},
        {"qty":4,"unit":"","food":"egg"},
        {"qty":50,"unit":"g","food":"tomato", rayon:'Fruit & Legumes'},
        {"qty":4,"unit":"","food":"bread"},
        {"qty":400,"unit":"g","food":"steack"}
    ];*/
    $scope.listShop = [];

    /*
    ng-click : "Générer liste de course"
     */
    $scope.calculListShopping = function(){
        var meals = [];
        var recipes = [];
        var nbPersTmp = 0;
        var recipe = {};
        var multIngr = 0;
        var ingredient = {};
        var ingrTmp = {};
        var newQtity = 0;
        var newUnit = '';
        var newFood = '';
        var newRayonId = 0;

        var listShop = [];
        var fourMeals = $scope.fourWeekMeals;// [$scope.breakfasts, $scope.lunchs, $scope.snacks, $scope.dinners];
        for(var a=0; a<fourMeals.length; a++){
            meals = fourMeals[a].weekMeals;
            for(var i=0; i<meals.length; i++){
                recipes = meals[i].recipes; //list recipe
                nbPersTmp = meals[i].nbPers;
                for(var j=0; j<recipes.length; j++){
                    recipe = recipes[j];
                    if(typeof recipe != 'number'){
                        multIngr = nbPersTmp/recipe.nbPerson;
                        for(var k=0; k<recipe.ingredients.length; k++){
                            ingredient = recipe.ingredients[k];
                            newQtity = multIngr*ingredient.qty;
                            newUnit = ingredient.unit;
                            newFood = ingredient.food;
                            newRayonId = ingredient.rayonId;
                            ingrTmp = {qty:newQtity, unit:newUnit, food:newFood, rayonId:newRayonId};
                            listShop.push(ingrTmp);
                        }
                    }else{
                        //ON ne traite pas if its a number
                    }
                }
            }
        }

        $scope.listShop = mergeDuplicateIngredients(listShop);


        $scope.$broadcast('reCalculateCategories', $scope.listShop); //will tell to child (CustomizeShoppingCtrl to recalculate categories lists)


        //$location.path("/creationPlanning");$location.hash('listShopping'); //?????? fonctionne pas
      //  $log.debug("OK on change location to blbalabna v3")
       // $location.hash("yo");
       // $anchorScroll();   => NE FONCTIONNE PAS : recharge la page et perd les données...


    }
    $scope.testAnchor = function(){

    }

    var mergeDuplicateIngredients = function(listShop){
        var ingredient = {}; //{"qty":50,"unit":"g","food":"tomato"}
        var ingredient2 = {};
        for(var i=0; i<listShop.length; i++){
            ingredient = listShop[i];
            if(ingredient.food != ''){
                for(var j=i+1; j<listShop.length; j++){
                    ingredient2 = listShop[j];
                    if(ingredient.food != '' && ingredient.food == ingredient2.food){
                        var newQty = calculQty(ingredient, ingredient2);
                        if(ingredient.qty < newQty){ /*si qty na pas bougé : "cas ou unites differentes & unit NI g,kg,cl,l" */
                            ingredient2.food = '';
                            ingredient.qty = newQty;

                        }
                    }
                }

                setIngrWithGoodUnitAndQty(ingredient);
            }
        }

        //REMOVE ingredient empty
        for(var k=listShop.length-1; k>=0; k--) {
            if (listShop[k].food == '') {
                listShop.splice(k, 1);
            }
        }
        return listShop;
    }

    var calculQty = function(ing1, ing2){
        var unit1=ing1.unit;
        var unit2=ing2.unit;
        var qty1=ing1.qty;
        var qty2=ing2.qty;

        var newQty;
        if(unit1 == unit2){
            newQty = qty1+qty2;
        }
        else{
            switch(unit1){
                case 'g' : if(unit2=='kg'){newQty = qty1+qty2*1000} else{newQty = qty1} break;
                case 'kg' :  if(unit2=='g'){newQty = qty1+qty2/1000} else{newQty = qty1} break;
                case 'cl' :  if(unit2=='l'){newQty = qty1+qty2*100} else{newQty = qty1} break;
                case 'l' :  if(unit2=='cl'){newQty = qty1+qty2/100} else{newQty = qty1} break;
                default: newQty = qty1; /* cas ou unites differentes & unit NI g,kg,cl,l*/
            }
        }
        return newQty;
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
        /*
         Number((ingr.qty).toFixed(3)); => permet de limiter a 3 nb apres la virgule au MAX et eventuellement moins !
         .toFixed indique le nb de chiffre apres le virgule(force mm en mettant des zero : 32 sera 32.000)
         mais toFixed transform en String donc il est important ensuite d'englober le tout par Nummber()
         Number() enleve le nb de 0 inutile dc c'est le parfait mixe (ex : 32.000 redeviendra 32! 1.300 sera 1.3)
         */
    }


    $scope.unitStep = function(aUnit){
        switch(aUnit){
            case 'g' : return 25;
            case 'kg' : return 0.1;
            case 'cl' : return 1;
            case 'l' : return 1;
            default: return 1;
        }
    }
    $scope.displayIngrUnitAndFood = function(ingr){
        switch(ingr.unit){
            case 'g' : return ingr.unit+' de '+ingr.food;
            case 'kg' : return ingr.unit+' de '+ingr.food;
            case 'cl' : return ingr.unit+' de '+ingr.food;
            case 'l' : return ingr.unit+' de '+ingr.food;
            default: return ingr.unit+' '+ingr.food;
        /*On naffiche pas de 's' apres nom en cas de pluriel car ex : 6 crepe a burritos => 6 crepe a burritoss
         * Il faudrait plutot avoir un mode pluriel definit pour chaque food et l'activer en fonction.. */
        }
    }
    $scope.getIngrUnitDisplay = function(ingredientUnit){
        if(ingredientUnit != ''){
            return (ingredientUnit + ' of');
        }else {
            return ' piece(s) of';
        }
    }


});
