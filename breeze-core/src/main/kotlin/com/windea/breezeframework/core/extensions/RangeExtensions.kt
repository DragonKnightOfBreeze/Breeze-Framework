@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extensions

//REGION Global extensions

/**取在指定范围内的夹值。*/
infix fun <T : Comparable<T>> T.clamp(range: ClosedRange<T>): T = this.coerceIn(range)

//REGION Build extensions

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


/**限定在0和1之间。*/
inline fun Float.coerceIn(): Float = this.coerceIn(0f, 1f)

/**限定在0和1之间。*/
inline fun Double.coerceIn(): Double = this.coerceIn(0.0, 1.0)

/**限制在指定的相反数之间。*/
inline fun Int.coerceInOps(value: Int): Int = this.coerceIn(-value, value)

/**限制在指定的相反数之间。*/
inline fun Long.coerceInOps(value: Long): Long = this.coerceIn(-value, value)

/**限制在指定的相反数之间。默认为-1和1。*/
inline fun Float.coerceInOps(value: Float = 1f): Float = this.coerceIn(-value, value)

/**限制在指定的相反数之间。默认为-1和1。*/
inline fun Double.coerceInOps(value: Double = 1.0): Double = this.coerceIn(-value, value)
