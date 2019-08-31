package com.windea.utility.common.extensions

/**转化为1或0。*/
fun Boolean.toInt(): Int = if(this) 1 else 0

/**转化为1f或0f。*/
fun Boolean.toFloat(): Float = if(this) 1f else 0f

/**转化为1.0或0.0。*/
fun Boolean.toDouble(): Double = if(this) 1.0 else 0.0


/**转化为指定的正数或负数。默认为1或-1。*/
fun Boolean.toOpsInt(value: Int = 1): Int = if(this) value else -value

/**转化为指定的正数或负数。默认为1f或-1f。*/
fun Boolean.toOpsFloat(value: Float = 1f): Float = if(this) value else -value

/**转化为指定的正数或负数。默认为1f或-1f。*/
fun Boolean.toOpsDouble(value: Double = 1.0): Double = if(this) value else -value
