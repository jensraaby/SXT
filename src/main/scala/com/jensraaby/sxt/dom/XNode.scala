package com.jensraaby.sxt.dom

import com.jensraaby.sxt.data.tree.NTree
import com.jensraaby.sxt.dom.XNode.XmlTrees


sealed trait XNode
case class XText(string: String) extends XNode
case class XComment(string: String) extends XNode
case class XCdata(string: String) extends XNode
case class XTag(qName: QName, attributes: XNode.XmlTrees) extends XNode
case class XPi(qName: QName, attributes: XmlTrees) extends XNode
case class XAttr(qName: QName) extends XNode
case class XError(level: Int, text: String) extends XNode

object XPi {
  def xml(): XNode = {
    val attributes = XNode.attributes(List("version" -> "1.0", "encoding" -> "UTF-8", "standalone" -> "no"))
    XPi(QName.simple("xml"), attributes)
  }
}

object XNode {
  type XmlTree = NTree[XNode]
  type XmlTrees = Stream[XmlTree]

  def attribute(name: String, value: String): XmlTree = NTree.Inner[XNode](XAttr(QName.simple(name)), Stream(NTree.Leaf(XText(value))))
  def attributes(attrs: Traversable[(String, String)]): XmlTrees = attrs.toStream map { case (name, value) => attribute(name, value) }
}

case class QName(localPart: XName, namePrefix: XName, namespaceUri: XName)
object QName {
  def simple(localPart: String): QName = QName(XName(localPart), XName.empty, XName.empty)
}

case class XName(name: String) extends AnyVal
object XName {
  val empty = XName("")
}
