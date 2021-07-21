// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("UnstableExtensions")

package icu.windea.breezeframework.core.extension

import icu.windea.breezeframework.core.annotation.*
import java.util.concurrent.*
import javax.naming.*
import kotlin.jvm.Throws

/**
 * 得到指定的一组值中第一个不为null的值，或者抛出异常。
 */
@UnstableApi
fun <T> coalesce(vararg values: T?): T {
	for(value in values) {
		if(value != null) return value
	}
	throw IllegalArgumentException("No non-null value in values.")
}

/**
 * 得到指定的一组值中第一个不为null的值，或者返回null。
 */
@UnstableApi
fun <T> coalesceOrNull(vararg values: T?): T? {
	for(value in values) {
		if(value != null ) return value
	}
	return null
}

/**
 * 判断指定的关键字是否模糊匹配当前字符串。
 *
 * * 指定的关键字中的字符是否被当前字符串按顺序全部包含。
 * * 如果指定的一组分隔符不为空，则被跳过的子字符串需要以分隔符结束。
 */
@UnstableApi
fun String.fuzzyMatches(keyword: String,vararg delimiters:Char, ignoreCase: Boolean = false): Boolean {
	var index = -1
	var lastIndex = -2
	for(c in keyword) {
		index = indexOf(c,index+1,ignoreCase)
		println(index)
		when {
			index == -1 -> return false
			c !in delimiters && index != 0 && lastIndex != index-1 && this[index-1] !in delimiters -> return false
		}
		lastIndex = index
	}
	return true
}

/**
 * 根据指定的列表以及选择器排序当前列表，未匹配的元素将会排在开始或末尾，默认排在末尾。
 */
@UnstableApi
fun <T, E> List<T>.sortedByList(list: List<E>, unsortedAtLast: Boolean = true, selector: (T) -> E): List<T> {
	return sortedBy {
		val index = list.indexOf(selector(it))
		if(unsortedAtLast && index == -1) size else index
	}
}

/**
 * 根据指定的列表以及选择器倒序排序当前列表，未匹配的元素将会排在开始或末尾，默认排在末尾。
 */
@UnstableApi
fun <T, E> List<T>.sortedByListDescending(list: List<E>, unsortedAtLast: Boolean = true, selector: (T) -> E): List<T> {
	return sortedByDescending {
		val index = list.indexOf(selector(it))
		if(!unsortedAtLast && index == -1) size else index
	}
}


@PublishedApi
internal val parallelExecutor by lazy { Executors.newCachedThreadPool() }

/**
 * 并行遍历数组中的每个元素，执行指定的操作。
 *
 * Performs the given [action] on each element in parallel.
 *
 * @see kotlin.collections.forEach
 */
@UnstableApi
inline fun <T> Array<out T>.parallelForEach(crossinline action: (T) -> Unit) {
	val countDownLatch = CountDownLatch(size)
	for(element in this) {
		parallelExecutor.execute {
			action(element)
			countDownLatch.countDown()
		}
	}
	countDownLatch.await()
}

/**
 * 并行遍历数组中的每个元素，执行指定的操作，带有超时时间。
 *
 * Performs the given [action] on each element in parallel with timeout.
 *
 * @see kotlin.collections.forEach
 */
@UnstableApi
inline fun <T> Array<out T>.parallelForEach(timeout: Long, awaitTimeout: Long, timeUnit: TimeUnit,
	crossinline action: (T) -> Unit) {
	val countDownLatch = CountDownLatch(size)
	for(element in this) {
		parallelExecutor.execute {
			action(element)
			countDownLatch.countDown()
		}
	}
	countDownLatch.await(timeout, timeUnit)
}

/**
 * 并行遍历列表中的每个元素，执行指定的操作。
 *
 * Performs the given [action] on each element in parallel.
 *
 * @see kotlin.collections.forEach
 */
@UnstableApi
inline fun <T> List<T>.parallelForEach(crossinline action: (T) -> Unit) {
	val countDownLatch = CountDownLatch(size)
	for(element in this) {
		parallelExecutor.execute {
			action(element)
			countDownLatch.countDown()
		}
	}
	countDownLatch.await()
}

/**
 * 并行遍历列表中的每个元素，执行指定的操作，带有超时时间。
 *
 * Performs the given [action] on each element in parallel with timeout.
 *
 * @see kotlin.collections.forEach
 */
@UnstableApi
inline fun <T> List<T>.parallelForEach(timeout: Long, timeUnit: TimeUnit, crossinline action: (T) -> Unit) {
	val countDownLatch = CountDownLatch(size)
	for(element in this) {
		parallelExecutor.execute {
			action(element)
			countDownLatch.countDown()
		}
	}
	countDownLatch.await(timeout, timeUnit)
}

/**
 * 并行遍历映射中的每个键值对，执行指定的操作。
 *
 * Performs the given [action] on each entry in parallel.
 *
 * @see kotlin.collections.forEach
 */
@UnstableApi
inline fun <K, V> Map<out K, V>.parallelForEach(crossinline action: (Map.Entry<K, V>) -> Unit) {
	val countDownLatch = CountDownLatch(size)
	for(entry in this) {
		parallelExecutor.execute {
			action(entry)
			countDownLatch.countDown()
		}
	}
	countDownLatch.await()
}

/**
 * 并行遍历映射中的每个键值对，执行指定的操作，带有超时时间。
 *
 * Performs the given [action] on each entry in parallel with timeout.
 *
 * @see kotlin.collections.forEach
 */
@UnstableApi
inline fun <K, V> Map<out K, V>.parallelForEach(timeout: Long,  timeUnit: TimeUnit, crossinline action: (Map.Entry<K, V>) -> Unit) {
	val countDownLatch = CountDownLatch(size)
	for(entry in this) {
		parallelExecutor.execute {
			action(entry)
			countDownLatch.countDown()
		}
	}
	countDownLatch.await(timeout, timeUnit)
}

@UnstableApi
@Throws(InterruptedException::class)
inline fun retry(interval: Long,timeout: Long,timeUnit: TimeUnit,action: () -> Boolean):Boolean{
	val timeoutMillis = timeUnit.convert(timeout,TimeUnit.MILLISECONDS)
	val startTime = System.currentTimeMillis()
	while(true){
		if(action()) return true
		val endTime = System.currentTimeMillis()
		if(endTime - startTime >= timeoutMillis) return false
		timeUnit.sleep(interval)
	}
}

@UnstableApi
@Throws(InterruptedException::class)
inline fun retry(interval:Long,timeUnit: TimeUnit,times:Int, action:()->Boolean):Boolean{
	var t = 1
	while(true){
		if(action()) return true
		if(t++ >= times) return false
		timeUnit.sleep(interval)
	}
}
