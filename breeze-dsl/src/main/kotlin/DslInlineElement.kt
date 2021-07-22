// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.dsl

import icu.windea.breezeframework.core.model.*

/**
 * DSL内联元素。DSL定义结构的内联成员节点。可以直接作为字符序列使用。
 *
 * DSL element. Member node of dsl definition structure. Can be used as character sequence.
 */
@Suppress("METHOD_OF_ANY_IMPLEMENTED_IN_INTERFACE")
interface DslInlineElement : DslElement, Inlineable {
	override fun inline(): CharSequence {
		return buildString { render(this) }
	}

	override fun toString(): String {
		return inline().toString()
	}
}
