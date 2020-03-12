package com.windea.breezeframework.core.enums.text

import com.windea.breezeframework.core.extensions.*
import java.text.*

/**格式化类型。*/
enum class FormatType(
	override val formatter: (String, Array<out Any?>, String?) -> String
) : FormatStrategy {
	/**
	 * 基于[MessageFormat]格式化当前字符串。自动转义单引号。
	 *
	 * * `{0}` - 基于参数索引的占位符。
	 * * `{1,$number}` -  基于参数索引和类型的占位符。
	 * * `{2,$date,$shot}` - 基于参数索引、类型和长度的占位符。
	 *
	 * 示例：
	 * * `"hello {0}!" & "world"`
	 * * `"hello {0} at {1,date}!" & "world", Date())`
	 */
	Message({ string, args, _ ->
		MessageFormat.format(string.replace("'", "''"), *args)
	}),

	/**
	 * 基于指定的占位符格式化当前字符串。注意占位符格式是固定的。
	 *
	 * * `{}`, ${}` - （指定该占位符时）基于参数顺序的占位符。
	 * * `{index}`, `${index}` - （指定该占位符时）基于参数索引的占位符。
	 * * `${name}` `${name}` - （指定该占位符时）基于参数名称的占位符。
	 *
	 * 示例：
	 * * `"{} {}!" & "hello", "world")`
	 * * `"{0} {1}!" & "hello", "world")`
	 * * `"{arg1} {arg2}!" & "arg1" to "hello", "arg2" to "world")`
	 */
	Custom({ string, args, placeholder ->
		when {
			placeholder == null -> throw IllegalArgumentException("Placeholder cannot be null when using custom format.")
			"index" in placeholder -> {
				val (prefix, suffix) = placeholder.split("index", limit = 2).map { it.escapeBy(EscapeType.Regex) }
				string.replace("""$prefix(\d+)$suffix""".toRegex()) { r ->
					args.getOrNull(r.groupValues[1].toInt())?.toString().orEmpty()
				}
			}
			"name" in placeholder -> {
				val argPairs = args.map { it as Pair<*, *> }.toMap()
				val (prefix, suffix) = placeholder.split("name", limit = 2).map { it.escapeBy(EscapeType.Regex) }
				string.replace("""$prefix([a-zA-Z_$]+)$suffix""".toRegex()) { r ->
					argPairs[r.groupValues[1]]?.toString().orEmpty()
				}
			}
			else -> string.replaceIndexed(placeholder) { args.getOrNull(it)?.toString().orEmpty() }
		}
	})
}
