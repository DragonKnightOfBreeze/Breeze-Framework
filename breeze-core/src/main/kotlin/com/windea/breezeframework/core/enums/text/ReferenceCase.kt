package com.windea.breezeframework.core.enums.text

import com.windea.breezeframework.core.extensions.*

/**引用的显示格式。*/
enum class ReferenceCase(
	override val splitter: (CharSequence) -> Sequence<String> = { sequenceOf(it.toString()) },
	override val joiner: (Iterable<CharSequence>) -> String = { it.joinToString("") },
	override val arrayJoiner: (Array<out CharSequence>) -> String = { it.joinToString("") },
	override val regex: Regex? = null,
	override val predicate: (String) -> Boolean = { regex == null || it matches regex }
) : FormatCase {
	/**
	 * 路径引用。
	 *
	 * 示例：`/{Category}/0/Name`
	 *
	 * * `1` 表示一个列表的指定索引的元素。
	 * * `1..10` 表示一个列表的指定索引范围内的元素。
	 * * `[]`, `-` 表示一个列表。
	 * * `\[List]` 表示一个注为指定占位符的列表。
	 * * `Name` 表示一个映射的指定键的值。
	 * * `re:.*Name` 表示一个映射的键符合指定正则的键值对。
	 * * `{}` , `-` 表示一个映射。
	 * * `{Category}` 表示一个注为指定占位符的映射。
	 */
	PathReference(
		{ it.removePrefix("/").splitToSequence('/') },
		{ it.joinToString("/", "/") },
		{ it.joinToString("/", "/") },
		"""(?:/.+)+""".toRegex()
	),
	/**
	 * Java引用。
	 *
	 * 示例：`Category[0].name`
	 *
	 * * `Category` 表示一个对象/对象的属性/映射的值。
	 * * `T(Category)` 表示一个类型。（暂不支持）
	 * * `[0]` 表示一个列表的元素。
	 * * `name` 表示一个对象的属性/映射的值。
	 */
	JavaReference(
		{ it.splitToSequence('[', '.').dropEmpty().map { s -> s.removeSuffix("]") } },
		{ it.joinToString(".").wrapIndex().replace(".[", "[") },
		{ it.joinToString(".").wrapIndex().replace(".[", "[") },
		"""(?:[a-zA-Z_$]+|\[\d+])(?:\.[a-zA-Z_$]+|\[\d+])*""".toRegex()
	),
	/**
	 * Json引用。
	 *
	 * 示例：`$.Category.[0].Name`
	 *
	 * * `Category` 表示一个对象的属性/映射的值。
	 * * `[0]` 表示一个列表的元素。
	 */
	JsonReference(
		{ it.removePrefix("$.").splitToSequence('.').map { s -> s.removeSurrounding("[", "]") } },
		{ it.joinToString(".", "$.").wrapIndex() },
		{ it.joinToString(".", "$.").wrapIndex() },
		"""\$\..*""".toRegex() //不严格验证
	),
	/**未知格式。*/
	Unknown;
}


/**使用方括号包围索引。*/
private fun String.wrapIndex(): String {
	return this.replace("""\d+""".toRegex(), "[$0]")
}
