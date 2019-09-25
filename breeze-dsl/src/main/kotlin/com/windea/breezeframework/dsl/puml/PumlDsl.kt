@file:Reference("[PlantUml](http://plantuml.com)")
@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.dsl.puml

import com.windea.breezeframework.core.annotations.marks.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.puml.PumlConfig.indent
import com.windea.breezeframework.dsl.puml.PumlConfig.quote
import org.intellij.lang.annotations.*

//REGION Dsl annotations

@DslMarker
annotation class PumlDsl

//REGION Top interfaces

/**PlantUml Dsl.*/
@PumlDsl
interface Puml : Dsl {
	var title: PumlTitle?
	var legend: PumlLegend?
	var header: PumlHeader?
	var footer: PumlFooter?
	var scale: PumlScale?
	var caption: PumlCaption?
	val notes: MutableSet<PumlNote>
	val skinParams: PumlSkinParams
	
	fun getPrefixString(): String
	fun getSuffixString(): String
}

/**PlantUml Dsl的元素。*/
@PumlDsl
interface PumlDslElement : DslElement

/**PlantUml Dsl的配置。*/
object PumlConfig : DslConfig {
	/**缩进长度。*/
	var indentSize = 4
		set(value) = run { field = value.coerceIn(-2, 8) }
	/**是否使用双引号。*/
	var useDoubleQuote: Boolean = true
	
	internal val indent get() = if(indentSize <= -1) "\t" * indentSize else " " * indentSize
	internal val quote get() = if(useDoubleQuote) "\"" else "'"
}

//REGION General Dsl elements and related extensions

/**Puml的默认代理实现。*/
@PumlDsl
open class PumlDelegate @PublishedApi internal constructor() : Puml {
	override var title: PumlTitle? = null
	override var legend: PumlLegend? = null
	override var header: PumlHeader? = null
	override var footer: PumlFooter? = null
	override var scale: PumlScale? = null
	override var caption: PumlCaption? = null
	override val notes: MutableSet<PumlNote> = mutableSetOf()
	override val skinParams: PumlSkinParams = PumlSkinParams()
	
	//TODO need to generate alias for no-alias notes
	override fun getPrefixString(): String = TODO()
	
	override fun getSuffixString(): String = TODO()
	
	override fun toString(): String = TODO()
}

@PumlDsl
inline fun <T : Puml> T.title(text: String) = PumlTitle(text).also { title = it }

@PumlDsl
inline fun <T : Puml> T.legend(text: String) = PumlLegend(text).also { legend = it }

@PumlDsl
inline fun <T : Puml> T.header(text: String) = PumlHeader(text).also { header = it }

@PumlDsl
inline fun <T : Puml> T.footer(text: String) = PumlFooter(text).also { footer = it }

@PumlDsl
inline fun <T : Puml> T.scale(expression: String) = PumlScale(expression).also { scale = it }

@PumlDsl
inline fun <T : Puml> T.caption(text: String) = PumlCaption(text).also { caption = it }

@PumlDsl
inline fun <T : Puml> T.note(text: String) = PumlNote(text).also { notes += it }

@PumlDsl
inline fun <T : Puml> T.skinParams(builder: PumlSkinParams.() -> Unit) = skinParams.builder()


/**PlantUml顶级元素。*/
@PumlDsl
sealed class PumlTopElement(
	val type: String,
	@Language("Html")
	val text: String
) : PumlDslElement, CanIndentContent, CanWrapContent {
	var position: PumlTopElementPosition? = null
	
	override var indentContent: Boolean = true
	override var wrapContent: Boolean = "\n" in text || "\r" in text//wrap content when necessary
	
	override fun toString(): String {
		val positionSnippet = position?.let { "$it " } ?: ""
		return if(wrapContent) {
			val indentedTextSnippet = if(indentContent) text.prependIndent(indent) else text
			"$positionSnippet$type\n$indentedTextSnippet\nend $type"
		} else {
			//unescape "\n" if necessary
			val textSnippet = text.replaceWithEscapedWrap()
			"$positionSnippet$type $textSnippet"
		}
	}
	
	
	@PumlDsl
	inline infix fun at(position: PumlTopElementPosition) = this.also { it.position = position }
}

/**PlantUml标题。*/
@PumlDsl
class PumlTitle @PublishedApi internal constructor(
	text: String
) : PumlTopElement("title", text)

/**PlantUml图例说明。*/
@PumlDsl
class PumlLegend @PublishedApi internal constructor(
	text: String
) : PumlTopElement("legend", text)

/**PlantUml页眉。*/
@PumlDsl
class PumlHeader @PublishedApi internal constructor(
	text: String
) : PumlTopElement("header", text)

/**PlantUml页脚。*/
@PumlDsl
class PumlFooter @PublishedApi internal constructor(
	text: String
) : PumlTopElement("footer", text)


//TODO Better api
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
	@Language("Creole")
	val text: String //NOTE can wrap by "\n"
) : PumlStateDiagramDslElement, CanIndentContent, CanWrapContent {
	//must: alias or (position & targetStateName), position win first.
	var alias: String? = null
	var position: PumlNotePosition? = null
	var targetId: String? = null
	
	override var indentContent: Boolean = true
	override var wrapContent: Boolean = "\n" in text || "\r" in text //wrap content when necessary
	
	override fun equals(other: Any?): Boolean {
		return this === other || (other is PumlNote && other.alias == alias)
	}
	
	override fun hashCode(): Int {
		return alias.hashCode()
	}
	
	override fun toString(): String {
		val aliasSnippet = if(position == null) " as $alias" else ""
		val positionSnippet = position?.let { " ${it.text} $targetId" } ?: ""
		return if(wrapContent) {
			val indentedTextSnippet = if(indentContent) text.prependIndent(indent) else text
			"note$aliasSnippet$positionSnippet\n$indentedTextSnippet\nend note"
		} else {
			val textSnippet = text.replaceWithEscapedWrap()
			if(position == null) "note ${textSnippet.wrapQuote(quote)} as $alias"
			else "note$positionSnippet: $textSnippet"
		}
	}
	
	
	@PumlStateDiagramDsl
	inline infix fun alias(alias: String) = this.also { it.alias = alias }
	
	@PumlStateDiagramDsl
	inline infix fun leftOf(targetId: String) = this.also { it.targetId = targetId }
		.also { it.position = PumlNotePosition.LeftOf }
	
	@PumlStateDiagramDsl
	inline infix fun rightOf(targetId: String) = this.also { it.targetId = targetId }
		.also { it.position = PumlNotePosition.RightOf }
	
	@PumlStateDiagramDsl
	inline infix fun topOf(targetId: String) = this.also { it.targetId = targetId }
		.also { it.position = PumlNotePosition.TopOf }
	
	@PumlStateDiagramDsl
	inline infix fun bottomOf(targetId: String) = this.also { it.targetId = targetId }
		.also { it.position = PumlNotePosition.BottomOf }
}


//TODO
/**PlantUml显示参数。*/
@PumlDsl
class PumlSkinParams @PublishedApi internal constructor() :
	PumlDslElement, MutableMap<String, Any> by HashMap() {
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
class PumlNestedSkinParams @PublishedApi internal constructor() :
	PumlDslElement, CanIndentContent, MutableMap<String, Any> by HashMap() {
	override var indentContent: Boolean = true
	
	override fun toString(): String {
		if(isEmpty()) return "{}"
		val paramsSnippet = joinToString("\n") { (k, v) -> "skinparam $k $v" }
		val indentedSnippet = if(indentContent) paramsSnippet.prependIndent(indent) else paramsSnippet
		return "{\n$indentedSnippet\n}"
	}
}

//REGION Enumerations and constants

/**PlantUml顶级元素的位置。*/
enum class PumlTopElementPosition(
	val text: String
) {
	Right("right"), Left("left"), Center("center")
}

/**PlantUml箭头的形状。*/
enum class PumlArrowShape(val text: String) {
	Dotted("dotted"), Dashed("dashed"), Bold("bold"), Hidden("hidden")
}

/**PlantUml箭头的方向。*/
enum class PumlArrowDirection(val text: String) {
	Down("down"), Up("up"), Left("left"), Right("right")
}

/**PlantUml注释的位置。*/
enum class PumlNotePosition(val text: String) {
	RightOf("right of"), LeftOf("left of"), TopOf("top of"), BottomOf("bottom of")
}
