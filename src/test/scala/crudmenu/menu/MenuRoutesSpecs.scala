package crudmenu.menu

import crudmenu.adapters.menu.{ MenuDataMarshalling, MenuRoutes }
import crudmenu.{ EmbeddedMongoBaseSpec_, RouteBaseSpec }
import spray.http.FormData
import spray.http.MediaTypes._
import spray.http.StatusCodes._

class MenuRoutesSpecs extends RouteBaseSpec with MenuRoutes with MenuDataMarshalling with EmbeddedMongoBaseSpec_ {

  "CrudMenu API" when {
    "looking up menu" should {
      "return a tree of menu items" in {
        Get("/menu") ~> menuRoutes ~> check {
          status shouldEqual OK
          mediaType shouldEqual `application/json`
          val menu = responseAs[List[ChapterData]]
          menu.size shouldBe 2

          val chapter1 = menu(0)
          chapter1.name shouldEqual "Chapter 1"
          chapter1.categories.size shouldEqual 1

          val categories1 = chapter1.categories(0)
          categories1.name shouldEqual "Cat 1"
          categories1.items.size shouldEqual 1

          val item1 = categories1.items(0)
          item1.id shouldEqual 1
          item1.name shouldEqual "Item 1"
        }
      }
    }

    "posting a chapter" should {
      "add a chapter to dB" in {
        Post("/addChapter", FormData(Seq("chapter" -> "Hoofdstuk 1"))) ~> menuRoutes ~> check {
          status shouldEqual OK
          val chapter = responseAs[String]
          chapter shouldEqual "Chapter Hoofdstuk 1 is added to the database"
        }
      }
    }

    "deleting a chapter" should {
      "delete a chapter by id" in {
        Delete("/deleteChapter", FormData(Seq("chapterId" -> "1"))) ~> menuRoutes ~> check {
          status shouldEqual OK
          val chapter = responseAs[String]
          chapter shouldEqual "chapter with ID: 1 is removed from the database"
        }
      }
    }

    "showing a chapter" should {
      "show a chapter by id" in {
        Get("/showChapter", FormData(Seq("chapterId" -> "536ce83dc353720014000001"))) ~> menuRoutes ~> check {
          status shouldEqual OK
          mediaType shouldEqual `application/json`
          val chapter = responseAs[ChapterData]
          chapter.name shouldEqual "Chapter 1"
        }
      }
    }

    "looking up chapters" should {
      "return a tree of all chapters" in {
        Get("/showChapters") ~> menuRoutes ~> check {
          status shouldEqual OK
          mediaType shouldEqual `application/json`
          val chapters = responseAs[List[ChapterInfoData]]
          chapters.size shouldEqual 3
        }
      }
    }
  }
}