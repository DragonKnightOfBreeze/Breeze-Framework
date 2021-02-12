// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.model

/**
 * 映射入口。
 *
 * Map entry.
 */
data class MapEntry<K, V>(
	override val key: K,
	override val value: V,
) : Map.Entry<K, V>
