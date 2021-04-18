// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("AnyExtensions")
@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")

package com.windea.breezeframework.core.extension

import com.windea.breezeframework.core.model.*
import kotlin.reflect.*

/**
 * 得到当前对象的带有泛型参数信息的Java类型对象。
 */
inline val <reified T> T.javaType get() = object : TypeReference<T>() {}.type

/**
 * 将当前对象转换为指定类型。如果转换失败，则抛出异常。
 */
inline fun <reified T> Any?.cast(): T = this as T

/**
 * 将当前对象转换为指定类型。如果转换失败，则返回null。
 */
inline fun <reified T> Any?.castOrNull(): T? = this as? T

/**
 * 智能地判断两个对象是否值相等。
 *
 * 特殊对待数组类型。
 */
fun Any?.equalsSmartly(other: Any?, deepOp: Boolean = true): Boolean {
	return when {
		this !is Array<*> || other !is Array<*> -> this == other
		!deepOp -> this.contentEquals(other)
		else -> this.contentDeepEquals(other)
	}
}

/**
 * 智能地得到指定对象的哈希码。
 *
 * 特殊对待数组类型。
 */
fun Any?.hashCodeSmartly(deepOp: Boolean = true): Int {
	return when {
		this !is Array<*> -> this.hashCode()
		!deepOp -> this.contentHashCode()
		else -> this.contentDeepHashCode()
	}
}

/**
 * 智能地将指定对象转化为字符串。
 *
 * 特殊对待数组类型。
 */
fun Any?.toStringSmartly(deepOp: Boolean = true): String {
	return when {
		this !is Array<*> -> this.toString()
		!deepOp -> this.contentToString()
		else -> this.contentDeepToString()
	}
}

//为了避免污染Any?的代码提示，不要定义为Any?的扩展方法
//可以使用Kotlin委托为接口委托实现这些方法，但是结合Kotlin反射使用可能出现问题

/**
 * 基于指定的属性，判断两个对象是否值相等。默认对数组类型递归执行操作。
 *
 * * 如果引用相等，则值相等。
 * * 如果类型不相等，则值不相等。
 * * 如果类型相等且未指定属性，则值相等。
 * * 如果类型相等且已指定属性，则参照其属性。
 * * 对于数组类型的属性，参照其内容。
 */
fun <T : Any> equalsBy(target: T?, other: Any?, deepOp: Boolean = true, selector: T.() -> Array<*>): Boolean {
	return when {
		target === other -> true
		target == null || target.javaClass != other?.javaClass -> false
		else -> with(target.selector()) {
			when {
				isEmpty() -> true
				else -> zip((other as T).selector()).all { (a, b) -> a.equalsSmartly(b, deepOp) }
			}
		}
	}
}

/**
 * 基于指定的属性，得到指定对象的哈希码。默认对数组类型递归执行操作。
 *
 * * 如果目标为`null`，则返回`0`.
 * * 如果未指定属性，则参照其类型。
 * * 如果已指定属性，则参照其属性。
 * * 对于数组类型的属性，参照其内容。
 * */
fun <T : Any> hashCodeBy(target: T?, deepOp: Boolean = true, selector: T.() -> Array<*>): Int {
	return when {
		target == null -> 0
		else -> with(target.selector()) {
			when {
				isEmpty() -> target.javaClass.hashCode()
				else -> map { it.hashCodeSmartly(deepOp) }.reduce { a, b -> a * 31 + b }
			}
		}
	}
}

/**
 * 基于指定的属性的名字-值元组，将指定对象转化为字符串。默认使用Kotlin数据类风格的输出，输出简单类名，并对数组类型递归执行操作。
 *
 * * 如果目标为`null`，则返回`"null"`。
 * * 如果未指定属性，则忽略属性的输出。
 * * 如果已指定属性，则输出属性的名字-值的键值对。
 * * 对于数组类型的属性，参照其内容。
 *
 * 参考：
 * * Kotlin数据类风格的输出：`Foo(bar=123)`。
 * * Java记录风格的输出：`Foo[bar=123]`。
 */
fun <T : Any> toStringBy(
	target: T?, delimiter: String = ", ", prefix: String = "(", postfix: String = ")",
	simplifyClassName: Boolean = true, deepOp: Boolean = true, selector: T.() -> Array<Pair<String, *>>
): String {
	return when {
		target == null -> "null"
		else -> with(target.selector()) {
			val className = if(simplifyClassName) target.javaClass.simpleName else target.javaClass.name
			when {
				isEmpty() -> "$className$prefix$postfix"
				else -> joinToString(delimiter, "$className$prefix", postfix) { (n, v) -> "$n=${v.toStringSmartly(deepOp)}" }
			}
		}
	}
}

/**
 * 基于指定的属性的引用，将指定对象转化为字符串。默认使用Kotlin数据类风格的输出，输出简单类名，并对数组类型递归执行操作。
 *
 * * 如果目标为`null`，则返回`"null"`。
 * * 如果未指定属性，则忽略属性的输出。
 * * 如果已指定属性，则输出属性的名字-值的键值对。
 * * 对于数组类型的属性，参照其内容。
 *
 * 参考：
 * * Kotlin数据类风格的输出：`Foo(bar=123)`。
 * * Java记录风格的输出：`Foo[bar=123]`。
 */
fun <T : Any> toStringByReference(
	target: T?, delimiter: String = ", ", prefix: String = "(", postfix: String = ")",
	simplifyClassName: Boolean = true, deepOp: Boolean = true, selector: T.() -> Array<KProperty0<*>>
): String {
	return when {
		target == null -> "null"
		else -> with(target.selector()) {
			val className = if(simplifyClassName) target.javaClass.simpleName else target.javaClass.name
			when {
				isEmpty() -> "$className$prefix$postfix"
				else -> joinToString(delimiter, "$className$prefix", postfix) { "${it.name}=${it.get().toStringSmartly(deepOp)}" }
			}
		}
	}
}
