package crudmenu.models
import spray.json._

case class Chapter(id: String, name: String, category: List[String] = List(), healthcareRequest: List[String] = List())

object Chapter extends DefaultJsonProtocol{
  implicit val format = jsonFormat4(Chapter.apply)
}