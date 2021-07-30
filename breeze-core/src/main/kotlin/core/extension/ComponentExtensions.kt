// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("ComponentExtensions")

package icu.windea.breezeframework.core.extension

import icu.windea.breezeframework.core.annotation.*
import icu.windea.breezeframework.core.component.*
import java.lang.reflect.*
import java.nio.charset.*

//region Converter Extensions
/**
 * 根据可选的配置参数，将当前对象转化为指定类型。如果转换失败，则抛出异常。
 *
 * @see Converter
 */
inline fun <reified T> Any?.convert(componentParams: Map<String, Any?> = emptyMap()): T {
	if(this == null) return runCatching { this as T }.getOrElse {
		throw IllegalArgumentException("Cannot convert null value to a non-null type.")
	}
	return Converters.convert(this, componentParams)
}

/**
 * 根据可选的配置参数，将当前对象转化为指定类型。如果转换失败，则抛出异常。
 *
 * @see Converter
 */
@Suppress("UNCHECKED_CAST")
fun <T> Any?.convert(targetType: Class<T>, componentParams: Map<String, Any?> = emptyMap()): T {
	if(this == null) throw IllegalArgumentException("Cannot convert null value to a non-null type.")
	return Converters.convert(this, targetType, componentParams)
}

/**
 * 根据可选的配置参数，将当前对象转化为指定类型。如果转换失败，则抛出异常。
 *
 * @see Converter
 */
@Suppress("UNCHECKED_CAST")
fun Any?.convert(targetType: Type, componentParams: Map<String, Any?> = emptyMap()): Any {
	if(this == null) throw IllegalArgumentException("Cannot convert null value to a non-null type.")
	return Converters.convert(this, targetType, componentParams)
}

/**
 * 根据可选的配置参数，将当前对象转化为指定类型。如果转换失败，则返回null。
 *
 * @see Converter
 */
inline fun <reified T> Any?.convertOrNull(componentParams: Map<String, Any?> = emptyMap()): T? {
	if(this == null) return this
	return Converters.convertOrNull(this, componentParams)
}

/**
 * 根据可选的配置参数，将当前对象转化为指定类型。如果转换失败，则返回null。
 *
 * @see Converter
 */
fun <T> Any?.convertOrNull(targetType: Class<T>, componentParams: Map<String, Any?> = emptyMap()): T? {
	if(this == null) return this
	return Converters.convertOrNull(this, targetType, componentParams)
}

/**
 * 根据可选的配置参数，将当前对象转化为指定类型。如果转换失败，则返回null。
 *
 * @see Converter
 */
fun Any?.convertOrNull(targetType: Type, componentParams: Map<String, Any?> = emptyMap()): Any? {
	if(this == null) return this
	return Converters.convertOrNull(this, targetType, componentParams)
}
//endregion

//region Encoder Extensions
/**
 * 根据指定的编码器，编码当前字符串，以指定的字符集显示。
 *
 * @see Encoder
 */
@UnstableApi
fun String.encodeBy(encoder: Encoder, charset: Charset = Charsets.UTF_8): String {
	return encoder.encode(this, charset)
}

/**
 * 根据指定的编码器，解码当前字符串，以指定的字符集显示。
 *
 * @see Encoder
 */
@UnstableApi
fun String.decodeBy(encoder: Encoder, charset: Charset = Charsets.UTF_8): String {
	return encoder.decode(this, charset)
}
//endregion

//region Encrypter Extensions
/**
 * 根据指定的加密器，加密当前字节数组。
 *
 * @see Encrypter
 */
@UnstableApi
fun ByteArray.encryptBy(encrypter: Encrypter, secret: ByteArray? = null): ByteArray {
	return encrypter.encrypt(this, secret)
}

/**
 * 根据指定的加密器，解密当前字符串。某些加密算法可能不支持解密。
 *
 * @throws UnsupportedOperationException 如果不支持解密。
 * @see Encrypter
 */
@UnstableApi
fun ByteArray.decryptBy(encrypter: Encrypter, secret: ByteArray? = null): ByteArray {
	return encrypter.decrypt(this, secret)
}
//endregion

//region Escaper Extensions
/**
 * 根据指定的转义器，转义当前字符串。
 *
 * @see Escaper
 */
fun String.escapeBy(escaper: Escaper): String {
	return escaper.escape(this)
}

/**
 * 根据指定的转义器，反转义当前字符串。
 *
 * @see Escaper
 */
fun String.unescapeBy(escaper: Escaper): String {
	return escaper.unescape(this)
}
//endregion

//region Default Generator Extensions
/**
 * 根据可选的配置参数，生成指定类型的默认值。
 *
 * @see DefaultGenerator
 */
inline fun <reified T : Any> defaultValue(componentParams: Map<String, Any?> = emptyMap()): T {
	return DefaultGenerators.generate(componentParams)
}

/**
 * 根据可选的配置参数，生成指定类型的默认值。
 *
 * @see DefaultGenerator
 */
fun <T : Any> defaultValue(targetType: Class<T>, componentParams: Map<String, Any?> = emptyMap()): T {
	return DefaultGenerators.generate(targetType, componentParams)
}

/**
 * 根据可选的配置参数，生成指定类型的默认值。
 *
 * @see DefaultGenerator
 */
fun <T : Any> defaultValue(targetType: Type, componentParams: Map<String, Any?> = emptyMap()): T {
	return DefaultGenerators.generate(targetType, componentParams)
}
//endregion

//region Random Generator Extensions
/**
 * 根据可选的配置参数，生成指定类型的随机值。
 *
 * @see RandomGenerator
 */
inline fun <reified T : Any> randomValue(componentParams: Map<String, Any?> = emptyMap()): T {
	return RandomGenerators.generate(componentParams)
}

/**
 * 根据可选的配置参数，生成指定类型的随机值。
 *
 * @see RandomGenerator
 */
fun <T : Any> randomValue(targetType: Class<T>, componentParams: Map<String, Any?> = emptyMap()): T {
	return RandomGenerators.generate(targetType, componentParams)
}

/**
 * 根据可选的配置参数，生成指定类型的随机值。
 *
 * @see RandomGenerator
 */
fun <T : Any> randomValue(targetType: Type, componentParams: Map<String, Any?> = emptyMap()): T {
	return RandomGenerators.generate(targetType, componentParams)
}
//endregion

//region Char Matcher Extensions
/**
 * 判断指定的字符是否匹配指定的字符匹配器。
 *
 * @see StringMatcher
 */
fun Char.matchesBy(charMatcher: CharMatcher): Boolean {
	return charMatcher.matches(this)
}
//endregion

//region String Matcher Extensions
/**
 * 判断指定的字符串是否匹配指定的字符串匹配器。
 *
 * @see StringMatcher
 */
fun String.matchesBy(stringMatcher: StringMatcher): Boolean {
	return stringMatcher.matches(this)
}
//endregion

//region PathFormat Extensions
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
//endregion

//region CaseFormat Extensions
/**
 * 尝试推断当前字符串的字母格式。
 *
 * @see CaseFormat
 */
fun String.inferCase(): CaseFormat? {
	return CaseFormats.infer(this)
}

/**
 * 判断当前字符串是否匹配指定的字母格式。
 *
 * @see CaseFormat
 */
fun String.matchesBy(caseFormat: CaseFormat): Boolean {
	return caseFormat.matches(this)
}

/**
 * 根据指定的字母格式，分割当前字符串，返回对应的字符串列表。
 *
 * @see CaseFormat
 */
fun String.splitBy(caseFormat: CaseFormat): List<String> {
	return caseFormat.split(this)
}

/**
 * 根据指定的字母格式，分割当前字符串，返回对应的字符串序列。
 *
 * @see CaseFormat
 */
fun String.splitToSequenceBy(caseFormat: CaseFormat): Sequence<String> {
	return caseFormat.splitToSequence(this)
}

/**
 * 根据指定的字母格式，将当前字符串数组中的元素加入到字符串。
 *
 * @see CaseFormat
 */
fun Array<String>.joinToStringBy(caseFormat: CaseFormat): String {
	return caseFormat.joinToString(this)
}

/**
 * 根据指定的字母格式，将当前字符串集合中的元素加入到字符串。
 *
 * @see CaseFormat
 */
fun Iterable<String>.joinToStringBy(caseFormat: CaseFormat): String {
	return caseFormat.joinToString(this)
}

/**
 * 根据指定的字母格式，将当前字符串序列中的元素加入到字符串。
 *
 * @see CaseFormat
 */
fun Sequence<String>.joinToStringBy(caseFormat: CaseFormat): String {
	return caseFormat.joinToString(this)
}

/**
 * 根据指定的字母格式，切换当前字符串的格式。
 *
 * @see CaseFormat
 */
fun String.switchCaseBy(sourceCaseFormat: CaseFormat, targetCaseFormat: CaseFormat): String {
	return splitBy(sourceCaseFormat).joinToStringBy(targetCaseFormat)
}

/**
 * 根据指定的字母格式，切换当前字符串的格式。如果不指定字母格式，则尝试推断或者抛出异常。
 *
 * @see CaseFormat
 */
fun String.switchCaseBy(targetCaseFormat: CaseFormat): String {
	val sourceLetterCase = inferCase() ?: throw IllegalArgumentException("Cannot infer letter case for string '$this'.")
	return splitBy(sourceLetterCase).joinToStringBy(targetCaseFormat)
}
//endregion
