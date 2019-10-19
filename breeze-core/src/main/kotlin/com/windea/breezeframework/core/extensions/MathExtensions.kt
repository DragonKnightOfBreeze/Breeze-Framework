@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.annotations.api.*
import java.math.*
import kotlin.math.*

/**进行乘方运算。*/
fun Int.pow(n: Int): Int = this.toFloat().pow(n).toInt()

/**进行乘方运算。*/
fun Int.pow(x: Float): Float = this.toFloat().pow(x)

/**进行乘方运算。*/
fun Int.pow(x: Double): Double = this.toDouble().pow(x)

/**进行乘方运算。*/
fun Float.pow(x: Double): Double = this.toDouble().pow(x)

/**进行乘方运算。*/
fun Double.pow(x: Float): Double = this.pow(x.toDouble())


/**精确到指定位数，适用四舍五入。*/
fun Float.round(precision: Int): Float {
	require(precision > 0) { "Precision for round operation must be positive." }
	
	val ratio = 10.pow(precision)
	return (this * ratio).roundToInt().toFloat() / ratio
}

/**精确到指定位数，适用四舍五入。*/
fun Double.round(precision: Int): Double {
	require(precision > 0) { "Precision for round operation must be positive." }
	
	val ratio = 10.pow(precision)
	return (this * ratio).roundToLong().toDouble() / ratio
}


/**得到指定整数的阶乘。*/
@LowPerformanceApi
fun Int.factorial(): BigInteger = this.toBigInteger().privateFactorial()

/**得到指定整数的阶乘。*/
@LowPerformanceApi
fun Long.factorial(): BigInteger = this.toBigInteger().privateFactorial()

/**得到指定整数的阶乘。*/
@LowPerformanceApi
fun BigInteger.factorial(): BigInteger = this.privateFactorial()

private tailrec fun BigInteger.privateFactorial(result: BigInteger = 1.toBigInteger()): BigInteger {
	require(this > 0.toBigInteger()) { "Number for factorial operation must be positive." }
	
	if(this == 1.toBigInteger()) return result
	return (this - 1.toBigInteger()).privateFactorial(this * result)
}


/**得到指定整数的累加。*/
@LowPerformanceApi
fun Int.cumulative(): BigInteger = this.toBigInteger().privateCumulative()

/**得到指定整数的累加。*/
@LowPerformanceApi
fun Long.cumulative(): BigInteger = this.toBigInteger().privateCumulative()

/**得到指定整数的累加。*/
@LowPerformanceApi
fun BigInteger.cumulative(): BigInteger = this.privateCumulative()

private tailrec fun BigInteger.privateCumulative(result: BigInteger = 1.toBigInteger()): BigInteger {
	require(this > 0.toBigInteger()) { "Number for cumulative operation must be positive." }
	
	if(this == 1.toBigInteger()) return result
	return (this - 1.toBigInteger()).privateCumulative(this + result)
}
