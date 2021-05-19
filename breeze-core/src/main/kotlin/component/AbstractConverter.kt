// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import icu.windea.breezeframework.core.extension.inferComponentTargetType

abstract class AbstractConverter<T> : Converter<T> {
	override val targetType: Class<T> = inferComponentTargetType(this::class.javaObjectType, Converter::class.java)

	override fun equals(other: Any?): Boolean {
		if(this === other) return true
		if(other == null || javaClass != other.javaClass) return false
		return when {
			this is ConfigurableConverter<*> && other is ConfigurableConverter<*> -> configParams.toString() == other.configParams.toString()
			this is BindingTargetTypeConverter<*> && other is BindingTargetTypeConverter<*> -> actualTargetType == other.actualTargetType
			else -> true
		}
	}

	override fun hashCode(): Int {
		return when {
			this is ConfigurableConverter<*> -> configParams.toString().hashCode()
			this is BindingTargetTypeConverter<*> -> actualTargetType.hashCode()
			else -> 0
		}
	}

	override fun toString(): String {
		return when {
			this is ConfigurableConverter<*> -> targetType.name + '@' + configParams.toString()
			this is BindingTargetTypeConverter<*> -> targetType.name + '@' + actualTargetType.name
			else -> targetType.name
		}
	}
}
