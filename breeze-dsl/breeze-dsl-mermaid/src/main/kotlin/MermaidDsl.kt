// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl.mermaid

import com.windea.breezeframework.dsl.DslConfig as IDslConfig
import com.windea.breezeframework.dsl.DslDocument as IDslDocument
import com.windea.breezeframework.dsl.DslElement as IDslElement
import com.windea.breezeframework.dsl.DslEntry as IDslEntry


@MermaidDslMarker
interface MermaidDsl {
	@MermaidDslMarker
	abstract class DslDocument : IDslDocument

	@MermaidDslMarker
	object DslConfig : IDslConfig {
		var indent: String = "  "
		var doubleQuoted: Boolean = true

		internal val quote get() = if(doubleQuoted) '\"' else '\''
	}

	@MermaidDslMarker
	interface DslEntry : IDslEntry

	@MermaidDslMarker
	interface DslElement : IDslElement

	companion object {
		internal fun String.htmlWrap() = this.replace("\r\n", "<br>").replace("\r", "<br>").replace("\n", "<br>")
	}
}

