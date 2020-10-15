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
interface Querier<T : Any,R> {
	/**
	 * 根据指定类型的对象，查询目标查询对象。如果查询失败，则抛出异常。
	 */
	fun query(queryObject: T, target: Any): R

	/**
	 * 根据指定类型的对象，查询目标查询对象。如果查询失败，则返回null。
	 */
	fun queryOrNull(queryObject: T, target: Any): R? {
		return runCatching { query(queryObject, target) }.getOrNull()
	}

	companion object {
		//不需要进行注册
	}

	//region Default Queriers
	/**
	 * 查询所有结果的查询器。
	 *
	 * 支持[Array]，[List]，[Iterable]，[Map]，[Sequence]。
	 */
	object AllResultsQuerier:Querier<String,List<Any?>>{
		override fun query(queryObject: String, target: Any): List<Any?> {
			return try{
				when (target){
					is Array<*> -> target.toList()
					is List<*> -> target
					is Iterable<*> -> target.toList()
					is Map<*,*> -> target.values.toList()
					is Sequence<*> -> target.toList()
					else -> throw UnsupportedOperationException("Invalid for query ${target.javaClass.simpleName} by query all results.")
				}
			} catch(e: Exception) {
				throw IllegalArgumentException("Cannot query '${target.javaClass.simpleName}' by query all results.",e)
			}
		}
	}

	/**
	 * 查询一个结果的查询器。
	 *
	 * 支持[Array]，[List]，[Iterable]，[Map]，[Sequence]。
	 */
	object FirstResultQuerier:Querier<String,Any?>{
		override fun query(queryObject: String, target: Any): Any? {
			return try{
				when (target){
					is Array<*> -> target.firstOrNull()
					is List<*> -> target.firstOrNull()
					is Iterable<*> -> target.firstOrNull()
					is Map<*,*> -> target.values.firstOrNull()
					is Sequence<*> -> target.firstOrNull()
					else -> throw UnsupportedOperationException("Invalid for query ${target.javaClass.simpleName} by query first result.")
				}
			} catch(e: Exception) {
				throw IllegalArgumentException("Cannot query '${target.javaClass.simpleName}' by query first result.",e)
			}
		}

		override fun queryOrNull(queryObject: String, target: Any): Any? {
			return try{
				when (target){
					is Array<*> -> target.first()
					is List<*> -> target.first()
					is Iterable<*> -> target.first()
					is Map<*,*> -> target.values.first()
					is Sequence<*> -> target.first()
					else -> throw UnsupportedOperationException("Invalid for query ${target.javaClass.simpleName} by query first result.")
				}
			} catch(e: Exception) {
				throw IllegalArgumentException("Cannot query '${target.javaClass.simpleName}' by get first result.",e)
			}
		}
	}

	/**
	 * 查询最后一个结果的查询器。
	 *
	 * 支持[Array]，[List]，[Iterable]，[Map]，[Sequence]。
	 */
	object LastResultQuerier:Querier<String,Any?>{
		override fun query(queryObject: String, target: Any): Any? {
			return try{
				when (target){
					is Array<*> -> target.lastOrNull()
					is List<*> -> target.lastOrNull()
					is Iterable<*> -> target.lastOrNull()
					is Map<*,*> -> target.values.lastOrNull()
					is Sequence<*> -> target.lastOrNull()
					else -> throw UnsupportedOperationException("Invalid for query ${target.javaClass.simpleName} by query last result.")
				}
			} catch(e: Exception) {
				throw IllegalArgumentException("Cannot query '${target.javaClass.simpleName}' by query last result.",e)
			}
		}

		override fun queryOrNull(queryObject: String, target: Any): Any? {
			return try{
				when (target){
					is Array<*> -> target.last()
					is List<*> -> target.last()
					is Iterable<*> -> target.last()
					is Map<*,*> -> target.values.last()
					is Sequence<*> -> target.last()
					else -> throw UnsupportedOperationException("Invalid for query ${target.javaClass.simpleName} by query last result.")
				}
			} catch(e: Exception) {
				throw IllegalArgumentException("Cannot query '${target.javaClass.simpleName}' by get last result.",e)
			}
		}
	}

	/**
	 * 基于字符串的查询器。
	 *
	 * 注意：如果查询字符串是空字符串，则返回目标查询对象本身。
	 *
	 * 支持[Array]，[List]，[Iterable]，[Map]，[Sequence]。
	 */
	object StringQuerier : Querier<String,Any?> {
		override fun query(queryObject: String, target: Any): Any? {
			if(queryObject.isEmpty()) return target
			return try {
				when(target) {
					is Array<*> -> target.getOrNull(queryObject.toInt())
					is List<*> -> target.getOrNull(queryObject.toInt())
					is Iterable<*> -> target.elementAtOrNull(queryObject.toInt())
					is Map<*, *> -> target.cast<Map<String, Any?>>()[queryObject]
					is Sequence<*> -> target.elementAtOrNull(queryObject.toInt())
					else -> throw UnsupportedOperationException("Invalid string '$queryObject' for query ${target.javaClass.simpleName}.")
				}
			} catch(e: Exception) {
				throw IllegalArgumentException("Cannot query by string '$queryObject'.",e)
			}
		}
	}

	/**
	 * 基于正则表达式的查询器。
	 *
	 * 支持[Map]。
	 */
	object RegexQuerier : Querier<Regex,List<*>> {
		override fun query(queryObject: Regex, target: Any): List<*> {
			return try {
				when(target) {
					//忽略数组、列表、序列等类型的查询对象
					is Map<*, *> -> target.cast<Map<String, Any?>>().filterKeys { it.matches(queryObject) }.values.toList()
					else -> throw UnsupportedOperationException("Invalid regex '$queryObject' for query ${target.javaClass.simpleName}.")
				}
			} catch(e: Exception) {
				throw IllegalArgumentException ("Cannot query by regex '$queryObject'.",e)
			}
		}
	}

	/**
	 * 基于索引的查询器。
	 *
	 * 支持[Array]，[List]，[Iterable]，[Sequence]。
	 */
	object IndexQuerier : Querier<Int,Any?> {
		override fun query(queryObject: Int, target: Any):  Any? {
			return try {
				when(target) {
					is Array<*> -> target.getOrNull(queryObject)
					is List<*> -> target.getOrNull(queryObject)
					is Iterable<*> -> target.elementAtOrNull(queryObject)
					is Sequence<*> -> target.elementAtOrNull(queryObject)
					else -> throw UnsupportedOperationException("Invalid index '$queryObject' for query ${target.javaClass.simpleName}.")
				}
			} catch(e: Exception) {
				throw IllegalArgumentException("Cannot query by index '$queryObject'.", e)
			}
		}
	}

	/**
	 * 基于索引范围的查询器。
	 *
	 * 支持[Array]，[List]，[Iterable]，[Sequence]。
	 */
	object IndexRangeQuerier : Querier<IntRange,List<Any?>> {
		override fun query(queryObject: IntRange, target: Any): List<Any?> {
			return try {
				when(target) {
					is Array<*> -> target.toList().slice(queryObject)
					is List<*> -> target.slice(queryObject)
					is Iterable<*> -> target.toList().slice(queryObject)
					is Sequence<*> -> target.toList().slice(queryObject)
					else -> throw UnsupportedOperationException("Invalid range '$queryObject' for query ${target.javaClass.simpleName}.")
				}
			} catch(e: Exception) {
				throw IllegalArgumentException("Cannot query by range '$queryObject'.", e)
			}
		}
	}
	//endregion

	//region Reflection Queriers
	/**
	 * 基于字符串，通过反射查询查询对象的字段和属性的值的查询器。
	 */
	object ReflectionQuerier : Querier<String,Any?> {
		override fun  query(queryObject: String, target: Any): Any?{
			try {
				val targetType = target.javaClass
				val field: Field? = runCatching { targetType.getDeclaredField(queryObject) }.getOrNull()
				if(field != null) return field.apply { trySetAccessible() }.get(target)
				val getter: Method? = runCatching { targetType.getDeclaredMethod(getGetterName(queryObject)) }.getOrNull()
				if(getter != null) return getter.apply { trySetAccessible() }.invoke(target)
				throw UnsupportedOperationException("Invalid string '$queryObject' for query ${target.javaClass.simpleName} by reflection.")
			} catch(e: Exception) {
				throw IllegalArgumentException("Cannot query by string '$queryObject' by reflection.", e)
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
	object ReflectionMemberQuerier : Querier<String,Any?> {
		override fun query(queryObject: String, target: Any): Any? {
			try {
				val targetType = target.javaClass
				val result = when {
					queryObject.startsWith("@") -> {
						val name = getAnnotationName(queryObject)
						targetType.declaredAnnotations.find { it.javaClass.simpleName == name }
					}
					queryObject.endsWith("()") -> {
						val name = getMethodName(queryObject)
						targetType.declaredMethods.find { it.name == name }
					}
					else -> runCatching { targetType.getDeclaredField(queryObject) }.getOrNull()
				}
				if(result != null) return result
				throw UnsupportedOperationException("Invalid string '$queryObject' for query ${target.javaClass.simpleName} by reflection.")
			} catch(e: Exception) {
				throw IllegalArgumentException("Cannot query by string '$queryObject' by reflection.", e)
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
