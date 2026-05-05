ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.14"
ThisBuild / organization := "com.example"

lazy val root = (project in file("."))
  .settings(
    name := "visualPython",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19" % Test
  )
