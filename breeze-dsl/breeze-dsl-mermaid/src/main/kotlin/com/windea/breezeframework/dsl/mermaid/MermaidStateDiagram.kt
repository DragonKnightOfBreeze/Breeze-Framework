@file:Suppress("NOTHING_TO_INLINE", "unused")

package com.windea.breezeframework.dsl.mermaid

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.Mermaid.Companion.config

/**Mermaid状态图的Dsl。*/
@Reference("[Mermaid State Diagram](https://mermaidjs.github.io/#/stateDiagram)")
@DslMarker
@MustBeDocumented
internal annotation class MermaidStateDiagramDsl

/**Mermaid状态图。*/
@MermaidStateDiagramDsl
class MermaidStateDiagram @PublishedApi internal constructor() : Mermaid(), MermaidStateDiagramEntry, CanIndent {
	override val states: MutableSet<MermaidStateDiagramState> = mutableSetOf()
	override val links: MutableList<MermaidStateDiagramTransition> = mutableListOf()
	override val notes: MutableList<MermaidStateDiagramNote> = mutableListOf()

	override var indentContent: Boolean = true
	override var splitContent: Boolean = true

	override fun toString(): String {
		val contentSnippet = contentString().applyIndent(config.indent)
		return "stateDiagram\n$contentSnippet"
	}
}


/**Mermaid状态图Dsl的入口。*/
@MermaidStateDiagramDsl
interface MermaidStateDiagramEntry : MermaidEntry, CanSplitLine, WithTransition<MermaidStateDiagramState, MermaidStateDiagramTransition> {
	val states: MutableSet<MermaidStateDiagramState>
	val links: MutableList<MermaidStateDiagramTransition>
	val notes: MutableList<MermaidStateDiagramNote>

	override fun contentString(): String {
		return listOfNotNull(
			states.orNull()?.joinToString("\n"),
			links.orNull()?.joinToString("\n"),
			notes.orNull()?.joinToString("\n")
		).joinToString(splitSeparator)
	}

	@MermaidStateDiagramDsl
	override fun String.links(other: String) = transition(this, other)
}

/**Mermaid状态图Dsl的元素。*/
@MermaidStateDiagramDsl
interface MermaidStateDiagramElement : MermaidElement

//region dsl elements
/**Mermaid状态图状态。*/
@MermaidStateDiagramDsl
sealed class MermaidStateDiagramState(
	val name: String
) : MermaidStateDiagramElement, WithUniqueId {
	var text: String? = null

	override val id: String get() = name

	override fun equals(other: Any?) = equalsByOne(this, other) { id }

	override fun hashCode() = hashCodeByOne(this) { id }
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
) : MermaidStateDiagramState(name), MermaidStateDiagramEntry, CanIndent {
	override val states: MutableSet<MermaidStateDiagramState> = mutableSetOf()
	override val links: MutableList<MermaidStateDiagramTransition> = mutableListOf()
	override val notes: MutableList<MermaidStateDiagramNote> = mutableListOf()

	override var indentContent: Boolean = true
	override var splitContent: Boolean = true

	override fun toString(): String {
		val contentSnippet = contentString().applyIndent(config.indent)
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
		val contentSnippet = sections.joinToString("\n--\n").applyIndent(config.indent)
		return "state $name {\n$contentSnippet\n}"
	}
}

/**Mermaid状态图并发状态部分。*/
@MermaidStateDiagramDsl
class MermaidStateDiagramConcurrentSection : MermaidStateDiagramEntry {
	override val states: MutableSet<MermaidStateDiagramState> = mutableSetOf()
	override val links: MutableList<MermaidStateDiagramTransition> = mutableListOf()
	override val notes: MutableList<MermaidStateDiagramNote> = mutableListOf()

	override var splitContent: Boolean = true

	override fun toString() = contentString()
}

/**Mermaid状态图转化。*/
@MermaidStateDiagramDsl
class MermaidStateDiagramTransition @PublishedApi internal constructor(
	val fromStateId: String,
	val toStateId: String
) : MermaidStateDiagramElement, WithNode<MermaidStateDiagramState> {
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
) : MermaidStateDiagramElement, CanWrapLine, CanIndent {
	var text: String = ""  //换行符（内联注释）：\\n

	override var wrapContent: Boolean = false
	override var indentContent: Boolean = true

	override fun toString(): String {
		if("\n" in text || "\r" in text) wrapContent = true //wrapSeparator if necessary

		return when {
			wrapContent && indentContent -> "note $location\n${text.applyIndent(config.indent)}\nend note"
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

//region dsl build extensions
@MermaidStateDiagramDsl
inline fun mermaidStateDiagram(block: MermaidStateDiagram.() -> Unit) = MermaidStateDiagram().apply(block)

@InlineDslFunction
@MermaidStateDiagramDsl
fun MermaidStateDiagramEntry.initState() = "[*]"

@InlineDslFunction
@MermaidStateDiagramDsl
fun MermaidStateDiagramEntry.finishState() = "[*]"

@InlineDslFunction
@MermaidStateDiagramDsl
@MermaidDslExtendedFeature
fun MermaidStateDiagramEntry.anyState() = "<Any State>"

@MermaidStateDiagramDsl
inline fun MermaidStateDiagramEntry.state(name: String) =
	MermaidStateDiagramSimpleState(name).also { states += it }

@MermaidStateDiagramDsl
inline fun MermaidStateDiagramEntry.compositedState(name: String, block: MermaidStateDiagramCompositedState.() -> Unit) =
	MermaidStateDiagramCompositedState(name).apply(block).also { states += it }

@MermaidStateDiagramDsl
inline fun MermaidStateDiagramEntry.concurrentState(name: String, block: MermaidStateDiagramConcurrentState.() -> Unit) =
	MermaidStateDiagramConcurrentState(name).apply(block).also { states += it }

@MermaidStateDiagramDsl
inline fun MermaidStateDiagramEntry.transition(fromStateId: String, toStateId: String) =
	MermaidStateDiagramTransition(fromStateId, toStateId).also { links += it }

@MermaidStateDiagramDsl
inline fun MermaidStateDiagramEntry.note(location: MermaidStateDiagramNote.Location) =
	MermaidStateDiagramNote(location).also { notes += it }

@InlineDslFunction
@MermaidStateDiagramDsl
fun MermaidStateDiagramEntry.leftOf(stateName: String) =
	MermaidStateDiagramNote.Location(MermaidStateDiagramNote.Position.LeftOf, stateName)

@InlineDslFunction
@MermaidStateDiagramDsl
fun MermaidStateDiagramEntry.rightOf(stateName: String) =
	MermaidStateDiagramNote.Location(MermaidStateDiagramNote.Position.RightOf, stateName)

@MermaidStateDiagramDsl
inline infix fun MermaidStateDiagramState.text(text: String) =
	this.also { it.text = text }

@MermaidStateDiagramDsl
inline fun MermaidStateDiagramConcurrentState.section(block: MermaidStateDiagramConcurrentSection.() -> Unit) =
	MermaidStateDiagramConcurrentSection().apply(block).also { sections += it }

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
