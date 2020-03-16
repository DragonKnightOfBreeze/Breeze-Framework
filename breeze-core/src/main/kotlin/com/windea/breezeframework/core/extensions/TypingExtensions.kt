package com.windea.breezeframework.core.extensions

//这些特殊的字符串处理方法相比joinToString等更加灵活，更加适用于特殊情况
//随着需求的增加重构这些方法，使之更加灵活

/**
 * 将当前对象转化成字符串，转化规则如下：
 * * 空值 -> 返回对应的值（默认为空字符串）
 * * 空字符串 -> 返回对应的值（默认为空字符串）
 */
fun <T : Any> T?.typing(emptyValue:String = "", nullValue:String = "", block:(String) -> String):String {
	return this?.toString()?.let { if(it.isEmpty()) emptyValue else block(it) } ?: nullValue
}

/**
 * 将当前布尔值转化成字符串，转化规则如下：
 * * 空值 -> 返回对应的值（默认为空字符串）
 * * 其他情况 -> 返回对应的值
 */
fun Boolean?.typing(trueValue:String, falseValue:String = "", nullValue:String = ""):String {
	return this?.let { if(it) trueValue else falseValue } ?: nullValue
}

/**
 * 将当前的一组对象转化成字符串，转化规则如下：
 * * 没有有效值的数组 && 忽略空数组 -> 直接返回空字符串
 * * 空值和空字符串 && 忽略空元素 -> 忽略该元素
 * * 存在转化方法 -> 使用转化方法转化元素
 */
fun <T : Any> Array<out T?>.typingAll(
	separator:CharSequence = ", ", prefix:CharSequence = "", postfix:CharSequence = "",
	ignoreEmpty:Boolean = true, ignoreEmptyElement:Boolean = true,
	transform:((T) -> CharSequence)? = null
):String = buildString {
	var count = 0
	for(element in this@typingAll) {
		val snippet = if(element == null) null else if(transform == null) element.toString() else transform(element)
		if(ignoreEmptyElement && snippet.isNullOrEmpty()) continue
		if(count++ > 0) append(separator)
		append(snippet)
	}
	if(ignoreEmpty && length > 0) insert(0, prefix).append(postfix)
}

/**
 * 将当前的一组对象转化成字符串，转化规则如下：
 * * 没有有效值的集合 && 忽略空集合 -> 直接返回空字符串
 * * 空值和空字符串  && 忽略空元素 -> 忽略该元素
 * * 存在转化方法 -> 使用转化方法转化元素
 */
fun <T : Any> Iterable<T?>.typingAll(
	separator:CharSequence = ", ", prefix:CharSequence = "", postfix:CharSequence = "",
	ignoreEmpty:Boolean = true, ignoreEmptyElement:Boolean = true,
	transform:((T) -> CharSequence)? = null
):String = buildString {
	var count = 0
	for(element in this@typingAll) {
		val snippet = if(element == null) null else if(transform == null) element.toString() else transform(element)
		if(ignoreEmptyElement && snippet.isNullOrEmpty()) continue
		if(count++ > 0) append(separator)
		append(snippet)
	}
	if(ignoreEmpty && length > 0) insert(0, prefix).append(postfix)
}

/**
 * 将当前的一组键值对转化成字符串，转化规则如下：
 * * 没有有效值的数组 && 忽略空元素 -> 直接返回空字符串
 * * 空值和空字符串 && 忽略空键值对 -> 忽略该键值对
 * * 存在转化方法 -> 使用转化方法转化键值对
 */
fun <K, V> Map<K, V>.typingAll(
	separator:CharSequence = ", ", prefix:CharSequence = "", postfix:CharSequence = "",
	ignoreEmpty:Boolean = true, ignoreEmptyElement:Boolean = true,
	transform:((Map.Entry<K, V>) -> CharSequence)? = null
):String = buildString {
	var count = 0
	for(entry in this@typingAll) {
		val snippet = if(transform == null) entry.toString() else transform(entry)
		if(ignoreEmptyElement && snippet.isEmpty()) continue
		if(count++ > 0) append(separator)
		append(snippet)
	}
	if(ignoreEmpty && length > 0) insert(0, prefix).append(postfix)
}

/**
 * 将当前的一组对象转化成字符串，转化规则如下：
 * * 没有有效值的序列 && 忽略空虚列  -> 返回空字符串
 * * 空值和空字符串 && 忽略空元素 -> 忽略该元素
 * * 存在转化方法 -> 使用转化方法转化元素
 */
fun <T : Any> Sequence<T?>.typingAll(
	separator:CharSequence = ", ", prefix:CharSequence = "", postfix:CharSequence = "",
	ignoreEmpty:Boolean = true, ignoreEmptyElement:Boolean = true,
	transform:((T) -> CharSequence)? = null
):String = buildString {
	var count = 0
	for(element in this@typingAll) {
		val snippet = if(element == null) null else if(transform == null) element.toString() else transform(element)
		if(ignoreEmptyElement && snippet.isNullOrEmpty()) continue
		if(count++ > 0) append(separator)
		append(snippet)
	}
	if(ignoreEmpty && length > 0) insert(0, prefix).append(postfix)
}
