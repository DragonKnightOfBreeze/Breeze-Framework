// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.model

import com.windea.breezeframework.core.extension.*

/**引用的格式。*/
enum class ReferenceCase(
	override val splitter: (CharSequence) -> List<String> = { listOf(it.toString()) },
	override val sequenceSplitter: (CharSequence) -> Sequence<String> = { sequenceOf(it.toString()) },
	override val joiner: (Iterable<CharSequence>) -> String = { it.joinToString("") },
	override val arrayJoiner: (Array<out CharSequence>) -> String = { it.joinToString("") },
	override val regex: Regex? = null,
	override val predicate: (String) -> Boolean = { regex == null || it matches regex },
) : DisplayCase {
	/**
	 * 路径引用。
	 *
	 * 规则：
	 * * `$index` - 表示一个指定索引的元素。
	 * * `$key` - 表示一个指定键的值。
	 * * `$index-$index` - 表示一个用指定索引范围过滤的子列表。
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
	PathReference(
		{ it.removePrefix("/").split('/').dropEmpty() },
		{ it.removePrefix("/").splitToSequence('/').dropEmpty() },
		{ it.joinToString("/", "/") },
		{ it.joinToString("/", "/") },
		"""(?:/.+)+""".toRegex()
	),

	/**
	 * 对象引用。
	 *
	 * 规则：
	 * * `Category` - 表示一个对象/对象的属性/映射的值。
	 * * `T(Category)` - 表示一个类型。（暂不支持）
	 * * `[0]` - 表示一个列表的元素。
	 * * `name` - 表示一个对象的属性/映射的值。
	 *
	 * 示例：
	 * * `"categories[0].name"`
	 */
	ObjectReference(
		{ it.split('[', '.').dropEmpty().map { s -> s.removeSuffix("]") } },
		{ it.splitToSequence('[', '.').dropEmpty().map { s -> s.removeSuffix("]") } },
		{ it.joinToString(".").wrapIndex().replace(".[", "[") },
		{ it.joinToString(".").wrapIndex().replace(".[", "[") },
		"""(?:[a-zA-Z_$]+|\[\d+])(?:\.[a-zA-Z_$]+|\[\d+])*""".toRegex()
	),
	//TODO 有待完善
	/**
	 * Json引用。
	 *
	 * 规则：
	 * * `Category` 表示一个对象的属性/映射的值。
	 * * `[0]` 表示一个列表的元素。
	 *
	 * 示例：
	 * * `"$.Category.[0].Name"`
	 */
	JsonReference(
		{ it.removePrefix("$.").split('.').dropEmpty().map { s -> s.removeSurrounding("[", "]") } },
		{ it.removePrefix("$.").splitToSequence('.').dropEmpty().map { s -> s.removeSurrounding("[", "]") } },
		{ it.joinToString(".", "$.").wrapIndex() },
		{ it.joinToString(".", "$.").wrapIndex() },
		"""\$.*""".toRegex() //不进行严格验证
	),

	/**未知的引用格式。*/
	Unknown;

	companion object {
		private val wrapIndexRegex = """\d+""".toRegex()
		private fun String.wrapIndex() = this.replace(wrapIndexRegex, "[$0]")
	}
}
