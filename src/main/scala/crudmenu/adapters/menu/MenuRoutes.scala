package crudmenu.adapters.menu

import crudmenu.utils.ExecutionContextSupport
import spray.http.MediaTypes._
import spray.http.{ HttpEntity, HttpResponse }
import spray.httpx.SprayJsonSupport
import spray.routing.HttpService

trait MenuRoutes extends HttpService with ExecutionContextSupport with SprayJsonSupport with MenuQueries {

  def menuRoutes = pathSingleSlash {
    get {
      complete(index)
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
      formFields('chapterId) { (chapterId) ⇒
        complete(showChapter(chapterId))
      }
    } ~
    path("showChapters") {
      get {
        complete(showChapters())
      }
    }

  lazy val index = HttpResponse(
    entity = HttpEntity(`text/html`,
      <html>
        <body>
          <h1>
            Say hello to
            <i>spray-can</i>
            !
          </h1>
          <p>Defined resources:</p>
          <ul>
            <li>
              <a href="/menu">Het hele menu json</a>
            </li>
            <li><a href="/posting">/posting</a></li>
            <li><a href="/addChapter">add chapter without catergory and Item</a></li>
            <li><a href="/deleteChapter">remove chapter</a></li>
            <li><a href="/showChapters">all chapters</a></li>
            <!-- <li><a href="/">add category to chapter without Item</a></li>
            <li><a href="/">add Item to chapter</a></li>
            <li><a href="/">add Item to category</a></li>
            <li><a href="/">remove category</a></li>
            <li><a href="/">remove Item</a></li> -->
          </ul>
          <p>Add Chapter</p>
          <form action="/addChapter" enctype="multipart/form-data" method="post">
            Chapter:
            <input type="text" name="chapter"></input>
            <br/>
            <input type="submit">Submit</input>
          </form>
          <p>Remove Chapter with ID</p>
          <form action="/deleteChapter" enctype="multipart/form-data" method="post">
            Chapter ID:
            <input type="text" name="chapterId"></input>
            <br/>
            <input type="submit">Submit</input>
          </form>
        </body>
      </html>.toString()
    )
  )

}
