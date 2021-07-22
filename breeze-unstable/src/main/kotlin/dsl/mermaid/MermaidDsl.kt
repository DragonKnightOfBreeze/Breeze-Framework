// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.dsl.mermaid

import icu.windea.breezeframework.dsl.DslConfig as IDslConfig
import icu.windea.breezeframework.dsl.DslDocument as IDslDocument
import icu.windea.breezeframework.dsl.DslElement as IDslElement
import icu.windea.breezeframework.dsl.DslEntry as IDslEntry


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

