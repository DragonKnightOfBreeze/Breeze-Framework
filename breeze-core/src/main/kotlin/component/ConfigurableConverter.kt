// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

interface ConfigurableConverter<T> : Converter<T>, ConfigurableComponent {
	override fun configure(configParams: Map<String, Any?>): ConfigurableConverter<T>
}

