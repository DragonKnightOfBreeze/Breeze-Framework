package com.windea.breezeframework.core.enums.core

import com.windea.breezeframework.core.extensions.*

/**匹配类型。*/
enum class MatchType(
	val predicate: (String, String) -> Boolean
) {
	/**
	 * 正则表达式。用于匹配广义上的字符串。
	 *
	 * 不验证模式的合法性。
	 */
	Regex({ string, pattern ->
		string matches pattern.toRegex()
	}),
	/**
	 * Ant匹配模式。用于匹配多级的路径或链接。
	 *
	 * 示例：`/home/&#42;/detail`, `/home/&#42;&#42;`
	 *
	 * * `*` 用于匹配一级路径。
	 * * `**` 用于匹配任意级路径。
	 *
	 * 不验证模式的合法性。
	 */
	Ant({ string, pattern ->
		val regexPattern = pattern.replace("*", "[^/]*").replace("[^/]*[^/]*", ".*")
		string matches regexPattern.toRegex()
	}),
	/**
	 * EditConfig匹配模式。用于匹配关注后缀名的文件名。
	 *
	 * 示例：`*.kt`, `*.{java, kt}`
	 *
	 * * `*` 用于匹配任意数量的任意字符。
	 * * `{java, kt}` 用于匹配一组后缀名。
	 *
	 * 不验证模式的合法性。
	 */
	EditorConfig({ string, pattern ->
		val regexPattern = pattern.replace("*", ".*").replace("{", "(?:").replace("}", ")").replace("\\s*,\\s*".toRegex(), "|")
		string matches regexPattern.toRegex()
	}),
	/**
	 * 路径引用匹配模式。用于匹配关注查询的引用。
	 *
	 * 示例：`/{Category}/0/Name`
	 *
	 * * `1` 表示一个列表的指定索引的元素。
	 * * `1..10` 表示一个列表的指定索引范围内的元素。
	 * * `[]`, `-` 表示一个列表。
	 * * `\[List]` 表示一个注为指定占位符的列表。
	 * * `Name` 表示一个映射的指定键的值。
	 * * `re:.*Name` 表示一个映射的键符合指定正则的键值对。
	 * * `{}` 表示一个映射。
	 * * `{Category}` 表示一个注为指定占位符的映射。
	 */
	PathReference({ string, pattern ->
		val subStrings = string.removePrefix("/").split("/")
		val subPatterns = pattern.removePrefix("/").split("/")
		if(subStrings.size != subPatterns.size) false
		else (subStrings zip subPatterns).all { (a, b) ->
			when {
				b == "[]" || b == "-" -> a.isNumeric()
				b.surroundsWith("[", "]") -> a.isNumeric()
				b.contains("..") -> a.isNumeric() && a.toInt() in b.toIntRange()
				b == "{}" -> true
				b.surroundsWith("{", "}") -> true
				b.startsWith("re:") -> a matches b.substring(3).toRegex()
				else -> a == b
			}
		}
	})

	//TODO 支持更多的匹配类型
}
