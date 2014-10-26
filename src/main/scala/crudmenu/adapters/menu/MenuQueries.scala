package crudmenu.adapters.menu

import crudmenu.models._
import crudmenu.utils.ExecutionContextSupport
import crudmenu.utils.mongo.MongoDBSupport
import reactivemongo.bson._
import reactivemongo.api.collections.default.BSONCollection
import reactivemongo.bson.BSONDocument
import Menu._

import scala.concurrent.Future

trait MenuQueries extends ExecutionContextSupport with MongoDBSupport with ChapterMarshalling {

  def getFullMenu(): Future[List[Chapter]] = db[BSONCollection]("chapters").find(BSONDocument()).sort(BSONDocument("name" -> 1)).cursor[Chapter].collect[List]()

  var r = new scala.util.Random

  def addChapter(chapterName: String) = db[BSONCollection]("chapters").insert(BSONDocument("id" -> r.nextInt(), "name" -> chapterName))

  def deleteChapter(id: Int) = db[BSONCollection]("chapters").remove(BSONDocument("id" -> id), firstMatchOnly = true)

  def showChapter(id: String): Future[Option[Chapter]] = db[BSONCollection]("chapters").find(BSONDocument("_id" -> id)).one[Chapter]

  def showChapters(): Future[List[Chapter]] = db[BSONCollection]("chapters").find(BSONDocument()).sort(BSONDocument("name" -> 1)).cursor[Chapter].collect[List]()

  def cleanUpDB() = db[BSONCollection]("chapters").remove(BSONDocument.empty)
}
