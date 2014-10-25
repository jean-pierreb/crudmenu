package crudmenu.menu

import crudmenu.EmbeddedMongoBaseSpec_
import crudmenu.adapters.menu.MenuQueries
import org.scalatest.concurrent.ScalaFutures._
import org.specs2.mutable.After
import utils.tags.DbTest
import scala.concurrent.duration.DurationInt

@DbTest
class MenuQuerySpec extends EmbeddedMongoBaseSpec_ with MenuQueries {

  trait MongoBaseScope extends After {
    initData()
    def after = cleanUpDB()
  }

  "FullMenuQuery" should {
    "return complete menu" in {
      whenReady(getFullMenu(), timeout(10 seconds)) { menu ⇒
        menu should have size 2
      }
      cleanUpDB()
    }
  }

  "AddToMenu" should {
    "add a chapter to the menu" in new MongoBaseScope {
      val addition = addChapter("Chapter 3")
      println(s"This chapter will be added: $addition")
      whenReady(getFullMenu(), timeout(20 seconds)) { menu ⇒
        menu should have size 3
      }
    }
  }

  "DeleteFromMenu" should {
    "delete a chapter from the menu" in new MongoBaseScope {
      val deletion = deleteChapter("536ce83dc353720014000002")
      println(s"This will be deleted: $deletion")
      whenReady(getFullMenu(), timeout(20 seconds)) { menu ⇒
        menu should have size 1
      }
    }
  }

  "ShowMenuElements" should {
    "show chapter" in new MongoBaseScope {
      whenReady(showChapter("536ce83dc353720014000002"), timeout(10 seconds)) { chapter ⇒
        chapter.value.name shouldEqual "Chapter 2"
      }
    }
    "show all chapters" in new MongoBaseScope {
      whenReady(showChapters(), timeout(10 seconds)) { chapters ⇒
        chapters should have size 2
      }
    }
  }
}
