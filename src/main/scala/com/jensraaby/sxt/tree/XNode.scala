package com.jensraaby.sxt.tree

import scalaz.Show

object XNode {
  implicit def showXNode[A <: XNode]: Show[XNode] = Show.showFromToString[XNode]
}

sealed trait XNode

case class XText(text: String) extends XNode
case class XCmt(comment: String) extends XNode
case class XTag(qualifiedName: QName, xmlTrees: XmlTrees) extends XNode
case class XAttr(qualifiedName: QName) extends XNode
case class XCdata(cdata: String) extends XNode
case class XPi(qualifiedName: QName, xmlTrees: XmlTrees) extends XNode

//  case class XDTD(dtdElem: DTDElem, attributes: Attributes) extends XNode

case class QName(namePrefix: String, localPart: String, namespaceUri: String)

object QName {
  def apply(name: String): QName = QName("", name, "")

  def apply(nameAndPrefix: String, namespaceUri: String): QName = {
    val Seq(prefix, name) = nameAndPrefix.split(":").toSeq
    QName(prefix, name, namespaceUri)
  }
}
