// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import icu.windea.breezeframework.core.extension.*
import java.lang.reflect.*

abstract class AbstractConverter<T> : Converter<T> {
	override val targetType: Class<T> = inferComponentTargetClass(this::class.javaObjectType, Converter::class.java)

	override fun equals(other: Any?): Boolean {
		if(this === other) return true
		if(other == null || javaClass != other.javaClass) return false
		if(this is ConfigurableConverter<*> && other is ConfigurableConverter<*> && configParams.toString() != other.configParams.toString()) return false
		return true
	}

	override fun hashCode(): Int {
		var hash = 1
		if(this is ConfigurableConverter<*>) hash = 31 * hash + configParams.toString().hashCode()
		return hash
	}

	override fun toString(): String {
		return buildString {
			append(targetType.name)
			if(this@AbstractConverter is ConfigurableConverter<*>) append('@').append(configParams)
		}
	}
}
