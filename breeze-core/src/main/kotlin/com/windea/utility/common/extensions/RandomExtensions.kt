package com.windea.utility.common.extensions

import java.util.*
import kotlin.random.Random

object RandomExtensions {
	/**得到随机的uuid。*/
	fun uuid(): String {
		return UUID.randomUUID().toString()
	}
	
	
	/**得到指定范围内的随机数。包含上下限。*/
	fun range(min: Int, max: Int): Int {
		return Random.nextInt(min, max + 1)
	}
	
	/**得到0到指定范围内的随机数。包含上下限。*/
	fun range(max: Int): Int = range(0, max)
	
	
	/**得到指定范围内的随机数。包含上下限。*/
	fun range(min: Long, max: Long): Long {
		return Random.nextLong(min, max + 1L)
	}
	
	/**得到0到指定范围内的随机数。包含上下限。*/
	fun range(max: Long): Long = range(0L, max)
	
	
	/**得到指定范围内的随机数。包含上下限。可指定0-10的精确度[precision]，默认为2。*/
	fun range(min: Float, max: Float, precision: Int = 2): Float {
		val fixedPrecision = precision.coerceIn(0, 10)
		val ratio = 10.pow(fixedPrecision)
		return range((min * ratio).toInt(), (max * ratio).toInt()) / ratio.toFloat()
	}
	
	/**得到0到指定范围内的随机数。包含上下限。可指定0-10的精确度[precision]，默认为2。*/
	fun range(max: Float, precision: Int = 2): Float = range(0f, max, precision)
	
	/**得到0到1的随机数。包含上下限。可指定0-10的精确度[precision]，默认为2。*/
	fun range01(precision: Int = 2): Float = range(0f, 1f, precision)
	
	/**得到-1到1的随机数。包含上下限。可指定0-10的精确度[precision]，默认为2。*/
	fun rangeAbs1(precision: Int = 2): Float = range(-1f, 1f, precision)
	
	
	/**得到指定范围内的随机数。包含上下限。可指定0-10的精确度[precision]，默认为2。*/
	fun range(min: Double, max: Double, precision: Int = 2): Double {
		val fixedPrecision = precision.coerceIn(0, 10)
		val ratio = 10.pow(fixedPrecision)
		return range((min * ratio).toInt(), (max * ratio).toInt()) / ratio.toDouble()
	}
	
	/**得到0到指定范围内的随机数。包含上下限。可指定0-10的精确度[precision]，默认为2。*/
	fun range(max: Double, precision: Int = 2): Double = range(0.0, max, precision)
	
	
	/**得到以指定数值为中心的浮动范围内的随机数。包含上下限。*/
	fun delta(number: Int, lowerLimit: Int, upperLimit: Int): Int {
		return number - lowerLimit + range(lowerLimit, upperLimit)
	}
	
	/**得到以指定数值为中心的浮动范围内的随机数。包含上下限。*/
	fun delta(number: Int, limit: Int): Int = delta(number, limit, limit)
	
	
	/**得到以指定数值为中心的浮动范围内的随机数。包含上下限。*/
	fun delta(number: Long, lowerLimit: Long, upperLimit: Long): Long {
		return number - lowerLimit + range(lowerLimit, upperLimit)
	}
	
	/**得到以指定数值为中心的浮动范围内的随机数。包含上下限。*/
	fun delta(number: Long, limit: Long): Long = delta(number, limit, limit)
	
	
	/**得到以指定数值为中心的浮动范围内的随机数。包含上下限。可指定0-10的精确度[precision]，默认为2。*/
	fun delta(number: Float, lowerLimit: Float, upperLimit: Float, precision: Int = 2): Float {
		return number - lowerLimit + range(lowerLimit, upperLimit, precision)
	}
	
	/**得到以指定数值为中心的浮动范围内的随机数。包含上下限。可指定0-10的精确度[precision]，默认为2。*/
	fun delta(number: Float, limit: Float, precision: Int = 2): Float = delta(number, limit, limit, precision)
	
	/**得到以指定数值为中心的以1为浮动范围的随机数。包含上下限。可指定0-10的精确度[precision]，默认为2。*/
	fun deltaAbs1(number: Float, precision: Int = 2) = delta(number, -1f, 1f, precision)
	
	
	/**得到以指定数值为中心的浮动范围内的随机数。包含上下限。可指定0-10的精确度[precision]，默认为2。*/
	fun delta(number: Double, lowerLimit: Double, upperLimit: Double, precision: Int = 2): Double {
		return number - lowerLimit + range(lowerLimit, upperLimit, precision)
	}
	
	/**得到以指定数值为中心的浮动范围内的随机数。包含上下限。可指定0-10的精确度[precision]，默认为2。*/
	fun delta(number: Double, limit: Double, precision: Int = 2): Double = delta(number, limit, limit, precision)
}
