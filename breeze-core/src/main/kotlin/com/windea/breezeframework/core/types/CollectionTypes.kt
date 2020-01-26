package com.windea.breezeframework.core.types

import java.util.concurrent.*

/**线程安全的列表。*/
typealias ConcurrentList<E> = CopyOnWriteArrayList<E>

/**线程安全的集。*/
typealias ConcurrentSet<E> = CopyOnWriteArraySet<E>

//typealias MultiValueMap<K, V> = Map<K, List<V>>
//
//typealias MutableMultiValueMap<K, V> = MutableMap<K, MutableList<V>>
