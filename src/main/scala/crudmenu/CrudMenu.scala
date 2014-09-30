package crudmenu

import spray.http._
import spray.routing.{HttpServiceActor, HttpService}
import MediaTypes._

import scala.concurrent.ExecutionContext

class CrudMenu extends HttpServiceActor with ActorContextCreationSupport with MenuRoute {

  def receive = {
    runRoute(menuRoute)
  }

  implicit def executionContext = actorRefFactory.dispatcher
}


trait MenuRoute extends HttpService
with CreationSupport {

  implicit def executionContext: ExecutionContext

  def menuRoute = pathSingleSlash {
    get {
      complete(index)
    }
  } ~
    path("menu") {
      getFromResource("oneline.json")
    } ~
    path("posting") {
      formFields('chapter) { (chapter) =>
        complete(s"Do you want to add this chapter: $chapter")
      }
    } ~
    path("addChapter") {
      formFields('chapter) { (chapter) =>
        complete(s"Do you want to add this chapter: $chapter to the databases")
      }
    }

  lazy val index = HttpResponse(
    entity = HttpEntity(`text/html`,
      <html>
        <body>
          <h1>Say hello to
            <i>spray-can</i>
            !</h1>
          <p>Defined resources:</p>
          <ul>
            <li>
              <a href="/menu">Het hele menu</a>
            </li>
            <li><a href="/posting">/posting</a></li>
            <li><a href="/addChapter">add chapter without catergory and healthcareRequest</a></li>
            <li><a href="/">add category to chapter without healthcareRequest</a></li>
            <li><a href="/">add healthcareRequest to chapter</a></li>
            <li><a href="/">add healthcareRequest to category</a></li>
            <li><a href="/">remove chapter</a></li>
            <li><a href="/">remove category</a></li>
            <li><a href="/">remove healthcareRequest</a></li>
          </ul>
          <p>Test post message</p>
          <form action="/posting" enctype="multipart/form-data" method="post">
            Chapter:
            <input type="text" name="chapter"></input>
            <br/>
            <input type="submit">Submit</input>
          </form>
        </body>
      </html>.toString()
    )
  )

}



