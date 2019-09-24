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
