// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:NotTested

package com.windea.breezeframework.core.domain.math

import com.windea.breezeframework.core.annotations.*

/**包含当前值和总计值的数量。*/
data class Count @PublishedApi internal constructor(
	val value: Int,
	val totalValue: Int,
) {
	operator fun plus(other: Int): Count = Count((value + other).coerceIn(0, totalValue), totalValue)

	operator fun minus(other: Int): Count = Count((value - other).coerceIn(0, totalValue), totalValue)

	infix fun totalPlus(other: Int): Count = Count(value.coerceIn(0, totalValue + other), (totalValue + other).coerceAtLeast(0))

	infix fun totalMinus(other: Int): Count = Count(value.coerceIn(0, totalValue - other), (totalValue - other).coerceAtLeast(0))
}
