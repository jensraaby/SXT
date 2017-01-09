package com.jensraaby.sxt


class XmlNodeSpec extends SXTSuite with XmlNodeGenerators {
  import XmlNode._

  "Name method" should "return simple names" in {
    XmlElement("label", Nil, Set.empty).name shouldBe "Element"
    XmlComment("aa").name shouldBe "Comment"
    XmlData("bb").name shouldBe "Data"
    XmlProcessingInstruction("cc").name shouldBe "ProcessingInstruction"
    XmlDocument("cc", None, simpleElement.sample.get, Nil).name shouldBe "Document"
    XmlDocumentRoot(simpleElement.sample.get).name shouldBe "DocumentRoot"
  }

  "is* Methods" should "return correct values for elements" in {
    val element = XmlElement("name", Nil, Set.empty)
    element.isComment shouldBe false
    element.isData shouldBe false
    element.isDocument shouldBe false
    element.isElement shouldBe true
    element.isProcessingInstruction shouldBe false
  }

  it should "return correct values for comments" in {
    val comment = XmlComment("comment")
    comment.isComment shouldBe true
    comment.isData shouldBe false
    comment.isDocument shouldBe false
    comment.isElement shouldBe false
    comment.isProcessingInstruction shouldBe false
  }

  it should "return correct values for data" in {
    val data = XmlData("data")
    data.isComment shouldBe false
    data.isData shouldBe true
    data.isDocument shouldBe false
    data.isElement shouldBe false
    data.isProcessingInstruction shouldBe false
  }

  it should "return correct values for Documents" in {
    val document = XmlDocument("node", None, XmlElement("node", Nil, Set.empty), Nil)
    document.isComment shouldBe false
    document.isData shouldBe false
    document.isDocument shouldBe true
    document.isElement shouldBe false
    document.isProcessingInstruction shouldBe false
  }

  it should "return correct values for DocumentRoot" in {
    val documentRoot = XmlDocumentRoot(XmlElement("node", Nil, Set.empty))
    documentRoot.isComment shouldBe false
    documentRoot.isData shouldBe false
    documentRoot.isDocument shouldBe true
    documentRoot.isElement shouldBe false
    documentRoot.isProcessingInstruction shouldBe false
  }

  it should "return correct values for ProcessingInstructions" in {
    val processingInstruction = XmlProcessingInstruction("version")
    processingInstruction.isComment shouldBe false
    processingInstruction.isData shouldBe false
    processingInstruction.isDocument shouldBe false
    processingInstruction.isElement shouldBe false
    processingInstruction.isProcessingInstruction shouldBe true
  }
}
