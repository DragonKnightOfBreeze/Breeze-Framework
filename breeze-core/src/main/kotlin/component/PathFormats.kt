// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import icu.windea.breezeframework.core.extension.*

@Suppress("UNCHECKED_CAST")
object PathFormats : ComponentRegistry<PathFormat>() {
	//region Implementations
	/**
	 * 标准路径。
	 *
	 * 规则：
	 * * 以`/`作为分隔符。
	 * * `{name}` 匹配任意项，并命名为`name`。
	 * * `index` 匹配索引`index`，`index`是整数。
	 * * `name` 匹配名字、键`name`。
	 */
	object StandardPath : AbstractPathFormat()

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
	object JsonPointerPath : AbstractPathFormat()

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
	object AntPath : AbstractPathFormat() {
		private const val singleWildcard = '?'
		private const val wildCard = '*'

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
						do valueIndex++ while(valueIndex <= lastValueIndex && value[valueIndex] != delimiter)
						do pathIndex++ while(pathIndex <= lastPathIndex && path[pathIndex] != delimiter)
						if(variableSuffix != null && path[pathIndex - 1] != variableSuffix) {
							throw IllegalArgumentException("Invalid path '$path': mismatched variable prefix and suffix.")
						}
					}
					//如果path遍历到'**'处，则需要跳到这之后，此时value需要遍历到下个继续匹配处，无法继续匹配则直接匹配成功
					pathChar == wildCard && (pathIndex < lastPathIndex && path[pathIndex + 1] == wildCard) -> {
						pathIndex += 2
						if(pathIndex > lastPathIndex) return true
						do valueIndex++ while(valueIndex <= lastValueIndex && value[valueIndex] != path[pathIndex])
					}
					//如果path遍历到'*'处，则需要跳到这之后，此时value需要遍历到下个继续匹配的'/'之前处，无法继续匹配则直接匹配成功
					pathChar == wildCard -> {
						pathIndex += 1
						if(pathIndex > lastPathIndex) return true
						do valueIndex++ while(valueIndex <= lastValueIndex && value[valueIndex] != delimiter && value[valueIndex] != path[pathIndex])
					}
					//如果path遍历到'?'处，则需要跳这之后，此时value需要遍历到下个字符
					pathChar == singleWildcard -> {
						pathIndex++
						valueIndex++
					}
					//如果不匹配，则直接匹配失败
					pathChar != value[valueIndex] -> return false
					else -> {
						pathIndex++
						valueIndex++
					}
				}
			}
			//忽略多余的通配符
			return when {
				pathIndex - lastPathIndex == valueIndex - lastValueIndex -> true
				pathIndex == lastPathIndex && path[pathIndex] == wildCard -> true
				pathIndex == lastPathIndex - 1 && path[pathIndex] == wildCard && path[pathIndex + 1] == wildCard -> true
				else -> false
			}
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
					//如果path遍历到'**'处，则需要跳到这之后，此时value需要遍历到下个继续匹配处，无法继续匹配则直接匹配成功
					pathChar == wildCard && (pathIndex < lastPathIndex && path[pathIndex + 1] == wildCard) -> {
						pathIndex += 2
						if(pathIndex > lastPathIndex) return variables
						do valueIndex++ while(valueIndex <= lastValueIndex && value[valueIndex] != path[pathIndex])
					}
					//如果path遍历到'*'处，则需要跳到这之后，此时value需要遍历到下个继续匹配的'/'之前处，无法继续匹配则直接匹配成功
					pathChar == wildCard -> {
						pathIndex += 1
						if(pathIndex > lastPathIndex) return variables
						do valueIndex++ while(valueIndex <= lastValueIndex && value[valueIndex] != delimiter && value[valueIndex] != path[pathIndex])
					}
					//如果path遍历到'?'处，则需要跳这之后，此时value需要遍历到下个字符
					pathChar == singleWildcard -> {
						pathIndex++
						valueIndex++
					}
					//如果不匹配，则直接匹配失败
					pathChar != value[valueIndex] -> return mapOf()
					else -> {
						pathIndex++
						valueIndex++
					}
				}
			}
			//忽略多余的通配符
			return when {
				pathIndex - lastPathIndex == valueIndex - lastValueIndex -> variables
				pathIndex == lastPathIndex && path[pathIndex] == wildCard -> variables
				pathIndex == lastPathIndex - 1 && path[pathIndex] == wildCard && path[pathIndex + 1] == wildCard -> variables
				else -> mapOf()
			}
		}

		override fun <T> metaQuery(value: Any, path: String): List<T> {
			throw UnsupportedOperationException("Query operation is not supported by ant path.")
		}

		override fun <T> query(value: Any, path: String): List<T> {
			throw UnsupportedOperationException("Query operation is not supported by ant path.")
		}

		override fun <T> metaGet(value: Any, path: String): T? {
			throw UnsupportedOperationException("Get operation is not supported by ant path.")
		}

		override fun <T> getOrNull(value: Any, path: String): T? {
			throw UnsupportedOperationException("Get operation is not supported by ant path.")
		}

		override fun <T> getOrElse(value: Any, path: String, defaultValue: () -> T): T {
			throw UnsupportedOperationException("Get operation is not supported by ant path.")
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
	object ReferencePath : AbstractPathFormat('.', "") {
		private const val indexPrefix = '['
		private const val indexSuffix = ']'

		override fun normalize(path: String): String {
			return buildString {
				for(char in path.toCharArray()) {
					if(char == '[') append('.').append(char) else if(!char.isWhitespace()) append(char)
				}
			}.trimStart('.')
		}

		override fun matches(value: String, path: String): Boolean {
			return normalize(value) == normalize(path)
		}

		override fun resolveVariables(value: String, path: String): Map<String, String> {
			throw UnsupportedOperationException("Resolve variables operation is not supported by reference path.")
		}

		override fun <T> metaQuery(value: Any, path: String): List<T> {
			return when {
				path.surroundsWith(indexPrefix, indexSuffix) -> {
					val index = path.substring(1, path.length - 1).toInt()
					Queriers.IndexQuerier.queryOrNull(value, index)?.toSingletonList() ?: listOf()
				}
				else -> Queriers.StringQuerier.queryOrNull(value, path.removePrefix(prefix))?.toSingletonList() ?: listOf()
			} as List<T>
		}

		override fun <T> metaGet(value: Any, path: String): T? {
			return when {
				path.surroundsWith(indexPrefix, indexSuffix) -> {
					val index = path.substring(1, path.length - 1).toInt()
					Queriers.IndexQuerier.queryOrNull(value, index)
				}
				else -> Queriers.StringQuerier.queryOrNull(value, path.removePrefix(prefix)) as T?
			} as T?
		}
	}
	//endregion

	override fun registerDefault() {
		register(StandardPath)
		register(JsonPointerPath)
		register(AntPath)
		register(ReferencePath)
	}
}
