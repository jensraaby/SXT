package com.jensraaby.sxt.decoding

import scala.xml.NodeSeq

trait XmlDecoder[A] {
  def apply(node: NodeSeq): XmlDecoder.Result[A]

}

final object XmlDecoder {
  type Result[A] = Either[XmlDecodingFailure, A]

  implicit object StringDecoder extends XmlDecoder[String] {
    override def apply(node: NodeSeq): Result[String] = node.text match {
      case "" => Left(XmlDecodingFailure(s"No text in input: $node"))
      case text => Right(text)
    }
  }

  implicit def optionDecoder[A: XmlDecoder]: XmlDecoder[Option[A]] = new XmlDecoder[Option[A]] {
    private val aDecoder = implicitly[XmlDecoder[A]]
    override def apply(node: NodeSeq) = Right(aDecoder(node).toOption)
  }

  implicit def listDecoder[A: XmlDecoder]: XmlDecoder[List[A]] = new XmlDecoder[List[A]] {
    private val aDecoder = implicitly[XmlDecoder[A]]
    import cats.implicits._
    override def apply(node: NodeSeq) = node.map(aDecoder.apply).toList.sequence
  }
}
