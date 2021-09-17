// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.model.collections

/**
 * 可变的映射入口。
 *
 * Mutable map entry.
 */
data class MutableMapEntry<K, V>(
	override val key: K,
	override var value: V,
) : MutableMap.MutableEntry<K, V> {
	override fun setValue(newValue: V): V {
		value = newValue
		return newValue
	}
}
