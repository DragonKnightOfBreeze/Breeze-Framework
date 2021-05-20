// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("StreamExtensions")

package icu.windea.breezeframework.core.extension

import java.util.stream.*

fun <T> Stream<T>.toList(): List<T> {
	return collect(Collectors.toList())
}

fun <T> Stream<T>.toSet(): Set<T> {
	return collect(Collectors.toSet())
}

fun <T> Stream<T>.toLinkedSet(): LinkedHashSet<T> {
	return collect(Collectors.toCollection { linkedSetOf() })
}

fun <T : Map.Entry<K, V>, K, V> Stream<T>.toMap(): Map<K, V> {
	return collect(Collectors.toMap({ it.key }, { it.value }, { _, b -> b }))
}

fun <T : Map.Entry<K, V>, K, V> Stream<T>.toLinkedMap(): Map<K, V> {
	return collect(Collectors.toMap({ it.key }, { it.value }, { _, b -> b }, { linkedMapOf() }))
}

@JvmName("toMapByPair")
fun <K, V> Stream<Pair<K, V>>.toMap(): Map<K, V> {
	return collect(Collectors.toMap({ it.first }, { it.second }, { _, b -> b }))
}

@JvmName("toLinkedMapByPair")
fun <K, V> Stream<Pair<K, V>>.toLinkedMap(): Map<K, V> {
	return collect(Collectors.toMap({ it.first }, { it.second }, { _, b -> b }, { linkedMapOf() }))
}
