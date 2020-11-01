// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.core.component.*
import java.lang.reflect.*
import java.nio.charset.*

//region Converter Extensions
/**
 * 将当前对象转化为指定类型。如果转换失败，则抛出异常。转化后的对象是基于一般转化逻辑得到的新对象。
 */
@BreezeComponentExtension
inline fun <reified T> Any?.convert(): T {
	return if(this is T) this else Converter.convert(this)
}

/**
 * 将当前对象转化为指定类型。如果转换失败，则返回null。转化后的对象是基于一般转化逻辑得到的新对象。
 */
@BreezeComponentExtension
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
@BreezeComponentExtension
fun String.escapeBy(escaper: Escaper): String {
	return escaper.escape(this)
}

/**
 * 根据指定的转义器，反转义当前字符串。
 *
 * @see Escaper
 */
@BreezeComponentExtension
fun String.unescapeBy(escaper: Escaper): String {
	return escaper.unescape(this)
}
//endregion

//region Decoder Extensions
/**
 * 根据指定的编码器，编码当前字符串，以指定的字符集显示。
 */
@BreezeComponentExtension
fun String.encodeBy(encoder: Encoder, charset: Charset = Charsets.UTF_8): String {
	return encoder.encode(this, charset)
}

/**
 * 根据指定的编码器，解码当前字符串，以指定的字符集显示。
 */
@BreezeComponentExtension
fun String.decodeBy(encoder: Encoder, charset: Charset = Charsets.UTF_8): String {
	return encoder.decode(this, charset)
}
//endregion

//region Encrypter Extensions
/**
 * 根据指定的加密器，加密当前字节数组。
 */
@BreezeComponentExtension
fun ByteArray.encryptBy(encrypter: Encrypter,secret:ByteArray?=null): ByteArray {
	return encrypter.encrypt(this,secret)
}

/**
 * 根据指定的加密器，解密当前字符串。某些加密算法可能不支持解密。
 *
 * @throws UnsupportedOperationException 如果不支持解密。
 */
@BreezeComponentExtension
fun ByteArray.decryptBy(encrypter: Encrypter,secret: ByteArray?=null): ByteArray {
	return encrypter.decrypt(this,secret)
}
//endregion

//region CaseType Extensions
/**
 * 尝试推断当前字符串的字母格式。
 */
@BreezeComponentExtension
fun String.inferLetterCase(): CaseType? {
	return CaseType.infer(this)
}

/**
 * 判断当前字符串是否匹配指定的字母格式。
 *
 * @see CaseType
 */
@BreezeComponentExtension
fun String.matchesBy(caseType: CaseType): Boolean {
	return caseType.matches(this)
}

/**
 * 根据指定的字母格式，分割当前字符串，返回对应的字符串列表。
 *
 * @see CaseType
 */
@BreezeComponentExtension
fun String.splitBy(caseType: CaseType): List<String> {
	return caseType.split(this)
}

/**
 * 根据指定的字母格式，分割当前字符串，返回对应的字符串序列。
 *
 * @see CaseType
 */
@BreezeComponentExtension
fun String.splitToSequenceBy(caseType: CaseType): Sequence<String> {
	return caseType.splitToSequence(this)
}

/**
 * 根据指定的字母格式，将当前字符串数组中的元素加入到字符串。
 *
 * @see CaseType
 */
@BreezeComponentExtension
fun Array<String>.joinToStringBy(caseType: CaseType): String {
	return caseType.joinToString(this)
}

/**
 * 根据指定的字母格式，将当前字符串集合中的元素加入到字符串。
 *
 * @see CaseType
 */
@BreezeComponentExtension
fun Iterable<String>.joinToStringBy(caseType: CaseType): String {
	return caseType.joinToString(this)
}

/**
 * 根据指定的字母格式，将当前字符串序列中的元素加入到字符串。
 *
 * @see CaseType
 */
@BreezeComponentExtension
fun Sequence<String>.joinToStringBy(caseType: CaseType): String {
	return caseType.joinToString(this)
}

/**
 * 根据指定的字母格式，切换当前字符串的格式。
 *
 * @see CaseType
 */
@BreezeComponentExtension
fun String.switchCaseBy(sourceCaseType: CaseType, targetCaseType: CaseType): String {
	return splitBy(sourceCaseType).joinToStringBy(targetCaseType)
}

/**
 * 根据指定的字母格式，切换当前字符串的格式。如果不指定字母格式，则尝试推断或者抛出异常。
 *
 * @see CaseType
 */
@BreezeComponentExtension
fun String.switchCaseBy(targetCaseType: CaseType): String {
	val sourceLetterCase = inferLetterCase() ?: throw IllegalArgumentException("Cannot infer case type for string '$this'.")
	return splitBy(sourceLetterCase).joinToStringBy(targetCaseType)
}
//endregion

//region PathType Extensions
/**
 * 根据指定的路径类型，判断当前字符串是否匹配指定的路径。
 * 默认使用标准路径[PathType.StandardPath]。
 *
 * @see PathType
 */
@BreezeComponentExtension
fun String.matchesBy(path: String, pathType: PathType = PathType.StandardPath): Boolean {
	return pathType.matches(this, path)
}

/**
 * 根据指定的路径类型，解析路径变量。如果路径不匹配，则返回空结果。
 * 默认使用标准路径[PathType.StandardPath]。
 *
 * @see PathType
 */
@BreezeComponentExtension
fun String.resolveVariablesBy(path: String, pathType: PathType = PathType.StandardPath): Map<String, String> {
	return pathType.resolveVariables(this, path)
}


/**
 * 根据指定路径和指定路径类型查询当前数组，返回查询结果列表。
 * 如果指定路径为空路径，则目标返回查询对象的单例列表。
 * 默认使用标准路径[PathType.StandardPath]。
 *
 * @see PathType
 */
@BreezeComponentExtension
fun <T> Array<*>.queryBy(path: String, pathType: PathType = PathType.StandardPath): List<T> {
	return pathType.query(this, path)
}

/**
 * 根据指定路径和指定路径类型查询当前列表，返回查询结果列表。
 * 如果指定路径为空路径，则目标返回查询对象的单例列表。
 * 默认使用标准路径[PathType.StandardPath]。
 *
 * @see PathType
 */
@BreezeComponentExtension
fun <T> List<*>.queryBy(path: String, pathType: PathType = PathType.StandardPath): List<T> {
	return pathType.query(this, path)
}

/**
 * 根据指定路径和指定路径类型递归查询当前映射，返回查询结果列表。
 * 如果指定路径为空路径，则目标返回查询对象的单例列表。
 * 默认使用标准路径[PathType.StandardPath]。
 *
 * @see PathType
 */
@BreezeComponentExtension
fun <T> Map<*, *>.queryBy(path: String, pathType: PathType = PathType.StandardPath): List<T> {
	return pathType.query(this, path)
}


/**
 * 根据指定路径和指定路径类型查询当前数组，得到首个匹配的值，或者抛出异常。
 * 如果指定路径为空路径，则目标返回查询对象本身。
 * 默认使用标准路径[PathType.StandardPath]。
 *
 * @see PathType
 */
@BreezeComponentExtension
fun <T> Array<*>.getBy(path: String, pathType: PathType = PathType.StandardPath): T {
	return pathType.get(this, path)
}

/**
 * 根据指定路径和指定路径类型查询当前列表，得到首个匹配的值，或者抛出异常。
 * 如果指定路径为空路径，则目标返回查询对象本身。
 * 默认使用标准路径[PathType.StandardPath]。
 *
 * @see PathType
 */
@BreezeComponentExtension
fun <T> List<*>.getBy(path: String, pathType: PathType = PathType.StandardPath): T {
	return pathType.get(this, path)
}

/**
 * 根据指定路径和指定路径类型递归查询当前映射，得到首个匹配的值，或者抛出异常。
 * 如果指定路径为空路径，则目标返回查询对象本身。
 * 默认使用标准路径[PathType.StandardPath]。
 *
 * @see PathType
 */
@BreezeComponentExtension
fun <T> Map<*, *>.getBy(path: String, pathType: PathType = PathType.StandardPath): T {
	return pathType.get(this, path)
}


/**
 * 根据指定路径和指定路径类型查询当前数组，得到首个匹配的值，或者返回null。
 * 如果指定路径为空路径，则目标返回查询对象本身。
 * 默认使用标准路径[PathType.StandardPath]。
 *
 * @see PathType
 */
@BreezeComponentExtension
fun <T> Array<*>.getOrNullBy(path: String, pathType: PathType = PathType.StandardPath): T? {
	return pathType.getOrNull(this, path)
}

/**
 * 根据指定路径和指定路径类型查询当前列表，得到首个匹配的值，或者返回null。
 * 如果指定路径为空路径，则目标返回查询对象本身。
 * 默认使用标准路径[PathType.StandardPath]。
 *
 * @see PathType
 */
@BreezeComponentExtension
fun <T> List<*>.getOrNullBy(path: String, pathType: PathType = PathType.StandardPath): T? {
	return pathType.getOrNull(this, path)
}

/**
 * 根据指定路径和指定路径类型递归查询当前映射，得到首个匹配的值，或者返回null。
 * 如果指定路径为空路径，则目标返回查询对象本身。
 * 默认使用标准路径[PathType.StandardPath]。
 *
 * @see PathType
 */
@BreezeComponentExtension
fun <T> Map<*, *>.getOrNullBy(path: String, pathType: PathType = PathType.StandardPath): T? {
	return pathType.getOrNull(this, path)
}


/**
 * 根据指定路径和指定路径类型查询当前数组，得到首个匹配的值，或者返回null。
 * 如果指定路径为空路径，则目标返回查询对象本身。
 * 默认使用标准路径[PathType.StandardPath]。
 *
 * @see PathType
 */
@BreezeComponentExtension
fun <T> Array<*>.getOrElseBy(path: String, pathType: PathType = PathType.StandardPath, defaultValue: () -> T): T {
	return pathType.getOrElse(this, path, defaultValue)
}

/**
 * 根据指定路径和指定路径类型查询当前列表，得到首个匹配的值，或者返回null。
 * 如果指定路径为空路径，则目标返回查询对象本身。
 * 默认使用标准路径[PathType.StandardPath]。
 *
 * @see PathType
 */
@BreezeComponentExtension
fun <T> List<*>.getOrElseBy(path: String, pathType: PathType = PathType.StandardPath, defaultValue: () -> T): T {
	return pathType.getOrElse(this, path, defaultValue)
}

/**
 * 根据指定路径和指定路径类型递归查询当前映射，得到首个匹配的值，或者返回null。
 * 如果指定路径为空路径，则目标返回查询对象本身。
 * 默认使用标准路径[PathType.StandardPath]。
 *
 * @see PathType
 */
@BreezeComponentExtension
fun <T> Map<*, *>.getOrElseBy(path: String, pathType: PathType = PathType.StandardPath, defaultValue: () -> T): T {
	return pathType.getOrElse(this, path, defaultValue)
}
//endregion

//region DataType Extensions
/**
 * 根据指定的数据类型，序列化当前对象。
 */
@BreezeComponentExtension
fun <T:Any> T.serialize(dataType:DataType):String{
	return dataType.serialize(this)
}

/**
 * 根据指定的数据类型，反序列化当前文本。
 */
@BreezeComponentExtension
inline fun <reified T:Any> String.deserialize(dataType:DataType):T{
	return dataType.deserialize(this,javaTypeOf<T>())
}

/**
 * 根据指定的数据类型，反序列化当前文本。
 */
@BreezeComponentExtension
fun <T:Any> String.deserialize(dataType: DataType,type:Class<T>):T{
	return dataType.deserialize(this,type)
}

/**
 * 根据指定的数据类型，反序列化当前文本。
 */
@BreezeComponentExtension
fun <T:Any> String.deserialize(dataType: DataType,type: Type):T{
	return dataType.deserialize(this,type)
}
//endregion
