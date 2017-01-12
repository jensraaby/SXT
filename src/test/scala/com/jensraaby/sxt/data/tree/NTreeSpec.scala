package com.jensraaby.sxt.data.tree

import com.jensraaby.sxt.SXTSuite
import com.jensraaby.sxt.data.tree.NTree.{Inner, Leaf}

class NTreeSpec extends SXTSuite {

  private val stringTreeInstance = implicitly[Tree[String, NTree]]

  private val firstLeaf = stringTreeInstance.mkLeaf("firstLeaf")
  private val secondLeaf = stringTreeInstance.mkLeaf("secondLeaf")
  private val treeWith2Children = stringTreeInstance.mkTree("Top")(Stream(firstLeaf, secondLeaf))

  "NTree Leaf" should "unapply" in {
    val simpleLeaf = NTree(1, Stream.empty)

    val unfoldedValue = simpleLeaf match {
      case Leaf(nodeValue) => nodeValue
      case _ => ???
    }

    unfoldedValue shouldBe simpleLeaf.node
  }

  it should "apply" in {
    val leaf = Leaf("hello")
    leaf shouldBe NTree("hello", Stream.empty)
  }

  "NTree Inner" should "unapply" in {
    val simpleInnerNode = NTree("SomeThingStringy", Stream(firstLeaf))

    val unfoldedValue = simpleInnerNode match {
      case Inner(nodeValue, children) => NTree(nodeValue, children)
      case _ => ???
    }

    unfoldedValue shouldBe simpleInnerNode
  }

  it should "apply from a Traversable" in {
    val inner = Inner("topString", Seq(firstLeaf, secondLeaf))
    inner shouldBe NTree("topString", Stream(firstLeaf, secondLeaf))
  }

  it should "apply from a Stream" in {
    val inner = Inner("StreamyString", Stream(firstLeaf, secondLeaf))
    inner shouldBe NTree("StreamyString", Stream(firstLeaf, secondLeaf))
  }

  "Tree typeclass instance" should "construct a leaf" in {
    val nodeValue = "MyThing"
    val tree = stringTreeInstance.mkLeaf(nodeValue)

    stringTreeInstance.getNode(tree) shouldBe nodeValue
    stringTreeInstance.getChildren(tree) shouldBe Stream.empty
    assert(stringTreeInstance.isLeaf(tree))
  }

  it should "construct a tree with 2 sub-trees" in {
    val firstNodeValue = "First"

    val tree = stringTreeInstance.mkTree(firstNodeValue)(Stream(firstLeaf, secondLeaf))

    tree.node shouldBe firstNodeValue
    tree.children shouldBe Stream(firstLeaf, secondLeaf)
    assert(stringTreeInstance.isInner(tree))
  }

  it should "change a node" in {
    val initialTree = NTree("MyInitialString", Stream.empty)

    val transformer = (initial: String) => initial.reverse

    val result = stringTreeInstance.changeNode(transformer)(initialTree)
    result.node shouldBe initialTree.node.reverse
  }

  it should "fold over a tree with 2 leaf sub-trees" in {
    val tree = treeWith2Children

    val resultSumLength = stringTreeInstance.foldTree[Int]((string: String, acc: Stream[Int]) => acc.sum + string.length)(tree)
    resultSumLength shouldBe firstLeaf.node.length + secondLeaf.node.length + tree.node.length

    val resultConcat = stringTreeInstance.foldTree[String]((string: String, acc: Stream[String]) => string + acc.mkString)(tree)
    resultConcat shouldBe "TopfirstLeafsecondLeaf"
  }

  it should "get all nodes" in {
    stringTreeInstance.nodesTree(treeWith2Children) shouldBe Stream("Top", "firstLeaf", "secondLeaf")
  }

  it should "compute the depth" in {
    stringTreeInstance.depthTree(firstLeaf) shouldBe 1
    stringTreeInstance.depthTree(treeWith2Children) shouldBe 2
  }

  it should "compute the number of nodes" in {
    stringTreeInstance.cardTree(firstLeaf) shouldBe 1
    stringTreeInstance.cardTree(treeWith2Children) shouldBe 3
  }
}
