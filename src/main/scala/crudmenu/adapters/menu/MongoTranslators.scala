package crudmenu.adapters.menu

import crudmenu.models.{Category, Item, Chapter}
import reactivemongo.bson._
import reactivemongo.bson.BSONLong

object Menu {

  implicit object ItemReader extends BSONDocumentReader[Item] {
    def read(doc: BSONDocument): Item = {
      val id = doc.get("oracle_id").get

      //TODO fix mongo long/integer conversion
      val theId = id match {
        case BSONInteger(i) ⇒ i.toString
        case BSONLong(l)    ⇒ l.toString
      }

      val name = doc.getAs[String]("name").get

      Item(theId, name)
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
}
