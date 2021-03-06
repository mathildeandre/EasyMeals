ALTER TABLE Rel_User_Recipe DROP FOREIGN KEY FKRel_User_R252853;
ALTER TABLE Rel_User_Recipe DROP FOREIGN KEY FKRel_User_R67529;
ALTER TABLE Ingredient DROP FOREIGN KEY FKIngredient523181;
ALTER TABLE Ingredient DROP FOREIGN KEY FKIngredient896373;
ALTER TABLE Ingredient_Custom DROP FOREIGN KEY FKIngredient130777;
ALTER TABLE Ingredient_Custom DROP FOREIGN KEY FKIngredient711221;
ALTER TABLE Ingredient_Custom DROP FOREIGN KEY FKIngredient810394;
ALTER TABLE Recipe DROP FOREIGN KEY FKRecipe215426;
ALTER TABLE Recipe DROP FOREIGN KEY FKRecipe79198;
ALTER TABLE Food DROP FOREIGN KEY FKFood555976;
ALTER TABLE Recipe_Description DROP FOREIGN KEY FKRecipe_Des390294;
ALTER TABLE Rel_Recipe_Category DROP FOREIGN KEY FKRel_Recipe658924;
ALTER TABLE Rel_Recipe_Category DROP FOREIGN KEY FKRel_Recipe163667;

DROP TABLE IF EXISTS Food;
DROP TABLE IF EXISTS Food_Category;
DROP TABLE IF EXISTS Ingredient;
DROP TABLE IF EXISTS Ingredient_Custom;
DROP TABLE IF EXISTS Recipe;
DROP TABLE IF EXISTS Recipe_Category;
DROP TABLE IF EXISTS Recipe_Description;
DROP TABLE IF EXISTS Recipe_Origin;
DROP TABLE IF EXISTS Rel_Recipe_Category;
DROP TABLE IF EXISTS Rel_User_Recipe;
DROP TABLE IF EXISTS `User`;
