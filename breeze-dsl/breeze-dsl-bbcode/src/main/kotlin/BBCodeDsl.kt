// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl.bbcode

import com.windea.breezeframework.dsl.*

/**
 * [BBCode](https://www.bbcode.org/) dsl.
 */
class BBCodeDsl @PublishedApi internal constructor() : DslDocument {
	var text: CharSequence = ""

	override fun toString(): String = text.toString()
}
