package com.windea.breezeframework.dsl.mermaid.sequencediagram

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.*
import com.windea.breezeframework.dsl.mermaid.Mermaid.Companion.htmlWrap

/**
 * Mermaid序列图。
 */
@MermaidSequenceDiagramDsl
interface MermaidSequenceDiagram {
	/**Mermaid序列图的文档。*/
	@MermaidSequenceDiagramDsl
	class Document @PublishedApi internal constructor() : Mermaid.Document(), MermaidSequenceDiagramDslEntry, CanIndent {
		override val participants:MutableSet<Participant> = mutableSetOf()
		override val messages:MutableList<Message> = mutableListOf()
		override val notes:MutableList<Note> = mutableListOf()
		override val scopes:MutableList<Scope> = mutableListOf()
		override var indentContent:Boolean = true
		override var splitContent:Boolean = true

		override fun toString():String {
			val contentSnippet = contentString().doIndent(Mermaid.config.indent)
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
	) : MermaidSequenceDiagramDslElement, WithId {
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
	) : MermaidSequenceDiagramDslElement, WithNode {
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
	) : MermaidSequenceDiagramDslElement {
		override fun toString() = "note $location: ${text.htmlWrap()}"
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
	) : MermaidSequenceDiagramDslElement, MermaidSequenceDiagramDslEntry, CanIndent {
		override val participants:MutableSet<Participant> = mutableSetOf()
		override val messages:MutableList<Message> = mutableListOf()
		override val notes:MutableList<Note> = mutableListOf()
		override val scopes:MutableList<Scope> = mutableListOf()
		override var indentContent:Boolean = true
		override var splitContent:Boolean = true

		override fun toString() = "$type${text.typing { " $it" }}\n${contentString().doIndent(Mermaid.config.indent)}\nend"
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

		override fun toString() = "${contentString().doIndent(Mermaid.config.indent)}${elseScopes.typingAll(Mermaid.ls, Mermaid.ls)}"
	}

	/**Mermaid序列图的其余作用域。*/
	@MermaidSequenceDiagramDsl
	class Else @PublishedApi internal constructor(
		text:String? = null
	) : Scope("else", text)

	/**Mermaid序列图的颜色高亮作用域。*/
	@MermaidSequenceDiagramDsl
	class Highlight @PublishedApi internal constructor(
		color:String
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
