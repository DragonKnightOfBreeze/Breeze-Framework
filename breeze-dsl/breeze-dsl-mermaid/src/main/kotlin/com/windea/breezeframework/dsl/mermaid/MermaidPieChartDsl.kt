@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.dsl.mermaid

import com.windea.breezeframework.core.annotations.api.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.MermaidConfig.indent
import com.windea.breezeframework.dsl.mermaid.MermaidConfig.quote

//NOTE unstable raw api

//region top annotations and interfaces
/**Mermaid饼图的Dsl。*/
@ReferenceApi("[Mermaid Pie Chart](https://mermaidjs.github.io/#/pie)")
@DslMarker
@MustBeDocumented
internal annotation class MermaidPieChartDsl

/**Mermaid饼图。*/
@MermaidPieChartDsl
class MermaidPieChart @PublishedApi internal constructor() : Mermaid(), MermaidPieChartDslEntry, CanIndent, CanSplit {
	var title: MermaidPieChartTitle? = null
	override val sections: MutableSet<MermaidPieChartSection> = mutableSetOf()
	
	override var indentContent: Boolean = true
	override var splitContent: Boolean = false
	
	override fun toString(): String {
		val contentSnippet = arrayOf(
			title.toStringOrEmpty(),
			toContentString()
		).filterNotEmpty().joinToStringOrEmpty(split).applyIndent(indent)
		return "pie\n$contentSnippet"
	}
}
//endregion

//region dsl interfaces
/**Mermaid饼图Dsl的入口。*/
@MermaidPieChartDsl
interface MermaidPieChartDslEntry : MermaidDslEntry {
	val sections: MutableSet<MermaidPieChartSection>
	
	fun toContentString(): String {
		return sections.joinToStringOrEmpty("\n")
	}
}

/**Mermaid饼图Dsl的元素。*/
@MermaidPieChartDsl
interface MermaidPieChartDslElement : MermaidDslElement
//endregion

//region dsl elements
/**Mermaid饼图标题。*/
@MermaidPieChartDsl
class MermaidPieChartTitle @PublishedApi internal constructor(
	val text: String
) : MermaidPieChartDslElement {
	override fun toString(): String {
		return "title $text"
	}
}

/**Mermaid饼图部分。*/
@MermaidPieChartDsl
class MermaidPieChartSection @PublishedApi internal constructor(
	val key: String,
	val value: Number
) : MermaidPieChartDslElement, WithUniqueId {
	override val id: String get() = key
	
	override fun equals(other: Any?) = equalsBySelectId(this, other) { id }
	
	override fun hashCode() = hashCodeBySelectId(this) { id }
	
	override fun toString(): String {
		return "${key.wrapQuote(quote)}: $value"
	}
}
//endregion

//region build extensions
@MermaidPieChartDsl
inline fun mermaidPieChart(block: MermaidPieChart.() -> Unit) =
	MermaidPieChart().also { it.block() }

@MermaidPieChartDsl
inline fun MermaidPieChart.title(text: String) =
	MermaidPieChartTitle(text).also { title = it }

@MermaidPieChartDsl
inline fun MermaidPieChartDslEntry.section(key: String, value: Number) =
	MermaidPieChartSection(key, value).also { sections += it }
//endregion
