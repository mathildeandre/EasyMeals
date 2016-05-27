/**
 * Created by fabien on 17/03/2016.
 */

var myModule = angular.module('controllers');

myModule.controller('CalculationExpenseCtrl', function($scope, $log) {


    $scope.$emit('intoExpense'); //will tell to parents (global-controller.js) to modify pix


    $scope.expense = {
        name : "",
        nbPers : 4,
        persons : [{id:0, name:'pierre'}, {id:1, name:'paul'}, {id:2, name:'jack'}, {id:3, name:'brigit'}],
        rows : [
            {
                id:0,
                buyerId:0,
                price:30,
                listChecked:[{id:0, bool:true}, {id:1, bool:true}, {id:2, bool:false}, {id:3, bool:true}],
                checkedAll:false,
                description:'pour les courses'
            },
            {
                id:1,
                buyerId:3,
                price:15,
                listChecked:[{id:4, bool:false}, {id:5, bool:true}, {id:6, bool:true}, {id:7, bool:true}],
                checkedAll:false,
                description:'bonbons'
            }
        ]
    }

/*
    $scope.nbPers = 4;

    //$scope.persons = ['person1', 'person2', 'person3', 'person4'];
    $scope.persons = [{id:0, name:'pierre'}, {id:1, name:'paul'}, {id:2, name:'jack'}, {id:3, name:'brigit'}];
*/
    $scope.idRow = 17; //permet d'avoir un id unique pour chaque row qui s'incremente dans $scope.addRowExpense()
    // (autrement possible pb lors de suppression de row puis ajout...
    /*
     $scope.rows = [
     {
     id:0,
     buyerId:0,
     price:30,
     listChecked:[{id:0, bool:true}, {id:1, bool:true}, {id:2, bool:false}, {id:3, bool:true}],
     checkedAll:false,
     description:'pour les courses'
     },
     {
     id:1,
     buyerId:3,
     price:15,
     listChecked:[{id:4, bool:false}, {id:5, bool:true}, {id:6, bool:true}, {id:7, bool:true}],
     checkedAll:false,
     description:'bonbons'
     }
     ];
     */




    $scope.updatePersons = function(){
        var nbPers = $scope.expense.nbPers;

        $log.debug('BONJOUUUUUUR 1 - persons.length :'+$scope.expense.persons.length);
        $log.debug( ' nb persons :'+nbPers);
        //$scope.expense.persons = [];
        if(nbPers > $scope.expense.persons.length){//on ajoute des elemts
            for(var i=$scope.expense.persons.length; i<nbPers; i++){
                var obj = {id:i, name:'person'+i};
                $scope.expense.persons.push(obj);
                for(var k=0; k<$scope.expense.rows.length; k++){
                    var isChecked = $scope.expense.rows[k].checkedAll;
                    var objCheck = {id:i, bool:isChecked};
                    $scope.expense.rows[k].listChecked.push(objCheck);
                }
            }
        }else if(nbPers < $scope.expense.persons.length){//on doit supprimer
            for(var k=0; k<$scope.expense.rows.length; k++){
                $scope.expense.rows[k].listChecked.splice(nbPers, ($scope.expense.persons.length-nbPers));
            }
            $scope.expense.persons.splice(nbPers, ($scope.expense.persons.length-nbPers)); //le 1 indique combien d'element on remove a partir de index
        }else{
            $log.info('AIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIEE AIE AIE');
        }

        $log.info('BONJOUUUUUUR 2 - persons.length :'+$scope.expense.persons.length);
        $log.info( ' nb persons :'+nbPers);
    };


    $scope.aPersonColumnChecked = function(row){//a person has been (un)checked
        var checkAll = true;
        for(var i=0; i<row.listChecked.length; i++){
            if(!row.listChecked[i].bool){
                checkAll = false;
            }
        }
        row.checkedAll = checkAll;
    };
    $scope.aAllChecked = function(row){//'all' has been (un)checked
        for(var i=0; i<row.listChecked.length; i++){
            row.listChecked[i].bool = row.checkedAll;
        }
    };

    /* //ne pas supprimer... peut servir
     $scope.toggleCheckList = function(isChecked, row){
     isChecked.bool = !isChecked.bool ;
     $scope.aPersonColumnChecked(row)
     };
     $scope.toggleAll = function(row){
     row.checkedAll = !row.checkedAll;
     };
     */

    var updateRows = function(){
        creationTabExpense();
        creationTabCouple();

    };
    $scope.$watch('expense', updateRows, true);
    // $scope.$watch('filterQtyChosen', updateAlcoholsWithQty);

    $scope.addRowExpense = function(){
        var numId = $scope.idRow++;
        var listCheck = [];
        for(var i=0; i<$scope.expense.persons.length; i++){
            listCheck.push({id:i, bool:false});
        }
        var row = {id:numId, buyerId:0, price:0,listChecked:listCheck, checkedAll:false, description:''}
        $scope.expense.rows.push(row);
    };
    $scope.deleteRowExpense = function(row){
        var index = $scope.expense.rows.indexOf(row); //fonctionne aussi tres bien
        $scope.expense.rows.splice(index, 1);
    };

    /*******************************************************************************************************************************
     /*******************************************************************************************************************************
     /*******************************************************************************************************************************
     * CALCULATION OF TAB
     /*******************************************************************************************************************************
     /*******************************************************************************************************************************
     /*******************************************************************************************************************************
     */

    /*
     * Explication calcul :
     * On cree tout dabord un TAB_EXPENSE avec taille egale aux nombre de personnes
     * il contiendra la valeur que chaque personne a payé (nb positif) ou doit rembourser (nb negatif)
     *
     * On cree ensuite un TAB_COUPLE qui est un array de triplet(triplet de 3 valeurs : QUI doit COMBIEN à QUI)
     *
     *
     */

    $scope.tabExpense = [];
    var creationTabExpense = function(){
        $scope.tabExpense = [];
        var tabExpense = $scope.tabExpense;
        for(var i=0; i<$scope.expense.persons.length; i++){
            tabExpense.push(0);
        }

        for(var i=0; i<$scope.expense.rows.length; i++){
            var row = $scope.expense.rows[i];


            //value du select = 'id-name' mais name disparait avec parseInt
            var idBuyer = row.buyerId;
            var amountBuyerPayed = row.price;

            // on compte combien de personne sont concernees par le payement
            var nbChecked = 0;
            var buyerPaidForHimself = false;
            for(var j=0; j<row.listChecked.length; j++){
                if(row.listChecked[j].bool){
                    nbChecked++;
                    if(row.listChecked[j].id == idBuyer){//si celui qui a payé est coché
                        buyerPaidForHimself = true;
                    }
                }
            }
            if(nbChecked > 0){
                var amountPerson = (amountBuyerPayed/nbChecked); //.toFixed(1); //prix par personne
                var positionBuyer=idBuyer;
                //est ce que le buyer a payer pour lui meme?
                if (buyerPaidForHimself){
                    amountBuyerPayed -= amountPerson; //alors on lui soustrait sa part
                }

                tabExpense[positionBuyer] += amountBuyerPayed;
                for(var k=0; k<row.listChecked.length; k++){
                    //si ce nest pas le buyer && si la pers est cochee on soustrait
                    if(row.listChecked[k].id != idBuyer && row.listChecked[k].bool){
                        tabExpense[k] -= amountPerson;
                    }

                }


            }

        }
        verifyTabExpenseSumZero();
        $log.info('------------------------------  tabExpense='+tabExpense);
        $scope.tabExpense = tabExpense;
    };

    var verifyTabExpenseSumZero = function(){
        var sum = 0;
        for(var i=0; i<$scope.tabExpense.length; i++){
            sum += $scope.tabExpense[i];
        }

        $log.warn('>>>>>>>> verif SUM ='+sum);
        return sum < 1;
    }




    $scope.tabCouple = [];
    var creationTabCouple = function(){
        var num1 = 0.5;
        var num2 = 0.00005;
        var num3 = 0;
        /* true
         alert('PUTIN DADELLE');
         alert('PUTIN 3 : '+num1.toFixed(1) > num3.toFixed(1)); //true
         alert('0 toFixe 1 : '+num3.toFixed(1)); //true
         alert('PUTIN DADELLE2');
         */
        /* false
         alert(num1 > num2);
         alert(num2 > num1);
         alert(num1.toFixed(1) < num2.toFixed(1));
         alert(num3 ==  num2.toFixed(0));
         alert(num3.toFixed(0) ==  num2.toFixed(0));
         */

        $scope.tabCouple = [];
        var tabCouple = $scope.tabCouple;
        var tabExpense = $scope.tabExpense;
        var indexMax = findIndexMax(tabExpense);
        var indexMin = findIndexMin(tabExpense);
        var isBiggestPositive = true;
        var amount = 0;
        var maxLoop = 0;
        var isTooSmall = false;
        var isPosTooSmall = false;
        var isNegTooSmall = false;
        var zeroFive = 0.5;

        while(tabExpense[indexMax] > 0 && !isTooSmall){ //maxLoop < 20){
            isBiggestPositive = tabExpense[indexMax] >= Math.abs(tabExpense[indexMin]);
            maxLoop++;

            if(!isBiggestPositive){
                amount = tabExpense[indexMax];
                tabExpense[indexMin] += amount;
                tabExpense[indexMax] = 0;
            }else{
                amount = tabExpense[indexMin];
                tabExpense[indexMax] += amount; // tabExpense[indexMin] est < 0
                tabExpense[indexMin] = 0;
                //Math.abs(tabExpense[indexMax]) < 0.5; //useless ? 2/04/2016
            }
            tabCouple.push({debtGuy:$scope.expense.persons[indexMin], amount:Math.abs(amount.toFixed(0)), benefitGuy:$scope.expense.persons[indexMax]});
            indexMax = findIndexMax(tabExpense);
            indexMin = findIndexMin(tabExpense);

            /*** CONDITION DARRET ***/
            isPosTooSmall = zeroFive.toFixed(1) > Math.abs(tabExpense[indexMax]).toFixed(1);
            //$log.debug(' after MAJ - tabExpense[indexMax] : '+tabExpense[indexMax]+' || isPosTooSmall:'+isPosTooSmall);
            isNegTooSmall = zeroFive.toFixed(1) > Math.abs(tabExpense[indexMin]).toFixed(1);
            //$log.debug(' after MAJ - tabExpense[indexMin] : '+tabExpense[indexMin]+' || isNegTooSmall:'+isNegTooSmall);
            isTooSmall = (isPosTooSmall && isNegTooSmall);
        }
        //$log.error("AIIIIE AIIIIIE AIIIIIE --  MAX LOOP : "+maxLoop);
        //$log.debug('tabExpense : '+tabExpense+ '  -  tabCouple : '+tabCouple);
        $scope.tabCouple = tabCouple;
    };

    var findIndexMin = function(tab){
        if(tab.length > 0){
            var min = tab[0];
            var index = 0;
            for(var i=1; i<tab.length; i++){
                if(tab[i] < min){
                    min = tab[i];
                    index = i;
                }
            }
            return index;
        }
        return null;
    };
    var findIndexMax = function(tab){ //return index
        if(tab.length > 0){
            var max = tab[0];
            var index = 0;
            for(var i=1; i<tab.length; i++){
                if(tab[i] > max){
                    max = tab[i];
                    index = i;
                }
            }
            return index;
        }
        return null;
    }

});