package org.alexr.configuration

import controllers.AssetsComponents
import org.alexr.dish.{Dish, DishController, DishService}
import org.alexr.home.HomeController
import play.api.ApplicationLoader.Context
import play.api.{BuiltInComponentsFromContext, Configuration}
import play.api.routing.Router
import play.filters.HttpFiltersComponents
import pureconfig.ConfigSource
import pureconfig.generic.auto._
import router.Routes

import scala.collection.mutable

class AppComponents(context: Context) extends BuiltInComponentsFromContext(context)
  with HttpFiltersComponents
  with AssetsComponents {

  val dishes: mutable.Set[Dish] = mutable.Set(
    Dish("Avocado Sandwich", "Whole grain bread with Brie cheese, tomatoes and avocado", 10),
    Dish("Ice Cream", "Chocolate + Vanilla Ice Cream", 8)
  )

  lazy val config: AppConfig = ConfigSource.default.loadOrThrow[AppConfig]
  lazy val homeController: HomeController = new HomeController(
    controllerComponents,
    configuration,
    config)

  lazy val dishService: DishService = new DishService(dishes)
  lazy val dishController: DishController = new DishController(
    controllerComponents,
    dishService
  )

  override def router: Router = new Routes(
    httpErrorHandler,
    assets,
    homeController,
    dishController
  )
}
