ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.15"
ThisBuild / organization := "com.example"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := "visualPython",
    libraryDependencies ++= Seq(
      guice,
      "org.scalatest"            %% "scalatest"            % "3.2.19" % Test,
      "org.scalatestplus.play"   %% "scalatestplus-play"   % "7.0.1"  % Test
    )
  )
