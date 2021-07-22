// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.model

//注意：可以通过添加注解 @Suppress("METHOD_OF_ANY_IMPLEMENTED_IN_INTERFACE") 让接口可以重载Any中的方法，但不一定生效

/**
 * 可内联的对象。
 */
@Suppress("METHOD_OF_ANY_IMPLEMENTED_IN_INTERFACE")
interface Inlineable : CharSequence {
	fun inline(): CharSequence

	override val length get() = inline().length

	override fun get(index: Int) = inline()[index]

	override fun subSequence(startIndex: Int, endIndex: Int) = inline().subSequence(startIndex, endIndex)

	override fun toString(): String = inline().toString()
}

class A:Inlineable{
	override fun inline(): CharSequence {
		return "1"
	}
}

fun main() {
	println(A())
}
