// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import java.lang.reflect.*

interface GenericRandomGenerator<T> : RandomGenerator<T> {
	fun generate(targetType: Type): T

	override fun generate(): T {
		throw UnsupportedOperationException("Redirect to 'fun generate(targetType: Type): T'.")
	}
}
