/**
 * Created by fabien on 14/03/2016.
 */

myModule.controller('ListShoppingCtrl', function($scope, $location,$anchorScroll, $log, PlanningService, RecipeService, ErrandService , restFoodService, restListShoppingService, fourTypeMeal, units, steps) {


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
    /* display for "showListShopping()"
     $scope.elemNotEmptyListShopping = function(){
     for(var i=0; i<$scope.categories.length; i++){
     if($scope.categories[i].ingredients.length > 0){
     return $scope.categories[i].name;
     }
     }
     return "list shop not empty ";//+$scope.listShop.[0].food;
     }
     */


    //the parent scope (ListShoppingCtrl) sent broadcast to say we need to recalculate categories lists
    $scope.$on('reCalculateCategories', function(event, listShop) {
        //reset all categories
        for(var i=0; i<$scope.categories.length; i++){
            $scope.categories[i].ingredients = [];
        }

        var aListShop = [];
        var aCategory = [];
        var hasCategory = false;

        for(var i=0; i<listShop.length; i++){
            aListShop = listShop[i];//aListShop = {qty:newQtity, unit:newUnit, food:newFood, rayonId:newRayonId};

            hasCategory = false; //ne devrait pas etre utilisé: il est important de mettre par default une categorie (=0) lors de creation de recettes
            for(var j=0; j<$scope.categories.length; j++){
                aCategory = $scope.categories[j];
                if(aListShop.rayonId == aCategory.id){
                    aCategory.ingredients.push(aListShop);
                    hasCategory = true;
                }
            }
            if(!hasCategory){
                $scope.categories[0].ingredients.push(aListShop);
            }
        }
        $scope.listShop = [];
    });


    $scope.newIngredient = {qty:null, unit:'', food:''};


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



    /*************************************************************************************
     * ************************* CUSTOMIZE ABOVE ********************************************
     * ***********************************************************************************/




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

        //var listShop = [];
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

                                //$scope.categories[b].ingredients.push(ingrTmp);
                            }
                        }
                        //listShop.push(ingrTmp);
                    }
                }
            }
        }

        //$scope.listShop = mergeDuplicateIngredients(listShop);


        //$scope.$broadcast('reCalculateCategories', $scope.listShop); //will tell to child (CustomizeShoppingCtrl to recalculate categories lists)


        //$location.path("/creationPlanning");$location.hash('listShopping'); //?????? fonctionne pas
      //  $log.debug("OK on change location to blbalabna v3")
       // $location.hash("yo");
       // $anchorScroll();   => NE FONCTIONNE PAS : recharge la page et perd les données...

        setAllIngrWithGoodUnitAndQty();


    }

    var setAllIngrWithGoodUnitAndQty = function(){
        for(var i=0; i<$scope.categories.length; i++){
            for(var j=0; j<$scope.categories[i].ingredients.length; j++){
                setIngrWithGoodUnitAndQty($scope.categories[i].ingredients[j]);
            }
        }
    }
    var addIngrIntoCategorieAndFusionIfDuplicate = function(ingrTmp, ingredients){
        var isMerged = false;
        for(var i=0; i<ingredients.length; i++){
            var ingrFromCategory = ingredients[i];
            //if ingr already into this category and units of them compatible -> merge
            if(ingrFromCategory.food.id == ingrTmp.food.id && ingrUnitsCompatible(ingrFromCategory, ingrTmp)){
                ingrFromCategory.qty = calculQty(ingrFromCategory, ingrTmp);
                isMerged = true;
            }
        }
        if(!isMerged){
            ingredients.push(ingrTmp);
        }
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

    var ingrUnitsCompatible = function(ing1, ing2){
        return ((ing1.unit == ing2.unit) ||
                (ing1.unit=='g' && ing1.unit=='kg') ||
                (ing1.unit=='kg' && ing1.unit=='g') ||
                (ing1.unit=='cl' && ing1.unit=='l') ||
                (ing1.unit=='l' && ing1.unit=='cl'))
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
            case 'g' : return ingr.unit+' de '+ingr.food.name;
            case 'kg' : return ingr.unit+' de '+ingr.food.name;
            case 'cl' : return ingr.unit+' de '+ingr.food.name;
            case 'l' : return ingr.unit+' de '+ingr.food.name;
            default: return ingr.unit+' '+ingr.food.name;
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
