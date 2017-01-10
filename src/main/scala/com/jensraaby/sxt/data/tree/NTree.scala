package com.jensraaby.sxt.data.tree


/**
  * NTree: an ordered rose tree.
  *
  * A tree is a node and a possibly empty list of children.
  * If there are no children, the tree is a leaf, otherwise it is an inner node.
  *
  * @param node: the element at this tree node
  * @param children: the sub-trees of the current node
  * @tparam A: type of elements in the tree
  */
case class NTree[A](node: A, children: Stream[NTree[A]])

object NTree {

  def treeInstance[A] = new Tree[A, NTree[A]] {
    override def mkTree(a: A)(children: Stream[NTree[A]]): NTree[A] = NTree(a, children)

    override def getNode(tree: NTree[A]): A = tree.node

    override def getChildren(tree: NTree[A]): Stream[NTree[A]] = tree.children

    override def changeNode(transform: (A) => A)(tree: NTree[A]): NTree[A] = tree.copy(node = transform(tree.node))

    override def changeChildren(transform: (Stream[NTree[A]]) => Stream[NTree[A]])(tree: NTree[A]): NTree[A] =
      tree.copy(children = transform(tree.children))

    override def foldTree[B](combinator: (A, Seq[B]) => B)(tree: NTree[A]): B =
      combinator(tree.node, tree.children.map(foldTree(combinator)))
  }
}
