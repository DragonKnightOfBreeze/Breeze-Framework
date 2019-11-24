package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.types.*

//region MultiValueMap<K, V> extensions
fun <K, V> MultiValueMap<K, V>.containsSingleValue(value: @UnsafeVariance V): Boolean {
	return this.values.any { value in it }
}

fun <K, V> MultiValueMap<K, V>.getSingle(key: K): V? {
	return this[key]?.single()
}

fun <K, V> MultiValueMap<K, V>.getSingleValue(key: K): V {
	return this.getValue(key).single()
}

fun <K, V> MultiValueMap<K, V>.getSingleOrDefault(key: K, defaultValue: @UnsafeVariance V): V {
	return this[key]?.singleOrNull() ?: defaultValue
}

fun <K, V> MultiValueMap<K, V>.getSingleOrElse(key: K, defaultValue: () -> V): V {
	return this[key]?.singleOrNull() ?: defaultValue()
}

val <K, V> MultiValueMap<K, V>.flatValues: Collection<V>
	@JvmName("getFlatValues") get() = values.flatten()
//endregion

//region MutableMultiValueMap<K, V> extensions
fun <K, V> MutableMultiValueMap<K, V>.addSingle(key: K, value: V) {
	this.getOrPut(key) { mutableListOf() } += value
}

fun <K, V> MutableMultiValueMap<K, V>.add(key: K, value: List<V>) {
	this.getOrPut(key) { mutableListOf() } += value
}

fun <K, V> MutableMultiValueMap<K, V>.addAll(from: MultiValueMap<K, V>) {
	for((k, v) in from) {
		this.getOrPut(k) { mutableListOf() } += v
	}
}

fun <K, V> MutableMultiValueMap<K, V>.putSingle(key: K, value: V): V? {
	val result = this[key]?.firstOrNull()
	this[key] = mutableListOf(value)
	return result
}

@Suppress("UNCHECKED_CAST")
fun <K, V> MutableMultiValueMap<K, V>.putAll(from: MultiValueMap<K, V>) {
	this.putAll(from as Map<K, MutableList<V>>)
}

val <K, V> MutableMultiValueMap<K, V>.flatValues: MutableCollection<V>
	@JvmName("getMutableFlatValues") get() = values.flatten().toMutableList()
//endregion
