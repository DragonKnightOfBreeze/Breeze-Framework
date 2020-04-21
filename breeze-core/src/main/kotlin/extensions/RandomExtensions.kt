@file:JvmName("RandomExtensions")
@file:Suppress("NOTHING_TO_INLINE", "unused")

package com.windea.breezeframework.core.extensions

import java.util.*
import kotlin.random.*
import kotlin.random.Random

/**得到指定范围内的随机单精度浮点数。包含上下限。可指定基于位数的-10到0的精确度，默认为-2。用0表示个位，用较大数表示较高位。*/
@JvmOverloads
fun Random.nextFloat(range: ClosedFloatingPointRange<Float>, precision: Int = -2): Float {
	require(precision in -10..0) { "Precision for next float operation must between -10 and 0, but was $precision." }

	val ratio = 10.positivePow(precision)
	return this.nextInt((range.start * ratio).toInt()..(range.endInclusive * ratio).toInt()) / ratio.toFloat()
}

/**得到指定范围内的随机双精度浮点数。包含上下限。可指定基于位数的0到10的精确度，默认为-2。用0表示个位，用较大数表示较高位。*/
@JvmOverloads
fun Random.nextDouble(range: ClosedFloatingPointRange<Double>, precision: Int = -2): Double {
	require(precision in -10..0) { "Precision for next double operation must between -10 and 0, but was $precision." }

	val ratio = 10.positivePow(precision)
	return this.nextLong((range.start * ratio).toLong()..(range.endInclusive * ratio).toLong()) / ratio.toDouble()
}


/**得到指定相反数对应范围内的随机整数。默认上下限为-1和1。*/
@JvmOverloads
inline fun Random.nextOpsInt(limit: Int = 1): Int = this.nextInt(-limit..limit)

/**得到指定相反数对应范围内的随机长整数。默认上下限为-1和1。*/
@JvmOverloads
inline fun Random.nextOpsLong(limit: Long = 1): Long = this.nextLong(-limit..limit)

/**得到指定相反数对应范围内的随机单精度浮点数。默认上下限为-1和1。*/
@JvmOverloads
inline fun Random.nextOpsFloat(limit: Float = 1f): Float = this.nextFloat(-limit..limit)

/**得到指定相反数对应范围内的随机双精度浮点数。默认上下限为-1和1。*/
@JvmOverloads
inline fun Random.nextOpsDouble(limit: Double = 1.0): Double = this.nextDouble(-limit..limit)


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
