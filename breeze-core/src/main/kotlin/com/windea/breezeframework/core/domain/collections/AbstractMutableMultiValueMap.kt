package com.windea.breezeframework.core.domain.collections

abstract class AbstractMutableMultiValueMap<K, V> protected constructor() : MutableMultiValueMap<K, V> {
	override fun containsSingleValue(value: @UnsafeVariance V): Boolean {
		return this.values.any { value in it }
	}
	
	override fun getSingle(key: K): V? {
		return this[key]?.firstOrNull()
	}
	
	override fun getSingleOrDefault(key: K, defaultValue: @UnsafeVariance V): V {
		return this[key]?.firstOrNull() ?: defaultValue
	}
	
	override fun addSingle(key: K, value: V) {
		this.getOrPut(key) { mutableListOf() } += value
	}
	
	override fun add(key: K, value: List<V>) {
		this.getOrPut(key) { mutableListOf() } += value
	}
	
	override fun addAll(from: MultiValueMap<K, V>) {
		from.forEach { (k, v) -> this.getOrPut(k) { mutableListOf() } += v }
	}
	
	override fun putSingle(key: K, value: V): V? {
		val result = this[key]?.firstOrNull()
		this[key] = mutableListOf(value)
		return result
	}
	
	override fun putAll(from: MultiValueMap<K, V>) {
		this.putAll(from as Map<K, MutableList<V>>)
	}
	
	override val flatValues: MutableCollection<V>
		get() = values.flatten().toMutableList()
	
	override val flatEntries: MutableSet<MutableMap.MutableEntry<K, V>>
		get() = entries.flatMap { (k, v) -> v.map { MutableMapEntry(k, it) } }.toMutableSet()
	
	override fun equals(other: Any?): Boolean {
		if(other === this) return true
		if(other !is MutableMultiValueMap<*, *>) return false
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
