package crudmenu.menu

import crudmenu.EmbeddedMongoBaseSpec
import crudmenu.adapters.menu.MenuQueries
import org.scalatest.concurrent.ScalaFutures._
import utils.tags.DbTest


import scala.concurrent.duration.DurationInt

@DbTest
class MenuQuerySpec extends EmbeddedMongoBaseSpec with MenuQueries {
  "FullMenuQuery" should {
    "return menu" in {
      whenReady(getFullMenu(), timeout(10 seconds)) { menu ⇒
        menu should have size 2
      }
    }
  }
}
