@file:Reference("[Mermaid Gantt Diagram](https://mermaidjs.github.io/#/gantt)")
@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.dsl.mermaid

import com.windea.breezeframework.core.annotations.marks.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.MermaidConfig.indent
import java.time.*

//REGION Dsl annotations

@DslMarker
internal annotation class MermaidGanttDsl

//REGION Dsl elements & build functions

/**构建Mermaid甘特图。*/
@MermaidGanttDsl
fun mermaidGantt(builder: MermaidGantt.() -> Unit) = MermaidGantt().also { it.builder() }

/**Mermaid甘特图Dsl的元素。*/
@MermaidGanttDsl
interface MermaidGanttDslElement : MermaidDslElement

/**Mermaid甘特图。*/
@MermaidGanttDsl
class MermaidGantt @PublishedApi internal constructor() : MermaidGanttDslElement, CanIndentContent, Mermaid {
	var title: MermaidGanttTitle? = null
	var dataFormat: MermaidGanttDateFormat? = null
	val sections: MutableList<MermaidGanttSection> = mutableListOf()
	
	override var indentContent: Boolean = true
	
	override fun toString(): String {
		val contentSnippet = arrayOf(
			arrayOf(title, dataFormat).filterNotNull().joinToString("\n"),
			sections.joinToString("\n\n")
		).filterNotEmpty().joinToString("\n\n")
		val indentedSnippet = if(indentContent) contentSnippet.prependIndent(indent) else contentSnippet
		return "gantt\n$indentedSnippet"
	}
	
	
	@MermaidGanttDsl
	inline fun section(name: String, builder: MermaidGanttSection.() -> Unit) =
		MermaidGanttSection(name).also { it.builder() }.also { sections += it }
}

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
) : MermaidGanttDslElement, CanIndentContent {
	val tasks: MutableList<MermaidGanttTask> = mutableListOf()
	
	override var indentContent: Boolean = false
	
	override fun toString(): String {
		//trim "\n" if no tasks
		if(tasks.isEmpty()) return "section $name"
		val contentSnippet = tasks.joinToString("\n")
		val indentedSnippet = if(indentContent) contentSnippet.prependIndent(indent) else contentSnippet
		return "section $name\n$indentedSnippet"
	}
	
	
	@MermaidGanttDsl
	inline fun task(name: String, isCrit: Boolean = false, status: MermaidGanttTaskStatus = MermaidGanttTaskStatus.ToDo) =
		MermaidGanttTask(name, isCrit, status).also { tasks += it }
}

/**Mermaid甘特图任务。*/
@MermaidGanttDsl
class MermaidGanttTask @PublishedApi internal constructor(
	val name: String,
	val isCrit: Boolean = false,
	val status: MermaidGanttTaskStatus = MermaidGanttTaskStatus.ToDo
) : MermaidGanttDslElement {
	var alias: String? = null
	//LocalDate format or "after $alias" format
	var initialTime: String? = null
	//LocalDate format or Duration format
	var finishTime: String? = null
	
	override fun toString(): String {
		val critSnippet = if(isCrit) "crit" else null
		val statusSnippet = status.text
		val paramsSnippet = arrayOf(critSnippet, statusSnippet, alias, initialTime, finishTime)
			.filterNotNull().joinToString()
		return "$name: $paramsSnippet"
	}
	
	
	@MermaidGanttDsl
	inline infix fun alias(alias: String) = this.also { it.alias = alias }
	
	@MermaidGanttDsl
	inline infix fun initialAt(time: String) = this.also { it.initialTime = time }
	
	@MermaidGanttDsl
	inline infix fun finishAt(time: String) = this.also { it.finishTime = time }
	
	@MermaidGanttDsl
	inline infix fun initialAt(time: LocalDate) = this.also { it.initialTime = time.toString() }
	
	@MermaidGanttDsl
	inline infix fun finishAt(time: LocalDate) = this.also { it.finishTime = time.toString() }
	
	//LocalDateTime format causes error
	
	@MermaidGanttDsl
	inline infix fun previousTask(taskId: String) = this.also { it.initialTime = "after $taskId" }
	
	@MermaidGanttDsl
	inline infix fun duration(duration: String) = this.also { it.finishTime = duration }
	
	@MermaidGanttDsl
	inline infix fun duration(duration: Duration) = this.also {
		//the output string format is "PTnHnMnS", but mermaid use "xd"/"xh"
		it.finishTime = duration.toString().drop(2).toLowerCase()
	}
}

//REGION Enumerations and constants

/**Mermaid甘特图任务的状态。*/
enum class MermaidGanttTaskStatus(val text: String?) {
	ToDo(null), Done("done"), Active("active")
}

