@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.dsl.mermaid

import com.windea.breezeframework.core.annotations.marks.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.MermaidConfig.indent

//REGION Dsl annotations

@DslMarker
internal annotation class MermaidSequenceDsl

//REGION Dsl & Dsl elements & Dsl config

/**Mermaid序列图。*/
@Reference("[Mermaid Sequence Diagram](https://mermaidjs.github.io/#/sequenceDiagram)")
@MermaidSequenceDsl
class MermaidSequence @PublishedApi internal constructor() : Mermaid(), MermaidSequenceDslEntry {
	override val actors: MutableSet<MermaidSequenceParticipant> = mutableSetOf()
	override val messages: MutableList<MermaidSequenceMessage> = mutableListOf()
	override val notes: MutableList<MermaidSequenceNote> = mutableListOf()
	override val scopes: MutableList<MermaidSequenceScope> = mutableListOf()
	
	override var indentContent: Boolean = true
	
	override fun toString(): String {
		val contentSnippet = getString()
		val indentedContentSnippet = if(indentContent) contentSnippet.prependIndent(indent) else contentSnippet
		return "sequenceDiagram\n$indentedContentSnippet"
	}
}

/**Mermaid序列图Dsl的入口。*/
@MermaidSequenceDsl
interface MermaidSequenceDslEntry : CanIndentContent, CommentContent<MermaidSequenceNote> {
	val actors: MutableSet<MermaidSequenceParticipant>
	val messages: MutableList<MermaidSequenceMessage>
	val notes: MutableList<MermaidSequenceNote>
	val scopes: MutableList<MermaidSequenceScope>
	
	override var indentContent: Boolean
	
	fun getString(): String {
		return arrayOf(
			actors.joinToString("\n"),
			messages.joinToString("\n"),
			notes.joinToString("\n"),
			scopes.joinToString("\n")
		).filterNotEmpty().joinToString("\n\n")
	}
	
	@MermaidSequenceDsl
	override fun String.not() = note(this)
}

/**Mermaid序列图Dsl的元素。*/
@MermaidSequenceDsl
interface MermaidSequenceDslElement : MermaidDslElement

/**Mermaid序列图参与者。*/
@MermaidSequenceDsl
class MermaidSequenceParticipant @PublishedApi internal constructor(
	val name: String
) : MermaidSequenceDslElement, UniqueDslElement {
	var alias: String? = null
	
	override fun equals(other: Any?): Boolean {
		return this === other || (other is MermaidSequenceParticipant && (other.alias == alias || other.name == name))
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
@MermaidSequenceDsl
class MermaidSequenceMessage @PublishedApi internal constructor(
	val fromActorId: String,
	val toActorId: String,
	val text: String
) : MermaidSequenceDslElement {
	var arrowShape: MermaidSequenceMessageArrowShape = MermaidSequenceMessageArrowShape.Arrow
	var activateStatus: Boolean? = null
	
	override fun toString(): String {
		val activateSnippet = activateStatus?.let { if(it) "+ " else "- " } ?: ""
		return "$fromActorId ${arrowShape.text} $activateSnippet$toActorId: $text"
	}
}

/**Mermaid序列图注释。*/
@MermaidSequenceDsl
class MermaidSequenceNote @PublishedApi internal constructor(
	val text: String //NOTE can wrap by "<br>"
) : MermaidSequenceDslElement {
	var position: MermaidSequenceNodePosition = MermaidSequenceNodePosition.RightOf
	var targetActorId: String = "Default"
	var targetActor2Id: String? = null
	
	override fun toString(): String {
		val textSnippet = text.replaceWithHtmlWrap()
		val targetActor2NameSnippet = targetActor2Id?.let { ", $it" } ?: ""
		return "note $position $targetActorId$targetActor2NameSnippet: $textSnippet"
	}
}

/**Mermaid序列图作用域。*/
@MermaidSequenceDsl
sealed class MermaidSequenceScope(
	val type: String,
	val text: String?
) : MermaidSequenceDslElement, MermaidSequenceDslEntry {
	override val actors: MutableSet<MermaidSequenceParticipant> = mutableSetOf()
	override val messages: MutableList<MermaidSequenceMessage> = mutableListOf()
	override val notes: MutableList<MermaidSequenceNote> = mutableListOf()
	override val scopes: MutableList<MermaidSequenceScope> = mutableListOf()
	
	override var indentContent: Boolean = true
	
	override fun toString(): String {
		val textSnippet = text?.let { " $it" } ?: ""
		val contentSnippet = getString()
		val indentedContentSnippet = if(indentContent) contentSnippet.prependIndent(indent) else contentSnippet
		return "$type$textSnippet\n$indentedContentSnippet\nend"
	}
}

/**Mermaid序列图循环作用域。*/
@MermaidSequenceDsl
class MermaidSequenceLoop @PublishedApi internal constructor(text: String) : MermaidSequenceScope("loop", text)

/**Mermaid序列图可选作用域。*/
@MermaidSequenceDsl
class MermaidSequenceOptional @PublishedApi internal constructor(text: String) : MermaidSequenceScope("opt", text)

/**Mermaid序列图替代作用域。*/
@MermaidSequenceDsl
class MermaidSequenceAlternative @PublishedApi internal constructor(text: String) : MermaidSequenceScope("alt", text) {
	val elseScopes: MutableList<MermaidSequenceElse> = mutableListOf()
	
	override fun toString(): String {
		val contentSnippet = getString()
		val elseScopesSnippet = elseScopes.joinToString("\n").ifNotEmpty { "\n$it" }
		return "$contentSnippet$elseScopesSnippet"
	}
}

/**Mermaid序列图其余作用域。*/
@MermaidSequenceDsl
class MermaidSequenceElse @PublishedApi internal constructor(text: String? = null) : MermaidSequenceScope("else", text)

/**Mermaid序列图颜色高亮作用域。*/
@MermaidSequenceDsl
class MermaidSequenceHighlight @PublishedApi internal constructor(colorText: String) : MermaidSequenceScope("rect", colorText)

//REGION Enumerations and constants

/**Mermaid序列图消息的箭头形状。*/
@MermaidSequenceDsl
enum class MermaidSequenceMessageArrowShape(val text: String) {
	Arrow("->>"), DottedArrow("-->>"), Line("->"), DottedLine("-->"), Cross("-x"), DottedCross("--x")
}

/**Mermaid序列图注释的位置。*/
@MermaidSequenceDsl
enum class MermaidSequenceNodePosition(val text: String) {
	RightOf("right of"), LeftOf("left of"), Over("over")
}

//REGION Build extensions

/**构建Mermaid序列图。*/
@MermaidSequenceDsl
fun mermaidSequence(builder: MermaidSequence.() -> Unit) = MermaidSequence().also { it.builder() }

@MermaidSequenceDsl
inline fun MermaidSequenceDslEntry.participant(name: String) =
	MermaidSequenceParticipant(name).also { actors += it }

@MermaidSequenceDsl
inline fun MermaidSequenceDslEntry.message(fromActorId: String, toActorId: String, text: String) =
	MermaidSequenceMessage(fromActorId, toActorId, text).also { messages += it }

@MermaidSequenceDsl
inline fun MermaidSequenceDslEntry.message(fromActor: MermaidSequenceParticipant, toActorId: String, text: String) =
	message(fromActor.alias ?: fromActor.name, toActorId, text)

@MermaidSequenceDsl
inline fun MermaidSequenceDslEntry.message(fromActorId: String, toActor: MermaidSequenceParticipant, text: String) =
	message(fromActorId, toActor.alias ?: toActor.name, text)

@MermaidSequenceDsl
inline fun MermaidSequenceDslEntry.message(fromActor: MermaidSequenceParticipant, toActor: MermaidSequenceParticipant, text: String) =
	message(fromActor.alias ?: fromActor.name, toActor.alias ?: toActor.name, text)

@MermaidSequenceDsl
inline fun MermaidSequenceDslEntry.note(text: String) = MermaidSequenceNote(text).also { notes += it }

@MermaidSequenceDsl
inline fun MermaidSequenceDslEntry.loop(text: String, builder: MermaidSequenceLoop.() -> Unit) =
	MermaidSequenceLoop(text).also { it.builder() }.also { scopes += it }

@MermaidSequenceDsl
inline fun MermaidSequenceDslEntry.opt(text: String, builder: MermaidSequenceOptional.() -> Unit) =
	MermaidSequenceOptional(text).also { it.builder() }.also { scopes += it }

@MermaidSequenceDsl
inline fun MermaidSequenceDslEntry.alt(text: String, builder: MermaidSequenceAlternative.() -> Unit) =
	MermaidSequenceAlternative(text).also { it.builder() }.also { scopes += it }

@MermaidSequenceDsl
inline fun MermaidSequenceDslEntry.highlight(text: String, builder: MermaidSequenceHighlight.() -> Unit) =
	MermaidSequenceHighlight(text).also { it.builder() }.also { scopes += it }

@MermaidSequenceDsl
inline infix fun MermaidSequenceParticipant.alias(alias: String) =
	this.also { it.alias = alias }

@MermaidSequenceDsl
inline infix fun MermaidSequenceMessage.arrowShape(arrowShape: MermaidSequenceMessageArrowShape) =
	this.also { it.arrowShape = arrowShape }

@MermaidSequenceDsl
inline infix fun MermaidSequenceMessage.activate(status: Boolean) =
	this.also { it.activateStatus = status }

@MermaidSequenceDsl
inline infix fun MermaidSequenceNote.leftOf(actorId: String) =
	this.also { it.position = MermaidSequenceNodePosition.LeftOf }.also { it.targetActorId = actorId }

@MermaidSequenceDsl
inline infix fun MermaidSequenceNote.rightOf(actorId: String) =
	this.also { it.position = MermaidSequenceNodePosition.RightOf }.also { it.targetActorId = actorId }

@MermaidSequenceDsl
inline infix fun MermaidSequenceNote.over(actorIdPair: Pair<String, String>) =
	this.also { it.position = MermaidSequenceNodePosition.RightOf }
		.also { it.targetActorId = actorIdPair.first }.also { it.targetActor2Id = actorIdPair.second }

@MermaidSequenceDsl
inline fun MermaidSequenceAlternative.`else`(text: String) =
	MermaidSequenceElse(text).also { elseScopes += it }

@MermaidSequenceDsl
inline fun MermaidSequenceAlternative.`else`() =
	MermaidSequenceElse().also { elseScopes += it }
