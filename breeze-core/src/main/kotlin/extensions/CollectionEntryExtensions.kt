@file:JvmName("CollectionEntryExtensions")

package com.windea.breezeframework.core.extensions

import java.util.*
import java.util.concurrent.*
import kotlin.collections.LinkedHashMap
import kotlin.collections.LinkedHashSet

/**构建一个集并事先过滤空的元素。*/
fun <T : Any> setOfNotNull(element:T?) =
	if(element != null) listOf(element) else emptyList()

/**构建一个集并事先过滤空的元素。*/
fun <T : Any> setOfNotNull(vararg elements:T?):Set<T> =
	LinkedHashSet<T>().apply { for(element in elements) if(element != null) add(element) }

/**构建一个映射并事先过滤值为空的键值对。*/
fun <K, V : Any> mapOfValuesNotNull(pair:Pair<K, V?>) =
	if(pair.second != null) mapOf(pair) else emptyMap()

/**构建一个映射并事先过滤值为空的键值对。*/
fun <K, V : Any> mapOfValuesNotNull(vararg pairs:Pair<K, V?>) =
	LinkedHashMap<K, V>().apply { for((key, value) in pairs) if(value != null) put(key, value) }


/**构建一个空的枚举集。*/
inline fun <reified T : Enum<T>> enumSetOf():EnumSet<T> = EnumSet.noneOf(T::class.java)

/**构建一个枚举集。*/
fun <T : Enum<T>> enumSetOf(first:T, vararg elements:T):EnumSet<T> = EnumSet.of(first, *elements)

/**构建一个包含所有枚举值的枚举集。*/
inline fun <reified T : Enum<T>> enumSetOfAll():EnumSet<T> = EnumSet.allOf(T::class.java)

/**构建一个空的枚举映射。*/
inline fun <reified K : Enum<K>, V> enumMapOf():EnumMap<K, V> = EnumMap(K::class.java)

/**构建一个枚举映射。*/
fun <K : Enum<K>, V> enumMapOf(vararg pairs:Pair<K, V>):EnumMap<K, V> = EnumMap(pairs.toMap())


/**构建一个空的线程安全的并发列表。*/
fun <T> concurrentListOf():CopyOnWriteArrayList<T> = CopyOnWriteArrayList()

/**构建一个线程安全的并发列表。*/
fun <T> concurrentListOf(vararg elements: T):CopyOnWriteArrayList<T> = CopyOnWriteArrayList(elements)

/**构建一个空的线程安全的并发集。*/
 fun <T> concurrentSetOf():CopyOnWriteArraySet<T> = CopyOnWriteArraySet()

/**构建一个线程安全的并发集。*/
fun <T> concurrentSetOf(vararg elements: T):CopyOnWriteArraySet<T> = CopyOnWriteArraySet(elements.toSet())

/**构建一个空的线程安全的并发映射。*/
 fun <K, V> concurrentMapOf():ConcurrentHashMap<K, V> = ConcurrentHashMap()

/**构建一个线程安全的并发映射。*/
fun <K, V> concurrentMapOf(vararg pairs: Pair<K, V>):ConcurrentHashMap<K, V> = ConcurrentHashMap(pairs.toMap())