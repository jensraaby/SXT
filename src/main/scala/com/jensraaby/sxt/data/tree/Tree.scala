package com.jensraaby.sxt.data.tree

/**
  * Tree typeclass
  * @tparam A - type of the data in nodes
  * @tparam T - type of the container structure representing the tree
  */
trait Tree[A, T[_]] {
  def mkTree(a: A)(children: Stream[T[A]]): T[A]

  def mkLeaf(a: A): T[A] = mkTree(a)(Stream.empty)

  def isLeaf(tree: T[A]): Boolean = getChildren(tree).isEmpty

  def isInner(tree: T[A]): Boolean = !isLeaf(tree)


  def getNode(tree: T[A]): A

  def getChildren(tree: T[A]): Stream[T[A]]


  def changeNode(transform: (A => A))(tree: T[A]): T[A]

  def changeChildren(transform: (Stream[T[A]] => Stream[T[A]]))(tree: T[A]): T[A]

  def setNode(a: A)(tree: T[A]): T[A] = changeNode(_ => a)(tree)

  def setChildren(children: Stream[T[A]])(tree: T[A]): T[A] = changeChildren(_ => children)(tree)

  def foldTree[B](combinator: ((A, Stream[B]) => B))(tree: T[A]): B

  def nodesTree(tree: T[A]): Stream[A] = foldTree[Stream[A]] { (node, childrenResults) =>
    node #:: childrenResults.flatten
  }(tree)

  def depthTree(tree: T[A]): Int = foldTree[Int] { (_, childrenResults) =>
    1 + childrenResults.fold(0)(_ max _)
  }(tree)

  def cardTree(tree: T[A]): Int = foldTree[Int] { (_, childrenResults) =>
    1 + childrenResults.sum
  }(tree)
}
