// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("MermaidGanttDslExtensions")

package icu.windea.breezeframework.dsl.mermaid.gantt

import icu.windea.breezeframework.dsl.mermaid.gantt.MermaidGanttDsl.*
import java.time.*

/**
 * 开始构建[MermaidGanttDsl]。
 */
@MermaidGanttDslMarker
inline fun mermaidGanttDsl(block: DslDocument.() -> Unit): DslDocument {
	return DslDocument().apply(block)
}

@MermaidGanttDslMarker
fun DslDocument.title(text: String): Title {
	return Title(text).also { title = it }
}

@MermaidGanttDslMarker
fun DslDocument.dateFormat(expression: String): DateFormat {
	return DateFormat(expression).also { dateFormat = it }
}

@MermaidGanttDslMarker
inline fun DslDocument.section(name: String, block: Section.() -> Unit = {}): Section {
	return Section(name).apply(block).also { sections += it }
}

@MermaidGanttDslMarker
fun Section.task(name: String, status: TaskStatus = TaskStatus.Todo): Task {
	return Task(name, status).also { tasks += it }
}

@MermaidGanttDslMarker
infix fun Task.alias(alias: String): Task {
	return apply { this.alias = alias }
}

@MermaidGanttDslMarker
infix fun Task.status(status: TaskStatus): Task {
	return apply { this.status = status }
}

@MermaidGanttDslMarker
infix fun Task.crit(isCrit: Boolean): Task {
	return apply { this.isCrit = isCrit }
}

@MermaidGanttDslMarker
infix fun Task.initAt(time: String): Task {
	return apply { initTime = time }
}

@MermaidGanttDslMarker
infix fun Task.finishAt(time: String): Task {
	return apply { finishTime = time }
}

//LocalDateTime format causes error

@MermaidGanttDslMarker
infix fun Task.initAt(time: LocalDate): Task {
	return apply { initTime = time.toString() }
}

@MermaidGanttDslMarker
infix fun Task.finishAt(time: LocalDate): Task {
	return apply { finishTime = time.toString() }
}

@MermaidGanttDslMarker
infix fun Task.after(taskName: String): Task {
	return apply { initTime = "after $taskName" }
}

@MermaidGanttDslMarker
infix fun Task.duration(duration: String): Task {
	return apply { finishTime = duration }
}

//the output string format is "PTnHnMnS", but mermaid use "xd"/"xh"

@MermaidGanttDslMarker
infix fun Task.duration(duration: Duration): Task {
	return apply { finishTime = duration.toString().drop(2).toLowerCase() }
}
