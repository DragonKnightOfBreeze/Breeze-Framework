// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:Suppress("IMPLICIT_CAST_TO_ANY")

package com.windea.breezeframework.core.domain

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.core.extensions.*
import java.lang.reflect.*

//可以考虑将目标对象的类型也作为泛型参数，但是就目前来说，太过复杂，也没有必要
//暂不显式实现queryOrNull方法，由query方法委托实现

/**
 * 查询器。
 *
 * 查询器用于根据指定类型的对象，查询目标对象。
 */
@ComponentMarker
interface Querier<T : Any> {
	/**
	 * 根据指定类型的对象，查询目标查询对象。如果查询失败，则抛出异常。
	 */
	fun query(value: T, target: Any): Any?

	/**
	 * 根据指定类型的对象，查询目标查询对象。如果查询失败，则返回null。
	 */
	fun queryOrNull(value: T, target: Any): Any? {
		return runCatching { query(value, target) }.getOrNull()
	}

	companion object {
		//不需要进行注册
	}

	//region Default Query types
	/**
	 * 基于字符串的查询器。
	 *
	 * 支持[Array]，[List]，[Iterable]，[Map]，[Sequence]。
	 */
	object StringQuerier : Querier<String> {
		override fun query(value: String, target: Any): Any? {
			return try {
				when(target) {
					is Array<*> -> target.getOrNull(value.toInt())
					is List<*> -> target.getOrNull(value.toInt())
					is Iterable<*> -> target.elementAtOrNull(value.toInt())
					is Map<*, *> -> target.cast<Map<String, Any?>>()[value]
					is Sequence<*> -> target.elementAtOrNull(value.toInt())
					else -> throw UnsupportedOperationException("Cannot query '${target.javaClass.simpleName}' by string '$value'")
				}
			} catch(e: Exception) {
				throw UnsupportedOperationException("Cannot query '${target.javaClass.simpleName}' by string '$value'", e)
			}
		}
	}

	/**
	 * 基于正则表达式的查询器。
	 *
	 * 支持[Map]。
	 */
	object RegexQuerier : Querier<Regex> {
		override fun query(value: Regex, target: Any): Any? {
			return try {
				when(target) {
					//忽略数组、列表、序列等类型的查询对象
					is Map<*, *> -> target.cast<Map<String, Any?>>().filterKeys { it.matches(value) }
					else -> throw UnsupportedOperationException("Cannot query '${target.javaClass.simpleName}' by regex '$value'")
				}
			} catch(e: Exception) {
				throw UnsupportedOperationException("Cannot query '${target.javaClass.simpleName}' by regex '$value'", e)
			}
		}
	}

	/**
	 * 基于索引的查询器。
	 *
	 * 支持[Array]，[List]，[Iterable]，[Sequence]。
	 */
	object IndexQuerier : Querier<Int> {
		override fun query(value: Int, target: Any): Any? {
			return try {
				when(target) {
					is Array<*> -> target.getOrNull(value)
					is List<*> -> target.getOrNull(value)
					is Iterable<*> -> target.elementAtOrNull(value)
					is Sequence<*> -> target.elementAtOrNull(value)
					else -> throw UnsupportedOperationException("Cannot query '${target.javaClass.simpleName}' by index '$value'")
				}
			} catch(e: Exception) {
				throw UnsupportedOperationException("Cannot query '${target.javaClass.simpleName}' by index '$value'", e)
			}
		}
	}

	/**
	 * 基于索引范围的查询器。
	 *
	 * 支持[Array]，[List]，[Iterable]，[Sequence]。
	 */
	object IndexRangeQuerier : Querier<IntRange> {
		override fun query(value: IntRange, target: Any): Any? {
			return try {
				when(target) {
					is Array<*> -> target.toList().slice(value)
					is List<*> -> target.slice(value)
					is Iterable<*> -> target.toList().slice(value)
					is Sequence<*> -> target.toList().slice(value)
					else -> throw UnsupportedOperationException("Cannot query '${target.javaClass.simpleName}' by range '$value'")
				}
			} catch(e: Exception) {
				throw UnsupportedOperationException("Cannot query '${target.javaClass.simpleName}' by range '$value'", e)
			}
		}
	}

	/**
	 * 基于字符串，通过反射查询查询对象的字段和属性的值的查询器。
	 */
	object ReflectionQuerier : Querier<String> {
		override fun query(value: String, target: Any): Any? {
			try {
				val targetType = target.javaClass
				val field: Field? = runCatching { targetType.getDeclaredField(value) }.getOrNull()
				if(field != null) return field.apply { trySetAccessible() }.get(target)
				val getter: Method? = runCatching { targetType.getDeclaredMethod(getGetterName(value)) }.getOrNull()
				if(getter != null) return getter.apply { trySetAccessible() }.invoke(target)
				throw UnsupportedOperationException("Cannot query '${target.javaClass.simpleName}' by string '$value' use reflection.")
			} catch(e: Exception) {
				throw UnsupportedOperationException("Cannot query '${target.javaClass.simpleName}' by string '$value' use reflection.", e)
			}
		}

		private fun getGetterName(value: String): String {
			return "get" + value[0].toUpperCase() + value.drop(1)
		}
	}

	/**
	 * 基于字符串，通过反射查询查询对象的成员的查询器。仅返回第一个符合条件的结果。
	 *
	 * 支持[Field]，[Method]，[Annotation]。
	 */
	object ReflectionMemberQuerier : Querier<String> {
		override fun query(value: String, target: Any): Any? {
			try {
				val targetType = target.javaClass
				val result = when {
					value.startsWith("@") -> {
						val name = getAnnotationName(value)
						targetType.declaredAnnotations.find { it.javaClass.simpleName == name }
					}
					value.endsWith("()") -> {
						val name = getMethodName(value)
						targetType.declaredMethods.find { it.name == name }
					}
					else -> runCatching { targetType.getDeclaredField(value) }.getOrNull()
				}
				if(result != null) return result
				throw UnsupportedOperationException("Cannot query '${target.javaClass.simpleName}' by string '$value' use reflection.")
			} catch(e: Exception) {
				throw UnsupportedOperationException("Cannot query '${target.javaClass.simpleName}' by string '$value' use reflection.", e)
			}
		}

		private fun getAnnotationName(value: String): String {
			return value.drop(1)
		}

		private fun getMethodName(value: String): String {
			return value.dropLast(2)
		}
	}
	//endregion
}
