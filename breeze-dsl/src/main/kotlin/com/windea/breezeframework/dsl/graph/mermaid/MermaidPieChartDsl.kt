@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.dsl.graph.mermaid

import com.windea.breezeframework.core.annotations.api.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.core.interfaces.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.graph.mermaid.MermaidConfig.indent
import com.windea.breezeframework.dsl.graph.mermaid.MermaidConfig.quote

//NOTE unstable raw api

//REGION top annotations and interfaces

@DslMarker
private annotation class MermaidPieChartDsl

/**Mermaid饼图。*/
@ReferenceApi("[Mermaid Pie Chart](https://mermaidjs.github.io/#/pie)")
@MermaidPieChartDsl
class MermaidPieChart @PublishedApi internal constructor() : Mermaid(), MermaidPieChartDslEntry, IndentContent {
	override val parts: MutableSet<MermaidPieChartPart> = mutableSetOf()
	
	override var indentContent: Boolean = true
	
	override fun toString(): String {
		val contentSnippet = toContentString()
			.where(indentContent) { it.prependIndent(indent) }
		return "pie\n$contentSnippet"
	}
}

//REGION dsl interfaces

/**Mermaid饼图Dsl的入口。*/
@MermaidPieChartDsl
interface MermaidPieChartDslEntry : MermaidDslEntry {
	val parts: MutableSet<MermaidPieChartPart>
	
	fun toContentString(): String {
		return parts.joinToStringOrEmpty("\n")
	}
}

/**Mermaid饼图Dsl的元素。*/
@MermaidPieChartDsl
interface MermaidPieChartDslElement : MermaidDslElement

//REGION dsl elements

/**Mermaid饼图部分。*/
@MermaidPieChartDsl
class MermaidPieChartPart @PublishedApi internal constructor(
	val key: String,
	val value: Number
) : MermaidPieChartDslElement, CanEqual {
	override fun equals(other: Any?) = equalsBySelect(this, other) { arrayOf(key) }
	
	override fun hashCode() = hashCodeBySelect(this) { arrayOf(key) }
	
	override fun toString(): String {
		return "${key.wrapQuote(quote)}: $value"
	}
}

//REGION build extensions

@MermaidPieChartDsl
inline fun mermaidPieChart(builder: MermaidPieChart.() -> Unit) =
	MermaidPieChart().also { it.builder() }

@MermaidPieChartDsl
inline fun MermaidPieChartDslEntry.part(key: String, value: Number) =
	MermaidPieChartPart(key, value).also { parts += it }
