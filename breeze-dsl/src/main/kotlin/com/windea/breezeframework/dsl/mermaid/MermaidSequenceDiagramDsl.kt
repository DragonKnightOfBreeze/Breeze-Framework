@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.dsl.mermaid

import com.windea.breezeframework.core.annotations.api.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.MermaidConfig.indent

//REGION top annotations and interfaces

@DslMarker
private annotation class MermaidSequenceDiagramDsl

/**Mermaid序列图。*/
@ReferenceApi("[Mermaid Sequence Diagram](https://mermaidjs.github.io/#/sequenceDiagram)")
@MermaidSequenceDiagramDsl
class MermaidSequenceDiagram @PublishedApi internal constructor() : Mermaid(), MermaidSequenceDiagramDslEntry {
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

@MermaidSequenceDiagramDsl
interface MermaidSequenceDiagramDslEntry : IndentContent, WithComment<MermaidSequenceDiagramNote> {
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
	
	@MermaidSequenceDiagramDsl
	override fun String.unaryMinus() = note(this)
}

/**Mermaid序列图Dsl的元素。*/
@MermaidSequenceDiagramDsl
interface MermaidSequenceDiagramDslElement : MermaidDslElement

//REGION dsl elements

/**Mermaid序列图参与者。*/
@MermaidSequenceDiagramDsl
class MermaidSequenceDiagramParticipant @PublishedApi internal constructor(
	val name: String
) : MermaidSequenceDiagramDslElement {
	var alias: String? = null
	
	override fun equals(other: Any?): Boolean {
		return this === other || (other is MermaidSequenceDiagramParticipant && (other.alias == alias || other.name == name))
	}
	
	override fun hashCode(): Int {
		return alias?.hashCode() ?: name.hashCode()
	}
	
	override fun toString(): String {
		val aliasSnippet = if(alias.isNullOrEmpty()) "" else "$alias as "
		return "participant $aliasSnippet$name"
	}
}

/**Mermaid序列图消息。*/
@MermaidSequenceDiagramDsl
class MermaidSequenceDiagramMessage @PublishedApi internal constructor(
	val fromActorId: String,
	val toActorId: String,
	val text: String
) : MermaidSequenceDiagramDslElement {
	var arrowShape: MermaidSequenceDiagramMessageArrowShape = MermaidSequenceDiagramMessageArrowShape.Arrow
	var isActivated: Boolean? = null
	
	override fun toString(): String {
		val activateSnippet = isActivated?.let { if(it) "+ " else "- " }.orEmpty()
		return "$fromActorId ${arrowShape.text} $activateSnippet$toActorId: $text"
	}
}

/**Mermaid序列图注释。*/
@MermaidSequenceDiagramDsl
class MermaidSequenceDiagramNote @PublishedApi internal constructor(
	val text: String //NOTE can wrap by "<br>"
) : MermaidSequenceDiagramDslElement {
	var position: MermaidSequenceDiagramNodePosition = MermaidSequenceDiagramNodePosition.RightOf
	var targetActorId: String = "Default"
	var targetActor2Id: String? = null
	
	override fun toString(): String {
		val textSnippet = text.replaceWithHtmlWrap()
		val targetActor2NameSnippet = targetActor2Id?.let { ", $it" }.orEmpty()
		return "note $position $targetActorId$targetActor2NameSnippet: $textSnippet"
	}
}

/**Mermaid序列图作用域。*/
@MermaidSequenceDiagramDsl
sealed class MermaidSequenceDiagramScope(
	val type: String,
	val text: String?
) : MermaidSequenceDiagramDslElement, MermaidSequenceDiagramDslEntry {
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
	text: String) : MermaidSequenceDiagramScope("loop", text)

/**Mermaid序列图可选作用域。*/
@MermaidSequenceDiagramDsl
class MermaidSequenceDiagramOptional @PublishedApi internal constructor(
	text: String) : MermaidSequenceDiagramScope("opt", text)

/**Mermaid序列图替代作用域。*/
@MermaidSequenceDiagramDsl
class MermaidSequenceDiagramAlternative @PublishedApi internal constructor(
	text: String) : MermaidSequenceDiagramScope("alt", text) {
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
	text: String? = null) : MermaidSequenceDiagramScope("else", text)

/**Mermaid序列图颜色高亮作用域。*/
@MermaidSequenceDiagramDsl
class MermaidSequenceDiagramHighlight @PublishedApi internal constructor(
	colorText: String) : MermaidSequenceDiagramScope("rect", colorText)

//REGION enumerations and constants

/**Mermaid序列图消息的箭头形状。*/
@MermaidSequenceDiagramDsl
enum class MermaidSequenceDiagramMessageArrowShape(val text: String) {
	Arrow("->>"), DottedArrow("-->>"), Line("->"), DottedLine("-->"), Cross("-x"), DottedCross("--x")
}

/**Mermaid序列图注释的位置。*/
@MermaidSequenceDiagramDsl
enum class MermaidSequenceDiagramNodePosition(val text: String) {
	RightOf("right of"), LeftOf("left of"), Over("over")
}

//REGION build extensions

/**构建Mermaid序列图。*/
@MermaidSequenceDiagramDsl
fun mermaidSequenceDiagram(builder: MermaidSequenceDiagram.() -> Unit) = MermaidSequenceDiagram().also { it.builder() }

@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramDslEntry.participant(name: String) =
	MermaidSequenceDiagramParticipant(name).also { participants += it }

@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramDslEntry.message(fromActorId: String, toActorId: String, text: String) =
	MermaidSequenceDiagramMessage(fromActorId, toActorId, text).also { messages += it }

@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramDslEntry.message(fromActor: MermaidSequenceDiagramParticipant, toActorId: String,
	text: String) =
	message(fromActor.alias ?: fromActor.name, toActorId, text)

@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramDslEntry.message(fromActorId: String, toActor: MermaidSequenceDiagramParticipant,
	text: String) =
	message(fromActorId, toActor.alias ?: toActor.name, text)

@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramDslEntry.message(fromActor: MermaidSequenceDiagramParticipant,
	toActor: MermaidSequenceDiagramParticipant,
	text: String) =
	message(fromActor.alias ?: fromActor.name, toActor.alias ?: toActor.name, text)

@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramDslEntry.note(text: String) =
	MermaidSequenceDiagramNote(text).also { notes += it }

@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramDslEntry.loop(text: String, builder: MermaidSequenceDiagramLoop.() -> Unit) =
	MermaidSequenceDiagramLoop(text).also { it.builder() }.also { scopes += it }

@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramDslEntry.opt(text: String, builder: MermaidSequenceDiagramOptional.() -> Unit) =
	MermaidSequenceDiagramOptional(text).also { it.builder() }.also { scopes += it }

@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramDslEntry.alt(text: String, builder: MermaidSequenceDiagramAlternative.() -> Unit) =
	MermaidSequenceDiagramAlternative(text).also { it.builder() }.also { scopes += it }

@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramDslEntry.highlight(text: String, builder: MermaidSequenceDiagramHighlight.() -> Unit) =
	MermaidSequenceDiagramHighlight(text).also { it.builder() }.also { scopes += it }

@MermaidSequenceDiagramDsl
inline infix fun MermaidSequenceDiagramParticipant.alias(alias: String) =
	this.also { it.alias = alias }

@MermaidSequenceDiagramDsl
inline infix fun MermaidSequenceDiagramMessage.arrowShape(arrowShape: MermaidSequenceDiagramMessageArrowShape) =
	this.also { it.arrowShape = arrowShape }

@MermaidSequenceDiagramDsl
inline infix fun MermaidSequenceDiagramMessage.activate(isActivated: Boolean) =
	this.also { it.isActivated = isActivated }

@MermaidSequenceDiagramDsl
inline infix fun MermaidSequenceDiagramNote.leftOf(actorId: String) =
	this.also { it.position = MermaidSequenceDiagramNodePosition.LeftOf }.also { it.targetActorId = actorId }

@MermaidSequenceDiagramDsl
inline infix fun MermaidSequenceDiagramNote.rightOf(actorId: String) =
	this.also { it.position = MermaidSequenceDiagramNodePosition.RightOf }.also { it.targetActorId = actorId }

@MermaidSequenceDiagramDsl
inline infix fun MermaidSequenceDiagramNote.over(actorIdPair: Pair<String, String>) =
	this.also { it.position = MermaidSequenceDiagramNodePosition.RightOf }
		.also { it.targetActorId = actorIdPair.first }
		.also { it.targetActor2Id = actorIdPair.second }

@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramAlternative.`else`(text: String) =
	MermaidSequenceDiagramElse(text).also { elseScopes += it }

@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramAlternative.`else`() =
	MermaidSequenceDiagramElse().also { elseScopes += it }
