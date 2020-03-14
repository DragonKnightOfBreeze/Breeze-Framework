package com.windea.breezeframework.dsl.mermaid.flowchart

import com.windea.breezeframework.core.constants.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.*

/**
 * Mermaid流程图领域特定语言的入口。
 * @property nodes 节点一览。忽略重复的元素。
 * @property links 连接一览。
 * @property subGraphs 子图一览。
 * @property nodeStyles 节点风格一览。
 * @property linkStyles 连接风格一览。
 * @property classDefs CSS类定义一览。
 * @property classRefs CSS类引用一览。
 */
@MermaidFlowChartDsl
interface MermaidFlowChartDslEntry : MermaidDslEntry, CanSplitLine, WithTransition<MermaidFlowChart.Node, MermaidFlowChart.Link> {
	val nodes:MutableSet<MermaidFlowChart.Node>
	val links:MutableList<MermaidFlowChart.Link>
	val subGraphs:MutableList<MermaidFlowChart.SubGraph>
	val nodeStyles:MutableList<MermaidFlowChart.NodeStyle>
	val linkStyles:MutableList<MermaidFlowChart.LinkStyle>
	val classDefs:MutableList<MermaidFlowChart.ClassDef>
	val classRefs:MutableList<MermaidFlowChart.ClassRef>

	override fun contentString() = buildString {
		if(nodes.isNotEmpty()) appendJoin(nodes, SystemProperties.lineSeparator).append(splitSeparator)
		if(links.isNotEmpty()) appendJoin(links, SystemProperties.lineSeparator).append(splitSeparator)
		if(subGraphs.isNotEmpty()) appendJoin(subGraphs, SystemProperties.lineSeparator).append(splitSeparator)
		if(nodeStyles.isNotEmpty()) appendJoin(nodeStyles, SystemProperties.lineSeparator).append(splitSeparator)
		if(linkStyles.isNotEmpty()) appendJoin(linkStyles, SystemProperties.lineSeparator).append(splitSeparator)
		if(classDefs.isNotEmpty()) appendJoin(classDefs, SystemProperties.lineSeparator).append(splitSeparator)
		if(classRefs.isNotEmpty()) appendJoin(classRefs, SystemProperties.lineSeparator).append(splitSeparator)
	}.trimEnd()

	override fun String.links(other:String) = link(this, other)
}
