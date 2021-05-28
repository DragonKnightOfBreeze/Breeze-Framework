// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import icu.windea.breezeframework.core.model.*

interface ConfigurableDefaultGenerator<T> : DefaultGenerator<T>, ConfigurableComponent {
	override fun configure(configParams: Map<String, Any?>): ConfigurableDefaultGenerator<T>
}
