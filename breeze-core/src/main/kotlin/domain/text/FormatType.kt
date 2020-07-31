@file:Suppress("UNCHECKED_CAST")

package com.windea.breezeframework.core.domain.text

import com.windea.breezeframework.core.extensions.*
import java.text.*
import java.util.*

/**格式化类型。*/
enum class FormatType(
	internal val formatter:(String, Array<out Any?>, Locale?, Pair<String, String>?) -> String
) {
	/**
	 * 基于默认的格式化方式格式化当前字符串。
	 *
	 * @throws IllegalArgumentException 当参数不匹配时，或者格式化字符串不正确时。
	 * */
	Default({ string, args, locale, _ ->
		string.format(locale ?: Locale.getDefault(Locale.Category.FORMAT), args)
	}),

	/**
	 * 基于[MessageFormat]格式化当前字符串。自动转义单引号。
	 *
	 * * 规则：
	 * * `{0}` - 基于参数索引的占位符。
	 * * `{1,$number}` -  基于参数索引和类型的占位符。
	 * * `{2,$date,$shot}` - 基于参数索引、类型和长度的占位符。
	 *
	 * 示例：
	 * * `"hello {0}!" & "world"`
	 * * `"hello {0} at {1,date}!" & "world", Date()`
	 *
	 * @throws IllegalArgumentException 当参数不匹配时，或者格式化字符串不正确时。
	 */
	Message({ string, args, _, _ ->
		MessageFormat.format(string.replace("'", "''"), *args)
	}),

	/**
	 * 基于日志的格式化方式格式化当前字符串。
	 *
	 * 规则：
	 * * `{}` - 基于参数顺序的占位符。
	 *
	 * 示例：
	 * * `"{} {}!" & "hello", "world"`
	 *
	 * @throws IllegalArgumentException 当参数不匹配时。
	 */
	Log({ string, args, _, _ ->
		try {
			string.replaceIndexed("{}") { args[it].toString() }
		} catch(e:Exception) {
			throw IllegalArgumentException("Mismatched arguments.")
		}
	}),

	/**
	 * 基于带索引的占位符格式化当前字符串。默认使用`{}`作为占位符。
	 *
	 * 规则：
	 * * `{$index}` - （占位符为`{}`时）基于参数位置索引的占位符。
	 *
	 * 示例：
	 * * `"{} {}!" & "hello", "world"`
	 *
	 * @throws IllegalArgumentException 当参数不匹配时，或者索引非法时。
	 */
	Indexed({ string, args, _, placeholder ->
		try {
			val (prefix, suffix) = placeholder?.map { Regex.escape(it) } ?: defaultPlaceHolder
			val regex = """$prefix(\d+)$suffix""".toRegex()
			string.replace(regex) { args[it.groupValues[1].toInt()].toString() }
		} catch(e:Exception) {
			throw IllegalArgumentException("Mismatched arguments, or invalid index in placeholder.", e)
		}
	}),

	/**
	 * 基于带名字的占位符格式化当前字符串。默认使用`{}`作为占位符。
	 * 由于Java/Kotlin无法捕捉到参数的名字，传入的参数必须是名字-值元组。
	 *
	 * 规则：
	 * * `{$name}` - （占位符为`{}`时）基于参数名称的占位符。
	 *
	 * 示例：
	 * * `"{arg1} {arg2}!" & "arg1" to "hello", "arg2" to "world"`
	 *
	 * @throws IllegalArgumentException 当参数不匹配时，或者传入的参数不是名字-值元组时。
	 */
	Named({ string, args, _, placeholder ->
		try {
			val argPairs = (args as Array<out Pair<String, Any?>>).toMap()
			val (prefix, suffix) = placeholder?.map { Regex.escape(it) } ?: defaultPlaceHolder
			val regex = """$prefix([a-zA-Z_$]+)$suffix""".toRegex()
			string.replace(regex) { argPairs[it.groupValues[1]].toString() }
		} catch(e:Exception) {
			throw IllegalArgumentException("Mismatched arguments, or invalid argument type (not a name-value pair).", e)
		}
	});

	companion object {
		private val defaultPlaceHolder = Regex.escape("{") to Regex.escape("}")
	}
}
