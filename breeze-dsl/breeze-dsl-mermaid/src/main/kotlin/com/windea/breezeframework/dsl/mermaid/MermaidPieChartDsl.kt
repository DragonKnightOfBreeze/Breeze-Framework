@file:Suppress("unused", "NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS", "INLINE_CLASS_NOT_TOP_LEVEL")

package com.windea.breezeframework.dsl.mermaid

import com.windea.breezeframework.core.constants.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.Mermaid.Companion.config
import com.windea.breezeframework.dsl.mermaid.MermaidPieChart.*

/**
 * Mermaid饼图的Dsl。
 * 参见：[Mermaid Pie Chart](https://mermaidjs.github.io/#/pie)
 */
@DslMarker
@MustBeDocumented
annotation class MermaidPieChartDsl

/**
 * Mermaid饼图的入口。
 * @property sections 分区一览。忽略重复的元素。
 */
@MermaidPieChartDsl
interface MermaidPieChartEntry : MermaidEntry {
	val sections:MutableSet<Section>

	override val contentString get() = sections.joinToString(SystemProperties.lineSeparator)
}

/**
 * Mermaid饼图的元素。
 * 参见：[Mermaid Pie Chart](https://mermaidjs.github.io/#/pie)
 */
@MermaidPieChartDsl
interface MermaidPieChartElement : MermaidElement

/**
 * Mermaid饼图。
 * 参见：[Mermaid Pie Chart](https://mermaidjs.github.io/#/pie)
 */
@MermaidPieChartDsl
interface MermaidPieChart {
	/**
	 * Mermaid饼图的文档。
	 * @property title （可选项）图表的标题。
	 */
	class Document @PublishedApi internal constructor() : Mermaid.Document(), MermaidPieChartEntry, CanIndent {
		var title:Title? = null
		override val sections:MutableSet<Section> = mutableSetOf()
		override var indentContent:Boolean = true

		override fun toString() = "pie$ls${title.typing { "$it$ls" }}${contentString.doIndent(config.indent)}"
	}

	/**
	 * Mermaid饼图的标题。
	 * @property text 标题的文本。
	 */
	@MermaidPieChartDsl
	inline class Title @PublishedApi internal constructor(val text:String) : MermaidPieChartElement {
		override fun toString() = "title $text"
	}

	/**
	 * Mermaid饼图的分块。
	 * @property key 分块的键。只能使用双引号包围，不能包含双引号，也不能进行转义。
	 * @property value 分块的值。只能为数字，且不能为负数。
	 */
	@MermaidPieChartDsl
	class Section @PublishedApi internal constructor(
		val key:String, val value:String
	) : MermaidPieChartElement, WithId {
		init {
			require(value[0] != '-') { "Value of a section cannot be negative." }
		}

		override val id:String get() = key

		override fun equals(other:Any?) = equalsByOne(this, other) { id }
		override fun hashCode() = hashCodeByOne(this) { id }
		override fun toString():String = "${key.quote('"')}: $value"
	}
}


@TopDslFunction
@MermaidPieChartDsl
inline fun mermaidPieChart(block:Document.() -> Unit) = Document().apply(block)

@DslFunction
@MermaidPieChartDsl
fun Document.title(text:String) = Title(text).also { title = it }

@DslFunction
@MermaidPieChartDsl
fun MermaidPieChartEntry.section(key: String, value: Number) = Section(key, value.toString()).also { sections += it }
