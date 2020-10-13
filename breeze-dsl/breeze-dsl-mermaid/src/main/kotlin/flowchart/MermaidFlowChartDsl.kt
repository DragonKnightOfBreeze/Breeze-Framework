// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl.mermaid.flowchart

import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.*
import com.windea.breezeframework.dsl.mermaid.flowchart.MermaidFlowChartDslDefinitions.*

/**
 * [Mermaid Flow Chart](https://mermaidjs.github.io/#/flowchart) dsl.
 */
@MermaidFlowChartDslMarker
class MermaidFlowChartDsl @PublishedApi internal constructor(
	val direction: Direction,
) : MermaidDsl(), IDslEntry, Indentable {
	override val nodes: MutableSet<Node> = mutableSetOf()
	override val links: MutableList<Link> = mutableListOf()
	override val subGraphs: MutableList<SubGraph> = mutableListOf()
	override val nodeStyles: MutableList<NodeStyle> = mutableListOf()
	override val linkStyles: MutableList<LinkStyle> = mutableListOf()
	override val classDefs: MutableList<ClassDef> = mutableListOf()
	override val classRefs: MutableList<ClassRef> = mutableListOf()
	override var indentContent: Boolean = true
	override var splitContent: Boolean = true

	override fun toString(): String {
		val directionSnippet = direction.text
		val contentSnippet = toContentString().doIndent(MermaidDslConfig.indent)
		return "graph $directionSnippet${DslConstants.ls}$contentSnippet"
	}
}
