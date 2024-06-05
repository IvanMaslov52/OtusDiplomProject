
name := "scr-2024-02-play"

organization := "ru.otus"

version := "0.1"

scalaVersion := "2.11.5"

libraryDependencies += "com.google.inject" % "guice" % "4.2.3"

val sharedSettings = List(
  version := "0.1",
  scalaVersion := "2.11.5"
)
lazy val db = Project(id = "db", base = file("modules/db"))
  .settings(sharedSettings :_*)
  .settings(
    libraryDependencies += "com.typesafe" % "config" % "1.4.3",
    libraryDependencies ++= Seq(
      "org.liquibase" % "liquibase-core" % "3.4.2",
      "org.squeryl" %% "squeryl" % "0.9.5-7",
      "com.zaxxer" % "HikariCP" % "4.0.1",
      "org.postgresql" % "postgresql" % "42.3.1"
    )
  )

lazy val di = Project(id = "di", base = file("modules/di"))
  .settings(sharedSettings :_*)
  .settings(libraryDependencies += "net.codingwell" %% "scala-guice" %  "4.0.0")

lazy val scr = Project(id = "scr", base = file("modules/scr"))
  .dependsOn(di,db)
  .settings(libraryDependencies ++= Seq(
    "com.jason-goodwin" %% "authentikat-jwt" % "0.4.5"
  ))
  .settings(sharedSettings :_*)
  .enablePlugins(PlayScala)


lazy val root = (project in file("."))
  .settings(sharedSettings :_*)
  .dependsOn(scr)
  .aggregate(di)
  .enablePlugins(PlayScala)