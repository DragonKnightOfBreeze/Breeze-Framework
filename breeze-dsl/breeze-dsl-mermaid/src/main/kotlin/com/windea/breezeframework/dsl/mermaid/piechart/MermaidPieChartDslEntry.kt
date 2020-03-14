package com.windea.breezeframework.dsl.mermaid.piechart

import com.windea.breezeframework.core.constants.*
import com.windea.breezeframework.dsl.mermaid.*

/**
 * Mermaid饼图领域特定语言的入口。
 * @property sections 分区一览。忽略重复的元素。
 */
@MermaidPieChartDsl
interface MermaidPieChartDslEntry : MermaidDslEntry {
	val sections:MutableSet<MermaidPieChart.Section>

	override fun contentString() = sections.joinToString(SystemProperties.lineSeparator)
}
