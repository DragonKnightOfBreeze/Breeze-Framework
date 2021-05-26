// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.model

interface Page<T> {
	val pageNumber:Int
	val pageSize:Int
	val sort:Int
	val total:Int
	val items:List<T>
}

interface PageParam {
	val pageNumber:Int
	val pageSize:Int
	val sort:Int
}
