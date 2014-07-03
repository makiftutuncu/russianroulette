name := "RussianRoulette"

version := "1.0"

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.3.4",
  "com.typesafe.akka" %% "akka-testkit" % "2.3.4",
  "org.scalatest" % "scalatest_2.11" % "2.2.0" % "test"
)

scalacOptions += "-feature"
    