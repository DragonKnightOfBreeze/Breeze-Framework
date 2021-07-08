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
