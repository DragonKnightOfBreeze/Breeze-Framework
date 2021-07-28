// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import icu.windea.breezeframework.core.extension.*

abstract class AbstractRandomGenerator<T> : RandomGenerator<T> {
	override val targetType: Class<T> = inferComponentTargetClass(this::class.javaObjectType, RandomGenerator::class.java)

	override fun equals(other: Any?) = componentEquals(this, other)

	override fun hashCode() = componentHashcode(this)

	override fun toString() = componentToString(this)
}
