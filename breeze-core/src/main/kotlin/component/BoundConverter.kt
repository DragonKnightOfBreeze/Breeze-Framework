// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

interface BoundConverter<T> : Converter<T>, BoundComponent<T> {
	override fun bindingActualTargetType(actualTargetType: Class<*>): BoundConverter<T>
}

