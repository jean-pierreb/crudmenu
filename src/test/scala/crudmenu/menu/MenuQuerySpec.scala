package crudmenu.menu

import crudmenu.EmbeddedMongoBaseSpec_
import crudmenu.adapters.menu.MenuQueries
import org.scalatest.concurrent.ScalaFutures._
import utils.tags.DbTest

import scala.concurrent.duration.DurationInt

@DbTest
class MenuQuerySpec extends EmbeddedMongoBaseSpec_ with MenuQueries {
  "FullMenuQuery" should {
    "return complete menu" in {
      whenReady(getFullMenu(), timeout(10 seconds)) { menu ⇒
        menu should have size 2
      }
    }
  }

  "DeleteFromMenu" should {
    "delete a chapter from the menu" in {
      val deletion = deleteChapter("536ce83dc353720014000002")
      println(s"This will be deleted: $deletion")
      whenReady(getFullMenu(), timeout(10 seconds)) { menu ⇒
        menu should have size 1
      }
    }
  }
}
