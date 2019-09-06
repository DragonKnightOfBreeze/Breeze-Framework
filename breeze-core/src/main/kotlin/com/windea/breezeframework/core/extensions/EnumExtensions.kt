@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.annotations.api.*
import com.windea.breezeframework.core.enums.*

/**判断两个枚举的枚举常量名是否相等。*/
@OutlookImplementationApi
inline infix fun <E : Enum<E>> E.nameEquals(other: E): Boolean {
	return this.name == other.name
}

/**判断两个枚举的枚举常量名是否相等。忽略大小写。*/
@OutlookImplementationApi
inline infix fun <E : Enum<E>> E.nameEqualsIc(other: E): Boolean {
	return this.name equalsIc other.name
}

/**判断两个枚举的枚举常量名是否相等。忽略显示格式[LetterCase]。*/
inline infix fun <E : Enum<E>> E.nameEqualsIlc(other: E): Boolean {
	return this.name equalsIlc other.name
}
