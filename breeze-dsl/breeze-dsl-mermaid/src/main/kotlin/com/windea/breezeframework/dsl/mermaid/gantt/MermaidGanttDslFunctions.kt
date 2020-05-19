package com.windea.breezeframework.dsl.mermaid.gantt

import com.windea.breezeframework.dsl.mermaid.gantt.MermaidGantt.*
import java.time.*

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
	Task(name, status).also { tasks += it }

@MermaidGanttDsl
infix fun Task.alias(alias:String) =
	apply { this.alias = alias }

@MermaidGanttDsl
infix fun Task.status(status:TaskStatus) =
	apply { this.status = status }

@MermaidGanttDsl
infix fun Task.crit(isCrit:Boolean) =
	apply { this.isCrit = isCrit }

@MermaidGanttDsl
infix fun Task.initAt(time:String) =
	apply { initTime = time }

@MermaidGanttDsl
infix fun Task.finishAt(time:String) =
	apply { finishTime = time }

//LocalDateTime format causes error

@MermaidGanttDsl
infix fun Task.initAt(time:LocalDate) =
	apply { initTime = time.toString() }

@MermaidGanttDsl
infix fun Task.finishAt(time:LocalDate) =
	apply { finishTime = time.toString() }

@MermaidGanttDsl
infix fun Task.after(taskName:String) =
	apply { initTime = "after $taskName" }

@MermaidGanttDsl
infix fun Task.duration(duration:String) =
	apply { finishTime = duration }

//the output string format is "PTnHnMnS", but mermaid use "xd"/"xh"

@MermaidGanttDsl
infix fun Task.duration(duration:Duration) =
	apply { finishTime = duration.toString().drop(2).toLowerCase() }
