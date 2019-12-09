@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extensions

/**判断两个枚举的枚举值名是否相等。*/
inline infix fun <T : Enum<T>> T.nameEquals(other: T): Boolean {
	return this.name == other.name
}

/**忽略大小写，判断两个枚举的枚举值名是否相等。*/
inline infix fun <T : Enum<T>> T.nameEqualsIc(other: T): Boolean {
	return this.name equalsIc other.name
}

/**
 * 忽略显示格式，判断两个枚举的枚举值名是否相等。
 *
 * @see com.windea.breezeframework.core.enums.core.LetterCase
 */
inline infix fun <T : Enum<T>> T.nameEqualsIlc(other: T): Boolean {
	return this.name equalsIlc other.name
}
