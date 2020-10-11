// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl.mermaid.piechart

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.DslConstants.ls
import com.windea.breezeframework.dsl.mermaid.*

/**
 * Dsl definitions of [MermaidPieChartDsl].
 */
@MermaidPieChartDslMarker
interface MermaidPieChartDslDefinitions {
	/**
	 * Mermaid饼图领域特定语言的入口。
	 * @property sections 分区一览。忽略重复的元素。
	 */
	@MermaidPieChartDslMarker
	interface IChartDslEntry : MermaidDslDefinitions.IDslEntry {
		val sections: MutableSet<Section>

		override fun toContentString(): String {
			return sections.joinToText(ls)
		}
	}

	/**
	 * Mermaid饼图领域特定语言的元素。
	 */
	@MermaidPieChartDslMarker
	interface MermaidPieChartDslElement : MermaidDslDefinitions.IDslElement

	/**
	 * Mermaid饼图的标题。
	 * @property text 标题的文本。
	 */
	@MermaidPieChartDslMarker
	class Title @PublishedApi internal constructor(
		val text: String
	) : MermaidPieChartDslElement {
		override fun toString(): String {
			return "title $text"
		}
	}

	/**
	 * Mermaid饼图的分块。
	 * @property key 分块的键。只能使用双引号包围，不能包含双引号，也不能进行转义。
	 * @property value 分块的值。只能为数字，且不能为负数。
	 */
	@MermaidPieChartDslMarker
	class Section @PublishedApi internal constructor(
		val key: String, val value: String
	) : MermaidPieChartDslElement, WithId {
		init {
			require(value[0] != '-') { "Value of a section cannot be negative." }
		}

		override val id: String get() = key

		override fun equals(other: Any?) = equalsBy(this, other) { arrayOf(id) }

		override fun hashCode() = hashCodeBy(this) { arrayOf(id) }

		override fun toString():String {
			return "${key.quote('"')}: $value"
		}
	}
}
