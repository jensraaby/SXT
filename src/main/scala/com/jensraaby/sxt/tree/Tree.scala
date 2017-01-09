package com.jensraaby.sxt.tree

object Tree {

  def node(xNode: XNode): XmlTree = xNode match {
    case XTag(qname, xmlTrees) => scalaz.Tree.Node(xNode, xmlTrees)
  }

  def leaf(xNode: XNode): XmlTree = scalaz.Tree.Leaf(xNode)
}
