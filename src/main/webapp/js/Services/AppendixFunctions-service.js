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



        return {
            unitStep: unitStep,
            displayIngrUnitAndFood: displayIngrUnitAndFood
        };
    })

    /*
    attention les units et step sont en lien, les 2 tableaux doivent correspondrent
     */
    .constant('units',
        ['', 'g', 'kg', 'cl', 'l']
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