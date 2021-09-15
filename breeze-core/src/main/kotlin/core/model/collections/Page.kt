// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.model.collections

interface Page<out T> {
	val pageNumber: Int
	val pageSize: Int
	val total: Int
	val totalPage: Int
	val fromIndex: Int
	val toIndex: Int
	val content: List<T>
}

fun <T> emptyPage(): Page<T> {
	return EmptyPage
}

fun <T> List<T>.toPage(pageNumber: Int = 1, pageSize: Int = 10): Page<T> {
	return PageImpl(this, pageNumber, pageSize)
}

