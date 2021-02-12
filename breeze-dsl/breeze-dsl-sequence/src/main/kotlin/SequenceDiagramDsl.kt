// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl.sequence

import com.windea.breezeframework.core.extension.*
import com.windea.breezeframework.dsl.api.*
import com.windea.breezeframework.dsl.DslDocument as IDslDocument
import com.windea.breezeframework.dsl.DslElement as IDslElement
import com.windea.breezeframework.dsl.DslEntry as IDslEntry

@SequenceDiagramDslMarker
interface SequenceDiagramDsl {
	@SequenceDiagramDslMarker
	class DslDocument @PublishedApi internal constructor() : IDslDocument, DslEntry {
		var title: Title? = null
		override val participants: MutableSet<Participant> = mutableSetOf()
		override val messages: MutableList<Message> = mutableListOf()
		override val notes: MutableList<Note> = mutableListOf()

		override fun toString(): String {
			return arrayOf(title, toContentString()).joinToText("\n\n")
		}
	}

	@SequenceDiagramDslMarker
	interface DslEntry : IDslEntry, WithTransition<Participant, Message> {
		val participants: MutableSet<Participant>
		val messages: MutableList<Message>
		val notes: MutableList<Note>

		override fun toContentString(): String {
			return arrayOf(participants.joinToText("\n"), messages.joinToText("\n"), notes.joinToText("\n")).joinToText("\n\n")
		}

		override fun String.links(other: String) = message(this, other)
	}

	@SequenceDiagramDslMarker
	interface DslElement : IDslElement

	@SequenceDiagramDslMarker
	class Title @PublishedApi internal constructor(
		val text: String,
	) : DslElement {
		override fun toString(): String {
			val textSnippet = text.normalWrap()
			return "title: $textSnippet"
		}
	}

	@SequenceDiagramDslMarker
	class Participant @PublishedApi internal constructor(
		val name: String,
	) : DslElement, WithId {
		var alias: String? = null
		override val id: String get() = alias ?: name

		override fun equals(other: Any?) = equalsBy(this, other) { arrayOf(id) }

		override fun hashCode() = hashCodeBy(this) { arrayOf(id) }

		override fun toString(): String {
			val aliasSnippet = alias.toText { "as $it" }
			return "participant $name$aliasSnippet"
		}
	}

	@SequenceDiagramDslMarker
	class Message @PublishedApi internal constructor(
		val fromParticipantId: String, val toParticipantId: String,
	) : DslElement, WithNode {
		var text: String = ""
		var arrowShape: ArrowShape = ArrowShape.Arrow
		override val sourceNodeId: String get() = fromParticipantId
		override val targetNodeId: String get() = toParticipantId

		override fun toString(): String {
			val arrowShapeSnippet = arrowShape.text
			return "$fromParticipantId $arrowShapeSnippet $toParticipantId: $text"
		}
	}

	@SequenceDiagramDslMarker
	class Note @PublishedApi internal constructor(
		val location: NoteLocation, var text: String = "",
	) : DslElement {

		override fun toString(): String {
			val textSnippet = text.normalWrap()
			return "note $location: $textSnippet"
		}
	}

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

	@SequenceDiagramDslMarker
	enum class ArrowShape(val text: String) {
		Arrow("->"), DashedArrow("-->"), OpenArrow("->>"), DashedOpenArrow("-->>")
	}

	@SequenceDiagramDslMarker
	enum class NotePosition(val text: String) {
		LeftOf("left of"), RightOf("right of"), Over("over")
	}

	companion object {
		internal fun String.normalWrap() = this.replace("\n", "\\n").replace("\r", "\\r")
	}
}
