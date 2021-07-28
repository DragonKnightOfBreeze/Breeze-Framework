// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

//pathMatcher *.kt
//https://docs.oracle.com/javase/8/docs/api/java/nio/file/FileSystem.html#getPathMatcher-java.lang.String-

/**
 * 路径格式。
 *
 * 路径格式用于表示查询对象在其结构中的位置，可以包含多个元路径和变量，可以用于匹配和查询。
 */
@Suppress("UNCHECKED_CAST", "KDocUnresolvedReference")
interface PathFormat : Component {
	/**
	 * 标准化指定的路径。将会去除其中的空白以及尾随的分隔符。
	 */
	fun normalize(path: String): String

	/**
	 * 判断指定的字符串是否匹配指定的路径。
	 */
	fun matches(value: String, path: String): Boolean

	/**
	 * 解析路径变量。如果路径不匹配，则返回空结果。
	 */
	fun resolveVariables(value: String, path: String): Map<String, String>

	/**
	 * 将指定的字符串分隔成元路径列表，并过滤空的元路径。
	 */
	fun split(path: String): List<String>

	/**
	 * 将指定的字符串分隔成元路径序列，并过滤空的元路径。
	 */
	fun splitToSequence(path: String): Sequence<String>

	/**
	 * 将元路径数组组合成完整路径。
	 */
	fun joinToString(metaPaths: Array<String>): String

	/**
	 * 将元路径列表组合成完整路径。
	 */
	fun joinToString(metaPaths: Iterable<String>): String

	/**
	 * 将元路径序列组合成完整路径。
	 */
	fun joinToString(metaPaths: Sequence<String>): String

	/**
	 * 根据指定路径查询查询对象，返回查询结果列表。
	 * 如果指定路径为空路径，则返回查询对象的单例列表。
	 */
	fun <T> query(value: Any, path: String): List<T>

	/**
	 * 根据指定路径查询查询对象，得到首个匹配的值，或者抛出异常。
	 * 如果指定路径为空路径，则返回查询对象本身。
	 */
	fun <T> get(value: Any, path: String): T

	/**
	 * 根据指定路径查询查询对象，得到首个匹配的值，或者返回null。
	 * 如果指定路径为空路径，则返回查询对象本身。
	 */
	fun <T> getOrNull(value: Any, path: String): T?

	/**
	 * 根据指定路径查询查询对象，得到首个匹配的值，或者返回默认值。
	 * 如果指定路径为空路径，则返回查询对象本身。
	 */
	fun <T> getOrDefault(value: Any, path: String, defaultValue: T): T

	/**
	 * 根据指定路径查询查询对象，得到首个匹配的值，或者返回默认值。
	 * 如果指定路径为空路径，则返回查询对象本身。
	 */
	fun <T> getOrElse(value: Any, path: String, defaultValue: () -> T): T
}
