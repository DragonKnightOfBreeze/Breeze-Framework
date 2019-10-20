@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.dsl.mermaid

import com.windea.breezeframework.core.annotations.api.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.MermaidConfig.indent
import java.time.*

//REGION Top annotations and interfaces

@DslMarker
private annotation class MermaidGanttDsl

/**Mermaid甘特图。*/
@ReferenceApi("[Mermaid Gantt Diagram](https://mermaidjs.github.io/#/gantt)")
@MermaidGanttDsl
class MermaidGantt @PublishedApi internal constructor() : Mermaid(), IndentContent, WithBlock<MermaidGanttSection> {
	var title: MermaidGanttTitle? = null
	var dateFormat: MermaidGanttDateFormat? = null
	val sections: MutableList<MermaidGanttSection> = mutableListOf()
	
	override var indentContent: Boolean = true
	
	override fun toString(): String {
		val contentSnippet = arrayOf(
			arrayOf(title, dateFormat).filterNotNull().joinToStringOrEmpty("\n"),
			sections.joinToStringOrEmpty("\n\n")
		).filterNotEmpty().joinToStringOrEmpty("\n\n")
			.let { if(indentContent) it.prependIndent(indent) else it }
		return "gantt\n$contentSnippet"
	}
	
	@MermaidGanttDsl
	override fun String.invoke() = section(this)
	
	@MermaidGanttDsl
	override fun String.invoke(builder: MermaidGanttSection.() -> Unit) = section(this, builder)
}

//REGION Dsl elements

/**Mermaid甘特图Dsl的元素。*/
@MermaidGanttDsl
interface MermaidGanttDslElement : MermaidDslElement


/**Mermaid甘特图标题。*/
@MermaidGanttDsl
class MermaidGanttTitle @PublishedApi internal constructor(
	val name: String
) : MermaidGanttDslElement {
	override fun toString(): String {
		return "title $name"
	}
}

/**Mermaid甘特图日期格式。*/
@MermaidGanttDsl
class MermaidGanttDateFormat @PublishedApi internal constructor(
	val expression: String = "YYYY-MM-DD"
) : MermaidGanttDslElement {
	override fun toString(): String {
		return "dateFormat $expression"
	}
}

/**Mermaid甘特图章节。*/
@MermaidGanttDsl
class MermaidGanttSection @PublishedApi internal constructor(
	val name: String
) : MermaidGanttDslElement, IndentContent, WithText<MermaidGanttTask> {
	val tasks: MutableList<MermaidGanttTask> = mutableListOf()
	
	override var indentContent: Boolean = false
	
	override fun toString(): String {
		//trim "\n" if no tasks
		if(tasks.isEmpty()) return "section $name"
		val contentSnippet = tasks.joinToStringOrEmpty("\n")
			.let { if(indentContent) it.prependIndent(indent) else it }
		return "section $name\n$contentSnippet"
	}
	
	@MermaidGanttDsl
	override fun String.unaryPlus() = task(this)
}

/**Mermaid甘特图任务。*/
@MermaidGanttDsl
class MermaidGanttTask @PublishedApi internal constructor(
	val name: String,
	var status: MermaidGanttTaskStatus = MermaidGanttTaskStatus.ToDo
) : MermaidGanttDslElement {
	var alias: String? = null
	var isCrit: Boolean = false
	//LocalDate format or "after $alias" format
	var initTime: String? = null
	//LocalDate format or Duration format
	var finishTime: String? = null
	
	override fun toString(): String {
		val critSnippet = if(isCrit) "crit" else null
		val statusSnippet = status.text
		val paramsSnippet = arrayOf(critSnippet, statusSnippet, alias, initTime, finishTime)
			.filterNotNull().joinToStringOrEmpty()
		return "$name: $paramsSnippet"
	}
}

//REGION Enumerations and constants

/**Mermaid甘特图任务的状态。*/
@MermaidGanttDsl
enum class MermaidGanttTaskStatus(val text: String?) {
	ToDo(null), Done("done"), Active("active")
}

//REGION Build extensions

@MermaidGanttDsl
inline fun mermaidGantt(builder: MermaidGantt.() -> Unit) =
	MermaidGantt().also { it.builder() }


@MermaidGanttDsl
inline fun MermaidGantt.section(name: String) =
	MermaidGanttSection(name).also { sections += it }

@MermaidGanttDsl
inline fun MermaidGantt.section(name: String, builder: MermaidGanttSection.() -> Unit) =
	MermaidGanttSection(name).also { it.builder() }.also { sections += it }

@MermaidGanttDsl
inline fun MermaidGanttSection.task(name: String, status: MermaidGanttTaskStatus = MermaidGanttTaskStatus.ToDo) =
	MermaidGanttTask(name, status).also { tasks += it }

@MermaidGanttDsl
inline infix fun MermaidGanttTask.alias(alias: String) =
	this.also { it.alias = alias }

@MermaidGanttDsl
inline infix fun MermaidGanttTask.status(status: MermaidGanttTaskStatus) =
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
inline infix fun MermaidGanttTask.after(taskId: String) =
	this.also { it.initTime = "after $taskId" }

@MermaidGanttDsl
inline infix fun MermaidGanttTask.duration(duration: String) =
	this.also { it.finishTime = duration }

//the output string format is "PTnHnMnS", but mermaid use "xd"/"xh"
@MermaidGanttDsl
inline infix fun MermaidGanttTask.duration(duration: Duration) =
	this.also { it.finishTime = duration.toString().drop(2).toLowerCase() }
