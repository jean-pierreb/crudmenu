package crudmenu

import akka.actor.ActorSystem
import crudmenu.adapters.menu.MenuRoutes
import crudmenu.utils.ExecutionContextSupport
import crudmenu.utils.mongo.DefaultMongoDBSupport
import reactivemongo.api.DefaultDB
import spray.routing.HttpServiceActor

class CrudMenu extends HttpServiceActor with ActorContextCreationSupport with ExecutionContextSupport with MenuRoutes with DefaultMongoDBSupport {

  def receive = {
    runRoute(menuRoutes)
  }

  implicit def executionContext = actorRefFactory.dispatcher

  override def db: DefaultDB = connection("Experimental")

  override def system: ActorSystem = context.system
}


