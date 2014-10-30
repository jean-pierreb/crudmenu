package crudmenu.adapters.menu

import crudmenu.utils.ExecutionContextSupport
import spray.http.MediaTypes._
import spray.http.{ HttpEntity, HttpResponse }
import spray.httpx.SprayJsonSupport
import spray.routing.HttpService

trait MenuRoutes extends HttpService with ExecutionContextSupport with SprayJsonSupport with MenuQueries {

  def menuRoutes = pathSingleSlash {
    get {
      getFromResource("html/index.html")
    }
  } ~
    path("menu") {
      complete(getFullMenu())
    } ~
    path("addChapter") {
      formFields('chapter) { (chapter) ⇒
        addChapter(chapter)
        complete(s"Chapter $chapter is added to the database")
      }
    } ~
    path("deleteChapter") {
      formFields('chapterId.as[Int]) { (chapterId) ⇒
        deleteChapter(chapterId)
        complete(s"chapter with ID: $chapterId is removed from the database")
      }
    } ~
    path("showChapter") {
      formFields('chapterId.as[Int]) { (chapterId) ⇒
        complete(showChapter(chapterId))
      }
    } ~
    path("showChapters") {
      get {
        complete(showChapters())
      }
    } ~
    path("addCategory") {
      formFields('chapterId.as[Int], 'category) { (chapterId, category) ⇒
        complete(s"Category $category is added to chapter with Id: $chapterId")
      }
    }
}
