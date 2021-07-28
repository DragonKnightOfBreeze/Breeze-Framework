// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import icu.windea.breezeframework.core.extension.*

//region Path Patterns
@Suppress("UNCHECKED_CAST")
abstract class AbstractPathFormat(
	protected val delimiter: Char = '/',
	protected val prefix: String = "/",
	protected val variablePrefix: Char = '{',
	protected val variableSuffix: Char? = '}',
) : PathFormat {
	private val delimiterString: String = delimiter.toString()

	override fun normalize(path: String): String {
		return buildString {
			for(char in path.trim().toCharArray()) {
				if(!char.isWhitespace()) append(char)
			}
		}.trimEnd(delimiter)
	}

	override fun matches(value: String, path: String): Boolean {
		//NOTE 遍历并比较两个字符串中的字符以优化性能
		var pathIndex = 0
		var valueIndex = 0
		val lastPathIndex = path.lastIndex
		val lastValueIndex = value.lastIndex
		while(pathIndex <= lastPathIndex && valueIndex <= lastValueIndex) {
			val pathChar = path[pathIndex]
			when {
				//如果path遍历到下一个'/'并且之后是'{'处，则需要跳到'/'并且之前必须是'}'处，此时value要遍历到下一个'/'处
				pathChar == delimiter && (pathIndex < lastPathIndex && path[pathIndex + 1] == variablePrefix) -> {
					do pathIndex++ while(pathIndex <= lastPathIndex && path[pathIndex] != delimiter)
					do valueIndex++ while(valueIndex <= lastValueIndex && value[valueIndex] != delimiter)
					if(variableSuffix != null && path[pathIndex - 1] != variableSuffix) {
						throw IllegalArgumentException("Invalid path '$path': mismatched variable prefix and suffix.")
					}
				}
				//如果不匹配，则直接匹配失败
				pathChar != value[valueIndex] -> return false
				else -> {
					pathIndex++
					valueIndex++
				}
			}
		}
		//如果索引都最终遍历到了最后的索引，则匹配成功
		return pathIndex - lastPathIndex == valueIndex - lastValueIndex
	}

	override fun resolveVariables(value: String, path: String): Map<String, String> {
		//NOTE 遍历并比较两个字符串中的字符以优化性能
		val variables = mutableMapOf<String, String>()
		var pathIndex = 0
		var valueIndex = 0
		val lastPathIndex = path.lastIndex
		val lastValueIndex = value.lastIndex
		while(pathIndex <= lastPathIndex && valueIndex <= lastValueIndex) {
			val pathChar = path[pathIndex]
			when {
				//如果path遍历到下一个'/'并且之后是'{'处，则需要跳到'/'并且之前必须是'}'处，此时value要遍历到下一个'/'处
				pathChar == delimiter && (pathIndex < lastPathIndex && path[pathIndex + 1] == variablePrefix) -> {
					val variableNameStartIndex = pathIndex + 2
					val variableValueStartIndex = valueIndex + 1
					do pathIndex++ while(pathIndex <= lastPathIndex && path[pathIndex] != delimiter)
					do valueIndex++ while(valueIndex <= lastValueIndex && value[valueIndex] != delimiter)
					if(variableSuffix != null && path[pathIndex - 1] != variableSuffix) {
						throw IllegalArgumentException("Invalid path '$path': mismatched variable prefix and suffix.")
					}
					val variableNameEndIndex = pathIndex - 1
					val variableValueEndIndex = valueIndex
					val variableName = path.substring(variableNameStartIndex, variableNameEndIndex)
					val variableValue = value.substring(variableValueStartIndex, variableValueEndIndex)
					variables[variableName] = variableValue
				}
				//如果不匹配，则直接匹配失败
				pathChar != value[valueIndex] -> return mapOf()
				else -> {
					pathIndex++
					valueIndex++
				}
			}
		}
		//如果索引都最终遍历到了最后的索引，则匹配成功
		return if(pathIndex - lastPathIndex == valueIndex - lastValueIndex) variables else mapOf()
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

	protected open fun <T> metaQuery(value: Any, path: String): List<T> {
		return when {
			path.startsWith(variablePrefix) && (variableSuffix == null || path.endsWith(variableSuffix)) -> {
				Queriers.ResultsQuerier.query(value, "")
			}
			path.startsWith(variablePrefix) -> {
				throw IllegalArgumentException("Invalid path '$path': mismatched variable prefix and suffix.")
			}
			else -> Queriers.StringQuerier.queryOrNull(value, path.removePrefix(prefix))?.toSingletonList() ?: listOf()
		} as List<T>
	}

	override fun <T> query(value: Any, path: String): List<T> {
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
		return when {
			path.startsWith(variablePrefix) && (variableSuffix == null || path.endsWith(variableSuffix)) -> {
				Queriers.FirstResultQuerier.queryOrNull(value, "")
			}
			path.startsWith(variablePrefix) -> {
				throw IllegalArgumentException("Invalid path '$path': mismatched variable prefix and suffix.")
			}
			else -> Queriers.StringQuerier.queryOrNull(value, path.removePrefix(prefix)) as T?
		} as T?
	}

	override fun <T> get(value: Any, path: String): T {
		val metaPaths = splitToSequence(path)
		if(metaPaths.none()) return value as T
		var currentValue = value
		for(metaPath in metaPaths) {
			currentValue =
				metaGet(currentValue, metaPath) ?: throw IllegalArgumentException("Cannot query by path '$path'.")
		}
		return currentValue as T
	}

	override fun <T> getOrNull(value: Any, path: String): T? {
		val metaPaths = splitToSequence(path)
		if(metaPaths.none()) return value as T
		var currentValue = value
		for(metaPath in metaPaths) {
			currentValue = metaGet(currentValue, metaPath) ?: return null
		}
		return currentValue as T
	}

	override fun <T> getOrDefault(value: Any, path: String, defaultValue: T): T {
		val metaPaths = splitToSequence(path)
		if(metaPaths.none()) return value as T
		var currentValue = value
		for(metaPath in metaPaths) {
			currentValue = metaGet(currentValue, metaPath) ?: return defaultValue
		}
		return currentValue as T
	}

	override fun <T> getOrElse(value: Any, path: String, defaultValue: () -> T): T {
		val metaPaths = splitToSequence(path)
		if(metaPaths.none()) return value as T
		var currentValue = value
		for(metaPath in metaPaths) {
			currentValue = metaGet(currentValue, metaPath) ?: return defaultValue()
		}
		return currentValue as T
	}

	override fun equals(other: Any?) = componentEquals(this, other)

	override fun hashCode() = componentHashcode(this)

	override fun toString() = componentToString(this)
}
