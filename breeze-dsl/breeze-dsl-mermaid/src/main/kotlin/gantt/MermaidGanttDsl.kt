// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl.mermaid.gantt

import com.windea.breezeframework.core.extension.*
import com.windea.breezeframework.dsl.api.*
import com.windea.breezeframework.dsl.mermaid.*
import com.windea.breezeframework.dsl.mermaid.MermaidDsl.DslConfig.indent

/**
 * Mermaid甘特图。
 */
@MermaidGanttDslMarker
interface MermaidGanttDsl {
	@MermaidGanttDslMarker
	class DslDocument @PublishedApi internal constructor() : MermaidDsl.DslDocument(), DslEntry, Indentable {
		var title: Title? = null
		var dateFormat: DateFormat? = null
		override val sections: MutableList<Section> = mutableListOf()
		override var indentContent: Boolean = true

		override fun toString(): String {
			val contentSnippet = arrayOf(title, dateFormat, toContentString()).joinToText("\n\n")
				.let { if(indentContent) it.prependIndent(indent) else it }
			return "gantt\n$contentSnippet"
		}
	}

	/**
	 * Mermaid甘特图领域特定语言的入口。
	 * @property dateFormat 图标的日期格式化方式。
	 * @property sections 图表的分区一览。
	 */
	@MermaidGanttDslMarker
	interface DslEntry : MermaidDsl.DslEntry {
		val sections: MutableList<Section>

		override fun toContentString(): String {
			return sections.joinToText("\n")
		}
	}

	/**
	 * Mermaid甘特图领域特定语言的元素。
	 */
	@MermaidGanttDslMarker
	interface DslElement : MermaidDsl.DslElement

	/**
	 * Mermaid甘特图的标题。
	 * @property text 标题的文本。
	 */
	@MermaidGanttDslMarker
	class Title @PublishedApi internal constructor(
		val text: String,
	) : DslElement {
		override fun toString(): String {
			return "title $text"
		}
	}

	/**
	 * Mermaid甘特图的日期格式。
	 * @property expression 日期的表达式。默认为"YYYY-MM-DD"。
	 */
	@MermaidGanttDslMarker
	class DateFormat @PublishedApi internal constructor(
		val expression: String,
	) : DslElement {
		override fun toString(): String {
			return "dateFormat $expression"
		}
	}

	/**
	 * Mermaid甘特图的分块。
	 * @property name 分块的名字。
	 * @property tasks 分块的任务一览。
	 */
	@MermaidGanttDslMarker
	class Section @PublishedApi internal constructor(
		val name: String,
	) : DslElement, Indentable, WithId {
		val tasks: MutableList<Task> = mutableListOf()
		override var indentContent: Boolean = false
		override val id: String get() = name

		override fun toString(): String {
			return when {
				tasks.isEmpty() -> "section $name"
				else -> "section $name\n${tasks.joinToText("\n").let { if(indentContent) it.prependIndent(indent) else it }}"
			}
		}
	}

	/**
	 * Mermaid甘特图的任务。
	 * @property name 任务的名字。
	 * @property alias （可选项）任务的别名。
	 * @property isCrit 是否是紧急的。默认为否。
	 * @property initTime （可选项）起始时间。
	 * @property finishTime （可选项）结束时间。
	 */
	@MermaidGanttDslMarker
	class Task @PublishedApi internal constructor(
		val name: String, var status: TaskStatus = TaskStatus.Todo,
	) : DslElement, WithId {
		var alias: String? = null
		var isCrit: Boolean = false
		var initTime: String? = null //LocalDate format or "after $alias" format
		var finishTime: String? = null //LocalDate format or Duration format
		override val id: String get() = name

		override fun toString(): String {
			val critSnippet = if(isCrit) "crit" else ""
			val statusSnippet = status.text
			return "$name: ${arrayOf(critSnippet, statusSnippet, alias, initTime, finishTime).joinToText()}"
		}
	}

	/**
	 * Mermaid甘特图任务的状态。
	 */
	@MermaidGanttDslMarker
	enum class TaskStatus(internal val text: String) {
		Todo(""), Done("done"), Active("active")
	}
}
