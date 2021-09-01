// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.model.deprecated

interface Page<T> {
	val pageNumber: Int
	val pageSize: Int
	val sort: Int
	val total: Int
	val items: List<T>
}

interface PageParam {
	val pageNumber: Int
	val pageSize: Int
	val sort: String
}

data class PageRequest(
	override val pageNumber: Int = 0,
	override val pageSize: Int = 10,
	override val sort: String = ""
) : PageParam
