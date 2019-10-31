@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")

package com.windea.breezeframework.core.domain.collections

//region implementations
class LinkedMultiValueHashMap<K, V>(
	original: Map<K, MutableList<V>> = mapOf()
) : AbstractMutableMultiValueMap<K, V>(), MutableMap<K, MutableList<V>> by LinkedHashMap(original)

class MultiValueHashMap<K, V>(
	original: Map<K, MutableList<V>> = mapOf()
) : AbstractMutableMultiValueMap<K, V>(), MutableMap<K, MutableList<V>> by HashMap(original)
//endregion

//region entry extensions
inline fun <K, V> multiValueMapOf(): MultiValueMap<K, V> =
	LinkedMultiValueHashMap()

inline fun <K, V> multiValueMapOf(vararg pairs: Pair<K, List<V>>): MultiValueMap<K, V> =
	LinkedMultiValueHashMap(pairs.associate { (k, v) -> k to v.toMutableList() })

inline fun <K, V> mutableMultiValueMapOf(): MutableMultiValueMap<K, V> =
	LinkedMultiValueHashMap()

inline fun <K, V> mutableMultiValueMapOf(vararg pairs: Pair<K, MutableList<V>>): MultiValueMap<K, V> =
	LinkedMultiValueHashMap(pairs.toMap())
//endregion

//region convert extensions
fun <K, V> MultiValueMap<K, V>.toSingleValueMap(): Map<K, V> {
	return this.filterNot { (_, v) -> v.isEmpty() }.mapValues { (_, v) -> v.first() }
}

fun <K, V> MultiValueMap<K, V>.toFlatList(): List<Pair<K, V>> {
	return this.flatMap { (k, v) -> v.map { k to it } }
}
//endregion
