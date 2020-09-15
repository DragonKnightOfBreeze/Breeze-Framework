/*******************************************************************************
 * Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
 * Breeze is blowing...
 ******************************************************************************/

package com.windea.breezeframework.dsl.mermaid.piechart

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.*
import com.windea.breezeframework.dsl.mermaid.piechart.MermaidPieChartDslDefinitions.*

/**
 * [Mermaid Pie Chart](https://mermaidjs.github.io/#/pie) dsl.
 */
@MermaidPieChartDslMarker
class MermaidPieChartDsl @PublishedApi internal constructor() : MermaidDsl(), IChartDslEntry, Indentable {
	var title: Title? = null
	override val sections: MutableSet<Section> = mutableSetOf()
	override var indentContent: Boolean = true

	override fun toString(): String {
		val titleSnippet = title.typing { "$it${DslConstants.ls}" }
		val contentSnippet = toContentString().doIndent(MermaidDslConfig.indent)
		return "pie${DslConstants.ls}$titleSnippet$contentSnippet"
	}
}
