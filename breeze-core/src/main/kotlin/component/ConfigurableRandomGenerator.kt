// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import icu.windea.breezeframework.core.model.*

interface ConfigurableRandomGenerator<T> : RandomGenerator<T>, Configurable {
	override fun configure(configParams: Map<String, Any?>): ConfigurableRandomGenerator<T>
}
