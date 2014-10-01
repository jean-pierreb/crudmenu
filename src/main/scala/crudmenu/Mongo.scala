package crudmenu

import reactivemongo.api.{MongoDriver, MongoConnection, DefaultDB}
import reactivemongo.api.MongoConnection.ParsedURI
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

trait MongoDBSupport {
  def db: DefaultDB
}

trait MongoUrl extends  ActorSystemSupport {

  val serverUri = system.settings.config.getString("crudmenu.mongodb.server-uri")
  val parsedServerUriLocal: ParsedURI = MongoConnection.parseURI(serverUri) match {
    case Success(uri)   ⇒ uri
    case Failure(error) ⇒ throw new RuntimeException(s"Unparsable value '$serverUri' for MongoDB server URI. Error: $error")
  }

  def parsedServerUri = parsedServerUriLocal
}

trait DefaultMongoDBSupport extends MongoUrl with MongoDBSupport with ActorContextCreationSupport  {
  def parsedServerUri: ParsedURI

  //Mongo initialization
  val driver = new MongoDriver(system)
  val connection: MongoConnection = driver.connection(parsedServerUri)
  override def db: DefaultDB = connection("zorgdomein")
}
