package com.windea.breezeframework.core.enums.core

/**引用的显示格式。*/
enum class ReferenceCase(
	override val regex: Regex,
	override val splitFunction: (String) -> List<String>,
	override val joinFunction: (List<String>) -> String
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
	 * * `{}` 表示一个映射。
	 * * `{Category}` 表示一个注为指定占位符的映射。
	 */
	PathReference(
		//allow: /, unchecked subPaths
		"""(?:/.+)+""".toRegex(),
		{ it.removePrefix("/").split("/") },
		{ it.joinToString("/", "/") }
	),
	/**
	 * Java引用。
	 *
	 * 示例：`Category[0].name`
	 *
	 * * `Category` 表示一个对象/对象的属性/映射的值。
	 * * `[0]` 表示一个列表的元素。
	 * * `name` 表示一个对象的属性/映射的值。
	 */
	//TODO 支持静态成员/类型 `T(a).b`
	JavaReference(
		//allow: $, ., words, [number]
		"""(?:[a-zA-Z_$]+|\[\d+])(?:\.(?:[a-zA-Z_$]+|\[\d+]))*""".toRegex(),
		{ it.replace("[", ".[").split(".").dropWhile { s -> s.isEmpty() }.map { s -> s.removeSurrounding("[", "]") } },
		{ it.joinToString(".").replace("""(\d+)""".toRegex(), "[$1]").replace(".[", "[") }
	),
	/**
	 * Json引用。
	 *
	 * 示例：`$.Category.[0].Name`
	 *
	 * * `Category` 表示一个对象的属性/映射的值。
	 * * `[0]` 表示一个列表的元素。
	 */
	//TODO 更符合规范
	JsonReference(
		//allow: $, ., words, [number]
		"""\$(?:\.(?:[a-zA-Z_]+|\[\d+]))*""".toRegex(),
		{ it.removePrefix("$.").split(".").map { s -> s.removeSurrounding("[", "]") } },
		{ it.joinToString(".", "$.").replace("""(\d+)""".toRegex(), "[$1]") }
	),
	/**未知格式。*/
	Unknown(".*".toRegex(), { listOf(it) }, { it.joinToString("") });
}
