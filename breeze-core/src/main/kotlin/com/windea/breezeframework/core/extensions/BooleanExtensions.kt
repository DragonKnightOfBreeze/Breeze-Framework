@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extensions

/**如果为null，则返回true，否则返回本身。用于链式调用。*/
inline fun Boolean?.orTrue(): Boolean = this ?: true

/**如果为null，则返回false，否则返回本身。用于链式调用。*/
inline fun Boolean?.orFalse(): Boolean = this ?: false


/**转化为1或0。*/
inline fun Boolean.toInt(): Int = if(this) 1 else 0

/**转化为1f或0f。*/
inline fun Boolean.toFloat(): Float = if(this) 1f else 0f

/**转化为1.0或0.0。*/
inline fun Boolean.toDouble(): Double = if(this) 1.0 else 0.0

/**转化为指定的正数或负数。默认为1或-1。*/
inline fun Boolean.toOpsInt(value: Int = 1): Int = if(this) value else -value

/**转化为指定的正数或负数。默认为1f或-1f。*/
inline fun Boolean.toOpsFloat(value: Float = 1f): Float = if(this) value else -value

/**转化为指定的正数或负数。默认为1f或-1f。*/
inline fun Boolean.toOpsDouble(value: Double = 1.0): Double = if(this) value else -value
