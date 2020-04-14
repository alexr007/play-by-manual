package org.alexr.dish

import play.api.mvc.{AbstractController, Action, ControllerComponents}
import play.api.libs.json.{Json, OWrites, Reads}

class DishController(
                      controllerComponents: ControllerComponents,
                      dishService: DishLibrary,
                    ) extends AbstractController(controllerComponents)
{

  def allDishes(): Action[Unit] = Action(parse.empty) { _ =>
    implicit val dishWrites: OWrites[Dish] = Json.writes[Dish]
    Ok(Json.toJson(dishService.all))
  }

  def findDish(name: String): Action[Unit] = Action(parse.empty) { _ =>
    val maybeDish = dishService.find(name)
    maybeDish match {
      case Some(dish) =>
        implicit val dishWrites: OWrites[Dish] = Json.writes[Dish]
        Ok(Json.toJson(dish))
      case None =>
        NotFound("could not find the specified dish")
    }
  }
  implicit val dishReads: Reads[Dish] = Json.reads[Dish]

  // this line will parse request automatically and pass dish to body
  def createDish(): Action[Dish] = Action(parse.json[Dish]) { request =>
    val dishToCreate: Dish = request.body

    val message = if (dishService.create(dishToCreate))
        s"Added dish ${dishToCreate.name} to the dish list"
      else s"Dish ${dishToCreate.name} already exists"

    Ok(message)
  }
}
