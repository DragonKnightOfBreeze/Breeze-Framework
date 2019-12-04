@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.dsl.graph.mermaid

import com.windea.breezeframework.core.annotations.api.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.graph.mermaid.MermaidConfig.indent
import java.time.*

//region top annotations and interfaces
/**Mermaid甘特图的Dsl。*/
@ReferenceApi("[Mermaid Gantt Diagram](https://mermaidjs.github.io/#/gantt)")
@DslMarker
@MustBeDocumented
internal annotation class MermaidGanttDsl

/**Mermaid甘特图。*/
@MermaidGanttDsl
class MermaidGantt @PublishedApi internal constructor() : Mermaid(), MermaidGanttDslEntry, CanIndent, CanSplit {
	var title: MermaidGanttTitle? = null
	var dateFormat: MermaidGanttDateFormat? = null
	override val sections: MutableList<MermaidGanttSection> = mutableListOf()
	
	override var indentContent: Boolean = true
	override var splitContent: Boolean = false
	
	override fun toString(): String {
		val contentSnippet = arrayOf(
			title.toStringOrEmpty(),
			dateFormat.toStringOrEmpty(),
			toContentString()
		).filterNotEmpty().joinToStringOrEmpty(split).applyIndent(indent)
		return "gantt\n$contentSnippet"
	}
}
//endregion

//region dsl interfaces
/**Mermaid甘特图Dsl的入口。*/
@MermaidGanttDsl
interface MermaidGanttDslEntry : MermaidDslEntry, CanSplit {
	val sections: MutableList<MermaidGanttSection>
	
	fun toContentString(): String {
		return sections.joinToStringOrEmpty(split)
	}
}

/**Mermaid甘特图Dsl的元素。*/
@MermaidGanttDsl
interface MermaidGanttDslElement : MermaidDslElement
//endregion

//region dsl elements
/**Mermaid甘特图标题。*/
@MermaidGanttDsl
class MermaidGanttTitle @PublishedApi internal constructor(
	val text: String
) : MermaidGanttDslElement {
	override fun toString(): String {
		return "title $text"
	}
}

/**Mermaid甘特图日期格式。*/
@MermaidGanttDsl
class MermaidGanttDateFormat @PublishedApi internal constructor(
	val expression: String
) : MermaidGanttDslElement {
	override fun toString(): String {
		return "dateFormat $expression"
	}
}

/**Mermaid甘特图部分。*/
@MermaidGanttDsl
class MermaidGanttSection @PublishedApi internal constructor(
	val name: String
) : MermaidGanttDslElement, CanIndent, WithId {
	val tasks: MutableList<MermaidGanttTask> = mutableListOf()
	
	override var indentContent: Boolean = false
	
	override val id: String get() = name
	
	override fun toString(): String {
		if(tasks.isEmpty()) return "section $name"
		
		val contentSnippet = tasks.joinToStringOrEmpty("\n").applyIndent(indent)
		return "section $name\n$contentSnippet"
	}
}

/**Mermaid甘特图任务。*/
@MermaidGanttDsl
class MermaidGanttTask @PublishedApi internal constructor(
	val name: String,
	var status: Status = Status.ToDo
) : MermaidGanttDslElement, WithId {
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
		val paramsSnippet = arrayOf(critSnippet, statusSnippet, alias, initTime, finishTime).filterNotNull().joinToStringOrEmpty()
		return "$name: $paramsSnippet"
	}
	
	/**Mermaid甘特图任务的状态。*/
	@MermaidGanttDsl
	enum class Status(val text: String?) {
		ToDo(null), Done("done"), Active("active")
	}
}
//endregion

//region build extensions
@MermaidGanttDsl
inline fun mermaidGantt(block: MermaidGantt.() -> Unit) =
	MermaidGantt().also { it.block() }

@MermaidGanttDsl
inline fun MermaidGantt.title(text: String) =
	MermaidGanttTitle(text).also { title = it }

@MermaidGanttDsl
inline fun MermaidGantt.dateFormat(expression: String) =
	MermaidGanttDateFormat(expression).also { dateFormat = it }

@MermaidGanttDsl
inline fun MermaidGanttDslEntry.section(name: String, block: MermaidGanttSection.() -> Unit = {}) =
	MermaidGanttSection(name).also { it.block() }.also { sections += it }

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
