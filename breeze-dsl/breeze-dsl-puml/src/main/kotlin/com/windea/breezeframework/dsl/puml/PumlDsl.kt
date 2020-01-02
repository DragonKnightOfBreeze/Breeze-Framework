@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.dsl.puml

import com.windea.breezeframework.core.annotations.api.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.puml.PumlConfig.indent
import com.windea.breezeframework.dsl.puml.PumlConfig.quote
import org.intellij.lang.annotations.*

//DELAY fully support

//region top annotations and interfaces
/**PlantUml的Dsl。*/
@ReferenceApi("[PlantUml](http://plantuml.com)")
@DslMarker
@MustBeDocumented
internal annotation class PumlDsl

/**PlantUml。*/
@PumlDsl
abstract class Puml : DslDocument, WithComment<PumlNote> {
	var title: PumlTitle? = null
	var legend: PumlLegend? = null
	var header: PumlHeader? = null
	var footer: PumlFooter? = null
	var scale: PumlScale? = null
	var caption: PumlCaption? = null
	val notes: MutableSet<PumlNote> = mutableSetOf()
	val skinParams: PumlSkinParams = PumlSkinParams()

	//TODO need to generate alias for no-alias notes
	fun toPrefixString(): String = TODO()

	fun toSuffixString(): String = TODO()

	@PumlDsl
	override fun String.unaryMinus() = note(this)
}

/**PlantUml的配置。*/
@PumlDsl
object PumlConfig : DslConfig {
	private val indentSizeRange = -2..8

	var indentSize = 4
		set(value) = run { if(value in indentSizeRange) field = value }
	var preferDoubleQuote: Boolean = true

	@PublishedApi internal val indent get() = if(indentSize <= -1) "\t" * indentSize else " " * indentSize
	@PublishedApi internal val quote get() = if(preferDoubleQuote) '"' else '\''
}

/**PlantUml Dsl的入口。*/
@PumlDsl
interface PumlDslEntry : DslEntry

/**PlantUml Dsl的元素。*/
@PumlDsl
interface PumlDslElement : DslElement
//endregion

//region dsl elements
/**PlantUml元素。*/
@PumlDsl
sealed class PumlElement(
	protected val type: String,
	@Language("Html")
	val text: String
) : PumlDslElement, CanWrap, CanIndent {
	var position: PumlTopElementPosition? = null

	override var indentContent: Boolean = true
	override var wrapContent: Boolean = "\n" in text || "\r" in text//wrap content when necessary

	override fun toString(): String {
		val positionSnippet = position?.text?.let { "$it " }.orEmpty()
		return if(wrapContent) {
			val indentedTextSnippet = if(indentContent) text.prependIndent(indent) else text
			"$positionSnippet$type\n$indentedTextSnippet\nend $type"
		} else {
			//unescape "\n" if necessary
			val textSnippet = text.replaceWithEscapedWrap()
			"$positionSnippet$type $textSnippet"
		}
	}
}

/**PlantUml标题。*/
@PumlDsl
class PumlTitle @PublishedApi internal constructor(
	text: String
) : PumlElement("title", text)

/**PlantUml图例说明。*/
@PumlDsl
class PumlLegend @PublishedApi internal constructor(
	text: String
) : PumlElement("legend", text)

/**PlantUml页眉。*/
@PumlDsl
class PumlHeader @PublishedApi internal constructor(
	text: String
) : PumlElement("header", text)

/**PlantUml页脚。*/
@PumlDsl
class PumlFooter @PublishedApi internal constructor(
	text: String
) : PumlElement("footer", text)

/**PlantUml缩放。*/
@PumlDsl
class PumlScale @PublishedApi internal constructor(
	val expression: String
) : PumlDslElement {
	override fun toString(): String {
		return "scale $expression"
	}
}

/**PlantUml图片标题。*/
@PumlDsl
class PumlCaption @PublishedApi internal constructor(
	val text: String
) : PumlDslElement {
	override fun toString(): String {
		return "caption $text"
	}
}

/**
 * PlantUml注释。
 *
 * 语法示例：
 * ```
 * note right of Target: text can\n wrap line.
 *
 * note "text" as Note
 *
 * note right of Target
 *     (multiline text.)
 * end note
 *
 * note as Note
 *     (multiline text.)
 * end note
 * ```
 */
@PumlDsl
class PumlNote @PublishedApi internal constructor(
	@Language("Creole") @MultilineProp("\\n")
	val text: String
) : PumlDslElement, CanWrap, CanIndent {
	//must: alias or (position & targetStateName), position win first.
	var alias: String? = null
	var position: PumlNotePosition? = null
	var targetName: String? = null

	override var indentContent: Boolean = true
	override var wrapContent: Boolean = false

	override fun equals(other: Any?): Boolean {
		return this === other || (other is PumlNote && other.alias == alias)
	}

	override fun hashCode(): Int {
		return alias.hashCode()
	}

	override fun toString(): String {
		if("\n" in text || "\r" in text) wrapContent = true //wrap if necessary

		val aliasSnippet = if(position == null) " as $alias" else ""
		val positionSnippet = position?.text?.let { " $it $targetName" }.orEmpty()
		return if(wrapContent) {
			val indentedTextSnippet = if(indentContent) text.prependIndent(indent) else text
			"note$aliasSnippet$positionSnippet\n$indentedTextSnippet\nend note"
		} else {
			val textSnippet = text.replaceWithEscapedWrap()
			if(position == null) "note ${textSnippet.wrapQuote(quote)} as $alias"
			else "note$positionSnippet: $textSnippet"
		}
	}
}

//TODO
/**PlantUml显示参数。*/
@PumlDsl
class PumlSkinParams @PublishedApi internal constructor() : PumlDslElement, MutableMap<String, Any> by HashMap() {
	override fun toString(): String {
		return joinToString("\n") { (k, v) -> "skinparam $k $v" }
	}

	@PumlDsl
	inline fun defaultFontName(value: String) = run { this["defaultFontName"] = value }

	@PumlDsl
	inline fun defaultFontColor(value: String) = run { this["defaultFontColor"] = value }

	@PumlDsl
	inline fun defaultFontSize(value: Int) = run { this["defaultFontSize"] = value }

	@PumlDsl
	inline fun backgroundColor(value: String) = run { this["backgroundColor"] = value }

	@PumlDsl
	inline fun borderColor(value: String) = run { this["borderColor"] = value }

	@PumlDsl
	inline fun roundCorner(value: Int) = run { this["roundCorner"] = value }

	/**Default to false.*/
	@PumlDsl
	inline fun handWritten(value: Boolean) = run { this["handWritten"] = value }

	/**Can be true/false/reverse.*/
	@PumlDsl
	inline fun monoChrome(value: Boolean?) = run { this["monoChrome"] = value ?: "reverse" }
}

//TODO
/**PlantUml内嵌显示参数。*/
@PumlDsl
class PumlNestedSkinParams @PublishedApi internal constructor() : PumlDslElement, CanIndent, MutableMap<String, Any> by HashMap() {
	override var indentContent: Boolean = true

	override fun toString(): String {
		if(isEmpty()) return "{}"
		val paramsSnippet = joinToString("\n") { (k, v) -> "skinparam $k $v" }
		val indentedContentSnippet = if(indentContent) paramsSnippet.prependIndent(indent) else paramsSnippet
		return "{\n$indentedContentSnippet\n}"
	}
}
//endregion

//region enumerations and constants
/**PlantUml顶级元素的位置。*/
@PumlDsl
enum class PumlTopElementPosition(val text: String) {
	Right("right"), Left("left"), Center("center")
}

/**PlantUml箭头的形状。*/
@PumlDsl
enum class PumlArrowShape(val text: String) {
	Dotted("dotted"), Dashed("dashed"), Bold("bold"), Hidden("hidden")
}

/**PlantUml箭头的方向。*/
@PumlDsl
enum class PumlArrowDirection(val text: String) {
	Down("down"), Up("up"), Left("left"), Right("right")
}

/**PlantUml注释的位置。*/
@PumlDsl
enum class PumlNotePosition(val text: String) {
	RightOf("right of"), LeftOf("left of"), TopOf("top of"), BottomOf("bottom of")
}
//endregion

//region build extensions
@PumlDsl
inline fun Puml.title(text: String) =
	PumlTitle(text).also { title = it }

@PumlDsl
inline fun Puml.legend(text: String) =
	PumlLegend(text).also { legend = it }

@PumlDsl
inline fun Puml.header(text: String) =
	PumlHeader(text).also { header = it }

@PumlDsl
inline fun Puml.footer(text: String) =
	PumlFooter(text).also { footer = it }

@PumlDsl
inline fun Puml.scale(expression: String) =
	PumlScale(expression).also { scale = it }

@PumlDsl
inline fun Puml.caption(text: String) =
	PumlCaption(text).also { caption = it }

@PumlDsl
inline fun Puml.note(text: String) =
	PumlNote(text).also { notes += it }

@PumlDsl
inline fun Puml.skinParams(block: PumlSkinParams.() -> Unit) =
	skinParams.block()

@PumlDsl
inline infix fun PumlElement.at(position: PumlTopElementPosition) =
	this.also { it.position = position }

@PumlDsl
inline infix fun PumlNote.alias(alias: String) =
	this.also { it.alias = alias }

@PumlDsl
inline infix fun PumlNote.leftOf(targetName: String) =
	this.also { it.targetName = targetName }.also { it.position = PumlNotePosition.LeftOf }

@PumlDsl
inline infix fun PumlNote.rightOf(targetName: String) =
	this.also { it.targetName = targetName }.also { it.position = PumlNotePosition.RightOf }

@PumlDsl
inline infix fun PumlNote.topOf(targetName: String) =
	this.also { it.targetName = targetName }.also { it.position = PumlNotePosition.TopOf }

@PumlDsl
inline infix fun PumlNote.bottomOf(targetName: String) =
	this.also { it.targetName = targetName }.also { it.position = PumlNotePosition.BottomOf }
//endregion

//region helpful extensions
/**将`\n`或`\r`替换成`<br>`。*/
@PublishedApi
internal fun String.replaceWithHtmlWrap() = this.replaceAll("\n" to "<br>", "\r" to "<br>")

/**将`\n`或`\r`替换成`\\n`和`\\r`。*/
@PublishedApi
internal fun String.replaceWithEscapedWrap() = this.replaceAll("\n" to "\\n", "\r" to "\\r")
//endregion