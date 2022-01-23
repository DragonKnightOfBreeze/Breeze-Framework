// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("StreamExtensions")

package icu.windea.breezeframework.core.extension

import java.util.*
import java.util.function.Function
import java.util.stream.Collectors
import java.util.stream.Stream

//region common extensions
@Suppress("UNCHECKED_CAST")
fun <T: Any,R> Stream<T?>.mapNotNull(mapper: (T) -> R): Stream<R>{
	return (filter{ it != null} as Stream<T>).map(mapper)
}

@Suppress("UNCHECKED_CAST")
fun <T: Any,R> Stream<T?>.mapNotNull(mapper: Function<in T,out R>): Stream<R>{
	return (filter{ it != null} as Stream<T>).map(mapper)
}

@Suppress("UNCHECKED_CAST")
fun <T : Any> Stream<T?>.filterNotNull(): Stream<T> {
	return filter { it != null } as Stream<T>
}

fun <T, R> Stream<T>.distinctBy(selector: (T) -> R): Stream<T> {
	val keys = HashSet<R>()
	return filter { keys.add(selector(it)) }
}

fun <T, R> Stream<T>.distinctBy(selector: Function<in T,out R>): Stream<T> {
	val keys = HashSet<R>()
	return filter { keys.add(selector.apply(it)) }
}
//endregion

//region convert extensions
fun <T> Stream<T>.toCollection(): Collection<T> {
	return toSet()
}

fun <T> Stream<T>.toMutableCollection(): MutableCollection<T> {
	return toMutableSet()
}

fun <T> Stream<T>.toList(): List<T> {
	val result = collect(Collectors.toList())
	return when (result.size) {
		0 -> emptyList()
		1 -> listOf(result.first())
		else -> result
	}
}

fun <T> Stream<T>.toMutableList(): MutableList<T> {
	return collect(Collectors.toList())
}

fun <T> Stream<T>.toSet(): Set<T> {
	val result = collect(Collectors.toCollection { linkedSetOf() })
	return when (result.size) {
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
	return when (result.size) {
		0 -> emptyMap()
		1 -> result.entries.first().let { (k, v) -> Collections.singletonMap(k, v) }
		else -> result
	}
}

@JvmName("toMapByPair")
fun <K, V> Stream<Pair<K, V>>.toMap(): Map<K, V> {
	val result = collect(Collectors.toMap({ it.first }, { it.second }, { _, b -> b }, { linkedMapOf() }))
	return when (result.size) {
		0 -> emptyMap()
		1 -> result.entries.first().let { (k, v) -> Collections.singletonMap(k, v) }
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
//endregion
