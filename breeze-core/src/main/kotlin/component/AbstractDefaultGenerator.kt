// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import icu.windea.breezeframework.core.extension.*

abstract class AbstractDefaultGenerator<T> : DefaultGenerator<T> {
	override val targetType: Class<T> = inferComponentTargetClass(this::class.javaObjectType, DefaultGenerator::class.java)

	override fun equals(other: Any?) = componentEquals(this, other)

	override fun hashCode() = componentHashcode(this)

	override fun toString() = componentToString(this)
}
