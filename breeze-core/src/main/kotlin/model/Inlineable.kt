// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.model

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

