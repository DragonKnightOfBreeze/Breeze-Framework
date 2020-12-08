// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.component

import com.windea.breezeframework.core.annotation.*
import com.windea.breezeframework.core.extension.*
import java.lang.reflect.*

/**
 * 查询器。
 *
 * 查询器用于根据指定类型的查询对象，查询目标对象。
 */
@Suppress("IMPLICIT_CAST_TO_ANY")
@BreezeComponent
interface Querier<T : Any,R> {
	/**
	 * 根据指定类型的查询对象，查询查询对象。如果查询失败，则抛出异常。
	 */
	fun query(value: Any, queryObject: T): R

	/**
	 * 根据指定类型的查询对象，查询查询对象。如果查询失败，则返回null。
	 */
	fun queryOrNull(value: Any, queryObject: T): R? {
		return runCatching { query(value, queryObject) }.getOrNull()
	}

	//region Default Queriers
	/**
	 * 查询所有结果的查询器。
	 *
	 * 支持[Array]，[List]，[Iterable]，[Map]，[Sequence]。
	 */
	object ResultsQuerier: Querier<String, List<Any?>> {
		override fun query(value: Any, queryObject: String): List<Any?> {
			return try{
				when (value){
					is Array<*> -> value.toList()
					is List<*> -> value
					is Iterable<*> -> value.toList()
					is Sequence<*> -> value.toList()
					is Map<*,*> -> value.values.toList()
					else -> throw UnsupportedOperationException("Invalid for query ${value.javaClass.simpleName} by query results.")
				}
			} catch(e: Exception) {
				throw IllegalArgumentException("Cannot query '${value.javaClass.simpleName}' by query results.",e)
			}
		}
	}

	/**
	 * 查询过滤后的结果的查询器。
	 *
	 * 支持[Array]，[List]，[Iterable]，[Map]，[Sequence]。
	 */
	object FilterableResultsQuerier: Querier<(Any?) -> Boolean, List<Any?>> {
		override fun query(value: Any, queryObject: (Any?)->Boolean): List<Any?> {
			return try{
				when (value){
					is Array<*> -> value.filter(queryObject)
					is List<*> -> value.filter(queryObject)
					is Iterable<*> -> value.filter(queryObject)
					is Sequence<*> -> value.filter(queryObject).toList()
					is Map<*,*> -> value.values.filter(queryObject)
					else -> throw UnsupportedOperationException("Invalid for query ${value.javaClass.simpleName} by query filterable results.")
				}
			} catch(e: Exception) {
				throw IllegalArgumentException("Cannot query '${value.javaClass.simpleName}' by query filterable results.",e)
			}
		}
	}

	/**
	 * 查询一个结果的查询器。忽略查询对象。
	 *
	 * 支持[Array]，[List]，[Iterable]，[Map]，[Sequence]。
	 */
	object FirstResultQuerier: Querier<String, Any?> {
		override fun query(value: Any, queryObject: String): Any? {
			return try{
				when (value){
					is Array<*> -> value.firstOrNull()
					is List<*> -> value.firstOrNull()
					is Iterable<*> -> value.firstOrNull()
					is Sequence<*> -> value.firstOrNull()
					is Map<*,*> -> value.values.firstOrNull()
					else -> throw UnsupportedOperationException("Invalid for query ${value.javaClass.simpleName} by query first result.")
				}
			} catch(e: Exception) {
				throw IllegalArgumentException("Cannot query '${value.javaClass.simpleName}' by query first result.",e)
			}
		}

		override fun queryOrNull(value: Any, queryObject: String): Any? {
			return try{
				when (value){
					is Array<*> -> value.first()
					is List<*> -> value.first()
					is Iterable<*> -> value.first()
					is Sequence<*> -> value.first()
					is Map<*,*> -> value.values.first()
					else -> throw UnsupportedOperationException("Invalid for query ${value.javaClass.simpleName} by query first result.")
				}
			} catch(e: Exception) {
				throw IllegalArgumentException("Cannot query '${value.javaClass.simpleName}' by get first result.",e)
			}
		}
	}

	/**
	 * 查询最后一个结果的查询器。忽略查询对象。
	 *
	 * 支持[Array]，[List]，[Iterable]，[Map]，[Sequence]。
	 */
	object LastResultQuerier: Querier<String, Any?> {
		override fun query(value: Any, queryObject: String): Any? {
			return try{
				when (value){
					is Array<*> -> value.lastOrNull()
					is List<*> -> value.lastOrNull()
					is Iterable<*> -> value.lastOrNull()
					is Sequence<*> -> value.lastOrNull()
					is Map<*,*> -> value.values.lastOrNull()
					else -> throw UnsupportedOperationException("Invalid for query ${value.javaClass.simpleName} by query last result.")
				}
			} catch(e: Exception) {
				throw IllegalArgumentException("Cannot query '${value.javaClass.simpleName}' by query last result.",e)
			}
		}

		override fun queryOrNull(value: Any, queryObject: String): Any? {
			return try{
				when (value){
					is Array<*> -> value.last()
					is List<*> -> value.last()
					is Iterable<*> -> value.last()
					is Sequence<*> -> value.last()
					is Map<*,*> -> value.values.last()
					else -> throw UnsupportedOperationException("Invalid for query ${value.javaClass.simpleName} by query last result.")
				}
			} catch(e: Exception) {
				throw IllegalArgumentException("Cannot query '${value.javaClass.simpleName}' by get last result.",e)
			}
		}
	}

	/**
	 * 基于字符串的查询器。
	 *
	 * 注意：如果查询字符串是空字符串，则返回查询对象本身。
	 *
	 * 支持[Array]，[List]，[Iterable]，[Map]，[Sequence]。
	 */
	object StringQuerier : Querier<String, Any?> {
		override fun query(value: Any, queryObject: String): Any? {
			if(queryObject.isEmpty()) return value
			return try {
				when(value) {
					is Array<*> -> value.getOrNull(queryObject.toInt())
					is List<*> -> value.getOrNull(queryObject.toInt())
					is Iterable<*> -> value.elementAtOrNull(queryObject.toInt())
					is Sequence<*> -> value.elementAtOrNull(queryObject.toInt())
					is Map<*, *> -> value.cast<Map<String, Any?>>()[queryObject]
					else -> throw UnsupportedOperationException("Invalid string '$queryObject' for query ${value.javaClass.simpleName}.")
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
	object RegexQuerier : Querier<Regex, List<Any?>> {
		override fun query(value: Any, queryObject: Regex): List<Any?> {
			return try {
				when(value) {
					//忽略数组、列表、序列等类型的查询对象
					is Map<*, *> -> value.cast<Map<String, Any?>>().filterKeys { it.matches(queryObject) }.values.toList()
					else -> throw UnsupportedOperationException("Invalid regex '$queryObject' for query ${value.javaClass.simpleName}.")
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
	object IndexQuerier : Querier<Int, Any?> {
		override fun query(value: Any, queryObject: Int):  Any? {
			return try {
				when(value) {
					is Array<*> -> value.getOrNull(queryObject)
					is List<*> -> value.getOrNull(queryObject)
					is Iterable<*> -> value.elementAtOrNull(queryObject)
					is Sequence<*> -> value.elementAtOrNull(queryObject)
					else -> throw UnsupportedOperationException("Invalid index '$queryObject' for query ${value.javaClass.simpleName}.")
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
	object IndexRangeQuerier : Querier<IntRange, List<Any?>> {
		override fun query(value: Any, queryObject: IntRange): List<Any?> {
			return try {
				when(value) {
					is Array<*> -> value.toList().slice(queryObject)
					is List<*> -> value.slice(queryObject)
					is Iterable<*> -> value.toList().slice(queryObject)
					is Sequence<*> -> value.toList().slice(queryObject)
					else -> throw UnsupportedOperationException("Invalid range '$queryObject' for query ${value.javaClass.simpleName}.")
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
	object ReflectionQuerier : Querier<String, Any?> {
		override fun  query(value: Any, queryObject: String): Any?{
			try {
				val targetType = value.javaClass
				val field: Field? = runCatching { targetType.getDeclaredField(queryObject) }.getOrNull()
				if(field != null) return field.apply { trySetAccessible() }.get(value)
				val getter: Method? = runCatching { targetType.getDeclaredMethod(getGetterName(queryObject)) }.getOrNull()
				if(getter != null) return getter.apply { trySetAccessible() }.invoke(value)
				throw UnsupportedOperationException("Invalid string '$queryObject' for query ${value.javaClass.simpleName} by reflection.")
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
	object ReflectionMemberQuerier : Querier<String, Any?> {
		override fun query(value: Any, queryObject: String): Any {
			try {
				val targetType = value.javaClass
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
				throw UnsupportedOperationException("Invalid string '$queryObject' for query ${value.javaClass.simpleName} by reflection.")
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

	companion object{
		private val queriers = mutableListOf<Querier<*,*>>()

		/**
		 * 得到已注册的查询器。
		 */
		@JvmStatic fun values(): List<Querier<*, *>> {
			return queriers
		}

		/**
		 * 注册指定的查询器。
		 */
		@JvmStatic fun register(querier: Querier<*,*>){
			queriers.add(querier)
		}

		init {
			registerDefaultQueriers()
			registerReflectionQueriers()
		}

		private fun registerDefaultQueriers() {
			register(ResultsQuerier)
			register(FilterableResultsQuerier)
			register(FirstResultQuerier)
			register(LastResultQuerier)
			register(StringQuerier)
			register(RegexQuerier)
			register(IndexQuerier)
			register(IndexRangeQuerier)
		}

		private fun registerReflectionQueriers() {
			register(ReflectionQuerier)
			register(ReflectionMemberQuerier)
		}
	}
}
