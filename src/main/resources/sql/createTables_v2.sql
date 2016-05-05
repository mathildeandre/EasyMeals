CREATE TABLE Food (id int(10) NOT NULL AUTO_INCREMENT, name varchar(255) NOT NULL, idCategory int(10) NOT NULL, hasBeenValidated tinyint, PRIMARY KEY (id));
CREATE TABLE Food_Category (id int(10) NOT NULL AUTO_INCREMENT, name varchar(255) NOT NULL, noRank int(10), PRIMARY KEY (id));
CREATE TABLE Food_Recipe (id int(10) NOT NULL AUTO_INCREMENT, name varchar(255) NOT NULL, idType int(10) NOT NULL, idFood int(10) NOT NULL, quantity int(10) NOT NULL, unit varchar(10),
  nbPerson int(10) DEFAULT 1 NOT NULL, pixName varchar(255), idOrigin int(10) NOT NULL, PRIMARY KEY (id), INDEX (idType));

CREATE TABLE Ingredient (idRecipe int(10) NOT NULL, idFood int(10) NOT NULL, quantity int(10) NOT NULL, unit varchar(10), PRIMARY KEY (idRecipe, idFood));
CREATE TABLE Ingredient_Custom (idRecipe int(10) NOT NULL, idFood int(10) NOT NULL, idUser int(10) NOT NULL, quantity int(10) NOT NULL, unit int(10), isHide tinyint(1) NOT NULL,
  PRIMARY KEY (idRecipe, idFood, idUser));

CREATE TABLE Recipe (id int(10) NOT NULL AUTO_INCREMENT, name varchar(255) NOT NULL, idType int(10) NOT NULL, isPublic tinyint NOT NULL, idUser int(10) NOT NULL, rating int(10),
  nbVoter int(10) DEFAULT 0 NOT NULL, nbPerson int(10) DEFAULT 1 NOT NULL, pixName varchar(255), idOrigin int(10) NOT NULL, PRIMARY KEY (id), INDEX (idType), INDEX (isPublic), INDEX (idUser));
CREATE TABLE Recipe_Category (id int(10) NOT NULL AUTO_INCREMENT, name varchar(255) NOT NULL, noRank int(10), PRIMARY KEY (id));
CREATE TABLE Recipe_Description (idRecipe int(10) NOT NULL, numDescription int(10) NOT NULL, description varchar(255), PRIMARY KEY (idRecipe, numDescription));
CREATE TABLE Recipe_Origin (id int(10) NOT NULL AUTO_INCREMENT, name varchar(255) NOT NULL, noRank int(10), PRIMARY KEY (id));
CREATE TABLE Recipe_Type (id int(10) NOT NULL AUTO_INCREMENT, name varchar(25) NOT NULL, PRIMARY KEY (id));

CREATE TABLE Rel_Food_Category (idCategory int(10) NOT NULL, idFoodRecipe int(10) NOT NULL, PRIMARY KEY (idCategory, idFoodRecipe));
CREATE TABLE Rel_Recipe_Category (idCategory int(10) NOT NULL, idRecipe int(10) NOT NULL, PRIMARY KEY (idCategory, idRecipe));
CREATE TABLE Rel_User_Recipe (idRecipe int(10) NOT NULL, idUser int(10) NOT NULL, isFavorite tinyint(1) NOT NULL, isForPlanning tinyint(1) NOT NULL, rating int(10), isHide tinyint(1) NOT NULL,
  PRIMARY KEY (idRecipe, idUser));

CREATE TABLE `User` (id int(10) NOT NULL AUTO_INCREMENT, name varchar(255) NOT NULL, pwd varchar(255) NOT NULL, email varchar(255), PRIMARY KEY (id));




ALTER TABLE Recipe_Description  ADD CONSTRAINT FKRecipe_Des390294 FOREIGN KEY (idRecipe) REFERENCES Recipe (id);
ALTER TABLE Rel_Recipe_Category  ADD CONSTRAINT FKRel_Recipe658924 FOREIGN KEY (idCategory) REFERENCES Recipe_Category (id);
ALTER TABLE Rel_Recipe_Category ADD CONSTRAINT FKRel_Recipe163667 FOREIGN KEY (idRecipe) REFERENCES Recipe (id);
ALTER TABLE Rel_User_Recipe ADD CONSTRAINT FKRel_User_R252853 FOREIGN KEY (idRecipe) REFERENCES Recipe (id);
ALTER TABLE Rel_User_Recipe ADD CONSTRAINT FKRel_User_R67529 FOREIGN KEY (idUser) REFERENCES `User` (id);
ALTER TABLE Ingredient ADD CONSTRAINT FKIngredient523181 FOREIGN KEY (idRecipe) REFERENCES Recipe (id);
ALTER TABLE Ingredient  ADD CONSTRAINT FKIngredient896373 FOREIGN KEY (idFood) REFERENCES Food (id);
ALTER TABLE Ingredient_Custom  ADD CONSTRAINT FKIngredient130777 FOREIGN KEY (idRecipe) REFERENCES Recipe (id);
ALTER TABLE Ingredient_Custom ADD CONSTRAINT FKIngredient711221 FOREIGN KEY (idFood) REFERENCES Food (id);
ALTER TABLE Ingredient_Custom ADD CONSTRAINT FKIngredient810394 FOREIGN KEY (idUser) REFERENCES `User` (id);
ALTER TABLE Recipe  ADD CONSTRAINT FKRecipe215426 FOREIGN KEY (idOrigin) REFERENCES Recipe_Origin (id);
ALTER TABLE Recipe  ADD CONSTRAINT FKRecipe662046 FOREIGN KEY (idUser) REFERENCES `User` (id);
ALTER TABLE Food  ADD CONSTRAINT FKFood555976 FOREIGN KEY (idCategory) REFERENCES Food_Category (id);
ALTER TABLE Food_Recipe  ADD CONSTRAINT FKFood_Recip918613 FOREIGN KEY (idFood) REFERENCES Food (id);
ALTER TABLE Recipe ADD CONSTRAINT FKRecipe65078 FOREIGN KEY (idType) REFERENCES Recipe_Type (id);
ALTER TABLE Food_Recipe  ADD CONSTRAINT FKFood_Recip58032 FOREIGN KEY (idOrigin) REFERENCES Recipe_Origin (id);
ALTER TABLE Food_Recipe ADD CONSTRAINT FKFood_Recip222472 FOREIGN KEY (idType) REFERENCES Recipe_Type (id);
ALTER TABLE Rel_Food_Category ADD CONSTRAINT FKRel_Food_C156777 FOREIGN KEY (idCategory) REFERENCES Recipe_Category (id);
ALTER TABLE Rel_Food_Category  ADD CONSTRAINT FKRel_Food_C945059 FOREIGN KEY (idFoodRecipe) REFERENCES Food_Recipe (id);




