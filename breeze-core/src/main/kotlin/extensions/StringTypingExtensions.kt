package com.windea.breezeframework.core.extensions

//这些特殊的字符串处理方法相比joinToString等更加灵活，更加适用于特殊情况
//随着需求的增加重构这些方法，使之更加灵活

/**
 * 使用特定的转化规则，将当前对象转化成字符串。
 * * 空值 -> 返回空字符串。
 * * 存在转化方法 -> 使用该方法将当前对象转化为字符串。
 * * 其他情况 -> 使用默认方法将当前对象转化为字符串。
 */
fun <T : Any> T?.typing(transform:((T) -> String)? = null):String {
	return if(this == null) "" else if(transform != null) transform(this) else toString()
}


/**
 * 使用特定的转化规则，将当前数组转化成字符串。
 * * 忽略空元素（默认为`true`） && 存在空元素 -> 忽略该元素.
 * * 忽略空数组（默认为`true`） && 不存在有效的元素 -> 返回空字符串。
 * * 存在转化方法 -> 使用该方法将元素转化为字符串。
 * * 其他情况 -> 使用默认方法将元素转化为字符串。
 */
fun <T : Any> Array<out T?>.typingAll(
	separator:CharSequence = ", ", prefix:CharSequence = "", postfix:CharSequence = "", contentIndent:String? = null,
	omitEmptyElement:Boolean = true, omitEmpty:Boolean = true,
	transform:((T) -> CharSequence)? = null
):String {
	var result = buildString {
		var count = 0
		for(element in this@typingAll) {
			val snippet = if(element == null) null else if(transform != null) transform(element) else element.toString()
			if(omitEmptyElement && snippet.isNullOrEmpty()) continue
			if(count++ > 0) append(separator)
			append(snippet)
		}
	}
	if(contentIndent != null) result = result.prependIndent(contentIndent)
	if(omitEmpty && result.isNotEmpty()) result = "$prefix$result$postfix"
	return result
}

/**
 * 使用特定的转化规则，将当前集合转化成字符串。
 * * 忽略空元素（默认为`true`） && 存在空元素 -> 忽略该元素.
 * * 忽略空集合（默认为`true`） && 不存在有效的元素 -> 返回空字符串。
 * * 存在转化方法 -> 使用该方法将元素转化为字符串。
 * * 其他情况 -> 使用默认方法将元素转化为字符串。
 */
fun <T : Any> Iterable<T?>.typingAll(
	separator:CharSequence = ", ", prefix:CharSequence = "", postfix:CharSequence = "", contentIndent:String? = null,
	omitEmpty:Boolean = true, omitEmptyElement:Boolean = true,
	transform:((T) -> CharSequence)? = null
):String {
	var result = buildString {
		var count = 0
		for(element in this@typingAll) {
			val snippet = if(element == null) null else if(transform != null) transform(element) else element.toString()
			if(omitEmptyElement && snippet.isNullOrEmpty()) continue
			if(count++ > 0) append(separator)
			append(snippet)
		}
	}
	if(contentIndent != null) result = result.prependIndent(contentIndent)
	if(omitEmpty && result.isNotEmpty()) result = "$prefix$result$postfix"
	return result
}

/**
 * 使用特定的转化规则，将当前映射转化成字符串。
 * * 忽略空键值对（默认为`true`） && 存在空键值对 -> 忽略该键值对.
 * * 忽略空值（默认为`true`） && 存在空值 -> 忽略该值.
 * * 忽略空映射（默认为`true`） && 不存在有效的键值对或值 -> 返回空字符串。
 * * 存在转化方法 -> 使用该方法将元素转化为字符串。
 * * 其他情况 -> 使用默认方法将元素转化为字符串。
 */
fun <K, V> Map<K, V>.typingAll(
	separator:CharSequence = ", ", prefix:CharSequence = "", postfix:CharSequence = "", contentIndent:String? = null,
	omitEmpty:Boolean = true, omitEmptyEntry:Boolean = true, omitEmptyValue:Boolean = true,
	transform:((Map.Entry<K, V>) -> CharSequence)? = null
):String {
	var result =  buildString {
		var count = 0
		for(entry in this@typingAll) {
			val valueSnippet = entry.value?.toString()
			if(omitEmptyValue && valueSnippet.isNullOrEmpty()) continue
			val snippet = if(transform == null) entry.toString() else transform(entry)
			if(omitEmptyEntry && snippet.isEmpty()) continue
			if(count++ > 0) append(separator)
			append(snippet)
		}
		if(omitEmpty && length > 0) insert(0, prefix).append(postfix)
	}
	if(contentIndent != null) result = result.prependIndent(contentIndent)
	if(omitEmpty && result.isNotEmpty()) result = "$prefix$result$postfix"
	return result
}

/**
 * 将当前的一组对象转化成字符串，默认的转化规则如下：
 * * 没有有效值的序列 && 忽略空虚列  -> 返回空字符串
 * * 空值和空字符串 && 忽略空元素 -> 忽略该元素
 * * 存在转化方法 -> 使用转化方法转化元素
 */
fun <T : Any> Sequence<T?>.typingAll(
	separator:CharSequence = ", ", prefix:CharSequence = "", postfix:CharSequence = "", contentIndent:String? = null,
	omitEmpty:Boolean = true, omitEmptyElement:Boolean = true,
	transform:((T) -> CharSequence)? = null
):String {
	var result =  buildString {
		var count = 0
		for(element in this@typingAll) {
			val snippet = if(element == null) null else if(transform != null) transform(element) else element.toString()
			if(omitEmptyElement && snippet.isNullOrEmpty()) continue
			if(count++ > 0) append(separator)
			append(snippet)
		}
		if(omitEmpty && length > 0) insert(0, prefix).append(postfix)
	}
	if(contentIndent != null) result = result.prependIndent(contentIndent)
	if(omitEmpty && result.isNotEmpty()) result = "$prefix$result$postfix"
	return result
}
