// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl.mermaid.gantt

import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.*
import com.windea.breezeframework.dsl.mermaid.gantt.MermaidGanttDslDefinitions.*

/**
 * [Mermaid Gantt Diagram](https://mermaidjs.github.io/#/gantt) dsl.
 */
@MermaidGanttDslMarker
class MermaidGanttDsl @PublishedApi internal constructor() : MermaidDsl(), IDslEntry, Indentable, Splitable {
	var title: Title? = null
	var dateFormat: DateFormat? = null
	override val sections: MutableList<Section> = mutableListOf()
	override var indentContent: Boolean = true
	override var splitContent: Boolean = false

	override fun toString(): String {
		val contentSnippet = arrayOf(title, dateFormat, toContentString()).doSplit().doIndent(MermaidDslConfig.indent)
		return "gantt${DslConstants.ls}$contentSnippet"
	}
}
