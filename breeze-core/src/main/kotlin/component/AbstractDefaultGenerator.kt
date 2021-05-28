// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import icu.windea.breezeframework.core.extension.*

abstract class AbstractDefaultGenerator<T> : DefaultGenerator<T> {
	override val targetType: Class<T> = inferComponentTargetClass(this::class.javaObjectType, DefaultGenerator::class.java)

	override fun equals(other: Any?): Boolean {
		if(this === other) return true
		if(other == null || javaClass != other.javaClass) return false
		if(this is ConfigurableDefaultGenerator<*> && other is ConfigurableDefaultGenerator<*> && configParams.toString() != other.configParams.toString()) return false
		return true
	}

	override fun hashCode(): Int {
		var hash = 1
		if(this is ConfigurableDefaultGenerator<*>) hash = 31 * hash + configParams.toString().hashCode()
		return hash
	}

	override fun toString(): String {
		return buildString {
			append(targetType)
			if(this@AbstractDefaultGenerator is ConfigurableDefaultGenerator<*>) append('@').append(configParams)
		}
	}
}
