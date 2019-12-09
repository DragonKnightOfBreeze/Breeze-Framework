@file:Suppress("NOTHING_TO_INLINE", "DuplicatedCode")

package com.windea.breezeframework.core.extensions

import kotlin.math.*

//region operator overrides & infix extensions
/**进行整乘运算。*/
inline infix fun Int.exactTimes(other: Int): Int = this.times(other)

/**进行整乘运算。*/
inline infix fun Int.exactTimes(other: Long): Int = this.times(other).toInt()

/**进行整乘运算。*/
inline infix fun Int.exactTimes(other: Float): Int = this.times(other).toInt()

/**进行整乘运算。*/
inline infix fun Int.exactTimes(other: Double): Int = this.times(other).toInt()

/**进行整乘运算。*/
inline infix fun Long.exactTimes(other: Int): Long = this.times(other)

/**进行整乘运算。*/
inline infix fun Long.exactTimes(other: Long): Long = this.times(other)

/**进行整乘运算。*/
inline infix fun Long.exactTimes(other: Float): Long = this.times(other).toLong()

/**进行整乘运算。*/
inline infix fun Long.exactTimes(other: Double): Long = this.times(other).toLong()


/**进行整除运算。*/
inline infix fun Int.exactDiv(other: Int): Int = this.div(other)

/**进行整除运算。*/
inline infix fun Int.exactDiv(other: Long): Int = this.div(other).toInt()

/**进行整除运算。*/
inline infix fun Int.exactDiv(other: Float): Int = this.div(other).toInt()

/**进行整除运算。*/
inline infix fun Int.exactDiv(other: Double): Int = this.div(other).toInt()

/**进行整除运算。*/
inline infix fun Long.exactDiv(other: Int): Long = this.div(other)

/**进行整除运算。*/
inline infix fun Long.exactDiv(other: Long): Long = this.div(other)

/**进行整除运算。*/
inline infix fun Long.exactDiv(other: Float): Long = this.div(other).toLong()

/**进行整除运算。*/
inline infix fun Long.exactDiv(other: Double): Long = this.div(other).toLong()


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
/**将当前整数转化为二进制字符串。*/
inline fun Int.toBinaryString(): String = Integer.toBinaryString(this)

/**将当前整数转化为八进制字符串。*/
inline fun Int.toHexString(): String = Integer.toHexString(this)

/**将当前整数转化为十六进制字符串。*/
inline fun Int.toOctalString(): String = Integer.toOctalString(this)


/**将当前数字转化为指定的数字类型。*/
inline fun <reified T : Number> Number.to(): T {
	//performance note: approach to 1/5
	return when(val typeName = T::class.java.name) {
		"java.lang.Integer" -> this.toInt() as T
		"java.lang.Long" -> this.toLong() as T
		"java.lang.Float" -> this.toFloat() as T
		"java.lang.Double" -> this.toDouble() as T
		"java.lang.Byte" -> this.toByte() as T
		"java.lang.Short" -> this.toShort() as T
		"java.math.BigInteger" -> this.toString().toBigInteger() as T
		"java.math.BigDecimal" -> this.toString().toBigDecimal() as T
		else -> throw UnsupportedOperationException("Unsupported reified type parameter '$typeName'.")
	}
}


/**将当前整数转化为对应的枚举值。如果转化失败，则转化为默认值。*/
inline fun <reified T : Enum<T>> Int.toEnumValue(): T = enumValues<T>().getOrDefault(this, enumValues<T>().first())

/**将当前整数转化为对应的枚举值。如果转化失败，则转化为null。*/
inline fun <reified T : Enum<T>> Int.toEnumValueOrNull(): T? = enumValues<T>().getOrNull(this)
//endregion
