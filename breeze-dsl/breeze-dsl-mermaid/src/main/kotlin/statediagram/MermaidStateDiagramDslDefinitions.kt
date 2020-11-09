// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl.mermaid.statediagram

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.DslConstants.ls
import com.windea.breezeframework.dsl.api.*
import com.windea.breezeframework.dsl.mermaid.*
import com.windea.breezeframework.dsl.mermaid.MermaidDslConfig.indent
import com.windea.breezeframework.dsl.mermaid.MermaidDslDefinitions.Companion.htmlWrap

/**
 * DslDocument definitions of [MermaidStateDiagramDsl].
 */
@MermaidStateDiagramDslMarker
interface MermaidStateDiagramDslDefinitions {
	/**
	 * Mermaid状态图领域特定语言的入口。
	 * @property states 状态一览。忽略重复的元素。
	 * @property links 连接一览。忽略重复的元素。
	 * @property notes 注释一览。
	 */
	@MermaidStateDiagramDslMarker
	interface IDslEntry : MermaidDslDefinitions.IDslEntry, Splitable, WithTransition<State, Transition> {
		val states: MutableSet<State>
		val links: MutableList<Transition>
		val notes: MutableList<Note>

		override fun toContentString(): String {
			return arrayOf(states.joinToText(ls), links.joinToText(ls), notes.joinToText(ls)).doSplit()
		}

		@MermaidStateDiagramDslMarker
		override fun String.links(other: String) = transition(this, other)
	}

	/**
	 * Mermaid状态图领域特定语言的元素。
	 */
	@MermaidStateDiagramDslMarker
	interface IDslElement : MermaidDslDefinitions.IDslElement

	/**
	 * Mermaid状态图的状态。
	 * @property name 状态名。
	 * @property text （可选项）状态的文本。
	 */
	abstract class State(val name: String) : IDslElement, WithId {
		var text: String? = null
		override val id: String get() = name

		override fun equals(other: Any?) = equalsBy(this, other) { arrayOf(id) }

		override fun hashCode() = hashCodeBy(this) { arrayOf(id) }
	}

	/**
	 * Mermaid状态图的简单状态。
	 * @property type （可选项）状态的类型。
	 */
	@MermaidStateDiagramDslMarker
	class SimpleState @PublishedApi internal constructor(name: String) : State(name) {
		var type: StateType? = null

		override fun toString(): String {
			val textSnippet = text.toText { ": $it" }
			val typeSnippet = type?.text.toText { " <<$it>>" }
			return "$name$textSnippet$typeSnippet"
		}
	}

	/**Mermaid状态图的复合状态。*/
	@MermaidStateDiagramDslMarker
	class CompositedState @PublishedApi internal constructor(name: String) : State(name), IDslEntry, Indentable {
		override val states: MutableSet<State> = mutableSetOf()
		override val links: MutableList<Transition> = mutableListOf()
		override val notes: MutableList<Note> = mutableListOf()
		override var indentContent: Boolean = true
		override var splitContent: Boolean = true

		override fun toString(): String {
			val contentSnippet = toContentString().doIndent(indent)
			return "state $name{\n$contentSnippet\n}"
		}
	}

	/**
	 * Mermaid状态图的并发状态。
	 * @property sections 并发状态的分块一览。
	 */
	@MermaidStateDiagramDslMarker
	class ConcurrentState @PublishedApi internal constructor(name: String) : State(name), Indentable {
		val sections: MutableList<ConcurrentSection> = mutableListOf()
		override var indentContent: Boolean = true

		override fun toString(): String {
			val sectionsSnippet = sections.joinToText("\n---\n").doIndent(indent)
			return "state $name{\n$sectionsSnippet\n}"
		}
	}

	/**Mermaid状态图的并发分块。*/
	@MermaidStateDiagramDslMarker
	class ConcurrentSection : IDslEntry {
		override val states: MutableSet<State> = mutableSetOf()
		override val links: MutableList<Transition> = mutableListOf()
		override val notes: MutableList<Note> = mutableListOf()
		override var splitContent: Boolean = true

		override fun toString(): String {
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
		val fromStateId: String, val toStateId: String,
	) : IDslElement, WithNode {
		var text: String? = null
		override val sourceNodeId get() = fromStateId
		override val targetNodeId get() = toStateId

		override fun toString(): String {
			val textSnippet = text.toText { ": $it" }
			return "$fromStateId --> $toStateId$textSnippet"
		}
	}

	/**
	 * Mermaid状态图的注释。
	 * @property location 注释的位置。
	 * @property text 注释的文本。可以使用`<br>`换行。
	 */
	@MermaidStateDiagramDslMarker
	class Note @PublishedApi internal constructor(
		val location: NoteLocation, var text: String,
	) : IDslElement, Wrappable, Indentable {
		override var wrapContent: Boolean = false
		override var indentContent: Boolean = true

		override fun toString(): String {
			return when {
				wrapContent -> "note $location\n${text.htmlWrap().doIndent(indent)}${ls}end note"
				else -> "note $location: ${text.htmlWrap()}"
			}
		}
	}

	/**Mermaid状态图注释的位置。*/
	@MermaidStateDiagramDslMarker
	class NoteLocation @PublishedApi internal constructor(
		internal val position: NotePosition, internal val stateId: String,
	) {
		override fun toString(): String {
			return "${position.text} $stateId"
		}
	}


	/**Mermaid状态图状态的类型。*/
	@MermaidStateDiagramDslMarker
	enum class StateType(internal val text: String) {
		Fork("fork"), Join("join")
	}

	/**Mermaid状态图注释的方位。*/
	@MermaidStateDiagramDslMarker
	enum class NotePosition(internal val text: String) {
		LeftOf("left of"), RightOf("right of")
	}
}

