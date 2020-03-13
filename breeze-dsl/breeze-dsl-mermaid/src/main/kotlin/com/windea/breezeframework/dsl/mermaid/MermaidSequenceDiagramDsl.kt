package com.windea.breezeframework.dsl.mermaid

import com.windea.breezeframework.core.constants.text.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.Mermaid.Companion.config
import com.windea.breezeframework.dsl.mermaid.MermaidSequenceDiagram.*
import org.intellij.lang.annotations.*

//can have a title by `title: text`, but it is not introduced in official api

/**
 * Mermaid序列图的Dsl。
 * 参见：[Mermaid Sequence Diagram](https://mermaidjs.github.io/#/sequenceDiagram)
 */
@DslMarker
@MustBeDocumented
annotation class MermaidSequenceDiagramDsl

/**
 * Mermaid序列图的入口。
 * 参见：[Mermaid Sequence Diagram](https://mermaidjs.github.io/#/sequenceDiagram)
 * @property participants 参与者一览。忽略重复的元素。
 * @property messages 消息一览。
 * @property notes 注释一览。
 * @property scopes 作用域一览。
 */
@MermaidSequenceDiagramDsl
interface MermaidSequenceDiagramEntry : MermaidEntry, CanSplitLine, WithTransition<Participant, Message> {
	val participants:MutableSet<Participant>
	val messages:MutableList<Message>
	val notes:MutableList<Note>
	val scopes:MutableList<Scope>

	override fun contentString() = buildString {
		if(participants.isNotEmpty()) appendJoin(participants, SystemProperties.lineSeparator).append(splitSeparator)
		if(messages.isNotEmpty()) appendJoin(messages, SystemProperties.lineSeparator).append(splitSeparator)
		if(notes.isNotEmpty()) appendJoin(notes, SystemProperties.lineSeparator).append(splitSeparator)
		if(scopes.isNotEmpty()) appendJoin(scopes, SystemProperties.lineSeparator).append(splitSeparator)
	}.trimEnd()

	@DslFunction
	@MermaidSequenceDiagramDsl
	override fun String.links(other:String) = message(this, other)

	@InlineDslFunction
	@MermaidSequenceDiagramDsl
	fun leftOf(participantId:String) = NoteLocation(NotePosition.LeftOf, participantId, null)

	@InlineDslFunction
	@MermaidSequenceDiagramDsl
	fun rightOf(participantId:String) = NoteLocation(NotePosition.RightOf, participantId, null)

	@InlineDslFunction
	@MermaidSequenceDiagramDsl
	fun over(participantId1:String, participantId2:String) = NoteLocation(NotePosition.Over, participantId1, participantId2)
}

/**
 * Mermaid序列图的元素。
 * 参见：[Mermaid Sequence Diagram](https://mermaidjs.github.io/#/sequenceDiagram)
 */
@MermaidSequenceDiagramDsl
interface MermaidSequenceDiagramElement : MermaidElement

/**
 * Mermaid序列图。
 * 参见：[Mermaid Sequence Diagram](https://mermaidjs.github.io/#/sequenceDiagram)
 */
@MermaidSequenceDiagramDsl
interface MermaidSequenceDiagram {
	class Document @PublishedApi internal constructor() : Mermaid.Document(), MermaidSequenceDiagramEntry, CanIndent {
		override val participants:MutableSet<Participant> = mutableSetOf()
		override val messages:MutableList<Message> = mutableListOf()
		override val notes:MutableList<Note> = mutableListOf()
		override val scopes:MutableList<Scope> = mutableListOf()
		override var indentContent:Boolean = true
		override var splitContent:Boolean = true

		override fun toString():String {
			val contentSnippet = contentString().doIndent(config.indent)
			return "sequenceDiagram\n$contentSnippet"
		}
	}

	/**
	 * Mermaid序列图的参与者。
	 * @property name 参与者的名字。
	 * @property alias （可选项）参与者的别名。
	 */
	@MermaidSequenceDiagramDsl
	class Participant @PublishedApi internal constructor(
		val name:String
	) : MermaidSequenceDiagramElement, WithId {
		var alias:String? = null
		override val id:String get() = alias ?: name

		override fun equals(other:Any?) = equalsByOne(this, other) { id }
		override fun hashCode() = hashCodeByOne(this) { id }
		override fun toString() = "participant ${alias.typing { "$it as " }}$name"
	}

	/**
	 * Mermaid序列图的消息。
	 * @property fromParticipantId 左参与者的编号。
	 * @property toParticipantId 右参与者的编号。
	 * @property text （可选项）消息的文本。
	 * @property arrowShape 箭头的形状。默认为单向箭头。
	 * @property isActivated 是否已激活。默认不设置。
	 */
	@MermaidSequenceDiagramDsl
	class Message @PublishedApi internal constructor(
		val fromParticipantId:String, val toParticipantId:String
	) : MermaidSequenceDiagramElement, WithNode {
		var text:String = ""
		var arrowShape:ArrowShape = ArrowShape.Arrow
		var isActivated:Boolean? = null
		override val sourceNodeId get() = fromParticipantId
		override val targetNodeId get() = toParticipantId

		override fun toString() = "$fromParticipantId ${arrowShape.text} ${isActivated.typing("+", "-")}$toParticipantId: $text"
	}

	/**
	 * Mermaid序列图的注释。
	 * @property location 注释的位置。
	 * @property text 注释的文本。可以使用`<br>`换行。
	 */
	@MermaidSequenceDiagramDsl
	class Note @PublishedApi internal constructor(
		val location:NoteLocation, var text:String = ""
	) : MermaidSequenceDiagramElement {
		override fun toString() = "note $location: ${text.doWrap()}"
	}

	/**
	 * Mermaid序列图的作用域。
	 * @property type 作用域的类型。
	 * @property text （可选）作用域的文本。
	 */
	@MermaidSequenceDiagramDsl
	abstract class Scope(
		val type:String,
		val text:String?
	) : MermaidSequenceDiagramElement, MermaidSequenceDiagramEntry, CanIndent {
		override val participants:MutableSet<Participant> = mutableSetOf()
		override val messages:MutableList<Message> = mutableListOf()
		override val notes:MutableList<Note> = mutableListOf()
		override val scopes:MutableList<Scope> = mutableListOf()
		override var indentContent:Boolean = true
		override var splitContent:Boolean = true

		override fun toString() = "$type${text.typing { " $it" }}\n${contentString().doIndent(config.indent)}\nend"
	}

	/**Mermaid序列图的循环作用域。*/
	@MermaidSequenceDiagramDsl
	class Loop @PublishedApi internal constructor(
		text:String
	) : Scope("loop", text)

	/**Mermaid序列图的可选作用域。*/
	@MermaidSequenceDiagramDsl
	class Optional @PublishedApi internal constructor(
		text:String
	) : Scope("opt", text)

	/**
	 * Mermaid序列图的替代作用域。
	 * @property elseScopes 其余作用域。
	 */
	@MermaidSequenceDiagramDsl
	class Alternative @PublishedApi internal constructor(
		text:String
	) : Scope("alt", text) {
		val elseScopes:MutableList<Else> = mutableListOf()

		override fun toString() = "${contentString().doIndent(config.indent)}${elseScopes.typingAll(ls, ls)}"
	}

	/**Mermaid序列图的其余作用域。*/
	@MermaidSequenceDiagramDsl
	class Else @PublishedApi internal constructor(
		text:String? = null
	) : Scope("else", text)

	/**Mermaid序列图的颜色高亮作用域。*/
	@MermaidSequenceDiagramDsl
	class Highlight @PublishedApi internal constructor(
		@Language(value = "Less", prefix = "@color:") color:String
	) : Scope("rect", color)

	/**Mermaid序列图消息的箭头形状。*/
	@MermaidSequenceDiagramDsl
	enum class ArrowShape(internal val text:String) {
		Arrow("->>"), DashedArrow("-->>"), Line("->"), DashedLine("-->"), Cross("-x"), DashedCross("--x")
	}

	/**Mermaid序列图注释的位置。*/
	@MermaidSequenceDiagramDsl
	class NoteLocation @PublishedApi internal constructor(
		internal val position:NotePosition, internal val participantId1:String, internal val participantId2:String?
	) {
		override fun toString() = "${position.text} $participantId1${participantId2.typing { ", $it" }}"
	}

	/**Mermaid序列图注释的方位。*/
	@MermaidSequenceDiagramDsl
	enum class NotePosition(internal val text:String) {
		RightOf("right of"), LeftOf("left of"), Over("over")
	}
}


@TopDslFunction
@MermaidSequenceDiagramDsl
fun mermaidSequenceDiagram(block:Document.() -> Unit) = Document().apply(block)

@DslFunction
@MermaidSequenceDiagramDsl
fun MermaidSequenceDiagramEntry.participant(name:String) =
	Participant(name).also { participants += it }

@DslFunction
@MermaidSequenceDiagramDsl
fun MermaidSequenceDiagramEntry.message(fromParticipantId:String, toParticipantId:String) =
	Message(fromParticipantId, toParticipantId).also { messages += it }

@DslFunction
@MermaidSequenceDiagramDsl
fun MermaidSequenceDiagramEntry.message(
	fromParticipantId:String,
	arrowShape:ArrowShape,
	isActivated:Boolean = false,
	toParticipantId:String
) = Message(fromParticipantId, toParticipantId)
	.apply { this.arrowShape = arrowShape }
	.apply { this.isActivated = isActivated }
	.also { messages += it }

@DslFunction
@MermaidSequenceDiagramDsl
fun MermaidSequenceDiagramEntry.note(location:NoteLocation, text:String) =
	Note(location, text).also { notes += it }

@DslFunction
@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramEntry.loop(text:String, block:Loop.() -> Unit) =
	Loop(text).apply(block).also { scopes += it }

@DslFunction
@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramEntry.opt(text:String, block:Optional.() -> Unit) =
	Optional(text).apply(block).also { scopes += it }

@DslFunction
@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramEntry.alt(text:String, block:Alternative.() -> Unit) =
	Alternative(text).apply(block).also { scopes += it }

@DslFunction
@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramEntry.highlight(text:String, block:Highlight.() -> Unit) =
	Highlight(text).apply(block).also { scopes += it }

@DslFunction
@MermaidSequenceDiagramDsl
infix fun Participant.alias(alias:String) =
	apply { this.alias = alias }

@DslFunction
@MermaidSequenceDiagramDsl
infix fun Message.text(text:String) =
	apply { this.text = text }

@DslFunction
@MermaidSequenceDiagramDsl
infix fun Message.arrowShape(arrowShape:ArrowShape) =
	apply { this.arrowShape = arrowShape }

@DslFunction
@MermaidSequenceDiagramDsl
infix fun Message.activate(isActivated:Boolean) =
	apply { this.isActivated = isActivated }

@MermaidStateDiagramDsl
infix fun Note.text(text:String) =
	apply { this.text = text }

@DslFunction
@MermaidSequenceDiagramDsl
fun Alternative.`else`(text:String) =
	Else(text).also { elseScopes += it }

@DslFunction
@MermaidSequenceDiagramDsl
fun Alternative.`else`() =
	Else().also { elseScopes += it }
