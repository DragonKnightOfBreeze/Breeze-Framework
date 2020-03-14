package com.windea.breezeframework.dsl.markdown

import com.windea.breezeframework.dsl.*

/**Markdown领域特定语言的入口。*/
@MarkdownDsl
interface MarkdownDslEntry : DslEntry, WithText<Markdown.TextBlock> {
	val content:MutableList<MarkdownDslTopElement>
	override fun contentString():String = content.joinToString("\n\n")

	override fun String.unaryPlus() = Markdown.TextBlock(this).also { content += it }
}
