package com.windea.breezeframework.core.enums

import com.windea.breezeframework.core.annotations.marks.*

/**引用的显示格式。*/
@NotTested
enum class ReferenceCase(
	val regex: Regex,
	val joinFunction: (List<String>) -> String
) {
	Unknown("^$".toRegex(), { it.first() }),
	/**
	 * 标准引用。
	 *
	 * * 示例：`Reference.path[0]`。
	 * * `Reference` 表示一个对象/属性/映射的值。
	 * * `[0]` 表示一个列表的元素。
	 */
	//TODO 支持静态成员/类型 `T(a).b`
	StandardReference(
		"^([a-zA-Z_])(?:(?:\\.([a-zA-Z_]))|(?:\\[(\\d+)]))*$".toRegex(),
		{ it.joinToString(".").replace("^(\\d+)$|\\.?(\\d+)\\.?".toRegex(), "[$1$2]") }
	),
	/*
	 * Json引用。
	 *
	 * * 示例：`$.Category.[0].Name`。
	 * * `Reference` 表示一个对象/属性/映射的值。
	 * * `[0]` 表示一个列表的元素。
	 */
	JsonReference(
		"^\\$\\.(?:\\.(?:([a-zA-Z_]))|(?:\\[(\\d+)]))*$".toRegex(),
		{ it.joinToString(".").replace("^(\\d+)$".toRegex(), "[$1]") }
	),
	/**
	 * Json Schema引用。
	 *
	 * * 示例：`#/{Category}/0/Name`。
	 * * `[]`或`-` 表示一个列表。
	 * * '\[WeaponList]' 表示一个注为指定占位符的列表。
	 * * `1..10` 表示一个列表的指定范围内的元素。
	 * * `1` 表示一个列表的对应索引的元素。
	 * * `{}` 表示一个映射。
	 * * `{Category}` 表示一个注为指定占位符的映射。
	 * * `regex:.*Name` 表示一个映射的键符合指定正则的键值对。
	 * * `Name` 表示一个映射的对应键的值。
	 */
	JsonSchemaReference(
		"^#?(?:/(.+))+$".toRegex(),
		{ it.joinToString("/", "/") }
	)
}
