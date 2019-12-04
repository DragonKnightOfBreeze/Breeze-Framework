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
@MustBeDocumented
internal annotation class MermaidStateDiagramDsl

/**Mermaid状态图。*/
@MermaidStateDiagramDsl
class MermaidStateDiagram @PublishedApi internal constructor() : Mermaid(), MermaidStateDiagramDslEntry, CanIndent {
	override val states: MutableSet<MermaidStateDiagramState> = mutableSetOf()
	override val links: MutableList<MermaidStateDiagramTransition> = mutableListOf()
	override val notes: MutableList<MermaidStateDiagramNote> = mutableListOf()
	
	override var indentContent: Boolean = true
	override var splitContent: Boolean = true
	
	override fun toString(): String {
		val contentSnippet = toContentString().applyIndent(indent)
		return "stateDiagram\n$contentSnippet"
	}
}
//endregion

//region dsl interfaces
/**Mermaid状态图Dsl的入口。*/
@MermaidStateDiagramDsl
interface MermaidStateDiagramDslEntry : MermaidDslEntry, CanSplit, WithTransition<MermaidStateDiagramState, MermaidStateDiagramTransition> {
	val states: MutableSet<MermaidStateDiagramState>
	val links: MutableList<MermaidStateDiagramTransition>
	val notes: MutableList<MermaidStateDiagramNote>
	
	fun toContentString(): String {
		return arrayOf(
			states.joinToStringOrEmpty("\n"),
			links.joinToStringOrEmpty("\n"),
			notes.joinToStringOrEmpty("\n")
		).filterNotEmpty().joinToStringOrEmpty(split)
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
	val name: String
) : MermaidStateDiagramDslElement, WithUniqueId {
	var text: String? = null
	
	override val id: String get() = name
	
	override fun equals(other: Any?) = equalsBySelectId(this, other) { id }
	
	override fun hashCode() = hashCodeBySelectId(this) { id }
}

/**Mermaid状态图简单状态。*/
@MermaidStateDiagramDsl
class MermaidStateDiagramSimpleState @PublishedApi internal constructor(
	name: String
) : MermaidStateDiagramState(name) {
	var type: Type? = null
	
	override fun toString(): String {
		val textSnippet = text?.let { ": $it" }.orEmpty()
		val typeSnippet = type?.let { " <<${it.text}>>" }.orEmpty()
		return "$name$textSnippet$typeSnippet"
	}
	
	/**Mermaid状态图简单状态的类型。*/
	@MermaidStateDiagramDsl
	enum class Type(val text: String) {
		Fork("fork"), Join("join")
	}
}

/**Mermaid状态图复合状态。*/
@MermaidStateDiagramDsl
class MermaidStateDiagramCompositedState @PublishedApi internal constructor(
	name: String
) : MermaidStateDiagramState(name), MermaidStateDiagramDslEntry, CanIndent {
	override val states: MutableSet<MermaidStateDiagramState> = mutableSetOf()
	override val links: MutableList<MermaidStateDiagramTransition> = mutableListOf()
	override val notes: MutableList<MermaidStateDiagramNote> = mutableListOf()
	
	override var indentContent: Boolean = true
	override var splitContent: Boolean = true
	
	override fun toString(): String {
		val contentSnippet = toContentString().applyIndent(indent)
		return "state $name {\n$contentSnippet\n}"
	}
}

/**Mermaid状态图并发状态。*/
@MermaidStateDiagramDsl
class MermaidStateDiagramConcurrentState @PublishedApi internal constructor(
	name: String
) : MermaidStateDiagramState(name), CanIndent {
	val sections: MutableList<MermaidStateDiagramConcurrentSection> = mutableListOf()
	
	override var indentContent: Boolean = true
	
	override fun toString(): String {
		val contentSnippet = sections.joinToStringOrEmpty("\n--\n").applyIndent(indent)
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
	
	override fun toString() = toContentString()
}

/**Mermaid状态图转换。*/
@MermaidStateDiagramDsl
class MermaidStateDiagramTransition @PublishedApi internal constructor(
	val fromStateId: String,
	val toStateId: String
) : MermaidStateDiagramDslElement, WithNode<MermaidStateDiagramState> {
	var text: String? = null
	
	override val sourceNodeId get() = fromStateId
	override val targetNodeId get() = toStateId
	
	override fun toString(): String {
		val textSnippet = text?.let { ": $it" }.orEmpty()
		return "$fromStateId --> $toStateId$textSnippet"
	}
}

/**Mermaid状态图注释。*/
@MermaidStateDiagramDsl
class MermaidStateDiagramNote @PublishedApi internal constructor(
	val location: Location
) : MermaidStateDiagramDslElement, CanWrap, CanIndent {
	@Multiline("\\n", "Inline note.")
	var text: String = ""
	
	override var wrapContent: Boolean = false
	override var indentContent: Boolean = true
	
	override fun toString(): String {
		if("\n" in text || "\r" in text) wrapContent = true //wrap if necessary
		
		return when {
			wrapContent && indentContent -> "note $location\n${text.applyIndent(indent)}\nend note"
			wrapContent -> "note $location\n$text\nend note"
			else -> "note $location: $text"
		}
	}
	
	/**Mermaid状态图注释的位置。*/
	@MermaidStateDiagramDsl
	class Location @PublishedApi internal constructor(
		val position: Position,
		val stateId: String
	) {
		override fun toString(): String {
			return "${position.text} $stateId"
		}
	}
	
	/**Mermaid状态图注释的方位。*/
	@MermaidStateDiagramDsl
	enum class Position(val text: String) {
		LeftOf("left of"), RightOf("right of")
	}
}
//endregion

//region build extensions
@MermaidStateDiagramDsl
inline fun mermaidStateDiagram(block: MermaidStateDiagram.() -> Unit) = MermaidStateDiagram().also { it.block() }

@InlineDsl
@MermaidStateDiagramDsl
inline fun MermaidStateDiagramDslEntry.initState() = "[*]"

@InlineDsl
@MermaidStateDiagramDsl
inline fun MermaidStateDiagramDslEntry.finishState() = "[*]"

@InlineDsl
@MermaidStateDiagramDsl
@MermaidDslExtendedFeature
inline fun MermaidStateDiagramDslEntry.anyState() = "<Any State>"

@MermaidStateDiagramDsl
inline fun MermaidStateDiagramDslEntry.state(name: String) =
	MermaidStateDiagramSimpleState(name).also { states += it }

@MermaidStateDiagramDsl
inline fun MermaidStateDiagramDslEntry.compositedState(name: String, block: MermaidStateDiagramCompositedState.() -> Unit) =
	MermaidStateDiagramCompositedState(name).also { it.block() }.also { states += it }

@MermaidStateDiagramDsl
inline fun MermaidStateDiagramDslEntry.concurrentState(name: String, block: MermaidStateDiagramConcurrentState.() -> Unit) =
	MermaidStateDiagramConcurrentState(name).also { it.block() }.also { states += it }

@MermaidStateDiagramDsl
inline fun MermaidStateDiagramDslEntry.transition(fromStateId: String, toStateId: String) =
	MermaidStateDiagramTransition(fromStateId, toStateId).also { links += it }

@MermaidStateDiagramDsl
inline fun MermaidStateDiagramDslEntry.transition(fromState: MermaidStateDiagramSimpleState, toState: MermaidStateDiagramSimpleState) =
	MermaidStateDiagramTransition(fromState.name, toState.name).also { links += it }

@MermaidStateDiagramDsl
inline fun MermaidStateDiagramDslEntry.note(location: MermaidStateDiagramNote.Location) =
	MermaidStateDiagramNote(location).also { notes += it }

@InlineDsl
@MermaidStateDiagramDsl
inline fun MermaidStateDiagramDslEntry.leftOf(stateName: String) =
	MermaidStateDiagramNote.Location(MermaidStateDiagramNote.Position.LeftOf, stateName)

@InlineDsl
@MermaidStateDiagramDsl
inline fun MermaidStateDiagramDslEntry.rightOf(stateName: String) =
	MermaidStateDiagramNote.Location(MermaidStateDiagramNote.Position.RightOf, stateName)

@MermaidStateDiagramDsl
inline infix fun MermaidStateDiagramState.text(text: String) =
	this.also { it.text = text }

@MermaidStateDiagramDsl
inline fun MermaidStateDiagramConcurrentState.section(block: MermaidStateDiagramConcurrentSection.() -> Unit) =
	MermaidStateDiagramConcurrentSection().also { it.block() }.also { sections += it }

@MermaidStateDiagramDsl
inline infix fun MermaidStateDiagramSimpleState.type(type: MermaidStateDiagramSimpleState.Type) =
	this.also { it.type = type }

@MermaidStateDiagramDsl
inline infix fun MermaidStateDiagramTransition.text(text: String) =
	this.also { it.text = text }

@MermaidStateDiagramDsl
inline infix fun MermaidStateDiagramNote.text(text: String) =
	this.also { it.text = text }
//endregion
