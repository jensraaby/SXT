# Scala XML Toolbox
[![Build Status](https://travis-ci.org/jensraaby/SXT.svg?branch=master)](https://travis-ci.org/jensraaby/SXT)

The name of this repo/library is temporary. 


## The Plan

There are 2 main uses for an XML library in Scala:
- Serialisation to/from case classes
- Querying and transforming XML documents

Scala XML is a somewhat outdated library, which has now been separated from the core of the language.
This has left those of us who deal XML day-to-day in a state of frustration when we try to work around some of the quirks. 

### Inspiration & compatibility

I'm looking at [HXT](https://wiki.haskell.org/HXT#Background) and [Circe](https://circe.github.io/circe/) for inspiration.

I will probably provide conversion methods from/to Scala XML initially before I have worked out parsing/serialisation fully. 

## Phase 1: Data structures
Initially the goal is to create the data types to represent XML.

I will try to come up with a solid basis here, with a tree structure that should offer similar functionality to HXT.


## Phase 2 
With data structures in place, it should be possible to implement some basic selectors.
I am considering XPath style methods, or something similar to Monocle JsonPath.

## Phase 3
The next obvious task is to encoding/decoding from XML to case classes.
This can be achieved in a similar way to Circe with manual or derived type-classe instances.

- From case class to XML Document
- From XML to case class

## Testing
I'm using ScalaTest 3 and ScalaCheck. 

Any test cases that can use ScalaCheck should use it for exhaustiveness.

## Dependencies
I've already depended on Shapeless and Cats, but these have not been used extensively.
I'll try not to add many more dependencies to keep it simple.

Initially I will experiment with the Tree implementation from ScalaZ, perhaps replacing it with my own.
