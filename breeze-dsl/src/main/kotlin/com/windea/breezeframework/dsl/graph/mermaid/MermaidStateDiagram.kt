@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.dsl.graph.mermaid

import com.windea.breezeframework.core.annotations.api.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.graph.mermaid.MermaidConfig.indent
import com.windea.breezeframework.dsl.graph.mermaid.MermaidConfig.quote
import org.intellij.lang.annotations.*

//TODO

//NOTE unstable raw api

//REGION top annotations and interfaces

/**PlantUml状态图的Dsl。*/
@ReferenceApi("[Mermaid State Diagram](https://mermaidjs.github.io/#/stateDiagram)")
@DslMarker
private annotation class MermaidStateDiagramDsl

/**PlantUml状态图。*/
@MermaidStateDiagramDsl
class MermaidStateDiagram @PublishedApi internal constructor() : Mermaid(), MermaidStateDiagramDslEntry, CanIndentContent {
	override val states: MutableSet<MermaidStateDiagramState> = mutableSetOf()
	override val links: MutableList<MermaidStateDiagramTransition> = mutableListOf()
	
	override var indentContent: Boolean = true
	override var splitContent: Boolean = true
	
	override fun toString(): String {
		val contentSnippet = _toContentString()._applyIndent(indent)
		return "stateDiagram\n$contentSnippet"
	}
}

//REGION dsl interfaces

/**PlantUml状态图Dsl的入口。*/
@MermaidStateDiagramDsl
interface MermaidStateDiagramDslEntry : MermaidDslEntry, CanSplitContent, WithTransition<MermaidStateDiagramState, MermaidStateDiagramTransition> {
	val states: MutableSet<MermaidStateDiagramState>
	val links: MutableList<MermaidStateDiagramTransition>
	
	fun _toContentString(): String {
		return arrayOf(
			states.joinToStringOrEmpty("\n"),
			links.joinToStringOrEmpty("\n")
		).filterNotEmpty().joinToStringOrEmpty(_splitWrap)
	}
	
	@GenericDsl
	override fun String.fromTo(other: String) = transition(this, other)
}

/**PlantUml状态图Dsl的元素。*/
@MermaidStateDiagramDsl
interface MermaidStateDiagramDslElement : MermaidDslElement

//REGION dsl elements

/**Mermaid状态图状态。*/
@MermaidStateDiagramDsl
sealed class MermaidStateDiagramState(
	val name: String,
	val text: String? = null
) : MermaidStateDiagramDslElement, WithName {
	var alias: String? = null
	
	override val _name: String get() = alias ?: name
	
	override fun equals(other: Any?) = equalsBySelect(this, other) { arrayOf(alias ?: name) }
	
	override fun hashCode() = hashCodeBySelect(this) { arrayOf(alias ?: name) }
}

/**
 * PlantUml状态图简单状态。
 *
 * 语法示例：
 * ```
 * state A: text can\n wrap line.
 *
 * state A<<Tag>> #333: state with tag and color.
 *
 * state "Long name" as A: state with long name.
 * ```
 */
@MermaidStateDiagramDsl
class MermaidStateDiagramSimpleState @PublishedApi internal constructor(
	name: String,
	text: String? = null
) : MermaidStateDiagramState(name, text) {
	override fun toString(): String {
		val textSnippet = text?.replaceWithEscapedWrap().orEmpty()
		return if(alias == null) {
			"state $name: $textSnippet"
		} else {
			val nameSnippet = name.replaceWithEscapedWrap().wrapQuote(quote)
			"state $nameSnippet as $alias: $textSnippet"
		}
	}
}

/**
 * Mermaid状态图复合状态。
 *
 * 语法示例：
 * ```
 * state A {
 *     A1 -> A2
 * }
 *
 * state A { ... }
 * state A: text can\n wrap line.
 *
 * state A { ... }
 * state "Long name" as A: state with long name.
 */
@MermaidStateDiagramDsl
class MermaidStateDiagramCompositedState @PublishedApi internal constructor(
	name: String,
	text: String? = null
) : MermaidStateDiagramState(name, text), MermaidStateDiagramDslEntry, CanIndentContent {
	override val states: MutableSet<MermaidStateDiagramState> = mutableSetOf()
	override val links: MutableList<MermaidStateDiagramTransition> = mutableListOf()
	
	override var indentContent: Boolean = true
	override var splitContent: Boolean = true
	
	override fun toString(): String {
		val contentSnippet = _toContentString()
			.let { if(indentContent) it.prependIndent(indent) else it }
		val nameSnippet = alias ?: name
		val extraSnippet = alias?.let { "\nstate ${name.replaceWithEscapedWrap().wrapQuote(quote)} as $alias" }
			.orEmpty()
		val extraSnippetWithText = when {
			text == null -> extraSnippet
			extraSnippet.isEmpty() -> "\nstate $name: ${text.replaceWithEscapedWrap()}"
			else -> "$extraSnippet: ${text.replaceWithEscapedWrap()}"
		}
		return "state $nameSnippet {\n$contentSnippet\n}$extraSnippetWithText"
	}
}

/**
 * Mermaid状态图并发状态。
 *
 * 语法示例：
 * ```
 * state A {
 *     A1 -> A2
 *     ---
 *     A2 -> A1
 * }
 */
@MermaidStateDiagramDsl
class MermaidStateDiagramConcurrentState @PublishedApi internal constructor(
	name: String,
	text: String? = null
) : MermaidStateDiagramState(name, text), CanIndentContent {
	val states: MutableSet<MermaidStateDiagramSimpleState> = mutableSetOf()
	val sections: MutableList<MermaidStateDiagramConcurrentSection> = mutableListOf()
	
	override var indentContent: Boolean = true
	
	override fun toString(): String {
		val contentSnippet = arrayOf(
			states.joinToStringOrEmpty("\n"),
			sections.joinToStringOrEmpty("\n---\n")
		).filterNotEmpty().joinToStringOrEmpty("\n\n")
		val indentedContentSnippet = if(indentContent) contentSnippet.prependIndent(indent) else contentSnippet
		val nameSnippet = alias ?: name
		val extraSnippet = alias?.let { "\nstate ${name.replaceWithHtmlWrap().wrapQuote(quote)} as $alias" }.orEmpty()
		val extraSnippetWithText = when {
			text == null -> extraSnippet
			extraSnippet.isEmpty() -> "\nstate $name: ${text.replaceWithHtmlWrap()}"
			else -> "$extraSnippet: ${text.replaceWithHtmlWrap()}"
		}
		return "state $nameSnippet {\n$indentedContentSnippet\n}$extraSnippetWithText"
	}
}

/**Mermaid状态图并发状态部分。*/
@MermaidStateDiagramDsl
class MermaidStateDiagramConcurrentSection : MermaidStateDiagramDslEntry {
	override val states: MutableSet<MermaidStateDiagramState> = mutableSetOf()
	override val links: MutableList<MermaidStateDiagramTransition> = mutableListOf()
	
	override var splitContent: Boolean = true
	
	override fun toString() = _toContentString()
}

/**
 * PlantUml状态图转换。
 *
 * 语法示例：
 * * `A -> B`
 * * `A -> B: text can\n wrap line.`
 * * `A --> B: arrow can be longer.`
 * * `A -[#333,dotted]right-> B: arrow with color, shape and direction.`
 */
@MermaidStateDiagramDsl
class MermaidStateDiagramTransition @PublishedApi internal constructor(
	val sourceStateName: String,
	val targetStateName: String,
	@Language("Creole")
	var text: String? = null //NOTE can wrap by "\n"
) : MermaidStateDiagramDslElement, WithNode<MermaidStateDiagramState> {
	var arrowColor: String? = null
	var arrowShape: ArrowShape? = null
	
	var arrowDirection: MermaidArrowDirection? = null
	
	var arrowLength: Int = 1
	
	override val _fromNodeName get() = sourceStateName
	override val _toNodeName get() = targetStateName
	
	override fun toString(): String {
		val textSnippet = text?.let { ": ${it.replaceWithEscapedWrap()}" }.orEmpty()
		val arrowParamsSnippet = arrayOf(arrowColor?.addPrefix("#"), arrowShape?.text).filterNotNull()
			.joinToStringOrEmpty(",", "[", "]")
		val arrowDirectionSnippet = arrowDirection?.text.orEmpty()
		val arrowLengthSnippet = "-" * (arrowLength - 1)
		val arrowSnippet = "-$arrowParamsSnippet$arrowDirectionSnippet$arrowLengthSnippet>"
		return "$sourceStateName $arrowSnippet $targetStateName$textSnippet"
	}
	
	enum class ArrowShape(internal val text: String)
	
	enum class MermaidArrowDirection(internal val text: String)
}

//REGION build extensions

@MermaidStateDiagramDsl
inline fun pumlStateDiagram(block: MermaidStateDiagram.() -> Unit) = MermaidStateDiagram().also { it.block() }

@MermaidStateDiagramDsl
inline fun MermaidStateDiagramDslEntry.state(name: String, text: String? = null) =
	MermaidStateDiagramSimpleState(name, text).also { states += it }

@InlineDsl
@MermaidStateDiagramDsl
inline val MermaidStateDiagramDslEntry.initState
	get() = "[*]"

@InlineDsl
@MermaidStateDiagramDsl
inline val MermaidStateDiagramDslEntry.finishState
	get() = "[*]"

@MermaidStateDiagramDsl
inline fun MermaidStateDiagramDslEntry.compositedState(name: String, text: String? = null,
	block: MermaidStateDiagramCompositedState.() -> Unit) =
	MermaidStateDiagramCompositedState(name, text).also { it.block() }.also { states += it }

@MermaidStateDiagramDsl
inline fun MermaidStateDiagramDslEntry.concurrentState(name: String, text: String? = null,
	block: MermaidStateDiagramConcurrentState.() -> Unit) =
	MermaidStateDiagramConcurrentState(name, text).also { it.block() }.also { states += it }

@MermaidStateDiagramDsl
inline fun MermaidStateDiagramDslEntry.transition(sourceStateName: String, targetStateName: String,
	text: String? = null) =
	MermaidStateDiagramTransition(sourceStateName, targetStateName, text).also { links += it }

@MermaidStateDiagramDsl
inline fun MermaidStateDiagramDslEntry.transition(sourceState: MermaidStateDiagramSimpleState,
	targetState: MermaidStateDiagramSimpleState, text: String? = null) =
	MermaidStateDiagramTransition(sourceState.alias ?: sourceState.name, targetState.alias ?: targetState.name, text)
		.also { links += it }

@MermaidStateDiagramDsl
inline fun MermaidStateDiagramDslEntry.transition(
	sourceStateName: String,
	arrowColor: String?,
	arrowStyle: MermaidStateDiagramTransition.ArrowShape?,
	arrowDirection: MermaidStateDiagramTransition.MermaidArrowDirection?,
	targetStateName: String,
	text: String? = null
) = MermaidStateDiagramTransition(sourceStateName, targetStateName, text)
	.also { it.arrowColor = arrowColor;it.arrowShape = arrowStyle;it.arrowDirection = arrowDirection }
	.also { links += it }


@MermaidStateDiagramDsl
inline infix fun MermaidStateDiagramState.alias(alias: String) =
	this.also { it.alias = alias }

@MermaidStateDiagramDsl
inline fun MermaidStateDiagramConcurrentState.state(name: String, text: String? = null) =
	MermaidStateDiagramSimpleState(name, text).also { states += it }

@MermaidStateDiagramDsl
inline fun MermaidStateDiagramConcurrentState.section(block: MermaidStateDiagramConcurrentSection.() -> Unit) =
	MermaidStateDiagramConcurrentSection().also { it.block() }.also { sections += it }

@MermaidStateDiagramDsl
inline infix fun MermaidStateDiagramTransition.text(text: String) =
	this.also { it.text = text }

@MermaidStateDiagramDsl
inline infix fun MermaidStateDiagramTransition.arrowColor(arrowColor: String) =
	this.also { it.arrowColor = arrowColor }

@MermaidStateDiagramDsl
inline infix fun MermaidStateDiagramTransition.arrowShape(arrowShape: MermaidStateDiagramTransition.ArrowShape) =
	this.also { it.arrowShape = arrowShape }
