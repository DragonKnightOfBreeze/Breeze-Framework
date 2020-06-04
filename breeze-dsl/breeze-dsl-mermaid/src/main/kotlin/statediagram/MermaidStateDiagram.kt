package com.windea.breezeframework.dsl.mermaid.statediagram

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.DslConstants.ls
import com.windea.breezeframework.dsl.mermaid.*
import com.windea.breezeframework.dsl.mermaid.Mermaid.Companion.config
import com.windea.breezeframework.dsl.mermaid.Mermaid.Companion.htmlWrap

/**
 * Mermaid状态图。
 */
@MermaidStateDiagramDsl
interface MermaidStateDiagram {
	/**Mermaid状态图的文档。*/
	@MermaidStateDiagramDsl
	class Document @PublishedApi internal constructor() : Mermaid.Document(), MermaidStateDiagramDslEntry, Indentable {
		override val states:MutableSet<State> = mutableSetOf()
		override val links:MutableList<Transition> = mutableListOf()
		override val notes:MutableList<Note> = mutableListOf()
		override var indentContent:Boolean = true
		override var splitContent:Boolean = true

		override fun toString():String {
			val contentSnippet = toContentString().doIndent(config.indent)
			return "stateDiagram$ls$contentSnippet"
		}
	}

	/**
	 * Mermaid状态图领域特定语言的入口。
	 * @property states 状态一览。忽略重复的元素。
	 * @property links 连接一览。忽略重复的元素。
	 * @property notes 注释一览。
	 */
	@MermaidStateDiagramDsl
	interface MermaidStateDiagramDslEntry : Mermaid.IDslEntry, Splitable, WithTransition<State, Transition> {
		val states:MutableSet<State>
		val links:MutableList<Transition>
		val notes:MutableList<Note>

		override fun toContentString():String {
			return arrayOf(states.typingAll(ls), links.typingAll(ls), notes.typingAll(ls)).doSplit()
		}

		@DslFunction
		@MermaidStateDiagramDsl
		override fun String.links(other:String) = transition(this, other)
	}

	/**
	 * Mermaid状态图领域特定语言的元素。
	 */
	@MermaidStateDiagramDsl
	interface MermaidStateDiagramDslElement : Mermaid.IDslElement

	/**
	 * Mermaid状态图的状态。
	 * @property name 状态名。
	 * @property text （可选项）状态的文本。
	 */
	abstract class State(val name:String) : MermaidStateDiagramDslElement, WithId {
		var text:String? = null
		override val id:String get() = name

		override fun equals(other:Any?) = equalsBy(this, other) { arrayOf(id) }

		override fun hashCode() = hashCodeBy(this) { arrayOf(id) }
	}

	/**
	 * Mermaid状态图的简单状态。
	 * @property type （可选项）状态的类型。
	 */
	@MermaidStateDiagramDsl
	class SimpleState @PublishedApi internal constructor(name:String) : State(name) {
		var type:StateType? = null

		override fun toString():String {
			val textSnippet = text.typing { ": $it" }
			val typeSnippet = type?.text.typing { " <<$it>>" }
			return "$name$textSnippet$typeSnippet"
		}
	}

	/**Mermaid状态图的复合状态。*/
	@MermaidStateDiagramDsl
	class CompositedState @PublishedApi internal constructor(name:String) : State(name), MermaidStateDiagramDslEntry, Indentable {
		override val states:MutableSet<State> = mutableSetOf()
		override val links:MutableList<Transition> = mutableListOf()
		override val notes:MutableList<Note> = mutableListOf()
		override var indentContent:Boolean = true
		override var splitContent:Boolean = true

		override fun toString():String {
			val contentSnippet = toContentString().doIndent(config.indent)
			return "state $name{$ls$contentSnippet$ls}"
		}
	}

	/**
	 * Mermaid状态图的并发状态。
	 * @property sections 并发状态的分块一览。
	 */
	@MermaidStateDiagramDsl
	class ConcurrentState @PublishedApi internal constructor(name:String) : State(name), Indentable {
		val sections:MutableList<ConcurrentSection> = mutableListOf()
		override var indentContent:Boolean = true

		override fun toString():String {
			val sectionsSnippet = sections.typingAll("$ls---$ls").doIndent(config.indent)
			return "state $name{$ls$sectionsSnippet$ls}"
		}
	}

	/**Mermaid状态图的并发分块。*/
	@MermaidStateDiagramDsl
	class ConcurrentSection : MermaidStateDiagramDslEntry {
		override val states:MutableSet<State> = mutableSetOf()
		override val links:MutableList<Transition> = mutableListOf()
		override val notes:MutableList<Note> = mutableListOf()
		override var splitContent:Boolean = true

		override fun toString():String {
			return toContentString()
		}
	}

	/**
	 * Mermaid状态图的转化。
	 * @property fromStateId 左状态的编号。
	 * @property toStateId 右状态的编号。
	 * @property text （可选项）转化的文本。
	 */
	class Transition @PublishedApi internal constructor(
		val fromStateId:String, val toStateId:String
	) : MermaidStateDiagramDslElement, WithNode {
		var text:String? = null
		override val sourceNodeId get() = fromStateId
		override val targetNodeId get() = toStateId

		override fun toString():String {
			val textSnippet = text.typing { ": $it" }
			return "$fromStateId --> $toStateId$textSnippet"
		}
	}

	/**
	 * Mermaid状态图的注释。
	 * @property location 注释的位置。
	 * @property text 注释的文本。可以使用`<br>`换行。
	 */
	@MermaidStateDiagramDsl
	class Note @PublishedApi internal constructor(
		val location:NoteLocation, var text:String
	) : MermaidStateDiagramDslElement, Wrappable, Indentable {
		override var wrapContent:Boolean = false
		override var indentContent:Boolean = true

		override fun toString():String {
			return when {
				wrapContent -> "note $location$ls${text.htmlWrap().doIndent(config.indent)}${ls}end note"
				else -> "note $location: ${text.htmlWrap()}"
			}
		}
	}

	/**Mermaid状态图注释的位置。*/
	@MermaidStateDiagramDsl
	class NoteLocation @PublishedApi internal constructor(
		internal val position:NotePosition, internal val stateId:String
	) {
		override fun toString():String {
			return "${position.text} $stateId"
		}
	}


	/**Mermaid状态图状态的类型。*/
	@MermaidStateDiagramDsl
	enum class StateType(internal val text:String) {
		Fork("fork"), Join("join")
	}

	/**Mermaid状态图注释的方位。*/
	@MermaidStateDiagramDsl
	enum class NotePosition(internal val text:String) {
		LeftOf("left of"), RightOf("right of")
	}
}

