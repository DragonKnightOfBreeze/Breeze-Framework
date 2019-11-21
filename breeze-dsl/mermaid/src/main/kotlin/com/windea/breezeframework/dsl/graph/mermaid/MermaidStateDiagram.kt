@file:Suppress("NOTHING_TO_INLINE", "unused")

package com.windea.breezeframework.dsl.graph.mermaid

import com.windea.breezeframework.core.annotations.api.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.graph.mermaid.MermaidConfig.indent

//NOTE unstable raw api

//region top annotations and interfaces
/**Mermaid状态图的Dsl。*/
@ReferenceApi("[Mermaid State Diagram](https://mermaidjs.github.io/#/stateDiagram)")
@DslMarker
private annotation class MermaidStateDiagramDsl

/**Mermaid状态图。*/
@MermaidStateDiagramDsl
class MermaidStateDiagram @PublishedApi internal constructor() : Mermaid(), MermaidStateDiagramDslEntry, CanIndentContent {
	override val states: MutableSet<MermaidStateDiagramState> = mutableSetOf()
	override val links: MutableList<MermaidStateDiagramTransition> = mutableListOf()
	override val notes: MutableList<MermaidStateDiagramNote> = mutableListOf()
	
	override var indentContent: Boolean = true
	override var splitContent: Boolean = true
	
	override fun toString(): String {
		val contentSnippet = _toContentString()._applyIndent(indent)
		return "stateDiagram\n$contentSnippet"
	}
}
//endregion

//region dsl interfaces
/**Mermaid状态图Dsl的入口。*/
@MermaidStateDiagramDsl
interface MermaidStateDiagramDslEntry : MermaidDslEntry, CanSplitContent, WithTransition<MermaidStateDiagramState, MermaidStateDiagramTransition> {
	val states: MutableSet<MermaidStateDiagramState>
	val links: MutableList<MermaidStateDiagramTransition>
	val notes: MutableList<MermaidStateDiagramNote>
	
	fun _toContentString(): String {
		return arrayOf(
			states.joinToStringOrEmpty("\n"),
			links.joinToStringOrEmpty("\n"),
			notes.joinToStringOrEmpty("\n")
		).filterNotEmpty().joinToStringOrEmpty(_splitWrap)
	}
	
	@GenericDsl
	override fun String.fromTo(other: String) = transition(this, other)
}

/**Mermaid状态图Dsl的元素。*/
@MermaidStateDiagramDsl
interface MermaidStateDiagramDslElement : MermaidDslElement
//endregion

//region dsl elements
/**Mermaid状态图状态。*/
@MermaidStateDiagramDsl
sealed class MermaidStateDiagramState(
	val name: String,
	val text: String? = null
) : MermaidStateDiagramDslElement, WithName {
	override val _name: String get() = name
	
	override fun equals(other: Any?) = equalsBySelect(this, other) { arrayOf(name) }
	
	override fun hashCode() = hashCodeBySelect(this) { arrayOf(name) }
}

/**Mermaid状态图简单状态。*/
@MermaidStateDiagramDsl
class MermaidStateDiagramSimpleState @PublishedApi internal constructor(
	name: String,
	text: String? = null
) : MermaidStateDiagramState(name, text) {
	var type: Type? = null
	
	override fun toString(): String {
		val textSnippet = text?.let { ": $it" }.orEmpty()
		val typeSnippet = type?.let { " <<${it.text}>>" }.orEmpty()
		return "$name$textSnippet$typeSnippet"
	}
	
	enum class Type(internal val text: String) {
		Fork("fork"), Join("join")
	}
}

/**Mermaid状态图复合状态。*/
@MermaidStateDiagramDsl
class MermaidStateDiagramCompositedState @PublishedApi internal constructor(
	name: String,
	text: String? = null
) : MermaidStateDiagramState(name, text), MermaidStateDiagramDslEntry, CanIndentContent {
	override val states: MutableSet<MermaidStateDiagramState> = mutableSetOf()
	override val links: MutableList<MermaidStateDiagramTransition> = mutableListOf()
	override val notes: MutableList<MermaidStateDiagramNote> = mutableListOf()
	
	override var indentContent: Boolean = true
	override var splitContent: Boolean = true
	
	override fun toString(): String {
		val contentSnippet = _toContentString()._applyIndent(indent)
		return "state $name {\n$contentSnippet\n}"
	}
}

/**Mermaid状态图并发状态。*/
@MermaidStateDiagramDsl
class MermaidStateDiagramConcurrentState @PublishedApi internal constructor(
	name: String,
	text: String? = null
) : MermaidStateDiagramState(name, text), CanIndentContent {
	val sections: MutableList<MermaidStateDiagramConcurrentSection> = mutableListOf()
	
	override var indentContent: Boolean = true
	
	override fun toString(): String {
		val contentSnippet = sections.joinToStringOrEmpty("\n--\n")._applyIndent(indent)
		return "state $name {\n$contentSnippet\n}"
	}
}

/**Mermaid状态图并发状态部分。*/
@MermaidStateDiagramDsl
class MermaidStateDiagramConcurrentSection : MermaidStateDiagramDslEntry {
	override val states: MutableSet<MermaidStateDiagramState> = mutableSetOf()
	override val links: MutableList<MermaidStateDiagramTransition> = mutableListOf()
	override val notes: MutableList<MermaidStateDiagramNote> = mutableListOf()
	
	override var splitContent: Boolean = true
	
	override fun toString() = _toContentString()
}

/**Mermaid状态图转换。*/
@MermaidStateDiagramDsl
class MermaidStateDiagramTransition @PublishedApi internal constructor(
	val sourceStateName: String,
	val targetStateName: String,
	var text: String? = null
) : MermaidStateDiagramDslElement, WithNode<MermaidStateDiagramState> {
	override val _fromNodeName get() = sourceStateName
	override val _toNodeName get() = targetStateName
	
	override fun toString(): String {
		val textSnippet = text?.let { ": $it" }.orEmpty()
		return "$sourceStateName --> $targetStateName$textSnippet"
	}
}

class MermaidStateDiagramNote @PublishedApi internal constructor(
	val location: Location,
	val text: String //NOTE can be multiline
) : MermaidStateDiagramDslElement, CanWrapContent, CanIndentContent {
	override var wrapContent: Boolean = false
	override var indentContent: Boolean = true
	
	override fun toString(): String {
		if("\n" in text || "\r" in text) wrapContent = true //wrap if necessary
		
		return when {
			wrapContent && indentContent -> "note $location\n${text._applyIndent(indent)}\nend note"
			wrapContent -> "note $location\n$text\nend note"
			else -> "note $location: $text"
		}
	}
	
	class Location @PublishedApi internal constructor(
		val position: Position,
		val stateName: String
	) {
		override fun toString(): String {
			return "${position.text} $stateName"
		}
	}
	
	enum class Position(internal val text: String) {
		LeftOf("left of"), RightOf("right of")
	}
}
//endregion

//region build extensions
@MermaidStateDiagramDsl
inline fun pumlStateDiagram(block: MermaidStateDiagram.() -> Unit) = MermaidStateDiagram().also { it.block() }

@MermaidStateDiagramDsl
inline fun MermaidStateDiagramDslEntry.state(name: String, text: String? = null) =
	MermaidStateDiagramSimpleState(name, text).also { states += it }

@InlineDsl
@MermaidStateDiagramDsl
inline fun MermaidStateDiagramDslEntry.initState() = "[*]"

@InlineDsl
@MermaidStateDiagramDsl
inline fun MermaidStateDiagramDslEntry.finishState() = "[*]"

@MermaidStateDiagramDsl
inline fun MermaidStateDiagramDslEntry.compositedState(name: String, text: String? = null,
	block: MermaidStateDiagramCompositedState.() -> Unit) =
	MermaidStateDiagramCompositedState(name, text).also { it.block() }.also { states += it }

@MermaidStateDiagramDsl
inline fun MermaidStateDiagramDslEntry.concurrentState(name: String, text: String? = null,
	block: MermaidStateDiagramConcurrentState.() -> Unit) =
	MermaidStateDiagramConcurrentState(name, text).also { it.block() }.also { states += it }

@MermaidStateDiagramDsl
inline fun MermaidStateDiagramDslEntry.transition(sourceStateName: String, targetStateName: String,
	text: String? = null) =
	MermaidStateDiagramTransition(sourceStateName, targetStateName, text).also { links += it }

@MermaidStateDiagramDsl
inline fun MermaidStateDiagramDslEntry.transition(sourceState: MermaidStateDiagramSimpleState,
	targetState: MermaidStateDiagramSimpleState, text: String? = null) =
	MermaidStateDiagramTransition(sourceState.name, targetState.name, text)
		.also { links += it }

@MermaidStateDiagramDsl
inline fun MermaidStateDiagramDslEntry.note(location: MermaidStateDiagramNote.Location, text: String) =
	MermaidStateDiagramNote(location, text).also { notes += it }

@InlineDsl
@MermaidStateDiagramDsl
inline fun MermaidStateDiagramDslEntry.leftOf(stateName: String) =
	MermaidStateDiagramNote.Location(MermaidStateDiagramNote.Position.LeftOf, stateName)

@InlineDsl
@MermaidStateDiagramDsl
inline fun MermaidStateDiagramDslEntry.rightOf(stateName: String) =
	MermaidStateDiagramNote.Location(MermaidStateDiagramNote.Position.RightOf, stateName)

@MermaidStateDiagramDsl
inline fun MermaidStateDiagramConcurrentState.section(block: MermaidStateDiagramConcurrentSection.() -> Unit) =
	MermaidStateDiagramConcurrentSection().also { it.block() }.also { sections += it }

@MermaidStateDiagramDsl
inline infix fun MermaidStateDiagramSimpleState.type(type: MermaidStateDiagramSimpleState.Type) =
	this.also { it.type = type }

@MermaidStateDiagramDsl
inline infix fun MermaidStateDiagramTransition.text(text: String) =
	this.also { it.text = text }
//endregion
