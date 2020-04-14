# --- !Ups

INSERT
  INTO Dish (DishName, Description, Price)
  VALUES ('Avocado Sandwich', 'Whole grain bread with Brie cheese, tomatoes and avocado', 8);

# --- !Downs

DELETE FROM Dish WHERE DishName = 'Avocado Sandwich'
