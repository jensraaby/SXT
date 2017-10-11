import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.4"

  lazy val scalaCheck = "org.scalacheck" %% "scalacheck" % "1.13.4"

  lazy val shapeless = "com.chuusai" %% "shapeless" % "2.3.2"

  lazy val cats = "org.typelevel" %% "cats-core" % "1.0.0-MF"

  lazy val catsFree = "org.typelevel" %% "cats-free" % "1.0.0-MF"

  lazy val scalaXml = "org.scala-lang.modules" %% "scala-xml" % "1.0.6"
}
