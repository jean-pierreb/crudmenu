package crudmenu

import java.util.logging.Logger

import com.github.simplyscala.{ MongoEmbedDatabase, MongodProps }
import crudmenu.utils.ExecutionContextSupport
import de.flapdoodle.embed.mongo.MongodStarter
import de.flapdoodle.embed.mongo.config.{ MongodConfig, MongodProcessOutputConfig, RuntimeConfig }
import de.flapdoodle.embed.mongo.distribution.Version
import org.slf4j.bridge.SLF4JBridgeHandler
import reactivemongo.api.{ DefaultDB, MongoConnection, MongoDriver }
import reactivemongo.extensions.bson.fixtures.BsonFixtures

import scala.concurrent.Await
import scala.concurrent.duration.DurationLong

object EmbeddedMongoBaseSpec {

  lazy val loggingInitialized = {
    // The mongod runtime uses a JUL logger, redirect to SLF4J
    SLF4JBridgeHandler.removeHandlersForRootLogger()
    SLF4JBridgeHandler.install()
  }
}

//Used directly by the tests for faster compile (http://scalatest.org/user_guide/defining_base_classes)
abstract class EmbeddedMongoBaseSpec extends EmbeddedMongoBaseSpec_

//Used as base to mix in with other Zorgdomein base specs
trait EmbeddedMongoBaseSpec_ extends BaseSpec with MongoEmbedDatabase with ExecutionContextSupport {

  EmbeddedMongoBaseSpec.loggingInitialized

  //TODO see if this is an efficent way of using the DB

  private val port = FreePort.randomFreePort()

  private var embeddedDb: DefaultDB = null
  def db: DefaultDB = embeddedDb
  private var mongoProps: MongodProps = null
  private var driver: MongoDriver = null

  override implicit def executionContext = driver.system.dispatcher

  private lazy val mongoRuntimeConfig = {
    val config = new RuntimeConfig()
    config.setProcessOutput(MongodProcessOutputConfig.getInstance(Logger.getLogger("[mongod output]")))
    config
  }
  private lazy val mongoRuntime = MongodStarter.getInstance(mongoRuntimeConfig)

  override protected def mongoStart(port: Int, version: Version) = {
    val mongodExe = mongoRuntime.prepare(new MongodConfig(version, port, true))
    MongodProps(mongodExe.start(), mongodExe)
  }

  override protected def beforeAll() {
    mongoProps = mongoStart(port)
    initDb()
    initData()
  }

  override protected def afterAll() {
    mongoStop(mongoProps)
  }

  private def initDb() {
    driver = new MongoDriver()
    val connection: MongoConnection = driver.connection(List(s"localhost:$port"))
    embeddedDb = connection("Experimental")
  }

  def initData() {
    Await.result(BsonFixtures(embeddedDb).load("menu.conf"), 5 seconds)
  }
}
