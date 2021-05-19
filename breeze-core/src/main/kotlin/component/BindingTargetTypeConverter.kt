// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

interface BindingTargetTypeConverter<T>: Converter<T> {
	val actualTargetType:Class<out T>

	fun bindingTargetType(actualTargetType:Class<*>) :BindingTargetTypeConverter<T>
}
