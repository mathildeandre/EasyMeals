CREATE TABLE Food (id int(10) NOT NULL AUTO_INCREMENT, name varchar(255) NOT NULL, idCategory int(10) NOT NULL, isValidated tinyint DEFAULT 0 NOT NULL, PRIMARY KEY (id));
CREATE TABLE Food_Category (id int(10) NOT NULL AUTO_INCREMENT, name varchar(255) NOT NULL, numRank int(10), PRIMARY KEY (id));
CREATE TABLE Food_Recipe (id int(10) NOT NULL AUTO_INCREMENT, name varchar(255) NOT NULL, idType int(10) NOT NULL, idFood int(10) NOT NULL, quantity int(10) NOT NULL, unit varchar(10), nbPerson int(10) DEFAULT 1 NOT NULL, pixName varchar(255), idOrigin int(10) NOT NULL, PRIMARY KEY (id), INDEX (idType));
CREATE TABLE Ingredient (idRecipe int(10) NOT NULL, idFood int(10) NOT NULL, quantity int(10) NOT NULL, unit varchar(10), PRIMARY KEY (idRecipe, idFood));
CREATE TABLE Ingredient_Custom (idRecipe int(10) NOT NULL, idFood int(10) NOT NULL, idUser int(10) NOT NULL, quantity int(10) NOT NULL, unit int(10), isHide tinyint(1) NOT NULL, PRIMARY KEY (idRecipe, idFood, idUser));
/*CREATE TABLE Ingredient_ListShop (idFood int(10) NOT NULL, idListShopCategory int(10) NOT NULL, quantity int(10) NOT NULL, unit varchar(10), PRIMARY KEY (idFood, idListShopCategory));*/
CREATE TABLE Ingredient_ListShop (nameFood varchar(255) NOT NULL, idListShopCategory int(10) NOT NULL, quantity int(10) NOT NULL, unit varchar(10));
CREATE TABLE List_Shopping (id int(10) NOT NULL AUTO_INCREMENT, name varchar(255) NOT NULL, PRIMARY KEY (id));
CREATE TABLE ListShopping_Category (id int(10) NOT NULL AUTO_INCREMENT, idListShop int(10) NOT NULL, idFoodCategory int(10) NOT NULL, PRIMARY KEY (id));
CREATE TABLE ListShopPlanning_User (id int(10) NOT NULL AUTO_INCREMENT, name varchar(255), `date` int(11), idListShop int(10) NOT NULL, idPlanning int(10) NOT NULL, idUser int(10) NOT NULL, lastOpen tinyint DEFAULT false NOT NULL, PRIMARY KEY (id));
CREATE TABLE Planning (id int(10) NOT NULL AUTO_INCREMENT, lastOpen tinyint DEFAULT false NOT NULL, name varchar(255) NOT NULL, idUser int(10) NOT NULL, nbPersGlobal int(10) DEFAULT 4 NOT NULL, isForListShop tinyint DEFAULT false NOT NULL, PRIMARY KEY (id), INDEX (idUser));
CREATE TABLE Planning_CaseMeal (id int(10) NOT NULL AUTO_INCREMENT, numDay int(10) NOT NULL, nbPers int(10) NOT NULL, idPlanningWeekMeal int(10) NOT NULL, PRIMARY KEY (id));
CREATE TABLE Planning_WeekMeal (id int(10) NOT NULL AUTO_INCREMENT, weekMealName varchar(255) NOT NULL, showWeekMeal tinyint NOT NULL, idPlanning int(10) NOT NULL, PRIMARY KEY (id));
CREATE TABLE Recipe (id int(10) NOT NULL AUTO_INCREMENT, name varchar(255) NOT NULL, idType int(10) NOT NULL, isPublic tinyint NOT NULL, idUser int(10) NOT NULL, rating int(10), nbVoter int(10) DEFAULT 0 NOT NULL, nbPerson int(10) DEFAULT 1 NOT NULL, pixName varchar(255), idOrigin int(10) NOT NULL, isValidated tinyint DEFAULT 0 NOT NULL, timeCooking int(10), timePreparation int(10), PRIMARY KEY (id), INDEX (idType), INDEX (isPublic), INDEX (idUser));
CREATE TABLE Recipe_Category (id int(10) NOT NULL AUTO_INCREMENT, name varchar(255) NOT NULL, numRank int(10), PRIMARY KEY (id));
CREATE TABLE Recipe_Description (idRecipe int(10) NOT NULL, numDescription int(10) NOT NULL, description varchar(255), PRIMARY KEY (idRecipe, numDescription));
CREATE TABLE Recipe_Origin (id int(10) NOT NULL AUTO_INCREMENT, name varchar(255) NOT NULL, numRank int(10), PRIMARY KEY (id));
CREATE TABLE Recipe_Type (id int(10) NOT NULL AUTO_INCREMENT, name varchar(25) NOT NULL, PRIMARY KEY (id));
CREATE TABLE Rel_Food_Category (idCategory int(10) NOT NULL, idFoodRecipe int(10) NOT NULL, PRIMARY KEY (idCategory, idFoodRecipe));
CREATE TABLE Rel_Recipe_CaseMealPlanning (idRecipe int(10) NOT NULL, idPlanningCaseMeal int(10) NOT NULL, nbPers int(10), PRIMARY KEY (idRecipe, idPlanningCaseMeal));
CREATE TABLE Rel_Recipe_Category (idCategory int(10) NOT NULL, idRecipe int(10) NOT NULL, PRIMARY KEY (idCategory, idRecipe));
CREATE TABLE Rel_User_Recipe (idRecipe int(10) NOT NULL, idUser int(10) NOT NULL, isFavorite tinyint(1) NOT NULL, isForPlanning tinyint(1) NOT NULL, ratingUser int(10), isHide tinyint(1) NOT NULL, PRIMARY KEY (idRecipe, idUser));
CREATE TABLE `User` (id int(10) NOT NULL AUTO_INCREMENT, pseudo varchar(255) NOT NULL, pwd varchar(255) NOT NULL, email varchar(255), isAdmin tinyint DEFAULT false NOT NULL, PRIMARY KEY (id));


/* ON DELETE CASCADE for USER deleted*/
ALTER TABLE Planning ADD INDEX FKPlanning761199 (idUser), ADD CONSTRAINT FKPlanning761199 FOREIGN KEY (idUser) REFERENCES `User` (id) ON DELETE CASCADE; /*'on delete cascade' veut dire que si on delete un user, tous les planning avec cet idUser correspondant sera deleted*/
ALTER TABLE ListShopPlanning_User ADD INDEX FKListShopPl181909 (idUser), ADD CONSTRAINT FKListShopPl181909 FOREIGN KEY (idUser) REFERENCES `User` (id);

/* ON DELETE CASCADE for PLANNING deleted*/
ALTER TABLE Planning_WeekMeal ADD INDEX FKPlanning_W938841 (idPlanning), ADD CONSTRAINT FKPlanning_W938841 FOREIGN KEY (idPlanning) REFERENCES Planning (id) ON DELETE CASCADE;
ALTER TABLE Planning_CaseMeal ADD INDEX FKPlanning_C546459 (idPlanningWeekMeal), ADD CONSTRAINT FKPlanning_C546459 FOREIGN KEY (idPlanningWeekMeal) REFERENCES Planning_WeekMeal (id) ON DELETE CASCADE;
ALTER TABLE Rel_Recipe_CaseMealPlanning ADD INDEX FKRel_Recipe36474 (idPlanningCaseMeal), ADD CONSTRAINT FKRel_Recipe36474 FOREIGN KEY (idPlanningCaseMeal) REFERENCES Planning_CaseMeal (id) ON DELETE CASCADE;

/* ON DELETE CASCADE for LIST SHOPPING deleted*/
ALTER TABLE ListShopping_Category ADD INDEX FKListShoppi989241 (idListShop), ADD CONSTRAINT FKListShoppi989241 FOREIGN KEY (idListShop) REFERENCES List_Shopping (id) ON DELETE CASCADE;
ALTER TABLE Ingredient_ListShop ADD INDEX FKIngredient293299 (idListShopCategory), ADD CONSTRAINT FKIngredient293299 FOREIGN KEY (idListShopCategory) REFERENCES ListShopping_Category (id) ON DELETE CASCADE;

/* ON DELETE NO ACTION for LISTSHOPPLANNING_USER*/
ALTER TABLE ListShopPlanning_User ADD INDEX FKListShopPl355885 (idPlanning), ADD CONSTRAINT FKListShopPl355885 FOREIGN KEY (idPlanning) REFERENCES Planning (id) ON DELETE NO ACTION; /* 'on delete cascade' veut dire que si on delete un planning qui a  une reference 'idPlanning' dans listShopPlanning_user : listshop ne sera pas touche et ainsi le planning pas supprime*/
ALTER TABLE ListShopPlanning_User ADD INDEX FKListShopPl179177 (idListShop), ADD CONSTRAINT FKListShopPl179177 FOREIGN KEY (idListShop) REFERENCES List_Shopping (id) ON DELETE NO ACTION; /* idem : en d'autres mots, impossible de delete une list_shopping & un planning faisant partie de LSP_user */


/*ALTER TABLE Ingredient_ListShop ADD INDEX FKIngredient178456 (idFood), ADD CONSTRAINT FKIngredient178456 FOREIGN KEY (idFood) REFERENCES Food (id);*/
ALTER TABLE Rel_Recipe_CaseMealPlanning ADD INDEX FKRel_Recipe821448 (idRecipe), ADD CONSTRAINT FKRel_Recipe821448 FOREIGN KEY (idRecipe) REFERENCES Recipe (id);
ALTER TABLE ListShopping_Category ADD INDEX FKListShoppi31201 (idFoodCategory), ADD CONSTRAINT FKListShoppi31201 FOREIGN KEY (idFoodCategory) REFERENCES Food_Category (id);
ALTER TABLE Food_Recipe ADD INDEX FKFood_Recip918613 (idFood), ADD CONSTRAINT FKFood_Recip918613 FOREIGN KEY (idFood) REFERENCES Food (id);
ALTER TABLE Recipe ADD INDEX FKRecipe65078 (idType), ADD CONSTRAINT FKRecipe65078 FOREIGN KEY (idType) REFERENCES Recipe_Type (id);
ALTER TABLE Food_Recipe ADD INDEX FKFood_Recip58032 (idOrigin), ADD CONSTRAINT FKFood_Recip58032 FOREIGN KEY (idOrigin) REFERENCES Recipe_Origin (id);
ALTER TABLE Food_Recipe ADD INDEX FKFood_Recip222472 (idType), ADD CONSTRAINT FKFood_Recip222472 FOREIGN KEY (idType) REFERENCES Recipe_Type (id);
ALTER TABLE Rel_Food_Category ADD INDEX FKRel_Food_C452139 (idCategory), ADD CONSTRAINT FKRel_Food_C452139 FOREIGN KEY (idCategory) REFERENCES Recipe_Category (id);
ALTER TABLE Rel_Food_Category ADD INDEX FKRel_Food_C945059 (idFoodRecipe), ADD CONSTRAINT FKRel_Food_C945059 FOREIGN KEY (idFoodRecipe) REFERENCES Food_Recipe (id);
ALTER TABLE Recipe_Description ADD INDEX FKRecipe_Des390294 (idRecipe), ADD CONSTRAINT FKRecipe_Des390294 FOREIGN KEY (idRecipe) REFERENCES Recipe (id);
ALTER TABLE Rel_Recipe_Category ADD INDEX FKRel_Recipe949991 (idCategory), ADD CONSTRAINT FKRel_Recipe949991 FOREIGN KEY (idCategory) REFERENCES Recipe_Category (id);
ALTER TABLE Rel_Recipe_Category ADD INDEX FKRel_Recipe163667 (idRecipe), ADD CONSTRAINT FKRel_Recipe163667 FOREIGN KEY (idRecipe) REFERENCES Recipe (id);
ALTER TABLE Rel_User_Recipe ADD INDEX FKRel_User_R252853 (idRecipe), ADD CONSTRAINT FKRel_User_R252853 FOREIGN KEY (idRecipe) REFERENCES Recipe (id);
ALTER TABLE Rel_User_Recipe ADD INDEX FKRel_User_R67529 (idUser), ADD CONSTRAINT FKRel_User_R67529 FOREIGN KEY (idUser) REFERENCES `User` (id);
ALTER TABLE Ingredient ADD INDEX FKIngredient523181 (idRecipe), ADD CONSTRAINT FKIngredient523181 FOREIGN KEY (idRecipe) REFERENCES Recipe (id);
ALTER TABLE Ingredient ADD INDEX FKIngredient896373 (idFood), ADD CONSTRAINT FKIngredient896373 FOREIGN KEY (idFood) REFERENCES Food (id);
ALTER TABLE Ingredient_Custom ADD INDEX FKIngredient130777 (idRecipe), ADD CONSTRAINT FKIngredient130777 FOREIGN KEY (idRecipe) REFERENCES Recipe (id);
ALTER TABLE Ingredient_Custom ADD INDEX FKIngredient711221 (idFood), ADD CONSTRAINT FKIngredient711221 FOREIGN KEY (idFood) REFERENCES Food (id);
ALTER TABLE Ingredient_Custom ADD INDEX FKIngredient810394 (idUser), ADD CONSTRAINT FKIngredient810394 FOREIGN KEY (idUser) REFERENCES `User` (id);
ALTER TABLE Recipe ADD INDEX FKRecipe215426 (idOrigin), ADD CONSTRAINT FKRecipe215426 FOREIGN KEY (idOrigin) REFERENCES Recipe_Origin (id);
ALTER TABLE Recipe ADD INDEX FKRecipe662046 (idUser), ADD CONSTRAINT FKRecipe662046 FOREIGN KEY (idUser) REFERENCES `User` (id);
ALTER TABLE Food ADD INDEX FKFood555976 (idCategory), ADD CONSTRAINT FKFood555976 FOREIGN KEY (idCategory) REFERENCES Food_Category (id);
