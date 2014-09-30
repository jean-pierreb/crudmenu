package crudmenu

import java.util.concurrent.TimeUnit

import akka.actor._
import com.typesafe.config.Config

class Settings(config: Config, extendedSystem: ExtendedActorSystem) extends Extension {

  object Http {
    val Port = config.getInt("crudmenu.http.port")
    val Host = config.getString("crudmenu.http.host")
  }

  val askTimeout = config.getDuration("crudmenu.ask-timeout", TimeUnit.MILLISECONDS)

}


object Settings extends ExtensionId[Settings] with ExtensionIdProvider {
  override def lookup = Settings
  override def createExtension(system: ExtendedActorSystem) = new Settings(system.settings.config, system)
}
