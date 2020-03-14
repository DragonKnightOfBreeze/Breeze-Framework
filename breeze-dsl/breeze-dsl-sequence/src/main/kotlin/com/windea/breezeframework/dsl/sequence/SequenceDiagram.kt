package com.windea.breezeframework.dsl.sequence

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*

/**序列图。*/
@SequenceDiagramDsl
interface SequenceDiagram {
	/**序列图的文档。*/
	@SequenceDiagramDsl
	class Document @PublishedApi internal constructor() : DslDocument, SequenceDiagramDslEntry {
		var title:Title? = null
		override val participants:MutableSet<Participant> = mutableSetOf()
		override val messages:MutableList<Message> = mutableListOf()
		override val notes:MutableList<Note> = mutableListOf()
		override var splitContent:Boolean = true

		override fun toString():String {
			return listOfNotNull(
				title?.toString(),
				contentString().orNull()
			).joinToString(splitSeparator)
		}
	}

	/**
	 * 序列图的标题。
	 * @property text 标题的文本。可以使用`\\n`换行。
	 */
	@SequenceDiagramDsl
	class Title @PublishedApi internal constructor(
		val text:String
	) : SequenceDiagramDslElement {
		override fun toString():String {
			return "title: ${text.normalWrap()}"
		}
	}

	/**序列图的参与者。*/
	@SequenceDiagramDsl
	class Participant @PublishedApi internal constructor(
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

	/**序列图的消息。*/
	@SequenceDiagramDsl
	class Message @PublishedApi internal constructor(
		val fromParticipantId:String, val toParticipantId:String
	) : SequenceDiagramDslElement, WithNode {
		var text:String = ""
		var arrowShape:ArrowShape = ArrowShape.Arrow

		override val sourceNodeId:String get() = fromParticipantId
		override val targetNodeId:String get() = toParticipantId

		override fun toString():String {
			return "$fromParticipantId ${arrowShape.text} $toParticipantId: $text"
		}
	}

	/**
	 * 序列图的注释。
	 * @property location 注释的位置。
	 * @property text 注释的文本。可以使用`\\n`换行。
	 */
	@SequenceDiagramDsl
	class Note @PublishedApi internal constructor(
		val location:NoteLocation, var text:String = ""
	) : SequenceDiagramDslElement {

		override fun toString():String {
			val textSnippet = text.normalWrap()
			return "note $location: $textSnippet"
		}
	}

	/**序列图消息的箭头类型。*/
	@SequenceDiagramDsl
	enum class ArrowShape(
		internal val text:String
	) {
		Arrow("->"), DashedArrow("-->"), OpenArrow("->>"), DashedOpenArrow("-->>")
	}

	/**序列图注释的位置。*/
	@SequenceDiagramDsl
	class NoteLocation @PublishedApi internal constructor(
		val position:NotePosition, val participantId1:String, val participantId2:String? = null
	) {
		override fun toString():String {
			val participantId2Snippet = participantId2?.let { ", $it" }.orEmpty()
			return "${position.text} $participantId1$participantId2Snippet"
		}
	}

	/**序列图注释的方位。*/
	@SequenceDiagramDsl
	enum class NotePosition(
		internal val text:String
	) {
		LeftOf("left of"), RightOf("right of"), Over("over")
	}

	companion object {
		@PublishedApi
		internal fun String.normalWrap() = this.replace("\n", "\\n").replace("\r", "\\r")
	}
}
