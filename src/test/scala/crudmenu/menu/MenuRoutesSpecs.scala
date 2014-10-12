package crudmenu.menu

import crudmenu.adapters.menu.{ChapterDataMarshalling, MenuRoutes}
import crudmenu.RouteBaseSpec
import spray.http.FormData
import spray.http.MediaTypes._
import spray.http.StatusCodes._

class MenuRoutesSpecs extends RouteBaseSpec with MenuRoutes with ChapterDataMarshalling{

"CrudMenu API" when {
  "looking up menu" should {
    "return a tree of menu items" in {
      Get("/menu") ~> menuRoutes ~> check {
        status shouldEqual OK
        mediaType shouldEqual `application/json`
        val menu = responseAs[String]
        menu shouldEqual "{\"name\": \"Hoofdstuk\"}"
      }
    }
  }

  "posting a chapter" should {
    "add a chapter to dB" in {
      Post("/addChapter", FormData(Seq("chapter" -> "Hoofdstuk 1"))) ~> menuRoutes ~> check {
        status shouldEqual OK
        val chapter = responseAs[String]
        chapter shouldEqual "Do you want to add this chapter: Hoofdstuk 1 to the databases"
      }
    }
  }

  "deleting a chapter" should {
    "delete a chapter by id" in {
      Delete("/deleteChapter", FormData(Seq("chapterId" -> "1"))) ~> menuRoutes ~> check {
        status shouldEqual OK
        val chapter = responseAs[String]
        chapter shouldEqual "Do you want to remove this a chapter with ID: 1 from the database"
      }
    }
  }

  "looking up chapters" should {
    "return a tree of all chapters" in {
      Get("/showChapters") ~> menuRoutes ~> check {
        status shouldEqual OK
        mediaType shouldEqual `application/json`
        val chapter = responseAs[ChapterData]
        chapter.name shouldEqual "Hoofdstuk"
      }
    }
  }

}
}