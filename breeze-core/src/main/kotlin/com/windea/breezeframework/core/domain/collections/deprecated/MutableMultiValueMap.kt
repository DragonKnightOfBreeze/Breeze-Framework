@file:Suppress("KDocUnresolvedReference")

package com.windea.breezeframework.core.domain.collections.deprecated

//TODO 如何继承自MutableMap<K, List<V>>而不引起泛型冲突？

/**
 * 可变的多值映射。
 * @see org.springframework.util.MultiValueMap
 * @see com.google.common.collect.Multimap
 */
interface MutableMultiValueMap<K, V> : MutableMap<K, MutableList<V>>, MultiValueMap<K, V> {
	fun addSingle(key: K, value: V)
	
	fun add(key: K, value: List<V>)
	
	fun addAll(from: MultiValueMap<K, V>)
	
	fun putSingle(key: K, value: V): V?
	
	fun putAll(from: MultiValueMap<K, V>)
	
	override val flatValues: MutableCollection<V>
	
	override val flatEntries: MutableSet<MutableMap.MutableEntry<K, V>>
}

