@file:Suppress("NOTHING_TO_INLINE", "unused")

package com.windea.breezeframework.dsl.graph

import com.windea.breezeframework.core.annotations.api.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*

//TODO

//REGION top annotations and interfaces

/**序列图的Dsl。*/
@ReferenceApi("[Sequence Diagram](https://bramp.github.io/js-sequence-diagrams/)")
@DslMarker
private annotation class SequenceDiagramDsl

/**序列图。*/
@SequenceDiagramDsl
class SequenceDiagram @PublishedApi internal constructor() : DslBuilder, SequenceDiagramDslEntry {
	var title: SequenceDiagramTitle? = null
	override val participants: MutableSet<SequenceDiagramParticipant> = mutableSetOf()
	override val messages: MutableList<SequenceDiagramMessage> = mutableListOf()
	override val notes: MutableList<SequenceDiagramNote> = mutableListOf()
	
	override fun toString(): String {
		val titleSnippet = title?.let { "$it\n\n" }.orEmpty()
		val contentSnippet = toContentString()
		return "$titleSnippet$contentSnippet"
	}
}

//REGION dsl interfaces

/**序列图Dsl的入口。*/
@SequenceDiagramDsl
interface SequenceDiagramDslEntry : DslEntry, WithTransition<SequenceDiagramParticipant, SequenceDiagramMessage> {
	val participants: MutableSet<SequenceDiagramParticipant>
	val messages: MutableList<SequenceDiagramMessage>
	val notes: MutableList<SequenceDiagramNote>
	
	fun toContentString(): String {
		return arrayOf(
			participants.joinToStringOrEmpty("\n"),
			messages.joinToStringOrEmpty("\n"),
			notes.joinToStringOrEmpty("\n")
		).filterNotEmpty().joinToString("\n\n")
	}
	
	@GenericDsl
	override fun String.fromTo(other: String) = message(this, other)
}

/**序列图Dsl的元素。*/
@SequenceDiagramDsl
interface SequenceDiagramDslElement : DslElement

//REGION dsl elements

/**序列图标题。*/
@SequenceDiagramDsl
class SequenceDiagramTitle @PublishedApi internal constructor(
	val name: String //NOTE can wrap by "\n", treat blank as whitespace
) : SequenceDiagramDslElement {
	override fun toString(): String {
		return "title: ${name.replaceWithEscapedWrap()}"
	}
}

/**序列图参与者。*/
@SequenceDiagramDsl
class SequenceDiagramParticipant @PublishedApi internal constructor(
	val name: String
) : SequenceDiagramDslElement, WithName {
	var alias: String? = null
	
	override val _name: String get() = alias ?: name
	
	override fun equals(other: Any?) = equalsBySelect(this, other) { arrayOf(alias ?: name) }
	
	override fun hashCode() = hashCodeBySelect(this) { arrayOf(alias ?: name) }
	
	override fun toString(): String {
		val aliasSnippet = alias?.let { "as $it" }.orEmpty()
		return "participant $name$aliasSnippet"
	}
}

/**序列图消息。*/
@SequenceDiagramDsl
class SequenceDiagramMessage @PublishedApi internal constructor(
	val fromActorName: String,
	val toActorName: String,
	var text: String = "" //NOTE can not be null
) : SequenceDiagramDslElement, WithNode<SequenceDiagramParticipant> {
	var arrowShape: ArrowShape = ArrowShape.Arrow
	
	override val _fromNodeName: String get() = fromActorName
	override val _toNodeName: String get() = toActorName
	
	override fun toString(): String {
		return "$fromActorName ${arrowShape.text} $toActorName: $text"
	}
	
	enum class ArrowShape(val text: String) {
		Arrow("->"), DashedArrow("-->"), OpenArrow("->>"), DashedOpenArrow("-->>")
	}
}

/**序列图注释。*/
@SequenceDiagramDsl
class SequenceDiagramNote @PublishedApi internal constructor(
	val location: Location,
	val text: String //NOTE can be wrap by "\n"
) : SequenceDiagramDslElement {
	override fun toString(): String {
		val textSnippet = text.replaceWithEscapedWrap()
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

//REGION build extensions

@SequenceDiagramDsl
inline fun sequenceDiagram(block: SequenceDiagram.() -> Unit) = SequenceDiagram().also { it.block() }

@SequenceDiagramDsl
inline fun SequenceDiagram.title(naem: String) =
	SequenceDiagramTitle(naem).also { title = it }

@SequenceDiagramDsl
inline fun SequenceDiagramDslEntry.participant(name: String) =
	SequenceDiagramParticipant(name).also { participants += it }

@SequenceDiagramDsl
inline fun SequenceDiagramDslEntry.message(fromActorName: String, toActorName: String, text: String = "") =
	SequenceDiagramMessage(fromActorName, toActorName, text).also { messages += it }

@SequenceDiagramDsl
inline fun SequenceDiagramDslEntry.message(fromActor: SequenceDiagramParticipant,
	toActor: SequenceDiagramParticipant, text: String = "") =
	SequenceDiagramMessage(fromActor.alias ?: fromActor.name, toActor.alias ?: toActor.name, text)
		.also { messages += it }

@SequenceDiagramDsl
inline fun SequenceDiagramDslEntry.message(
	fromActorName: String,
	arrowShape: SequenceDiagramMessage.ArrowShape,
	toActorName: String,
	text: String = ""
) = SequenceDiagramMessage(fromActorName, toActorName, text)
	.also { it.arrowShape = arrowShape }
	.also { messages += it }

@SequenceDiagramDsl
inline fun SequenceDiagramDslEntry.note(location: SequenceDiagramNote.Location, text: String) =
	SequenceDiagramNote(location, text).also { notes += it }

@InlineDsl
@SequenceDiagramDsl
inline fun SequenceDiagramDslEntry.leftOf(actorName: String) =
	SequenceDiagramNote.Location(SequenceDiagramNote.Position.LeftOf, actorName)

@InlineDsl
@SequenceDiagramDsl
inline fun SequenceDiagramDslEntry.rightOf(actorName: String) =
	SequenceDiagramNote.Location(SequenceDiagramNote.Position.RightOf, actorName)

@InlineDsl
@SequenceDiagramDsl
inline fun SequenceDiagramDslEntry.over(actorName1: String, actorName2: String) =
	SequenceDiagramNote.Location(SequenceDiagramNote.Position.RightOf, actorName1, actorName2)

@SequenceDiagramDsl
inline infix fun SequenceDiagramParticipant.alias(alias: String) =
	this.also { it.alias = alias }

@SequenceDiagramDsl
inline infix fun SequenceDiagramMessage.text(text: String) =
	this.also { it.text = text }

@SequenceDiagramDsl
inline infix fun SequenceDiagramMessage.arrowShape(arrowShape: SequenceDiagramMessage.ArrowShape) =
	this.also { it.arrowShape = arrowShape }
