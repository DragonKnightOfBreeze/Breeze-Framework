/*******************************************************************************
 * Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
 * Breeze is blowing...
 ******************************************************************************/

package com.windea.breezeframework.dsl.mermaid.gantt

import com.windea.breezeframework.dsl.mermaid.gantt.MermaidGanttDslDefinitions.*
import java.time.*

@MermaidGanttDslMarker
inline fun mermaidGanttDsl(block: MermaidGanttDsl.() -> Unit) = MermaidGanttDsl().apply(block)


@MermaidGanttDslMarker
fun MermaidGanttDsl.title(text: String) = Title(text).also { title = it }

@MermaidGanttDslMarker
fun MermaidGanttDsl.dateFormat(expression: String) = DateFormat(expression).also { dateFormat = it }

@MermaidGanttDslMarker
inline fun MermaidGanttDsl.section(
	name: String,
	block: Section.() -> Unit = {}
) = Section(name).apply(block).also { sections += it }

@MermaidGanttDslMarker
fun Section.task(name: String, status: TaskStatus = TaskStatus.Todo) = Task(name, status).also { tasks += it }

@MermaidGanttDslMarker
infix fun Task.alias(alias: String) = apply { this.alias = alias }

@MermaidGanttDslMarker
infix fun Task.status(status: TaskStatus) = apply { this.status = status }

@MermaidGanttDslMarker
infix fun Task.crit(isCrit: Boolean) = apply { this.isCrit = isCrit }

@MermaidGanttDslMarker
infix fun Task.initAt(time: String) = apply { initTime = time }

@MermaidGanttDslMarker
infix fun Task.finishAt(time: String) = apply { finishTime = time }

//LocalDateTime format causes error

@MermaidGanttDslMarker
infix fun Task.initAt(time: LocalDate) = apply { initTime = time.toString() }

@MermaidGanttDslMarker
infix fun Task.finishAt(time: LocalDate) = apply { finishTime = time.toString() }

@MermaidGanttDslMarker
infix fun Task.after(taskName: String) = apply { initTime = "after $taskName" }

@MermaidGanttDslMarker
infix fun Task.duration(duration: String) = apply { finishTime = duration }

//the output string format is "PTnHnMnS", but mermaid use "xd"/"xh"

@MermaidGanttDslMarker
infix fun Task.duration(duration: Duration) = apply { finishTime = duration.toString().drop(2).toLowerCase() }
