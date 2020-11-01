// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("RandomExtensions")
@file:Suppress("NOTHING_TO_INLINE", "unused")

package com.windea.breezeframework.core.extensions

import java.util.*
import kotlin.random.*
import kotlin.random.Random

fun Random.nextByte(): Byte {
	return Random.nextInt(Byte.MIN_VALUE.toInt(), Byte.MAX_VALUE.toInt()).toByte()
}

fun Random.nextByte(until: Byte): Byte {
	return Random.nextInt(until.toInt()).toByte()
}

fun Random.nextByte(from: Byte, until: Byte): Byte {
	return Random.nextInt(from.toInt(), until.toInt()).toByte()
}

fun Random.nextByte(range: ClosedRange<Byte>): Byte {
	return Random.nextInt(range.start.toInt()..range.endInclusive.toInt()).toByte()
}


fun Random.nextShort(): Short {
	return Random.nextInt(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
}

fun Random.nextShort(until: Short): Short {
	return Random.nextInt(until.toInt()).toShort()
}

fun Random.nextShort(from: Short, until: Short): Short {
	return Random.nextInt(from.toInt(), until.toInt()).toShort()
}

fun Random.nextShort(range: ClosedRange<Short>): Short {
	return Random.nextInt(range.start.toInt()..range.endInclusive.toInt()).toShort()
}


/**得到指定范围内的随机单精度浮点数。包含上下限。可指定基于位数的-10到0的精确度，默认为-2。用0表示个位，用较大数表示较高位。*/
@JvmOverloads
fun Random.nextFloat(range: ClosedFloatingPointRange<Float>, precision: Int = -2): Float {
	require(precision in -10..0) { "Precision for next float operation should between -10 and 0, but was $precision." }

	val ratio = 10.positivePow(precision)
	return this.nextInt((range.start * ratio).toInt()..(range.endInclusive * ratio).toInt()) / ratio.toFloat()
}

/**得到指定范围内的随机双精度浮点数。包含上下限。可指定基于位数的0到10的精确度，默认为-2。用0表示个位，用较大数表示较高位。*/
@JvmOverloads
fun Random.nextDouble(range: ClosedFloatingPointRange<Double>, precision: Int = -2): Double {
	require(precision in -10..0) { "Precision for next double operation should between -10 and 0, but was $precision." }

	val ratio = 10.positivePow(precision)
	return this.nextLong((range.start * ratio).toLong()..(range.endInclusive * ratio).toLong()) / ratio.toDouble()
}


fun Random.nextChar(): Char {
	return Random.nextInt(Char.MIN_VALUE.toInt(), Char.MAX_VALUE.toInt()).toChar()
}

fun Random.nextChar(until: Char): Char {
	return Random.nextInt(until.toInt()).toChar()
}

fun Random.nextChar(from: Char, until: Char): Char {
	return Random.nextInt(from.toInt(), until.toInt()).toChar()
}

fun Random.nextChar(range: ClosedRange<Char>): Char {
	return Random.nextInt(range.start.toInt()..range.endInclusive.toInt()).toChar()
}


/**得到随机的UUID字符串。*/
fun Random.nextUUID(): String = UUID.randomUUID().toString()


/**检查一个几率发生的事件是否发生。*/
fun Random.checkChance(chance: Float): Boolean {
	require(chance in 0f..1f) { "Chance must between 0 and 1, but was $chance." }

	return when {
		chance == 1f -> true
		chance == 0f -> false
		else -> chance in 0f..this.nextFloat()
	}
}

/**检查一个几率发生的事件是否发生，附带额外条件。*/
fun Random.checkChance(chance: Float, condition: Boolean): Boolean = condition && checkChance(chance)

/**检查一个几率发生的事件是否发生，附带额外条件。*/
fun Random.checkChance(chance: Float, predicate: () -> Boolean): Boolean = predicate() && checkChance(chance)
