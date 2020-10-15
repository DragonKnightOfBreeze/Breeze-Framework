// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:Suppress("IMPLICIT_CAST_TO_ANY", "UNCHECKED_CAST")

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
	fun <R> query(queryString: T, target: Any): R

	/**
	 * 根据指定类型的对象，查询目标查询对象。如果查询失败，则返回null。
	 */
	fun <R> queryOrNull(queryString: T, target: Any): R? {
		return runCatching { query<R>(queryString, target) }.getOrNull()
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
		override fun <R> query(queryString: String, target: Any): R {
			return try {
				when(target) {
					is Array<*> -> target.getOrNull(queryString.toInt())
					is List<*> -> target.getOrNull(queryString.toInt())
					is Iterable<*> -> target.elementAtOrNull(queryString.toInt())
					is Map<*, *> -> target.cast<Map<String, Any?>>()[queryString]
					is Sequence<*> -> target.elementAtOrNull(queryString.toInt())
					else -> throw UnsupportedOperationException("Invalid string '$queryString' for query.")
				} as R
			} catch(e: Exception) {
				throw IllegalArgumentException("Cannot query '${target.javaClass.simpleName}' by string '$queryString'.",e)
			}
		}
	}

	/**
	 * 基于正则表达式的查询器。
	 *
	 * 支持[Map]。
	 */
	object RegexQuerier : Querier<Regex> {
		override fun <R> query(queryString: Regex, target: Any): R {
			return try {
				when(target) {
					//忽略数组、列表、序列等类型的查询对象
					is Map<*, *> -> target.cast<Map<String, Any?>>().filterKeys { it.matches(queryString) }
					else -> throw UnsupportedOperationException("Invalid regex '$queryString' for query.")
				} as R
			} catch(e: Exception) {
				throw IllegalArgumentException ("Cannot query '${target.javaClass.simpleName}' by regex '$queryString'.",e)
			}
		}
	}

	/**
	 * 基于索引的查询器。
	 *
	 * 支持[Array]，[List]，[Iterable]，[Sequence]。
	 */
	object IndexQuerier : Querier<Int> {
		override fun <R> query(queryString: Int, target: Any):  R {
			return try {
				when(target) {
					is Array<*> -> target.getOrNull(queryString)
					is List<*> -> target.getOrNull(queryString)
					is Iterable<*> -> target.elementAtOrNull(queryString)
					is Sequence<*> -> target.elementAtOrNull(queryString)
					else -> throw UnsupportedOperationException("Invalid index '$queryString' for query.")
				} as R
			} catch(e: Exception) {
				throw IllegalArgumentException("Cannot query '${target.javaClass.simpleName}' by index '$queryString'.", e)
			}
		}
	}

	/**
	 * 基于索引范围的查询器。
	 *
	 * 支持[Array]，[List]，[Iterable]，[Sequence]。
	 */
	object IndexRangeQuerier : Querier<IntRange> {
		override fun <R> query(queryString: IntRange, target: Any): R {
			return try {
				when(target) {
					is Array<*> -> target.toList().slice(queryString)
					is List<*> -> target.slice(queryString)
					is Iterable<*> -> target.toList().slice(queryString)
					is Sequence<*> -> target.toList().slice(queryString)
					else -> throw UnsupportedOperationException("Invalid range '$queryString' for query.")
				} as R
			} catch(e: Exception) {
				throw IllegalArgumentException("Cannot query '${target.javaClass.simpleName}' by range '$queryString'.", e)
			}
		}
	}

	/**
	 * 基于字符串，通过反射查询查询对象的字段和属性的值的查询器。
	 */
	object ReflectionQuerier : Querier<String> {
		override fun <R> query(queryString: String, target: Any): R {
			try {
				val targetType = target.javaClass
				val field: Field? = runCatching { targetType.getDeclaredField(queryString) }.getOrNull()
				if(field != null) return field.apply { trySetAccessible() }.get(target) as R
				val getter: Method? = runCatching { targetType.getDeclaredMethod(getGetterName(queryString)) }.getOrNull()
				if(getter != null) return getter.apply { trySetAccessible() }.invoke(target) as R
				throw UnsupportedOperationException("Invalid string '$queryString' for query use reflection.")
			} catch(e: Exception) {
				throw IllegalArgumentException("Cannot query '${target.javaClass.simpleName}' by string '$queryString' use reflection.", e)
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
		override fun <R> query(queryString: String, target: Any): R {
			try {
				val targetType = target.javaClass
				val result = when {
					queryString.startsWith("@") -> {
						val name = getAnnotationName(queryString)
						targetType.declaredAnnotations.find { it.javaClass.simpleName == name }
					}
					queryString.endsWith("()") -> {
						val name = getMethodName(queryString)
						targetType.declaredMethods.find { it.name == name }
					}
					else -> runCatching { targetType.getDeclaredField(queryString) }.getOrNull()
				} as R?
				if(result != null) return result
				throw UnsupportedOperationException("Invalid string '$queryString' for query use reflection.")
			} catch(e: Exception) {
				throw IllegalArgumentException("Cannot query '${target.javaClass.simpleName}' by string '$queryString' use reflection.", e)
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
