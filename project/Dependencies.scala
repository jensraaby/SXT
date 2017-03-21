import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1"

  lazy val scalaCheck = "org.scalacheck" %% "scalacheck" % "1.13.4"

  lazy val shapeless = "com.chuusai" %% "shapeless" % "2.3.2"

  lazy val cats = "org.typelevel" %% "cats" % "0.9.0"

  lazy val scalaXml = "org.scala-lang.modules" %% "scala-xml" % "1.0.6"
}
