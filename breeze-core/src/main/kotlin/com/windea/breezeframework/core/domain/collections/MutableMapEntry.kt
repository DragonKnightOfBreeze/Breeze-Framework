package com.windea.breezeframework.core.domain.collections

data class MutableMapEntry<K, V>(
	override val key: K,
	override var value: V
) : MutableMap.MutableEntry<K, V> {
	override fun setValue(newValue: V): V {
		value = newValue
		return newValue
	}
}
