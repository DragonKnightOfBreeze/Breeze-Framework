@file:Reference("[PlantUml](http://plantuml.com)")
@file:Suppress("CanBePrimaryConstructorProperty", "NOTHING_TO_INLINE")

package com.windea.breezeframework.dsl.puml

import com.windea.breezeframework.core.annotations.marks.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.puml.PumlConfig.indent
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
	val skinParams: PumlSkinParams
	
	val prefixString: String get() = ""
	val suffixString: String get() = ""
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
class PumlDelegate : Puml {
	override var title: PumlTitle? = null
	override var legend: PumlLegend? = null
	override var header: PumlHeader? = null
	override var footer: PumlFooter? = null
	override var scale: PumlScale? = null
	override var caption: PumlCaption? = null
	override val skinParams: PumlSkinParams = PumlSkinParams()
	
	override val prefixString: String
		get() = TODO()
	
	override val suffixString: String
		get() = TODO()
}

@PumlDsl
inline fun <T : Puml> T.title(text: String, position: PumlDslElementPosition? = null) =
	PumlTitle(text, position).also { title = it }

@PumlDsl
inline fun <T : Puml> T.legend(text: String, position: PumlDslElementPosition? = null) =
	PumlLegend(text, position).also { legend = it }

@PumlDsl
inline fun <T : Puml> T.header(text: String, position: PumlDslElementPosition? = null) =
	PumlHeader(text, position).also { header = it }

@PumlDsl
inline fun <T : Puml> T.footer(text: String, position: PumlDslElementPosition? = null) =
	PumlFooter(text, position).also { footer = it }

@PumlDsl
inline fun <T : Puml> T.scale(expression: String) = PumlScale(expression).also { scale = it }

@PumlDsl
inline fun <T : Puml> T.caption(text: String) = PumlCaption(text).also { caption = it }

@PumlDsl
inline fun <T : Puml> T.skinParams(builder: PumlSkinParams.() -> Unit) = skinParams.also { it.builder() }


/**PlantUml顶级元素。*/
@PumlDsl
sealed class PumlTopElement(
	val type: String,
	text: String,
	position: PumlDslElementPosition? = null
) : PumlDslElement, CanIndentContent, CanWrapContent {
	@Language("Html")
	val text: String = text.also {
		//wrap when necessary
		if("\n" in it) wrapContent = true
	}
	val position: PumlDslElementPosition? = position
	
	override var indentContent: Boolean = true
	override var wrapContent: Boolean = false
	
	override fun toString(): String {
		val positionSnippet = position?.let { "$it " } ?: ""
		return if(wrapContent) {
			val indentedTextSnippet = if(indentContent) text.prependIndent(indent) else text
			"$positionSnippet$type\n$indentedTextSnippet\nend $type"
		} else {
			//unescape "\n" if necessary
			val textSnippet = text.replace("\n", "\\n")
			"$positionSnippet$type $textSnippet"
		}
	}
}

/**PlantUml标题。*/
@PumlDsl
class PumlTitle(
	text: String,
	position: PumlDslElementPosition? = null
) : PumlTopElement("title", text, position)

/**PlantUml图例说明。*/
@PumlDsl
class PumlLegend(
	text: String,
	position: PumlDslElementPosition? = null
) : PumlTopElement("legend", text, position)

/**PlantUml页眉。*/
@PumlDsl
class PumlHeader(
	text: String,
	position: PumlDslElementPosition? = null
) : PumlTopElement("header", text, position)

/**PlantUml页脚。*/
@PumlDsl
class PumlFooter(
	text: String,
	position: PumlDslElementPosition? = null
) : PumlTopElement("footer", text, position)


//TODO Better api
/**PlantUml缩放。*/
@PumlDsl
class PumlScale(
	expression: String
) : PumlDslElement {
	val expression: String = expression
	
	override fun toString(): String {
		return "scale $expression"
	}
}

/**PlantUml图片标题。*/
class PumlCaption(
	text: String
) : PumlDslElement {
	val text: String = text
	
	override fun toString(): String {
		return "caption $text"
	}
}


//TODO
/**PlantUml显示参数。*/
@PumlDsl
class PumlSkinParams : PumlDslElement, MutableMap<String, Any> by HashMap() {
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
class PumlNestedSkinParams : PumlDslElement, CanIndentContent, MutableMap<String, Any> by HashMap() {
	override var indentContent: Boolean = true
	
	override fun toString(): String {
		if(isEmpty()) return "{}"
		val paramsSnippet = joinToString("\n") { (k, v) -> "skinparam $k $v" }
		val indentedSnippet = if(indentContent) paramsSnippet.prependIndent(indent) else paramsSnippet
		return "{\n$indentedSnippet\n}"
	}
}

//REGION Enumerations and constants

/**PlantUml Dsl元素位置。*/
enum class PumlDslElementPosition(
	val text: String
) {
	Right("right"), Left("left"), Center("center")
}
