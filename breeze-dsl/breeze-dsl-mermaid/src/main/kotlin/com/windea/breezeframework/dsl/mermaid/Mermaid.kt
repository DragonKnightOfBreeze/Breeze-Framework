package com.windea.breezeframework.dsl.mermaid

import com.windea.breezeframework.core.constants.*

/**Mermaid。*/
@MermaidDsl
interface Mermaid {
	/**Mermaid文档。*/
	@MermaidDsl
	abstract class Document : MermaidDslEntry

	/**
	 * Mermaid配置。
	 * @property indent 文本的缩进。
	 * @property doubleQuoted 是否偏向使用双引号。
	 */
	data class Config(
		var indent:String = "  ",
		var doubleQuoted:Boolean = true
	) {
		val quote get() = if(doubleQuoted) '\"' else '\''
	}

	companion object {
		@PublishedApi internal val config = Config()
		@PublishedApi internal val ls = SystemProperties.lineSeparator

		@PublishedApi
		internal fun String.htmlWrap() = this.replace("\r\n", "<br>").replace("\r", "<br>").replace("\n", "<br>")
	}
}
