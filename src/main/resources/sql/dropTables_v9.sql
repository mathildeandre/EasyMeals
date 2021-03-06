ALTER TABLE ListShopping_Category DROP FOREIGN KEY FKListShoppi165950;
ALTER TABLE Planning_WeekMeal DROP FOREIGN KEY FKPlanning_W938841;
ALTER TABLE Planning DROP FOREIGN KEY FKPlanning761199;
ALTER TABLE Planning_CaseMeal DROP FOREIGN KEY FKPlanning_C546459;
ALTER TABLE Rel_Recipe_CaseMealPlanning DROP FOREIGN KEY FKRel_Recipe36474;
ALTER TABLE Rel_Recipe_CaseMealPlanning DROP FOREIGN KEY FKRel_Recipe821448;
ALTER TABLE Ingredient_ListShop DROP FOREIGN KEY FKIngredient293299;
ALTER TABLE ListShopping_Category DROP FOREIGN KEY FKListShoppi31201;
ALTER TABLE Food_Recipe DROP FOREIGN KEY FKFood_Recip918613;
ALTER TABLE Recipe DROP FOREIGN KEY FKRecipe65078;
ALTER TABLE Food_Recipe DROP FOREIGN KEY FKFood_Recip58032;
ALTER TABLE Food_Recipe DROP FOREIGN KEY FKFood_Recip222472;
ALTER TABLE Rel_Food_Category DROP FOREIGN KEY FKRel_Food_C452139;
ALTER TABLE Rel_Food_Category DROP FOREIGN KEY FKRel_Food_C945059;
ALTER TABLE Recipe_Description DROP FOREIGN KEY FKRecipe_Des390294;
ALTER TABLE Rel_Recipe_Category DROP FOREIGN KEY FKRel_Recipe949991;
ALTER TABLE Rel_Recipe_Category DROP FOREIGN KEY FKRel_Recipe163667;
ALTER TABLE Rel_User_Recipe DROP FOREIGN KEY FKRel_User_R252853;
ALTER TABLE Rel_User_Recipe DROP FOREIGN KEY FKRel_User_R67529;
ALTER TABLE Ingredient DROP FOREIGN KEY FKIngredient523181;
ALTER TABLE Ingredient DROP FOREIGN KEY FKIngredient896373;
ALTER TABLE Ingredient_Custom DROP FOREIGN KEY FKIngredient130777;
ALTER TABLE Ingredient_Custom DROP FOREIGN KEY FKIngredient711221;
ALTER TABLE Ingredient_Custom DROP FOREIGN KEY FKIngredient810394;
ALTER TABLE Recipe DROP FOREIGN KEY FKRecipe215426;
ALTER TABLE Recipe DROP FOREIGN KEY FKRecipe265030;
ALTER TABLE Food DROP FOREIGN KEY FKFood555976;
ALTER TABLE Recipe_Category DROP FOREIGN KEY FKRecipe_Cat360407;
ALTER TABLE Rel_User_RecipeCategory DROP FOREIGN KEY FKRel_User_R345726;
ALTER TABLE Rel_User_RecipeCategory DROP FOREIGN KEY FKRel_User_R844919;
ALTER TABLE FriendWith DROP FOREIGN KEY FKfriendWith872932;
ALTER TABLE FriendWith DROP FOREIGN KEY FKfriendWith402870;
ALTER TABLE Rel_User_RecipeOrigin DROP FOREIGN KEY FKRel_User_R970858;
ALTER TABLE Rel_User_RecipeOrigin DROP FOREIGN KEY FKRel_User_R298780;

ALTER TABLE Expense DROP FOREIGN KEY FKRel_Expense_S1234;
ALTER TABLE ExpensePers DROP FOREIGN KEY  FKRel_Expense_S2345;
ALTER TABLE ExpenseRow DROP FOREIGN KEY  FKRel_Expense_S3456 ;
ALTER TABLE ExpenseRow DROP FOREIGN KEY  FKRel_Expense_S4567 ;
ALTER TABLE ExpenseCase DROP FOREIGN KEY  FKRel_Expense_S5678 ;
ALTER TABLE ExpenseCase DROP FOREIGN KEY  FKRel_Expense_S6789 ;





DROP TABLE IF EXISTS Food;
DROP TABLE IF EXISTS Food_Category;
DROP TABLE IF EXISTS Food_Recipe;
DROP TABLE IF EXISTS FriendWith;
DROP TABLE IF EXISTS Ingredient;
DROP TABLE IF EXISTS Ingredient_Custom;
DROP TABLE IF EXISTS Ingredient_ListShop;
DROP TABLE IF EXISTS ListShopping_Category;
DROP TABLE IF EXISTS Planning;
DROP TABLE IF EXISTS Planning_CaseMeal;
DROP TABLE IF EXISTS Planning_WeekMeal;
DROP TABLE IF EXISTS Recipe;
DROP TABLE IF EXISTS Recipe_Category;
DROP TABLE IF EXISTS Recipe_Description;
DROP TABLE IF EXISTS Recipe_Origin;
DROP TABLE IF EXISTS Recipe_Type;
DROP TABLE IF EXISTS Rel_Food_Category;
DROP TABLE IF EXISTS Rel_Recipe_CaseMealPlanning;
DROP TABLE IF EXISTS Rel_Recipe_Category;
DROP TABLE IF EXISTS Rel_User_Recipe;
DROP TABLE IF EXISTS Rel_User_RecipeCategory;
DROP TABLE IF EXISTS Rel_User_RecipeOrigin;
DROP TABLE IF EXISTS `User`;

DROP TABLE IF EXISTS Expense;
DROP TABLE IF EXISTS ExpensePers;
DROP TABLE IF EXISTS ExpenseRow;
DROP TABLE IF EXISTS ExpenseCase;