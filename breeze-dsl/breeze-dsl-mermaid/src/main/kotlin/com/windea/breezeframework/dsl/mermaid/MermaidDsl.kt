@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.dsl.mermaid

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*

//region dsl top declarations
/**Mermaid的Dsl。*/
@Reference("[Mermaid](https://mermaidjs.github.io)")
@DslMarker
@MustBeDocumented
internal annotation class MermaidDsl

/**Mermaid的扩展特性。*/
@MustBeDocumented
internal annotation class MermaidDslExtendedFeature

/**Mermaid。*/
@Reference("[Mermaid](https://mermaidjs.github.io)")
@MermaidDsl
abstract class Mermaid : DslDocument

/**Mermaid配置。*/
@MermaidDsl
object MermaidConfig : DslConfig {
	private val indentSizeRange = -2..8

	var indentSize = 4
		set(value) = run { field = value.coerceIn(-2, 8) }
	var preferDoubleQuote: Boolean = true

	@PublishedApi internal val indent get() = if(indentSize <= -1) "\t" * indentSize else " " * indentSize
	@PublishedApi internal val quote get() = if(preferDoubleQuote) '"' else '\''
}

/**Mermaid Dsl的入口。*/
@MermaidDsl
interface MermaidDslEntry : DslEntry

/**Mermaid Dsl的元素。*/
@MermaidDsl
interface MermaidDslElement : DslElement
//endregion

//region helpful extensions
/**将`\n`或`\r`替换成`<br>`。*/
@PublishedApi
internal fun String.replaceWithHtmlWrap() = this.replace("\n", "<br>").replace("\r", "<br>")
//endregion

//region build extensions
@MermaidDsl
inline fun mermaidConfig(block: MermaidConfig.() -> Unit) = MermaidConfig.apply(block)
//endregion
