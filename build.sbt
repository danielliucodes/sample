import Dependencies._

name := "zendesk-datadog-sample"

version := "0.1"

scalaVersion := "2.12.8"

lazy val root = (project in file("."))
  .settings(
    name := "Sample",
    libraryDependencies ++= dependencies
  )
