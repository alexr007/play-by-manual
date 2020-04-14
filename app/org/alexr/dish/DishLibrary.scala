package org.alexr.dish

import scala.collection.mutable
import play.api.db.Database

sealed trait DishLibrary {
  def all: Set[Dish]
  def find(name: String): Option[Dish]
  def create(dishToCreate: Dish): Boolean
}

class DishLibraryInMemory(dishes: mutable.Set[Dish]) extends DishLibrary {
  def all: Set[Dish] = dishes.toSet
  def find(name: String): Option[Dish] = dishes.find(_.name == name)
  def create(dishToCreate: Dish): Boolean = dishes.add(dishToCreate)
}

class DishLibraryDb(dbApi: play.api.db.DBApi) extends DishLibrary {
  private val database: Database = dbApi.database("dish_db")

  override def all: Set[Dish] = {
    database.withConnection { connection =>

      val resultSet = connection
        .createStatement()
        .executeQuery("SELECT DishName, Description, Price FROM Dish")

      val dishes = mutable.ListBuffer.empty[Dish]

      while (resultSet.next()) {
        val name: String        = resultSet.getString("DishName")
        val description: String = resultSet.getString("Description")
        val price: Double       = resultSet.getDouble("Price")
        val dish = Dish(name, description, price)
        dishes.addOne(dish)
      }

      dishes.toSet
    }
  }

  override def find(name: String): Option[Dish] = {
    database.withConnection { connection =>

      val resultSet =
        connection
          .createStatement()
          .executeQuery(s"SELECT DishName, Description, Price FROM Dish WHERE DishName = '$name'")

      if (resultSet.next()) {
        Some(
          Dish(
            resultSet.getString("DishName"),
            resultSet.getString("Description"),
            resultSet.getInt("Price")
          )
        )
      } else None
    }
  }

  override def create(dishToCreate: Dish): Boolean = {
    database.withTransaction { connection =>

      val sql = s"""INSERT INTO Dish (DishName, Description, Price)
                   |SELECT * FROM (SELECT '${dishToCreate.name}', '${dishToCreate.description}', ${dishToCreate.price}) AS tmp
                   |WHERE NOT EXISTS (
                   |    SELECT DishName FROM Dish WHERE DishName = '${dishToCreate.name}'
                   |) LIMIT 1;
                   |""".stripMargin

      val rowsAffected = connection.createStatement().executeUpdate(sql)
      rowsAffected == 1
    }
  }
}
