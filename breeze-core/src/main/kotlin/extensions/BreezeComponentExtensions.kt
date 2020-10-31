// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.core.domain.*

//region Any Extensions
/**
 * 将当前对象转化为指定类型。如果转换失败，则抛出异常。转化后的对象是基于一般转化逻辑得到的新对象。
 */
inline fun <reified T> Any?.convert(): T {
	return if(this is T) this else Converter.convert(this)
}

/**
 * 将当前对象转化为指定类型。如果转换失败，则返回null。转化后的对象是基于一般转化逻辑得到的新对象。
 */
inline fun <reified T> Any?.convertOrNull(): T? {
	return if(this is T) this else Converter.convertOrNull(this)
}
//endregion

//region String Extensions
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


/**
 * 尝试推断当前字符串的字母格式。
 */
@BreezeComponentExtension
fun String.inferLetterCase():LetterCase?{
	return LetterCase.infer(this)
}

/**
 * 判断当前字符串是否匹配指定的字母格式。
 *
 * @see LetterCase
 */
@BreezeComponentExtension
fun String.matchesBy(letterCase: LetterCase):Boolean{
	return letterCase.matches(this)
}

/**
 * 根据指定的字母格式，分割当前字符串，返回对应的字符串列表。
 *
 * @see LetterCase
 */
@BreezeComponentExtension
fun String.splitBy(letterCase: LetterCase): List<String> {
	return letterCase.split(this)
}

/**
 * 根据指定的字母格式，分割当前字符串，返回对应的字符串序列。
 *
 * @see LetterCase
 */
@BreezeComponentExtension
fun String.splitToSequenceBy(letterCase: LetterCase): Sequence<String> {
	return letterCase.splitToSequence(this)
}

/**
 * 根据指定的字母格式，将当前字符串数组中的元素加入到字符串。
 *
 * @see LetterCase
 */
@BreezeComponentExtension
fun Array<String>.joinToStringBy(letterCase: LetterCase): String {
	return letterCase.joinToString(this)
}

/**
 * 根据指定的字母格式，将当前字符串集合中的元素加入到字符串。
 *
 * @see LetterCase
 */
@BreezeComponentExtension
fun Iterable<String>.joinToStringBy(letterCase: LetterCase): String {
	return letterCase.joinToString(this)
}

/**
 * 根据指定的字母格式，将当前字符串序列中的元素加入到字符串。
 *
 * @see LetterCase
 */
@BreezeComponentExtension
fun Sequence<String>.joinToStringBy(letterCase: LetterCase ): String {
	return letterCase.joinToString(this)
}

/**
 * 根据指定的字母格式，切换当前字符串的格式。
 *
 * @see LetterCase
 */
@BreezeComponentExtension
fun String.switchCaseBy(sourceLetterCase: LetterCase, targetLetterCase: LetterCase): String {
	return splitBy(sourceLetterCase).joinToStringBy(targetLetterCase)
}

/**
 * 根据指定的字母格式，切换当前字符串的格式。如果不指定字母格式，则尝试推断或者抛出异常。
 *
 * @see LetterCase
 */
@BreezeComponentExtension
fun String.switchCaseBy(targetLetterCase: LetterCase): String {
	val sourceLetterCase = inferLetterCase()?: throw IllegalArgumentException("Cannot infer letter case for string '$this'.")
	return splitBy(sourceLetterCase).joinToStringBy(targetLetterCase)
}


/**
 * 根据指定的路径类型，判断当前字符串是否匹配指定的路径。
 * 默认使用标准路径[PathType.StandardPath]。
 *
 * @see PathType
 */
@BreezeComponentExtension
fun String.matchesBy(path:String,pathType:PathType = PathType.StandardPath):Boolean{
	return pathType.matches(this,path)
}

/**
 * 根据指定的路径类型，解析路径变量。如果路径不匹配，则返回空结果。
 * 默认使用标准路径[PathType.StandardPath]。
 *
 * @see PathType
 */
@BreezeComponentExtension
fun String.resolveVariablesBy(path:String,pathType:PathType = PathType.StandardPath):Map<String,String>{
	return pathType.resolveVariables(this,path)
}
//endregion

//region Collection Extensions
/**
 * 根据指定路径和指定路径类型查询当前数组，返回查询结果列表。
 * 如果指定路径为空路径，则目标返回查询对象的单例列表。
 * 默认使用标准路径[PathType.StandardPath]。
 *
 * @see PathType
 */
@BreezeComponentExtension
fun <T> Array<*>.queryBy(path:String,pathType: PathType = PathType.StandardPath):List<T>{
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
fun <T> List<*>.queryBy(path:String,pathType: PathType = PathType.StandardPath):List<T>{
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
fun <T> Map<*,*>.queryBy(path:String,pathType: PathType = PathType.StandardPath):List<T>{
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
fun <T> Array<*>.getBy(path:String,pathType: PathType = PathType.StandardPath):T{
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
fun <T> List<*>.getBy(path:String,pathType: PathType = PathType.StandardPath):T{
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
fun <T> Map<*,*>.getBy(path:String,pathType: PathType = PathType.StandardPath):T{
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
fun <T> Array<*>.getOrNullBy(path:String,pathType: PathType = PathType.StandardPath):T?{
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
fun <T> List<*>.getOrNullBy(path:String,pathType: PathType = PathType.StandardPath):T?{
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
fun <T> Map<*,*>.getOrNullBy(path:String,pathType: PathType = PathType.StandardPath):T?{
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
fun <T> Array<*>.getOrElseBy(path:String,pathType: PathType = PathType.StandardPath,defaultValue:()->T):T{
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
fun <T> List<*>.getOrElseBy(path:String,pathType: PathType = PathType.StandardPath,defaultValue:()->T):T{
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
fun <T> Map<*,*>.getOrElseBy(path:String,pathType: PathType = PathType.StandardPath,defaultValue:()->T):T{
	return pathType.getOrElse(this, path, defaultValue)
}
//endregion
