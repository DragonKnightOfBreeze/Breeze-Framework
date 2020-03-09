package com.windea.breezeframework.core.enums.text

import com.windea.breezeframework.core.extensions.*

//注意：这里不要使用String.replace(regex, replacement)方法，因为replacement中的"\Q"会变成"Q"

/**匹配类型。*/
enum class MatchType(
	/**转化为对应的正则表达式的方法。*/
	val transform: (String) -> String
) {
	/**
	 * Ant路径匹配模式。用于匹配路径。
	 *
	 * 示例：`/home/&#42;/detail`, `/home/&#42;&#42;`
	 *
	 * * `?` 匹配任意单个字符。
	 * * `*` 匹配除了路径分隔符`/`之外的任意数量的任意字符。
	 * * `**` 匹配任意数量的任意字符。
	 */
	AntPath({ pattern ->
		pattern.escapeRegex()
			.replace("?", "\\E.\\Q").replace("**", "\\E.*\\Q")
			.transformIn("\\Q", "\\E") { it.replace("*", "\\E[^/]*\\Q") }
			.trimRegex()
	}),
	/**
	 * ignore文件（如.gitignore）的路径匹配模式。用于匹配路径。
	 *
	 * 示例：`test*.txt`, `test[0-9].txt`
	 *
	 * * `?` 匹配任意单个字符。
	 * * `*` 匹配除了路径分隔符`/`之外的任意数量的任意字符。
	 * * `**` 匹配任意数量的任意字符。
	 * * `\[name]` 匹配指定的任意字符。
	 * * `\[a-z]` 匹配指定范围内的任意字符。
	 * * `!path` 对当前匹配规则取反。（暂不支持）
	 */
	IgnorePath({ pattern ->
		pattern.escapeRegex()
			.replace("?", "\\E.\\Q").replace("**", "\\E.*\\Q")
			.transformIn("\\Q", "\\E") { it.replace("*", "\\E[^/]*\\Q") }
			.transformIn("\\Q", "\\E") { it.replace("\\[.*?]".toRegex()) { r -> "\\E${r[0]}\\Q" } }
			.trimRegex()
	}),
	/**
	 * .editorconfig文件的路径匹配模式。用于匹配关注后缀名的路径。
	 *
	 * 示例：`*.kt`, `*.{java, kt}`
	 *
	 * * `?` 匹配任意单个字符。
	 * * `*` 匹配除了路径分隔符`/`之外的任意数量的任意字符。
	 * * `**` 匹配任意数量的任意字符。
	 * * `\[name]` 匹配指定的任意字符。
	 * * `\[a-z]` 匹配指定范围内的任意字符。
	 * * `{a, b, c}` 匹配任意指定的字符串。至少指定两个字符串。（暂不验证）
	 */
	EditorConfigPath({ pattern ->
		pattern.escapeRegex()
			.replace("?", "\\E.\\Q").replace("**", "\\E.*\\Q")
			.transformIn("\\Q", "\\E") { it.replace("*", "\\E[^/]*\\Q") }
			.transformIn("\\Q", "\\E") { it.replace("\\[.*?]".toRegex()) { r -> "\\E${r[0]}\\Q" } }
			.replace("\\{(.*?)}".toRegex()) { r -> r[1].split(",").joinToString("|", "\\E(", ")\\Q") { it.trim() } }
			.trimRegex()
	}),
	/**
	 * 路径引用匹配模式。用于匹配关注查询的引用。
	 *
	 * 示例：`/{Category}/0/Name`
	 *
	 * * `1` 表示一个列表的指定索引的元素。
	 * * `1..10`，`1-10` 表示一个列表的指定索引范围内的元素。
	 * * `[]`，`-` 表示一个列表。
	 * * `\[List]` 表示一个注为指定占位符的列表。
	 * * `Name` 表示一个映射的指定键的值。
	 * * `re:.*Name` 表示一个映射的键符合指定正则的键值对。
	 * * `{}`，`-` 表示一个映射。
	 * * `{Category}` 表示一个注为指定占位符的映射。
	 */
	PathReference({ pattern ->
		pattern.escapeRegex()
			.replace("/(?:\\[]|-|\\[[^/]*?])".toRegex()) { "/\\E\\d+\\Q" }
			.replace("/(?:\\{}|\\{[^/]*?})".toRegex()) { "/\\E[^/]*\\Q" }
			.replace("/(\\d+)(?:\\.\\.|-)(\\d+)".toRegex()) { r -> "/\\E${Regex.fromRange(r[1].toInt(), r[2].toInt())}\\Q" }
			.transformIn("\\Q", "\\E") { it.replace("/re:([^/]*)".toRegex()) { r -> "/\\E${r[1]}\\Q" } }
			.trimRegex()
	});

	companion object {
		/**转义正则表达式字符串。*/
		private fun String.escapeRegex(): String {
			return Regex.escape(this)
		}

		/**精简正则表达式字符串。*/
		private fun String.trimRegex(): String {
			return this.remove("\\Q\\E")
		}
	}
}
