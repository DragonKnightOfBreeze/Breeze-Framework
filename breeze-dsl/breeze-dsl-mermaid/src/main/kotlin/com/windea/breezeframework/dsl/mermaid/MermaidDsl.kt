package com.windea.breezeframework.dsl.mermaid

import com.windea.breezeframework.core.constants.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.Mermaid.*
import com.windea.breezeframework.dsl.mermaid.Mermaid.Companion.config

/**
 * Mermaid的Dsl。
 * 参见：[Mermaid](https://mermaidjs.github.io)
 */
@DslMarker
@MustBeDocumented
annotation class MermaidDsl

/**
 * Mermaid的扩展特性。
 * 参见：[Mermaid](https://mermaidjs.github.io)
 * */
@MustBeDocumented
annotation class MermaidDslExtendedFeature

/**
 * Mermaid的入口。
 * 参见：[Mermaid](https://mermaidjs.github.io)
 * */
@MermaidDsl
interface MermaidEntry : DslEntry

/**
 * Mermaid的元素。
 * 参见：[Mermaid](https://mermaidjs.github.io)
 * */
@MermaidDsl
interface MermaidElement : DslElement

/**
 * Mermaid。
 * 参见：[Mermaid](https://mermaidjs.github.io)
 */
@MermaidDsl
interface Mermaid {
	/**
	 * Mermaid的配置。
	 * @property indent 文本的缩进。
	 * @property doubleQuoted 是否偏向使用双引号。
	 */
	data class Config(
		var indent:String = "  ",
		var doubleQuoted:Boolean = true
	) {
		@PublishedApi internal val quote get() = if(doubleQuoted) '\"' else '\''
	}

	companion object {
		@PublishedApi internal val config = Config()
	}

	/**Mermaid的文档。*/
	abstract class Document : MermaidEntry
}


/**
 * (No document.)
 */
@TopDslFunction
@MermaidDsl
inline fun mermaidConfig(block:Config.() -> Unit) = config.block()


@PublishedApi internal val ls = SystemProperties.lineSeparator

@PublishedApi internal fun String.doWrap() = this.replace("\r\n", "<br>").replace("\r", "<br>").replace("\n", "<br>")



