package crudmenu.models

import spray.json.DefaultJsonProtocol

case class Item(id: String, name: String)

case class Category(name: String, items: List[Item])

case class Chapter(name: String, categories: List[Category])

case class ChapterTree(chapters: List[Chapter])

trait ChapterMarshalling extends DefaultJsonProtocol{
  implicit val itemFormat = jsonFormat2(Item)
  implicit val categoryFormat = jsonFormat2(Category)
  implicit val chapterFormat = jsonFormat2(Chapter)
  implicit val chapterTreeFormat = rootFormat(lazyFormat(jsonFormat1(ChapterTree)))
}
