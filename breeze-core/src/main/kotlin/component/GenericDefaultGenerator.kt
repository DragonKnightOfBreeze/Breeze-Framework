// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import java.lang.reflect.*

interface GenericDefaultGenerator<T> : DefaultGenerator<T> {
	override fun generate(): T {
		throw UnsupportedOperationException("Redirect to 'fun generate(targetType: Type): T'.")
	}

	fun generate(targetType: Type): T
}
