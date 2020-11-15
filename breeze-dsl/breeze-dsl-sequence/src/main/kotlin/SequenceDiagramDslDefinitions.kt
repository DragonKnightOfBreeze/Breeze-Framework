// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl.sequence

import com.windea.breezeframework.core.extension.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.DslConstants.ls
import com.windea.breezeframework.dsl.api.*

/**
 * DslDocument definitions of [SequenceDiagramDsl].
 */
@SequenceDiagramDslMarker
interface SequenceDiagramDslDefinitions {
	companion object {
		internal fun String.normalWrap() = this.replace("\n", "\\n").replace("\r", "\\r")
	}


	/**序列图领域特定语言的入口。*/
	@SequenceDiagramDslMarker
	interface IDslEntry : DslEntry, Splitable, WithTransition<Participant, Message> {
		val participants: MutableSet<Participant>
		val messages: MutableList<Message>
		val notes: MutableList<Note>

		override fun toContentString(): String {
			return arrayOf(participants.joinToText(ls), messages.joinToText(ls), notes.joinToText(ls)).doSplit()
		}

		override fun String.links(other: String) = message(this, other)
	}

	/**序列图领域特定语言的元素。*/
	@SequenceDiagramDslMarker
	interface IDslElement : DslElement


	/**
	 * 序列图的标题。
	 * @property text 标题的文本。可以使用`\\n`换行。
	 */
	@SequenceDiagramDslMarker
	class Title @PublishedApi internal constructor(
		val text: String,
	) : IDslElement {
		override fun toString(): String {
			val textSnippet = text.normalWrap()
			return "title: $textSnippet"
		}
	}

	/**序列图的参与者。*/
	@SequenceDiagramDslMarker
	class Participant @PublishedApi internal constructor(
		val name: String,
	) : IDslElement, WithId {
		var alias: String? = null
		override val id: String get() = alias ?: name

		override fun equals(other: Any?) = equalsBy(this, other) { arrayOf(id) }

		override fun hashCode() = hashCodeBy(this) { arrayOf(id) }

		override fun toString(): String {
			val aliasSnippet = alias.toText { "as $it" }
			return "participant $name$aliasSnippet"
		}
	}

	/**序列图的消息。*/
	@SequenceDiagramDslMarker
	class Message @PublishedApi internal constructor(
		val fromParticipantId: String, val toParticipantId: String,
	) : IDslElement, WithNode {
		var text: String = ""
		var arrowShape: ArrowShape = ArrowShape.Arrow
		override val sourceNodeId: String get() = fromParticipantId
		override val targetNodeId: String get() = toParticipantId

		override fun toString(): String {
			val arrowShapeSnippet = arrowShape.text
			return "$fromParticipantId $arrowShapeSnippet $toParticipantId: $text"
		}
	}

	/**
	 * 序列图的注释。
	 * @property location 注释的位置。
	 * @property text 注释的文本。可以使用`\\n`换行。
	 */
	@SequenceDiagramDslMarker
	class Note @PublishedApi internal constructor(
		val location: NoteLocation, var text: String = "",
	) : IDslElement {

		override fun toString(): String {
			val textSnippet = text.normalWrap()
			return "note $location: $textSnippet"
		}
	}

	/**序列图注释的位置。*/
	@SequenceDiagramDslMarker
	class NoteLocation @PublishedApi internal constructor(
		val position: NotePosition, val participantId1: String, val participantId2: String? = null,
	) {
		override fun toString(): String {
			val participantId2Snippet = participantId2?.let { ", $it" }.orEmpty()
			val positionSnippet = position.text
			return "$positionSnippet $participantId1$participantId2Snippet"
		}
	}


	/**序列图消息的箭头类型。*/
	@SequenceDiagramDslMarker
	enum class ArrowShape(internal val text: String) {
		Arrow("->"), DashedArrow("-->"), OpenArrow("->>"), DashedOpenArrow("-->>")
	}

	/**序列图注释的方位。*/
	@SequenceDiagramDslMarker
	enum class NotePosition(internal val text: String) {
		LeftOf("left of"), RightOf("right of"), Over("over")
	}
}
