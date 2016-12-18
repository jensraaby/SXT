import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.jensraaby",
      scalaVersion := "2.12.1"
    )),
    name := "shapeless-xml",
    libraryDependencies += scalaTest % Test
  )
