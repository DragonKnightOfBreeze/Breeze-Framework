/*******************************************************************************
 * Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
 * Breeze is blowing...
 ******************************************************************************/

@file:JvmName("RangeExtensions")
@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.annotations.*

//region build extensions
infix fun Long.downUntil(until: Byte): LongProgression {
	return LongProgression.fromClosedRange(this, until.toLong() - 1, -1L)
}

infix fun Byte.downUntil(until: Byte): IntProgression {
	return IntProgression.fromClosedRange(this.toInt(), until.toInt() - 1, -1)
}

infix fun Short.downUntil(until: Byte): IntProgression {
	return IntProgression.fromClosedRange(this.toInt(), until.toInt() - 1, -1)
}

infix fun Char.downUntil(until: Char): CharProgression {
	return CharProgression.fromClosedRange(this, until - 1, -1)
}

infix fun Int.downUntil(until: Int): IntProgression {
	return IntProgression.fromClosedRange(this, until - 1, -1)
}

infix fun Long.downUntil(until: Int): LongProgression {
	return LongProgression.fromClosedRange(this, until.toLong() - 1, -1L)
}

infix fun Byte.downUntil(until: Int): IntProgression {
	return IntProgression.fromClosedRange(this.toInt(), until - 1, -1)
}

infix fun Short.downUntil(until: Int): IntProgression {
	return IntProgression.fromClosedRange(this.toInt(), until - 1, -1)
}

infix fun Int.downUntil(until: Long): LongProgression {
	return LongProgression.fromClosedRange(this.toLong(), until - 1, -1L)
}

infix fun Long.downUntil(until: Long): LongProgression {
	return LongProgression.fromClosedRange(this, until - 1, -1L)
}

infix fun Byte.downUntil(until: Long): LongProgression {
	return LongProgression.fromClosedRange(this.toLong(), until - 1, -1L)
}

infix fun Short.downUntil(until: Long): LongProgression {
	return LongProgression.fromClosedRange(this.toLong(), until - 1, -1L)
}

infix fun Int.downUntil(until: Short): IntProgression {
	return IntProgression.fromClosedRange(this, until.toInt() - 1, -1)
}

infix fun Long.downUntil(until: Short): LongProgression {
	return LongProgression.fromClosedRange(this, until.toLong() - 1, -1L)
}

infix fun Byte.downUntil(until: Short): IntProgression {
	return IntProgression.fromClosedRange(this.toInt(), until.toInt() - 1, -1)
}

infix fun Short.downUntil(until: Short): IntProgression {
	return IntProgression.fromClosedRange(this.toInt(), until.toInt() - 1, -1)
}
//endregion

//region common extensions
inline fun <T:Comparable<T>,R:Comparable<R>> ClosedRange<T>.map(transform:(T)->R):ClosedRange<R> {
	return transform(start)..transform(endInclusive)
}


/**限定在0和1之间。*/
inline fun Float.coerceIn(): Float = this.coerceIn(0f, 1f)

/**限定在0和1之间。*/
inline fun Double.coerceIn(): Double = this.coerceIn(0.0, 1.0)
//endregion

//region convert extensions
/**将范围转化为二元素元组。*/
inline fun <T : Comparable<T>> ClosedRange<T>.toPair(): Pair<T, T> = this.start to this.endInclusive


/**将范围转化为基于指定长度的循环范围。即，当上限或下限为负数时，尝试将其加上指定长度。*/
fun IntRange.toCircledRange(length: Int): IntRange {
	return when {
		this.last >= 0 && this.first < this.last -> this
		this.last < 0 && this.first >= 0 -> this.first..length + this.last
		this.last < 0 && this.first < this.last -> length + this.first..length + this.last
		else -> IntRange.EMPTY
	}
}
//endregion
