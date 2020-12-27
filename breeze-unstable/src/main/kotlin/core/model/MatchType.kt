// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.model

import com.windea.breezeframework.core.extension.*

//注意：这里不要使用String.replace(regex, replacement)方法，因为replacement中的"\Q"会变成"Q"

/**匹配类型。*/
enum class MatchType(
	internal val regexTransform: (String) -> String,
) {
	/**
	 * Ant风格的路径匹配类型。用于匹配路径。
	 *
	 * 规则：
	 * * `?` - 匹配任意单个字符。
	 * * `*` - 匹配除了路径分隔符`/`之外的任意数量的任意字符。
	 * * `**` - 匹配任意数量的任意字符。
	 *
	 * 示例：
	 * * `"/home/&#42;/detail"`
	 * * `"/home/&#42;&#42;"`
	 */
	AntPath({ pattern ->
		pattern.escapeRegex()
			.replace("?", "\\E.\\Q").replace("**", "\\E.*\\Q")
			.transformIn("\\Q", "\\E") { it.replace("*", "\\E[^/]*\\Q") }
			.trimRegex()
	}),

	/**
	 * ignore文件（如.gitignore）的路径匹配类型。用于匹配路径。
	 *
	 * 规则：
	 * * `?` - 匹配任意单个字符。
	 * * `*` - 匹配除了路径分隔符`/`之外的任意数量的任意字符。
	 * * `**` - 匹配任意数量的任意字符。
	 * * `[\$str]` - 匹配指定的任意字符。
	 * * `[$char1-$char2]` - 匹配指定范围内的任意字符。
	 * * `!$path` - 对当前匹配规则取反。（暂不支持）
	 *
	 * 示例：
	 * * `"test*.txt"`
	 * * `"/home/&#42;/detail"`
	 * * `"/home/&#42;&#42;"`
	 * * `"test[01].txt"`
	 * * `"test[0-9].txt"`
	 */
	IgnorePath({ pattern ->
		pattern.escapeRegex()
			.replace("?", "\\E.\\Q").replace("**", "\\E.*\\Q")
			.transformIn("\\Q", "\\E") { it.replace("*", "\\E[^/]*\\Q") }
			.transformIn("\\Q", "\\E") { it.replace("\\[.*?]".toRegex()) { r -> "\\E${r.value}\\Q" } }
			.trimRegex()
	}),

	/**
	 * .editorconfig文件的路径匹配类型。用于匹配关注后缀名的路径。
	 *
	 * 规则：
	 * * `?` - 匹配任意单个字符。
	 * * `*` - 匹配除了路径分隔符`/`之外的任意数量的任意字符。
	 * * `**` - 匹配任意数量的任意字符。
	 * * `[\$str]` - 匹配指定的任意字符。
	 * * `[$char1-$char2]` - 匹配指定范围内的任意字符。
	 * * `{$str1, $str2, ...}` - 匹配任意指定的字符串。至少指定两个字符串。（暂不验证）
	 *
	 * 示例：
	 * * `"test*.txt"`
	 * * `"/home/&#42;/detail"`
	 * * `"/home/&#42;&#42;"`
	 * * `"test[01].txt"`
	 * * `"test[0-9].txt"`
	 * * `"*.kt"`
	 * * `"*.{java, kt}"`
	 */
	EditorConfigPath({ pattern ->
		pattern.escapeRegex()
			.replace("?", "\\E.\\Q").replace("**", "\\E.*\\Q")
			.transformIn("\\Q", "\\E") { it.replace("*", "\\E[^/]*\\Q") }
			.transformIn("\\Q", "\\E") { it.replace("\\[.*?]".toRegex()) { r -> "\\E${r.value}\\Q" } }
			.replace("\\{(.*?)}".toRegex()) { r -> r.groupValues[1].split(",").joinToString("|", "\\E(", ")\\Q") { it.trim() } }
			.trimRegex()
	}),

	/**
	 * 路径引用匹配类型。用于匹配关注查询的引用。
	 *
	 * 规则：
	 * * `$index` - 表示一个指定索引的元素。
	 * * `$key` - 表示一个指定键的值。
	 * * `$index1-$index2` - 表示一个用指定索引范围过滤的子列表。
	 * * `re:$regex` - 表示一个用指定正则表达式过滤的子列表/映射。
	 * * `-`, `[]` - 表示一个列表。
	 * * `[\$placeholder]` - 表示一个用指定占位符表示的列表。
	 * * `-`, `{}` - 表示一个映射。
	 * * `{$placeholder}` - 表示一个用指定占位符表示的映射。
	 *
	 * 示例：
	 * * `"/{Category}/0/Name"`
	 * * `"/{Category}/0-10/Name"`
	 * * `"/{Category}/0-10/re:*Name"`
	 * * `"/{Category}/[List]/Name"`
	 */
	PathReference({ pattern ->
		pattern.escapeRegex()
			.replace("/(?:\\[]|-|\\[[^/]*?])".toRegex()) { "/\\E\\d+\\Q" }
			.replace("/(?:\\{}|\\{[^/]*?})".toRegex()) { "/\\E[^/]*\\Q" }
			.replace("/(\\d+)(?:\\.\\.|-)(\\d+)".toRegex()) { "/\\E${Regex.fromRange(it.groupValues[1].toInt(), it.groupValues[2].toInt())}\\Q" }
			.transformIn("\\Q", "\\E") { it.replace("/re:([^/]*)".toRegex()) { r -> "/\\E${r.groupValues[1]}\\Q" } }
			.trimRegex()
	});

	companion object {
		private fun String.escapeRegex() = Regex.escape(this)

		private fun String.trimRegex() = this.remove("\\Q\\E")
	}
}
