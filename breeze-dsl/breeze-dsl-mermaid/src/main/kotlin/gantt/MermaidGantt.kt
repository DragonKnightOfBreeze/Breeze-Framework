package com.windea.breezeframework.dsl.mermaid.gantt

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.DslConstants.ls
import com.windea.breezeframework.dsl.mermaid.*
import com.windea.breezeframework.dsl.mermaid.Mermaid.Companion.config

/**
 * Mermaid甘特图。
 */
@MermaidGanttDsl
interface MermaidGantt {
	/**
	 * Mermaid甘特图的文档。
	 * @property title （可选项）图表的标题。
	 * @property dateFormat （可选项）图标的日期格式化方式。
	 */
	@MermaidGanttDsl
	class Document @PublishedApi internal constructor() : Mermaid.Document(), IDslEntry, Indentable, Splitable {
		var title:Title? = null
		var dateFormat:DateFormat? = null
		override val sections:MutableList<Section> = mutableListOf()
		override var indentContent:Boolean = true
		override var splitContent:Boolean = false

		override fun toString():String {
			val contentSnippet = arrayOf(title, dateFormat, contentString()).doSplit().doIndent(config.indent)
			return "gantt$ls$contentSnippet"
		}
	}


	/**
	 * Mermaid甘特图领域特定语言的入口。
	 * @property dateFormat 图标的日期格式化方式。
	 * @property sections 图表的分区一览。
	 */
	@MermaidGanttDsl
	interface IDslEntry : Mermaid.IDslEntry, Splitable {
		val sections:MutableList<Section>

		override fun contentString():String {
			return sections.typingAll(ls)
		}
	}

	/**
	 * Mermaid甘特图领域特定语言的元素。
	 */
	@MermaidGanttDsl
	interface IDslElement : Mermaid.IDslElement

	/**
	 * Mermaid甘特图的标题。
	 * @property text 标题的文本。
	 */
	@MermaidGanttDsl
	class Title @PublishedApi internal constructor(
		val text:String
	) : IDslElement {
		override fun toString():String {
			return "title $text"
		}
	}

	/**
	 * Mermaid甘特图的日期格式。
	 * @property expression 日期的表达式。默认为"YYYY-MM-DD"。
	 */
	@MermaidGanttDsl
	class DateFormat @PublishedApi internal constructor(
		val expression:String
	) : IDslElement {
		override fun toString():String {
			return "dateFormat $expression"
		}
	}

	/**
	 * Mermaid甘特图的分块。
	 * @property name 分块的名字。
	 * @property tasks 分块的任务一览。
	 */
	@MermaidGanttDsl
	class Section @PublishedApi internal constructor(
		val name:String
	) : IDslElement, Indentable, WithId {
		val tasks:MutableList<Task> = mutableListOf()
		override var indentContent:Boolean = false
		override val id:String get() = name

		override fun toString():String {
			val tasksSnippet = tasks.ifNotEmpty { "$ls${it.typingAll(ls).doIndent(config.indent)}" }
			return "section $name$tasksSnippet"
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
	@MermaidGanttDsl
	class Task @PublishedApi internal constructor(
		val name:String, var status:TaskStatus = TaskStatus.Todo
	) : IDslElement, WithId {
		var alias:String? = null
		var isCrit:Boolean = false
		var initTime:String? = null //LocalDate format or "after $alias" format
		var finishTime:String? = null //LocalDate format or Duration format
		override val id:String get() = name

		override fun toString():String {
			val critSnippet = if(isCrit) "crit" else ""
			val statusSnippet = status.text
			return "$name: ${arrayOf(critSnippet, statusSnippet, alias, initTime, finishTime).typingAll()}"
		}
	}


	/**Mermaid甘特图任务的状态。*/
	@MermaidGanttDsl
	enum class TaskStatus(internal val text:String) {
		Todo(""), Done("done"), Active("active")
	}
}
