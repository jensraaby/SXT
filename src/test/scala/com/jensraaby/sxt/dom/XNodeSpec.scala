package com.jensraaby.sxt.dom

import com.jensraaby.sxt.SXTSuite
import com.jensraaby.sxt.data.tree.NTree

class XNodeSpec extends SXTSuite {

  behavior of "XNode"

  it should "generate attributes" in {
    val attributes = List("name" -> "testName")

    XNode.attributes(attributes) shouldBe Stream(NTree.Inner[XNode](XAttr(QName.simple("name")), Stream(NTree.Leaf(XText("testName")))))
  }
}
