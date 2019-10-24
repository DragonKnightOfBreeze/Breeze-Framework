@file:Suppress("NOTHING_TO_INLINE", "DuplicatedCode", "unused")

package com.windea.breezeframework.dsl.graph.puml

import com.windea.breezeframework.core.annotations.api.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.graph.puml.PumlConfig.indent
import com.windea.breezeframework.dsl.graph.puml.PumlConfig.quote
import org.intellij.lang.annotations.*

//REGION top annotations and interfaces

/**PlantUml状态图的Dsl。*/
@ReferenceApi("[PlantUml State Diagram](http://plantuml.com/zh/state-diagram)")
@DslMarker
private annotation class PumlStateDiagramDsl

/**PlantUml状态图。*/
@PumlStateDiagramDsl
class PumlStateDiagram @PublishedApi internal constructor() : Puml(), PumlStateDiagramDslEntry {
	override val states: MutableSet<PumlStateDiagramState> = mutableSetOf()
	override val links: MutableList<PumlStateDiagramTransition> = mutableListOf()
	
	override var splitContent: Boolean = true
	
	override fun toString(): String {
		val contentSnippet = arrayOf(
			toPrefixString(),
			_toContentString(),
			toSuffixString()
		).filterNotEmpty().joinToStringOrEmpty(_splitWrap)
		return "@startuml\n$contentSnippet\n@enduml"
	}
}

//REGION dsl interfaces

/**PlantUml状态图Dsl的入口。*/
@PumlStateDiagramDsl
interface PumlStateDiagramDslEntry : PumlDslEntry, CanSplitContent, WithTransition<PumlStateDiagramState, PumlStateDiagramTransition> {
	val states: MutableSet<PumlStateDiagramState>
	val links: MutableList<PumlStateDiagramTransition>
	
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
@PumlStateDiagramDsl
interface PumlStateDiagramDslElement : PumlDslElement

//REGION dsl elements

/**Puml状态图状态。*/
@PumlStateDiagramDsl
sealed class PumlStateDiagramState(
	@Language("Creole")
	val name: String, //NOTE can wrap by "\n" if alias is not null
	@Language("Creole")
	val text: String? = null //NOTE can wrap by "\n"
) : PumlStateDiagramDslElement, WithName {
	var alias: String? = null
	var tag: String? = null
	var color: String? = null
	
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
@PumlStateDiagramDsl
class PumlStateDiagramSimpleState @PublishedApi internal constructor(
	name: String,
	text: String? = null
) : PumlStateDiagramState(name, text) {
	override fun toString(): String {
		val textSnippet = text?.replaceWithEscapedWrap().orEmpty()
		val tagSnippet = tag?.let { "<<$it>>" }.orEmpty()
		val colorSnippet = color?.let { " ${it.addPrefix("#")}" }.orEmpty()
		return if(alias == null) {
			"state $name$tagSnippet$colorSnippet: $textSnippet"
		} else {
			val nameSnippet = name.replaceWithEscapedWrap().wrapQuote(quote) //only escaped with an existing alias
			"state $nameSnippet as $alias$tagSnippet$colorSnippet: $textSnippet"
		}
	}
}

/**
 * Puml状态图复合状态。
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
@PumlStateDiagramDsl
class PumlStateDiagramCompositedState @PublishedApi internal constructor(
	name: String,
	text: String? = null
) : PumlStateDiagramState(name, text), PumlStateDiagramDslEntry, CanIndentContent {
	override val states: MutableSet<PumlStateDiagramState> = mutableSetOf()
	override val links: MutableList<PumlStateDiagramTransition> = mutableListOf()
	
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
 * Puml状态图并发状态。
 *
 * 语法示例：
 * ```
 * state A {
 *     A1 -> A2
 *     ---
 *     A2 -> A1
 * }
 */
@PumlStateDiagramDsl
class PumlStateDiagramConcurrentState @PublishedApi internal constructor(
	name: String,
	text: String? = null
) : PumlStateDiagramState(name, text), CanIndentContent {
	val states: MutableSet<PumlStateDiagramSimpleState> = mutableSetOf()
	val sections: MutableList<PumlStateDiagramConcurrentSection> = mutableListOf()
	
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

/**Puml状态图并发状态部分。*/
@PumlStateDiagramDsl
class PumlStateDiagramConcurrentSection : PumlStateDiagramDslEntry {
	override val states: MutableSet<PumlStateDiagramState> = mutableSetOf()
	override val links: MutableList<PumlStateDiagramTransition> = mutableListOf()
	
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
@PumlStateDiagramDsl
class PumlStateDiagramTransition @PublishedApi internal constructor(
	val sourceStateName: String,
	val targetStateName: String,
	@Language("Creole")
	var text: String? = null //NOTE can wrap by "\n"
) : PumlStateDiagramDslElement, WithNode<PumlStateDiagramState> {
	var arrowColor: String? = null
	var arrowStyle: PumlArrowStyle? = null
	var arrowDirection: PumlArrowDirection? = null
	var arrowLength: Int = 1
	
	override val _fromNodeName get() = sourceStateName
	override val _toNodeName get() = targetStateName
	
	override fun toString(): String {
		val textSnippet = text?.let { ": ${it.replaceWithEscapedWrap()}" }.orEmpty()
		val arrowParamsSnippet = arrayOf(arrowColor?.addPrefix("#"), arrowStyle?.text).filterNotNull()
			.joinToStringOrEmpty(",", "[", "]")
		val arrowDirectionSnippet = arrowDirection?.text.orEmpty()
		val arrowLengthSnippet = "-" * (arrowLength - 1)
		val arrowSnippet = "-$arrowParamsSnippet$arrowDirectionSnippet$arrowLengthSnippet>"
		return "$sourceStateName $arrowSnippet $targetStateName$textSnippet"
	}
}

//REGION build extensions

@PumlStateDiagramDsl
inline fun pumlStateDiagram(block: PumlStateDiagram.() -> Unit) = PumlStateDiagram().also { it.block() }

@PumlStateDiagramDsl
inline fun PumlStateDiagramDslEntry.state(name: String, text: String? = null) =
	PumlStateDiagramSimpleState(name, text).also { states += it }

@InlineDsl
@PumlStateDiagramDsl
inline val PumlStateDiagramDslEntry.initState
	get() = "[*]"

@InlineDsl
@PumlStateDiagramDsl
inline val PumlStateDiagramDslEntry.finishState
	get() = "[*]"

@PumlStateDiagramDsl
inline fun PumlStateDiagramDslEntry.compositedState(name: String, text: String? = null,
	block: PumlStateDiagramCompositedState.() -> Unit) =
	PumlStateDiagramCompositedState(name, text).also { it.block() }.also { states += it }

@PumlStateDiagramDsl
inline fun PumlStateDiagramDslEntry.concurrentState(name: String, text: String? = null,
	block: PumlStateDiagramConcurrentState.() -> Unit) =
	PumlStateDiagramConcurrentState(name, text).also { it.block() }.also { states += it }

@PumlStateDiagramDsl
inline fun PumlStateDiagramDslEntry.transition(sourceStateName: String, targetStateName: String, text: String? = null) =
	PumlStateDiagramTransition(sourceStateName, targetStateName, text).also { links += it }

@PumlStateDiagramDsl
inline fun PumlStateDiagramDslEntry.transition(sourceState: PumlStateDiagramSimpleState,
	targetState: PumlStateDiagramSimpleState, text: String? = null) =
	PumlStateDiagramTransition(sourceState.alias ?: sourceState.name, targetState.alias ?: targetState.name, text)
		.also { links += it }

@PumlStateDiagramDsl
inline fun PumlStateDiagramDslEntry.transition(
	sourceStateName: String,
	arrowColor: String?,
	arrowStyle: PumlArrowStyle?,
	arrowDirection: PumlArrowDirection?,
	targetStateName: String,
	text: String? = null
) = PumlStateDiagramTransition(sourceStateName, targetStateName, text)
	.also { it.arrowColor = arrowColor;it.arrowStyle = arrowStyle;it.arrowDirection = arrowDirection }
	.also { links += it }

@PumlStateDiagramDsl
inline infix fun PumlStateDiagramState.color(color: String) =
	this.also { it.color = color }

@PumlStateDiagramDsl
inline infix fun PumlStateDiagramState.tag(tag: String) =
	this.also { it.tag = tag }

@PumlStateDiagramDsl
inline infix fun PumlStateDiagramState.alias(alias: String) =
	this.also { it.alias = alias }

@PumlStateDiagramDsl
inline fun PumlStateDiagramConcurrentState.state(name: String, text: String? = null) =
	PumlStateDiagramSimpleState(name, text).also { states += it }

@PumlStateDiagramDsl
inline fun PumlStateDiagramConcurrentState.section(block: PumlStateDiagramConcurrentSection.() -> Unit) =
	PumlStateDiagramConcurrentSection().also { it.block() }.also { sections += it }

@PumlStateDiagramDsl
inline infix fun PumlStateDiagramTransition.text(text: String) =
	this.also { it.text = text }

@PumlStateDiagramDsl
inline infix fun PumlStateDiagramTransition.arrowColor(arrowColor: String) =
	this.also { it.arrowColor = arrowColor }

@PumlStateDiagramDsl
inline infix fun PumlStateDiagramTransition.arrowStyle(arrowShape: PumlArrowStyle) =
	this.also { it.arrowStyle = arrowShape }

@PumlStateDiagramDsl
inline infix fun PumlStateDiagramTransition.arrowDirection(arrowDirection: PumlArrowDirection) =
	this.also { it.arrowDirection = arrowDirection }

@PumlStateDiagramDsl
inline infix fun PumlStateDiagramTransition.arrowLength(arrowLength: Int) =
	this.also { it.arrowLength = arrowLength.coerceIn(1, 8) }
