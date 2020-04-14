package org.alexr.dish

import scala.collection.mutable

class DishService(dishes: mutable.Set[Dish]) {

  def getAllDishes: Set[Dish] =
    dishes.toSet

  def findDish(name: String): Option[Dish] =
    dishes.find(_.name == name)

  def createDish(dishToCreate: Dish): Boolean =
    dishes.add(dishToCreate)

}
