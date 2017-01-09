package com.jensraaby.sxt

import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Gen

trait XmlNodeGenerators {
  import XmlNode._

  val simpleElement: Gen[XmlElement] = for {
    label <- arbitrary[String]
  } yield XmlElement(label, Nil, Set.empty)

  val comment: Gen[XmlComment] = for {
    commentText <- arbitrary[String]
  } yield XmlComment(commentText)

  val data: Gen[XmlData] = for {
    dataText <- arbitrary[String]
  } yield XmlData(dataText)

  val processingInstruction: Gen[XmlProcessingInstruction] = for {
    instruction <- arbitrary[String]
  } yield XmlProcessingInstruction(instruction)

  val nonPrimaryDocumentChild: Gen[NonPrimaryDocumentChild] = Gen.oneOf(comment, processingInstruction)

  val nonPrimaryDocumentChildSeq: Gen[Seq[NonPrimaryDocumentChild]] =
    Gen.containerOf[Seq, NonPrimaryDocumentChild](nonPrimaryDocumentChild)

}
