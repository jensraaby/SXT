# xmlforcats
Trying to make an XML library for Scala using Cats

The name of this repo/library is temporary. 
I have a lot of respect for the Travis Brown's Circe library, which was originally called "JSON for Cats", hence "XML for Cats".


## The Plan

Initially the goal is to create the data types to represent XML.

With these it should be possible to serialise any XML tree.

Once that mechanism is in place, I'll look at encoding. For example:
- From case class to Xml Element
- From case class to Xml Document

The next obvious task is to decode from Xml Elements to case classes.

If the above goes to plan, then it would be nice to start implementing some of the features of Scala XML - namely pattern matching and transformers.


## Testing
I'm using ScalaTest 3 and ScalaCheck. 

Any test cases that can use ScalaCheck should use it for exhaustiveness 

## Dependencies
I've already depended on Shapeless and Cats, but these have not been used extensively.
I'll try not to add many more dependencies to keep it simple.
