// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("ComponentExtensions")

package com.windea.breezeframework.core.extension

import com.windea.breezeframework.core.annotation.*
import com.windea.breezeframework.core.component.*
import java.nio.charset.*

//region Converter Extensions
/**
 * 将当前对象转化为指定类型。如果转换失败，则抛出异常。转化后的对象是基于一般转化逻辑得到的新对象。
 *
 * @see Converter
 */
inline fun <reified T> Any?.convert(): T {
	return if(this is T) this else Converter.convert(this)
}

/**
 * 将当前对象转化为指定类型。如果转换失败，则返回null。转化后的对象是基于一般转化逻辑得到的新对象。
 *
 * @see Converter
 */
inline fun <reified T> Any?.convertOrNull(): T? {
	return if(this is T) this else Converter.convertOrNull(this)
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

//region LetterCase Extensions
/**
 * 尝试推断当前字符串的字母格式。
 *
 * @see LetterCase
 */
fun String.inferCase(): LetterCase? {
	return LetterCase.infer(this)
}

/**
 * 判断当前字符串是否匹配指定的字母格式。
 *
 * @see LetterCase
 */
fun String.matchesBy(letterCase: LetterCase): Boolean {
	return letterCase.matches(this)
}

/**
 * 根据指定的字母格式，分割当前字符串，返回对应的字符串列表。
 *
 * @see LetterCase
 */
fun String.splitBy(letterCase: LetterCase): List<String> {
	return letterCase.split(this)
}

/**
 * 根据指定的字母格式，分割当前字符串，返回对应的字符串序列。
 *
 * @see LetterCase
 */
fun String.splitToSequenceBy(letterCase: LetterCase): Sequence<String> {
	return letterCase.splitToSequence(this)
}

/**
 * 根据指定的字母格式，将当前字符串数组中的元素加入到字符串。
 *
 * @see LetterCase
 */
fun Array<String>.joinToStringBy(letterCase: LetterCase): String {
	return letterCase.joinToString(this)
}

/**
 * 根据指定的字母格式，将当前字符串集合中的元素加入到字符串。
 *
 * @see LetterCase
 */
fun Iterable<String>.joinToStringBy(letterCase: LetterCase): String {
	return letterCase.joinToString(this)
}

/**
 * 根据指定的字母格式，将当前字符串序列中的元素加入到字符串。
 *
 * @see LetterCase
 */
fun Sequence<String>.joinToStringBy(letterCase: LetterCase): String {
	return letterCase.joinToString(this)
}

/**
 * 根据指定的字母格式，切换当前字符串的格式。
 *
 * @see LetterCase
 */
fun String.switchCaseBy(sourceLetterCase: LetterCase, targetLetterCase: LetterCase): String {
	return splitBy(sourceLetterCase).joinToStringBy(targetLetterCase)
}

/**
 * 根据指定的字母格式，切换当前字符串的格式。如果不指定字母格式，则尝试推断或者抛出异常。
 *
 * @see LetterCase
 */
fun String.switchCaseBy(targetLetterCase: LetterCase): String {
	val sourceLetterCase = inferCase() ?: throw IllegalArgumentException("Cannot infer letter case for string '$this'.")
	return splitBy(sourceLetterCase).joinToStringBy(targetLetterCase)
}
//endregion

//region StringPattern Extensions
/**
 * 判断指定的字符串是否匹配指定的字符串模式。
 *
 * @see StringPattern
 */
fun String.matchesBy(stringPattern:StringPattern):Boolean{
	return stringPattern.matches(this)
}
//endregion

//region PathPattern Extensions
/**
 * 根据指定的路径类型，判断当前字符串是否匹配指定的路径。
 * 默认使用标准路径[PathPattern.StandardPath]。
 *
 * @see PathPattern
 */
fun String.matchesBy(path: String, pathPattern: PathPattern = PathPattern.StandardPath): Boolean {
	return pathPattern.matches(this, path)
}

/**
 * 根据指定的路径类型，解析路径变量。如果路径不匹配，则返回空结果。
 * 默认使用标准路径[PathPattern.StandardPath]。
 *
 * @see PathPattern
 */
fun String.resolveVariablesBy(path: String, pathPattern: PathPattern = PathPattern.StandardPath): Map<String, String> {
	return pathPattern.resolveVariables(this, path)
}


/**
 * 根据指定路径和指定路径类型查询当前数组，返回查询结果列表。
 * 如果指定路径为空路径，则目标返回查询对象的单例列表。
 * 默认使用标准路径[PathPattern.StandardPath]。
 *
 * @see PathPattern
 */
fun <T> Array<*>.queryBy(path: String, pathPattern: PathPattern = PathPattern.StandardPath): List<T> {
	return pathPattern.query(this, path)
}

/**
 * 根据指定路径和指定路径类型查询当前列表，返回查询结果列表。
 * 如果指定路径为空路径，则目标返回查询对象的单例列表。
 * 默认使用标准路径[PathPattern.StandardPath]。
 *
 * @see PathPattern
 */
fun <T> List<*>.queryBy(path: String, pathPattern: PathPattern = PathPattern.StandardPath): List<T> {
	return pathPattern.query(this, path)
}

/**
 * 根据指定路径和指定路径类型递归查询当前映射，返回查询结果列表。
 * 如果指定路径为空路径，则目标返回查询对象的单例列表。
 * 默认使用标准路径[PathPattern.StandardPath]。
 *
 * @see PathPattern
 */
fun <T> Map<*, *>.queryBy(path: String, pathPattern: PathPattern = PathPattern.StandardPath): List<T> {
	return pathPattern.query(this, path)
}


/**
 * 根据指定路径和指定路径类型查询当前数组，得到首个匹配的值，或者抛出异常。
 * 如果指定路径为空路径，则目标返回查询对象本身。
 * 默认使用标准路径[PathPattern.StandardPath]。
 *
 * @see PathPattern
 */
fun <T> Array<*>.getBy(path: String, pathPattern: PathPattern = PathPattern.StandardPath): T {
	return pathPattern.get(this, path)
}

/**
 * 根据指定路径和指定路径类型查询当前列表，得到首个匹配的值，或者抛出异常。
 * 如果指定路径为空路径，则目标返回查询对象本身。
 * 默认使用标准路径[PathPattern.StandardPath]。
 *
 * @see PathPattern
 */
fun <T> List<*>.getBy(path: String, pathPattern: PathPattern = PathPattern.StandardPath): T {
	return pathPattern.get(this, path)
}

/**
 * 根据指定路径和指定路径类型递归查询当前映射，得到首个匹配的值，或者抛出异常。
 * 如果指定路径为空路径，则目标返回查询对象本身。
 * 默认使用标准路径[PathPattern.StandardPath]。
 *
 * @see PathPattern
 */
fun <T> Map<*, *>.getBy(path: String, pathPattern: PathPattern = PathPattern.StandardPath): T {
	return pathPattern.get(this, path)
}


/**
 * 根据指定路径和指定路径类型查询当前数组，得到首个匹配的值，或者返回null。
 * 如果指定路径为空路径，则目标返回查询对象本身。
 * 默认使用标准路径[PathPattern.StandardPath]。
 *
 * @see PathPattern
 */
fun <T> Array<*>.getOrNullBy(path: String, pathPattern: PathPattern = PathPattern.StandardPath): T? {
	return pathPattern.getOrNull(this, path)
}

/**
 * 根据指定路径和指定路径类型查询当前列表，得到首个匹配的值，或者返回null。
 * 如果指定路径为空路径，则目标返回查询对象本身。
 * 默认使用标准路径[PathPattern.StandardPath]。
 *
 * @see PathPattern
 */
fun <T> List<*>.getOrNullBy(path: String, pathPattern: PathPattern = PathPattern.StandardPath): T? {
	return pathPattern.getOrNull(this, path)
}

/**
 * 根据指定路径和指定路径类型递归查询当前映射，得到首个匹配的值，或者返回null。
 * 如果指定路径为空路径，则目标返回查询对象本身。
 * 默认使用标准路径[PathPattern.StandardPath]。
 *
 * @see PathPattern
 */
fun <T> Map<*, *>.getOrNullBy(path: String, pathPattern: PathPattern = PathPattern.StandardPath): T? {
	return pathPattern.getOrNull(this, path)
}


/**
 * 根据指定路径和指定路径类型查询当前数组，得到首个匹配的值，或者返回null。
 * 如果指定路径为空路径，则目标返回查询对象本身。
 * 默认使用标准路径[PathPattern.StandardPath]。
 *
 * @see PathPattern
 */
fun <T> Array<*>.getOrElseBy(path: String, pathPattern: PathPattern = PathPattern.StandardPath, defaultValue: () -> T): T {
	return pathPattern.getOrElse(this, path, defaultValue)
}

/**
 * 根据指定路径和指定路径类型查询当前列表，得到首个匹配的值，或者返回null。
 * 如果指定路径为空路径，则目标返回查询对象本身。
 * 默认使用标准路径[PathPattern.StandardPath]。
 *
 * @see PathPattern
 */
fun <T> List<*>.getOrElseBy(path: String, pathPattern: PathPattern = PathPattern.StandardPath, defaultValue: () -> T): T {
	return pathPattern.getOrElse(this, path, defaultValue)
}

/**
 * 根据指定路径和指定路径类型递归查询当前映射，得到首个匹配的值，或者返回null。
 * 如果指定路径为空路径，则目标返回查询对象本身。
 * 默认使用标准路径[PathPattern.StandardPath]。
 *
 * @see PathPattern
 */
fun <T> Map<*, *>.getOrElseBy(path: String, pathPattern: PathPattern = PathPattern.StandardPath, defaultValue: () -> T): T {
	return pathPattern.getOrElse(this, path, defaultValue)
}
//endregion
