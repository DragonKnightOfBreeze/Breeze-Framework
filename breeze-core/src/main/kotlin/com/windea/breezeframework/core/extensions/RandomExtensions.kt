@file:Suppress("NOTHING_TO_INLINE", "unused")

package com.windea.breezeframework.core.extensions

import java.util.*
import kotlin.random.*
import kotlin.random.Random

/**得到指定范围内的随机单精度浮点数。包含上下限。可指定0-10的精确度，默认为2。*/
fun Random.nextFloat(range: ClosedFloatingPointRange<Float>, precision: Int = 2): Float {
	val fixedPrecision = precision.coerceIn(0, 10)
	val ratio = 10.pow(fixedPrecision)
	return this.nextInt((range.start * ratio).toInt()..(range.endInclusive * ratio).toInt()) / ratio.toFloat()
}

/**得到指定范围内的随机双精度浮点数。包含上下限。可指定0-20的精确度，默认为2。*/
fun Random.nextDouble(range: ClosedFloatingPointRange<Double>, precision: Int = 2): Double {
	val fixedPrecision = precision.coerceIn(0, 20)
	val ratio = 10.pow(fixedPrecision)
	return this.nextLong((range.start * ratio).toLong()..(range.endInclusive * ratio).toLong()) / ratio.toDouble()
}

/**得到随机的UUID字符串。*/
fun Random.nextUUID(): String = UUID.randomUUID().toString()


/**得到指定相反数对应范围内的随机整数。默认上下限为-1和1。*/
inline fun Random.nextOpsInt(limit: Int = 1): Int = this.nextInt(-limit..limit)

/**得到指定相反数对应范围内的随机长整数。默认上下限为-1和1。*/
inline fun Random.nextOpsLong(limit: Long = 1): Long = this.nextLong(-limit..limit)

/**得到指定相反数对应范围内的随机单精度浮点数。默认上下限为-1和1。*/
inline fun Random.nextOpsFloat(limit: Float = 1f): Float = this.nextFloat(-limit..limit)

/**得到指定相反数对应范围内的随机双精度浮点数。默认上下限为-1和1。*/
inline fun Random.nextOpsDouble(limit: Double = 1.0): Double = this.nextDouble(-limit..limit)
