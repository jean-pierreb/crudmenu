package crudmenu.models

import spray.json.DefaultJsonProtocol

case class Item(id: Int, name: String)

case class Category(id: Int, name: String, items: List[Item])

case class Chapter(id: Int, name: String, categories: List[Category])

case class ChapterTree(chapters: List[Chapter])

trait ChapterMarshalling extends DefaultJsonProtocol {
  implicit val itemFormat = jsonFormat2(Item)
  implicit val categoryFormat = jsonFormat3(Category)
  implicit val chapterFormat = jsonFormat3(Chapter)
  implicit val chapterTreeFormat = rootFormat(lazyFormat(jsonFormat1(ChapterTree)))
}
