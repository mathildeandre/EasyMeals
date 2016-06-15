/**
 * Created by fabien on 07/04/2016.
 */

var myModule = angular.module('controllers');



myModule.controller('FilterCtrl', function($scope, $routeParams, $location, $localStorage, $window,  $log, restRecipeService) {




    /*********************************** USER CONNECTED **************************************/
        //$localStorage.userConnected = { pseudo: response.pseudo, id: response.idUser, token: response.token }
    $scope.isUserConnected = false;
    $scope.userConnected;
    if($localStorage.userConnected){
        $log.debug("[[FilterCtrl]] - USER CONNECTED !! ($localStorage known) idUser :"+$localStorage.userConnected.id)
        $scope.isUserConnected = true;
        $scope.userConnected = $localStorage.userConnected;
    }
    /*********************************** end USER CONNECTED **************************************/


    /*****
     * ORDER BY list
     */

    $scope.listOrderBy = [{name:'nom', value:'name'}, {name:'note', value:'-rating'}, {name:'nbVote', value:'-nbVoter'},
        {name:'temps cuisson', value:'+timeCooking'}, {name:'temps préparat°', value:'+timePreparation'}, {name:'temps total', value:'+timeTotal'}]; /*- permet dinverser lordre*/
    $scope.recipeOrderBy = 'name'; /* initialisation*/




    /** broadcast provenant de fct : $scope.toggleIsFavorite ()
     * ->into controller parent 'recipe-controller.js'
     * && [planning-controller.js] : $scope.changeRecipeType() */
    $scope.$on('updateFilter', function() {
        updateFilter();
    });
    $scope.$on('recipeTypeHasChanged', function(event, recipeTypeName) {
        $scope.filterMySelection.categories = [];
        var newIdRecipeType = restRecipeService.getIdRecipeType (recipeTypeName);
        $scope.filterSearch.categories = restRecipeService.getCategories(recipeTypeName);/*.filter(function(obj){
            return obj.idRecipeType == newIdRecipeType;
        })*/

        updateFilter();
    });

    /************************* FILTRE *********************************/
    $scope.filterSearch = {
        myLists:[ {id:'myFavorite', name:'Mes recettes préférées'}, {id:'myPlanning', name:'Mes recettes planning'}], /*{id:'myMeal', name:'Mes plats'},*/
        categories: restRecipeService.getCategories($scope.recipeType),/*.filter(function(obj){
            return obj.idRecipeType == 2;
        })*/
        origins:restRecipeService.getOrigins()
    };
    $scope.filterMySelection = {
        myLists:[],
        ingredients:[],
        users:[],
        categories:[],
        origins:[]
    };

    var addInitFirstElemenIntoFilterSearch = function(){
        $scope.filterSearch.categories.splice(0,0,{name:'Categories :'}); //INSERT EN POSITION 0
        //var index = $scope.filterSearch.categories.indexOf({name:'boom'}); //fonctionne aussi tres bien
        $scope.myCategory = $scope.filterSearch.categories[0];

        $scope.filterSearch.origins.splice(0,0,{name:'Spécialités :'}); //INSERT EN POSITION 0
        $scope.myOrigin = $scope.filterSearch.origins[0];
    }
    //addInitFirstElemenIntoFilterSearch(); instancie plusieurs fois au debut donc ajoute trop de fois le mm elm...
    // => essayer un elm en disable dans le select..

    $scope.isRecipeFavorite = function(myListId){
        return myListId == 'myFavorite';
    }
    $scope.isRecipePlanning = function(myListId){
        return myListId == 'myPlanning';

    }
    $scope.separateMyList = function(){
        return $scope.filterMySelection.myLists.length > 0 &&
            ($scope.filterMySelection.users.length > 0 || $scope.filterMySelection.ingredients.length > 0 || $scope.filterMySelection.categories.length > 0 || $scope.filterMySelection.origins.length > 0);
    }
    $scope.separateUser = function(){
        return $scope.filterMySelection.users.length > 0 &&
            ($scope.filterMySelection.ingredients.length > 0 || $scope.filterMySelection.categories.length > 0 || $scope.filterMySelection.origins.length > 0);
    }
    $scope.separateIngredient = function(){
        return $scope.filterMySelection.ingredients.length > 0 &&
            ($scope.filterMySelection.categories.length > 0 || $scope.filterMySelection.origins.length > 0);
    }
    $scope.separateCategorie = function(){
        return $scope.filterMySelection.categories.length > 0 && $scope.filterMySelection.origins.length > 0;
    }

    $scope.emptyFilterSelection = function(){
        var filterMySelection = $scope.filterMySelection;
        var filterSearch = $scope.filterSearch;
        for(var i=0; i<filterMySelection.myLists.length; i++){
            filterSearch.myLists.push(filterMySelection.myLists[i]);
        }
        for(var i=0; i<filterMySelection.categories.length; i++){
            filterSearch.categories.push(filterMySelection.categories[i]);
        }
        for(var i=0; i<filterMySelection.origins.length; i++){
            filterSearch.origins.push(filterMySelection.origins[i]);
        }

        filterMySelection.myLists = [];
        filterMySelection.ingredients = [];
        filterMySelection.users = [];
        filterMySelection.categories = [];
        filterMySelection.origins = [];
    }
    $scope.pressEnterInputIngredientFilter = function(event, ingredientName){
        $log.info("BOOM KEYUP pressed on the ingredient name :"+ingredientName+ " event.keyCode : "+event.keyCode);
        event.stopPropagation();
        event.preventDefault();
        if(event.keyCode == 13){
            $scope.moveIngredientToSelection(ingredientName);
        }
    }
    $scope.pressEnterInputUserFilter = function(event, userName){
        $log.info("BOOM KEYUP pressed on the user name :"+userName+ " event.keyCode : "+event.keyCode);
        event.stopPropagation();
        event.preventDefault();
        if(event.keyCode == 13){
            $scope.moveUserToSelection(userName);
        }
    }

    /***************************************************************************************************
     FONCTION pour display FILTER
     **************************************************************************************************/

    $scope.removeMyListFromSelection = function(myList){
        $scope.filterSearch.myLists.push(myList);
        var index = $scope.filterMySelection.myLists.indexOf(myList); //fonctionne aussi tres bien
        $scope.filterMySelection.myLists.splice(index, 1);
    }
    $scope.removeUserFromSelection = function(userName){
        var index = $scope.filterMySelection.users.indexOf(userName); //fonctionne aussi tres bien
        $scope.filterMySelection.users.splice(index, 1);
    }
    $scope.removeIngredientFromSelection = function(ingredientName){
        var index = $scope.filterMySelection.ingredients.indexOf(ingredientName); //fonctionne aussi tres bien
        $scope.filterMySelection.ingredients.splice(index, 1);
    }
    $scope.removeCategoryFromSelection = function(category){
        $scope.filterSearch.categories.push(category);
        var index = $scope.filterMySelection.categories.indexOf(category); //fonctionne aussi tres bien
        $scope.filterMySelection.categories.splice(index, 1);
        //init select avec elem fictif  "choisir"
        //$scope.myCategory = $scope.filterSearch.categories[0];
    }
    $scope.removeOriginFromSelection = function(origin){
        $scope.filterSearch.origins.push(origin);
        var index = $scope.filterMySelection.origins.indexOf(origin); //fonctionne aussi tres bien
        $scope.filterMySelection.origins.splice(index, 1);
        //init select avec elem fictif  "choisir"
        //$scope.myOrigin = $scope.filterSearch.origins[0];
    }



    $scope.moveMyListToSelection = function(myList){
        $scope.filterMySelection.myLists.push(myList);
        var index = $scope.filterSearch.myLists.indexOf(myList); //fonctionne aussi tres bien
        $scope.filterSearch.myLists.splice(index, 1);
    }
    $scope.moveUserToSelection = function(userName){
        if(userName != undefined && userName != ''){
            $scope.filterMySelection.users.push(userName);
            $scope.userName = '';
        }
    }
    $scope.moveIngredientToSelection = function(ingredientName){
        if(ingredientName != undefined && ingredientName != ''){
            $scope.filterMySelection.ingredients.push(ingredientName);
            $scope.ingredientName = '';
        }
    }
    $scope.moveCategoryToSelection = function(category){
        if(category != undefined){//car il semblerait qu'il appel cette fct 2fois, ainsi la seconde la category nest plus ds la liste...

            $log.info("BOOM je le fais une fois la puis ? (categorie : "+category.name);
            $scope.filterMySelection.categories.push(category);
            var index =  $scope.filterSearch.categories.indexOf(category); //fonctionne aussi tres bien
            $scope.filterSearch.categories.splice(index, 1);
            //init select avec elem fictif  "choisir"
            //$scope.myCategory = $scope.filterSearch.categories[0];
            incrNumRankCategory(category);

        }
    }
    $scope.moveOriginToSelection = function(origin){
        if(origin != undefined){//car il semblerait qu'il appel cette fct 2fois, ainsi la seconde origin nest plus ds la liste...
            $log.info("BOOM je le fais une fois la puis ? (origin : "+origin.name);
            $scope.filterMySelection.origins.push(origin);
            var index =  $scope.filterSearch.origins.indexOf(origin); //fonctionne aussi tres bien
            $scope.filterSearch.origins.splice(index, 1);
            //init select avec elem fictif  "choisir"
            //$scope.myOrigin = $scope.filterSearch.origins[0];
            incrNumRankOrigin(origin);
        }
    }

    /******************* IA on numRank specialite / recipeCategory ********************/
    /*On part du principe que les numRank sont initialisee avec un nb plus petit que 10
    et que des quon clique sur une category for the 1st time, on veut la voir directement en haut donc on lui affecte directement 10
    On incrementera seulement par la suite..*/
    var incrNumRankCategory = function(category){
        if(category.numRank < 10){
            category.numRank = 10;
        }else{
            category.numRank++;
        }
        restRecipeService.putIncrNumRankCategory(category.id, $localStorage.userConnected.id);//idUser
    }
    var incrNumRankOrigin = function(origin){
        if(origin.numRank < 10){
            origin.numRank = 10;
        }else{
            origin.numRank++;
        }
        restRecipeService.putIncrNumRankOrigin(origin.id, $localStorage.userConnected.id);//idUser
    }

    /************* end IA ***************/


    /** si on a select coeur/pushPin depuis NAVBAR */
    var directParamSelection = function(recipeSelection){
        if(recipeSelection != undefined ){
            for(var i=0; i<$scope.filterSearch.myLists.length; i++){
                if($scope.filterSearch.myLists[i].id == recipeSelection){
                    $scope.moveMyListToSelection($scope.filterSearch.myLists[i]);
                }
            }
            for(var i=0; i<$scope.filterSearch.categories.length; i++){
                if($scope.filterSearch.categories[i].toUpperCase() == recipeSelection.toUpperCase()){
                    $scope.moveCategoryToSelection($scope.filterSearch.categories[i]);
                }
            }
        }
    }
    directParamSelection($scope.recipeSelection);



    /***************************************************************************************************
     *                                                           fin  FONCTION pour display FILTER
     **************************************************************************************************/




    /***************************************************************************************************
     MAJ FILTER
     **************************************************************************************************/


    var updateFilter = function(){
        var recipes = $scope.recipes;
        var recipesNew = [];
        var filterMySelection = $scope.filterMySelection;

        //si aucun filtre na ete selectionné on met la full list recipes!
        if(filterMySelection.myLists.length == 0 &&
            filterMySelection.ingredients.length == 0 &&
            filterMySelection.users.length == 0 &&
            filterMySelection.categories.length == 0 &&
            filterMySelection.origins.length == 0){

            recipesNew = recipes;
        }

        //sinon on va remplir la list de recette (oupah si rien ne correspond aux criteres)
        else {

            /******************************MY LIST *****************************************************************/
            var isMyFavoriteIntoSelection = false;
            var isPlanningIntoSelection = false;
            for(var i=0; i<filterMySelection.myLists.length; i++){
                if(filterMySelection.myLists[i].id == 'myFavorite'){
                    isMyFavoriteIntoSelection = true;
                }
                if(filterMySelection.myLists[i].id == 'myPlanning'){
                    isPlanningIntoSelection = true; }
            }

            var isThereAfilterMyList = isMyFavoriteIntoSelection || isPlanningIntoSelection ;
            if(isThereAfilterMyList){ //if there is at least one filter into selection concerning myList
                for(var k=0; k<recipes.length; k++){
                    //hence we check for each item of the recipes if it can be selected ! (and added to the new recipes)
                    if((isMyFavoriteIntoSelection && recipes[k].isFavorite) || (isPlanningIntoSelection && recipes[k].isForPlanning)){
                        recipesNew.push(recipes[k]);
                    }
                }
            }
            /****************************** ORIGIN ******************************************************************/
            var isThereAfilterOrigin = filterMySelection.origins.length > 0;
            if(isThereAfilterOrigin){
                if(isThereAfilterMyList){//si il y un filtre (au moins) myList dans selection :
                    // recipesNew a ete rempli donc on fait l'intersection avec origin
                    for(var k=recipesNew.length-1; k>=0; k--) {
                        //REMOVE //si l elem ne repond pas au critere selection avec origin -> out!
                        if(! originsContains(filterMySelection.origins, recipesNew[k].origin)){//si origins contient pas recipesNew[k].origin , on delete l'elem de la list
                            recipesNew.splice(k,1);
                        }
                    }
                }else{//si il n'y a pas de filtre myList dans selection, alr on construit recipesNew avec origin
                    for(var k=0; k<recipes.length; k++){
                        //hence we check for each item of the recipes if it can be selected ! (and added to the new recipes)
                        if(originsContains(filterMySelection.origins, recipes[k].origin)){//si origins contient recipesNew[k].origin, on lajoute a la liste
                            recipesNew.push(recipes[k]);
                        }
                    }
                }
            }


            /****************************** USER ***************************************************************/
            var isThereAfilterUser = filterMySelection.users.length > 0;
            $log.debug("coucou")
            if(isThereAfilterUser){
                if(isThereAfilterMyList || isThereAfilterOrigin){//si il y un filtre (au moins) myList OU origin OU category dans selection :
                    // recipesNew a ete rempli donc on fait l'intersection avec ingredients
                    for(var k=recipesNew.length-1; k>=0; k--) {
                        //REMOVE //si l elem ne repond pas au critere selection avec ingredients -> out!
                        if(! userContains(filterMySelection.users, recipesNew[k].user.pseudo)){
                            recipesNew.splice(k,1);
                        }
                    }
                }else{//si il n'y a pas de filtre myList NI origin NI category dans selection, alr on construit recipesNew avec ingredients
                    for(var k=0; k<recipes.length; k++){
                        //hence we check for each item of the recipes if it can be selected ! (and added to the new recipes)
                        if(userContains(filterMySelection.users, recipes[k].user.pseudo)){
                            recipesNew.push(recipes[k]);
                        }
                    }
                }
            }


            /****************************** CATEGORIE ***************************************************************/
            var isThereAfilterCategory = filterMySelection.categories.length > 0;
            if(isThereAfilterCategory){
                if(isThereAfilterMyList || isThereAfilterOrigin  || isThereAfilterUser){//si il y un filtre (au moins) myList OU origin dans selection :
                    // recipesNew a ete rempli donc on fait l'intersection avec category
                    for(var k=recipesNew.length-1; k>=0; k--) {
                        //REMOVE //si l elem ne repond pas au critere selection avec category -> out!
                        if(!intersectExistStrict(filterMySelection.categories, recipesNew[k].categories)){
                            recipesNew.splice(k,1);
                        }

                    }
                }else{//si il n'y a pas de filtre myList NI origin dans selection, alr on construit recipesNew avec category
                    for(var k=0; k<recipes.length; k++){
                        //hence we check for each item of the recipes if it can be selected ! (and added to the new recipes)
                        if(intersectExistStrict(filterMySelection.categories, recipes[k].categories)){
                            recipesNew.push(recipes[k]);
                        }
                    }
                }
            }


            /****************************** INGREDIENTS ***************************************************************/
            var isThereAfilterIngredient = filterMySelection.ingredients.length > 0;
            if(isThereAfilterIngredient){
                if(isThereAfilterMyList || isThereAfilterOrigin || isThereAfilterUser|| isThereAfilterCategory){//si il y un filtre (au moins) myList OU origin OU category dans selection :
                    // recipesNew a ete rempli donc on fait l'intersection avec ingredients
                    for(var k=recipesNew.length-1; k>=0; k--) {
                        //REMOVE //si l elem ne repond pas au critere selection avec ingredients -> out!
                        if(!intersectStricteIngrsMySelectWithIngrsOfRecipe(filterMySelection.ingredients, recipesNew[k].ingredients)){
                            recipesNew.splice(k,1);
                        }

                    }
                }else{//si il n'y a pas de filtre myList NI origin NI category dans selection, alr on construit recipesNew avec ingredients
                    for(var k=0; k<recipes.length; k++){
                        //hence we check for each item of the recipes if it can be selected ! (and added to the new recipes)
                        if(intersectStricteIngrsMySelectWithIngrsOfRecipe(filterMySelection.ingredients, recipes[k].ingredients)){
                            recipesNew.push(recipes[k]);
                        }
                    }
                }
            }

        }

        $scope.recipesToDisplay = recipesNew;

        $log.info("----------------------------------------------------------------------------a end supdate filter")
    }
    $scope.$watch('filterMySelection', updateFilter, true);

    var originsContains = function(origins, origin){
        for(var i=0; i<origins.length; i++){
            if(origins[i].id == origin.id){
                return true;
            }
        }
        return false;
    }
    //we check if the pseudo of owner of the recipe is contained into the list of users of our selection
    var userContains = function(users, ownerPseudo){
        for(var i=0; i<users.length; i++){
            if(ownerPseudo.indexOf(users[i]) > -1){ //return -1 si ownerPseudo ne contient PAS users[i], entier positif sinn
                return true;
            }
        }
        return false;
    }
    var intersectExistUnion = function(arr1, arr2){//on veut ici au moins 1 elem de arr1 contenut ds arr2
        var intersect = false;
        for(var i=0; i<arr1.length; i++){
            for(var j=0; j<arr2.length; j++){
                if(arr1[i].id === arr2[j].id){
                    intersect = true;
                }
            }
        }
        return intersect;
    }
    var intersectExistStrict = function(arr1, arr2){//on veut ici que TOUS les elem de arr1 soient contenu ds arr2
        var intersectFinal = true;
        var intersect = false;
        for(var i=0; i<arr1.length; i++){
            intersect = false;
            for(var j=0; j<arr2.length; j++){
                if(arr1[i].id === arr2[j].id){
                    intersect = true;
                }
            }
            if(!intersect){
                intersectFinal = false;
            }
        }
        return intersectFinal;
    }

    /* arr 1 array de tous les ingr de notre liste de selection
       arr 2 array de tous les ingr d'une recette
       return TRUE si TOUS les ingr de my selection sont contenu dans un ingredient de la recette
     */
    var intersectStricteIngrsMySelectWithIngrsOfRecipe= function(arr1, arr2){//on veut ici que TOUS les elem de arr1 soient contenu ds arr2
        //arr1 list string - arr2 list of {qty:0.3, unit:'kg', food:'aubergine', rayonId:6}
        var intersectFinal = true;
        var intersect = false;
        for(var i=0; i<arr1.length; i++){
            intersect = false;
            for(var j=0; j<arr2.length; j++){
                var nameIngrMySelection = arr1[i];//arr1[i] : un ingr de ma selection
                var nameIngrRecipe = arr2[j].food.name;//arr2[j].food.name : un aliment de recipe
                if(nameIngrRecipe.indexOf(nameIngrMySelection) > -1){//return -1 si nameIngrRecipe ne contient PAS nameIngrMySelection, entier positif sinn
                    intersect = true;
                }
            }
            if(!intersect){
                intersectFinal = false;
            }
        }
        return intersectFinal;
    }

});