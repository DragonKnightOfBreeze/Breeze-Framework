package com.windea.breezeframework.core.enums

/**路径的显示格式。*/
enum class PathCase(
	val regex: Regex
) {
	Unknown("^$".toRegex()),
	/**
	 * Windows路径。
	 *
	 * 示例：`Directory\\FileName.ext`。
	 */
	WindowsPath("^\\.?\\.?(?:\\\\\\\\)?(.+)(?:\\\\\\\\(.+))*$".toRegex()),
	/**
	 * Unix路径。
	 *
	 * 示例：`Directory/FileName.ext`。
	 */
	UnixPath("^\\.?\\.?(?:/)?(.+)(?:/(.+))*$".toRegex()),
	/**
	 * 引用路径。
	 *
	 * 示例：`Reference.path[0]`。
	 *
	 * * `Reference` 表示一个对象/属性/映射的值。
	 * * `[0]` 表示一个列表的元素。
	 */
	ReferencePath("^([a-zA-Z_])(?:(?:\\.([a-zA-Z_]))|(?:\\[(\\d+)]))*$".toRegex()),
	/**
	 * Json路径。
	 *
	 * 示例：`#/{Category}/0/Name`。
	 *
	 * * `[]`或`-` 表示一个列表。
	 * * '\[WeaponList]' 表示一个注为指定占位符的列表。
	 * * `1..10` 表示一个列表的指定范围内的元素。
	 * * `1` 表示一个列表的对应索引的元素。
	 * * `{}` 表示一个映射。
	 * * `{Category}` 表示一个注为指定占位符的映射。
	 * * `regex:.*Name` 表示一个映射的键符合指定正则的键值对。
	 * * `Name` 表示一个映射的对应键的值。
	 */
	JsonPath("^#?(?:/(.+))+$".toRegex())
}
