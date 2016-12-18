package com.jensraaby.xmlforcats

import com.jensraaby.xmlforcats.XmlNode.{NonPrimaryDocumentChild, XmlComment, XmlElement, XmlProcessingInstruction}
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Gen

trait XmlNodeGenerators {

  val simpleElement: Gen[XmlElement] = for {
    label <- arbitrary[String]
  } yield XmlElement(label, Nil, Set.empty)

  val comment: Gen[XmlComment] = for {
    commentText <- arbitrary[String]
  } yield XmlComment(commentText)

  val processingInstruction: Gen[XmlProcessingInstruction] = for {
    instruction <- arbitrary[String]
  } yield XmlProcessingInstruction(instruction)

  val nonPrimaryDocumentChild: Gen[NonPrimaryDocumentChild] = Gen.oneOf(comment, processingInstruction)

  val nonPrimaryDocumentChildSeq: Gen[Seq[NonPrimaryDocumentChild]] =
    Gen.containerOf[Seq, NonPrimaryDocumentChild](nonPrimaryDocumentChild)

}
