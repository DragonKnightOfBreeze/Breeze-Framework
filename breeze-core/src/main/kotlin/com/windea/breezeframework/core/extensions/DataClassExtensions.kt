@file:Suppress("DuplicatedCode")

package com.windea.breezeframework.core.extensions

import kotlin.reflect.*

//https://github.com/consoleau/kassava

//为了避免污染Any?的代码提示，不要定义为扩展方法

/**通过选择并比较指定类型中的属性，判断两个对象是否相等。特殊对待数组类型，默认递归执行操作。*/
inline fun <reified T> equalsBy(target: T?, other: Any?, deepOperation: Boolean = true,
	selector: T.() -> Array<*>): Boolean {
	return target === other || target != null && other is T &&
	       target.selector().zip(other.selector()) { a, b -> a.smartEquals(b, deepOperation) }.all { it }
}

/**通过选择并比较指定类型中的某个属性，判断两个对象是否相等。特殊对待数组类型，默认递归执行操作。*/
inline fun <reified T> equalsByOne(target: T?, other: Any?, deepOperation: Boolean = true,
	selector: T.() -> Any?): Boolean {
	return target === other || target != null && other is T &&
	       target.selector().smartEquals(other.selector(), deepOperation)
}

/**通过选择指定类型中的属性，得到指定对象的哈希码。特殊对待数组类型，默认递归执行操作。*/
inline fun <reified T> hashCodeBy(target: T?, deepOperation: Boolean = true, selector: T.() -> Array<*>): Int {
	if(target == null) return 0
	return target.selector().fold(1) { r, k -> 31 * r + k.smartHashCode(deepOperation) }
}

/**通过选择指定类型中的某个属性，得到指定对象的哈希码。特殊对待数组类型，默认递归执行操作。*/
inline fun <reified T> hashCodeByOne(target: T?, deepOperation: Boolean = true, selector: T.() -> Any?): Int {
	if(target == null) return 0
	return target.selector().smartHashCode(deepOperation)
}

/**通过选择指定类型中的属性，将指定对象转化为字符串。默认不忽略空值。默认使用简单类名。特殊对待数组类型，默认递归执行操作。*/
inline fun <reified T> toStringBy(target: T?, delimiter: String = ", ", prefix: String = "(",
	postfix: String = ")", omitNulls: Boolean = false, fullClassName: Boolean = false,
	deepOperation: Boolean = true, selector: T.() -> Array<Pair<String, *>>): String {
	if(target == null) return "null"
	val className = if(!fullClassName) T::class.java.simpleName else T::class.java.name
	return target.selector().toMap()
		.let(omitNulls) { it.filterValueNotNull() }
		.joinToString(delimiter, className + prefix, postfix) { (k, v) -> "$k=${v.smartToString(deepOperation)}" }
}

/**通过选择指定类型中的属性引用，将指定对象转化为字符串。默认不忽略空值。默认使用简单类名。特殊对待数组类型，默认递归执行操作。*/
inline fun <reified T> toStringByRef(target: T?, delimiter: String = ", ", prefix: String = "(",
	postfix: String = ")", omitNulls: Boolean = false, fullClassName: Boolean = false,
	deepOperation: Boolean = true, selector: T.() -> Array<KProperty0<*>>): String {
	if(target == null) return "null"
	val className = if(!fullClassName) T::class.java.simpleName else T::class.java.name
	return target.selector().associateBy({ it.name }, { it.get() })
		.let(omitNulls) { it.filterValueNotNull() }
		.joinToString(delimiter, className + prefix, postfix) { (k, v) -> "$k=${v.smartToString(deepOperation)}" }
}
