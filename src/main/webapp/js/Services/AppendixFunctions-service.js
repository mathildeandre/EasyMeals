/**
 * Created by fabien on 02/03/2016.
 * OLD
 */

var myService = angular.module('services');
myService.service('AppendixFunctionsService', function($http, $q, $log) {

        function unitStep(aUnit){
            switch(aUnit){
                case 'g' : return 25;
                case 'kg' : return 0.1;
                case 'cl' : return 1;
                case 'l' : return 1;
                default: return 1;
            }
        }
        function displayIngrUnitAndFood(ingr){
            if(ingr.unit=='g' || ingr.unit=='kg' || ingr.unit=='cl' || ingr.unit=='l'){
                return ingr.unit+' de '+ingr.food.name;
            }else{
                return ingr.unit+' '+ingr.food.name;
            }
            /*On naffiche pas de 's' apres nom en cas de pluriel car ex : 6 crepe a burritos => 6 crepe a burritoss
             * Il faudrait plutot avoir un mode pluriel definit pour chaque food et l'activer en fonction.. */
        }

        function displayRecipeType(recipeType){
            switch(recipeType){
                case 'starter' : return 'Entrées';
                case 'course' :  return 'Plats';
                case 'dessert' : return 'Desserts';
                case 'breakfast' : return 'Déjeuners - Goûters';
                case 'cocktail' : return 'Cocktails';
            }
        }
        function displayButtonCreationRecipeType(recipeType){
            switch(recipeType){
                case 'starter' : return 'Créer une nouvelle Entrée';
                case 'course' :  return 'Créer un nouveau Plat';
                case 'dessert' : return 'Créer un nouveau Dessert';
                case 'breakfast' : return 'Créer un nouveau Dej/Goûter';
                case 'cocktail' : return 'Créer un nouveau Cocktail';
            }
        }
        function displayTitleCreationRecipeType(recipeType){
            switch(recipeType){
                case 'starter' : return 'Création d\'une Entrée';
                case 'course' :  return 'Création d\'un Plat';
                case 'dessert' : return 'Création d\'un Dessert';
                case 'breakfast' : return 'Création d\'un Dej/Goûter';
                case 'cocktail' : return 'Création d\'un Cocktail';
            }
        }
        function displayMealType(mealType){
            switch(mealType){
                case 'breakfast' : return 'Petit déjeuner';
                case 'lunch' :  return 'Repas du midi';
                case 'snack' : return 'Goûter';
                case 'dinner' : return 'Dîner';
            }
        }

        //CETTE FONCTION EST APPELlé bcp trop souvent, entrer directement le str dans
                // les $scope de chaque recette avec le init ds restService recipe...
        function displayTime(timeInMinute){
            var hour = Math.floor(timeInMinute/60);
            var minLeft = Math.round(timeInMinute%60);
            //$log.debug("hour : "+hour+" - min left : "+minLeft);
            var strReturn;
            if(hour > 0  && minLeft > 0){
                strReturn = hour+"h"+minLeft;
            }
            else if(hour > 0){
                strReturn = hour+"h"
            }
            else if(minLeft > 0){
                strReturn = minLeft+"min"
            }
            else{
                strReturn = "-";
            }
            return strReturn;
        }

        return {
            unitStep: unitStep,
            displayIngrUnitAndFood: displayIngrUnitAndFood,
            displayRecipeType: displayRecipeType,
            displayButtonCreationRecipeType: displayButtonCreationRecipeType,
            displayTitleCreationRecipeType: displayTitleCreationRecipeType,
            displayMealType: displayMealType,
            displayTime: displayTime
        };
    })

    /*
    attention les units et step sont en lien, les 2 tableaux doivent correspondrent
     */
    .constant('units',
        ['', 'g', 'kg', 'cl', 'l', 'cuillère à soupe']
    )
    .constant('steps',
        [1, 25, 0.5, 5, 0.5]
    )

    .constant('rayonMagasin', [
        'Fruit & Legumes',
        'Boucherie',
        'Poissonnerie',
        'Epicerie',
        'Boites',
        'Surgeles',
        'Petit dejeune',
        'Autres'
    ]);