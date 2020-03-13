@file:Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS", "INLINE_CLASS_NOT_TOP_LEVEL")


package com.windea.breezeframework.dsl.mermaid

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.Mermaid.Companion.config
import com.windea.breezeframework.dsl.mermaid.MermaidGantt.*
import java.time.*

/**
 * Mermaid甘特图的Dsl。
 * 参见：[Mermaid Gantt Diagram](https://mermaidjs.github.io/#/gantt)
 */
@DslMarker
@MustBeDocumented
annotation class MermaidGanttDsl

/**
 * Mermaid甘特图的入口。
 * 参见：[Mermaid Gantt Diagram](https://mermaidjs.github.io/#/gantt)
 * @property dateFormat 图标的日期格式化方式。
 * @property sections 图表的分区一览。
 */
@MermaidGanttDsl
interface MermaidGanttEntry : MermaidEntry, CanSplitLine {
	var dateFormat:DateFormat?
	val sections:MutableList<Section>

	override fun contentString() = buildString {
		if(dateFormat != null) append(dateFormat).append(splitSeparator)
		appendJoin(sections, splitSeparator)
	}
}

/**
 * Mermaid甘特图的元素。
 * 参见：[Mermaid Gantt Diagram](https://mermaidjs.github.io/#/gantt)
 */
@MermaidGanttDsl
interface MermaidGanttElement : MermaidElement

/**
 * Mermaid甘特图。
 * 参见：[Mermaid Gantt Diagram](https://mermaidjs.github.io/#/gantt)
 */
@MermaidGanttDsl
interface MermaidGantt {
	/**
	 * Mermaid甘特图的文档。
	 * @property title （可选项）图表的标题。
	 * @property dateFormat （可选项）图标的日期格式化方式。
	 */
	class Document @PublishedApi internal constructor() : Mermaid.Document(), MermaidGanttEntry, CanIndent, CanSplitLine {
		var title:Title? = null
		override var dateFormat:DateFormat? = null
		override val sections:MutableList<Section> = mutableListOf()
		override var indentContent:Boolean = true
		override var splitContent:Boolean = false

		override fun toString() = "gantt$ls${"${title.typing { "$it$ls" }}${contentString()}".doIndent(config.indent)}"
	}

	/**
	 * Mermaid甘特图的标题。
	 * @property text 标题的文本。
	 */
	@MermaidGanttDsl
	inline class Title @PublishedApi internal constructor(val text:String) : MermaidGanttElement {
		override fun toString() = "title $text"
	}

	/**
	 * Mermaid甘特图的日期格式。
	 * @property expression 日期的表达式。默认为"YYYY-MM-DD"。
	 */
	@MermaidGanttDsl
	inline class DateFormat @PublishedApi internal constructor(val expression:String) : MermaidGanttElement {
		override fun toString() = "dateFormat $expression"
	}

	/**
	 * Mermaid甘特图的分块。
	 * @property name 分块的名字。
	 * @property tasks 分块的任务一览。
	 */
	@MermaidGanttDsl
	class Section @PublishedApi internal constructor(
		val name:String
	) : MermaidGanttElement, CanIndent, WithId {
		val tasks:MutableList<MermaidGanttTask> = mutableListOf()
		override var indentContent:Boolean = false
		override val id:String get() = name

		override fun toString() = "section $name${tasks.ifNotEmpty { "$ls${it.joinToString(ls).doIndent(config.indent)}" }}"
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
	class MermaidGanttTask @PublishedApi internal constructor(
		val name:String, var status:TaskStatus = TaskStatus.Todo
	) : MermaidGanttElement, WithId {
		var alias:String? = null
		var isCrit:Boolean = false
		var initTime:String? = null //LocalDate format or "after $alias" format
		var finishTime:String? = null //LocalDate format or Duration format
		override val id:String get() = name

		override fun toString() = "$name: ${arrayOf(isCrit.typing("crit"), status.text, alias, initTime, finishTime).typingAll()}"
	}

	/**Mermaid甘特图任务的状态。*/
	@MermaidGanttDsl
	enum class TaskStatus(internal val text:String) {
		Todo(""), Done("done"), Active("active")
	}
}


@MermaidGanttDsl
inline fun mermaidGantt(block:Document.() -> Unit) = Document().apply(block)

@MermaidGanttDsl
fun Document.title(text:String) =
	Title(text).also { title = it }

@MermaidGanttDsl
fun Document.dateFormat(expression:String) =
	DateFormat(expression).also { dateFormat = it }

@MermaidGanttDsl
inline fun Document.section(name:String, block:Section.() -> Unit = {}) =
	Section(name).apply(block).also { sections += it }

@MermaidGanttDsl
fun Section.task(name:String, status:TaskStatus = TaskStatus.Todo) =
	MermaidGanttTask(name, status).also { tasks += it }

@MermaidGanttDsl
infix fun MermaidGanttTask.alias(alias:String) =
	apply { this.alias = alias }

@MermaidGanttDsl
infix fun MermaidGanttTask.status(status:TaskStatus) =
	apply { this.status = status }

@MermaidGanttDsl
infix fun MermaidGanttTask.crit(isCrit:Boolean) =
	apply { this.isCrit = isCrit }

@MermaidGanttDsl
infix fun MermaidGanttTask.initAt(time:String) =
	apply { initTime = time }

@MermaidGanttDsl
infix fun MermaidGanttTask.finishAt(time:String) =
	apply { finishTime = time }

//LocalDateTime format causes error

@MermaidGanttDsl
infix fun MermaidGanttTask.initAt(time:LocalDate) =
	apply { initTime = time.toString() }

@MermaidGanttDsl
infix fun MermaidGanttTask.finishAt(time:LocalDate) =
	apply { finishTime = time.toString() }

@MermaidGanttDsl
infix fun MermaidGanttTask.after(taskName:String) =
	apply { initTime = "after $taskName" }

@MermaidGanttDsl
infix fun MermaidGanttTask.duration(duration:String) =
	apply { finishTime = duration }

//the output string format is "PTnHnMnS", but mermaid use "xd"/"xh"

@MermaidGanttDsl
infix fun MermaidGanttTask.duration(duration:Duration) =
	apply { finishTime = duration.toString().drop(2).toLowerCase() }
