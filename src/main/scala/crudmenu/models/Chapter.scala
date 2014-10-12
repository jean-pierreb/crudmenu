package crudmenu.models

import spray.json.DefaultJsonProtocol

case class Chapter(name: String)

trait ChapterMarshalling extends DefaultJsonProtocol{
  implicit val chapterFormat = jsonFormat1(Chapter)
}
