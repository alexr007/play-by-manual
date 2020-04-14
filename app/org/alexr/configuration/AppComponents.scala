package org.alexr.configuration

import controllers.AssetsComponents
import org.alexr.dish.{Dish, DishController, DishLibrary, DishLibraryDb, DishLibraryInMemory}
import org.alexr.home.HomeController
import play.api.ApplicationLoader.Context
import play.api.db.{DBComponents, HikariCPComponents}
import play.api.{BuiltInComponentsFromContext, Configuration}
import play.api.routing.Router
import play.filters.HttpFiltersComponents
import pureconfig.ConfigSource
import pureconfig.generic.auto._
import router.Routes
import play.api.db.evolutions.{ApplicationEvolutions, EvolutionsComponents}

import scala.collection.mutable

class AppComponents(context: Context) extends BuiltInComponentsFromContext(context)
  with HttpFiltersComponents
  with AssetsComponents
  with HikariCPComponents
  with DBComponents
  with EvolutionsComponents {

  def runEvolutions(): ApplicationEvolutions = {
    applicationEvolutions
  }

  val dishes: mutable.Set[Dish] = mutable.Set(
    Dish("Avocado Sandwich", "Whole grain bread with Brie cheese, tomatoes and avocado", 10),
    Dish("Ice Cream", "Chocolate + Vanilla Ice Cream", 8)
  )

  lazy val config: AppConfig = ConfigSource.default.loadOrThrow[AppConfig]
  lazy val homeController: HomeController = new HomeController(
    controllerComponents,
    configuration,
    config)

  lazy val dishService: DishLibrary = new
//      DishLibraryInMemory(dishes)
    DishLibraryDb(dbApi) // dbApi goes from the DBComponents trait

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
