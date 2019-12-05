@file:Suppress("NOTHING_TO_INLINE", "unused")

package com.windea.breezeframework.core.extensions

import java.util.*
import kotlin.random.*
import kotlin.random.Random

/**得到指定范围内的随机单精度浮点数。包含上下限。可指定0-20的精确度，默认为3。*/
fun Random.nextFloat(range: ClosedFloatingPointRange<Float>, precision: Int = 2): Float {
	val fixedPrecision = precision.coerceIn(0, 20)
	val ratio = 10.pow(fixedPrecision)
	return this.nextInt((range.start * ratio).toInt()..(range.endInclusive * ratio).toInt()) / ratio.toFloat()
}

/**得到指定范围内的随机双精度浮点数。包含上下限。可指定0-20的精确度，默认为3。*/
fun Random.nextDouble(range: ClosedFloatingPointRange<Double>, precision: Int = 2): Double {
	val fixedPrecision = precision.coerceIn(0, 20)
	val ratio = 10.pow(fixedPrecision)
	return this.nextLong((range.start * ratio).toLong()..(range.endInclusive * ratio).toLong()) / ratio.toDouble()
}


/**得到指定相反数对应范围内的随机整数。默认上下限为-1和1。*/
inline fun Random.nextOpsInt(limit: Int = 1): Int = this.nextInt(-limit..limit)

/**得到指定相反数对应范围内的随机长整数。默认上下限为-1和1。*/
inline fun Random.nextOpsLong(limit: Long = 1): Long = this.nextLong(-limit..limit)

/**得到指定相反数对应范围内的随机单精度浮点数。默认上下限为-1和1。*/
inline fun Random.nextOpsFloat(limit: Float = 1f): Float = this.nextFloat(-limit..limit)

/**得到指定相反数对应范围内的随机双精度浮点数。默认上下限为-1和1。*/
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
