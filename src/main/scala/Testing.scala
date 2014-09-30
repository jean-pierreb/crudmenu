import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object Testing extends App{

  val firstOccurrence = Future  {

    val source = scala.io.Source.fromFile("/Users/bakaman/Documents/Projecten-IntelliJ/Experiment/src/main/resources/myText.txt")
    source.toSeq.indexOfSlice("myKeyword")
  }

  println("file had to be read or not")
  firstOccurrence.onComplete {
    case Success(index) => println(s"this has to be printed $index")
    case Failure(e) => e.printStackTrace()
  }
  println("..."); sleep(1000)
  println("either succes or failure")

  def sleep(duration: Long) { Thread.sleep(duration) }
}
