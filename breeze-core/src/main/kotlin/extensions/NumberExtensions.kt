@file:JvmName("NumberExtensions")
@file:Suppress("NOTHING_TO_INLINE", "DuplicatedCode")

package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.core.domain.text.*
import java.text.*
import java.util.*
import kotlin.math.*

//region common extensions
/**如果为null，则返回0，否则返回自身。*/
@UselessCallOnNotNullType
@JvmSynthetic
inline fun Byte?.orZero(): Byte = this ?: 0

/**如果为null，则返回0，否则返回自身。*/
@UselessCallOnNotNullType
@JvmSynthetic
inline fun Short?.orZero(): Short = this ?: 0

/**如果为null，则返回0，否则返回自身。*/
@UselessCallOnNotNullType
@JvmSynthetic
inline fun Int?.orZero(): Int = this ?: 0

/**如果为null，则返回0，否则返回自身。*/
@UselessCallOnNotNullType
@JvmSynthetic
inline fun Long?.orZero(): Long = this ?: 0

/**如果为null，则返回0，否则返回自身。*/
@UselessCallOnNotNullType
@JvmSynthetic
inline fun Float?.orZero(): Float = this ?: 0f

/**如果为null，则返回0，否则返回自身。*/
@UselessCallOnNotNullType
@JvmSynthetic
inline fun Double?.orZero(): Double = this ?: 0.0


/**进行一次计算并将结果转化为整型。*/
inline fun Int.exact(block: (Int) -> Number): Int = block(this).toInt()

/**进行一次计算并将结果转化为长整型。*/
inline fun Long.exact(block: (Long) -> Number): Long = block(this).toLong()

/**进行一次计算并将结果转化为单精度浮点型。*/
inline fun Float.exact(block: (Float) -> Number): Float = block(this).toFloat()

/**进行一次计算并将结果转化为双精度浮点型。*/
inline fun Double.exact(block: (Double) -> Number): Double = block(this).toDouble()


/**得到指定位数的数字。用0表示个位，用较大数表示较高位。*/
fun Int.getDigitNumber(index: Int): Int {
	require(index >= 0) { "Index must be non-negative, but was $index." }
	return this / 10.positivePow(index) % 10
}

/**得到指定位数的数字。用0表示个位，用较大数表示较高位。*/
fun Long.getDigitNumber(index: Int): Long {
	require(index >= 0) { "Index must be non-negative, but was $index." }
	return this / 10.positivePow(index) % 10
}


/**根据指定的格式化类型，格式化当前数字。可以指定可选的语言环境。*/
@UnstableImplementationApi
fun Number.formatBy(type:NumberFormatType,locale:Locale? = null):String{
	return getNumberFormatInstance(type,locale?:Locale.getDefault(Locale.Category.FORMAT)).format(this)
}

/**根据指定的格式化类型，格式化当前数字。可以指定可选的语言环境。可以进行额外的配置。*/
@UnstableImplementationApi
fun Number.formatBy(type:NumberFormatType,locale:Locale? = null, configBlock:NumberFormat.()->Unit):String {
	return getNumberFormatInstance(type,locale?:Locale.getDefault(Locale.Category.FORMAT)).apply(configBlock).format(this)
}

private fun getNumberFormatInstance(type:NumberFormatType,locale:Locale):NumberFormat {
	return when(type){
		NumberFormatType.Default -> NumberFormat.getInstance(locale)
		NumberFormatType.Number -> NumberFormat.getNumberInstance(locale)
		NumberFormatType.Integer -> NumberFormat.getIntegerInstance(locale)
		NumberFormatType.Percent -> NumberFormat.getPercentInstance(locale)
		NumberFormatType.Currency -> NumberFormat.getCurrencyInstance(locale)
		else -> throw UnsupportedOperationException("Target number format type is not yet supported.")
	}
}


/**判断两个数是否近似相等。需要指定对应小数部分的精确度。当差值的绝对值小于此精确度时，认为两数近似相等。*/
fun Number?.equalsNearly(other: Number?, precision: Float): Boolean {
	return when {
		this == null && other == null -> true
		this == null || other == null -> false
		else -> abs(this.toFloat() - other.toFloat()) < precision
	}
}
//endregion

//region convert extensions
/**将当前整数转化为从低位到高位的每位数字组成的数组。*/
fun Int.toDigitNumberArray(): IntArray {
	val size = this.toString().length
	var temp = this
	val result = IntArray(size)
	for(i in 0 until size) {
		result[i] = temp % 10
		temp /= 10
	}
	return result
}

/**将当前长整数转化为从低位到高位的每位数字组成的数组。*/
fun Long.toDigitNumberArray(): LongArray {
	val size = this.toString().length
	var temp = this
	val result = LongArray(size)
	for(i in 0 until size) {
		result[i] = temp % 10
		temp /= 10
	}
	return result
}


/**将当前数字转化为指定的数字类型。如果转化失败或者不支持指定的数字类型，则抛出异常。*/
inline fun <reified T : Number> Number.toNumber(): T {
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
		else -> throw UnsupportedOperationException("Unsupported reified number type: '$typeName'.")
	}
}

/**将当前数字转化为指定的数字类型。如果转化失败或者不支持指定的数字类型，则返回null。*/
inline fun <reified T : Number> Number.toNumberOrNull(): T? {
	//performance note: approach to 1/5
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
inline fun <reified T : Enum<T>> Int.toEnumValue(): T = enumValues<T>().getOrDefault(this, enumValues<T>().first())

/**将当前整数转化为对应的枚举值。如果转化失败，则转化为null。*/
inline fun <reified T : Enum<T>> Int.toEnumValueOrNull(): T? = enumValues<T>().getOrNull(this)
//endregion