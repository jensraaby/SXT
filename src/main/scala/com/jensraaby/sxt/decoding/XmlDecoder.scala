package com.jensraaby.sxt.decoding

import scala.xml.NodeSeq

trait XmlDecoder[A] {
  def apply(node: NodeSeq): XmlDecoder.Result[A]

}

final object XmlDecoder {
  type Result[A] = Either[XmlDecodingFailure, A]
}
