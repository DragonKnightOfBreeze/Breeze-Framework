// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.model.collections

interface ParameterMap {
	val size: Int

	fun hasValue(name: String): Boolean

	fun getValue(name: String): Any?

	fun getValueOrNull(name: String): Any?

	fun addValue(name: String, value: Any?): ParameterMap

	operator fun get(name: String) = getValue(name)
}
