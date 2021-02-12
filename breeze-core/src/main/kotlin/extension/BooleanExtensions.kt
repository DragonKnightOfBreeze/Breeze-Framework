// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("BooleanExtensions")
@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extension

import com.windea.breezeframework.core.annotation.*
import kotlin.contracts.*

//region Optional Operation Extensions
/**如果为null，则返回true，否则返回自身。*/
@UselessCallOnNotNullType
@JvmSynthetic
@InlineOnly
inline fun Boolean?.orTrue(): Boolean = this ?: true

/**如果为null，则返回false，否则返回自身。*/
@UselessCallOnNotNullType
@JvmSynthetic
@InlineOnly
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

/**合并作为参数的值，将当前布尔值转化为对象。*/
fun Boolean?.coalesce(trueValue: String, falseValue: String, nullValue: String = falseValue): String {
	return if(this == null) nullValue else if(this) trueValue else falseValue
}
//endregion

//region Convert Extensions
/**将当前布尔值转化为字节类型的1或0。*/
fun Boolean.toByte(): Byte = if(this) 1 else 0

/**将当前布尔值转化为短整数类型的1或0。*/
fun Boolean.toShort(): Short = if(this) 1 else 0

/**将当前布尔值转化为整数类型的1或0。*/
fun Boolean.toInt(): Int = if(this) 1 else 0

/**将当前布尔值转化为长整数类型的1或0。*/
fun Boolean.toLong(): Long = if(this) 1 else 0

/**将当前布尔值转化为单精度浮点数类型的1或0。*/
fun Boolean.toFloat(): Float = if(this) 1f else 0f

/**将当前布尔值转化为双精度浮点数类型的1或0。*/
fun Boolean.toDouble(): Double = if(this) 1.0 else 0.0

/**将当前布尔值转化为以"true"和"false"表示的字符串。*/
fun Boolean.toStringTrueFalse(): String = if(this) "true" else "false"

/**将当前布尔值转化为以"yes"和"no"表示的字符串。*/
fun Boolean.toStringYesNo(): String = if(this) "yes" else "no"

/**将当前布尔值转化为以"on"和"off"表示的字符串。*/
fun Boolean.toStringOnOff(): String = if(this) "on" else "off"
//endregion
