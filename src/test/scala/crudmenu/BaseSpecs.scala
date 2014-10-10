package crudmenu

import akka.actor.ActorRefFactory
import crudmenu.utils.ExecutionContextSupport
import org.scalatest._
import spray.testkit.ScalatestRouteTest
import spray.routing.HttpService
import scala.concurrent.ExecutionContext

//Using abstract instead of trait because of faster compile (http://scalatest.org/user_guide/defining_base_classes)
abstract class BaseSpec extends BaseSpec_

trait BaseSpec_ extends Matchers with WordSpecLike with BeforeAndAfterEach with BeforeAndAfterAll with BeforeAndAfter with OptionValues with ExecutionContextSupport {
  override implicit def executionContext: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global
}

abstract class RouteBaseSpec extends BaseSpec with ScalatestRouteTest with HttpService with ExecutionContextSupport {

  override def actorRefFactory: ActorRefFactory = system
  override implicit def executionContext = system.dispatcher
}