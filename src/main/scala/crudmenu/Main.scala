package crudmenu

import akka.actor.{ Props, ActorSystem }
import akka.io.IO

import spray.can.Http
import spray.can.Http.Bind

object Main extends App {

  implicit val system = ActorSystem("newstestins")

  val receptionist = system.actorOf(Props(new CrudMenu), "crudmenu")
  val settings = Settings(system)

  IO(Http) ! Bind(listener = receptionist, interface = settings.Http.Host, port = settings.Http.Port)
}