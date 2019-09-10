@file:Reference(value = "[PlantUml](http://plantuml.com)")
@file:Reference(value = "[PlantUml State Diagram](http://plantuml.com/zh/state-diagram)")
@file:NotImplemented

package com.windea.breezeframework.data.dsl.text

import com.windea.breezeframework.core.annotations.marks.*
import com.windea.breezeframework.core.annotations.messages.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.data.dsl.*

//////////Portal function

fun pumlStateDiagram(builder: PumlStateDiagram.() -> Unit) = PumlStateDiagram().builder()

///////////////Dsl marker annotations & Dsl element interfaces

@DslMarker
internal annotation class PumlStateDiagramDsl

@PumlStateDiagramDsl
interface PumlStateDiagramDslElement

interface PumlStateDiagramState : PumlStateDiagramDslElement

////////////Dsl elements & Build functions

class PumlStateDiagram(
	override var indentContent: Boolean = true
) : Dsl, PumlStateDiagramDslElement, CanIndentContent {
	override fun toString(): String {
		val snippet = ""
		return "@startuml\n\n$snippet\n\n@enduml"
	}
}

class PumlStateDiagramSimpleState : PumlStateDiagramState

class PumlStateDiagramCompositedState : PumlStateDiagramState

class PumlStateDiagramConcurrentState : PumlStateDiagramState

class PumlStateDiagramTransition : PumlStateDiagramDslElement

class PumlStateDiagramNote : PumlStateDiagramDslElement

class PumlStateDiagramSkinParam : PumlStateDiagramDslElement

//////////TODO Param handler extensions


//////////Config object

object PumlStateDiagramConfig : DslConfig {
	/**缩进长度。*/
	var indentSize = 2
		set(value) = run { field = value.coerceIn(-2, 8) }
	/**是否使用双引号。*/
	var useDoubleQuote: Boolean = true
	
	internal val indent get() = if(indentSize <= -1) "\t" * indentSize else " " * indentSize
	internal val quote get() = if(useDoubleQuote) "\"" else "'"
	
	inline operator fun invoke(builder: PumlStateDiagramConfig.() -> Unit) = this.builder()
}
