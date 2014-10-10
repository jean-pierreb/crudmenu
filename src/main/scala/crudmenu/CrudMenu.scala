package crudmenu

import crudmenu.adapters.menu.MenuRoutes
import spray.routing.HttpServiceActor

class CrudMenu extends HttpServiceActor with ActorContextCreationSupport with MenuRoutes {

  def receive = {
    runRoute(menuRoutes)
  }

  implicit def executionContext = actorRefFactory.dispatcher
}


