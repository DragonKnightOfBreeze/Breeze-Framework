// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:Suppress("METHOD_OF_ANY_IMPLEMENTED_IN_INTERFACE")

package icu.windea.breezeframework.dsl

import icu.windea.breezeframework.core.model.*

/**
 * [Breeze Dsl Api](https://github.com/DragonKnightOfBreeze/Breeze-Framework/tree/master/breeze-dsl)的标记注解。
 *
 * Marker annotation of [Breeze DslDocument Api](https://github.com/DragonKnightOfBreeze/Breeze-Framework/tree/master/breeze-dsl).
 */
@DslMarker
@MustBeDocumented
annotation class DslApiMarker

/**
 * DSl配置。
 *
 * DSL configuration.
 */
@DslApiMarker
interface DslConfig

/**
 * DSL文档。DSL定义结构的顶级节点。
 *
 * DSL document. Top node of DSL definition structure.
 */
@DslApiMarker
interface DslDocument : Renderable {
	override fun toString(): String = render()
}

/**
 * DSL内容。DSL定义结果的节点的抽象。
 *
 * DSL content. Node Abstraction of dsl definition structure.
 */
@DslApiMarker
interface DslContent : Renderable {
	fun toContentString(): String = render()
}

/**
 * DSL元素。DSL定义结构的成员节点。
 *
 * DSL element. Member node of dsl definition structure.
 */
@DslApiMarker
interface DslElement : Renderable {
	override fun toString(): String = render()
}

/**
 * DSL内联点。可在此内联DSL内联元素。
 *
 * DSL inline point. Can inline dsl inline element here.
 */
@DslApiMarker
interface DslInlinePoint

/**
 * DSL内联元素。DSL定义结构的内联成员节点。
 *
 * DSL element. Inline member node of dsl definition structure.
 */
interface DslInlineElement : DslElement, Inlineable {
	override fun inline(): CharSequence = render()

	override fun toString(): String = render()
}
