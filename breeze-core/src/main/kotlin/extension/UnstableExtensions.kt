// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("UnstableExtensions")

package icu.windea.breezeframework.core.extension

import icu.windea.breezeframework.core.annotation.*

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
