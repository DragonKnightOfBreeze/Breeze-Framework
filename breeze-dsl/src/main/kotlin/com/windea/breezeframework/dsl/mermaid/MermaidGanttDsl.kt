@file:Reference("[Mermaid Gantt Diagram](https://mermaidjs.github.io/#/gantt)")
@file:Suppress("CanBePrimaryConstructorProperty", "NOTHING_TO_INLINE")

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
fun mermaidGantt(title: String, dataFormat: String = "YYYY-MM-DD", builder: MermaidGantt.() -> Unit) =
	MermaidGantt(title, dataFormat).also { it.builder() }

/**Mermaid甘特图Dsl的元素。*/
@MermaidGanttDsl
interface MermaidGanttDslElement : MermaidDslElement

/**Mermaid甘特图。*/
@MermaidGanttDsl
class MermaidGantt @PublishedApi internal constructor(
	title: String,
	dataFormat: String = "YYYY-MM-DD"
) : MermaidGanttDslElement, CanIndentContent, Mermaid {
	val title: String = title.replaceWithHtmlWrap() //NOTE do not ensure argument is valid
	val dataFormat: String = dataFormat //NOTE do not ensure argument is valid
	val sections: MutableList<MermaidGanttSection> = mutableListOf()
	
	override var indentContent: Boolean = true
	
	override fun toString(): String {
		val contentSnippet = arrayOf(
			"title $title\ndataFormat $dataFormat",
			sections.joinToString("\n\n")
		).filterNotEmpty().joinToString("\n\n")
		val indentedSnippet = if(indentContent) contentSnippet.prependIndent(indent) else contentSnippet
		return "gantt\n$indentedSnippet"
	}
	
	
	@MermaidGanttDsl
	inline fun section(name: String, builder: MermaidGanttSection.() -> Unit) =
		MermaidGanttSection(name).also { it.builder() }.also { sections += it }
}

/**Mermaid甘特图章节。*/
@MermaidGanttDsl
class MermaidGanttSection @PublishedApi internal constructor(
	name: String
) : MermaidGanttDslElement, CanIndentContent {
	val name: String = name.replaceWithHtmlWrap() //NOTE do not ensure argument is valid
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
	name: String,
	isCrit: Boolean = false,
	status: MermaidGanttTaskStatus = MermaidGanttTaskStatus.ToDo
) : MermaidGanttDslElement {
	val name: String = name.replaceWithHtmlWrap() //NOTE do not ensure argument is valid
	val isCrit: Boolean = isCrit
	val status: MermaidGanttTaskStatus = status
	
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
	inline infix fun previousTask(taskName: String) = this.also { it.initialTime = "after $taskName" }
	
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

