name := "Experiment"

version := "1.0"

scalaVersion := "2.10.4"

scalacOptions := Seq("-encoding", "utf8",
  "-target:jvm-1.7",
  "-feature",
  "-language:implicitConversions",
  "-language:postfixOps",
  "-unchecked",
  "-deprecation",
  "-Xlog-reflective-calls"
)

mainClass := Some("crudmenu.Main")

resolvers ++= Seq("Sonatype Releases"   at "http://oss.sonatype.org/content/repositories/releases",
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Spray Repository"    at "http://repo.spray.io/",
  "Spray Nightlies"     at "http://nightlies.spray.io/",
  "Base64 Repo"         at "http://dl.bintray.com/content/softprops/maven")

resolvers += Resolver.url("ReactiveMongo Fixes", url("http://dl.bintray.com/rayroestenburg/reactivemongo-fixes-ssl"))(Resolver.ivyStylePatterns)

libraryDependencies ++= {
  val akkaVersion  = "2.3.4"
  val sprayVersion = "1.3.1"
  Seq(
    "com.typesafe.akka"       %%  "akka-actor"             % akkaVersion,
    "com.typesafe.akka"       %%  "akka-slf4j"             % akkaVersion,
    "io.spray"                %%  "spray-caching"          % sprayVersion,
    "io.spray"                %%  "spray-can"              % sprayVersion,
    "io.spray"                %%  "spray-client"           % sprayVersion,
    "io.spray"                %%  "spray-routing"          % sprayVersion,
    "io.spray"                %%  "spray-json"             % "1.2.6",
    "ch.qos.logback"          %   "logback-classic"        % "1.0.12",
    "com.typesafe.akka"       %%  "akka-testkit"           % akkaVersion    % "test",
    "io.spray"                %%  "spray-testkit"          % sprayVersion   % "test",
    "org.specs2"              %%  "specs2"                 % "2.3.13"       % "test",
    "org.scalatest" %% "scalatest" % "2.2.1" % "test",
    "org.reactivemongo" %% "reactivemongo" % "0.10.0-fixes-ssl-2.3.0",
    "org.reactivemongo" %% "reactivemongo-bson" % "0.10.0",
    "net.fehmicansaglam" %% "reactivemongo-extensions-bson" % "0.10.0.3" excludeAll ExclusionRule(organization = "org.reactivemongo")
  )
}