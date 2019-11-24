@file:Suppress("KDocUnresolvedReference")

package com.windea.breezeframework.core.domain.collections.deprecated

/**
 * 多值映射。
 * @see org.springframework.util.MultiValueMap
 * @see com.google.common.collect.Multimap
 */
interface MultiValueMap<K, V> : Map<K, MutableList<V>> {
	fun containsSingleValue(value: @UnsafeVariance V): Boolean
	
	fun getSingle(key: K): V?
	
	fun getSingleOrDefault(key: K, defaultValue: @UnsafeVariance V): V
	
	val flatValues: Collection<V>
	
	val flatEntries: Set<Map.Entry<K, V>>
}

