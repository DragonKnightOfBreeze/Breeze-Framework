package com.windea.utility.common.extensions

import com.windea.utility.common.enums.*

/**判断两个枚举的枚举常量名是否相等。*/
infix fun <E : Enum<E>> E.nameEquals(other: E): Boolean {
	return this.name == other.name
}

/**判断两个枚举的枚举常量名是否相等。忽略大小写。*/
infix fun <E : Enum<E>> E.nameEqualsIc(other: E): Boolean {
	return this.name equalsIc other.name
}

/**判断两个枚举的枚举常量名是否相等。忽略显示格式[StringCase]。*/
infix fun <E : Enum<E>> E.nameEqualsIsc(other: E): Boolean {
	return this.name equalsIsc other.name
}
