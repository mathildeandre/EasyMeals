CREATE TABLE Food (id int(10) NOT NULL AUTO_INCREMENT, name varchar(255) NOT NULL, idCategory int(10) NOT NULL, isValidated tinyint DEFAULT 0 NOT NULL, PRIMARY KEY (id));
CREATE TABLE Food_Category (id int(10) NOT NULL AUTO_INCREMENT, name varchar(255) NOT NULL, numRank int(10), PRIMARY KEY (id));
CREATE TABLE Food_Recipe (id int(10) NOT NULL AUTO_INCREMENT, name varchar(255) NOT NULL, idType int(10) NOT NULL, idFood int(10) NOT NULL, quantity float(10,3) NOT NULL, unit varchar(255), nbPerson int(10) DEFAULT 1 NOT NULL, pixName varchar(255), idOrigin int(10) NOT NULL, PRIMARY KEY (id), INDEX (idType));
CREATE TABLE Ingredient (idRecipe int(10) NOT NULL, idFood int(10) NOT NULL, quantity float(10,3) NOT NULL, unit varchar(255), PRIMARY KEY (idRecipe, idFood));
CREATE TABLE Ingredient_Custom (idRecipe int(10) NOT NULL, idFood int(10) NOT NULL, idUser int(10) NOT NULL, quantity float(10,3) NOT NULL, unit varchar(255), isHide tinyint(1) NOT NULL, PRIMARY KEY (idRecipe, idFood, idUser));
CREATE TABLE Ingredient_ListShop (nameFood varchar(255) NOT NULL, idListShopCategory int(10) NOT NULL, quantity float(10,3) NOT NULL, unit varchar(255), PRIMARY KEY (nameFood, idListShopCategory));
CREATE TABLE ListShopping_Category (id int(10) NOT NULL AUTO_INCREMENT, idFoodCategory int(10) NOT NULL, idPlanning int(10) NOT NULL, PRIMARY KEY (id), CONSTRAINT UniqueListShopCat UNIQUE (idFoodCategory, idPlanning));
CREATE TABLE Planning (id int(10) NOT NULL AUTO_INCREMENT, lastOpen tinyint DEFAULT false NOT NULL, name varchar(255) NOT NULL, idUser int(10) NOT NULL, nbPersGlobal int(10), isForListShop tinyint, PRIMARY KEY (id), INDEX (idUser));
CREATE TABLE Planning_CaseMeal (id int(10) NOT NULL AUTO_INCREMENT, numDay int(10) NOT NULL, nbPers int(10) NOT NULL, idPlanningWeekMeal int(10) NOT NULL, PRIMARY KEY (id));
CREATE TABLE Planning_WeekMeal (id int(10) NOT NULL AUTO_INCREMENT, weekMealName varchar(255) NOT NULL, showWeekMeal tinyint NOT NULL, idPlanning int(10) NOT NULL, PRIMARY KEY (id));
CREATE TABLE Recipe (id int(10) NOT NULL AUTO_INCREMENT, name varchar(255) NOT NULL, idType int(10) NOT NULL, isPublic tinyint NOT NULL, idOwner int(10) NOT NULL, rating float(2,1), nbVoter int(10) DEFAULT 0 NOT NULL, nbPerson int(10) DEFAULT 1 NOT NULL, pixName varchar(255), idOrigin int(10) NOT NULL, isValidated tinyint DEFAULT 0 NOT NULL, timeCooking int(10), timePreparation int(10), PRIMARY KEY (id), INDEX (idType), INDEX (isPublic), INDEX (idOwner));
CREATE TABLE Recipe_Category (id int(10) NOT NULL AUTO_INCREMENT, name varchar(255) NOT NULL, numRank int(10), idRecipeType int(10) NOT NULL, isValidatedRecipeCategory tinyint DEFAULT 0 NOT NULL, PRIMARY KEY (id));
CREATE TABLE Recipe_Description (idRecipe int(10) NOT NULL, numDescription int(10) NOT NULL, description varchar(255), PRIMARY KEY (idRecipe, numDescription));
CREATE TABLE Recipe_Origin (id int(10) NOT NULL AUTO_INCREMENT, name varchar(255) NOT NULL, numRank int(10), isValidatedRecipeOrigin tinyint DEFAULT 0 NOT NULL, PRIMARY KEY (id));
CREATE TABLE Recipe_Type (id int(10) NOT NULL AUTO_INCREMENT, name varchar(25) NOT NULL, PRIMARY KEY (id));
CREATE TABLE Rel_Food_Category (idCategory int(10) NOT NULL, idFoodRecipe int(10) NOT NULL, PRIMARY KEY (idCategory, idFoodRecipe));
CREATE TABLE Rel_Recipe_CaseMealPlanning (idRecipe int(10) NOT NULL, idPlanningCaseMeal int(10) NOT NULL, nbPers int(10), PRIMARY KEY (idRecipe, idPlanningCaseMeal));
CREATE TABLE Rel_Recipe_Category (idCategory int(10) NOT NULL, idRecipe int(10) NOT NULL, PRIMARY KEY (idCategory, idRecipe));
CREATE TABLE Rel_User_Recipe (idRecipe int(10) NOT NULL, idUser int(10) NOT NULL, isFavorite tinyint(1), isForPlanning tinyint(1), ratingUser int(10), isHide tinyint, PRIMARY KEY (idRecipe, idUser));
CREATE TABLE `User` (id int(10) NOT NULL AUTO_INCREMENT, pseudo varchar(255) NOT NULL, pwd varchar(255) NOT NULL, email varchar(255), isAdmin tinyint DEFAULT 0 NOT NULL, colorThemeRecipe varchar(25) DEFAULT 'grey' NOT NULL, PRIMARY KEY (id));

CREATE TABLE FriendWith (idUser1 int(10) NOT NULL, idUser2 int(10) NOT NULL, PRIMARY KEY (idUser1, idUser2));
CREATE TABLE Rel_User_RecipeCategory (idRecipeCategory int(10) NOT NULL, idUser int(10) NOT NULL, numRankRel int(10) NOT NULL, PRIMARY KEY (idRecipeCategory, idUser));
CREATE TABLE Rel_User_RecipeOrigin (idRecipe_Origin int(10) NOT NULL, idUser int(10) NOT NULL, numRankRel int(10) NOT NULL, PRIMARY KEY (idRecipe_Origin, idUser));




/* ON DELETE CASCADE for USER deleted*/
ALTER TABLE Planning ADD INDEX FKPlanning761199 (idUser), ADD CONSTRAINT FKPlanning761199 FOREIGN KEY (idUser) REFERENCES `User` (id) ON DELETE CASCADE; /*'on delete cascade' veut dire que si on delete un user, tous les planning avec cet idUser correspondant sera deleted*/

/* ON DELETE CASCADE for PLANNING deleted*/
ALTER TABLE Planning_WeekMeal ADD INDEX FKPlanning_W938841 (idPlanning), ADD CONSTRAINT FKPlanning_W938841 FOREIGN KEY (idPlanning) REFERENCES Planning (id) ON DELETE CASCADE;
ALTER TABLE Planning_CaseMeal ADD INDEX FKPlanning_C546459 (idPlanningWeekMeal), ADD CONSTRAINT FKPlanning_C546459 FOREIGN KEY (idPlanningWeekMeal) REFERENCES Planning_WeekMeal (id) ON DELETE CASCADE;
ALTER TABLE Rel_Recipe_CaseMealPlanning ADD INDEX FKRel_Recipe36474 (idPlanningCaseMeal), ADD CONSTRAINT FKRel_Recipe36474 FOREIGN KEY (idPlanningCaseMeal) REFERENCES Planning_CaseMeal (id) ON DELETE CASCADE;


ALTER TABLE ListShopping_Category ADD INDEX FKListShoppi165950 (idPlanning), ADD CONSTRAINT FKListShoppi165950 FOREIGN KEY (idPlanning) REFERENCES Planning (id) ON DELETE CASCADE;
ALTER TABLE Ingredient_ListShop ADD INDEX FKIngredient293299 (idListShopCategory), ADD CONSTRAINT FKIngredient293299 FOREIGN KEY (idListShopCategory) REFERENCES ListShopping_Category (id) ON DELETE CASCADE;

/* ON DELETE CASCADE for FOOD deleted*/
ALTER TABLE Food_Recipe ADD INDEX FKFood_Recip918613 (idFood), ADD CONSTRAINT FKFood_Recip918613 FOREIGN KEY (idFood) REFERENCES Food (id) ON DELETE CASCADE;
ALTER TABLE Ingredient ADD INDEX FKIngredient896373 (idFood), ADD CONSTRAINT FKIngredient896373 FOREIGN KEY (idFood) REFERENCES Food (id) ON DELETE CASCADE;
ALTER TABLE Ingredient_Custom ADD INDEX FKIngredient711221 (idFood), ADD CONSTRAINT FKIngredient711221 FOREIGN KEY (idFood) REFERENCES Food (id) ON DELETE CASCADE;

/* ON DELETE CASCADE for RECIPE deleted*/
ALTER TABLE Recipe_Description ADD INDEX FKRecipe_Des390294 (idRecipe), ADD CONSTRAINT FKRecipe_Des390294 FOREIGN KEY (idRecipe) REFERENCES Recipe (id) ON DELETE CASCADE;
ALTER TABLE Rel_Recipe_Category ADD INDEX FKRel_Recipe163667 (idRecipe), ADD CONSTRAINT FKRel_Recipe163667 FOREIGN KEY (idRecipe) REFERENCES Recipe (id) ON DELETE CASCADE;
ALTER TABLE Rel_User_Recipe ADD INDEX FKRel_User_R252853 (idRecipe), ADD CONSTRAINT FKRel_User_R252853 FOREIGN KEY (idRecipe) REFERENCES Recipe (id) ON DELETE CASCADE;
ALTER TABLE Ingredient ADD INDEX FKIngredient523181 (idRecipe), ADD CONSTRAINT FKIngredient523181 FOREIGN KEY (idRecipe) REFERENCES Recipe (id) ON DELETE CASCADE;
ALTER TABLE Ingredient_Custom ADD INDEX FKIngredient130777 (idRecipe), ADD CONSTRAINT FKIngredient130777 FOREIGN KEY (idRecipe) REFERENCES Recipe (id) ON DELETE CASCADE;
ALTER TABLE Rel_Recipe_CaseMealPlanning ADD INDEX FKRel_Recipe821448 (idRecipe), ADD CONSTRAINT FKRel_Recipe821448 FOREIGN KEY (idRecipe) REFERENCES Recipe (id) ON DELETE CASCADE;

/* ON DELETE CASCADE for FOOD_RECIPE deleted*/
ALTER TABLE Rel_Food_Category ADD INDEX FKRel_Food_C945059 (idFoodRecipe), ADD CONSTRAINT FKRel_Food_C945059 FOREIGN KEY (idFoodRecipe) REFERENCES Food_Recipe (id) ON DELETE CASCADE;

/* ON DELETE CASCADE for USER deleted*/
ALTER TABLE Rel_User_Recipe ADD INDEX FKRel_User_R67529 (idUser), ADD CONSTRAINT FKRel_User_R67529 FOREIGN KEY (idUser) REFERENCES `User` (id) ON DELETE CASCADE;
ALTER TABLE Ingredient_Custom ADD INDEX FKIngredient810394 (idUser), ADD CONSTRAINT FKIngredient810394 FOREIGN KEY (idUser) REFERENCES `User` (id) ON DELETE CASCADE;
/* ON DELETE CASCADE for USER */
ALTER TABLE Rel_User_RecipeCategory ADD INDEX FKRel_User_R844919 (idUser), ADD CONSTRAINT FKRel_User_R844919 FOREIGN KEY (idUser) REFERENCES `User` (id) ON DELETE CASCADE;
ALTER TABLE Rel_User_RecipeOrigin ADD INDEX FKRel_User_R298780 (idUser), ADD CONSTRAINT FKRel_User_R298780 FOREIGN KEY (idUser) REFERENCES `User` (id) ON DELETE CASCADE;
ALTER TABLE FriendWith ADD INDEX FKfriendWith872932 (idUser1), ADD CONSTRAINT FKfriendWith872932 FOREIGN KEY (idUser1) REFERENCES `User` (id) ON DELETE CASCADE;
ALTER TABLE FriendWith ADD INDEX FKfriendWith402870 (idUser2), ADD CONSTRAINT FKfriendWith402870 FOREIGN KEY (idUser2) REFERENCES `User` (id) ON DELETE CASCADE;
/* DELETE USER : gerer pour voir ce quil advient des recette dont il est le createur...*/
ALTER TABLE Recipe ADD INDEX FKRecipe265030 (idOwner), ADD CONSTRAINT FKRecipe265030 FOREIGN KEY (idOwner) REFERENCES `User` (id);

/* ON DELETE CASCADE for Recipe_Category deleted */
ALTER TABLE Rel_Food_Category ADD INDEX FKRel_Food_C452139 (idCategory), ADD CONSTRAINT FKRel_Food_C452139 FOREIGN KEY (idCategory) REFERENCES Recipe_Category (id) ON DELETE CASCADE;
ALTER TABLE Rel_Recipe_Category ADD INDEX FKRel_Recipe949991 (idCategory), ADD CONSTRAINT FKRel_Recipe949991 FOREIGN KEY (idCategory) REFERENCES Recipe_Category (id) ON DELETE CASCADE;
ALTER TABLE Rel_User_RecipeCategory ADD INDEX FKRel_User_R345726 (idRecipeCategory), ADD CONSTRAINT FKRel_User_R345726 FOREIGN KEY (idRecipeCategory) REFERENCES Recipe_Category (id) ON DELETE CASCADE;

/* ON DELETE CASCADE for Recipe_Origin deleted */
ALTER TABLE Rel_User_RecipeOrigin ADD INDEX FKRel_User_R970858 (idRecipe_Origin), ADD CONSTRAINT FKRel_User_R970858 FOREIGN KEY (idRecipe_Origin) REFERENCES Recipe_Origin (id);
/* NOTHING here for Recipe_Origin deleted  :  ATTENTION il faudra bien penser a remplacer lorigin par une autre dans chaque RECIPE & FOOD_RECIPE avt de deleted cett derniere  */
ALTER TABLE Food_Recipe ADD INDEX FKFood_Recip58032 (idOrigin), ADD CONSTRAINT FKFood_Recip58032 FOREIGN KEY (idOrigin) REFERENCES Recipe_Origin (id);
ALTER TABLE Recipe ADD INDEX FKRecipe215426 (idOrigin), ADD CONSTRAINT FKRecipe215426 FOREIGN KEY (idOrigin) REFERENCES Recipe_Origin (id);


/* NO DELETE CASCADE FOR THE FOLLOWING REFERENCE - those should'nt be deleted..*/
ALTER TABLE Food ADD INDEX FKFood555976 (idCategory), ADD CONSTRAINT FKFood555976 FOREIGN KEY (idCategory) REFERENCES Food_Category (id);
ALTER TABLE ListShopping_Category ADD INDEX FKListShoppi31201 (idFoodCategory), ADD CONSTRAINT FKListShoppi31201 FOREIGN KEY (idFoodCategory) REFERENCES Food_Category (id);
ALTER TABLE Recipe ADD INDEX FKRecipe65078 (idType), ADD CONSTRAINT FKRecipe65078 FOREIGN KEY (idType) REFERENCES Recipe_Type (id);
ALTER TABLE Food_Recipe ADD INDEX FKFood_Recip222472 (idType), ADD CONSTRAINT FKFood_Recip222472 FOREIGN KEY (idType) REFERENCES Recipe_Type (id);
ALTER TABLE Recipe_Category ADD INDEX FKRecipe_Cat360407 (idRecipeType), ADD CONSTRAINT FKRecipe_Cat360407 FOREIGN KEY (idRecipeType) REFERENCES Recipe_Type (id);



