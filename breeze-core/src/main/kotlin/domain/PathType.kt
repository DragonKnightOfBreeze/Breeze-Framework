// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:Suppress("UNCHECKED_CAST")

package com.windea.breezeframework.core.domain

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.core.extensions.*

/**
 * 路径类型。
 *
 * 路径用于表示目标查询对象在其系统中的位置，可以包含多个元路径和变量，可以用于匹配和查询。
 *
 * 查询对象可能是：文件，组件，元素，数组，列表，映射，序列，...
 */
@ComponentMarker
interface PathType {
	val delimiter: String
	val prefix: String

	/**
	 * 标准化指定的路径。
	 */
	fun normalize(path: String): String {
		return path.trim().removeSuffix(delimiter)
	}

	/**
	 * 判断指定的字符串是否匹配指定的路径。
	 */
	fun matches(value: String, path: String): Boolean {
		val iterator1 = split(normalize(value)).iterator()
		val iterator2 = split(normalize(path)).iterator()
		loop@ while(iterator1.hasNext() && iterator2.hasNext()) {
			val a = iterator1.next()
			val b = iterator2.next()
			when {
				b.surroundsWith('{', '}') -> continue@loop
				b != a -> return false
			}
		}
		if(iterator1.hasNext() || iterator2.hasNext()) return false
		return true
	}

	/**
	 * 将指定的字符串分隔成元路径列表，并过滤空的元路径。
	 */
	fun split(path: String): List<String> {
		return normalize(path).removePrefix(prefix).split(delimiter).filterNotEmpty()
	}

	/**
	 * 将指定的字符串分隔成元路径序列，并过滤空的元路径。
	 */
	fun splitToSequence(path: String): Sequence<String> {
		return normalize(path).removePrefix(prefix).splitToSequence(delimiter).filterNotEmpty()
	}

	/**
	 * 将元路径数组组合成完整路径。
	 */
	fun joinToString(metaPaths: Array<String>): String {
		return metaPaths.joinToString(delimiter).addPrefix(prefix)
	}

	/**
	 * 将元路径列表组合成完整路径。
	 */
	fun joinToString(metaPaths: Iterable<String>): String {
		return metaPaths.joinToString(delimiter).addPrefix(prefix)
	}

	/**
	 * 将元路径序列组合成完整路径。
	 */
	fun joinToString(metaPaths: Sequence<String>): String {
		return metaPaths.joinToString(delimiter).addPrefix(prefix)
	}

	/**
	 * 根据指定元路径查询目标查询对象，返回查询结果列表。
	 *
	 * 注意：如果需要递归查询，请使用[query]。
	 */
	fun <T> metaQuery(path: String, target: Any): List<T> {
		return when{
			path.surroundsWith('{','}') -> Querier.AllResultsQuerier.query("",target)
			else -> {
				val value = Querier.StringQuerier.queryOrNull(path.removePrefix(prefix), target)
				if(value != null) listOf(value) else listOf()
			}
		} as List<T>
	}

	/**
	 * 根据指定路径查询目标查询对象，返回查询结果列表。
	 * 如果指定路径为空路径，则返回目标查询对象的单元素列表。
	 */
	fun <T> query(path: String, target: Any): List<T> {
		val metaPaths = splitToSequence(path)
		if(metaPaths.none()) return listOf(target) as List<T>
		var result = listOf<Any?>(target)
		for(metaPath in metaPaths) {
			result = result.flatMap { value ->
				try {
					if(value != null) metaQuery(metaPath, value) else listOf()
				} catch(e: Exception) {
					throw IllegalArgumentException("Cannot query '${target.javaClass.simpleName}' by path '$path'.", e)
				}
			}
		}
		return result as List<T>
	}

	/**
	 * 根据指定元路径查询目标查询对象，得到首个匹配的值，或者返回null。
	 *
	 * 注意：如果需要递归查询，请使用[get]、[getOrNull]、[getOrElse]。
	 */
	fun <T> metaGet(path: String, target: Any): T? {
		return when{
			path.surroundsWith('{','}') -> Querier.FirstResultQuerier.queryOrNull("",target)
			else -> Querier.StringQuerier.queryOrNull(path.removePrefix(prefix), target) as T?
		} as T?
	}

	/**
	 * 根据指定路径查询目标查询对象，得到首个匹配的值，或者抛出异常。
	 * 如果指定路径为空路径，则返回目标查询对象本身。
	 */
	fun <T> get(path: String, target: Any): T {
		val metaPaths = splitToSequence(path)
		if(metaPaths.none()) return target as T
		var currentValue = target
		for(metaPath in metaPaths) {
			currentValue = metaGet(metaPath, currentValue)
			               ?: throw IllegalArgumentException("Cannot query by path '$path'.")
		}
		return currentValue as T
	}

	/**
	 * 根据指定路径查询目标查询对象，得到首个匹配的值，或者返回null。
	 * 如果指定路径为空路径，则返回目标查询对象本身。
	 */
	fun <T> getOrNull(path: String, target: Any): T? {
		val metaPaths = splitToSequence(path)
		if(metaPaths.none()) return target as T
		var currentValue = target
		for(metaPath in metaPaths) {
			currentValue = metaGet(metaPath, currentValue) ?: return null
		}
		return currentValue as T
	}

	/**
	 * 根据指定路径查询目标查询对象，得到首个匹配的值，或者返回默认值。
	 * 如果指定路径为空路径，则返回目标查询对象本身。
	 */
	fun <T> getOrElse(path: String, target: Any, defaultValue: () -> T): T {
		val metaPaths = splitToSequence(path)
		if(metaPaths.none()) return target as T
		var currentValue = target
		for(metaPath in metaPaths) {
			currentValue = metaGet(metaPath, currentValue) ?: return defaultValue()
		}
		return currentValue as T
	}


	companion object {
		//不需要进行注册
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
	object StandardPath : PathType {
		override val prefix = "/"
		override val delimiter = "/"
	}

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
	object JsonPointerPath : PathType {
		override val prefix = "/"
		override val delimiter = "/"
		//匹配和查询时，与标准路径没有区别
	}

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
	object JsonSchemaPath : PathType {
		override val prefix = "/#/"
		override val delimiter = "/"
		//匹配和查询时，与标准路径没有区别
	}

	/**
	 * Ant路径。
	 *
	 * 规则：
	 * * 以`/`作为分隔符。
	 * * `?` 匹配任意单个字符。
	 * * `*` 匹配除了分隔符之外的任意数量的任意字符。
	 * * `**` 匹配任意数量的任意字符。
	 * * `{name:regex}` 匹配正则表达式`regex`，并命名为`name`，`regex`是正则表达式。
	 * * `m-n` 匹配索引范围`m`到`n`，`m`和`n`是整数。
	 * * `index` 匹配索引`index`，`index`是整数。
	 * * `name` 匹配名字、键`name`。
	 */
	object AntPath : PathType {
		override val prefix = "/"
		override val delimiter = "/"

		override fun matches(value: String, path: String): Boolean {
			TODO()
		}

		override fun <T> metaQuery(path: String, target: Any) = notUsedForQuery()
		override fun <T> query(path: String, target: Any) = notUsedForQuery()
		override fun <T> get(path: String, target: Any) = notUsedForQuery()
		override fun <T> getOrNull(path: String, target: Any) = notUsedForQuery()
		override fun <T> getOrElse(path: String, target: Any, defaultValue: () -> T) = notUsedForQuery()
		private fun notUsedForQuery(): Nothing = throw UnsupportedOperationException("Ant path is not used for query.")
	}

	///**
	// * 引用路径。
	// *
	// * 规则：
	// * * 以`.`作为分隔符，可以在索引之前省略。
	// * * `[index]` 匹配索引`index`，`index`是整数。
	// * * `name` 匹配名字、键`name`。
	// */
	//object ReferencePath : PathType {
	//	override val prefix = ""
	//	override val delimiter = "."
	//}
	//endregion
}
