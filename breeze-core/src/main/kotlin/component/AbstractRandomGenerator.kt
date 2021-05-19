// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import icu.windea.breezeframework.core.extension.*

abstract class AbstractRandomGenerator<T> : RandomGenerator<T> {
	override val targetType: Class<T> = inferComponentTargetType(this::class.javaObjectType, RandomGenerator::class.java)

	override fun equals(other: Any?): Boolean {
		if(this === other) return true
		if(other == null || javaClass != other.javaClass) return false
		if(this is ConfigurableRandomGenerator<*> && other is ConfigurableRandomGenerator<*> && configParams.toString() != other.configParams.toString()) return false
		if(this is BoundRandomGenerator<*> && other is BoundRandomGenerator<*> && actualTargetType != other.actualTargetType) return false
		return true
	}

	override fun hashCode(): Int {
		var hash = 1
		if(this is ConfigurableRandomGenerator<*>) hash = 31 * hash + configParams.toString().hashCode()
		if(this is BoundRandomGenerator<*>) hash = 31 * hash + actualTargetType.hashCode()
		return hash
	}

	override fun toString(): String {
		return buildString {
			append(targetType)
			if(this@AbstractRandomGenerator is ConfigurableRandomGenerator<*>) append('@').append(configParams)
			if(this@AbstractRandomGenerator is BoundRandomGenerator<*>) append('@').append(actualTargetType)
		}
	}
}
