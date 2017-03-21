package com.jensraaby.sxt.decoding

import com.jensraaby.sxt.SXTSuite

import scala.xml.{NodeSeq, Text}

class XmlDecoderSpec extends SXTSuite {

  "Xml Decoder typeclass default instances" should "decode Text node to a String" in {
    val decoder = implicitly[XmlDecoder[String]]

    val exampleString = "Some Text"
    decoder(Text(exampleString)) shouldBe Right(exampleString)
  }

  it should "decode Option[String] automatically" in {
    val decoder = implicitly[XmlDecoder[Option[String]]]

    val exampleString = "1234"
    decoder(Text(exampleString)) shouldBe Right(Some(exampleString))

    decoder(Text("")) shouldBe Right(None)
    decoder(NodeSeq.Empty) shouldBe Right(None)
  }

  it should "decode List[String] automatically" in {
    val decoder = implicitly[XmlDecoder[List[String]]]

    val text = Seq("this", "that")
    val exampleXml: NodeSeq = text.map(t => Text(t))

    decoder(exampleXml) shouldBe Right(text.toList)
  }
}
