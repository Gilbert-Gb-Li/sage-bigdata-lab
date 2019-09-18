name := """dmp-api"""
organization := "com.haimalab.dmp.web"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.0"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3" % Test
libraryDependencies += "com.zaxxer" % "HikariCP" % "2.7.8"
libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.17"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick" % "4.0.2",
  "com.typesafe.play" %% "play-slick-evolutions" % "4.0.2"
)


// https://mvnrepository.com/artifact/com.fasterxml.jackson.module/jackson-module-scala
libraryDependencies += "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.9"

libraryDependencies ++= Seq(
  "com.pauldijou" %% "jwt-play" % "4.0.0"
)