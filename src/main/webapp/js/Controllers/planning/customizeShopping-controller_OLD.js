/**
 * Created by fabien on 14/03/2016.
 */

myModule.controller('CustomizeShoppingCtrl', function($scope, $log, $location, PlanningService, RecipeService, ErrandService, restFoodService, restListShoppingService, fourTypeMeal, units, steps) {




    // $scope.listShop = [];
    $scope.categories = restFoodService.getFoodCategories();/*  [
     {id:0, name:'Autre', ingredients:[]},
     {id:1, name:'Boucherie', ingredients:[]},
     {id:2, name:'Poissonnerie', ingredients:[]},
     {id:3, name:'Epicerie', ingredients:[]},
     {id:4, name:'Surgeles', ingredients:[]},
     {id:5, name:'Boulangerie', ingredients:[]},
     {id:6, name:'Fruit/Legumes', ingredients:[]},
     {id:7, name:'Frais', ingredients:[]}
     ];*/

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


    $scope.categoryChosen = 'Surgeles';
    $scope.modifQty = false;
    $scope.toggleModifQty = function(){
        $scope.modifQty = ! $scope.modifQty;
    }


    $scope.showListShopping = function(){
        for(var i=0; i<$scope.categories.length; i++){
            if($scope.categories[i].ingredients != undefined){
                $log.debug("ON VA AFFICHER TRUE");
                return true;
            }
        }
        $log.debug("ON VA AFFICHER  listShop.length "+$scope.listShop.length);
        return $scope.listShop.length>0;
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
     * ************************* USELES BELOW ********************************************
     * ***********************************************************************************/
    $scope.addIngredientList = function(ingr){
        var newIngr = JSON.parse(JSON.stringify(ingr));//NEW OBJECT
        $scope.listShop.push(newIngr);
    }


    $scope.addCategoryList = function(catName){
        var newCat = {name:catName, ingredients:[]};//JSON.parse(JSON.stringify(cat));//NEW OBJECT
        $scope.categories.push(newCat);
    }


    $scope.trashIngredientFromListShop = function(ingr){
        var index = $scope.listShop.indexOf(ingr); //fonctionne aussi tres bien
        $scope.listShop.splice(index, 1);
    }
    $scope.moveListToCategory = function(ingr){
        for(var i=0; i<$scope.categories.length; i++){
            if($scope.categories[i].name == $scope.categoryChosen){
                $scope.categories[i].ingredients.push(ingr);
            }
        }
        var index = $scope.listShop.indexOf(ingr); //fonctionne aussi tres bien
        $scope.listShop.splice(index, 1);
    }

    $scope.moveCategoryToList = function(category, ingr){

        var index = category.ingredients.indexOf(ingr); //fonctionne aussi tres bien
        category.ingredients.splice(index, 1);
        $scope.listShop.push(ingr);
    }

});