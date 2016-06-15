
var myModule = angular.module('controllers');
myModule.controller('ModalCreationRecipeCtrl', ['$scope', '$uibModal', '$log', '$location', 'restPlanningService', '$localStorage', function ($scope, $uibModal, $log, $location, $localStorage, restPlanningService) {


    $scope.openModalCreationRecipe = function () {

        if(!$scope.isUserConnected ){
            $location.path('/connexion');
        }else{
            event.stopPropagation();
            var recipeType = $scope.recipeType;

            $log.warn("ON est entre dans [[[ ModalCreationRecipeCtrl]]] info : recipeTYpe : "+$scope.recipeType+"  -- ")

            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: '../../partials/recipe/modals/modalCreationRecipe.html',
                controller: 'ModalInstanceCreationRecipeCtrl',
                size: 'lg',
                resolve: {
                    recipeType: function () {
                        return recipeType;
                    },
                    idUser: function(){
                        return $scope.userConnected.id
                    }
                }
            });

            modalInstance.result.then(function () {
                $log.debug("Creation recette validé");
            }, function () {
                $log.info('Modal dismissed at: ' + new Date());
            });
        }

    };

}]);

// Please note that $uibModalInstance represents a modal window (instance) dependency.
// It is not the same as the $uibModal service used above.

myModule.controller('ModalInstanceCreationRecipeCtrl', function ($scope, $log, $uibModalInstance, recipeType, idUser, restRecipeService, restFoodService, units, AppendixFunctionsService) {


    $log.debug("[ ModalInstanceCreationRecipeCtrl] info IDUSER : "+idUser)

    /**************************************************************************************************************/
    /**************************************** LOADING DATA NEEDED ********************************************/
    /**************************************************************************************************************/
    var recipeTypeName = recipeType;
    var idRecipeType = restRecipeService.getIdRecipeType (recipeTypeName);
    $scope.categories = restRecipeService.getCategories(recipeTypeName);
    /*.filter(function(obj) {
        return obj.idRecipeType == idRecipeType;
    })*/
    $scope.origins = restRecipeService.getOrigins()
    $scope.foods = restFoodService.getFoods();

    $scope.recipe =  {
        name:'',
        recipeType:{idType:restRecipeService.getIdRecipeType(recipeTypeName),nameType:recipeTypeName},
        user:{id:idUser, pseudo:'', email:''}, /* <---------- idUser A AFFINER ----------------------- */
        nbPerson:4,
        ingredients:[{qty:1, unit:'g', food:{"id":-1,"name":"","idCategory":1,"isValidated":false}}],
        descriptions:[{name:"",numDescrip:1}],
        origin:$scope.origins[0],
        categories:[],
        timeCooking:10,
        timePreparation:12,
        isPublic: true,
        nbVoter:0,
        ratingUser:0
    };
    /**************************************************************************************************************/
    /**************************************** end LOADING DATA NEEDED ********************************************/
    /**************************************************************************************************************/

    $scope.displayTitleCreationRecipeType = AppendixFunctionsService.displayTitleCreationRecipeType(recipeTypeName);

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };


    $scope.triedCreate = false;
    $scope.enablePopoverNewSpeciality = true;



    /************************************************************************************************** RESTE : not check yet ****************************************************************************/
    /************************************************************************************************** RESTE : not check yet ****************************************************************************/
    /************************************************************************************************** RESTE : not check yet ****************************************************************************/
    /************************************************************************************************** RESTE : not check yet ****************************************************************************/

    $scope.cutEnable = function(){
        $log.debug("youpiiiii")
        $scope.enablePopoverNewSpeciality = false; $scope.enablePopoverNewSpeciality=true;
    }
    $scope.isEnable = function(){

        return ($scope.newSpeciality =! undefined) && ($scope.newSpeciality =! '');
    }

    /******************************************** CREER NEW SPECIALITY ***************************************************/
    $scope.creatingNewSpeciality = false;
    $scope.createNewSpeciality = function(newSpecialityName){
        if(newSpecialityName != '' && newSpecialityName != undefined){
            restRecipeService.createNewSpeciality(newSpecialityName, idUser).then(function (data) {
                var newIdSpeciality = data.id;
                if(newIdSpeciality != -1){
                    var newSpecialty = {"id": newIdSpeciality, "name": newSpecialityName, "numRank": 0}
                    $scope.origins.push(newSpecialty);
                    $scope.recipe.origin = newSpecialty;
                }else{$log.error("speciality '"+newSpecialityName+"' didnt created (id = -1)")}
                $scope.newSpecialityName = '';
            })
        }
        //CLOSE popover : enbale to false close le popover puis on enbale=true pr pouvoir l'utiliser de nouveau!
        //$scope.enablePopoverNewSpeciality = false;
        //$scope.enablePopoverNewSpeciality=true;
        $scope.creatingNewSpeciality = false;
    }
    $scope.pressEnterCreateSpeciality = function(event, newSpecialityName){
        event.stopPropagation();
        event.preventDefault();
        if(event.keyCode == 13){
            $scope.createNewSpeciality(newSpecialityName);
        }
    }
    /******************************************** end CREER NEW SPECIALITY ***************************************************/

    /******************************************** CREER NEW CATEGORY ***************************************************/
    $scope.creatingNewCategory = false;
    $scope.createNewCategory = function(newCategoryName){
        if(newCategoryName != '' && newCategoryName != undefined){
            restRecipeService.createNewCategory(newCategoryName, idRecipeType, idUser).then(function (data) {
                var newIdCategory = data.id;
                if(newIdCategory != -1){
                    var newCategory = {"id": newIdCategory, "name": newCategoryName, "numRank": 0, "idRecipeType":idRecipeType, "checked":true}
                    $scope.categories.push(newCategory);
                    $scope.recipe.categories.push(newCategory);
                }else{$log.error("category '"+newCategoryName+"' didnt created (id = -1)")}
                $scope.newCategoryName = '';
            })
        }
        $scope.creatingNewCategory = false;
    }
    $scope.pressEnterCreateCategory = function(event, newCategoryName){
        event.stopPropagation();
        event.preventDefault();
        if(event.keyCode == 13){
            $scope.createNewCategory(newCategoryName);
        }
    }
    /******************************************** end CREER NEW CATEGORY ***************************************************/

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

    $scope.createRecipe = function(isFormValid){

        if(isFormValid){
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
            restRecipeService.createRecipe($scope.recipe);


            /* IMAGE */
            var file = $scope.picFile;
            if($scope.picFile != null){
                restRecipeService.sendImage($scope.picFile);
            }
            /*switch(recipeTypeName){
             case 'starter' : RecipeService.addStarter(recipe); break;
             case 'course' :  RecipeService.addCourse(recipe); break;
             case 'dessert' : RecipeService.addDessert(recipe); break;
             case 'breakfast' : RecipeService.addBreakfast(recipe); break;
             case 'cocktail' : RecipeService.addCocktail(recipe); break;

             }*/
            //$location.path("/recipe/"+recipeTypeName);//$location.hash(recipe.id);
            $uibModalInstance.close();
        }
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

    //UPLOAD IMAGE
    $scope.uploadPic = function(file) {
        file.upload = Upload.upload({
            url: 'upload/url',
            data: {file: file}


        });
        console.dir(file)

        file.upload.then(function (response) {
            alert(response.data);
            $timeout(function () {
                file.result = response.data;
            });
        }, function (response) {
            if (response.status > 0)
                $scope.errorMsg = response.status + ': ' + response.data;
        }, function (evt) {
            // Math.min is to fix IE which reports 200% sometimes
            file.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
        });
    }


    // upload on file select or drop
    $scope.upload = function (file) {
        Upload.upload({
            url: 'https://angular-file-upload-cors-srv.appspot.com/upload',
            data: {file: file}
        }).then(function (resp) {
            console.log('Success ' + resp.config.data.file.path + 'uploaded. Response: ' + resp.data);
        }, function (resp) {
            console.log('Error status: ' + resp.status);
        }, function (evt) {
            var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
            console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
        });
    };

});
