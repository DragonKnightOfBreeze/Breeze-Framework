// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import icu.windea.breezeframework.core.extension.inferComponentTargetType

abstract class AbstractDefaultGenerator<T> : DefaultGenerator<T> {
	override val targetType: Class<T> = inferComponentTargetType(this::class.javaObjectType, DefaultGenerator::class.java)

	override fun equals(other: Any?): Boolean {
		if(this === other) return true
		if(other == null || javaClass != other.javaClass) return false
		return when {
			this is ConfigurableDefaultGenerator<*> && other is ConfigurableDefaultGenerator<*> -> configParams.toString() == other.configParams.toString()
			else -> true
		}
	}

	override fun hashCode(): Int {
		return when {
			this is ConfigurableDefaultGenerator<*> -> configParams.toString().hashCode()
			else -> 0
		}
	}

	override fun toString(): String {
		return when {
			this is ConfigurableDefaultGenerator<*> -> targetType.name + '@' + configParams.toString()
			else -> targetType.name
		}
	}
}
