@file:Suppress("NOTHING_TO_INLINE", "FunctionName")

package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.annotations.api.*
import kotlin.math.*

//REGION Operator overrides & infix extensions

/**进行乘方运算。*/
inline infix fun Int.`^`(n: Int) = this.pow(n)

/**进行乘方运算。*/
inline infix fun Int.`^`(x: Float) = this.pow(x)

/**进行乘方运算。*/
inline infix fun Int.`^`(x: Double) = this.pow(x)

/**进行乘方运算。*/
inline infix fun Float.`^`(n: Int) = this.pow(n)

/**进行乘方运算。*/
inline infix fun Float.`^`(x: Float) = this.pow(x)

/**进行乘方运算。*/
inline infix fun Float.`^`(x: Double) = this.pow(x)

/**进行乘方运算。*/
inline infix fun Double.`^`(n: Int) = this.pow(n)

/**进行乘方运算。*/
inline infix fun Double.`^`(x: Float) = this.pow(x)

/**进行乘方运算。*/
inline infix fun Double.`^`(x: Double) = this.pow(x)


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


/**判断是否约等于另一个数，可指定精确度。默认为0.1。*/
fun Number?.nearlyEqualsTo(other: Number?, precision: Float = 0.1f): Boolean {
	return when {
		this == null && other == null -> true
		this == null || other == null -> false
		else -> abs(this.toFloat() - other.toFloat()) <= precision
	}
}

//REGION Convert extensions

/**转化为二进制字符串。*/
@OutlookImplementationApi
inline fun Int.toBinaryString(): String = Integer.toBinaryString(this)

/**转化为八进制字符串。*/
@OutlookImplementationApi
inline fun Int.toHexString(): String = Integer.toHexString(this)

/**转化为十六进制字符串。*/
@OutlookImplementationApi
inline fun Int.toOctalString(): String = Integer.toOctalString(this)


/**转化为指定的数字类型。*/
inline fun <reified T : Number> Number.to(): T {
	//performance note: approach to 1/5
	val typeName = T::class.java.name
	return when(typeName[10]) {
		'I' -> this.toInt() as T
		'L' -> this.toLong() as T
		'F' -> this.toFloat() as T
		'D' -> this.toDouble() as T
		'B' -> this.toByte() as T
		'S' -> this.toShort() as T
		else -> throw IllegalArgumentException("Illegal reified type parameter '$typeName'. Not supported.")
	}
}


/**将当前整数转化为对应的枚举值。如果转化失败，则转化为默认值。*/
inline fun <reified T : Enum<T>> Int.toEnumValue(): T = enumValues<T>().getOrDefault(this, enumValues<T>().first())

/**将当前整数转化为对应的枚举值。如果转化失败，则转化为null。*/
inline fun <reified T : Enum<T>> Int.toEnumValueOrNull(): T? = enumValues<T>().getOrNull(this)
