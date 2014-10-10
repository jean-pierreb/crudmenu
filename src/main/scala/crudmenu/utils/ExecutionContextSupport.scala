package crudmenu.utils

import akka.actor.Actor

trait ExecutionContextSupport {
  import scala.concurrent.ExecutionContext
  implicit def executionContext: ExecutionContext
}

trait ActorExecutionContextSupport extends ExecutionContextSupport { this: Actor ⇒
  implicit def executionContext = context.dispatcher
}