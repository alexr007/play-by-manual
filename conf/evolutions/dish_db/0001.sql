# --- !Ups

CREATE TABLE IF NOT EXISTS Dish (
 DishName varchar(255) NOT NULL,
 Description varchar(255) NOT NULL,
 Price INT NOT NULL,
 Id INT NOT NULL AUTO_INCREMENT,
 PRIMARY KEY (Id)
) AUTO_INCREMENT=0;

# --- !Downs
DROP TABLE Dish;
