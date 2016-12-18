package com.jensraaby.xmlforcats

import org.scalacheck.Arbitrary


class ShowSpec extends XmlForCatsSuite with XmlNodeGenerators {

  import cats.syntax.show._

  private def showNode(node: XmlNode): String = node.show

  import XmlNode._

  "A Data node" should "simply toString the underlying data" in {
    check { str: String =>
      showNode(XmlData(str)) == str
    }
  }

  "A Comment node" should "wrap the underlying comment c as in <!--c-->" in {
    check { str: String =>
      showNode(XmlComment(str)) == s"<!--$str-->"
    }
  }

  "A Processing Instruction node" should "wrap the instruction i as in <?i?>" in {
    check { str: String =>
      showNode(XmlProcessingInstruction(str)) == s"<?$str?>"
    }
  }

  "An Element node" should "print its label and self close" in {
    check { (label: String) =>
      showNode(XmlElement(label, Nil, Set.empty)) == s"<$label/>"
    }
  }

  "A Document node" should "print its type (basic child)" in {
    check { (label: String) =>
      val child = XmlElement(label, Nil, Set.empty)
      showNode(XmlDocument(label, None, child, Nil)) == s"<!doctype $label>${child}"
    }
  }

  it should "print its type (basic child and URL)" in {
    check { (label: String, url: String) =>
      val child = XmlElement(label, Nil, Set.empty)
      showNode(XmlDocument(label, Some(url), child, Nil)) == s"""<!doctype $label "$url">${child}"""
    }
  }

  implicit val arbitraryNonPrimaryDocumentChildren: Arbitrary[Seq[NonPrimaryDocumentChild]] = Arbitrary(nonPrimaryDocumentChildSeq)
  it should "print all the children (matching child and URL)" in {
    check { (label: String, url: String, otherChildren: Seq[NonPrimaryDocumentChild]) =>
      val child = XmlElement(label, Nil, Set.empty)
      showNode(XmlDocument(label, Some(url), child, otherChildren)) ==
        s"""<!doctype $label "$url">${child.toString}""" + otherChildren.mkString("\n")
    }
  }

  it should "print all the children (basic child)" in {
    implicit val childElement = Arbitrary(simpleElement)
    check { (label: String, child: XmlElement, otherChildren: Seq[NonPrimaryDocumentChild]) =>
      showNode(XmlDocument(label, None, child, otherChildren)) ==
        s"""<!doctype $label>${child.toString}""" + otherChildren.mkString("\n")
    }
  }

  it should "not print itself if it is the root node" in {
    check { (childLabel: String) =>
      val child = XmlElement(childLabel, Nil, Set.empty)
      showNode(XmlDocumentRoot(child)) == child.toString
    }
  }
}
