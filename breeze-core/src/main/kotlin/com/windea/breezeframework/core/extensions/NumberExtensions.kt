@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.annotations.api.*
import java.math.*
import kotlin.math.*


/**进行乘方运算。*/
inline fun Int.pow(n: Int): Int = this.toFloat().pow(n).toInt()

/**进行乘方运算。*/
inline fun Int.pow(x: Float): Float = this.toFloat().pow(x)

/**进行乘方运算。*/
inline fun Int.pow(x: Double): Double = this.toDouble().pow(x)

/**进行乘方运算。*/
inline fun Float.pow(x: Double): Double = this.toDouble().pow(x)

/**进行乘方运算。*/
inline fun Double.pow(x: Float): Double = this.pow(x.toDouble())


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
	require(this > 0.toBigInteger()) { "Number for factorial operation should be a positive number." }
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
	require(this > 0.toBigInteger()) { "Number for cumulative operation should be a positive number." }
	if(this == 1.toBigInteger()) return result
	return (this - 1.toBigInteger()).privateCumulative(this + result)
}
