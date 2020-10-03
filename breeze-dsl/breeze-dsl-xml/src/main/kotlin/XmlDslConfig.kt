// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl.xml

import com.windea.breezeframework.dsl.*

/**
 * Dsl config of [XmlDsl].
 */
@XmlDslMarker
object XmlDslConfig : DslConfig {
	var indent: String = "  "
	var doubleQuoted: Boolean = true
	var autoCloseTag: Boolean = false

	internal val quote get() = if(doubleQuoted) '"' else '\''
}
