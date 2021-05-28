// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("StreamExtensions")

package icu.windea.breezeframework.core.extension

import java.util.*
import java.util.stream.*

fun <T> Stream<T>.toList(): List<T> {
	val result = collect(Collectors.toList())
	return when(result.size){
		0 -> emptyList()
		1 -> listOf(result.first())
		else -> result
	}
}

fun <T> Stream<T>.toMutableList(): MutableList<T> {
	return collect(Collectors.toList())
}

fun <T> Stream<T>.toSet(): Set<T> {
	val result =  collect(Collectors.toCollection { linkedSetOf() })
	return when(result.size){
		0 -> emptySet()
		1 -> setOf(result.first())
		else -> result
	}
}

fun <T> Stream<T>.toMutableSet(): MutableSet<T> {
	return collect(Collectors.toCollection { linkedSetOf() })
}

fun <T : Map.Entry<K, V>, K, V> Stream<T>.toMap(): Map<K, V> {
	val result = collect(Collectors.toMap({ it.key }, { it.value }, { _, b -> b }, { linkedMapOf() }))
	return when(result.size){
		0 -> emptyMap()
		1 ->  result.entries.first().let { (k,v)-> Collections.singletonMap(k,v) }
		else -> result
	}
}

@JvmName("toMapByPair")
fun <K, V> Stream<Pair<K, V>>.toMap(): Map<K, V> {
	val result = collect(Collectors.toMap({ it.first }, { it.second }, { _, b -> b }, { linkedMapOf() }))
	return when(result.size){
		0 -> emptyMap()
		1 ->  result.entries.first().let { (k,v)-> Collections.singletonMap(k,v) }
		else -> result
	}
}

fun <T : Map.Entry<K, V>, K, V> Stream<T>.toMutableMap(): Map<K, V> {
	return collect(Collectors.toMap({ it.key }, { it.value }, { _, b -> b }, { linkedMapOf() }))
}

@JvmName("toMutableMapByPair")
fun <K, V> Stream<Pair<K, V>>.toMutableMap(): Map<K, V> {
	return collect(Collectors.toMap({ it.first }, { it.second }, { _, b -> b }, { linkedMapOf() }))
}
