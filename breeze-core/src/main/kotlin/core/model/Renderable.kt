// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:Suppress("METHOD_OF_ANY_IMPLEMENTED_IN_INTERFACE")

package icu.windea.breezeframework.core.model

/**
 * 可渲染文本的对象。
 *
 * 注意：这个接口重载的[toString]方法不一定生效。
 */
interface Renderable {
	fun renderTo(builder: StringBuilder)

	fun render(): String = buildString { renderTo(this) }

	override fun toString(): String = render()
}
