package com.jensraaby.sxt.decoding

import java.io.{PrintWriter, StringWriter}

sealed abstract class Error extends Exception

sealed class XmlDecodingFailure(val message: String) extends Error {
  override def toString: String = s"XmlDecodingFailure($message)"

  override def equals(that: scala.Any): Boolean = that match {
    case other: XmlDecodingFailure => other.message == this.message
    case _ => false
  }
}

object XmlDecodingFailure {
  def apply(message: String): XmlDecodingFailure = new XmlDecodingFailure(message)

  def unapply(error: XmlDecodingFailure): Option[String] = error match {
    case failure: XmlDecodingFailure => Some(failure.message)
  }

  def fromThrowable(t: Throwable): XmlDecodingFailure = t match {
    case d: XmlDecodingFailure => d
    case other =>
      val stringWriter = new StringWriter
      val printWriter = new PrintWriter(stringWriter)
      other.printStackTrace(printWriter)
      XmlDecodingFailure(stringWriter.toString)
  }
}