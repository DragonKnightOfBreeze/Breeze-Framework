// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("PathFormatExtensions")

package icu.windea.breezeframework.core.component.extension

import icu.windea.breezeframework.core.component.PathFormat
import icu.windea.breezeframework.core.component.PathFormats

/**
 * 根据指定的路径类型，判断当前字符串是否匹配指定的路径。
 * 默认使用标准路径[PathFormats.StandardPath]。
 *
 * @see PathFormat
 */
fun String.matchesBy(path: String, pathFormat: PathFormat = PathFormats.StandardPath): Boolean {
	return pathFormat.matches(this, path)
}

/**
 * 根据指定的路径类型，解析路径变量。如果路径不匹配，则返回空结果。
 * 默认使用标准路径[PathFormats.StandardPath]。
 *
 * @see PathFormat
 */
fun String.resolveVariablesBy(path: String, pathFormat: PathFormat = PathFormats.StandardPath): Map<String, String> {
	return pathFormat.resolveVariables(this, path)
}

/**
 * 根据指定路径和指定路径类型查询当前数组，返回查询结果列表。
 * 如果指定路径为空路径，则目标返回查询对象的单例列表。
 * 默认使用标准路径[PathFormats.StandardPath]。
 *
 * @see PathFormat
 */
fun <T> Array<*>.queryBy(path: String, pathFormat: PathFormat = PathFormats.StandardPath): List<T> {
	return pathFormat.query(this, path)
}

/**
 * 根据指定路径和指定路径类型查询当前列表，返回查询结果列表。
 * 如果指定路径为空路径，则目标返回查询对象的单例列表。
 * 默认使用标准路径[PathFormats.StandardPath]。
 *
 * @see PathFormat
 */
fun <T> List<*>.queryBy(path: String, pathFormat: PathFormat = PathFormats.StandardPath): List<T> {
	return pathFormat.query(this, path)
}

/**
 * 根据指定路径和指定路径类型递归查询当前映射，返回查询结果列表。
 * 如果指定路径为空路径，则目标返回查询对象的单例列表。
 * 默认使用标准路径[PathFormats.StandardPath]。
 *
 * @see PathFormat
 */
fun <T> Map<*, *>.queryBy(path: String, pathFormat: PathFormat = PathFormats.StandardPath): List<T> {
	return pathFormat.query(this, path)
}

/**
 * 根据指定路径和指定路径类型查询当前数组，得到首个匹配的值，或者抛出异常。
 * 如果指定路径为空路径，则目标返回查询对象本身。
 * 默认使用标准路径[PathFormats.StandardPath]。
 *
 * @see PathFormat
 */
fun <T> Array<*>.getBy(path: String, pathFormat: PathFormat = PathFormats.StandardPath): T {
	return pathFormat.get(this, path)
}

/**
 * 根据指定路径和指定路径类型查询当前列表，得到首个匹配的值，或者抛出异常。
 * 如果指定路径为空路径，则目标返回查询对象本身。
 * 默认使用标准路径[PathFormats.StandardPath]。
 *
 * @see PathFormat
 */
fun <T> List<*>.getBy(path: String, pathFormat: PathFormat = PathFormats.StandardPath): T {
	return pathFormat.get(this, path)
}

/**
 * 根据指定路径和指定路径类型递归查询当前映射，得到首个匹配的值，或者抛出异常。
 * 如果指定路径为空路径，则目标返回查询对象本身。
 * 默认使用标准路径[PathFormats.StandardPath]。
 *
 * @see PathFormat
 */
fun <T> Map<*, *>.getBy(path: String, pathFormat: PathFormat = PathFormats.StandardPath): T {
	return pathFormat.get(this, path)
}

/**
 * 根据指定路径和指定路径类型查询当前数组，得到首个匹配的值，或者返回null。
 * 如果指定路径为空路径，则目标返回查询对象本身。
 * 默认使用标准路径[PathFormats.StandardPath]。
 *
 * @see PathFormat
 */
fun <T> Array<*>.getOrNullBy(path: String, pathFormat: PathFormat = PathFormats.StandardPath): T? {
	return pathFormat.getOrNull(this, path)
}

/**
 * 根据指定路径和指定路径类型查询当前列表，得到首个匹配的值，或者返回null。
 * 如果指定路径为空路径，则目标返回查询对象本身。
 * 默认使用标准路径[PathFormats.StandardPath]。
 *
 * @see PathFormat
 */
fun <T> List<*>.getOrNullBy(path: String, pathFormat: PathFormat = PathFormats.StandardPath): T? {
	return pathFormat.getOrNull(this, path)
}

/**
 * 根据指定路径和指定路径类型递归查询当前映射，得到首个匹配的值，或者返回null。
 * 如果指定路径为空路径，则目标返回查询对象本身。
 * 默认使用标准路径[PathFormats.StandardPath]。
 *
 * @see PathFormat
 */
fun <T> Map<*, *>.getOrNullBy(path: String, pathFormat: PathFormat = PathFormats.StandardPath): T? {
	return pathFormat.getOrNull(this, path)
}

/**
 * 根据指定路径和指定路径类型查询当前数组，得到首个匹配的值，或者返回默认值。
 * 如果指定路径为空路径，则目标返回查询对象本身。
 * 默认使用标准路径[PathFormats.StandardPath]。
 *
 * @see PathFormat
 */
fun <T> Array<*>.getOrDefaultBy(path: String, pathFormat: PathFormat = PathFormats.StandardPath, defaultValue: T): T {
	return pathFormat.getOrDefault(this, path, defaultValue)
}

/**
 * 根据指定路径和指定路径类型查询当前列表，得到首个匹配的值，或者返回默认值。
 * 如果指定路径为空路径，则目标返回查询对象本身。
 * 默认使用标准路径[PathFormats.StandardPath]。
 *
 * @see PathFormat
 */
fun <T> List<*>.getOrDefaultBy(path: String, pathFormat: PathFormat = PathFormats.StandardPath, defaultValue: T): T {
	return pathFormat.getOrDefault(this, path, defaultValue)
}

/**
 * 根据指定路径和指定路径类型递归查询当前映射，得到首个匹配的值，或者返回默认值。
 * 如果指定路径为空路径，则目标返回查询对象本身。
 * 默认使用标准路径[PathFormats.StandardPath]。
 *
 * @see PathFormat
 */
fun <T> Map<*, *>.getOrDefaultBy(path: String, pathFormat: PathFormat = PathFormats.StandardPath, defaultValue: T): T {
	return pathFormat.getOrDefault(this, path, defaultValue)
}

/**
 * 根据指定路径和指定路径类型查询当前数组，得到首个匹配的值，或者返回默认值。
 * 如果指定路径为空路径，则目标返回查询对象本身。
 * 默认使用标准路径[PathFormats.StandardPath]。
 *
 * @see PathFormat
 */
fun <T> Array<*>.getOrElseBy(path: String, pathFormat: PathFormat = PathFormats.StandardPath, defaultValue: () -> T): T {
	return pathFormat.getOrElse(this, path, defaultValue)
}

/**
 * 根据指定路径和指定路径类型查询当前列表，得到首个匹配的值，或者返回默认值。
 * 如果指定路径为空路径，则目标返回查询对象本身。
 * 默认使用标准路径[PathFormats.StandardPath]。
 *
 * @see PathFormat
 */
fun <T> List<*>.getOrElseBy(path: String, pathFormat: PathFormat = PathFormats.StandardPath, defaultValue: () -> T): T {
	return pathFormat.getOrElse(this, path, defaultValue)
}

/**
 * 根据指定路径和指定路径类型递归查询当前映射，得到首个匹配的值，或者返回默认值。
 * 如果指定路径为空路径，则目标返回查询对象本身。
 * 默认使用标准路径[PathFormats.StandardPath]。
 *
 * @see PathFormat
 */
fun <T> Map<*, *>.getOrElseBy(path: String, pathFormat: PathFormat = PathFormats.StandardPath, defaultValue: () -> T): T {
	return pathFormat.getOrElse(this, path, defaultValue)
}
