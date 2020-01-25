@file:JvmName("EnumExtensions")
@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extensions

/**判断两个枚举值的名字是否相等。*/
inline infix fun <T : Enum<T>> T.nameEquals(other: T): Boolean = this.name == other.name

/**判断两个枚举的名字是否相等，忽略大小写。*/
inline infix fun <T : Enum<T>> T.nameEqualsIc(other: T): Boolean = this.name equalsIc other.name

///**
// * 判断两个枚举的枚举值名是否相等，忽略显示格式。
// *
// * @see com.windea.breezeframework.core.enums.text.LetterCase
// */
//inline infix fun <T : Enum<T>> T.nameEqualsIlc(other: T): Boolean {
//	return this.name equalsIlc other.name
//}
