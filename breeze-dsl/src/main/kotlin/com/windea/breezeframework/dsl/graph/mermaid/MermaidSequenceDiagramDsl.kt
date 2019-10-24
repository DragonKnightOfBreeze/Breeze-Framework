@file:Suppress("NOTHING_TO_INLINE", "unused")

package com.windea.breezeframework.dsl.graph.mermaid

import com.windea.breezeframework.core.annotations.api.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.graph.mermaid.MermaidConfig.indent
import org.intellij.lang.annotations.*

//NOTE can have a title by `title: text`, but it is not introduced in official api

//REGION top annotations and interfaces

/**Mermaid序列图的Dsl。*/
@ReferenceApi("[Mermaid Sequence Diagram](https://mermaidjs.github.io/#/sequenceDiagram)")
@DslMarker
private annotation class MermaidSequenceDiagramDsl

/**Mermaid序列图。*/
@MermaidSequenceDiagramDsl
class MermaidSequenceDiagram @PublishedApi internal constructor() : Mermaid(), MermaidSequenceDiagramDslEntry, CanIndent {
	override val participants: MutableSet<MermaidSequenceDiagramParticipant> = mutableSetOf()
	override val messages: MutableList<MermaidSequenceDiagramMessage> = mutableListOf()
	override val notes: MutableList<MermaidSequenceDiagramNote> = mutableListOf()
	override val scopes: MutableList<MermaidSequenceDiagramScope> = mutableListOf()
	
	override var indentContent: Boolean = true
	
	override fun toString(): String {
		val contentSnippet = toContentString()
			.let { if(indentContent) it.prependIndent(indent) else it }
		return "sequenceDiagram\n$contentSnippet"
	}
}

//REGION dsl interfaces

/**Mermaid序列图Dsl的入口。*/
@MermaidSequenceDiagramDsl
interface MermaidSequenceDiagramDslEntry : MermaidDslEntry,
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
		).filterNotEmpty().joinToStringOrEmpty("\n\n")
	}
	
	@GenericDsl
	override fun String.fromTo(other: String) = message(this, other)
}

/**Mermaid序列图Dsl的元素。*/
@MermaidSequenceDiagramDsl
interface MermaidSequenceDiagramDslElement : MermaidDslElement

//REGION dsl elements

/**Mermaid序列图参与者。*/
@MermaidSequenceDiagramDsl
class MermaidSequenceDiagramParticipant @PublishedApi internal constructor(
	val name: String
) : MermaidSequenceDiagramDslElement, WithName {
	var alias: String? = null
	
	override val _name: String get() = alias ?: name
	
	override fun equals(other: Any?) = equalsBySelect(this, other) { arrayOf(alias ?: name) }
	
	override fun hashCode() = hashCodeBySelect(this) { arrayOf(alias ?: name) }
	
	override fun toString(): String {
		val aliasSnippet = alias?.let { "$it as " }.orEmpty()
		return "participant $aliasSnippet$name"
	}
}

/**Mermaid序列图消息。*/
@MermaidSequenceDiagramDsl
class MermaidSequenceDiagramMessage @PublishedApi internal constructor(
	val fromActorName: String,
	val toActorName: String,
	var text: String = ""//NOTE can not be null
) : MermaidSequenceDiagramDslElement, WithNode<MermaidSequenceDiagramParticipant> {
	var arrowShape: ArrowShape = ArrowShape.Arrow
	var isActivated: Boolean? = null
	
	override val _fromNodeName get() = fromActorName
	override val _toNodeName get() = toActorName
	
	override fun toString(): String {
		val activateSnippet = isActivated?.let { if(it) "+ " else "- " }.orEmpty()
		return "$fromActorName ${arrowShape.text} $activateSnippet$toActorName: $text"
	}
	
	enum class ArrowShape(val text: String) {
		Arrow("->>"), DashedArrow("-->>"), Line("->"), DashedLine("-->"), Cross("-x"), DashedCross("--x")
	}
}

/**Mermaid序列图注释。*/
@MermaidSequenceDiagramDsl
class MermaidSequenceDiagramNote @PublishedApi internal constructor(
	val location: Location,
	val text: String //NOTE can wrap by "<br>"
) : MermaidSequenceDiagramDslElement {
	override fun toString(): String {
		val textSnippet = text.replaceWithHtmlWrap()
		return "note $location: $textSnippet"
	}
	
	class Location @PublishedApi internal constructor(
		val position: Position,
		val actorName1: String,
		val actorName2: String? = null
	) {
		override fun toString(): String {
			val targetActorName2Snippet = actorName2?.let { ", $it" }.orEmpty()
			return "${position.text} $actorName1$targetActorName2Snippet"
		}
	}
	
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
	
	override fun toString(): String {
		val textSnippet = text?.let { " $it" }.orEmpty()
		val contentSnippet = toContentString()
			.let { if(indentContent) it.prependIndent(indent) else it }
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
	@Language(value = "SCSS", prefix = "\$color:")
	color: String
) : MermaidSequenceDiagramScope("rect", color)

//REGION build extensions

/**构建Mermaid序列图。*/
@MermaidSequenceDiagramDsl
fun mermaidSequenceDiagram(block: MermaidSequenceDiagram.() -> Unit) = MermaidSequenceDiagram().also { it.block() }

@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramDslEntry.participant(name: String) =
	MermaidSequenceDiagramParticipant(name).also { participants += it }

@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramDslEntry.message(fromActorName: String, toActorName: String, text: String = "") =
	MermaidSequenceDiagramMessage(fromActorName, toActorName, text).also { messages += it }

@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramDslEntry.message(fromActor: MermaidSequenceDiagramParticipant,
	toActor: MermaidSequenceDiagramParticipant, text: String = "") =
	MermaidSequenceDiagramMessage(fromActor.alias ?: fromActor.name, toActor.alias ?: toActor.name, text)
		.also { messages += it }

@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramDslEntry.message(
	fromActorName: String,
	arrowShape: MermaidSequenceDiagramMessage.ArrowShape,
	isActivated: Boolean = false,
	toActorName: String,
	text: String = ""
) = MermaidSequenceDiagramMessage(fromActorName, toActorName, text)
	.also { it.arrowShape = arrowShape;it.isActivated = isActivated }
	.also { messages += it }

@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramDslEntry.note(location: MermaidSequenceDiagramNote.Location, text: String) =
	MermaidSequenceDiagramNote(location, text).also { notes += it }

@InlineDsl
@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramDslEntry.leftOf(actorName: String) =
	MermaidSequenceDiagramNote.Location(MermaidSequenceDiagramNote.Position.LeftOf, actorName)

@InlineDsl
@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramDslEntry.rightOf(actorName: String) =
	MermaidSequenceDiagramNote.Location(MermaidSequenceDiagramNote.Position.RightOf, actorName)

@InlineDsl
@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramDslEntry.over(actorName1: String, actorName2: String) =
	MermaidSequenceDiagramNote.Location(MermaidSequenceDiagramNote.Position.RightOf, actorName1, actorName2)

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
