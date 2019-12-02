package com.windea.breezeframework.core.extensions

/**得到指定字符串对于当前正则的匹配分组。不包含索引0的分组，返回的列表可能为空。*/
fun Regex.matchValues(string: String): List<String> {
	return this.matchEntire(string)?.groupValues?.drop(1) ?: listOf()
}
