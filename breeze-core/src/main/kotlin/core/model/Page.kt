// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.model

interface Page<out T> {
	val pageNumber: Int
	val pageSize: Int
	val total: Int
	val totalPage: Int
	val fromIndex: Int
	val toIndex: Int
	val content: List<T>
}

