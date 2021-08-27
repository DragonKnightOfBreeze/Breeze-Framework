// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.model

internal class PageImpl<T>(
	source: List<T> = emptyList(),
	pageNumber: Int = 1,
	pageSize: Int = 10
) : Page<T> {
	override val pageNumber = pageNumber.coerceAtLeast(0)
	override val pageSize = pageSize.coerceAtLeast(0)
	override val total: Int = source.size
	override val totalPage: Int = if (pageSize == 0) 0 else ((total - 1) / pageSize) + 1
	override val fromIndex: Int = (pageNumber - 1) * pageSize
	override val toIndex: Int = (pageNumber * pageSize).coerceAtMost(total)
	override val content: List<T> = source.subList(fromIndex, toIndex)

	override fun equals(other: Any?): Boolean{
		return other is Page<*> && pageNumber == other.pageNumber && pageSize == other.pageSize &&
			this.content == other.content
	}

	override fun hashCode(): Int {
		var result = pageNumber
		result = 31 * result + pageSize
		result = 31 * result + content.hashCode()
		return result
	}
}

