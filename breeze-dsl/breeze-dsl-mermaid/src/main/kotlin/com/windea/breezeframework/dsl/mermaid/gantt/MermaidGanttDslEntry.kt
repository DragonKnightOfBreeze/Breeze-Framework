package com.windea.breezeframework.dsl.mermaid.gantt

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.*

/**
 * Mermaid甘特图领域特定语言的入口。
 * @property dateFormat 图标的日期格式化方式。
 * @property sections 图表的分区一览。
 */
@MermaidGanttDsl
interface MermaidGanttDslEntry : MermaidDslEntry, CanSplitLine {
	var dateFormat:MermaidGantt.DateFormat?
	val sections:MutableList<MermaidGantt.Section>

	override fun contentString() = buildString {
		if(dateFormat != null) append(dateFormat).append(splitSeparator)
		appendJoin(sections, splitSeparator)
	}
}
