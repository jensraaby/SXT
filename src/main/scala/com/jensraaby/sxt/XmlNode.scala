package com.jensraaby.sxt

import cats.{Eq, Show}


/**
  * Data model for XML nodes based on https://www.w3.org/XML/Datamodel.html
  */
sealed abstract class XmlNode extends Product with Serializable {
  import XmlNode._

  final def name: String =
    this match {
      case _: XmlComment => "Comment"
      case _: XmlElement => "Element"
      case _: XmlData => "Data"
      case _: XmlDocument => "Document"
      case _: XmlDocumentRoot => "DocumentRoot"
      case _: XmlProcessingInstruction => "ProcessingInstruction"
    }

  def isElement: Boolean
  def isDocument: Boolean
  def isProcessingInstruction: Boolean
  def isComment: Boolean
  def isData: Boolean

  override final def equals(that: Any): Boolean = that match {
    case that: XmlNode => XmlNode.eqNode.eqv(this, that)
    case _ => false
  }

  override final def toString: String = this match {
    case XmlData(data) => data.toString
    case XmlComment(comment) => "<!--" + comment + "-->"
    case XmlProcessingInstruction(instruction) => "<?" + instruction + "?>"
    case XmlElement(label, _, _) => "<" + label + "/>"
    case document: XmlDocument => documentToString(document)
    case XmlDocumentRoot(child) => child.toString
  }

  private def documentToString(xmlDocument: XmlDocument): String = xmlDocument match {
    case XmlDocument(label, Some(url), child, otherChildren) => s"""<!doctype $label "$url">$child${sequenceOfDocumentChildNodesToString(otherChildren)}"""
    case XmlDocument(label, None, child, otherChildren) => s"""<!doctype $label>$child${sequenceOfDocumentChildNodesToString(otherChildren)}"""
  }

  private def sequenceOfDocumentChildNodesToString(nodes: Seq[NonPrimaryDocumentChild]): String = nodes.mkString("\n")
}

final object XmlNode {
  type AttrName = String
  type AttrValue = String
  type ElementLabel = String
  type Url = String
  type Instruction = String
  type Comment = String
  type Data = String

  private[sxt] final case class XmlElement(label: ElementLabel,
                                           children: Seq[XmlNode],
                                           attributes: Set[(AttrName, AttrValue)])
    extends XmlNode {
    override def isElement: Boolean = true
    override def isDocument: Boolean = false
    override def isProcessingInstruction: Boolean = false
    override def isComment: Boolean = false
    override def isData: Boolean = false
  }

  private[sxt] sealed trait NonPrimaryDocumentChild

  private[sxt] final case class XmlDocumentRoot(child: XmlElement) extends XmlNode {
    override def isElement: Boolean = false
    override def isDocument: Boolean = true
    override def isProcessingInstruction: Boolean = false
    override def isComment: Boolean = false
    override def isData: Boolean = false
  }

  //TODO: explore how to enforce the constraint: "if this document node is not the root, its one child that is an element node must be its last child."
  //TODO: merge the primaryChild and otherChildren to maintain ordering - implement the types requirements some other way
  private[sxt] final case class XmlDocument(label: ElementLabel,
                                            url: Option[Url],
                                            primaryChild: XmlElement,
                                            otherChildren: Seq[NonPrimaryDocumentChild])
    extends XmlNode {
    override def isElement: Boolean = false
    override def isDocument: Boolean = true
    override def isProcessingInstruction: Boolean = false
    override def isComment: Boolean = false
    override def isData: Boolean = false
  }

  private[sxt] final case class XmlProcessingInstruction(instruction: Instruction) extends XmlNode with NonPrimaryDocumentChild {
    override def isElement: Boolean = false
    override def isDocument: Boolean = false
    override def isProcessingInstruction: Boolean = true
    override def isComment: Boolean = false
    override def isData: Boolean = false
  }


  private[sxt] final case class XmlComment(comment: String) extends XmlNode with NonPrimaryDocumentChild {
    override def isElement: Boolean = false
    override def isDocument: Boolean = false
    override def isProcessingInstruction: Boolean = false
    override def isComment: Boolean = true
    override def isData: Boolean = false
  }

  private[sxt] final case class XmlData(data: String) extends XmlNode {
    override def isElement: Boolean = false
    override def isDocument: Boolean = false
    override def isProcessingInstruction: Boolean = false
    override def isComment: Boolean = false
    override def isData: Boolean = true
  }

  implicit final val eqNode: Eq[XmlNode] = Eq.instance {
    case (XmlComment(a), XmlComment(b)) => a == b
    case (XmlData(a), XmlData(b)) => a == b
    case (XmlProcessingInstruction(a), XmlProcessingInstruction(b)) => a == b
    case (a: XmlElement, b: XmlElement) => (a.label == b.label) && (a.children == b.children) && (a.attributes == b.attributes)
    case (XmlDocumentRoot(a), XmlDocumentRoot(b)) => a == b
    case _ => false
  }

  implicit final val showNode: Show[XmlNode] = Show.fromToString[XmlNode]
}