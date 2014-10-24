package crudmenu.adapters.menu

import crudmenu.models.{ ChapterInfo, Category, Item, Chapter }
import reactivemongo.bson._
import reactivemongo.bson.BSONLong

object Menu {

  implicit object ItemReader extends BSONDocumentReader[Item] {
    def read(doc: BSONDocument): Item = {
      val id = doc.getAs[Int]("oracle_id").get
      val name = doc.getAs[String]("name").get

      Item(id, name)
    }
  }

  implicit object CategoryReader extends BSONDocumentReader[Category] {
    def read(doc: BSONDocument): Category = {
      val id = doc.get("oracle_id").get

      //TODO fix mongo long/integer conversion
      val theId = id match {
        case BSONInteger(i) ⇒ i.toString
        case BSONLong(l)    ⇒ l.toString
      }

      val name = doc.getAs[String]("name").get
      val items = doc.getAs[List[Item]]("items").getOrElse(List())

      Category(name, items)
    }
  }

  implicit object ChapterReader extends BSONDocumentReader[Chapter] {
    def read(doc: BSONDocument): Chapter = {
      val id = doc.get("oracle_id").get

      //TODO fix mongo long/integer conversion
      val theId = id match {
        case BSONInteger(i) ⇒ i.toString
        case BSONLong(l)    ⇒ l.toString
      }

      val name = doc.getAs[String]("name").get
      val categories = doc.getAs[List[Category]]("categories").getOrElse(List())

      Chapter(name, categories)
    }
  }

  implicit object ChapterInfoReader extends BSONDocumentReader[ChapterInfo] {
    def read(doc: BSONDocument): ChapterInfo = {
      val id = doc.get("oracle_id").get

      //TODO fix mongo long/integer conversion
      val theId = id match {
        case BSONInteger(i) ⇒ i.toString
        case BSONLong(l)    ⇒ l.toString
      }

      val name = doc.getAs[String]("name").get

      ChapterInfo(theId, name)
    }
  }
}
