package com.windea.breezeframework.dsl.commandline

import com.windea.breezeframework.dsl.*

/**
 * Command line text dsl.
 */
@CommandLineTextDslMarker
class CommandLineTextDsl @PublishedApi internal constructor() : Dsl, CommandLineTextDslDefinitions.InlineDslEntry {
	var text: CharSequence = ""
	override val inlineText get() = text

	override fun toString(): String = text.toString()
}
