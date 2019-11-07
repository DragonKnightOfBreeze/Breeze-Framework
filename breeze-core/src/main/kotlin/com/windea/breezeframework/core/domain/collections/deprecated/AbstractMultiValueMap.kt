package com.windea.breezeframework.core.domain.collections.deprecated

import com.windea.breezeframework.core.domain.collections.*

/**
 * 抽象的多值映射。
 */
abstract class AbstractMultiValueMap<K, V> protected constructor() : MultiValueMap<K, V> {
	override fun containsSingleValue(value: @UnsafeVariance V): Boolean {
		return this.values.any { value in it }
	}
	
	override fun getSingle(key: K): V? {
		return this[key]?.firstOrNull()
	}
	
	override fun getSingleOrDefault(key: K, defaultValue: @UnsafeVariance V): V {
		return this[key]?.firstOrNull() ?: defaultValue
	}
	
	override val flatValues: Collection<V>
		get() = values.flatten()
	
	override val flatEntries: Set<Map.Entry<K, V>>
		get() = entries.flatMap { (k, v) -> v.map { MapEntry(k, it) } }.toSet()
	
	override fun equals(other: Any?): Boolean {
		if(other === this) return true
		if(other !is MultiValueMap<*, *>) return false
		if(size != other.size) return false
		return other.entries.all { (k, v) -> this[k] == v }
	}
	
	override fun hashCode(): Int {
		return entries.hashCode()
	}
	
	override fun toString(): String {
		return entries.joinToString(", ", "{", "}")
	}
}
