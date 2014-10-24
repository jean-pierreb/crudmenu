import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import reactivemongo.api.MongoConnection.ParsedURI
import reactivemongo.api.{ DefaultDB, MongoConnection, MongoDriver }
import reactivemongo.extensions.bson.fixtures.BsonFixtures

import scala.util.{ Failure, Success }

object InitLocalMongo {
  def main(args: Array[String]) = {
    implicit def system = ActorSystem("newstestinsdB", ConfigFactory.load())

    implicit def executionContext = system.dispatcher

    val serverUri = system.settings.config.getString("crudmenu.mongodb.server-uri")
    val parsedServerUriLocal: ParsedURI = MongoConnection.parseURI(serverUri) match {
      case Success(uri)   ⇒ uri
      case Failure(error) ⇒ throw new RuntimeException(s"Unparsable value '$serverUri' for MongoDB server URI. Error: $error")
    }

    def parsedServerUri = parsedServerUriLocal

    val driver = new MongoDriver(system)
    val connection: MongoConnection = driver.connection(parsedServerUri)
    val db: DefaultDB = connection("Experimental")
    db drop () onComplete {
      case _ ⇒ BsonFixtures(db).load("menu.conf") onComplete {
        case _ ⇒ System exit 0
      }
    }
  }
}