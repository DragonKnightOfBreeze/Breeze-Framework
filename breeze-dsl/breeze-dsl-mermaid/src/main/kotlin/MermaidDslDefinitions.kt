// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl.mermaid

import com.windea.breezeframework.dsl.*

/**
 * Dsl definitions of [MermaidDsl].
 */
@MermaidDslMarker
interface MermaidDslDefinitions {
	companion object {
		internal fun String.htmlWrap() = this.replace("\r\n", "<br>").replace("\r", "<br>").replace("\n", "<br>")
	}


	/**
	 * Mermaid领域特定语言的入口。
	 */
	@MermaidDslMarker
	interface IDslEntry : DslEntry

	/**
	 * Mermaid领域特定语言的元素。
	 */
	@MermaidDslMarker
	interface IDslElement : DslElement
}

