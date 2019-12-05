package com.windea.breezeframework.core.domain.collections

/**可变的映射入口。*/
data class MutableMapEntry<K, V>(
	override val key: K,
	override var value: V
) : MutableMap.MutableEntry<K, V> {
	override fun setValue(newValue: V): V {
		value = newValue
		return newValue
	}
}
