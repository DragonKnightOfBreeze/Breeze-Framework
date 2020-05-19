package com.windea.breezeframework.dsl.mermaid

import com.windea.breezeframework.dsl.*

/**Mermaid。*/
@MermaidDsl
interface Mermaid {
	/**Mermaid文档。*/
	@MermaidDsl
	abstract class Document : IDslEntry


	/**
	 * Mermaid领域特定语言的入口。
	 */
	@MermaidDsl
	interface IDslEntry : DslEntry

	/**
	 * Mermaid领域特定语言的元素。
	 */
	@MermaidDsl
	interface IDslElement : DslElement


	/**
	 * Mermaid配置。
	 * @property indent 文本缩进。
	 * @property doubleQuoted 是否偏向使用双引号。
	 */
	data class Config(
		var indent:String = "  ",
		var doubleQuoted:Boolean = true
	) {
		val quote get() = if(doubleQuoted) '\"' else '\''
	}

	companion object {
		val config = Config()

		internal fun String.htmlWrap() = this.replace("\r\n", "<br>").replace("\r", "<br>").replace("\n", "<br>")
	}
}
