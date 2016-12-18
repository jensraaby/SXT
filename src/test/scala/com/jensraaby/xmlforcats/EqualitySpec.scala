package com.jensraaby.xmlforcats

import cats.Eq
import org.scalacheck.Arbitrary
import org.scalatest.prop.PropertyChecks


class EqualitySpec extends XmlForCatsSuite with XmlNodeGenerators with PropertyChecks {

  import XmlNode._

  "XmlComment" should "equal based on the string" in {
    forAll { (comment1: String, comment2: String) =>
      val xmlComment1 = XmlComment(comment1)
      val xmlComment2 = XmlComment(comment2)

      Eq.eqv[XmlNode](xmlComment1, xmlComment2) should be(comment1 == comment2)
    }
  }

  "XmlData" should "equal based on the string" in {
    implicit val arbitraryData = Arbitrary(data)

    forAll { (data1: XmlData, data2: XmlData) =>
      Eq.eqv[XmlNode](data1, data2) should be(data1.data == data2.data)
    }
  }

  "XmlProcessingInstruction" should "equal based on the string" in {
    implicit val arbitraryInstruction = Arbitrary(processingInstruction)

    forAll { (instr1: XmlProcessingInstruction, instr2: XmlProcessingInstruction) =>
      Eq.eqv[XmlNode](instr1, instr2) should be(instr1.instruction == instr2.instruction)
    }
  }

  "XmlElement" should "equal based on the label" in {
    implicit val arbitrarySimpleElement = Arbitrary(simpleElement)

    forAll { (elem1: XmlElement, elem2: XmlElement) =>
      Eq.eqv[XmlNode](elem1, elem2) shouldBe (
        elem1.label == elem2.label &&
          elem1.attributes == elem2.attributes &&
          elem1.children == elem2.children
        )
    }
  }

  "XmlDocumentRoot" should "equal based on all fields" in {
    implicit val arbitrarySimpleElement = Arbitrary(simpleElement)

    forAll { (data1: XmlElement, data2: XmlElement) =>
      Eq.eqv[XmlNode](XmlDocumentRoot(data1), XmlDocumentRoot(data2)) shouldBe Eq.eqv[XmlNode](data1, data2)
    }
  }

}
