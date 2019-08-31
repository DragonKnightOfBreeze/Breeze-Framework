package com.windea.utility.common.dsl.text

import com.windea.utility.common.annotations.marks.*
import com.windea.utility.common.dsl.*
import com.windea.utility.common.dsl.text.StarBoundTextDslConfig.defaultName

/**StarBound文本的领域专用语言。*/
@NotSuitable("直接从json/yaml/patch文件生成StarBound文本时")
data class StarBoundTextDsl @PublishedApi internal constructor(
	override val name: String,
	override val text: MutableList<StarBoundTextDslElement> = mutableListOf()
) : Dsl, StarBoundRichText(text) {
	override fun toString(): String {
		return text.joinToString("")
	}
}

/**StarBound文本的Dsl的配置。*/
object StarBoundTextDslConfig : DslConfig {
	const val defaultName = "starBoundText"
}


/**StarBound文本的Dsl标记。*/
@DslMarker
internal annotation class StarBoundTextDslMarker


/**StarBound文本的Dsl的元素。*/
@StarBoundTextDslMarker
interface StarBoundTextDslElement


/**StarBound普通文本。*/
data class StarBoundText @PublishedApi internal constructor(
	val text: String
) : StarBoundTextDslElement {
	override fun toString(): String {
		return text
	}
}

/**StarBound占位文本。*/
data class StarBoundPlaceHolderText @PublishedApi internal constructor(
	val placeHolder: String
) : StarBoundTextDslElement {
	override fun toString(): String {
		return "<$placeHolder>"
	}
}


/**StarBound富文本。*/
abstract class StarBoundRichText(
	open val text: MutableList<StarBoundTextDslElement> = mutableListOf()
) : StarBoundTextDslElement {
	operator fun String.unaryPlus() = this@StarBoundRichText.t(this)
	
	operator fun String.unaryMinus() = this@StarBoundRichText.t(this, true)
	
	operator fun StarBoundTextDslElement.plus(text: String) = this@StarBoundRichText.t(text)
	
	operator fun StarBoundTextDslElement.plus(element: StarBoundTextDslElement) = element
}

/**StarBound彩色文本。*/
data class StarBoundColorText @PublishedApi internal constructor(
	val color: String,
	override val text: MutableList<StarBoundTextDslElement> = mutableListOf()
) : StarBoundRichText(text) {
	override fun toString(): String {
		val textSnippet = text.joinToString("")
		return "^$color;$textSnippet^reset;"
	}
}


/**构建StarBound文本的Dsl。*/
inline fun Dsl.Companion.starBoundText(name: String = defaultName, text: StarBoundTextDsl.() -> Unit) =
	StarBoundTextDsl(name).also { it.text() }

/**配置StarBound文本的Dsl。*/
inline fun DslConfig.Companion.starBoundText(config: StarBoundTextDslConfig.() -> Unit) = StarBoundTextDslConfig.config()


/**创建StarBound文本。*/
fun StarBoundRichText.t(text: String, cleartext: Boolean = false) =
	StarBoundText(text).also { if(cleartext) this.text.clear() }.also { this.text += it }

/**创建StarBound占位文本。*/
fun StarBoundRichText.pht(placeHolder: String) =
	StarBoundPlaceHolderText(placeHolder).also { this.text += it }

/**创建StarBound彩色文本。*/
inline fun StarBoundRichText.ct(color: String, text: StarBoundColorText.() -> Unit) =
	StarBoundColorText(color).also { it.text() }.also { this.text += it }
