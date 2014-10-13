package crudmenu.adapters.menu

import crudmenu.models.Chapter
import crudmenu.utils.ExecutionContextSupport
import crudmenu.utils.mongo.MongoDBSupport
import reactivemongo.bson._
import reactivemongo.api.collections.default.BSONCollection
import reactivemongo.bson.BSONDocument
import Menu._

import scala.concurrent.Future

trait MenuQueries extends ExecutionContextSupport  with MongoDBSupport {

  def getFullMenu(): Future[List[Chapter]] = db[BSONCollection]("chapters").find(BSONDocument()).cursor[Chapter].collect[List]()

}
