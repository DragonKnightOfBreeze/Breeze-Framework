// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:Suppress("UNCHECKED_CAST", "KDocUnresolvedReference", "UNUSED_VALUE")

package com.windea.breezeframework.core.domain

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.core.extensions.*

/**
 * 路径类型。
 *
 * 路径用于表示查询对象在其系统中的位置，可以包含多个元路径和变量，可以用于匹配和查询。
 *
 * 查询对象可能是：文件，组件，元素，数组，列表，映射，序列，...
 */
@BreezeComponent
interface PathType {
	/**
	 * 标准化指定的路径。
	 */
	fun normalize(path: String): String

	/**
	 * 判断指定的字符串是否匹配指定的路径。
	 */
	fun matches(value: String, path: String): Boolean

	/**
	 * 解析路径变量。如果路径不匹配，则返回空结果。
	 */
	fun resolveVariables(value: String, path: String):Map<String,String>

	/**
	 * 将指定的字符串分隔成元路径列表，并过滤空的元路径。
	 */
	fun split(path: String): List<String>

	/**
	 * 将指定的字符串分隔成元路径序列，并过滤空的元路径。
	 */
	fun splitToSequence(path: String): Sequence<String>

	/**
	 * 将元路径数组组合成完整路径。
	 */
	fun joinToString(metaPaths: Array<String>): String

	/**
	 * 将元路径列表组合成完整路径。
	 */
	fun joinToString(metaPaths: Iterable<String>): String

	/**
	 * 将元路径序列组合成完整路径。
	 */
	fun joinToString(metaPaths: Sequence<String>): String

	/**
	 * 是否支持查询。
	 *
	 * 注意：需要在相关实现中处理不支持的情况。
	 */
	fun supportsQuery():Boolean

	/**
	 * 根据指定路径查询查询对象，返回查询结果列表。
	 * 如果指定路径为空路径，则返回查询对象的单例列表。
	 */
	fun <T> query(value: Any, path: String): List<T>

	/**
	 * 根据指定路径查询查询对象，得到首个匹配的值，或者抛出异常。
	 * 如果指定路径为空路径，则返回查询对象本身。
	 */
	fun <T> get(value: Any, path: String): T

	/**
	 * 根据指定路径查询查询对象，得到首个匹配的值，或者返回null。
	 * 如果指定路径为空路径，则返回查询对象本身。
	 */
	fun <T> getOrNull(value: Any, path: String): T?

	/**
	 * 根据指定路径查询查询对象，得到首个匹配的值，或者返回默认值。
	 * 如果指定路径为空路径，则返回查询对象本身。
	 */
	fun <T> getOrElse(value: Any, path: String, defaultValue: () -> T): T


	companion object {
		//不需要进行注册
	}

	abstract class AbstractPathType(
		protected val delimiter: Char = '/',
		protected val prefix: String = "/",
		protected val variablePrefix: Char = '{',
		protected val variableSuffix: Char? = '}'
	) : PathType {
		private val delimiterString: String = delimiter.toString()

		override fun normalize(path: String): String {
			return path.trim().trimEnd(delimiter)
		}

		override fun matches(value: String, path: String): Boolean {
			//NOTE 遍历并比较两个字符串中的字符以优化性能
			val valueCharIterator = normalize(value).toCharArray().iterator()
			val pathCharIterator = normalize(path).toCharArray().iterator()
			var valueChar: Char?
			var pathChar: Char?
			loop1@ while(valueCharIterator.hasNext() && pathCharIterator.hasNext()) {
				valueChar = valueCharIterator.nextChar()
				pathChar = pathCharIterator.nextChar()
				when {
					//如果path遍历到delimiter且下一个是pathVariablePrefix，则要跳到下一个delimiter且上一个是pathVariableSuffix处
					//此时value要遍历到下一个delimiter处
					pathChar == delimiter -> {
						valueChar = valueCharIterator.nextChar()
						pathChar = pathCharIterator.nextChar()
						if(pathChar == variablePrefix) {
							valueChar = valueCharIterator.next { current, _ ->
								current == delimiter
							}
							pathChar = pathCharIterator.next { current, prev ->
								current == delimiter && (variableSuffix == null || prev == variableSuffix)
							}
							if(valueChar == null || pathChar == null) return false
						}
					}
					pathChar != valueChar -> return false
				}
			}
			if(valueCharIterator.hasNext() || pathCharIterator.hasNext()) return false
			return true
		}

		@NotOptimized
		override fun resolveVariables(value: String, path: String): Map<String, String> {
			val variables = mutableMapOf<String,String>()
			val iterator1 = value.split('/').iterator()
			val iterator2 = path.split('/').iterator()
			loop@ while(iterator1.hasNext() && iterator2.hasNext()) {
				val a = iterator1.next()
				val b = iterator2.next()
				when {
					b.surroundsWith('{', '}') -> variables[b.substring(1, b.length - 1)] = a
					b != a -> return mapOf()
				}
			}
			if(iterator1.hasNext() || iterator2.hasNext()) return mapOf()
			return variables
		}

		override fun split(path: String): List<String> {
			return normalize(path).removePrefix(prefix).split(delimiter).filterNotEmpty()
		}

		override fun splitToSequence(path: String): Sequence<String> {
			return normalize(path).removePrefix(prefix).splitToSequence(delimiter).filterNotEmpty()
		}

		override fun joinToString(metaPaths: Array<String>): String {
			return metaPaths.joinToString(delimiterString).addPrefix(prefix)
		}

		override fun joinToString(metaPaths: Iterable<String>): String {
			return metaPaths.joinToString(delimiterString).addPrefix(prefix)
		}

		override fun joinToString(metaPaths: Sequence<String>): String {
			return metaPaths.joinToString(delimiterString).addPrefix(prefix)
		}

		override fun supportsQuery(): Boolean {
			return true
		}

		private fun throwOnNotSupportsQuery(){
			if(!supportsQuery()) throw UnsupportedOperationException("Query operation is not supported.")
		}

		protected open fun <T> metaQuery(value: Any, path: String): List<T> {
			throwOnNotSupportsQuery()
			return when {
				path.startsWith(variablePrefix) && (variableSuffix == null || path.endsWith(variableSuffix))-> {
					Querier.ResultsQuerier.query(value, "")
				}
				else -> Querier.StringQuerier.queryOrNull(value, path.removePrefix(prefix))?.toSingletonList() ?: listOf()
			} as List<T>
		}

		override fun <T> query(value: Any, path: String): List<T> {
			throwOnNotSupportsQuery()
			val metaPaths = splitToSequence(path)
			if(metaPaths.none()) return listOf(value) as List<T>
			var result = listOf<Any?>(value)
			for(metaPath in metaPaths) {
				result = result.flatMap { v ->
					try {
						if(v != null) metaQuery(v, metaPath) else listOf()
					} catch(e: Exception) {
						throw IllegalArgumentException("Cannot query '${value.javaClass.simpleName}' by path '$path'.", e)
					}
				}
			}
			return result as List<T>
		}

		protected open fun <T> metaGet(value: Any, path: String): T? {
			throwOnNotSupportsQuery()
			return when {
				path.startsWith(variablePrefix) && (variableSuffix == null || path.endsWith(variableSuffix))-> {
					Querier.FirstResultQuerier.queryOrNull(value, "")
				}
				else -> Querier.StringQuerier.queryOrNull(value, path.removePrefix(prefix)) as T?
			} as T?
		}

		override fun <T> get(value: Any, path: String): T {
			throwOnNotSupportsQuery()
			val metaPaths = splitToSequence(path)
			if(metaPaths.none()) return value as T
			var currentValue = value
			for(metaPath in metaPaths) {
				currentValue = metaGet(currentValue, metaPath) ?: throw IllegalArgumentException("Cannot query by path '$path'.")
			}
			return currentValue as T
		}

		override fun <T> getOrNull(value: Any, path: String): T? {
			throwOnNotSupportsQuery()
			val metaPaths = splitToSequence(path)
			if(metaPaths.none()) return value as T
			var currentValue = value
			for(metaPath in metaPaths) {
				currentValue = metaGet(currentValue, metaPath) ?: return null
			}
			return currentValue as T
		}

		override fun <T> getOrElse(value: Any, path: String, defaultValue: () -> T): T {
			throwOnNotSupportsQuery()
			val metaPaths = splitToSequence(path)
			if(metaPaths.none()) return value as T
			var currentValue = value
			for(metaPath in metaPaths) {
				currentValue = metaGet(currentValue, metaPath) ?: return defaultValue()
			}
			return currentValue as T
		}
	}

	//region Default Path Types
	/**
	 * 标准路径。
	 *
	 * 规则：
	 * * 以`/`作为分隔符。
	 * * `{name}` 匹配任意项，并命名为`name`。
	 * * `index` 匹配索引`index`，`index`是整数。
	 * * `name` 匹配名字、键`name`。
	 */
	object StandardPath : AbstractPathType()

	/**
	 * Json指针路径。
	 *
	 * 规则：
	 * * 以`/`作为分隔符。
	 * * `{name}` 匹配任意项，并命名为`name`。
	 * * `-` （仅对于赋值操作）匹配数组最后一个元素的下一个元素。
	 * * `index` 匹配索引`index`，`index`是整数。
	 * * `name` 匹配名字、键`name`。
	 */
	object JsonPointerPath :  AbstractPathType()

	/**
	 * Json Schema路径。
	 *
	 * 规则：
	 * * 以`/`作为分隔符。
	 * * 以`/#/`作为前缀。
	 * * `{name}` 匹配任意项，并命名为`name`。
	 * * `index` 匹配索引`index`，`index`是整数。
	 * * `name` 匹配名字、键`name`。
	 */
	object JsonSchemaPath :  AbstractPathType('/',"/#/")

	/**
	 * Ant路径。
	 *
	 * 规则：
	 * * 以`/`作为分隔符。
	 * * `?` 匹配任意单个字符。
	 * * `*` 匹配除了分隔符之外的任意数量的任意字符。
	 * * `**` 匹配任意数量的任意字符。
	 * * `{name}` 匹配任意项，并命名为`name`。
	 * * `index` 匹配索引`index`，`index`是整数。
	 * * `name` 匹配名字、键`name`。
	 */
	object AntPath : AbstractPathType() {
		@NotOptimized
		override fun matches(value: String, path: String): Boolean {
			val fqValue = normalize(value)
			val fqPath = normalize(path)
			val fqPathRegex = Regex.escape(fqPath)
				.transformIn("\\Q", "\\E") { it.replace("\\{.*?}".toRegex()) { "\\E[^/]*?\\Q" } }
				.transformIn("\\Q", "\\E") { it.replace("?", "\\E.\\Q") }
				.transformIn("\\Q", "\\E") { it.replace("**", "\\E.*\\Q") }
				.transformIn("\\Q", "\\E") { it.replace("*", "\\E[^/]*?\\Q") }.toRegex()
			return fqValue.matches(fqPathRegex)
		}

		override fun supportsQuery(): Boolean {
			return false
		}
	}

	/**
	 * 引用路径。
	 *
	 * 规则：
	 * * 以`.`作为分隔符，可以在索引之前省略。
	 * * `[index]` 匹配索引`index`，`index`是整数。
	 * * `name` 匹配名字、键`name`。
	 */
	object ReferencePath : AbstractPathType('.',"") {
		private const val indexPrefix = '['
		private const val indexSuffix = ']'

		override fun normalize(path: String): String {
			//NOTE 在'['之前插入'.'并且移除开始的'.'以提高性能
			return buildString {
				val chars = path.trim().toCharArray()
				for(char in chars) {
					if(char == '[') append('.').append(char) else append(char)
				}
			}.trimStart('.')
		}

		override fun matches(value: String, path: String): Boolean {
			return normalize(value) == normalize(path)
		}

		override fun resolveVariables(value: String, path: String): Map<String, String> {
			return mapOf()
		}

		override fun <T> metaQuery(value: Any, path: String): List<T> {
			return when {
				path.surroundsWith(indexPrefix, indexSuffix) -> {
					val index = path.substring(1, path.length - 1).toInt()
					Querier.IndexQuerier.queryOrNull(value, index)?.toSingletonList() ?: listOf()
				}
				else -> Querier.StringQuerier.queryOrNull(value, path.removePrefix(prefix))?.toSingletonList() ?: listOf()
			} as List<T>
		}

		override fun <T> metaGet(value: Any, path: String): T? {
			return when {
				path.surroundsWith(indexPrefix, indexSuffix) -> {
					val index = path.substring(1, path.length - 1).toInt()
					Querier.IndexQuerier.queryOrNull(value, index)
				}
				else -> Querier.StringQuerier.queryOrNull(value, path.removePrefix(prefix)) as T?
			} as T?
		}
	}
	//endregion
}
