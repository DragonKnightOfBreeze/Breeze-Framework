package com.windea.breezeframework.dsl.sequence

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.DslConstants.ls

/**序列图。*/
@SequenceDiagramDsl
interface SequenceDiagram {
	/**序列图的文档。*/
	@SequenceDiagramDsl
	class Document @PublishedApi internal constructor() : DslDocument, IDslEntry {
		var title:Title? = null
		override val participants:MutableSet<Participant> = mutableSetOf()
		override val messages:MutableList<Message> = mutableListOf()
		override val notes:MutableList<Note> = mutableListOf()
		override var splitContent:Boolean = true

		override fun toString():String {
			return arrayOf(title, toContentString()).doSplit()
		}
	}


	/**序列图领域特定语言的入口。*/
	@SequenceDiagramDsl
	interface IDslEntry : DslEntry, Splitable, WithTransition<Participant, Message> {
		val participants:MutableSet<Participant>
		val messages:MutableList<Message>
		val notes:MutableList<Note>

		override fun toContentString():String {
			return arrayOf(participants.typingAll(ls), messages.typingAll(ls), notes.typingAll(ls)).doSplit()
		}

		override fun String.links(other:String) = message(this, other)
	}

	/**序列图领域特定语言的元素。*/
	@SequenceDiagramDsl
	interface IDslElement : DslElement

	/**
	 * 序列图的标题。
	 * @property text 标题的文本。可以使用`\\n`换行。
	 */
	@SequenceDiagramDsl
	class Title @PublishedApi internal constructor(
		val text:String
	) : IDslElement {
		override fun toString():String {
			val textSnippet = text.normalWrap()
			return "title: $textSnippet"
		}
	}

	/**序列图的参与者。*/
	@SequenceDiagramDsl
	class Participant @PublishedApi internal constructor(
		val name:String
	) : IDslElement, WithId {
		var alias:String? = null
		override val id:String get() = alias ?: name

		override fun equals(other:Any?) = equalsBy(this, other) { arrayOf(id) }

		override fun hashCode() = hashCodeBy(this) { arrayOf(id) }

		override fun toString():String {
			val aliasSnippet = alias.typing { "as $it" }
			return "participant $name$aliasSnippet"
		}
	}

	/**序列图的消息。*/
	@SequenceDiagramDsl
	class Message @PublishedApi internal constructor(
		val fromParticipantId:String, val toParticipantId:String
	) : IDslElement, WithNode {
		var text:String = ""
		var arrowShape:ArrowShape = ArrowShape.Arrow
		override val sourceNodeId:String get() = fromParticipantId
		override val targetNodeId:String get() = toParticipantId

		override fun toString():String {
			val arrowShapeSnippet = arrowShape.text
			return "$fromParticipantId $arrowShapeSnippet $toParticipantId: $text"
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
	) : IDslElement {

		override fun toString():String {
			val textSnippet = text.normalWrap()
			return "note $location: $textSnippet"
		}
	}

	/**序列图注释的位置。*/
	@SequenceDiagramDsl
	class NoteLocation @PublishedApi internal constructor(
		val position:NotePosition, val participantId1:String, val participantId2:String? = null
	) {
		override fun toString():String {
			val participantId2Snippet = participantId2?.let { ", $it" }.orEmpty()
			val positionSnippet = position.text
			return "$positionSnippet $participantId1$participantId2Snippet"
		}
	}


	/**序列图消息的箭头类型。*/
	@SequenceDiagramDsl
	enum class ArrowShape(internal val text:String) {
		Arrow("->"), DashedArrow("-->"), OpenArrow("->>"), DashedOpenArrow("-->>")
	}

	/**序列图注释的方位。*/
	@SequenceDiagramDsl
	enum class NotePosition(internal val text:String) {
		LeftOf("left of"), RightOf("right of"), Over("over")
	}


	companion object {
		internal fun String.normalWrap() = this.replace("\n", "\\n").replace("\r", "\\r")
	}
}
