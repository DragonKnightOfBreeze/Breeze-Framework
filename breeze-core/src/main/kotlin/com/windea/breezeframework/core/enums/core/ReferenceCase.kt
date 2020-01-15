package com.windea.breezeframework.core.enums.core

/**引用的显示格式。*/
enum class ReferenceCase(
	override val regex: Regex,
	override val splitFunction: (String) -> List<String>,
	override val joinFunction: (List<String>) -> String
) : FormatCase {
	/**
	 * 标准引用。
	 *
	 * * 示例：`Reference.path[0]`
	 * * `Reference` 表示一个对象/属性/映射的值。
	 * * `[0]` 表示一个列表的元素。
	 */
	//TODO 支持静态成员/类型 `T(a).b`
	Standard(
		//allow: $, ., words, [number]
		"""(?:[a-zA-Z_$]+|\[\d+])(?:\.(?:[a-zA-Z_$]+|\[\d+]))*""".toRegex(),
		{ it.replace("]", "].").split(".").map { s -> s.removeSurrounding("[", "]") } },
		{ it.joinToString(".").replace("""(\d+)""".toRegex(), "[$1]").replace(".[", "[") }
	),
	/**
	 * Json引用。
	 *
	 * * 示例：`$.Category.[0].Name`
	 * * `Reference` 表示一个对象/属性/映射的值。
	 * * `[0]` 表示一个列表的元素。
	 */
	//TODO 更符合规范
	Json(
		//allow: $, ., words, [number]
		"""\$(?:\.(?:[a-zA-Z_]+|\[\d+]))*""".toRegex(),
		{ it.removePrefix("$.").split(".").map { s -> s.removeSurrounding("[", "]") } },
		{ it.joinToString(".", "$.").replace("""(\d+)""".toRegex(), "[$1]") }
	),
	/**
	 * Json Schema引用。
	 *
	 * * 示例：`#/{Category}/0/Name`
	 * * `[]`, `-` 表示一个列表。
	 * * `\[WeaponList]` 表示一个注为指定占位符的列表。
	 * * `1..10` 表示一个列表的指定范围内的元素。
	 * * `1` 表示一个列表的对应索引的元素。
	 * * `{}` 表示一个映射。
	 * * `{Category}` 表示一个注为指定占位符的映射。
	 * * `regex:.*Name` 表示一个映射的键符合指定正则的键值对。
	 * * `Name` 表示一个映射的对应键的值。
	 */
	//TODO 严格验证
	JsonSchema(
		//allow: #, /, unchecked supPaths
		"""#?(?:/.+)+""".toRegex(),
		{ it.removePrefix("#").removePrefix("/").split("/") },
		{ it.joinToString("/", "#/") }
	),
	Unknown(".*".toRegex(), { listOf(it) }, { it.joinToString("") });
}
