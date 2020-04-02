@file:JvmName("DataClassExtensions")
@file:Suppress("DuplicatedCode", "UNCHECKED_CAST")

package com.windea.breezeframework.core.extensions

import kotlin.reflect.*

//https://github.com/consoleau/kassava

//为了避免污染Any?的代码提示，不要定义为Any?的扩展方法
//可以使用Kotlin委托为接口委托实现这些方法，但是结合Kotlin反射使用可能出现问题

/**通过选择并比较指定类型中的属性，判断两个对象是否相等。特殊对待数组类型，并且默认递归执行操作。*/
inline fun <T : Any> equalsBy(target:T?, other:Any?, deepOp:Boolean = true, selector:T.() -> Array<*>):Boolean {
	return when {
		target === other -> true
		target == null || target.javaClass != other?.javaClass -> false
		else -> target.selector().zip((other as T).selector()).all { (a, b) -> a.smartEquals(b, deepOp) }
	}
}

/**通过选择指定类型中的属性，得到指定对象的哈希码。特殊对待数组类型，并且默认递归执行操作。*/
inline fun <T : Any> hashCodeBy(target:T?, deepOp:Boolean = true, selector:T.() -> Array<*>):Int {
	return target?.selector()?.fold(1) { r, k -> 31 * r + k.smartHashCode(deepOp) } ?: 0
}

/**通过选择指定类型中的属性引用，将指定对象转化为字符串。特殊对待数组类型，并且默认递归执行操作。*/
inline fun <T : Any> toStringBy(target:T?, deepOp:Boolean = true, selector:T.() -> Array<KProperty0<*>>):String {
	return when {
		target == null -> "null"
		else -> target.selector().associateBy({ it.name }, { it.get() }).joinToString(", ", "(", ")") { (k, v) ->
			"$k=${v.smartToString(deepOp)}"
		}.let { "${target.javaClass.simpleName}$it" }
	}
}


/**智能地判断两个对象是否相等。特殊对待数组类型，默认递归执行操作。*/
@PublishedApi
internal fun Any?.smartEquals(other:Any?, deepOp:Boolean = true):Boolean {
	return when {
		this !is Array<*> || other !is Array<*> -> this == other
		!deepOp -> this.contentEquals(other)
		else -> this.contentDeepEquals(other)
	}
}

/**智能地得到当前对象的哈希码。特殊对待数组类型，默认递归执行操作。*/
@PublishedApi
internal fun Any?.smartHashCode(deepOp:Boolean = true):Int {
	return when {
		this !is Array<*> -> this.hashCode()
		!deepOp -> this.contentHashCode()
		else -> this.contentDeepHashCode()
	}
}

/**智能地将当前对象转化为字符串。特殊对待数组类型，默认递归执行操作。*/
@PublishedApi
internal fun Any?.smartToString(deepOp:Boolean = true):String {
	return when {
		this !is Array<*> -> this.toString()
		!deepOp -> this.contentToString()
		else -> this.contentDeepToString()
	}
}
