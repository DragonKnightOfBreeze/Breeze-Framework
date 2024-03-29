// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("NumberExtensions")
@file:Suppress("NOTHING_TO_INLINE")

package icu.windea.breezeframework.core.extension

import icu.windea.breezeframework.core.annotation.*
import kotlin.math.*

//region common extensions
/**如果为null，则返回0，否则返回自身。*/
@UselessCallOnNotNullType
@JvmSynthetic
@InlineOnly
inline fun Byte?.orZero(): Byte {
	return this ?: 0
}

/**如果为null，则返回0，否则返回自身。*/
@UselessCallOnNotNullType
@JvmSynthetic
@InlineOnly
inline fun Short?.orZero(): Short {
	return this ?: 0
}

/**如果为null，则返回0，否则返回自身。*/
@UselessCallOnNotNullType
@JvmSynthetic
@InlineOnly
inline fun Int?.orZero(): Int {
	return this ?: 0
}

/**如果为null，则返回0，否则返回自身。*/
@UselessCallOnNotNullType
@JvmSynthetic
@InlineOnly
inline fun Long?.orZero(): Long {
	return this ?: 0
}

/**如果为null，则返回0，否则返回自身。*/
@UselessCallOnNotNullType
@JvmSynthetic
@InlineOnly
inline fun Float?.orZero(): Float {
	return this ?: 0f
}

/**如果为null，则返回0，否则返回自身。*/
@UselessCallOnNotNullType
@JvmSynthetic
@InlineOnly
inline fun Double?.orZero(): Double {
	return this ?: 0.0
}


/**如果为0，则返回null，否则返回自身。*/
@JvmSynthetic
@InlineOnly
inline fun Byte.orNull(): Byte? {
	return if(this == 0.toByte()) null else this
}

/**如果为0，则返回null，否则返回自身。*/
@JvmSynthetic
@InlineOnly
inline fun Short.orNull(): Short? {
	return if(this == 0.toShort()) null else this
}

/**如果为0，则返回null，否则返回自身。*/
@JvmSynthetic
@InlineOnly
inline fun Int.orNull(): Int? {
	return if(this == 0) null else this
}

/**如果为0，则返回null，否则返回自身。*/
@JvmSynthetic
@InlineOnly
inline fun Long.orNull(): Long? {
	return if(this == 0L) null else this
}

/**如果为0，则返回null，否则返回自身。*/
@JvmSynthetic
@InlineOnly
inline fun Float.orNull(): Float? {
	return if(this == 0F) null else this
}

/**如果为0，则返回null，否则返回自身。*/
@JvmSynthetic
@InlineOnly
inline fun Double.orNull(): Double? {
	return if(this == 0.0) null else this
}


/**进行一次计算并将结果转化为字节型。*/
inline fun Byte.exact(block: (Byte) -> Number): Byte {
	return block(this).toByte()
}

/**进行一次计算并将结果转化为短整型。*/
inline fun Short.exact(block: (Short) -> Number): Short {
	return block(this).toShort()
}

/**进行一次计算并将结果转化为整型。*/
inline fun Int.exact(block: (Int) -> Number): Int {
	return block(this).toInt()
}

/**进行一次计算并将结果转化为长整型。*/
inline fun Long.exact(block: (Long) -> Number): Long {
	return block(this).toLong()
}

/**进行一次计算并将结果转化为单精度浮点型。*/
inline fun Float.exact(block: (Float) -> Number): Float {
	return block(this).toFloat()
}

/**进行一次计算并将结果转化为双精度浮点型。*/
inline fun Double.exact(block: (Double) -> Number): Double {
	return block(this).toDouble()
}


/**得到指定位数的数字。用0表示个位，用较大数表示较高位。*/
fun Int.getDigitNumber(index: Int, radix: Int = 10): Int {
	require(index >= 0) { "Index must be non-negative, but was $index." }
	return this / radix.positivePow(index) % radix
}

/**得到指定位数的数字。用0表示个位，用较大数表示较高位。*/
fun Long.getDigitNumber(index: Int, radix: Int = 10): Long {
	require(index >= 0) { "Index must be non-negative, but was $index." }
	return this / radix.positivePow(index) % radix
}


/**得到从最低位到最高位的每位数字组成的数组。*/
fun Int.getDigitNumbers(radix: Int = 10): IntArray {
	val size = this.toString().length
	var temp = this
	val result = IntArray(size)
	for(i in 0 until size) {
		result[i] = temp % radix
		temp /= radix
	}
	return result
}

/**得到从最低位到最高位的每位数字组成的数组。*/
fun Long.getDigitNumbers(radix: Int = 10): LongArray {
	val size = this.toString().length
	var temp = this
	val result = LongArray(size)
	for(i in 0 until size) {
		result[i] = temp % radix
		temp /= radix
	}
	return result
}


/**判断两个数是否近似相等。需要指定对应小数部分的精确度。当差值的绝对值小于此精确度时，认为两数近似相等。*/
fun Number?.nearlyEquals(other: Number?, precision: Float): Boolean {
	return when {
		this == null && other == null -> true
		this == null || other == null -> false
		else -> abs(this.toFloat() - other.toFloat()) < precision
	}
}
//endregion

//region convert extensions
/**将当前数字转化为指定的数字类型。如果转化失败或者不支持指定的数字类型，则抛出异常。*/
@Deprecated("Use this.convert<T>()", ReplaceWith("this.convert<T>()"))
inline fun <reified T : Number> Number.toNumber(): T {
	return when(val typeName = T::class.java.name) {
		"java.lang.Integer" -> this.toInt() as T
		"java.lang.Long" -> this.toLong() as T
		"java.lang.Float" -> this.toFloat() as T
		"java.lang.Double" -> this.toDouble() as T
		"java.lang.Byte" -> this.toByte() as T
		"java.lang.Short" -> this.toShort() as T
		"java.math.BigInteger" -> this.toString().toBigInteger() as T
		"java.math.BigDecimal" -> this.toString().toBigDecimal() as T
		else -> throw UnsupportedOperationException("Unsupported reified number type: '$typeName'.")
	}
}

/**将当前数字转化为指定的数字类型。如果转化失败或者不支持指定的数字类型，则返回null。*/
@Deprecated("Use Number.convertOrNull<T>()", ReplaceWith("this.convertOrNull<T>()"))
inline fun <reified T : Number> Number.toNumberOrNull(): T? {
	return when(T::class.java.name) {
		"java.lang.Integer" -> this.toInt() as T?
		"java.lang.Long" -> this.toLong() as T?
		"java.lang.Float" -> this.toFloat() as T?
		"java.lang.Double" -> this.toDouble() as T?
		"java.lang.Byte" -> this.toByte() as T?
		"java.lang.Short" -> this.toShort() as T?
		"java.math.BigInteger" -> this.toString().toBigIntegerOrNull() as T?
		"java.math.BigDecimal" -> this.toString().toBigDecimalOrNull() as T?
		else -> null
	}
}


/**将当前整数转化为对应的枚举值。如果转化失败，则转化为默认值。*/
inline fun <reified T : Enum<T>> Int.toEnumValue(): T {
	return enumValues<T>().getOrElse(this) { enumValues<T>().first() }
}

/**将当前整数转化为对应的枚举值。如果转化失败，则转化为null。*/
inline fun <reified T : Enum<T>> Int.toEnumValueOrNull(): T? {
	return enumValues<T>().getOrNull(this)
}

@ExperimentalUnsignedTypes
inline fun UInt.toChar(): Char {
	return toInt().toChar()
}

@ExperimentalUnsignedTypes
inline fun ULong.toChar(): Char {
	return toLong().toInt().toChar()
}
//endregion
