package crudmenu.adapters.menu

import crudmenu.models._
import spray.json.{ DefaultJsonProtocol, RootJsonWriter }

trait MenuDataMarshalling extends DefaultJsonProtocol {

  case class ItemData(id: Int, name: String)

  object ItemData {
    implicit def fromDomain(data: Item) = ItemData(data.id, data.name)
    implicit val format = jsonFormat2(ItemData.apply)
  }

  case class CategoryData(name: String, items: List[ItemData])

  object CategoryData {
    implicit def fromDomain(data: Category) = CategoryData(data.name, data.items.map(ItemData.fromDomain(_)))
    implicit val format = jsonFormat2(CategoryData.apply)
  }

  case class ChapterData(name: String, categories: List[CategoryData])

  object ChapterData {
    implicit def fromDomain(data: Chapter) = ChapterData(data.name, data.categories.map(CategoryData.fromDomain(_)))
    implicit val format = jsonFormat2(ChapterData.apply)
  }

  case class ChapterInfoData(id: Int, name: String)

  object ChapterInfoData {
    implicit def fromDomain(data: Chapter) = ChapterInfoData(data.id, data.name)
    implicit val format = jsonFormat2(ChapterInfoData.apply)
  }

  case class ChapterTreeData(chapters: List[ChapterData])

  object ChapterTreeData {
    implicit def fromDomain(data: ChapterTree) = ChapterTreeData(data.chapters.map(ChapterData.fromDomain(_)))
    implicit val format = jsonFormat1(ChapterTreeData.apply)
  }

  implicit val itemWriter = new RootJsonWriter[Item] {
    def write(item: Item) = ItemData.format.write(ItemData.fromDomain(item))
  }

  implicit val categoryWriter = new RootJsonWriter[Category] {
    def write(category: Category) = CategoryData.format.write(CategoryData.fromDomain(category))
  }

  implicit val chapterWriter = new RootJsonWriter[Chapter] {
    def write(chapter: Chapter) = ChapterData.format.write(ChapterData.fromDomain(chapter))
  }

  implicit val chapterInfoWriter = new RootJsonWriter[Chapter] {
    def write(chapter: Chapter) = ChapterInfoData.format.write(ChapterInfoData.fromDomain(chapter))
  }

  implicit val chapterTreeWriter = new RootJsonWriter[ChapterTree] {
    def write(chapterTree: ChapterTree) = ChapterTreeData.format.write(ChapterTreeData.fromDomain(chapterTree))
  }
}
