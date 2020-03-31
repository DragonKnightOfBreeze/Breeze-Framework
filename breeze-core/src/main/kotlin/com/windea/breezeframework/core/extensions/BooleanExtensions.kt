@file:JvmName("BooleanExtensions")
@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.annotations.*
import kotlin.contracts.*

//region common extensions
/**如果为null，则返回true，否则返回自身。*/
@UselessCallOnNotNullType
@JvmSynthetic
inline fun Boolean?.orTrue(): Boolean = this ?: true

/**如果为null，则返回false，否则返回自身。*/
@UselessCallOnNotNullType
@JvmSynthetic
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
inline fun Boolean.onFalse(block:() -> Unit):Boolean {
	contract {
		callsInPlace(block, InvocationKind.AT_MOST_ONCE)
	}
	if(!this) block()
	return this
}
//endregion

//region convert extensions
/**将当前布尔值转化为对象。*/
inline fun <T> Boolean.asObject(trueValue:T, falseValue:T):T = if(this) trueValue else falseValue

/**将当前布尔值转化为字节类型的1或0。*/
inline fun Boolean.toByte():Byte = if(this) 1 else 0

/**将当前布尔值转化为短整数类型的1或0。*/
inline fun Boolean.toShort():Short = if(this) 1 else 0

/**将当前布尔值转化为整数类型的1或0。*/
inline fun Boolean.toInt():Int = if(this) 1 else 0

/**将当前布尔值转化为长整数类型的1或0。*/
inline fun Boolean.toLong():Long = if(this) 1 else 0

/**将当前布尔值转化为单精度浮点数类型的1或0。*/
inline fun Boolean.toFloat():Float = if(this) 1f else 0f

/**将当前布尔值转化为双精度浮点数类型的1或0。*/
inline fun Boolean.toDouble():Double = if(this) 1.0 else 0.0
//endregion
