name := "Life Simulation"
version := "1.0"

scalaVersion := "2.12.12"
scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

Compile/mainClass := Some("ghx.simulation.life.SimulationApp")

libraryDependencies ++= {
  val akkaVersion = "2.5.19"
  Seq(
    "ch.qos.logback"       %  "logback-classic" % "1.1.3",
    "org.scalafx"         %%  "scalafx"         % "8.0.102-R11",
    "com.typesafe.akka"   %%  "akka-actor"      % akkaVersion,
    "com.typesafe.akka"   %% "akka-cluster-tools" % akkaVersion,
    "com.typesafe.akka"   %%  "akka-testkit"    % akkaVersion   % "test",
    "org.scalatest"        %  "scalatest_2.11"  % "2.2.4" % "test",
//    "org.specs2"          %%  "specs2-core"     % "2.3.11" % "test"
  )
}
// Determine OS version of JavaFX binaries
lazy val osName = System.getProperty("os.name") match {
  case n if n.startsWith("Linux") => "linux"
  case n if n.startsWith("Mac") => "mac"
  case n if n.startsWith("Windows") => "win"
  case _ => throw new Exception("Unknown platform!")
}

// Add JavaFX dependencies
lazy val javaFXModules = Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
libraryDependencies ++= javaFXModules.map(m =>
  "org.openjfx" % s"javafx-$m" % "11" classifier osName
)

javaOptions / run ++= Seq(
  "--module-path", Properties.javaFxPath,
  "--add-modules=" + javaFXModules.map("javafx."+_).mkString(","))

fork := true
