package crudmenu

import akka.actor.ActorSystem

trait ActorSystemSupport {
  def system: ActorSystem
}
