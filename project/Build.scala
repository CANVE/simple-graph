import sbt._
import Keys._

object BuildSettings {
  val buildSettings = Defaults.defaultSettings ++ Seq(
    name := "simple-graph",
    organization := "canve",
    version := "0.0.1",
    scalacOptions ++= Seq("-deprecation"),
    scalaVersion := "2.11.7",
    crossScalaVersions := Seq("2.10.4", "2.11.6"),
    resolvers += Resolver.sonatypeRepo("snapshots"),
    resolvers += Resolver.sonatypeRepo("releases"),
    libraryDependencies ++= Seq("com.lihaoyi" %% "utest" % "0.3.1" % "test"),
    testFrameworks += new TestFramework("utest.runner.Framework"))
}

object MyBuild extends Build {
  import BuildSettings._

  lazy val root: Project = Project(
    "root",
    file("."),
    settings = buildSettings 
  )
}
