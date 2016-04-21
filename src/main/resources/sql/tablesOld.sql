
CREATE TABLE Recipe (
 id int(10) NOT NULL AUTO_INCREMENT,
 name varchar(255) NOT NULL,
 type varchar(25) NOT NULL,
 description varchar(255),
 nbPerson int(10) NOT NULL,
 rating int(10) NOT NULL,
 nbVoter int(10) NOT NULL,
 idUser int(10) NOT NULL,
 idOldUser int(10),
 idOrigin int(10) NOT NULL,
 idCategory int(10) NOT NULL,
 FOREIGN KEY (idUser) REFERENCES User(id),
 FOREIGN KEY (idOldUser) REFERENCES User(id),
 FOREIGN KEY (idCategory) REFERENCES Recipe_Category(id),
 FOREIGN KEY (idOrigin) REFERENCES Origin(id),
 PRIMARY KEY (id)
);

CREATE TABLE Origin (
 id int(10) NOT NULL AUTO_INCREMENT,
 name varchar(255) NOT NULL,
 PRIMARY KEY (id)
);

CREATE TABLE Recipe_Category (
 id int(10) NOT NULL AUTO_INCREMENT,
 name varchar(255) NOT NULL,
 PRIMARY KEY (id)
);


CREATE TABLE Rel_User_Recipe (
 idRecipe int(10) NOT NULL,
 idUser int(10) NOT NULL,
 isFavorite tinyint(1) NOT NULL,
 isForPlanning tinyint(1) NOT NULL,
 rating int(10),
 isHide tinyint(1) NOT NULL,
 FOREIGN KEY (idRecipe) REFERENCES Recipe(id),
 FOREIGN KEY (idUser) REFERENCES User(id),
 PRIMARY KEY (idRecipe, idUser)
);

CREATE TABLE User (
 id int(10) NOT NULL AUTO_INCREMENT,
 name varchar(255) NOT NULL,
 pwd varchar(255) NOT NULL,
 email varchar(255),
 PRIMARY KEY (id)
);


CREATE TABLE Food (
 id int(10) NOT NULL AUTO_INCREMENT,
 name varchar(255) NOT NULL,
 idCategory int(10) NOT NULL,
 PRIMARY KEY (id)
);


