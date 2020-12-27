// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.model

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
