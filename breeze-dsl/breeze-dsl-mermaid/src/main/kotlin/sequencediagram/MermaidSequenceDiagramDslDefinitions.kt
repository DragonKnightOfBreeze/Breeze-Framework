package com.windea.breezeframework.dsl.mermaid.sequencediagram

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.DslConstants.ls
import com.windea.breezeframework.dsl.mermaid.*
import com.windea.breezeframework.dsl.mermaid.MermaidDslDefinitions.Companion.htmlWrap

/**
 * Dsl definitions of [MermaidSequenceDiagramDsl].
 */
@MermaidSequenceDiagramDslMarker
interface MermaidSequenceDiagramDslDefinitions {
	/**
	 * Mermaid序列图领域特定语言的入口。
	 * @property participants 参与者一览。忽略重复的元素。
	 * @property messages 消息一览。
	 * @property notes 注释一览。
	 * @property scopes 作用域一览。
	 */
	@MermaidSequenceDiagramDslMarker
	interface IDslEntry : MermaidDslDefinitions.IDslEntry, Splitable, WithTransition<Participant, Message> {
		val participants: MutableSet<Participant>
		val messages: MutableList<Message>
		val notes: MutableList<Note>
		val scopes: MutableList<Scope>

		override fun toContentString(): String {
			return arrayOf(participants.typingAll(ls), messages.typingAll(ls), notes.typingAll(ls), scopes.typingAll(ls))
				.doSplit()
		}

		@MermaidSequenceDiagramDslMarker
		override fun String.links(other: String) = message(this, other)
	}

	/**
	 * Mermaid序列图领域特定语言的元素。
	 */
	@MermaidSequenceDiagramDslMarker
	interface IDslElement : MermaidDslDefinitions.IDslElement

	/**
	 * Mermaid序列图的参与者。
	 * @property name 参与者的名字。
	 * @property alias （可选项）参与者的别名。
	 */
	@MermaidSequenceDiagramDslMarker
	class Participant @PublishedApi internal constructor(
		val name: String
	) : IDslElement, WithId {
		var alias: String? = null
		override val id: String get() = alias ?: name

		override fun equals(other: Any?) = equalsBy(this, other) { arrayOf(id) }

		override fun hashCode() = hashCodeBy(this) { arrayOf(id) }

		override fun toString(): String {
			val aliasSnippet = alias.typing { "$it as " }
			return "participant $aliasSnippet$name"
		}
	}

	/**
	 * Mermaid序列图的消息。
	 * @property fromParticipantId 左参与者的编号。
	 * @property toParticipantId 右参与者的编号。
	 * @property text （可选项）消息的文本。
	 * @property arrowShape 箭头的形状。默认为单向箭头。
	 * @property isActivated 是否已激活。默认不设置。
	 */
	@MermaidSequenceDiagramDslMarker
	class Message @PublishedApi internal constructor(
		val fromParticipantId: String, val toParticipantId: String
	) : IDslElement, WithNode {
		var text: String = ""
		var arrowShape: ArrowShape = ArrowShape.Arrow
		var isActivated: Boolean? = null
		override val sourceNodeId get() = fromParticipantId
		override val targetNodeId get() = toParticipantId

		override fun toString(): String {
			val arrowShapeSnippet = arrowShape.text
			val activatedSnippet = if(isActivated == true) "+" else "-"
			return "$fromParticipantId $arrowShapeSnippet $activatedSnippet$toParticipantId: $text"
		}
	}

	/**
	 * Mermaid序列图的注释。
	 * @property location 注释的位置。
	 * @property text 注释的文本。可以使用`<br>`换行。
	 */
	@MermaidSequenceDiagramDslMarker
	class Note @PublishedApi internal constructor(
		val location: NoteLocation, var text: String = ""
	) : IDslElement {
		override fun toString(): String {
			val textSnippet = text.htmlWrap()
			return "note $location: $textSnippet"
		}
	}

	/**Mermaid序列图注释的位置。*/
	@MermaidSequenceDiagramDslMarker
	class NoteLocation @PublishedApi internal constructor(
		internal val position: NotePosition, internal val participantId1: String, internal val participantId2: String?
	) {
		override fun toString(): String {
			val positionSnippet = position.text
			val participantId2Snippet = participantId2.typing { ", $it" }
			return "$positionSnippet $participantId1$participantId2Snippet"
		}
	}

	/**
	 * Mermaid序列图的作用域。
	 * @property type 作用域的类型。
	 * @property text （可选）作用域的文本。
	 */
	@MermaidSequenceDiagramDslMarker
	abstract class Scope(
		val type: String,
		val text: String?
	) : IDslElement, IDslEntry, Indentable {
		override val participants: MutableSet<Participant> = mutableSetOf()
		override val messages: MutableList<Message> = mutableListOf()
		override val notes: MutableList<Note> = mutableListOf()
		override val scopes: MutableList<Scope> = mutableListOf()
		override var indentContent: Boolean = true
		override var splitContent: Boolean = true

		override fun toString(): String {
			val contentSnippet = toContentString().doIndent(MermaidDslConfig.indent)
			return "$type $text$ls$contentSnippet${ls}end"
		}
	}

	/**Mermaid序列图的循环作用域。*/
	@MermaidSequenceDiagramDslMarker
	class Loop @PublishedApi internal constructor(
		text: String
	) : Scope("loop", text)

	/**Mermaid序列图的可选作用域。*/
	@MermaidSequenceDiagramDslMarker
	class Optional @PublishedApi internal constructor(
		text: String
	) : Scope("opt", text)

	/**
	 * Mermaid序列图的替代作用域。
	 * @property elseScopes 其余作用域。
	 */
	@MermaidSequenceDiagramDslMarker
	class Alternative @PublishedApi internal constructor(
		text: String
	) : Scope("alt", text) {
		val elseScopes: MutableList<Else> = mutableListOf()

		override fun toString(): String {
			val contentSnippet = toContentString().doIndent(MermaidDslConfig.indent)
			val elseScopesSnippet = elseScopes.typingAll(ls, ls)
			return "$contentSnippet$elseScopesSnippet"
		}
	}

	/**Mermaid序列图的其余作用域。*/
	@MermaidSequenceDiagramDslMarker
	class Else @PublishedApi internal constructor(
		text: String? = null
	) : Scope("else", text)

	/**Mermaid序列图的颜色高亮作用域。*/
	@MermaidSequenceDiagramDslMarker
	class Highlight @PublishedApi internal constructor(
		color: String
	) : Scope("rect", color)


	/**Mermaid序列图消息的箭头形状。*/
	@MermaidSequenceDiagramDslMarker
	enum class ArrowShape(internal val text: String) {
		Arrow("->>"), DashedArrow("-->>"), Line("->"), DashedLine("-->"), Cross("-x"), DashedCross("--x")
	}

	/**Mermaid序列图注释的方位。*/
	@MermaidSequenceDiagramDslMarker
	enum class NotePosition(internal val text: String) {
		RightOf("right of"), LeftOf("left of"), Over("over")
	}
}
