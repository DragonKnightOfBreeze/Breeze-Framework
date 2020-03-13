package com.windea.breezeframework.core.extensions

/**
 * 将当前布尔值转化成字符串，转化规则如下：
 * * 空值 -> 直接返回空字符串
 * * 其他情况 -> 对应的值
 */
fun Boolean?.typing(trueValue:String, falseValue:String = ""):String {
	return if(this == null) "" else if(this) trueValue else falseValue
}

/**
 * 将当前对象转化成字符串，转化规则如下：
 * * 空值和空字符串 -> 直接返回空字符串
 */
inline fun <T : Any> T?.typing(emptyValue:String = "", block:(String) -> String):String {
	return this?.toString().let { if(it.isNullOrEmpty()) emptyValue else block(it) }
}

/**
 * 将当前的一组对象转化成字符串，转化规则如下：
 * * 没有有效值的数组 -> 直接返回空字符串
 * * 空值和空字符串 -> 忽略该元素
 */
fun <T : Any> Array<out T?>.typingAll(
	separator:CharSequence = ", ", prefix:CharSequence = "", postfix:CharSequence = ""
):String = buildString {
	var count = 0
	for(element in this@typingAll) {
		val snippet = element?.toString()
		if(snippet.isNullOrEmpty()) continue
		if(count++ > 0) append(separator)
		append(element)
	}
	if(length > 0) insert(0, prefix).append(postfix)
}

/**
 * 将当前的一组对象转化成字符串，转化规则如下：
 * * 没有有效值的数组 -> 直接返回空字符串
 * * 空值和空字符串 -> 忽略该元素
 */
fun <T : Any> Iterable<T?>.typingAll(
	separator:CharSequence = ", ", prefix:CharSequence = "", postfix:CharSequence = ""
):String = buildString {
	var count = 0
	for(element in this@typingAll) {
		val snippet = element?.toString()
		if(snippet.isNullOrEmpty()) continue
		if(count++ > 0) append(separator)
		append(element)
	}
	if(length > 0) insert(0, prefix).append(postfix)
}
