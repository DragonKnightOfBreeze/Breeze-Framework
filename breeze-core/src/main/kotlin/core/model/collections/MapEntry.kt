// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.model.collections

/**
 * 映射入口。
 *
 * Map entry.
 */
data class MapEntry<K, V>(
	override val key: K,
	override val value: V,
) : Map.Entry<K, V>
