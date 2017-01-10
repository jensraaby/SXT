package com.jensraaby.sxt.data.tree

import com.jensraaby.sxt.SXTSuite

class NTreeSpec extends SXTSuite {

  private val stringTreeInstance = implicitly[Tree[String, NTree]]

  private val firstLeaf = stringTreeInstance.mkLeaf("firstLeaf")
  private val secondLeaf = stringTreeInstance.mkLeaf("secondLeaf")

  "Tree typeclass instance" should "construct a leaf" in {
    val nodeValue = "MyThing"
    val tree = stringTreeInstance.mkLeaf(nodeValue)

    stringTreeInstance.getNode(tree) shouldBe nodeValue
    stringTreeInstance.getChildren(tree) shouldBe Stream.empty
  }

  it should "construct a tree with 2 sub-trees" in {
    val firstNodeValue = "First"

    val tree = stringTreeInstance.mkTree(firstNodeValue)(Stream(firstLeaf, secondLeaf))

    tree.node shouldBe firstNodeValue
    tree.children shouldBe Stream(firstLeaf, secondLeaf)
  }

  it should "change a node" in {
    val initialTree = NTree("MyInitialString", Stream.empty)

    val transformer = (initial: String) => initial.reverse

    val result = stringTreeInstance.changeNode(transformer)(initialTree)
    result.node shouldBe initialTree.node.reverse
  }

  it should "fold over a tree with 2 leaf sub-trees" in {
    val tree = stringTreeInstance.mkTree("Top")(Stream(firstLeaf, secondLeaf))

    val resultSumLength = stringTreeInstance.foldTree[Int]((string: String, acc: Stream[Int]) => acc.sum + string.length)(tree)
    resultSumLength shouldBe firstLeaf.node.length + secondLeaf.node.length + tree.node.length

    val resultConcat = stringTreeInstance.foldTree[String]((string: String, acc: Stream[String]) => string + acc.mkString)(tree)
    resultConcat shouldBe "TopfirstLeafsecondLeaf"
  }
}
