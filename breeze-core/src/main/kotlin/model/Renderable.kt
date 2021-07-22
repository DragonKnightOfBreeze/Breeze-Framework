// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.model

/**
 * 可渲染文本的对象。
 */
@Suppress("METHOD_OF_ANY_IMPLEMENTED_IN_INTERFACE")
interface Renderable {
	fun render(builder: StringBuilder)

	override fun toString(): String = buildString { render(this) }
}
