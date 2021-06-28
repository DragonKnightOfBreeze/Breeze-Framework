// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("UnstableExtensions")

package icu.windea.breezeframework.core.extension

import icu.windea.breezeframework.core.annotation.*

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
 * 得到字符序列的字节长度。
 */
@UnstableApi
val CharSequence.byteLength: Int
	get() = TODO()

/**
 * 判断指定的关键字是否模糊匹配当前字符串。（指定的关键字中的字符是否被当前字符串按顺序全部包含）
 */
@UnstableApi
fun String.fuzzyMatches(keyword: String, ignoreCase: Boolean = false): Boolean {
	TODO()
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
