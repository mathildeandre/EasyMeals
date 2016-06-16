

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
INSERT INTO Food(name, idCategory, isValidated) VALUES ('steack', 2, 1);
INSERT INTO Food(name, idCategory, isValidated) VALUES ('steack haché', 2, 1);
INSERT INTO Food(name, idCategory, isValidated) VALUES ('dinde', 2, 1);
INSERT INTO Food(name, idCategory, isValidated) VALUES ('filet boeuf', 2, 1);
INSERT INTO Food(name, idCategory, isValidated) VALUES ('jambon', 2, 1);

INSERT INTO Food(name, idCategory, isValidated) VALUES ('saumon', 3, 1);
INSERT INTO Food(name, idCategory, isValidated) VALUES ('cabillaud', 3, 1);
INSERT INTO Food(name, idCategory, isValidated) VALUES ('crevette', 3, 1);
INSERT INTO Food(name, idCategory, isValidated) VALUES ('poisson pané', 3, 1);
INSERT INTO Food(name, idCategory, isValidated) VALUES ('thon', 3, 1);

INSERT INTO Food(name, idCategory, isValidated) VALUES ('tomate', 4, 1);
INSERT INTO Food(name, idCategory, isValidated) VALUES ('salade', 4, 1);

INSERT INTO Food(name, idCategory, isValidated) VALUES ('pain', 5, 1);
INSERT INTO Food(name, idCategory, isValidated) VALUES ('pain à burger', 5, 1);
INSERT INTO Food(name, idCategory, isValidated) VALUES ('pain aux céréales', 5, 1);
INSERT INTO Food(name, idCategory, isValidated) VALUES ('croissant', 5, 1);
INSERT INTO Food(name, idCategory, isValidated) VALUES ('pain au chocolat', 5, 1);

INSERT INTO Food(name, idCategory, isValidated) VALUES ('farine', 6, 1);
INSERT INTO Food(name, idCategory, isValidated) VALUES ('lait', 6, 1);
INSERT INTO Food(name, idCategory, isValidated) VALUES ('oeuf', 6, 1);

INSERT INTO Food(name, idCategory, isValidated) VALUES ('quenelle', 7, 1);

INSERT INTO Food(name, idCategory, isValidated) VALUES ('fromage rapé', 8, 1);
INSERT INTO Food(name, idCategory, isValidated) VALUES ('comté', 8, 1);
INSERT INTO Food(name, idCategory, isValidated) VALUES ('gruyère', 8, 1);
INSERT INTO Food(name, idCategory, isValidated) VALUES ('yaourt nature', 8, 1);
INSERT INTO Food(name, idCategory, isValidated) VALUES ('reblochon', 8, 1);


INSERT INTO Food(name, idCategory, isValidated) VALUES ('pomme noisette', 9, 1);
INSERT INTO Food(name, idCategory, isValidated) VALUES ('pizza', 9, 1);

INSERT INTO Food(name, idCategory, isValidated) VALUES ('pate macaroni', 11, 1);
INSERT INTO Food(name, idCategory, isValidated) VALUES ('riz basmati', 11, 1);
INSERT INTO Food(name, idCategory, isValidated) VALUES ('crêpe à burritos', 13, 1);

INSERT INTO Food(name, idCategory, isValidated) VALUES ('sauce burger', 14, 1);


/* SPECIALITES */
INSERT INTO Recipe_Origin(name, numRank) VALUES ('français',1); /*id 1*/
INSERT INTO Recipe_Origin(name, numRank) VALUES ('italien',2);
INSERT INTO Recipe_Origin(name, numRank) VALUES ('thai',3);
INSERT INTO Recipe_Origin(name, numRank) VALUES ('indien',4);
INSERT INTO Recipe_Origin(name, numRank) VALUES ('americain',5);
INSERT INTO Recipe_Origin(name, numRank) VALUES ('mexicain',6);


INSERT INTO Recipe_Type(name) VALUES ('starter'); /*id 1*/
INSERT INTO Recipe_Type(name) VALUES ('course');
INSERT INTO Recipe_Type(name) VALUES ('dessert');
INSERT INTO Recipe_Type(name) VALUES ('breakfast');
INSERT INTO Recipe_Type(name) VALUES ('cocktail');

INSERT INTO Recipe_Category(name, numRank, idRecipeType) VALUES ('viande', 1, 2);
INSERT INTO Recipe_Category(name, numRank, idRecipeType) VALUES ('four', 5, 2);
INSERT INTO Recipe_Category(name, numRank, idRecipeType) VALUES ('legume', 3, 2);
INSERT INTO Recipe_Category(name, numRank, idRecipeType) VALUES ('poisson', 2, 2);
INSERT INTO Recipe_Category(name, numRank, idRecipeType) VALUES ('vegetarien', 4, 2);
INSERT INTO Recipe_Category(name, numRank, idRecipeType) VALUES ('poêle', 6, 2);
INSERT INTO Recipe_Category(name, numRank, idRecipeType) VALUES ('sucré salé', 7, 2);
INSERT INTO Recipe_Category(name, numRank, idRecipeType) VALUES ('gratin', 8, 2);

INSERT INTO Recipe_Category(name, numRank, idRecipeType) VALUES ('chocolat', 1, 3);
INSERT INTO Recipe_Category(name, numRank, idRecipeType) VALUES ('four', 5,3);
INSERT INTO Recipe_Category(name, numRank, idRecipeType) VALUES ('fruits', 3, 3);

INSERT INTO Recipe_Category(name, numRank, idRecipeType) VALUES ('feuillete', 1, 1);
INSERT INTO Recipe_Category(name, numRank, idRecipeType) VALUES ('salade', 5,1);
INSERT INTO Recipe_Category(name, numRank, idRecipeType) VALUES ('cake', 3, 1);


/*
INSERT INTO `User`(pseudo, pwd) VALUES ('common', 'common'); /*id 1*/
INSERT INTO `User`(pseudo, pwd, isAdmin) VALUES ('fab', 'fab', 1); /*1d 2 */
INSERT INTO `User`(id, pseudo, pwd) VALUES (2117, 'mathou', 'mathou'); /*1d 2 */



/* BURGER maison*/
INSERT INTO Recipe(name, idType, isPublic,  idOwner, nbPerson,  pixName,  idOrigin, rating, nbVoter, isValidated, timeCooking, timePreparation) VALUES ('Burgers Maison', 2, 1, 2117, 4, 'burgersMaison.jpeg', 5, 3.7, 10, 1, 15, 20); /*id 1 */

INSERT INTO Rel_Recipe_Category(idRecipe, idCategory) VALUES (1, 1);
INSERT INTO Rel_Recipe_Category(idRecipe, idCategory) VALUES (1, 2);

INSERT INTO Recipe_Description(idRecipe, numDescription, description) VALUES (1, 1, 'cuire steack à la poêle');
INSERT INTO Recipe_Description(idRecipe, numDescription, description) VALUES (1, 2, 'preparer salade,couper tomates en rondelle et ouvrir les pains à burger et mettre du fromage sur chaque partie');
INSERT INTO Recipe_Description(idRecipe, numDescription, description) VALUES (1, 3, 'quand les steack sont pret, mettre pains au four 2min');
INSERT INTO Recipe_Description(idRecipe, numDescription, description) VALUES (1, 4, 'tout est pret, mettre sauce au choix sur chaque partie du pain, steack, tomate salade');
INSERT INTO Recipe_Description(idRecipe, numDescription, description) VALUES (1, 5, 'Votre burger est pret !');

INSERT INTO Ingredient(idRecipe, idFood, quantity, unit) VALUES (1, 14, 8, '');
INSERT INTO Ingredient(idRecipe, idFood, quantity, unit) VALUES (1, 32, 1, '');
INSERT INTO Ingredient(idRecipe, idFood, quantity, unit) VALUES (1, 2, 4, '');
INSERT INTO Ingredient(idRecipe, idFood, quantity, unit) VALUES (1, 11, 3, '');
INSERT INTO Ingredient(idRecipe, idFood, quantity, unit) VALUES (1, 22, 100, 'g');
INSERT INTO Ingredient(idRecipe, idFood, quantity, unit) VALUES (1, 12, 1, '');

/* BURRITOS*/
INSERT INTO Recipe(name, idType, isPublic,  idOwner, nbPerson,  pixName,  idOrigin, rating, nbVoter, isValidated, timeCooking, timePreparation) VALUES ('Burritos', 2, 1, 2117, 4, 'burritos.jpg', 6, 4.2, 18, 1, 10, 15);

INSERT INTO Rel_Recipe_Category(idRecipe, idCategory) VALUES (2, 1);
INSERT INTO Rel_Recipe_Category(idRecipe, idCategory) VALUES (2, 3);

INSERT INTO Recipe_Description(idRecipe, numDescription, description) VALUES (2, 1, 'faire cuire steack');
INSERT INTO Recipe_Description(idRecipe, numDescription, description) VALUES (2, 2, 'mettre dans crepe avec legumes');
INSERT INTO Recipe_Description(idRecipe, numDescription, description) VALUES (2, 3, 'Votre burritos est pret !');

INSERT INTO Ingredient(idRecipe, idFood, quantity, unit) VALUES (2, 11, 3, '');
INSERT INTO Ingredient(idRecipe, idFood, quantity, unit) VALUES (2, 22, 100, 'g');
INSERT INTO Ingredient(idRecipe, idFood, quantity, unit) VALUES (2, 2, 500, 'g');
INSERT INTO Ingredient(idRecipe, idFood, quantity, unit) VALUES (2, 31, 8, '');
INSERT INTO Ingredient(idRecipe, idFood, quantity, unit) VALUES (2, 12, 1, '');

*/