// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import icu.windea.breezeframework.core.extension.inferComponentTargetType

abstract class AbstractRandomGenerator<T> : RandomGenerator<T> {
	override val targetType: Class<T> = inferComponentTargetType(this::class.javaObjectType, RandomGenerator::class.java)

	override fun equals(other: Any?): Boolean {
		if(this === other) return true
		if(other == null || javaClass != other.javaClass) return false
		return when {
			this is ConfigurableRandomGenerator<*> && other is ConfigurableRandomGenerator<*> -> configParams.toString() == other.configParams.toString()
			else -> true
		}
	}

	override fun hashCode(): Int {
		return when {
			this is ConfigurableRandomGenerator<*> -> configParams.toString().hashCode()
			else -> 0
		}
	}

	override fun toString(): String {
		return when {
			this is ConfigurableRandomGenerator<*> -> targetType.name + '@' + configParams.toString()
			else -> targetType.name
		}
	}
}
