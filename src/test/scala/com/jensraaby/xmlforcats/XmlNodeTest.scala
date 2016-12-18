package com.jensraaby.xmlforcats

import org.scalacheck.Arbitrary
import org.scalacheck.Arbitrary._
import org.scalatest.prop.Checkers
import org.scalatest.{FlatSpec, Matchers}

class XmlNodeTest extends FlatSpec with Matchers with Checkers with XmlNodeGenerators {
  import XmlNode._

  "A Data node" should "simply toString the underlying data" in {
    check { str: String =>
      XmlData(str).toString == str
    }
  }

  "A Comment node" should "wrap the underlying comment c as in <!--c-->" in {
    check { str: String =>
      XmlComment(str).toString == s"<!--$str-->"
    }
  }

  "A Processing Instruction node" should "wrap the instruction i as in <?i?>" in {
    check { str: String =>
      XmlProcessingInstruction(str).toString == s"<?$str?>"
    }
  }

  "An Element node" should "print its label and self close" in {
    check { (label: String) =>
      XmlElement(label, Nil, Set.empty).toString == s"<$label/>"
    }
  }

  "A Document node" should "print its type (basic child)" in {
    check { (label: String) =>
      val child = XmlElement(label, Nil, Set.empty)
      XmlDocument(label, None, child, Nil).toString == s"<!doctype $label>${child.toString}"
    }
  }

  it should "print its type (basic child and URL)" in {
    check { (label: String, url: String) =>
      val child = XmlElement(label, Nil, Set.empty)
      XmlDocument(label, Some(url), child, Nil).toString == s"""<!doctype $label "$url">${child.toString}"""
    }
  }

  implicit val arbitraryNonPrimaryDocumentChildren = Arbitrary(nonPrimaryDocumentChildSeq)
  it should "print all the children (matching child and URL)" in {
    check { (label: String, url: String, otherChildren: Seq[NonPrimaryDocumentChild]) =>
      val child = XmlElement(label, Nil, Set.empty)
      XmlDocument(label, Some(url), child, otherChildren).toString ==
        s"""<!doctype $label "$url">${child.toString}""" + otherChildren.mkString("\n")
    }
  }

  it should "print all the children (basic child)" in {
    implicit val childElement = Arbitrary(simpleElement)
    check { (label: String, child: XmlElement, otherChildren: Seq[NonPrimaryDocumentChild]) =>
      XmlDocument(label, None, child, otherChildren).toString ==
        s"""<!doctype $label>${child.toString}""" + otherChildren.mkString("\n")
    }
  }

  it should "not print itself if it is the root node" in {
    check { (childLabel: String) =>
      val child = XmlElement(childLabel, Nil, Set.empty)
      XmlDocumentRoot(child).toString == child.toString
    }
  }
}
