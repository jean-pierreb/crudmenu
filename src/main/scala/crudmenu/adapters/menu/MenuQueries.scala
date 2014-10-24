package crudmenu.adapters.menu

import crudmenu.models.{ ChapterMarshalling, Chapter }
import crudmenu.utils.ExecutionContextSupport
import crudmenu.utils.mongo.MongoDBSupport
import reactivemongo.bson._
import reactivemongo.api.collections.default.BSONCollection
import reactivemongo.bson.BSONDocument
import Menu._

import scala.concurrent.Future

trait MenuQueries extends ExecutionContextSupport  with MongoDBSupport with ChapterMarshalling {

  def getFullMenu(): Future[List[Chapter]] = db[BSONCollection]("chapters").find(BSONDocument()).sort(BSONDocument("name" -> 1)).cursor[Chapter].collect[List]()

  def deleteChapter(id: String) = db[BSONCollection]("chapters").remove(BSONDocument("_id" -> id), firstMatchOnly = true)
}
