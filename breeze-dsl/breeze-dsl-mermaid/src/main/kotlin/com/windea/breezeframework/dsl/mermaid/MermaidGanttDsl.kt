@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.dsl.mermaid

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.Mermaid.Companion.config
import java.time.*

//region dsl top declarations
/**Mermaid甘特图的Dsl。*/
@Reference("[Mermaid Gantt Diagram](https://mermaidjs.github.io/#/gantt)")
@DslMarker
@MustBeDocumented
internal annotation class MermaidGanttDsl

/**Mermaid甘特图。*/
@Reference("[Mermaid Gantt Diagram](https://mermaidjs.github.io/#/gantt)")
@MermaidGanttDsl
class MermaidGantt @PublishedApi internal constructor() : Mermaid(), MermaidGanttEntry, CanIndent, CanSplitLine {
	var title: MermaidGanttTitle? = null
	var dateFormat: MermaidGanttDateFormat? = null
	override val sections: MutableList<MermaidGanttSection> = mutableListOf()

	override var indentContent: Boolean = true
	override var splitContent: Boolean = false

	override fun toString(): String {
		val contentSnippet = listOfNotNull(
			title?.toString(),
			dateFormat?.toString(),
			contentString().orNull()
		).joinToString(splitSeparator).applyIndent(config.indent)
		return "gantt\n$contentSnippet"
	}
}
//endregion

//region dsl declarations
/**Mermaid甘特图Dsl的入口。*/
@MermaidGanttDsl
interface MermaidGanttEntry : MermaidEntry, CanSplitLine {
	val sections: MutableList<MermaidGanttSection>

	override fun contentString(): String {
		return sections.joinToString(splitSeparator)
	}
}

/**Mermaid甘特图Dsl的元素。*/
@MermaidGanttDsl
interface MermaidGanttElement : MermaidElement
//endregion

//region dsl elements
/**Mermaid甘特图标题。*/
@MermaidGanttDsl
class MermaidGanttTitle @PublishedApi internal constructor(
	val text: String
) : MermaidGanttElement {
	override fun toString(): String {
		return "title $text"
	}
}

/**Mermaid甘特图日期格式。*/
@MermaidGanttDsl
class MermaidGanttDateFormat @PublishedApi internal constructor(
	val expression: String
) : MermaidGanttElement {
	override fun toString(): String {
		return "dateFormat $expression"
	}
}

/**Mermaid甘特图部分。*/
@MermaidGanttDsl
class MermaidGanttSection @PublishedApi internal constructor(
	val name: String
) : MermaidGanttElement, CanIndent, WithId {
	val tasks: MutableList<MermaidGanttTask> = mutableListOf()

	override var indentContent: Boolean = false

	override val id: String get() = name

	override fun toString(): String {
		if(tasks.isEmpty()) return "section $name"

		val contentSnippet = tasks.joinToString("\n").applyIndent(config.indent)
		return "section $name\n$contentSnippet"
	}
}

/**Mermaid甘特图任务。*/
@MermaidGanttDsl
class MermaidGanttTask @PublishedApi internal constructor(
	val name: String,
	var status: Status = Status.ToDo
) : MermaidGanttElement, WithId {
	var alias: String? = null
	var isCrit: Boolean = false

	//LocalDate format or "after $alias" format
	var initTime: String? = null

	//LocalDate format or Duration format
	var finishTime: String? = null

	override val id: String get() = name

	override fun toString(): String {
		val critSnippet = if(isCrit) "crit" else null
		val statusSnippet = status.text
		val paramsSnippet = listOfNotNull(critSnippet, statusSnippet, alias, initTime, finishTime).joinToString()
		return "$name: $paramsSnippet"
	}

	/**Mermaid甘特图任务的状态。*/
	@MermaidGanttDsl
	enum class Status(val text: String?) {
		ToDo(null), Done("done"), Active("active")
	}
}
//endregion

//region dsl build extensions
@MermaidGanttDsl
inline fun mermaidGantt(block: MermaidGantt.() -> Unit) =
	MermaidGantt().apply(block)

@MermaidGanttDsl
inline fun MermaidGantt.title(text: String) =
	MermaidGanttTitle(text).also { title = it }

@MermaidGanttDsl
inline fun MermaidGantt.dateFormat(expression: String) =
	MermaidGanttDateFormat(expression).also { dateFormat = it }

@MermaidGanttDsl
inline fun MermaidGanttEntry.section(name: String, block: MermaidGanttSection.() -> Unit = {}) =
	MermaidGanttSection(name).apply(block).also { sections += it }

@MermaidGanttDsl
inline fun MermaidGanttSection.task(name: String, status: MermaidGanttTask.Status = MermaidGanttTask.Status.ToDo) =
	MermaidGanttTask(name, status).also { tasks += it }

@MermaidGanttDsl
inline infix fun MermaidGanttTask.alias(alias: String) =
	this.also { it.alias = alias }

@MermaidGanttDsl
inline infix fun MermaidGanttTask.status(status: MermaidGanttTask.Status) =
	this.also { it.status = status }

@MermaidGanttDsl
inline infix fun MermaidGanttTask.crit(isCrit: Boolean) =
	this.also { it.isCrit = isCrit }

@MermaidGanttDsl
inline infix fun MermaidGanttTask.initAt(time: String) =
	this.also { it.initTime = time }

@MermaidGanttDsl
inline infix fun MermaidGanttTask.finishAt(time: String) =
	this.also { it.finishTime = time }

//LocalDateTime format causes error
@MermaidGanttDsl
inline infix fun MermaidGanttTask.initAt(time: LocalDate) =
	this.also { it.initTime = time.toString() }

@MermaidGanttDsl
inline infix fun MermaidGanttTask.finishAt(time: LocalDate) =
	this.also { it.finishTime = time.toString() }

@MermaidGanttDsl
inline infix fun MermaidGanttTask.after(taskName: String) =
	this.also { it.initTime = "after $taskName" }

@MermaidGanttDsl
inline infix fun MermaidGanttTask.duration(duration: String) =
	this.also { it.finishTime = duration }

//the output string format is "PTnHnMnS", but mermaid use "xd"/"xh"
@MermaidGanttDsl
inline infix fun MermaidGanttTask.duration(duration: Duration) =
	this.also { it.finishTime = duration.toString().drop(2).toLowerCase() }
//endregion
