

/* FOOD CATEGORY */
INSERT INTO Food_Category( name, numRank) VALUES ('Autre', 1);
INSERT INTO Food_Category( name, numRank) VALUES ('Viande',2);
INSERT INTO Food_Category( name, numRank) VALUES ('Poisson',3);
INSERT INTO Food_Category( name, numRank) VALUES ('Fruit/Legume',4);
INSERT INTO Food_Category( name, numRank) VALUES ('Pain/Viennoiserie/Patisserie',5);
INSERT INTO Food_Category( name, numRank) VALUES ('Farine/Lait/Oeuf',6);
INSERT INTO Food_Category( name, numRank) VALUES ('Frais',7);
INSERT INTO Food_Category( name, numRank) VALUES ('Fromage/Yaourt',8);
INSERT INTO Food_Category( name, numRank) VALUES ('Surgelé',9);
INSERT INTO Food_Category( name, numRank) VALUES ('Petit dej/Biscuit',10);
INSERT INTO Food_Category( name, numRank) VALUES ('Epicerie Pate/Riz',11);
INSERT INTO Food_Category( name, numRank) VALUES ('Epicerie Conserve',12);
INSERT INTO Food_Category( name, numRank) VALUES ('Epicerie Produit du monde',13);
INSERT INTO Food_Category( name, numRank) VALUES ('Epicerie Condiment',14);
INSERT INTO Food_Category( name, numRank) VALUES ('Biscuit apéritif',15);
INSERT INTO Food_Category( name, numRank) VALUES ('Boisson',16);



/* FOOD */
INSERT INTO Food(name, idCategory) VALUES ('steack', 2);
INSERT INTO Food(name, idCategory) VALUES ('steack haché', 2);
INSERT INTO Food(name, idCategory) VALUES ('dinde', 2);
INSERT INTO Food(name, idCategory) VALUES ('filet boeuf', 2);
INSERT INTO Food(name, idCategory) VALUES ('jambon', 2);

INSERT INTO Food(name, idCategory) VALUES ('saumon', 3);
INSERT INTO Food(name, idCategory) VALUES ('cabillaud', 3);
INSERT INTO Food(name, idCategory) VALUES ('crevette', 3);
INSERT INTO Food(name, idCategory) VALUES ('poisson pané', 3);
INSERT INTO Food(name, idCategory) VALUES ('thon', 3);

INSERT INTO Food(name, idCategory) VALUES ('tomate', 4);
INSERT INTO Food(name, idCategory) VALUES ('salade', 4);

INSERT INTO Food(name, idCategory) VALUES ('pain', 5);
INSERT INTO Food(name, idCategory) VALUES ('pain à burger', 5);
INSERT INTO Food(name, idCategory) VALUES ('pain aux céréales', 5);
INSERT INTO Food(name, idCategory) VALUES ('croissant', 5);
INSERT INTO Food(name, idCategory) VALUES ('pain au chocolat', 5);

INSERT INTO Food(name, idCategory) VALUES ('farine', 6);
INSERT INTO Food(name, idCategory) VALUES ('lait', 6);
INSERT INTO Food(name, idCategory) VALUES ('oeuf', 6);

INSERT INTO Food(name, idCategory) VALUES ('quenelle', 7);

INSERT INTO Food(name, idCategory) VALUES ('fromage rapé', 8);
INSERT INTO Food(name, idCategory) VALUES ('comté', 8);
INSERT INTO Food(name, idCategory) VALUES ('gruyère', 8);
INSERT INTO Food(name, idCategory) VALUES ('yaourt nature', 8);
INSERT INTO Food(name, idCategory) VALUES ('reblochon', 8);


INSERT INTO Food(name, idCategory) VALUES ('pomme noisette', 9);
INSERT INTO Food(name, idCategory) VALUES ('pizza', 9);

INSERT INTO Food(name, idCategory) VALUES ('pate macaroni', 11);
INSERT INTO Food(name, idCategory) VALUES ('riz basmati', 11);
INSERT INTO Food(name, idCategory) VALUES ('crêpe à burritos', 13);

INSERT INTO Food(name, idCategory) VALUES ('sauce burger', 14);


/* SPECIALITES */
INSERT INTO Recipe_Origin(name, numRank) VALUES ('français',1); /*id 1*/
INSERT INTO Recipe_Origin(name, numRank) VALUES ('italien',2);
INSERT INTO Recipe_Origin(name, numRank) VALUES ('thai',3);
INSERT INTO Recipe_Origin(name, numRank) VALUES ('indien',4);
INSERT INTO Recipe_Origin(name, numRank) VALUES ('americain',5);
INSERT INTO Recipe_Origin(name, numRank) VALUES ('mexicain',6);


INSERT INTO Recipe_Category(name, numRank) VALUES ('viande', 1);
INSERT INTO Recipe_Category(name, numRank) VALUES ('four', 5);
INSERT INTO Recipe_Category(name, numRank) VALUES ('legume', 8);

INSERT INTO Recipe_Type(name) VALUES ('starter'); /*id 1*/
INSERT INTO Recipe_Type(name) VALUES ('course');
INSERT INTO Recipe_Type(name) VALUES ('dessert');
INSERT INTO Recipe_Type(name) VALUES ('breakfast');
INSERT INTO Recipe_Type(name) VALUES ('cocktail');



INSERT INTO `User`(pseudo, pwd) VALUES ('common', 'common'); /*id 1*/
INSERT INTO `User`(pseudo, pwd, isAdmin) VALUES ('fab', 'fab', 1); /*1d 2 */
INSERT INTO `User`(id, pseudo, pwd) VALUES (2117, 'mathou', 'mathou'); /*1d 2 */



INSERT INTO Recipe(name, idType, isPublic,  idUser, nbPerson,  pixName,  idOrigin, rating, nbVoter, isValidated, timeCooking, timePreparation)
VALUES ('Burgers Maison', 2, 1, 2117, 5, 'burgersMaison.jpeg', 5, 3.7, 10, 1, 15, 20); /*id 1 */
INSERT INTO Recipe(name, idType, isPublic,  idUser, nbPerson,  pixName,  idOrigin, rating, nbVoter, isValidated, timeCooking, timePreparation)
VALUES ('Burritos', 2, 1, 2117, 4, 'burritos.jpg', 6, 4.2, 18, 1, 10, 15);


INSERT INTO Rel_Recipe_Category(idRecipe, idCategory) VALUES (1, 1);
INSERT INTO Rel_Recipe_Category(idRecipe, idCategory) VALUES (1, 2);
INSERT INTO Rel_Recipe_Category(idRecipe, idCategory) VALUES (2, 1);
INSERT INTO Rel_Recipe_Category(idRecipe, idCategory) VALUES (2, 3);


INSERT INTO Recipe_Description(idRecipe, numDescription, description) VALUES (1, 1, 'cuire steack à la poêle');
INSERT INTO Recipe_Description(idRecipe, numDescription, description) VALUES (1, 2, 'preparer salade,couper tomates en rondelle et ouvrir les pains à burger et mettre du fromage sur chaque partie');
INSERT INTO Recipe_Description(idRecipe, numDescription, description) VALUES (1, 3, 'quand les steack sont pret, mettre pains au four 2min');
INSERT INTO Recipe_Description(idRecipe, numDescription, description) VALUES (1, 4, 'tout est pret, mettre sauce au choix sur chaque partie du pain, steack, tomate salade');
INSERT INTO Recipe_Description(idRecipe, numDescription, description) VALUES (1, 5, 'Votre burger est pret !');


INSERT INTO Recipe_Description(idRecipe, numDescription, description) VALUES (2, 1, 'faire cuire steack');
INSERT INTO Recipe_Description(idRecipe, numDescription, description) VALUES (2, 2, 'mettre dans crepe avec legumes');
INSERT INTO Recipe_Description(idRecipe, numDescription, description) VALUES (2, 3, 'Votre burritos est pret !');


/* INGREDIENTS */
INSERT INTO Ingredient(idRecipe, idFood, quantity, unit) VALUES (1, 14, 10, '');
INSERT INTO Ingredient(idRecipe, idFood, quantity, unit) VALUES (1, 11, 5, '');
INSERT INTO Ingredient(idRecipe, idFood, quantity, unit) VALUES (1, 22, 100, 'g');
INSERT INTO Ingredient(idRecipe, idFood, quantity, unit) VALUES (1, 2, 400, 'g');
INSERT INTO Ingredient(idRecipe, idFood, quantity, unit) VALUES (1, 31, 0.2, '');
INSERT INTO Ingredient(idRecipe, idFood, quantity, unit) VALUES (1, 12, 1, '');

INSERT INTO Ingredient(idRecipe, idFood, quantity, unit) VALUES (2, 31, 0.2, '');
INSERT INTO Ingredient(idRecipe, idFood, quantity, unit) VALUES (2, 32, 4, '');
INSERT INTO Ingredient(idRecipe, idFood, quantity, unit) VALUES (2, 2, 200, 'g');


/** LIST SHOPPING **/
INSERT INTO List_Shopping(name)VALUES ('myListShopping1');
INSERT INTO ListShopping_Category (idListShop, idFoodCategory) VALUES (1, 1); /* category autre*/
INSERT INTO ListShopping_Category (idListShop, idFoodCategory) VALUES (1, 3); /* category poisson*/
INSERT INTO ListShopping_Category (idListShop, idFoodCategory) VALUES (1, 4); /* category fruit legume*/

INSERT INTO Ingredient_ListShop (idFood, idListShopCategory, quantity, unit) VALUES (4, 1, 70, 'g');
INSERT INTO Ingredient_ListShop (idFood, idListShopCategory, quantity, unit) VALUES (18, 1, 1, 'kg');
INSERT INTO Ingredient_ListShop (idFood, idListShopCategory, quantity, unit) VALUES (8, 2, 50, 'g');
INSERT INTO Ingredient_ListShop (idFood, idListShopCategory, quantity, unit) VALUES (11, 3, 4, '');
INSERT INTO Ingredient_ListShop (idFood, idListShopCategory, quantity, unit) VALUES (12, 3, 1, '');


INSERT INTO List_Shopping(name)VALUES ('myListShopping2');
INSERT INTO ListShopping_Category (idListShop, idFoodCategory) VALUES (2, 1); /* category autre*/
INSERT INTO ListShopping_Category (idListShop, idFoodCategory) VALUES (2, 3); /* category poisson*/
INSERT INTO ListShopping_Category (idListShop, idFoodCategory) VALUES (2, 4); /* category fruit legume*/

INSERT INTO Ingredient_ListShop (idFood, idListShopCategory, quantity, unit) VALUES (4, 4, 99, 'g');
INSERT INTO Ingredient_ListShop (idFood, idListShopCategory, quantity, unit) VALUES (18, 4, 99, 'kg');
INSERT INTO Ingredient_ListShop (idFood, idListShopCategory, quantity, unit) VALUES (8, 5, 99, 'g');
INSERT INTO Ingredient_ListShop (idFood, idListShopCategory, quantity, unit) VALUES (11, 6, 99, '');
INSERT INTO Ingredient_ListShop (idFood, idListShopCategory, quantity, unit) VALUES (12, 6, 99, '');



/*****************************************************************************************/
/*****************************************************************************************/
/******************** NO USE ANYMORE ********************************************************/
/*****************************************************************************************/
/** PLANNING **/
INSERT INTO Planning (name, idUser )VALUES ('myPlanning1', 2);

INSERT INTO Planning_WeekMeal (weekMealName, showWeekMeal, idPlanning )VALUES ('lunch', 1, 1); /*id = 1*/
INSERT INTO Planning_WeekMeal (weekMealName, showWeekMeal, idPlanning )VALUES ('dinner', 1, 1);

INSERT INTO Planning_CaseMeal (noDay, nbPers, idPlanningWeekMeal)VALUES (2, 17, 1); /* 2 -> mercredi, 17pers, lunch */
INSERT INTO Planning_CaseMeal (noDay, nbPers, idPlanningWeekMeal)VALUES (0, 5, 2); /* 2 -> lundi, 5pers, dinner */

INSERT INTO Rel_Recipe_CaseMealPlanning (idRecipe, idPlanningCaseMeal)VALUES (1,1);
INSERT INTO Rel_Recipe_CaseMealPlanning (idRecipe, idPlanningCaseMeal)VALUES (1,2);
INSERT INTO Rel_Recipe_CaseMealPlanning (idRecipe, idPlanningCaseMeal)VALUES (2,2);


/** LISTSHOP PLANNING USER **/
INSERT INTO ListShopPlanning_User (name, idListShop, idPlanning, idUser )VALUES ('listShopPlanning_corsica', 1, 1, 2);
INSERT INTO ListShopPlanning_User (name, idListShop, idPlanning, idUser )VALUES ('listShopPlanning_no2', 2, 1, 2);


/*****************************************************************************************/



INSERT INTO Food(id, name, idCategory, isValidated) VALUES (?, ?, ?, ?);
INSERT INTO Food_Category(id, name, numRank) VALUES (?, ?, ?);
INSERT INTO Food_Recipe(id, name, idType, idFood, quantity, unit, nbPerson, pixName, idOrigin) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);
INSERT INTO Ingredient(idRecipe, idFood, quantity, unit) VALUES (?, ?, ?, ?);
INSERT INTO Ingredient_Custom(idRecipe, idFood, idUser, quantity, unit, isHide) VALUES (?, ?, ?, ?, ?, ?);
INSERT INTO Ingredient_ListShop(idFood, idListShopCategory, quantity, unit) VALUES (?, ?, ?, ?);
INSERT INTO List_Shopping(id, name) VALUES (?, ?);
INSERT INTO ListShopping_Category(id, idListShop, idFoodCategory) VALUES (?, ?, ?);
INSERT INTO ListShopPlanning_User(id, name, `date`, idListShop, idPlanning, idUser) VALUES (?, ?, ?, ?, ?, ?);
INSERT INTO Planning(id, lastOpen, name, idUser) VALUES (?, ?, ?, ?);
INSERT INTO Planning_CaseMeal(id, noDay, nbPers, idPlanningWeekMeal) VALUES (?, ?, ?, ?);
INSERT INTO Planning_WeekMeal(id, weekMealName, showWeekMeal, idPlanning) VALUES (?, ?, ?, ?);
INSERT INTO Recipe(id, name, idType, isPublic, idUser, rating, nbVoter, nbPerson, pixName, idOrigin, isValidated) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
INSERT INTO Recipe_Category(id, name, numRank) VALUES (?, ?, ?);
INSERT INTO Recipe_Description(idRecipe, numDescription, description) VALUES (?, ?, ?);
INSERT INTO Recipe_Origin(id, name, numRank) VALUES (?, ?, ?);
INSERT INTO Recipe_Type(id, name) VALUES (?, ?);
INSERT INTO Rel_Food_Category(idCategory, idFoodRecipe) VALUES (?, ?);
INSERT INTO Rel_Recipe_CaseMealPlanning(idRecipe, idPlanningCaseMeal, nbPers) VALUES (?, ?, ?);
INSERT INTO Rel_Recipe_Category(idCategory, idRecipe) VALUES (?, ?);
INSERT INTO Rel_User_Recipe(idRecipe, idUser, isFavorite, isForPlanning, ratingUser, isHide) VALUES (?, ?, ?, ?, ?, ?);
INSERT INTO `User`(id, pseudo, pwd, email) VALUES (?, ?, ?, ?);

