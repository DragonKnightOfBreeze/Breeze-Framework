@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extensions

import kotlin.math.*

/**进行乘方运算。*/
inline fun Int.pow(n: Int): Int = this.toFloat().pow(n).toInt()

/**进行乘方运算。*/
inline fun Int.pow(n: Float): Float = this.toFloat().pow(n)

/**进行乘方运算。*/
inline fun Int.pow(n: Double): Double = this.toDouble().pow(n)


/**限定在0和1之间。*/
fun Float.coerceIn(): Float = this.coerceIn(0f, 1f)

/**限定在0和1之间。*/
fun Double.coerceIn(): Double = this.coerceIn(0.0, 1.0)


/**限制在指定的相反数之间。*/
fun Int.coerceInOps(value: Int): Int = this.coerceIn(-value, value)

/**限制在指定的相反数之间。*/
fun Long.coerceInOps(value: Long): Long = this.coerceIn(-value, value)

/**限制在指定的相反数之间。默认为-1和1。*/
fun Float.coerceInOps(value: Float = 1f): Float = this.coerceIn(-value, value)

/**限制在指定的相反数之间。默认为-1和1。*/
fun Double.coerceInOps(value: Double = 1.0): Double = this.coerceIn(-value, value)
