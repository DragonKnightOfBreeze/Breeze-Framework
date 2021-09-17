// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.model

internal object EmptyPage : Page<Nothing> {
	override val pageNumber: Int = 0
	override val pageSize: Int = 0
	override val total: Int = 0
	override val totalPage: Int = 0
	override val fromIndex: Int = 0
	override val toIndex: Int = 0
	override val content: List<Nothing> = emptyList()

	override fun equals(other: Any?): Boolean = other is Page<*> && other.pageSize == 0 && other.pageNumber == 0

	override fun hashCode(): Int = 1
}
