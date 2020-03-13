@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.dsl.mermaid

import com.windea.breezeframework.core.constants.text.*
import com.windea.breezeframework.dsl.*

//region dsl top declarations
/**
 * Mermaid的Dsl。
 * 参见：[Mermaid](https://mermaidjs.github.io)
 */
@DslMarker
@MustBeDocumented
annotation class MermaidDsl

/**Mermaid的扩展特性。*/
@MustBeDocumented
annotation class MermaidDslExtendedFeature

/**Mermaid的入口。*/
@MermaidDsl
interface MermaidEntry : DslEntry

/**Mermaid的元素。*/
@MermaidDsl
interface MermaidElement : DslElement

/**
 * Mermaid。
 * 参见：[Mermaid](https://mermaidjs.github.io)
 */
@MermaidDsl
interface Mermaid {
	/**Mermaid的文档。*/
	abstract class Document : MermaidEntry

	/**
	 * Mermaid的配置。
	 * @property indent 文本的缩进。
	 * @property doubleQuoted 是否偏向使用双引号。
	 * @property quote 文本的引号。
	 */
	data class Config(
		var indent:String = "  ",
		var doubleQuoted:Boolean = true
	) {
		val quote get() = if(doubleQuoted) '\"' else '\''
	}

	companion object {
		@PublishedApi internal val config = Config()
	}
}


/**
 * (No document.)
 */
@TopDslFunction
@MermaidDsl
inline fun mermaidConfig(block:Mermaid.Config.() -> Unit) = Mermaid.config.block()


@PublishedApi internal val ls = SystemProperties.lineSeparator

@PublishedApi internal fun String.doWrap() = this.replace("\r\n", "<br>").replace("\r", "<br>").replace("\n", "<br>")



