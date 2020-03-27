@file:Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS", "INLINE_CLASS_NOT_TOP_LEVEL")

package com.windea.breezeframework.dsl.criticmarkup

import com.windea.breezeframework.core.domain.*
import com.windea.breezeframework.dsl.*

/**CriticMarkup文本。*/
@CriticMarkupDsl
interface CriticMarkup {
	/**CriticMarkup文本的文档。*/
	@CriticMarkupDsl
	class Document @PublishedApi internal constructor() : DslDocument, InlineDslEntry {
		var text:CharSequence = ""

		override fun toString() = text.toString()
	}


	/**CriticMarkup文本领域特定语言的内联入口。*/
	@CriticMarkupDsl
	interface InlineDslEntry : DslEntry

	/**CriticMarkup文本领域特定语言的内联元素。*/
	@CriticMarkupDsl
	interface InlineDslElement : DslElement, Inlineable

	/**CriticMarkup富文本。*/
	@CriticMarkupDsl
	interface RichText : InlineDslElement

	/**CriticMarkup添加文本。*/
	@CriticMarkupDsl
	inline class AppendText @PublishedApi internal constructor(override val text:CharSequence) : RichText {
		override fun toString() = "{++ $text ++}"
	}

	/**CriticMarkup删除线文本。*/
	@CriticMarkupDsl
	inline class DeleteText @PublishedApi internal constructor(override val text:CharSequence) : RichText {
		override fun toString() = "{-- $text --}"
	}

	/**
	 * CriticMarkup的替换文本。
	 * @property replacedText 替换后的文本。
	 */
	@CriticMarkupDsl
	class ReplaceText @PublishedApi internal constructor(
		override val text:CharSequence, val replacedText:CharSequence
	) : RichText {
		override fun toString() = "{~~ $text ~> $replacedText ~~}"
	}

	/**CriticMarkup注释文本。*/
	@CriticMarkupDsl
	inline class CommentText @PublishedApi internal constructor(override val text:CharSequence) : RichText {
		override fun toString() = "{>> $text <<}"
	}

	/**CriticMarkup强调文本。*/
	@CriticMarkupDsl
	inline class HighlightText @PublishedApi internal constructor(override val text:CharSequence) : RichText {
		override fun toString() = "{== $text ==}"
	}
}
