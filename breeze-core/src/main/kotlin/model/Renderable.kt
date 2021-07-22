// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.model

//注意：可以通过添加注解 @Suppress("METHOD_OF_ANY_IMPLEMENTED_IN_INTERFACE") 让接口可以重载Any中的方法，但不一定生效

/**
 * 可渲染文本的对象。
 */
@Suppress("METHOD_OF_ANY_IMPLEMENTED_IN_INTERFACE")
interface Renderable {
	fun renderTo(builder: StringBuilder)

	fun render(): String = buildString { renderTo(this) }

	override fun toString(): String = render()
}
