@file:Suppress("NOTHING_TO_INLINE", "DuplicatedCode")

package com.windea.breezeframework.dsl.puml

import com.windea.breezeframework.core.annotations.api.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.puml.PumlConfig.indent
import com.windea.breezeframework.dsl.puml.PumlConfig.quote
import org.intellij.lang.annotations.*

//REGION top annotations and interfaces

@DslMarker
private annotation class PumlStateDiagramDsl

/**PlantUml状态图。*/
@ReferenceApi("[PlantUml State Diagram](http://plantuml.com/zh/state-diagram)")
@PumlStateDiagramDsl
class PumlStateDiagram @PublishedApi internal constructor() : Puml(), PumlStateDiagramDslEntry {
	override val states: MutableSet<PumlStateDiagramState> = mutableSetOf()
	override val links: MutableList<PumlStateDiagramLink> = mutableListOf()
	
	override fun toString(): String {
		val prefixSnippet = toPrefixString()
		val contentSnippet = toContentString()
		val suffixSnippet = toSuffixString()
		return "@startuml\n$prefixSnippet\n\n$contentSnippet\n\n$suffixSnippet\n@enduml"
	}
}

//REGION dsl interfaces

/**PlantUml状态图Dsl的入口。*/
@PumlStateDiagramDsl
interface PumlStateDiagramDslEntry : PumlDslEntry {
	val states: MutableSet<PumlStateDiagramState>
	val links: MutableList<PumlStateDiagramLink>
	
	fun toContentString(): String {
		return arrayOf(
			states.joinToStringOrEmpty("\n"),
			links.joinToStringOrEmpty("\n")
		).filterNotEmpty().joinToStringOrEmpty("\n\n")
	}
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
	val text: String //NOTE can wrap by "\n"
) : PumlStateDiagramDslElement {
	var alias: String? = null
	var tag: String? = null
	var color: String? = null
	
	override fun equals(other: Any?): Boolean {
		return this === other || (other is PumlStateDiagramSimpleState && (other.alias == alias || other.name == name))
	}
	
	override fun hashCode(): Int {
		return alias?.hashCode() ?: name.hashCode()
	}
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
	name: String, text: String = ""
) : PumlStateDiagramState(name, text) {
	override fun toString(): String {
		val textSnippet = text.replaceWithEscapedWrap()
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
	name: String, text: String = ""
) : PumlStateDiagramState(name, text), PumlStateDiagramDslEntry, IndentContent {
	override val states: MutableSet<PumlStateDiagramState> = mutableSetOf()
	override val links: MutableList<PumlStateDiagramLink> = mutableListOf()
	
	override var indentContent: Boolean = true
	
	override fun toString(): String {
		val contentSnippet = toContentString()
			.let { if(indentContent) it.prependIndent(indent) else it }
		val nameSnippet = alias ?: name
		val extraSnippet = if(alias == null) "" else "\nstate ${name.replaceWithEscapedWrap().wrapQuote(quote)} as $alias"
		val extraSnippetWithText = when {
			text.isEmpty() -> extraSnippet
			text.isNotEmpty() && extraSnippet.isEmpty() -> "\nstate $name: ${text.replaceWithEscapedWrap()}"
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
	name: String, text: String = ""
) : PumlStateDiagramState(name, text), IndentContent {
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
		val extraSnippet = if(alias == null) "" else "\nstate ${name.replaceWithHtmlWrap().wrapQuote(quote)} as $alias"
		val extraSnippetWithText = when {
			text.isEmpty() -> extraSnippet
			text.isNotEmpty() && extraSnippet.isEmpty() -> "\nstate $name: ${text.replaceWithHtmlWrap()}"
			else -> "$extraSnippet: ${text.replaceWithHtmlWrap()}"
		}
		return "state $nameSnippet {\n$indentedContentSnippet\n}$extraSnippetWithText"
	}
}

/**Puml状态图并发状态部分。*/
@PumlStateDiagramDsl
class PumlStateDiagramConcurrentSection : PumlStateDiagramDslEntry {
	override val states: MutableSet<PumlStateDiagramState> = mutableSetOf()
	override val links: MutableList<PumlStateDiagramLink> = mutableListOf()
	
	override fun toString() = toContentString()
}

/**
 * PlantUml状态图连接。
 *
 * 语法示例：
 * * `A -> B`
 * * `A -> B: text can\n wrap line.`
 * * `A --> B: arrow can be longer.`
 * * `A -[#333,dotted]right-> B: arrow with color, shape and direction.`
 */
@PumlStateDiagramDsl
class PumlStateDiagramLink @PublishedApi internal constructor(
	val sourceStateId: String,
	val targetStateId: String,
	@Language("Creole")
	val text: String = "" //NOTE can wrap by "\n"
) : PumlStateDiagramDslElement {
	var arrowColor: String? = null
	var arrowStyle: PumlArrowStyle? = null
	var arrowDirection: PumlArrowDirection? = null
	var arrowLength: Int = 1
	
	override fun toString(): String {
		val textSnippet = if(text.isEmpty()) "" else ": ${text.replaceWithEscapedWrap()}"
		val arrowParamsSnippet = arrayOf(arrowColor?.addPrefix("#"), arrowStyle?.text).filterNotNull()
			.joinToStringOrEmpty(",", "[", "]")
		val arrowDirectionSnippet = arrowDirection?.text.orEmpty()
		val arrowLengthSnippet = "-" * (arrowLength - 1)
		val arrowSnippet = "-$arrowParamsSnippet$arrowDirectionSnippet$arrowLengthSnippet>"
		return "$sourceStateId $arrowSnippet $targetStateId$textSnippet"
	}
}

//REGION build extensions

@PumlStateDiagramDsl
inline fun pumlStateDiagram(builder: PumlStateDiagram.() -> Unit) = PumlStateDiagram().also { it.builder() }

@PumlStateDiagramDsl
inline fun PumlStateDiagramDslEntry.state(name: String, text: String = "") =
	PumlStateDiagramSimpleState(name, text).also { states += it }

@InlineDsl
@PumlStateDiagramDsl
inline fun PumlStateDiagramDslEntry.initState() = state("[*]")

@InlineDsl
@PumlStateDiagramDsl
inline fun PumlStateDiagramDslEntry.finishState() = state("[*]")

@PumlStateDiagramDsl
inline fun PumlStateDiagramDslEntry.state(name: String, text: String = "",
	builder: PumlStateDiagramCompositedState.() -> Unit) =
	PumlStateDiagramCompositedState(name, text).also { it.builder() }.also { states += it }

@PumlStateDiagramDsl
inline fun PumlStateDiagramDslEntry.concurrentState(name: String, text: String = "",
	builder: PumlStateDiagramConcurrentState.() -> Unit) =
	PumlStateDiagramConcurrentState(name, text).also { it.builder() }.also { states += it }

@PumlStateDiagramDsl
inline fun PumlStateDiagramDslEntry.link(sourceStateId: String, targetStateId: String, text: String = "") =
	PumlStateDiagramLink(sourceStateId, targetStateId, text).also { links += it }

@PumlStateDiagramDsl
inline fun PumlStateDiagramDslEntry.link(sourceState: PumlStateDiagramSimpleState, targetStateId: String,
	text: String = "") =
	link(sourceState.alias ?: sourceState.name, targetStateId, text)

@PumlStateDiagramDsl
inline fun PumlStateDiagramDslEntry.link(sourceStateId: String, targetState: PumlStateDiagramSimpleState,
	text: String = "") =
	link(sourceStateId, targetState.alias ?: targetState.name, text)

@PumlStateDiagramDsl
inline fun PumlStateDiagramDslEntry.link(sourceState: PumlStateDiagramSimpleState,
	targetState: PumlStateDiagramSimpleState, text: String = "") =
	link(sourceState.alias ?: sourceState.name, targetState.alias ?: targetState.name, text)

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
inline fun PumlStateDiagramConcurrentState.state(name: String, text: String = "") =
	PumlStateDiagramSimpleState(name, text).also { states += it }

@PumlStateDiagramDsl
inline fun PumlStateDiagramConcurrentState.section(builder: PumlStateDiagramConcurrentSection.() -> Unit) =
	PumlStateDiagramConcurrentSection().also { it.builder() }.also { sections += it }

@PumlStateDiagramDsl
inline infix fun PumlStateDiagramLink.arrowColor(arrowColor: String) =
	this.also { it.arrowColor = arrowColor }

@PumlStateDiagramDsl
inline infix fun PumlStateDiagramLink.arrowStyle(arrowShape: PumlArrowStyle) =
	this.also { it.arrowStyle = arrowShape }

@PumlStateDiagramDsl
inline infix fun PumlStateDiagramLink.arrowDirection(arrowDirection: PumlArrowDirection) =
	this.also { it.arrowDirection = arrowDirection }

@PumlStateDiagramDsl
inline infix fun PumlStateDiagramLink.arrowLength(arrowLength: Int) =
	this.also { it.arrowLength = arrowLength.coerceIn(1, 8) }
