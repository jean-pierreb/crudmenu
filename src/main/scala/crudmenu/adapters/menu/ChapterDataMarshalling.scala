package crudmenu.adapters.menu

import crudmenu.models.Chapter
import spray.json.{ DefaultJsonProtocol, RootJsonWriter }

trait ChapterDataMarshalling extends DefaultJsonProtocol {

  case class ChapterData(name: String)

  object ChapterData{
    implicit def fromDomain(data: Chapter) = ChapterData(data.name)
    implicit val format = jsonFormat1(ChapterData.apply)
  }

  implicit val chapterWriter = new RootJsonWriter[Chapter] {
    def write(chapter: Chapter) = ChapterData.format.write(ChapterData.fromDomain(chapter))
  }
}
