package com.windea.breezeframework.dsl.graph

import com.windea.breezeframework.core.annotations.api.*

//TODO

//REGION top annotations and interfaces

/**序列图的Dsl。*/
@ReferenceApi("[Sequence Diagram](https://bramp.github.io/js-sequence-diagrams/)")
@DslMarker
private annotation class SequenceDiagramDsl

class SequenceDiagram
