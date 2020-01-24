@file:JvmName("RegexExtensions")

package com.windea.breezeframework.core.extensions

/**得到指定字符串对于当前正则表达式的匹配分组。*/
fun Regex.matchGroups(string: String): MatchGroupCollection? = this.matchEntire(string)?.groups

/**得到指定字符串对于当前正则表达式的匹配字符串分组。*/
fun Regex.matchGroupValues(string: String): List<String>? = this.matchEntire(string)?.groupValues

/**得到当前匹配结果的指定索引的匹配字符串。*/
operator fun MatchResult.get(index: Int): String = this.groupValues[index]


/**
 * 解析多位数字范围，转化为合法的正则表达式字符串。
 *
 * 例如：`[2-13] -> [2-9]|[1][0-3]`。
 */
fun Regex.Companion.parseNumberRange(literal: String): String {
	TODO()
}
