@file:Suppress("NOTHING_TO_INLINE", "unused")

package com.windea.breezeframework.dsl.sequence

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*

//region dsl top declarations
/**序列图的Dsl。*/
@Reference("[Sequence Diagram](https://bramp.github.io/js-sequence-diagrams/)")
@DslMarker
@MustBeDocumented
annotation class SequenceDiagramDsl

/**序列图。*/
@Reference("[Sequence Diagram](https://bramp.github.io/js-sequence-diagrams/)")
@SequenceDiagramDsl
class SequenceDiagram @PublishedApi internal constructor() : DslDocument, SequenceDiagramDslEntry {
	var title: SequenceDiagramTitle? = null
	override val participants: MutableSet<SequenceDiagramParticipant> = mutableSetOf()
	override val messages: MutableList<SequenceDiagramMessage> = mutableListOf()
	override val notes: MutableList<SequenceDiagramNote> = mutableListOf()

	override var splitContent: Boolean = true

	override fun toString(): String {
		return listOfNotNull(
			title?.toString(),
			contentString.orNull()
		).joinToString(splitSeparator)
	}
}
//endregion

//region dsl declarations
/**序列图Dsl的入口。*/
@SequenceDiagramDsl
interface SequenceDiagramDslEntry : DslEntry, CanSplitLine, WithTransition<SequenceDiagramParticipant, SequenceDiagramMessage> {
	val participants:MutableSet<SequenceDiagramParticipant>
	val messages:MutableList<SequenceDiagramMessage>
	val notes:MutableList<SequenceDiagramNote>

	override val contentString:String
		get() {
			return listOfNotNull(
				participants.orNull()?.joinToString("\n"),
				messages.orNull()?.joinToString("\n"),
				notes.orNull()?.joinToString("\n")
			).joinToString(splitSeparator)
		}

	@SequenceDiagramDsl
	override fun String.links(other:String) = message(this, other)
}

/**序列图Dsl的元素。*/
@SequenceDiagramDsl
interface SequenceDiagramDslElement : DslElement
//endregion

//region dsl elements
/**序列图标题。*/
@SequenceDiagramDsl
class SequenceDiagramTitle @PublishedApi internal constructor(
	val text: String //换行符：\\n
) : SequenceDiagramDslElement {
	override fun toString(): String {
		return "title: ${text.replaceWithEscapedWrap()}"
	}
}

/**序列图参与者。*/
@SequenceDiagramDsl
class SequenceDiagramParticipant @PublishedApi internal constructor(
	val name:String
) : SequenceDiagramDslElement, WithId {
	var alias:String? = null

	override val id:String get() = alias ?: name

	override fun equals(other:Any?) = equalsByOne(this, other) { id }

	override fun hashCode() = hashCodeByOne(this) { id }

	override fun toString():String {
		val aliasSnippet = alias?.let { "as $it" }.orEmpty()
		return "participant $name$aliasSnippet"
	}
}

/**序列图消息。*/
@SequenceDiagramDsl
class SequenceDiagramMessage @PublishedApi internal constructor(
	val fromParticipantId:String,
	val toParticipantId:String
) : SequenceDiagramDslElement, WithNode {
	var text:String = ""
	var arrowShape:ArrowShape = ArrowShape.Arrow

	override val sourceNodeId:String get() = fromParticipantId
	override val targetNodeId:String get() = toParticipantId

	override fun toString():String {
		return "$fromParticipantId ${arrowShape.text} $toParticipantId: $text"
	}

	/**序列图消息的箭头类型。*/
	@SequenceDiagramDsl
	enum class ArrowShape(val text: String) {
		Arrow("->"), DashedArrow("-->"), OpenArrow("->>"), DashedOpenArrow("-->>")
	}
}

/**序列图注释。*/
@SequenceDiagramDsl
class SequenceDiagramNote @PublishedApi internal constructor(
	val location: Location
) : SequenceDiagramDslElement {
	var text: String = "" //换行符：\\n

	override fun toString(): String {
		val textSnippet = text.replaceWithEscapedWrap()
		return "note $location: $textSnippet"
	}

	/**序列图注释的位置。*/
	@SequenceDiagramDsl
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

	/**序列图注释的方位。*/
	@SequenceDiagramDsl
	enum class Position(val text: String) {
		LeftOf("left of"), RightOf("right of"), Over("over")
	}
}
//endregion

//region dsl build extensions
@SequenceDiagramDsl
inline fun sequenceDiagram(block: SequenceDiagram.() -> Unit) = SequenceDiagram().apply(block)

@SequenceDiagramDsl
inline fun SequenceDiagram.title(text: String) =
	SequenceDiagramTitle(text).also { title = it }

@SequenceDiagramDsl
inline fun SequenceDiagramDslEntry.participant(name: String) =
	SequenceDiagramParticipant(name).also { participants += it }

@SequenceDiagramDsl
inline fun SequenceDiagramDslEntry.message(fromParticipantId: String, toParticipantId: String) =
	SequenceDiagramMessage(fromParticipantId, toParticipantId).also { messages += it }

@SequenceDiagramDsl
inline fun SequenceDiagramDslEntry.message(
	fromParticipantId: String,
	arrowShape: SequenceDiagramMessage.ArrowShape,
	toParticipantId: String
) = SequenceDiagramMessage(fromParticipantId, toParticipantId)
	.also { it.arrowShape = arrowShape }
	.also { messages += it }

@SequenceDiagramDsl
inline fun SequenceDiagramDslEntry.note(location: SequenceDiagramNote.Location) =
	SequenceDiagramNote(location).also { notes += it }

@InlineDslFunction
@SequenceDiagramDsl
fun SequenceDiagramDslEntry.leftOf(participantId: String) =
	SequenceDiagramNote.Location(SequenceDiagramNote.Position.LeftOf, participantId)

@InlineDslFunction
@SequenceDiagramDsl
fun SequenceDiagramDslEntry.rightOf(participantId: String) =
	SequenceDiagramNote.Location(SequenceDiagramNote.Position.RightOf, participantId)

@InlineDslFunction
@SequenceDiagramDsl
fun SequenceDiagramDslEntry.over(participantId1: String, participantId2: String) =
	SequenceDiagramNote.Location(SequenceDiagramNote.Position.RightOf, participantId1, participantId2)

@SequenceDiagramDsl
inline infix fun SequenceDiagramParticipant.alias(alias: String) =
	this.also { it.alias = alias }

@SequenceDiagramDsl
inline infix fun SequenceDiagramMessage.text(text: String) =
	this.also { it.text = text }

@SequenceDiagramDsl
inline infix fun SequenceDiagramMessage.arrowShape(arrowShape: SequenceDiagramMessage.ArrowShape) =
	this.also { it.arrowShape = arrowShape }

@SequenceDiagramDsl
inline infix fun SequenceDiagramNote.text(text: String) =
	this.also { it.text = text }
//endregion

//region helpful extensions
/**将`\n`或`\r`替换成`\\n`和`\\r`。*/
@PublishedApi
internal fun String.replaceWithEscapedWrap() = this.replace("\n", "\\n").replace("\r", "\\r")
//endregion
