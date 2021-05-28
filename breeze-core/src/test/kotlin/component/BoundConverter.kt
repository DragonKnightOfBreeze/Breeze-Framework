// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import java.lang.reflect.*

interface BoundConverter<T> : Converter<T>, BoundComponent {
	override fun bindingActualTargetType(actualTargetType: Type): BoundConverter<T>
}

