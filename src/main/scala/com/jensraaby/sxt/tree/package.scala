package com.jensraaby.sxt

import scalaz.Tree


package object tree {
  type XmlTree = Tree[XNode]

  type XmlTrees = Stream[XmlTree]

  type Attributes = Set[(String, String)]
}
