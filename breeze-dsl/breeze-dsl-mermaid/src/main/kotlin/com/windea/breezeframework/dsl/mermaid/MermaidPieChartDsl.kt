@file:Suppress("unused", "NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS", "INLINE_CLASS_NOT_TOP_LEVEL")

package com.windea.breezeframework.dsl.mermaid

import com.windea.breezeframework.core.constants.text.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.MermaidPieChart.*

/**
 * Mermaid饼图的Dsl。
 * 参见：[Mermaid Pie Chart](https://mermaidjs.github.io/#/pie)
 */
@DslMarker
@MustBeDocumented
annotation class MermaidPieChartDsl

/**
 * Mermaid饼图。
 * 参见：[Mermaid Pie Chart](https://mermaidjs.github.io/#/pie)
 * @property title （可选项）Mermaid饼图的标题。
 */
@MermaidPieChartDsl
class MermaidPieChart @PublishedApi internal constructor() : Mermaid(), MermaidPieChartEntry, CanIndent {
	var title: Title? = null
	override val sections: MutableSet<Section> = mutableSetOf()
	override var indentContent: Boolean = true

	override fun toString() = buildString {
		appendln("pie")
		if(title != null) append(config.indent).appendln(title)
		append(contentString().applyIndent(config.indent))
	}

	/**
	 * Mermaid饼图的标题。
	 * @property text 标题的文本。
	 */
	@MermaidPieChartDsl
	inline class Title @PublishedApi internal constructor(val text: String) : MermaidPieChartElement {
		override fun toString() = "title $text"
	}

	/**
	 * Mermaid饼图的分区。
	 * @property key 分区的键。不能包含双引号，也不能进行转义。
	 * @property value 分区的值。只能为数字，且不能为负数。
	 * */
	@MermaidPieChartDsl
	class Section @PublishedApi internal constructor(val key: String, val value: String) : MermaidPieChartElement, WithId {
		init {
			require(value[0] != '-') { "Value of a section cannot be negative." }
		}

		override val id: String get() = key

		override fun equals(other: Any?) = equalsByOne(this, other) { id }
		override fun hashCode() = hashCodeByOne(this) { id }
		override fun toString(): String = "${key.quote(config.quote, false)}: $value"
	}
}

/**
 * Mermaid饼图的入口。
 * @property sections 分区组。舍弃重复的元素。
 */
@MermaidPieChartDsl
interface MermaidPieChartEntry : MermaidEntry {
	val sections: MutableSet<Section>

	override fun contentString() = sections.joinToString(SystemProperties.lineSeparator)
}

/**Mermaid饼图的元素。*/
@MermaidPieChartDsl
interface MermaidPieChartElement : MermaidElement


@TopDslFunction
@MermaidPieChartDsl
inline fun mermaidPieChart(block: MermaidPieChart.() -> Unit) = MermaidPieChart().apply(block)

@DslFunction
@MermaidPieChartDsl
fun MermaidPieChart.title(text: String) = Title(text).also { title = it }

@DslFunction
@MermaidPieChartDsl
fun MermaidPieChartEntry.section(key: String, value: Number) = Section(key, value.toString()).also { sections += it }
