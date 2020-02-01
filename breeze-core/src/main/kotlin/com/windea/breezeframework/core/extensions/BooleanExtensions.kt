@file:JvmName("BooleanExtensions")
@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extensions

import kotlin.contracts.*

//region common extensions
/**如果为null，则返回true，否则返回自身。*/
inline fun Boolean?.orTrue(): Boolean = this ?: true

/**如果为null，则返回false，否则返回自身。*/
inline fun Boolean?.orFalse(): Boolean = this ?: false


/**如果为true，则执行一段代码。总是返回自身。*/
inline fun Boolean.onTrue(block: () -> Unit): Boolean {
	contract {
		callsInPlace(block, InvocationKind.AT_MOST_ONCE)
	}
	if(this) block()
	return this
}

/**如果为false，则执行一段代码。总是返回自身。*/
inline fun Boolean.onFalse(block: () -> Unit): Boolean {
	contract {
		callsInPlace(block, InvocationKind.AT_MOST_ONCE)
	}
	if(!this) block()
	return this
}
//endregion

//region convert extensions
/**转化为1或0。*/
inline fun Boolean.toInt(): Int = if(this) 1 else 0

/**转化为1或0。*/
inline fun Boolean.toLong(): Long = if(this) 1 else 0

/**转化为1或0。*/
inline fun Boolean.toFloat(): Float = if(this) 1f else 0f

/**转化为1或0。*/
inline fun Boolean.toDouble(): Double = if(this) 1.0 else 0.0


/**转化为指定的相反数。默认为1或-1。*/
inline fun Boolean.toOpsInt(value: Int = 1): Int = if(this) value else -value

/**转化为指定的相反数。默认为1或-1。*/
inline fun Boolean.toOpsLong(value: Long = 1): Long = if(this) value else -value

/**转化为指定的相反数。默认为1或-1。*/
inline fun Boolean.toOpsFloat(value: Float = 1f): Float = if(this) value else -value

/**转化为指定的相反数。默认为1或-1。*/
inline fun Boolean.toOpsDouble(value: Double = 1.0): Double = if(this) value else -value


/**转化为指定的相反数。默认为1或-1。如果为null，则转化为0。*/
inline fun Boolean?.toOpsInt(value: Int = 1): Int = this?.toOpsInt(value) ?: 0

/**转化为指定的相反数。默认为1或-1。如果为null，则转化为0。*/
inline fun Boolean?.toOpsLong(value: Long = 1): Long = this?.toOpsLong(value) ?: 0

/**转化为指定的相反数。默认为1或-1。如果为null，则转化为0。*/
inline fun Boolean?.toOpsFloat(value: Float = 1f): Float = this?.toOpsFloat(value) ?: 0f

/**转化为指定的相反数。默认为1或-1。如果为null，则转化为0。*/
inline fun Boolean?.toOpsDouble(value: Double = 1.0): Double = this?.toOpsDouble(value) ?: 0.0
//endregion
