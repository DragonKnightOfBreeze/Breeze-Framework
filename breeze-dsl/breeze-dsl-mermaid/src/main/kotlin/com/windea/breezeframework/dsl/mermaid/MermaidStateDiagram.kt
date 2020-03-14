package com.windea.breezeframework.dsl.mermaid

import com.windea.breezeframework.core.constants.SystemProperties.lineSeparator
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.Mermaid.Companion.config
import com.windea.breezeframework.dsl.mermaid.MermaidStateDiagram.*

/**
 * Mermaid状态图的Dsl。
 * 参见：[Mermaid State Diagram](https://mermaidjs.github.io/#/stateDiagram)
 */
@DslMarker
@MustBeDocumented
annotation class MermaidStateDiagramDsl

/**
 * Mermaid状态图的入口。
 * 参见：[Mermaid State Diagram](https://mermaidjs.github.io/#/stateDiagram)
 * @property states 状态一览。忽略重复的元素。
 * @property links 连接一览。忽略重复的元素。
 * @property notes 注释一览。
 */
@MermaidStateDiagramDsl
interface MermaidStateDiagramEntry : MermaidEntry, CanSplitLine, WithTransition<State, Transition> {
	val states:MutableSet<State>
	val links:MutableList<Transition>
	val notes:MutableList<Note>

	override val contentString
		get() = buildString {
			if(states.isNotEmpty()) appendJoin(states, lineSeparator).append(splitSeparator)
			if(links.isNotEmpty()) appendJoin(links, lineSeparator).append(splitSeparator)
			if(notes.isNotEmpty()) appendJoin(notes, lineSeparator).append(splitSeparator)
		}.trimEnd()

	@DslFunction
	@MermaidStateDiagramDsl
	override fun String.links(other:String) = transition(this, other)

	@InlineDslFunction
	@MermaidStateDiagramDsl
	fun initState() = "[*]"

	@InlineDslFunction
	@MermaidStateDiagramDsl
	fun finishState() = "[*]"

	@InlineDslFunction
	@MermaidStateDiagramDsl
	@MermaidDslExtendedFeature
	fun anyState() = "<Any State>"

	@InlineDslFunction
	@MermaidStateDiagramDsl
	fun leftOf(stateName:String) = NoteLocation(NotePosition.LeftOf, stateName)

	@InlineDslFunction
	@MermaidStateDiagramDsl
	fun rightOf(stateName:String) = NoteLocation(NotePosition.RightOf, stateName)
}

/**
 * Mermaid状态图的元素。
 * 参见：[Mermaid State Diagram](https://mermaidjs.github.io/#/stateDiagram)
 */
@MermaidStateDiagramDsl
interface MermaidStateDiagramElement : MermaidElement

/**
 * Mermaid状态图。
 * 参见：[Mermaid State Diagram](https://mermaidjs.github.io/#/stateDiagram)
 */
@MermaidStateDiagramDsl
interface MermaidStateDiagram {
	/**Mermaid状态图的文档。*/
	class Document @PublishedApi internal constructor() : Mermaid.Document(), MermaidStateDiagramEntry, CanIndent {
		override val states:MutableSet<State> = mutableSetOf()
		override val links:MutableList<Transition> = mutableListOf()
		override val notes:MutableList<Note> = mutableListOf()
		override var indentContent:Boolean = true
		override var splitContent:Boolean = true

		override fun toString() = "stateDiagram$ls${contentString.doIndent(config.indent)}"
	}

	/**
	 * Mermaid状态图的状态。
	 * @property name 状态名。
	 * @property text （可选项）状态的文本。
	 */
	abstract class State(val name:String) : MermaidStateDiagramElement, WithId {
		var text:String? = null
		override val id:String get() = name

		override fun equals(other:Any?) = equalsByOne(this, other) { id }
		override fun hashCode() = hashCodeByOne(this) { id }
	}

	/**
	 * Mermaid状态图的简单状态。
	 * @property type （可选项）状态的类型。
	 */
	@MermaidStateDiagramDsl
	class SimpleState @PublishedApi internal constructor(name:String) : State(name) {
		var type:StateType? = null

		override fun toString() = "$name${text.typing { ": $it" }}${type?.text.typing { " <<$it>>" }}"
	}

	/**Mermaid状态图的复合状态。*/
	@MermaidStateDiagramDsl
	class CompositedState @PublishedApi internal constructor(name:String) : State(name), MermaidStateDiagramEntry, CanIndent {
		override val states:MutableSet<State> = mutableSetOf()
		override val links:MutableList<Transition> = mutableListOf()
		override val notes:MutableList<Note> = mutableListOf()
		override var indentContent:Boolean = true
		override var splitContent:Boolean = true

		override fun toString() = "state $name{$ls${contentString.doIndent(config.indent)}$ls}"
	}

	/**
	 * Mermaid状态图的并发状态。
	 * @property sections 并发状态的分块一览。
	 */
	@MermaidStateDiagramDsl
	class ConcurrentState @PublishedApi internal constructor(name:String) : State(name), CanIndent {
		val sections:MutableList<ConcurrentSection> = mutableListOf()
		override var indentContent:Boolean = true

		override fun toString() = "state $name{$ls${sections.typingAll("$ls---$ls").doIndent(config.indent)}$ls}"
	}

	/**Mermaid状态图的并发分块。*/
	@MermaidStateDiagramDsl
	class ConcurrentSection : MermaidStateDiagramEntry {
		override val states:MutableSet<State> = mutableSetOf()
		override val links:MutableList<Transition> = mutableListOf()
		override val notes:MutableList<Note> = mutableListOf()
		override var splitContent:Boolean = true

		override fun toString() = contentString
	}

	/**
	 * Mermaid状态图的转化。
	 * @property fromStateId 左状态的编号。
	 * @property toStateId 右状态的编号。
	 * @property text （可选项）转化的文本。
	 */
	class Transition @PublishedApi internal constructor(
		val fromStateId:String, val toStateId:String
	) : MermaidStateDiagramElement, WithNode {
		var text:String? = null
		override val sourceNodeId get() = fromStateId
		override val targetNodeId get() = toStateId

		override fun toString() = "$fromStateId --> $toStateId${text.typing { ": $it" }}"
	}

	/**
	 * Mermaid状态图的注释。
	 * @property location 注释的位置。
	 * @property text 注释的文本。可以使用`<br>`换行。
	 */
	@MermaidStateDiagramDsl
	class Note @PublishedApi internal constructor(
		val location:NoteLocation, var text:String
	) : MermaidStateDiagramElement, CanWrapLine, CanIndent {
		override var wrapContent:Boolean = false
		override var indentContent:Boolean = true

		override fun toString() = when {
			wrapContent -> "note $location$ls${text.doWrap().doIndent(config.indent)}${ls}end note"
			else -> "note $location: ${text.doWrap()}"
		}
	}

	/**Mermaid状态图状态的类型。*/
	@MermaidStateDiagramDsl
	enum class StateType(internal val text:String) {
		Fork("fork"), Join("join")
	}

	/**Mermaid状态图注释的位置。*/
	@MermaidStateDiagramDsl
	class NoteLocation @PublishedApi internal constructor(
		internal val position:NotePosition, internal val stateId:String
	) {
		override fun toString() = "${position.text} $stateId"
	}

	/**Mermaid状态图注释的方位。*/
	@MermaidStateDiagramDsl
	enum class NotePosition(internal val text:String) {
		LeftOf("left of"), RightOf("right of")
	}
}


@MermaidStateDiagramDsl
inline fun mermaidStateDiagram(block:Document.() -> Unit) = Document().apply(block)

@MermaidStateDiagramDsl
fun MermaidStateDiagramEntry.state(name:String) =
	SimpleState(name).also { states += it }

@MermaidStateDiagramDsl
inline fun MermaidStateDiagramEntry.compositedState(name:String, block:CompositedState.() -> Unit) =
	CompositedState(name).apply(block).also { states += it }

@MermaidStateDiagramDsl
inline fun MermaidStateDiagramEntry.concurrentState(name:String, block:ConcurrentState.() -> Unit) =
	ConcurrentState(name).apply(block).also { states += it }

@MermaidStateDiagramDsl
fun MermaidStateDiagramEntry.transition(fromStateId:String, toStateId:String) =
	Transition(fromStateId, toStateId).also { links += it }

@MermaidStateDiagramDsl
fun MermaidStateDiagramEntry.note(location:NoteLocation, text:String = "") =
	Note(location, text).also { notes += it }

@MermaidStateDiagramDsl
infix fun State.text(text:String) =
	apply { this.text = text }

@MermaidStateDiagramDsl
infix fun SimpleState.type(type:StateType) =
	apply { this.type = type }

@MermaidStateDiagramDsl
inline fun ConcurrentState.section(block:ConcurrentSection.() -> Unit) =
	ConcurrentSection().apply(block).also { sections += it }

@MermaidStateDiagramDsl
infix fun Transition.text(text:String) =
	apply { this.text = text }

@MermaidStateDiagramDsl
infix fun Note.text(text:String) =
	apply { this.text = text }
