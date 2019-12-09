@file:Suppress("NOTHING_TO_INLINE", "unused")

package com.windea.breezeframework.dsl.mermaid

import com.windea.breezeframework.core.annotations.api.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.MermaidConfig.indent
import org.intellij.lang.annotations.*

//NOTE can have a title by `title: text`, but it is not introduced in official api

//region top annotations and interfaces
/**Mermaid序列图的Dsl。*/
@ReferenceApi("[Mermaid Sequence Diagram](https://mermaidjs.github.io/#/sequenceDiagram)")
@DslMarker
@MustBeDocumented
internal annotation class MermaidSequenceDiagramDsl

/**Mermaid序列图。*/
@MermaidSequenceDiagramDsl
class MermaidSequenceDiagram @PublishedApi internal constructor() : Mermaid(), MermaidSequenceDiagramDslEntry, CanIndent {
	override val participants: MutableSet<MermaidSequenceDiagramParticipant> = mutableSetOf()
	override val messages: MutableList<MermaidSequenceDiagramMessage> = mutableListOf()
	override val notes: MutableList<MermaidSequenceDiagramNote> = mutableListOf()
	override val scopes: MutableList<MermaidSequenceDiagramScope> = mutableListOf()
	
	override var indentContent: Boolean = true
	override var splitContent: Boolean = true
	
	override fun toString(): String {
		val contentSnippet = toContentString().applyIndent(indent)
		return "sequenceDiagram\n$contentSnippet"
	}
}
//endregion

//region dsl interfaces
/**Mermaid序列图Dsl的入口。*/
@MermaidSequenceDiagramDsl
interface MermaidSequenceDiagramDslEntry : MermaidDslEntry, CanSplit,
	WithTransition<MermaidSequenceDiagramParticipant, MermaidSequenceDiagramMessage> {
	val participants: MutableSet<MermaidSequenceDiagramParticipant>
	val messages: MutableList<MermaidSequenceDiagramMessage>
	val notes: MutableList<MermaidSequenceDiagramNote>
	val scopes: MutableList<MermaidSequenceDiagramScope>
	
	fun toContentString(): String {
		return arrayOf(
			participants.joinToStringOrEmpty("\n"),
			messages.joinToStringOrEmpty("\n"),
			notes.joinToStringOrEmpty("\n"),
			scopes.joinToStringOrEmpty("\n")
		).filterNotEmpty().joinToStringOrEmpty(split)
	}
	
	@MermaidFlowChartDsl
	override fun String.fromTo(other: String) = message(this, other)
}

/**Mermaid序列图Dsl的元素。*/
@MermaidSequenceDiagramDsl
interface MermaidSequenceDiagramDslElement : MermaidDslElement
//endregion

//region dsl elements
/**Mermaid序列图参与者。*/
@MermaidSequenceDiagramDsl
class MermaidSequenceDiagramParticipant @PublishedApi internal constructor(
	val name: String
) : MermaidSequenceDiagramDslElement, WithUniqueId {
	var alias: String? = null
	
	override val id: String get() = alias ?: name
	
	override fun equals(other: Any?) = equalsByOne(this, other) { id }
	
	override fun hashCode() = hashCodeByOne(this) { id }
	
	override fun toString(): String {
		val aliasSnippet = alias?.let { "$it as " }.orEmpty()
		return "participant $aliasSnippet$name"
	}
}

/**Mermaid序列图消息。*/
@MermaidSequenceDiagramDsl
class MermaidSequenceDiagramMessage @PublishedApi internal constructor(
	val fromParticipantId: String,
	val toParticipantId: String
) : MermaidSequenceDiagramDslElement, WithNode<MermaidSequenceDiagramParticipant> {
	var text: String = ""
	var arrowShape: ArrowShape = ArrowShape.Arrow
	var isActivated: Boolean? = null
	
	override val sourceNodeId get() = fromParticipantId
	override val targetNodeId get() = toParticipantId
	
	override fun toString(): String {
		val activateSnippet = isActivated?.let { if(it) "+ " else "- " }.orEmpty()
		return "$fromParticipantId ${arrowShape.text} $activateSnippet$toParticipantId: $text"
	}
	
	/**Mermaid序列图消息的箭头形状。*/
	@MermaidSequenceDiagramDsl
	enum class ArrowShape(val text: String) {
		Arrow("->>"), DashedArrow("-->>"), Line("->"), DashedLine("-->"), Cross("-x"), DashedCross("--x")
	}
}

/**Mermaid序列图注释。*/
@MermaidSequenceDiagramDsl
class MermaidSequenceDiagramNote @PublishedApi internal constructor(
	val location: Location,
	@Multiline("<br>")
	val text: String
) : MermaidSequenceDiagramDslElement {
	override fun toString(): String {
		val textSnippet = text.replaceWithHtmlWrap()
		return "note $location: $textSnippet"
	}
	
	/**Mermaid序列图注释的位置。*/
	@MermaidSequenceDiagramDsl
	class Location @PublishedApi internal constructor(
		val position: Position,
		val participantId1: String,
		val participantId2: String? = null
	) {
		override fun toString(): String {
			val participantId2Snippet = participantId2?.let { ", $it" }.orEmpty()
			return "${position.text} $participantId1$participantId2Snippet"
		}
	}
	
	/**Mermaid序列图注释的方位。*/
	@MermaidSequenceDiagramDsl
	enum class Position(val text: String) {
		RightOf("right of"), LeftOf("left of"), Over("over")
	}
}

/**Mermaid序列图作用域。*/
@MermaidSequenceDiagramDsl
sealed class MermaidSequenceDiagramScope(
	val type: String,
	val text: String?
) : MermaidSequenceDiagramDslElement, MermaidSequenceDiagramDslEntry, CanIndent {
	override val participants: MutableSet<MermaidSequenceDiagramParticipant> = mutableSetOf()
	override val messages: MutableList<MermaidSequenceDiagramMessage> = mutableListOf()
	override val notes: MutableList<MermaidSequenceDiagramNote> = mutableListOf()
	override val scopes: MutableList<MermaidSequenceDiagramScope> = mutableListOf()
	
	override var indentContent: Boolean = true
	override var splitContent: Boolean = true
	
	override fun toString(): String {
		val textSnippet = text?.let { " $it" }.orEmpty()
		val contentSnippet = toContentString().applyIndent(indent)
		return "$type$textSnippet\n$contentSnippet\nend"
	}
}

/**Mermaid序列图循环作用域。*/
@MermaidSequenceDiagramDsl
class MermaidSequenceDiagramLoop @PublishedApi internal constructor(
	text: String
) : MermaidSequenceDiagramScope("loop", text)

/**Mermaid序列图可选作用域。*/
@MermaidSequenceDiagramDsl
class MermaidSequenceDiagramOptional @PublishedApi internal constructor(
	text: String
) : MermaidSequenceDiagramScope("opt", text)

/**Mermaid序列图替代作用域。*/
@MermaidSequenceDiagramDsl
class MermaidSequenceDiagramAlternative @PublishedApi internal constructor(
	text: String
) : MermaidSequenceDiagramScope("alt", text) {
	val elseScopes: MutableList<MermaidSequenceDiagramElse> = mutableListOf()
	
	override fun toString(): String {
		val contentSnippet = toContentString()
			.let { if(indentContent) it.prependIndent(indent) else it }
		val elseScopesSnippet = elseScopes.joinToStringOrEmpty("\n", "\n")
		return "$contentSnippet$elseScopesSnippet"
	}
	
}

/**Mermaid序列图其余作用域。*/
@MermaidSequenceDiagramDsl
class MermaidSequenceDiagramElse @PublishedApi internal constructor(
	text: String? = null
) : MermaidSequenceDiagramScope("else", text)

/**Mermaid序列图颜色高亮作用域。*/
@MermaidSequenceDiagramDsl
class MermaidSequenceDiagramHighlight @PublishedApi internal constructor(
	@Language(value = "Less", prefix = "@color:")
	color: String
) : MermaidSequenceDiagramScope("rect", color)
//endregion

//region build extensions
/**构建Mermaid序列图。*/
@MermaidSequenceDiagramDsl
fun mermaidSequenceDiagram(block: MermaidSequenceDiagram.() -> Unit) =
	MermaidSequenceDiagram().also { it.block() }

@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramDslEntry.participant(name: String) =
	MermaidSequenceDiagramParticipant(name).also { participants += it }

@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramDslEntry.message(fromParticipantId: String, toParticipantId: String) =
	MermaidSequenceDiagramMessage(fromParticipantId, toParticipantId).also { messages += it }

@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramDslEntry.message(
	fromParticipantId: String,
	arrowShape: MermaidSequenceDiagramMessage.ArrowShape,
	isActivated: Boolean = false,
	toParticipantId: String
) = MermaidSequenceDiagramMessage(fromParticipantId, toParticipantId)
	.also { it.arrowShape = arrowShape;it.isActivated = isActivated }
	.also { messages += it }

@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramDslEntry.note(location: MermaidSequenceDiagramNote.Location, text: String) =
	MermaidSequenceDiagramNote(location, text).also { notes += it }

@InlineDsl
@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramDslEntry.leftOf(participantId: String) =
	MermaidSequenceDiagramNote.Location(MermaidSequenceDiagramNote.Position.LeftOf, participantId)

@InlineDsl
@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramDslEntry.rightOf(participantId: String) =
	MermaidSequenceDiagramNote.Location(MermaidSequenceDiagramNote.Position.RightOf, participantId)

@InlineDsl
@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramDslEntry.over(participantId1: String, participantId2: String) =
	MermaidSequenceDiagramNote.Location(MermaidSequenceDiagramNote.Position.RightOf, participantId1, participantId2)

@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramDslEntry.loop(text: String, block: MermaidSequenceDiagramLoop.() -> Unit) =
	MermaidSequenceDiagramLoop(text).also { it.block() }.also { scopes += it }

@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramDslEntry.opt(text: String, block: MermaidSequenceDiagramOptional.() -> Unit) =
	MermaidSequenceDiagramOptional(text).also { it.block() }.also { scopes += it }

@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramDslEntry.alt(text: String, block: MermaidSequenceDiagramAlternative.() -> Unit) =
	MermaidSequenceDiagramAlternative(text).also { it.block() }.also { scopes += it }

@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramDslEntry.highlight(text: String, block: MermaidSequenceDiagramHighlight.() -> Unit) =
	MermaidSequenceDiagramHighlight(text).also { it.block() }.also { scopes += it }

@MermaidSequenceDiagramDsl
inline infix fun MermaidSequenceDiagramParticipant.alias(alias: String) =
	this.also { it.alias = alias }

@MermaidSequenceDiagramDsl
inline infix fun MermaidSequenceDiagramMessage.text(text: String) =
	this.also { it.text = text }

@MermaidSequenceDiagramDsl
inline infix fun MermaidSequenceDiagramMessage.arrowShape(arrowShape: MermaidSequenceDiagramMessage.ArrowShape) =
	this.also { it.arrowShape = arrowShape }

@MermaidSequenceDiagramDsl
inline infix fun MermaidSequenceDiagramMessage.activate(isActivated: Boolean) =
	this.also { it.isActivated = isActivated }

@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramAlternative.`else`(text: String) =
	MermaidSequenceDiagramElse(text).also { elseScopes += it }

@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramAlternative.`else`() =
	MermaidSequenceDiagramElse().also { elseScopes += it }
//endregion
