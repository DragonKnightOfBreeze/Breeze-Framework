@file:Reference("[PlantUml State Diagram](http://plantuml.com/zh/state-diagram)")
@file:NotImplemented
@file:Suppress("CanBePrimaryConstructorProperty", "NOTHING_TO_INLINE")

package com.windea.breezeframework.dsl.puml

import com.windea.breezeframework.core.annotations.marks.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.puml.PumlConfig.indent
import com.windea.breezeframework.dsl.puml.PumlConfig.quote
import org.intellij.lang.annotations.*

//REGION Dsl marker annotations & Dsl element interfaces

@DslMarker
internal annotation class PumlStateDiagramDsl

//REGION Dsl elements & Build functions

/**构建Puml状态图。*/
@PumlStateDiagramDsl
fun pumlStateDiagram(builder: PumlStateDiagram.() -> Unit) = PumlStateDiagram().also { it.builder() }

/**Puml状态图的元素。*/
@PumlStateDiagramDsl
interface PumlStateDiagramDslElement : PumlDslElement

/**Puml状态图。*/
@PumlStateDiagramDsl
class PumlStateDiagram : PumlStateDiagramDslElement, Puml by PumlDelegate() {
	var nodeIndex: Int = 0
	
	override fun toString(): String {
		val snippet = ""
		return "@startuml\n\n$snippet\n\n@enduml"
	}
}

@PumlStateDiagramDsl
open class PumlStateDiagramState : PumlStateDiagramDslElement

@PumlStateDiagramDsl
class PumlStateDiagramCompositedState : PumlStateDiagramState()

@PumlStateDiagramDsl
class PumlStateDiagramConcurrentState : PumlStateDiagramState()

@PumlStateDiagramDsl
class PumlStateDiagramLink : PumlStateDiagramDslElement

/**Mermaid状态图注释。*/
@PumlStateDiagramDsl
class PumlStateDiagramNote(
	text: String,
	alias: String //necessary for simple api
) : PumlStateDiagramDslElement, CanIndentContent, CanWrapContent {
	@Language("MarkUp")
	val text: String = text.also {
		//wrap when necessary
		if("\n" in it) wrapContent = true
	}
	
	override var indentContent: Boolean = true
	override var wrapContent: Boolean = false
	
	//must: alias or (position & targetStateName), position win first.
	var alias: String = alias
	var position: PumlStateDiagramNotePosition? = null
	var targetStateName: String? = null
	
	override fun toString(): String {
		val aliasSnippet = if(position == null) " $alias" else ""
		val positionSnippet = position?.let { " ${it.text} $targetStateName" } ?: ""
		return if(wrapContent) {
			val indentedTextSnippet = if(indentContent) text.prependIndent(indent) else text
			"note$aliasSnippet$positionSnippet\n$indentedTextSnippet\nend note"
		} else {
			//unescape "\n" if necessary
			val textSnippet = text.replace("\n", "\\n")
			if(position == null) "note ${textSnippet.wrapQuote(quote)} as $alias"
			else "note$positionSnippet: $textSnippet"
		}
	}
	
	
	@PumlStateDiagramDsl
	inline infix fun alias(alias: String) = this.also { it.alias = alias }
	
	@PumlStateDiagramDsl
	inline infix fun leftOf(targetStateName: String) = this.also { it.targetStateName = targetStateName }
		.also { it.position = PumlStateDiagramNotePosition.LeftOf }
	
	@PumlStateDiagramDsl
	inline infix fun rightOf(targetStateName: String) = this.also { it.targetStateName = targetStateName }
		.also { it.position = PumlStateDiagramNotePosition.RightOf }
	
	@PumlStateDiagramDsl
	inline infix fun topOf(targetStateName: String) = this.also { it.targetStateName = targetStateName }
		.also { it.position = PumlStateDiagramNotePosition.TopOf }
	
	@PumlStateDiagramDsl
	inline infix fun bottomOf(targetStateName: String) = this.also { it.targetStateName = targetStateName }
		.also { it.position = PumlStateDiagramNotePosition.BottomOf }
}

//REGION Enumerations and constants

/**Puml状态图连接的箭头形状。*/
enum class PumlStateDiagramLinkArrowShape(val text: String) {
	Dotted("dotted"), Dashed("dashed"), Bold("bold") //and more?
}

/**Puml状态图连接的箭头方向。*/
enum class PumlStateDiagramLinkArrowDirection(val text: String) {
	Down("down"), Up("up"), Left("left"), Right("right")
}

/**Puml状态图连接的注释位置。*/
enum class PumlStateDiagramNotePosition(val text: String) {
	RightOf("right of"), LeftOf("left of"), TopOf("top of"), BottomOf("bottom of")
}
