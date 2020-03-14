package com.windea.breezeframework.dsl.mermaid.piechart

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.*

/**
 * Mermaid饼图。
 */
@MermaidPieChartDsl
interface MermaidPieChart {
	/**
	 * Mermaid饼图的文档。
	 * @property title （可选项）图表的标题。
	 */
	@MermaidPieChartDsl
	class Document @PublishedApi internal constructor() : Mermaid.Document(), MermaidPieChartDslEntry, CanIndent {
		var title:Title? = null
		override val sections:MutableSet<Section> = mutableSetOf()
		override var indentContent:Boolean = true

		override fun toString() = "pie${Mermaid.ls}${title.typing { "$it${Mermaid.ls}" }}${contentString().doIndent(Mermaid.config.indent)}"
	}

	/**
	 * Mermaid饼图的标题。
	 * @property text 标题的文本。
	 */
	@MermaidPieChartDsl
	class Title @PublishedApi internal constructor(val text:String) : MermaidPieChartDslElement {
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
	) : MermaidPieChartDslElement, WithId {
		init {
			require(value[0] != '-') { "Value of a section cannot be negative." }
		}

		override val id:String get() = key

		override fun equals(other:Any?) = equalsByOne(this, other) { id }
		override fun hashCode() = hashCodeByOne(this) { id }
		override fun toString():String = "${key.quote('"')}: $value"
	}
}
