import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1"

  lazy val scalaCheck = "org.scalacheck" %% "scalacheck" % "1.13.4"

  lazy val shapeless = "com.chuusai" %% "shapeless" % "2.3.2"

  lazy val cats = "org.typelevel" %% "cats" % "0.8.1"

  lazy val scalaz = "org.scalaz" %% "scalaz-core" % "7.2.8"
}
