// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.model

/**
 * 可渲染文本的对象。
 */
@Suppress("METHOD_OF_ANY_IMPLEMENTED_IN_INTERFACE")
interface Renderable : CharSequence {
	/**
	 * 渲染文本。
	 */
	fun render(builder: StringBuilder)

	fun render(): String = buildString { render(this) }

	override fun toString(): String = render()
}
