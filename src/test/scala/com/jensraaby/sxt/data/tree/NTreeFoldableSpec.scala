package com.jensraaby.sxt.data.tree

import cats.Foldable
import com.jensraaby.sxt.SXTSuite
import com.jensraaby.sxt.data.tree.NTree.{Inner, Leaf}

class NTreeFoldableSpec extends SXTSuite {

  val instance = implicitly[Foldable[NTree]]

  "NTree foldable typeclass instance foldLeft" should "work correctly for a Leaf" in {
    instance.foldLeft(Leaf("a"), 0)((acc, newValue) => acc + newValue.length) shouldBe 1
  }

  it should "work for a simple Inner node" in {
    val result = instance.foldLeft(Inner("top", Stream(Leaf("leftChild"), Leaf("rightChild"))), "") {
      (acc, newValue) => acc + newValue
    }

    result shouldBe "topleftChildrightChild"
  }
}
