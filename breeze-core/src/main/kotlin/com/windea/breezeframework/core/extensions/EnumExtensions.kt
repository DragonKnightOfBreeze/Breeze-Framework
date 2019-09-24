@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.annotations.api.*
import com.windea.breezeframework.core.enums.core.*

/**判断两个枚举的枚举值名是否相等。*/
@OutlookImplementationApi
inline infix fun <T : Enum<T>> T.nameEquals(other: T): Boolean {
	return this.name == other.name
}

/**判断两个枚举的枚举值名是否相等。忽略大小写。*/
@OutlookImplementationApi
inline infix fun <T : Enum<T>> T.nameEqualsIc(other: T): Boolean {
	return this.name equalsIc other.name
}

/**判断两个枚举的枚举值名是否相等。忽略显示格式[LetterCase]。*/
inline infix fun <T : Enum<T>> T.nameEqualsIlc(other: T): Boolean {
	return this.name equalsIlc other.name
}
