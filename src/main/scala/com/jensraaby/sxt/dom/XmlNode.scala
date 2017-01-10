package com.jensraaby.sxt.dom


trait XmlNode[A] {

  // Discriminating predicates
  def isText(a: A): Boolean
  def isComment(a: A): Boolean
  def isCdata(a: A): Boolean
  def isPI(a: A): Boolean
  def isElem(a: A): Boolean
  def isRoot(a: A): Boolean
  def isAttr(a: A): Boolean
  def isError(a: A): Boolean
  // Others in HXT: isBlob, isCharRef, isEntityRef, isDTD


  // Constructors for Leaf nodes
  def mkText(string: String): A
  def mkComment(string: String): A
  def mkCdata(string: String): A
//  def mkPI(qName: QName, XmlTrees): A
  def mkError(int: Int, string: String): A
  // Others in HXT: mkCharRef, mkEntityRef


  // Selectors
  def getText(a: A): Option[String]
  def getComment(a: A): Option[String]
  def getCdata(a: A): Option[String]
  def getPIName(a: A): Option[QName]
//  def getPIContent(a: A): Option[XmlTrees]
  def getElemName(a: A): Option[QName]
//  def getAttrl(a: A): Option[XmlTrees]
//  def getDTDPart(a: A): Option[DTDElem]
//  def getDTDAttrl(a: A): Option[Attributes]
  def getAttrName(a: A): Option[QName]
  def getErrorLevel(a: A): Option[Int]
  def getErrorMsg(a: A): Option[String]


  // Derived selectors, with default implementations
  def getName(a: A): Option[QName] = getElemName(a).orElse(getAttrName(a)).orElse(getPIName(a))
  def getQualifiedName(a: A): Option[String] // getName n >>= return . qualifiedName
  def getUniversalName(a: A): Option[String]
  def getUniversalUri(a: A): Option[String]
  def getLocalPart(a: A): Option[String]
  def getNamePrefix(a: A): Option[String]
  def getNamespaceUri(a: A): Option[String]


  // "modifier" functions
  def changeText(transform: (String => String))(a: A): A
  def changeComment(transform: (String => String))(a: A): A
  def changeName(transform: (QName => QName))(a: A): A
  def changeElemName(transform: (QName => QName))(a: A): A
  def changeAttrName(transform: (QName => QName))(a: A): A
  def changePIName (transform: (QName => QName))(a: A): A
  def changeDTDAttrl(transform: (Attributes => Attributes))(a: A): A
  //  def changeBlob         (transform: (Blob   => Blob))(a: A): A
  //  def changeAttrl        (XmlTrees -> XmlTrees) -> a -> a

  def setText(string: String)(a: A): A = changeText(_ => string)(a)
  def setComment(string: String)(a: A): A = changeComment(_ => string)(a)
  def setName(qName: QName)(a: A): A = changeName(_ => qName)(a)
  def setElemName(qName: QName)(a: A): A = changeElemName(_ => qName)(a)
  def setAttrName(qName: QName)(a: A): A = changeAttrName(_ => qName)(a)
  def setPIName(qName: QName)(a: A): A = changePIName(_ => qName)(a)
  //  def setBlob         Blob     -> a -> a
  //  def setElemAttrl    XmlTrees -> a -> a
  //  def setDTDAttrl     Attributes -> a -> a
}

class QName
class Attributes
