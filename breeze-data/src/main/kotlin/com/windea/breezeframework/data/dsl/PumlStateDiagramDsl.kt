@file:Reference("[PlantUml](http://plantuml.com)")
@file:Reference("[PlantUml State Diagram](http://plantuml.com/zh/state-diagram)")
@file:NotImplemented

package com.windea.breezeframework.data.dsl

import com.windea.breezeframework.core.annotations.internal.*
import com.windea.breezeframework.core.annotations.marks.*
import com.windea.breezeframework.core.extensions.*

//////////Portal extensions

fun Dsl.Companion.pumlStateDiagram(builder: PumlStateDiagram.() -> Unit) = PumlStateDiagram().builder()

fun DslConfig.Companion.pumlStateDiagram(builder: PumlStateDiagramConfig.() -> Unit) = PumlStateDiagramConfig.builder()

//////////Main class & Config object

class PumlStateDiagram(
	override var indentContent: Boolean = true
) : Dsl, PumlStateDiagramDslElement, CanIndentContent {
	override fun toString(): String {
		val snippet = ""
		return "@startuml\n\n$snippet\n\n@enduml"
	}
}

object PumlStateDiagramConfig : DslConfig {
	/**缩进长度。*/
	var indentSize = 2
		set(value) = run { field = value.coerceIn(-2, 8) }
	/**是否使用双引号。*/
	var useDoubleQuote: Boolean = true
	
	internal val indent get() = if(indentSize <= -1) "\t" * indentSize else " " * indentSize
	internal val quote get() = if(useDoubleQuote) "\"" else "'"
}

///////////////Dsl marker annotations & Dsl element interfaces

@DslMarker
internal annotation class PumlStateDiagramDsl

interface PumlStateDiagramDslElement

////////////Dsl elements & build functions

open class PumlStateDiagramState : PumlStateDiagramDslElement

class PumlStateDiagramCompositedState : PumlStateDiagramState()

class PumlStateDiagramConcurrentState : PumlStateDiagramState()

class PumlStateDiagramTransition : PumlStateDiagramDslElement

class PumlStateDiagramNote : PumlStateDiagramDslElement

class PumlStateDiagramSkinParam : PumlStateDiagramDslElement

//////////TODO Param handler extensions
