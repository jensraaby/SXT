import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.jensraaby",
      scalaVersion := "2.12.3"
    )),
    name := "SXT",
    libraryDependencies ++= Seq(scalaTest, scalaCheck) map (_ % Test),
    libraryDependencies ++= Seq(cats, shapeless, scalaXml)
  )
