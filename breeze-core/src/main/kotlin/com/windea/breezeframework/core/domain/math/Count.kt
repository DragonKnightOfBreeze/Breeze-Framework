package com.windea.breezeframework.core.domain.math

import com.windea.breezeframework.core.domain.math.Count.Companion.magicValues

data class Count @PublishedApi internal constructor(
	val value: Int,
	val totalValue: Int
) {
	companion object {
		const val infinity = -1
		
		val magicValues = mutableSetOf(
			infinity
		)
	}
	
	
	operator fun plus(other: Int): Count =
		if(value in magicValues) this else Count((value + other).coerceIn(1, totalValue), totalValue)
	
	operator fun minus(other: Int): Count =
		if(value in magicValues) this else Count((value - other).coerceIn(1, totalValue), totalValue)
	
	infix fun totalPlus(other: Int): Count =
		if(totalValue + other <= 0) Count(0, 0) else Count(value.coerceAtMost(totalValue + other), totalValue + other)
	
	infix fun totalMinus(other: Int): Count =
		if(totalValue - other <= 0) Count(0, 0) else Count(value.coerceAtMost(totalValue - other), totalValue - other)
}


infix fun Int.countTo(that: Int) = if(this in magicValues) Count(this, that) else Count(this.coerceIn(1, that), that)
