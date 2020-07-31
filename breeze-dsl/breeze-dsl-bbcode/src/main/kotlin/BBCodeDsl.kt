package com.windea.breezeframework.dsl.bbcode

import com.windea.breezeframework.dsl.*

/**
 * [BBCode](https://www.bbcode.org/) dsl.
 */
class BBCodeDsl @PublishedApi internal constructor() : Dsl {
	var text: CharSequence = ""

	override fun toString(): String = text.toString()
}
