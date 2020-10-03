// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl.creole

import com.windea.breezeframework.dsl.*

/**
 * Dsl configuration of [CreoleDsl].
 */
@CreoleDslMarker
object CreoleDslConfig : DslConfig {
	var indent: String = "  "
	var truncated: String = "..."
	var doubleQuoted: Boolean = true
	var markerCount: Int = 3
	var emptyColumnLength: Int = 3

	internal val quote get() = if(doubleQuoted) '\"' else '\''
	internal val emptyColumnText: String get() = " ".repeat(emptyColumnLength)
}
