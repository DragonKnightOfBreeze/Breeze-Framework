// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("AnyExtensions")
@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")

package icu.windea.breezeframework.core.extension

import icu.windea.breezeframework.core.annotation.*
import icu.windea.breezeframework.core.model.*
import java.lang.reflect.*
import kotlin.reflect.*

fun Any.isNullLike(): Boolean {
	return when(this) {
		is Boolean -> !this
		is Number -> toString().let { it == "0" || it == "0.0" }
		is CharSequence -> isEmpty()
		is Array<*> -> isEmpty()
		is Collection<*> -> isEmpty()
		is Iterable<*> -> none()
		is Sequence<*> -> none()
		is Map<*, *> -> isEmpty()
		else -> false
	}
}

fun Any.isNotNullLike(): Boolean {
	return when(this) {
		is Boolean -> this
		is Number -> toString().let { it != "0" && it != "0.0" }
		is CharSequence -> isNotEmpty()
		is Array<*> -> isNotEmpty()
		is Collection<*> -> isNotEmpty()
		is Iterable<*> -> any()
		is Sequence<*> -> any()
		is Map<*, *> -> isNotEmpty()
		else -> false
	}
}

/**
 * 将当前对象转换为指定类型。如果转换失败，则抛出异常。
 */
inline fun <reified T> Any?.cast(): T {
	return this as T
}

/**
 * 将当前对象转换为指定类型。如果转换失败，则返回null。
 */
inline fun <reified T> Any?.castOrNull(): T? {
	return this as? T
}

/**
 * 智能判断当前对象与另一个对象是否值相等。特殊对待null值和数组类型。
 */
@OptIn(ExperimentalUnsignedTypes::class)
infix fun Any?.smartEquals(other: Any?): Boolean {
	return when {
		this == null && other == null -> true
		this == null || other == null -> false
		this === other -> true
		this is Array<*> && other is Array<*> -> this contentDeepEquals other
		this is ByteArray && other is ByteArray -> this contentEquals other
		this is ShortArray && other is ShortArray -> this contentEquals other
		this is IntArray && other is IntArray -> this contentEquals other
		this is LongArray && other is LongArray -> this contentEquals other
		this is FloatArray && other is FloatArray -> this contentEquals other
		this is DoubleArray && other is DoubleArray -> this contentEquals other
		this is UByteArray && other is UByteArray -> this contentEquals other
		this is UShortArray && other is UShortArray -> this contentEquals other
		this is UIntArray && other is UIntArray -> this contentEquals other
		this is ULongArray && other is ULongArray -> this contentEquals other
		this is CharArray && other is CharArray -> this contentEquals other
		this is BooleanArray && other is BooleanArray -> this contentEquals other
		else -> this == other
	}
}

/**
 * 智能得到当前对象的哈希码。特殊对待null值和数组类型。
 */
@OptIn(ExperimentalUnsignedTypes::class)
fun Any?.smartHashcode(): Int {
	return when {
		this == null -> 0
		this is Array<*> -> contentDeepHashCode()
		this is ByteArray -> contentHashCode()
		this is ShortArray -> contentHashCode()
		this is IntArray -> contentHashCode()
		this is LongArray -> contentHashCode()
		this is FloatArray -> contentHashCode()
		this is DoubleArray -> contentHashCode()
		this is UByteArray -> contentHashCode()
		this is UShortArray -> contentHashCode()
		this is UIntArray -> contentHashCode()
		this is ULongArray -> contentHashCode()
		this is CharArray -> contentHashCode()
		this is BooleanArray -> contentHashCode()
		else -> hashCode()
	}
}

/**
 * 智能将当前对象转化为字符串。特殊对待null值和数组类型。
 */
@OptIn(ExperimentalUnsignedTypes::class)
fun Any?.smartToString(): String {
	return when {
		this == null -> "null"
		this is Array<*> -> contentDeepToString()
		this is ByteArray -> contentToString()
		this is ShortArray -> contentToString()
		this is IntArray -> contentToString()
		this is LongArray -> contentToString()
		this is FloatArray -> contentToString()
		this is DoubleArray -> contentToString()
		this is UByteArray -> contentToString()
		this is UShortArray -> contentToString()
		this is UIntArray -> contentToString()
		this is ULongArray -> contentToString()
		this is CharArray -> contentToString()
		this is BooleanArray -> contentToString()
		else -> toString()
	}
}

//为了避免污染Any?的代码提示，不要定义为Any?的扩展方法
//可以使用Kotlin委托为接口委托实现这些方法，但是结合Kotlin反射使用可能出现问题

/**
 * 基于指定的属性判断两个对象是否值相等。特殊对待null值和数组类型。
 *
 * * 如果引用相等，则值相等。
 * * 如果类型不相等，则值不相等。
 * * 如果类型相等且未指定属性，则值相等。
 * * 如果类型相等且已指定属性，则参照其属性。
 * * 对于数组类型的属性，参照其内容。
 */
fun <T : Any> equalsBy(target: T?, other: Any?, selector: T.() -> Array<*>): Boolean {
	return when {
		target == null && other == null -> true
		target == null || other == null -> false
		target === other -> true
		target.javaClass != other.javaClass -> false
		else -> {
			val targetSelector = target.selector()
			val otherSelector = (other as T).selector()
			when {
				targetSelector.isEmpty() && otherSelector.isEmpty() -> true
				targetSelector.size != otherSelector.size -> false
				else -> targetSelector.zip(otherSelector).all { (a, b) -> a.smartEquals(b) }
			}
		}
	}
}

/**
 * 基于指定的属性得到指定对象的哈希码。特殊对待null值和数组类型。
 *
 * * 如果目标为`null`，则返回`0`.
 * * 如果未指定属性，则参照其类型。
 * * 如果已指定属性，则参照其属性。
 * * 对于数组类型的属性，参照其内容。
 * */
fun <T : Any> hashCodeBy(target: T?, selector: T.() -> Array<*>): Int {
	return when {
		target == null -> 0
		else -> {
			val targetSelector = target.selector()
			when {
				targetSelector.isEmpty() -> target.javaClass.hashCode()
				else -> targetSelector.map { it.smartHashcode() }.reduce { a, b -> a * 31 + b }
			}
		}
	}
}

/**
 * 基于指定的属性的名字-值元组，将指定对象转化为字符串。特殊对待null值和数组类型。
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
	simplifyClassName: Boolean = true, selector: T.() -> Array<Pair<String, *>>
): String {
	return when {
		target == null -> "null"
		else -> {
			val targetSelector = target.selector()
			val className = if(simplifyClassName) target.javaClass.simpleName else target.javaClass.name
			when {
				targetSelector.isEmpty() -> "$className$prefix$postfix"
				else -> buildString {
					var appendDelimiter = false
					append(className).append(prefix)
					for((k, v) in targetSelector) {
						if(appendDelimiter) append(delimiter) else appendDelimiter = true
						append(k).append("=").append(v.smartToString())
					}
					append(postfix)
				}
			}
		}
	}
}

/**
 * 基于指定的属性的引用，将指定对象转化为字符串。特殊对待null值和数组类型。
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
	simplifyClassName: Boolean = true, selector: T.() -> Array<KProperty0<*>>
): String {
	return when {
		target == null -> "null"
		else -> {
			val targetSelector = target.selector()
			val className = if(simplifyClassName) target.javaClass.simpleName else target.javaClass.name
			when {
				targetSelector.isEmpty() -> "$className$prefix$postfix"
				else -> buildString {
					var appendDelimiter = false
					append(className).append(prefix)
					for(p in targetSelector) {
						if(appendDelimiter) append(delimiter) else appendDelimiter = true
						append(p.name).append("=").append(p.get().smartToString())
					}
					append(postfix)
				}
			}
		}
	}
}

//得到带有泛型信息的类型

/**
 * 得到指定类型的带有泛型参数信息的Java类型对象。
 */
inline fun <reified T> javaTypeOf(): Type {
	return object : TypeReference<T>() {}.type
}

/**
 * 得到当前对象的带有泛型参数信息的Java类型对象。
 */
@Suppress("UNUSED_PARAMETER")
inline fun <reified T> javaTypeOf(target: T): Type {
	return object : TypeReference<T>() {}.type
}

//得到类型、方法、属性、参数等的名字
//无法直接通过方法的引用得到参数，也无法得到局部变量的任何信息

/**
 * 得到指定类型的名字。
 */
@TrickApi
inline fun <reified T> nameOf(): String? {
	return T::class.java.simpleName
}

/**
 * 得到指定项的名字。
 *
 * 适用于：类引用、属性引用、方法引用、实例。
 *
 * 不适用于：类型参数，参数，局部变量。
 */
@TrickApi
@JvmSynthetic
inline fun nameOf(target: Any?): String? {
	return when(target) {
		null -> null
		is Class<*> -> target.simpleName
		is KClass<*> -> target.simpleName
		is KCallable<*> -> target.name
		is KParameter -> target.name
		else -> target::class.java.simpleName
	}
}
