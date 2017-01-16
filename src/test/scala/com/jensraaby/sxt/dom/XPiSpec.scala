package com.jensraaby.sxt.dom

import com.jensraaby.sxt.SXTSuite
import com.jensraaby.sxt.data.tree.NTree

class XPiSpec extends SXTSuite {

  "XML Processing instruction" should "create XML PI" in {
    XPi.xml() shouldBe XPi(QName.simple("xml"), Stream(
      NTree.Inner(XAttr(QName.simple("version")), Stream(NTree.Leaf(XText("1.0")))),
      NTree.Inner(XAttr(QName.simple("encoding")), Stream(NTree.Leaf(XText("UTF-8")))),
      NTree.Inner(XAttr(QName.simple("standalone")), Stream(NTree.Leaf(XText("no"))))
    ))
  }
}
