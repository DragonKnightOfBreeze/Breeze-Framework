// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl.markdown

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*

/**
 * Markdown dsl.
 */
@MarkdownDslMarker
class MarkdownDsl @PublishedApi internal constructor() : Dsl, MarkdownDslDefinitions.IDslEntry {
	@MarkdownDslExtendedFeature
	var frontMatter: MarkdownDslDefinitions.FrontMatter? = null

	@MarkdownDslExtendedFeature
	var toc: MarkdownDslDefinitions.Toc? = null
	val references: MutableSet<MarkdownDslDefinitions.Reference> = mutableSetOf()
	override val content: MutableList<MarkdownDslDefinitions.TopDslElement> = mutableListOf()

	@MarkdownDslExtendedFeature
	override fun toString(): String {
		return arrayOf(
			frontMatter,
			toc,
			toContentString(),
			references.joinToText(DslConstants.ls)
		).joinToText("${DslConstants.ls}${DslConstants.ls}")
	}
}
