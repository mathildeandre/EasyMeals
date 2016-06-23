/**
 * Created by fabien on 13/03/2016.
 */
var myModule = angular.module('controllers');



myModule.controller('RecipeCtrl', function($scope, $localStorage, $routeParams, $location, $window, $log,  $http, $q, AppendixFunctionsService, restRecipeService, restUserService) {

    /*********************************** USER CONNECTED **************************************/
    $scope.isUserConnected = false;
    $scope.userConnected = {id: 0, pseudo: '', email: '', isAdmin: false, colorThemeRecipe: 'grey'};
    if($localStorage.userConnected){
        $log.debug("[[RecipeCtrl]] - USER CONNECTED !! ($localStorage known)")
        $scope.isUserConnected = true;
        $scope.userConnected = $localStorage.userConnected;
    }
    /*********************************** end USER CONNECTED **************************************/

    /*********************************** check for USER CONNECTED **************************************/
    var isTokenValidOrExpired = function(){
        if($localStorage.userConnected){
            restUserService.getIsTokenValid($scope.userConnected.id).then(function (isTokenStilValid) { //217 = idUser
                if(isTokenStilValid){
                    //user still connected
                    $log.error("[RecipeCtrl] -isTokenValidOrExpired()- USER IS STILL CONNECTED ->  do nothing")
                }else{
                    //user not connected anymore
                    $log.error("[RecipeCtrl] -isTokenValidOrExpired()- USER SHOULD NOT BE CONNECTED ANYMORE -> logout()")
                    $scope.$emit('userLogout');
                }
            })
        }
    }
    var checkIfTokenExpired = isTokenValidOrExpired();
    /*********************************** end check for USER CONNECTED **************************************/


    $scope.$emit('intoRecipe'); //will tell to parents (global-controller.js) to modify pix

    $scope.recipeType = $routeParams.recipeType;
    $scope.recipes = restRecipeService.getRecipes($scope.recipeType);
    $scope.pixxes = restRecipeService.getCoursesPix();

    var recipeSelection = $routeParams.selection; /*depuis navBar clique ds recette sur le coeur/pinTab...*/
    $scope.recipeSelection = recipeSelection;



    /*
    restRecipeService.getBddRecipesImages($scope.userConnected.id).then(function (data) { //217 = idUser

        $log.warn("[RecipeCtrl] -- getBddRecipesImages()--");
        var allRecipesImages = data;
        for(var i=0; i<allRecipesImages.length; i++){
            for(var j=0; j<$scope.recipes.length; j++) {
                if(allRecipesImages[i].id == $scope.recipes[j].id ){
                    $scope.recipes[j].image = allRecipesImages[i].image;
                }
            }
        }
    })
    */


    /********************** PAGINATION *************************/
    $scope.currentPage = 1;
    $scope.itemPerPage = 9;

    $scope.checkPagination = function(index, currentPage){
        $log.info("currentPage :  "+currentPage)

        var bool= (index >= ((currentPage-1)*$scope.itemPerPage)) && (index < (currentPage*$scope.itemPerPage))
        /*$log.info("((currentPage-1)*itemPerPage) :  "+(($scope.currentPage-1)*$scope.itemPerPage))
        $log.info("(index >= ((currentPage-1)*itemPerPage)) :  "+(index >= (($scope.currentPage-1)*$scope.itemPerPage)))
        $log.info("(currentPage-1*itemPerPage) :  "+($scope.currentPage-1*$scope.itemPerPage))
        $log.info("(index < (currentPage-1*itemPerPage)) :  "+(index < ($scope.currentPage-1*$scope.itemPerPage)))*/
        return bool
    }

    /******************* end PAGINATION ***********************/
    /*var setImgBurger = function(){

        var imageBase64 = $scope.recipes[0].image;
        var blob = new Blob([imageBase64], {type: 'image/png'});
        var file = new File([blob], 'imageFileName.png');
    }
    var setImg = setImgBurger();
    */

    $scope.changeRecipeType = function(recipeType){/* click on big top Buttons : starter, course, dessert...*/
        $scope.recipeType = recipeType;
        $scope.recipes = restRecipeService.getRecipes($scope.recipeType);
        //$scope.$broadcast('updateFilter');
        $scope.$broadcast('recipeTypeHasChanged', recipeType);
    }
    $scope.isRecipeTypeSelected = function(recipeType){
        return $scope.recipeType == recipeType;
    }

    $scope.displayRecipeType = function(){
        return AppendixFunctionsService.displayRecipeType($scope.recipeType);
    }
    $scope.displayButtonCreationRecipeType = function(){
        return AppendixFunctionsService.displayButtonCreationRecipeType($scope.recipeType);
    }

    $scope.displayTime = function(timeInMinute){
        return AppendixFunctionsService.displayTime(timeInMinute);
    }

    $scope.listColor = [{name:'blanc', value:'white'},{name:'gris', value:'grey'},{name:'noir', value:'black'},{name:'orange', value:'orange'} ]
    //$scope.colorChoosen = $scope.listColor[1];
    $scope.colorChoosen = $scope.listColor.filter(function(obj){
        return obj.value == $scope.userConnected.colorThemeRecipe;
    })[0]

    $scope.updateBddColor = function(colorChoosen){
        restRecipeService.updateBddColor(colorChoosen.value, $scope.userConnected.id); //idUser
    }
    $scope.stopPropag = function(event){
        event.stopPropagation();
        $log.info("STOP PROPAG !!")
    }


    /**********************************************************************************************************/
    /************************************** RATING MODE ******************************************************/
    /********************************************************************************************************/
    $scope.isStarFull = function(numStar, rating){
        //$log.info("---------------numstar : "+numStar+" rating : "+Math.round(rating)+"------------- RESULT ::: ");
        return numStar <= Math.round(rating);
    }
    $scope.editRatingUser = function($index, starsEdit){
        for(var i=0; i<starsEdit.length; i++){
            starsEdit[i] = (i<=$index);
        }
    }

    $scope.validateRatingUser = function(index, recipe, event){
        event.stopPropagation();
        recipe.ratingUser = index+1;
        var result = (recipe.rating * recipe.nbVoter + recipe.ratingUser)/(recipe.nbVoter+1);
        recipe.rating =  Number(result.toFixed(1));
        recipe.nbVoter = recipe.nbVoter+1;
        recipe.ratingSystem.isUserEditing = false;
        restRecipeService.putRatingUser(recipe.id, $scope.userConnected.id, recipe.ratingUser);//idUser
    }
    $scope.displayRatingOfRecipeByTitle = function(recipe){
        var ratingUser = recipe.ratingUser;
        if(ratingUser == 0){ratingUser = "-"}
        return ("Note : "+recipe.rating+"\nMa note : "+ratingUser);
    }
    $scope.displayRatingUser = function(recipe){
        if( recipe.ratingUser == 0){
            return "-"
        }else{
            return recipe.ratingUser;
        }
    }

    /* no use anymore...(was into recipeItemDisplayOpen.html)*/
    $scope.toggleEditingRate = function(recipe, event){
        event.stopPropagation();
        recipe.ratingSystem.isUserEditing = !recipe.ratingSystem.isUserEditing ;
    }
    $scope.stopPropag = function(event){
        event.stopPropagation();
        $log.info("STOP PROPAG !!")
    }

    /*
    var initStars = function(){
        for(var i=0; i<$scope.recipes.length; i++){
            $scope.recipes[i].starsIsFull = [false, false, false, false, false];
        }
    }
    initStars();
    */
    $scope.isUserEditRating = true;
    $scope.ratingUserEdit = 0;
    $scope.starsEdit = [true, false, false, false, false];

    $scope.changeRatingUser = function(numStar){
        $log.debug("ratingUserEdit : "+$scope.ratingUserEdit+" numstar:"+numStar);
        $scope.ratingUserEdit = numStar;
        $log.debug("ratingUserEdit : "+$scope.ratingUserEdit+" numstar:"+numStar);
        //$scope.apply();
    }
    //$scope.overRating = false;


    $scope.hoverStar = function(numStar){
        alert(numStar);
    }

    $scope.getNumberStarsFull = function(num){
        var newNum = Math.round(num);
        return new Array(newNum);
    }
    $scope.getNumberStarsEmpty = function(num){
        var newNum = Math.round(5 - num);
        return new Array(newNum);
    }
    /**********************************************************************************************************/
    /************************************** end RATING MODE **************************************************/
    /********************************************************************************************************/



    $scope.toggleDescOpen = function(recipe){
        recipe.descriptionOpen = !recipe.descriptionOpen;
    }

    /* important to have both fct */
    $scope.showInBlock = false;
    $scope.showInListFct = function(){
        $scope.showInBlock = false;
        closeAllOpenDescr();
    }
    $scope.showInBlockFct = function(){
        $scope.showInBlock = true;
        closeAllOpenDescr();
    }

    var closeAllOpenDescr = function(){
        for(var i=0; i<$scope.recipes.length; i++){
            $scope.recipes[i].descriptionOpen = false;
        }
        /* boadcast for filterRecipePlanning-controller.js */
        //$scope.$broadcast('closeAllOpenDescr');

    }

    /* ICI !!!! doit communiquer avec filter controller (broadcast) */
    $scope.toggleIsFavorite = function(recipe, event){
        event.stopPropagation();
        recipe.isFavorite = !recipe.isFavorite;
        $scope.$broadcast('updateFilter');
        restRecipeService.putIsFavorite(recipe.id, $scope.userConnected.id, recipe.isFavorite);//idUser
    }
    $scope.toggleIsForPlanning = function(recipe, event){
        event.stopPropagation();
        recipe.isForPlanning = !recipe.isForPlanning;
        $scope.$broadcast('updateFilter');
        restRecipeService.putIsForPlanning(recipe.id, $scope.userConnected.id, recipe.isForPlanning);//idUser
    }

    $scope.openRecipeNewWindow = function(id) {
        event.stopPropagation();
        $window.open('http://localhost:8080/#/singleRecipe/'+$scope.recipeType+'/'+id);
    };

    /*$scope.reloadRoute = function() {
        alert("bjr");
        $route.reload();
        $location.search( 'viande', null );
    }*/









    /********************************** CAROUSEL *************************************/
    /********************************** CAROUSEL *************************************/
    /********************************** CAROUSEL *************************************/
    /********************************** CAROUSEL *************************************/
    /********************************** CAROUSEL *************************************/
    /********************************** CAROUSEL *************************************/


    $scope.myInterval = 5000;
    $scope.noWrapSlides = false;
    $scope.active = 0;
    var slides = $scope.slides = [];
    var currIndex = 0;

    $scope.addSlide = function() {
        var newWidth = 600 + slides.length + 1;
        slides.push({
            image: 'http://lorempixel.com/' + newWidth + '/300',
            text: ['Nice image','Awesome photograph','That is so cool','I love that'][slides.length % 4],
            id: currIndex++
        });
    };

    $scope.randomize = function() {
        var indexes = generateIndexesArray();
        assignNewIndexesToSlides(indexes);
    };

    for (var i = 0; i < 4; i++) {
        $scope.addSlide();
    }

    // Randomize logic below

    function assignNewIndexesToSlides(indexes) {
        for (var i = 0, l = slides.length; i < l; i++) {
            slides[i].id = indexes.pop();
        }
    }

    function generateIndexesArray() {
        var indexes = [];
        for (var i = 0; i < currIndex; ++i) {
            indexes[i] = i;
        }
        return shuffle(indexes);
    }

    // http://stackoverflow.com/questions/962802#962890
    function shuffle(array) {
        var tmp, current, top = array.length;

        if (top) {
            while (--top) {
                current = Math.floor(Math.random() * (top + 1));
                tmp = array[current];
                array[current] = array[top];
                array[top] = tmp;
            }
        }

        return array;
    }




});