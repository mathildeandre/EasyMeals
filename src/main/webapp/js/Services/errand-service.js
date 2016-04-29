/**
 * Created by fabien on 12/04/2016.
 */

var myService = angular.module('services');
myService.service('ErrandService', function() {

    var ingredientCategories0 = [
        {id:0, name:'Autre', ingredients:[{qty:50, unit:"g", food:"patates", rayonId:0}]},
        {id:1, name:'Boucherie', ingredients:[ {qty:500, unit:"g", food:"steack", rayonId:0}]},
        {id:2, name:'Poissonnerie', ingredients:[ {qty:250, unit:"g", food:"cabillaud", rayonId:0},{qty:250, unit:"g", food:"saumon", rayonId:0}, {qty:400, unit:"g", food:"crevette", rayonId:0}]},
        {id:3, name:'Epicerie', ingredients:[]},
        {id:4, name:'Surgeles', ingredients:[]},
        {id:5, name:'Boulangerie', ingredients:[]},
        {id:6, name:'Fruit/Legumes', ingredients:[]},
        {id:7, name:'Frais', ingredients:[]}
    ];
    var ingredientCategories1 = [
        {id:0, name:'Autre', ingredients:[{qty:50, unit:"g", food:"ski", rayonId:0}]},
        {id:1, name:'Boucherie', ingredients:[]},
        {id:2, name:'Poissonnerie', ingredients:[]},
        {id:3, name:'Epicerie', ingredients:[]},
        {id:4, name:'Surgeles', ingredients:[]},
        {id:5, name:'Boulangerie', ingredients:[]},
        {id:6, name:'Fruit/Legumes', ingredients:[]},
        {id:7, name:'Frais', ingredients:[]}
    ];
    var ingredientCategories2 = [
        {id:0, name:'Autre', ingredients:[]},
        {id:1, name:'Boucherie', ingredients:[]},
        {id:2, name:'Poissonnerie', ingredients:[]},
        {id:3, name:'Epicerie', ingredients:[]},
        {id:4, name:'Surgeles', ingredients:[{qty:50, unit:"g", food:"kids", rayonId:0}]},
        {id:5, name:'Boulangerie', ingredients:[]},
        {id:6, name:'Fruit/Legumes', ingredients:[]},
        {id:7, name:'Frais', ingredients:[]}
    ];
    var lists = [
        {id:0, name:"holidays week june"},
        {id:1, name:"ski week 2016"},
        {id:2, name:"kids october"}
    ]


    getLists = function(){
        return lists;
    }

    changeViewList = function(id){
        if(id == 0){
            return ingredientCategories0;
        }else if(id == 1){
            return ingredientCategories1;
        }else if(id == 2){
            return ingredientCategories2;
        }
    }
    getIngrCategories = function(){
        return ingredientCategories0;
    }
    setIngrCategories = function(ingrCategories){
        ingredientCategories0 = [];
        for(var i=0; i<ingrCategories.length; i++){
            ingredientCategories0.push(ingrCategories[i]);
        }
    }






    /*
     //fourWeekMeals = [aWeekMealLunch, aWeekMealDinner, .., ..]
     //aWeekMeal = {typeMeal: lunch, show:true, weekMeals:[meal1, meal2, ..., meal7]}
     //meal = {id: lunch4, nbPers:5 , recipes:[recipe1, recipe2, ...]} //ex lunch of thursday
     //recipe =  {id:'1',name:'burger',recipeType:'course',nbPerson:4,ingredients:[{qty:400, unit:'g', food:'steak'},{qty:4, unit:'', food:'bread'}],description:'faire des burgers'}
     */


    var planning =
        [{"id":0,"typeMeal":"breakfast","show":false,"weekMeals":
            [{"id":"breakfast0","nbPers":4,"recipes":[]},
                {"id":"breakfast1","nbPers":4,"recipes":[]},
                {"id":"breakfast2","nbPers":4,"recipes":[]},
                {"id":"breakfast3","nbPers":4,"recipes":[]},
                {"id":"breakfast4","nbPers":4,"recipes":[]},
                {"id":"breakfast5","nbPers":4,"recipes":[]},
                {"id":"breakfast6","nbPers":4,"recipes":[]}]},
            {"id":1,"typeMeal":"lunch","show":true,"weekMeals":
                [{"id":"lunch0","nbPers":4,"recipes":[]},
                    {"id":"lunch1","nbPers":4,"recipes":[]},
                    {"id":"lunch2","nbPers":4,"recipes":[
                        {"id":6,"name":"Burgers Maison","pixName":"burgersMaison.jpeg","recipeType":"course","nbPerson":5,"ingredients":[
                            {"qty":"10","unit":"","food":"pains burgers","rayonId":5},
                            {"qty":"5","unit":"","food":"tomate","rayonId":6},
                            {"qty":"100","unit":"g","food":"fromage rapé","rayonId":7},
                            {"qty":"10","unit":"","food":"steack","rayonId":1},
                            {"qty":"1","unit":"","food":"sauce burger","rayonId":3},
                            {"qty":"1","unit":"","food":"salade","rayonId":6}],
                            "description":"Faire cuire les steacks hachés sans matière grasse dans une poele avec un couvercle. \nPendant ce temps, coupez les tomates" +
                            " en tranches fines et lavez la salade si besoin.Lorsque les steacks sont prêts, préparez les burgers:Déposez les tranches de pains burgers" +
                            " dans une assiète. Mettre du fromage sur les deux côtés du pain. Les faire chauffer au micro onde 30 secondes. Recouvrez les de sauce burger." +
                            " Mettez le steack, 3 tranches de tomates et une feuille de salade.",
                            "descriptionOpen":false,
                            "origin":"Americain",
                            "categories":["viande","four","Facile"],
                            "isFavorite":true,
                            "isForPlanning":true,
                            "rating":5}]},
                    {"id":"lunch3","nbPers":4,"recipes":[]},
                    {"id":"lunch4","nbPers":4,"recipes":[]},
                    {"id":"lunch5","nbPers":4,"recipes":[]},
                    {"id":"lunch6","nbPers":4,"recipes":[]}]},
            {"id":2,"typeMeal":"snack","show":false,"weekMeals":
                [{"id":"snack0","nbPers":4,"recipes":[]},
                    {"id":"snack1","nbPers":4,"recipes":[]},
                    {"id":"snack2","nbPers":4,"recipes":[]},
                    {"id":"snack3","nbPers":4,"recipes":[]},
                    {"id":"snack4","nbPers":4,"recipes":[]},
                    {"id":"snack5","nbPers":4,"recipes":[]},
                    {"id":"snack6","nbPers":4,"recipes":[]}]},
            {"id":3,"typeMeal":"dinner","show":true,"weekMeals":
                [{"id":"dinner0","nbPers":4,"recipes":[]},
                    {"id":"dinner1","nbPers":4,"recipes":[]},
                    {"id":"dinner2","nbPers":4,"recipes":[]},
                    {"id":"dinner3","nbPers":4,"recipes":
                        [{"id":2,"name":"Cabillaud au Four","pixName":"cabillaudFour.jpg","recipeType":"course","nbPerson":2,"ingredients":
                            [{"qty":0.3,"unit":"kg","food":"aubergine","rayonId":6},
                                {"qty":200,"unit":"g","food":"cabillaud","rayonId":2}],
                            "description":"le cabillaud c le meilleur",
                            "descriptionOpen":false,
                            "origin":"Francais",
                            "categories":["poisson","legume","four"],
                            "isFavorite":true,
                            "isForPlanning":true,
                            "rating":2}]},
                    {"id":"dinner4","nbPers":4,"recipes":[]},
                    {"id":"dinner5","nbPers":4,"recipes":[]},
                    {"id":"dinner6","nbPers":4,"recipes":[]}]}] ;


    getPlanning = function(){
        return planning;
    }
    setPlanning = function(myPlanning){
        planning= myPlanning;
    }



    return {
        getLists: getLists,
        changeViewList: changeViewList,
        getIngrCategories: getIngrCategories,
        setIngrCategories: setIngrCategories,
        getPlanning: getPlanning,
        setPlanning: setPlanning
    };
})

    /*
    .constant('fourTypeMeal',
        ['breakfast', 'lunch', 'snack', 'dinner']
    );
    */
