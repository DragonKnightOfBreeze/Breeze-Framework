package com.windea.breezeframework.dsl.creole

/**
 * [Creole](http://plantuml.com/zh/creoleDsl) dsl.
 */
@CreoleDslMarker
class CreoleDsl @PublishedApi internal constructor() : CreoleDslDefinitions.IDslEntry, CreoleDslDefinitions.InlineDslEntry {
	override val content: MutableList<CreoleDslDefinitions.TopDslElement> = mutableListOf()

	override fun toString() = toContentString()
}
