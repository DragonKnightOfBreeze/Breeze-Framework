package com.windea.breezeframework.core.domain.math

/**包含当前值和总计值的数量。*/
data class Count @PublishedApi internal constructor(
	val value: Int,
	val totalValue: Int
) {
	operator fun plus(other: Int): Count =
		if(value in magicValues) this else Count((value + other).coerceIn(1, totalValue), totalValue)

	operator fun minus(other: Int): Count =
		if(value in magicValues) this else Count((value - other).coerceIn(1, totalValue), totalValue)

	infix fun totalPlus(other: Int): Count =
		if(totalValue + other <= 0) Count(0, 0) else Count(value.coerceAtMost(totalValue + other), totalValue + other)

	infix fun totalMinus(other: Int): Count =
		if(totalValue - other <= 0) Count(0, 0) else Count(value.coerceAtMost(totalValue - other), totalValue - other)

	companion object {
		const val infinity = -1

		val magicValues = mutableSetOf(
			infinity
		)
	}
}
