@file:JvmName("AnyExtensions")

package com.windea.breezeframework.core.extensions

/**智能地判断两个对象是否相等。特殊对待数组类型，默认递归执行操作。*/
@JvmOverloads
fun Any?.smartEquals(other: Any?, deepOperation: Boolean = true) = when {
	this !is Array<*> || other !is Array<*> -> this == other
	!deepOperation -> this contentEquals other
	else -> this contentDeepEquals other
}

/**智能地得到当前对象的哈希码。特殊对待数组类型，默认递归执行操作。*/
@JvmOverloads
fun Any?.smartHashCode(deepOperation: Boolean = true) = when {
	this !is Array<*> -> this.hashCode()
	!deepOperation -> this.contentHashCode()
	else -> this.contentDeepHashCode()
}

/**智能地将当前对象转化为字符串。特殊对待数组类型，默认递归执行操作。*/
@JvmOverloads
fun Any?.smartToString(deepOperation: Boolean = true) = when {
	this !is Array<*> -> this.toString()
	!deepOperation -> this.contentToString()
	else -> this.contentDeepToString()
}


/**将当前对象转化为字符串。如果为null，则转化为空字符串。*/
@Deprecated("Redundant extension method: Please consider checking nullability instead.", ReplaceWith("toString()"))
fun Any?.toStringOrEmpty(): String = this?.toString() ?: ""
