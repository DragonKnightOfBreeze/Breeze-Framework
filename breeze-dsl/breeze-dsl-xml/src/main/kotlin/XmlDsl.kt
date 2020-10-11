// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl.xml

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.core.types.*
import com.windea.breezeframework.dsl.*

/**
 * Xml dsl.
 */
@XmlDslMarker
class XmlDsl @PublishedApi internal constructor() : Dsl {
	val declarations: MutableList<XmlDslDefinitions.Statement> = mutableListOf()
	val comments: MutableList<XmlDslDefinitions.Comment> = mutableListOf()
	var rootElement: XmlDslDefinitions.Element? = null

	operator fun String.unaryMinus(): XmlDslDefinitions.Comment = comment(this)
	operator fun String.invoke(block: Block<XmlDslDefinitions.Element>): XmlDslDefinitions.Element = element(this, block = block)
	operator fun String.invoke(vararg args: Arg, block: Block<XmlDslDefinitions.Element>): XmlDslDefinitions.Element = element(this, *args, block = block)

	override fun toString(): String {
		require(rootElement != null) { "Root element of Xml document cannot be null." }
		return arrayOf(
			declarations.joinToText(DslConstants.ls),
			comments.joinToText(DslConstants.ls),
			rootElement
		).joinToText(DslConstants.ls)
	}
}
