package crudmenu
import java.net.{ ConnectException, Socket, SocketException }

import scala.util.Random

/**
 * Adopted from [[https://github.com/scalatra/scalatra-netty/blob/master/src/test/scala/org/scalatra/tests/FreePort.scala]]
 */
object FreePort {

  def isPortFree(port: Int) = {
    try {
      val socket = new Socket("127.0.0.1", port)
      socket.close()
      false
    } catch {
      case e: ConnectException                                              ⇒ true
      case e: SocketException if e.getMessage == "Connection reset by peer" ⇒ true
    }
  }

  private def newPort = Random.nextInt(55365) + 10000

  def randomFreePort(maxRetries: Int = 50) = {
    var count = 0
    var freePort = newPort
    while (!isPortFree(freePort)) {
      freePort = newPort
      count += 1
      if (count >= maxRetries) {
        throw new RuntimeException("Couldn't determine a free port")
      }
    }
    freePort
  }
}