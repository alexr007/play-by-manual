# --- !Ups

INSERT
  INTO Dish (DishName, Description, Price)
  VALUES ('Pepperoni', 'Pizza', 15);

# --- !Downs

DELETE FROM Dish WHERE DishName = 'Pepperoni'
