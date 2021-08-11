// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.model

/**
 * 有条件的对象。
 */
interface Conditional {
	fun matches(): Boolean
}
