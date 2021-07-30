// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("RandomExtensions")

package icu.windea.breezeframework.core.extension

import icu.windea.breezeframework.core.annotation.*
import java.math.*
import java.util.*
import kotlin.math.*
import kotlin.random.Random

/**得到随机的字节。*/
fun Random.nextByte(): Byte {
	return nextInt(Byte.MIN_VALUE.toInt(), Byte.MAX_VALUE.toInt()).toByte()
}

/**得到随机的字节。*/
fun Random.nextByte(until: Byte): Byte {
	return nextInt(until.toInt()).toByte()
}

/**得到随机的字节。*/
fun Random.nextByte(from: Byte, until: Byte): Byte {
	return nextInt(from.toInt(), until.toInt()).toByte()
}

/**得到随机的短整数。*/
fun Random.nextShort(): Short {
	return nextInt(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
}

/**得到随机的短整数。*/
fun Random.nextShort(until: Short): Short {
	return nextInt(until.toInt()).toShort()
}

/**得到随机的短整数。*/
fun Random.nextShort(from: Short, until: Short): Short {
	return nextInt(from.toInt(), until.toInt()).toShort()
}

/**得到随机的单精度浮点数。*/
fun Random.nextFloat(until: Float): Float {
	return nextFloat(0.0f, until)
}

/**得到随机的单精度浮点数。*/
fun Random.nextFloat(from: Float, until: Float): Float {
	checkRangeBounds(from, until)
	val size = until - from
	val r = if(size.isInfinite() && from.isFinite() && until.isFinite()) {
		val r1 = nextFloat() * (until / 2 - from / 2)
		from + r1 + r1
	} else {
		from + nextFloat() * size
	}
	return if(r >= until) until.nextDown() else r
}

internal fun checkRangeBounds(from: Float, until: Float) = require(until > from) { boundsErrorMessage(from, until) }

internal fun boundsErrorMessage(from: Any, until: Any) = "Random range is empty: [$from, $until)."

/**得到随机的字符。*/
fun Random.nextChar(): Char {
	return nextInt(Char.MIN_VALUE.code, Char.MAX_VALUE.code).toChar()
}

/**得到随机的字符。*/
fun Random.nextChar(until: Char): Char {
	return nextInt(until.code).toChar()
}

/**得到随机的字符。*/
fun Random.nextChar(from: Char, until: Char): Char {
	return nextInt(from.code, until.code).toChar()
}

/**得到随机的大整数。*/
@UnstableApi
fun Random.nextBigInteger(): BigInteger {
	return nextLong().toBigInteger()
}

/**得到随机的大整数。*/
@UnstableApi
fun Random.nextBigInteger(until: BigInteger): BigInteger {
	return nextLong(until.toLongOrMax()).toBigInteger()
}

/**得到随机的大整数。*/
@UnstableApi
fun Random.nextBigInteger(from: BigInteger, until: BigInteger): BigInteger {
	return nextLong(from.toLongOrMax(), until.toLongOrMax()).toBigInteger()
}

/**得到随机的大数字。*/
@UnstableApi
fun Random.nextBigDecimal(mathContext: MathContext = MathContext.UNLIMITED): BigDecimal {
	return nextDouble().toBigDecimal(mathContext)
}

/**得到随机的大数字。*/
@UnstableApi
fun Random.nextBigDecimal(until: BigDecimal, mathContext: MathContext = MathContext.UNLIMITED): BigDecimal {
	return nextDouble(until.toDoubleOrMax()).toBigDecimal(mathContext)
}

/**得到随机的大数字。*/
@UnstableApi
fun Random.nextBigDecimal(from: BigDecimal, until: BigDecimal, mathContext: MathContext = MathContext.UNLIMITED): BigDecimal {
	return nextDouble(from.toDoubleOrMax(), until.toDoubleOrMax()).toBigDecimal(mathContext)
}

/**得到随机的无符号字节。*/
@ExperimentalUnsignedTypes
fun Random.nextUByte(): UByte {
	return nextInt(UByte.MIN_VALUE.toInt(), UByte.MAX_VALUE.toInt()).toUByte()
}

/**得到随机的无符号字节。*/
@ExperimentalUnsignedTypes
fun Random.nextUByte(until: UByte): UByte {
	return nextInt(until.toInt()).toUByte()
}

/**得到随机的无符号字节。*/
@ExperimentalUnsignedTypes
fun Random.nextUByte(from: UByte, until: UByte): UByte {
	return nextInt(from.toInt(), until.toInt()).toUByte()
}

/**得到随机的无符号短整数。*/
@ExperimentalUnsignedTypes
fun Random.nextUShort(): UShort {
	return nextInt(UShort.MIN_VALUE.toInt(), UShort.MAX_VALUE.toInt()).toUShort()
}

/**得到随机的无符号短整数。*/
@ExperimentalUnsignedTypes
fun Random.nextUShort(until: UShort): UShort {
	return nextInt(until.toInt()).toUShort()
}

/**得到随机的无符号短整数。*/
@ExperimentalUnsignedTypes
fun Random.nextUShort(from: UShort, until: UShort): UShort {
	return nextInt(from.toInt(), until.toInt()).toUShort()
}


/**
 * 得到由[source]中的字符组成的长度为[length]的字符串。
 */
fun Random.nextString(source: String, length: Int): String {
	val chars = source.toCharArray()
	val size = chars.size
	return when {
		length < 0 -> throw IllegalArgumentException("Length cannot be negetive.")
		length == 0 -> ""
		else -> {
			buildString {
				repeat(length) {
					append(chars[nextInt(size)])
				}
			}
		}
	}
}

/**
 * 得到随机的UUID。
 */
fun Random.nextUuid(): UUID {
	return UUID.randomUUID()
}

/**
 * 得到随机的UUID字符串。
 */
fun Random.nextUuidString(): String {
	return nextUuid().toString()
}


/**
 * 得到随机的字符。
 */
fun Random.nextElement(string: String): Char {
	val length = string.length
	return when(length) {
		0 -> throw IllegalArgumentException("Elements cannot be empty.")
		1 -> string[0]
		else -> string[nextInt(length)]
	}
}

/**
 * 得到随机的元素。
 */
fun Random.nextElement(elements: CharArray): Char {
	val size = elements.size
	return when(size) {
		0 -> throw IllegalArgumentException("Elements cannot be empty.")
		1 -> elements[0]
		else -> elements[nextInt(size)]
	}
}

/**
 * 得到随机的元素。
 */
fun <T> Random.nextElement(elements: Array<out T>): T {
	val size = elements.size
	return when(size) {
		0 -> throw IllegalArgumentException("Elements cannot be empty.")
		1 -> elements[0]
		else -> elements[nextInt(size)]
	}
}

/**
 * 得到随机的元素。
 */
fun <T> Random.nextElement(elements: List<T>): T {
	val size = elements.size
	return when(size) {
		0 -> throw IllegalArgumentException("Elements cannot be empty.")
		1 -> elements[0]
		else -> elements[nextInt(size)]
	}
}


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
fun Random.checkChance(chance: Float, condition: Boolean): Boolean {
	return condition && checkChance(chance)
}

/**检查一个几率发生的事件是否发生，附带额外条件。*/
fun Random.checkChance(chance: Float, predicate: () -> Boolean): Boolean {
	return predicate() && checkChance(chance)
}
