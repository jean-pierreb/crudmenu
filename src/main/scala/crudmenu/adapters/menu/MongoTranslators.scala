package crudmenu.adapters.menu

import crudmenu.models.{ ChapterInfo, Category, Item, Chapter }
import reactivemongo.bson._
import reactivemongo.bson.BSONLong

object Menu {

  implicit object ItemReader extends BSONDocumentReader[Item] {
    def read(doc: BSONDocument): Item = {
      val id = doc.getAs[Int]("id").get
      val name = doc.getAs[String]("name").get

      Item(id, name)
    }
  }

  implicit object CategoryReader extends BSONDocumentReader[Category] {
    def read(doc: BSONDocument): Category = {
      val id = doc.getAs[Int]("id").get
      val name = doc.getAs[String]("name").get
      val items = doc.getAs[List[Item]]("items").getOrElse(List())

      Category(id, name, items)
    }
  }

  implicit object ChapterReader extends BSONDocumentReader[Chapter] {
    def read(doc: BSONDocument): Chapter = {
      val id = doc.getAs[Int]("id").get
      val name = doc.getAs[String]("name").get
      val categories = doc.getAs[List[Category]]("categories").getOrElse(List())

      Chapter(id, name, categories)
    }
  }

  implicit object ChapterInfoReader extends BSONDocumentReader[ChapterInfo] {
    def read(doc: BSONDocument): ChapterInfo = {
      val id = doc.getAs[Int]("id").get
      val name = doc.getAs[String]("name").get

      ChapterInfo(id, name)
    }
  }
}
